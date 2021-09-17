package net.congueror.clib.api.machine.fluid;

import net.congueror.clib.api.machine.ModEnergyStorage;
import net.congueror.clib.api.machine.tickable.AbstractTickableBlockEntity;
import net.congueror.clib.api.recipe.FluidRecipeWrapper;
import net.congueror.clib.api.recipe.IFluidRecipe;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public abstract class AbstractFluidBlockEntity extends AbstractTickableBlockEntity implements IFluidHandler, MenuProvider {
    protected ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected FluidRecipeWrapper wrapper = new FluidRecipeWrapper(itemHandler, this);

    /**
     * Initialization in subclass constructor like so:<p>
     * <pre>tanks = IntStream.range(0, [Amount of tanks]).mapToObj(k -> new FluidTank([Capacity])).toArray(FluidTank[]::new);</pre>
     */
    public FluidTank[] tanks;
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this);

    protected ModEnergyStorage energyStorage;
    protected LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    protected float progress;
    protected int processTime;
    public static final int FIELDS_COUNT = 2;
    public final ContainerData data = new ContainerData() {

        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> (int) progress;
                case 1 -> processTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> processTime = value;
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };

    public AbstractFluidBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);

        this.energyStorage = createEnergy();
    }

    /**
     * The recipe that the tile entity uses. This can be acquired by using the recipe manager like so:
     * <pre>world.getRecipeManager().getRecipe([RECIPE], wrapper, world).orElse(null)</pre>
     */
    @Nullable
    public abstract IFluidRecipe<?> getRecipe();

    /**
     * An array of numbers that represent slots. Must start at 0. Must include 4 additional slots at the end for upgrades.
     */
    public abstract int[] invSize();

    /**
     * A map of all output slot and tank indexes. String should be either "tanks" or "slots" and the array of integers should be the indexes of all output slots/tanks.
     */
    public abstract HashMap<String, int[]> outputSlotsAndTanks();

    /**
     * Check whether an item can fit in the given slot.
     * @param slot  The slot of the item
     * @param stack The ItemStack in the slot.
     */
    public abstract boolean canItemFit(int slot, ItemStack stack);

    /**
     * The amount of FE consumed per tick.
     */
    public abstract int getEnergyUsage();

    /**
     * The capacity of the energy buffer.
     */
    public abstract int getEnergyCapacity();

    /**
     * The amount of ticks it takes for the machine to complete a process
     */
    public abstract int getProcessTime();

    /**
     * The fluid results of the given recipe.
     *
     * @param recipe The recipe
     * @return A {@link Collection} of {@link FluidStack}s.
     */
    @Nullable
    public abstract Collection<FluidStack> getFluidResults(IFluidRecipe<?> recipe);

    /**
     * The item results of the given recipe.
     *
     * @param recipe The recipe
     * @return A {@link Collection} of {@link ItemStack}s.
     */
    @Nullable
    public abstract Collection<ItemStack> getItemResults(IFluidRecipe<?> recipe);

    /**
     * Here you can add any additional requirements for the machine to start
     *
     * @return True if the machine should run.
     */
    public abstract boolean requisites();

    /**
     * The result of the machine.
     */
    public abstract void execute();

    /**
     * What happens to a slot when it detects an item.
     */
    public abstract void executeSlot();

    @Override
    public void tick() {
        if (level != null && level.isClientSide) {
            return;
        }

        FluidStack tank = tanks[0].getFluid();
        IFluidRecipe<?> recipe = getRecipe();
        if (recipe != null && shouldRun()) {
            if (energyStorage.getEnergyStored() >= getEnergyUsage() && requisites()) {
                processTime = getProcessTime();
                progress += getProgressSpeed();
                energyStorage.consumeEnergy(getEnergyUsage());
                setChanged();
                if (progress >= processTime) {
                    execute();
                    progress = 0;
                    sendUpdate(getActiveState());
                    setChanged();
                } else {
                    sendUpdate(getActiveState());
                }
            } else {
                if (progress > 0) {
                    progress--;
                }
            }
        } else {
            if (tank.getAmount() < 100) {
                progress = 0;
            }
            sendUpdate(getInactiveState());
        }

        sendOutFluid(outputSlotsAndTanks().get("tanks"));
        executeSlot();
    }

    /**
     * Checks whether the tile entity should run based on the operation type.
     */
    public boolean shouldRun() {
        boolean flag = false;
        boolean flag1 = false;
        for (int i : outputSlotsAndTanks().get("tanks")) {
            flag = tanks[i].getFluidAmount() + getFluidProcessSize() <= tanks[i].getCapacity();
        }
        for (int i : outputSlotsAndTanks().get("slots")) {
            flag1 = itemHandler.getStackInSlot(i).getCount() < itemHandler.getStackInSlot(i).getMaxStackSize();
        }
        return flag || flag1;
    }

    /**
     * Puts the resulting fluid stack in the output tanks.
     * @param result The resulting {@link FluidStack}. Should be used like so:
     * <pre>getFluidResults(getRecipe()).forEach(this::storeResultFluid)</pre>
     */
    protected void storeResultFluid(FluidStack result) {
        for (int i : outputSlotsAndTanks().get("tanks")) {
            FluidStack output = tanks[i].getFluid();
            if (getFluidProcessSize() + output.getAmount() <= tanks[i].getCapacity()) {
                tanks[i].fill(result, FluidAction.EXECUTE);
            }
        }
    }

    /**
     * Calculates how much the {@link #progress} will be increased each tick.
     *
     * @return the progress per tick.
     */
    public float getProgressSpeed() {
        int[] slots = new int[]{invSize().length - 1, invSize().length - 2, invSize().length - 3, invSize().length - 4};
        int progress = 1;
        for (int i = 0; i < slots.length; i++) {
            if (itemHandler.getStackInSlot(i).getItem() instanceof UpgradeItem) {
                if (((UpgradeItem) itemHandler.getStackInSlot(i).getItem()).getType().equals(UpgradeItem.UpgradeType.SPEED)) {
                    progress += 1;
                }
            }
        }
        return progress;
    }

    /**
     * Calculates how many mB per operation will be consumed.
     *
     * @return The mB per operation.
     */
    public int getFluidProcessSize() {
        int[] slots = new int[]{invSize().length - 1, invSize().length - 2, invSize().length - 3, invSize().length - 4};
        int stack = 0;
        for (FluidStack i : Objects.requireNonNull(getFluidResults(getRecipe()))) {
            stack = i.getAmount();
        }
        for (int i = 0; i < slots.length; i++) {
            if (itemHandler.getStackInSlot(i).getItem() instanceof UpgradeItem) {
                if (((UpgradeItem) itemHandler.getStackInSlot(i).getItem()).getType().equals(UpgradeItem.UpgradeType.STACK)) {
                    stack += 100;
                }
            }
        }
        return stack;
    }

    public int getEnergyUsageFinal() {//TODO
        int usage = getEnergyUsage();
        return usage;
    }

    /**
     * Used by block to drop contents when broken.
     *
     * @return A list of item stacks inside the te.
     */
    public NonNullList<ItemStack> getDrops() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < invSize().length; i++) {
            drops.add(itemHandler.getStackInSlot(i));
        }
        return drops;
    }

    /**
     * Sends the fluid in the given tanks to other tile entities that have the {@link IFluidHandler} capability around it.
     *
     * @param tank The tanks whose fluid will be extracted.
     */
    public void sendOutFluid(int... tank) {
        for (int i : tank) {
            FluidStack fluidStack = tanks[i].getFluid();
            if (fluidStack.getAmount() > 0 && !fluidStack.isEmpty()) {
                for (Direction direction : Direction.values()) {
                    assert level != null;
                    BlockEntity te = level.getBlockEntity(worldPosition.relative(direction));
                    if (te != null) {
                        boolean doContinue = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction).map(handler -> {
                                    if (handler.getTanks() >= 0) {
                                        int fill = handler.fill(fluidStack, FluidAction.EXECUTE);
                                        fluidStack.shrink(fill);
                                        tanks[i].getFluid().shrink(fill);
                                        setChanged();
                                        return fluidStack.getAmount() > 0;
                                    } else {
                                        return true;
                                    }
                                }
                        ).orElse(true);
                        if (!doContinue) {
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Empties the bucket at the given slots in the given tank. Usually used with {@link #executeSlot()}
     *
     * @param tank  The tank to be filled
     * @param slots The slots to be emptied
     */
    public void emptyBucketSlot(int tank, int... slots) {
        for (int i : slots) {
            ItemStack slot = itemHandler.getStackInSlot(i);
            if (slot.getItem() instanceof BucketItem) {
                if (((BucketItem) slot.getItem()).getFluid() != Fluids.EMPTY) {
                    if (tanks[tank].isEmpty()) {
                        tanks[tank].setFluid(new FluidStack(((BucketItem) slot.getItem()).getFluid(), 1000));
                    } else if (tanks[tank].getFluid().getFluid().equals(((BucketItem) slot.getItem()).getFluid())) {
                        tanks[tank].getFluid().grow(1000);
                    } else {
                        return;
                    }
                    itemHandler.setStackInSlot(i, new ItemStack(Items.BUCKET));
                }
            }
        }
    }

    /**
     * Fills the bucket at the given slots with the fluid in the given tank.
     *
     * @param tank  The tank to be emptied.
     * @param slots The slots to be filled
     */
    public void fillBucketSlot(int tank, int... slots) {
        for (int i : slots) {
            ItemStack slot1 = itemHandler.getStackInSlot(i);
            if (slot1.getItem().equals(Items.BUCKET)) {
                if (tanks[tank].getFluid().getAmount() > 1000) {
                    itemHandler.setStackInSlot(i, tanks[tank].getFluid().getFluid().getAttributes().getBucket(tanks[tank].getFluid()));
                    tanks[tank].getFluid().shrink(1000);
                } else if (tanks[tank].getFluid().getAmount() == 1000) {
                    itemHandler.setStackInSlot(i, tanks[tank].getFluid().getFluid().getAttributes().getBucket(tanks[tank].getFluid()));
                    tanks[tank].setFluid(FluidStack.EMPTY);
                } else if (tanks[tank].getFluid().getAmount() < 1000) {
                    break;
                }
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);//TODO: Change which side can accept a capability
    }

    private ModEnergyStorage createEnergy() {
        return new ModEnergyStorage(getEnergyCapacity(), 1000, 0);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(invSize().length) {

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return canItemFit(slot, stack);
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                if (stack.getItem() instanceof UpgradeItem) {
                    return 1;
                }
                return super.getStackLimit(slot, stack);
            }
        };
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        fluidHandler.invalidate();
        energy.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("Progress");
        this.processTime = nbt.getInt("ProcessTime");
        energyStorage.deserializeNBT(nbt.getCompound("energy"));

        for (int i = 0; i < tanks.length; i++) {
            if (nbt.contains("tank" + i)) {
                tanks[i].setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("tank" + i)));
            }
        }

        super.load(nbt);
    }

    @Nonnull
    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("Progress", (int) this.progress);
        tag.putInt("ProcessTime", this.processTime);
        tag.put("energy", energyStorage.serializeNBT());

        for (int i = 0; i < tanks.length; i++) {
            if (!tanks[i].isEmpty()) {
                tag.put("tank" + i, tanks[i].getFluid().writeToNBT(new CompoundTag()));
            }
        }

        return super.save(tag);
    }

    protected void sendUpdate(BlockState newState) {
        if (level == null)
            return;
        BlockState oldState = level.getBlockState(worldPosition);
        if (oldState != newState) {
            level.setBlock(worldPosition, newState, 3);
            level.sendBlockUpdated(worldPosition, oldState, newState, 3);
        }
    }

    protected BlockState getActiveState() {
        return getBlockState().setValue(AbstractFurnaceBlock.LIT, true);
    }

    protected BlockState getInactiveState() {
        return getBlockState().setValue(AbstractFurnaceBlock.LIT, false);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public int getTanks() {
        return tanks.length;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank < 0 || tank >= tanks.length) {
            return FluidStack.EMPTY;
        }
        return tanks[tank].getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        if (tank < 0 || tank >= tanks.length) {
            return 0;
        }
        return tanks[tank].getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        if (tank < 0 || tank >= tanks.length) {
            return false;
        }
        return tanks[tank].isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        for (int i = 0; i < tanks.length; ++i) {
            FluidStack fluidInTank = tanks[i].getFluid();
            if (isFluidValid(i, resource) && (fluidInTank.isEmpty() || resource.isFluidEqual(fluidInTank))) {
                return tanks[i].fill(resource, action);
            }
        }
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }

        for (int i = 0; i < 1; ++i) {
            if (resource.isFluidEqual(tanks[i].getFluid())) {
                return tanks[i].drain(resource, action);
            }
        }
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        for (int i = 0; i < 1; ++i) {
            if (tanks[i].getFluidAmount() > 0) {
                return tanks[i].drain(maxDrain, action);
            }
        }
        return FluidStack.EMPTY;
    }
}
