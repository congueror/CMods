package net.congueror.clib.blocks.abstract_machine.item;

import net.congueror.clib.api.capability.ModEnergyStorage;
import net.congueror.clib.blocks.abstract_machine.tickable.AbstractTickableBlockEntity;
import net.congueror.clib.api.recipe.ItemRecipe;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractItemBlockEntity<R extends ItemRecipe<?>> extends AbstractTickableBlockEntity implements MenuProvider {
    protected ItemStackHandler itemHandler = createHandler();
    protected final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected RecipeWrapper wrapper = new RecipeWrapper(itemHandler);

    protected ModEnergyStorage energyStorage;
    protected LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    protected float progress;
    protected int processTime;
    public String info = "";
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

    public AbstractItemBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        this.energyStorage = createEnergy();
    }

    private ModEnergyStorage createEnergy() {
        return new ModEnergyStorage(getEnergyCapacity(), 1000, getMaxExtract());
    }

    /**
     * The recipe that the block entity uses. This can be acquired by using the recipe manager like so:
     * <pre>level.getRecipeManager().getRecipe([RECIPE], wrapper, world).orElse(null)</pre>
     */
    @Nullable
    public abstract R getRecipe();

    /**
     * An array of all input slot indexes.
     */
    public abstract int[] inputSlots();

    /**
     * An array of all output slot indexes.
     */
    public abstract int[] outputSlots();

    /**
     * An array of numbers that represent slots. Must start at 0. Must include 4 additional slots at the end for upgrade slots.
     */
    public abstract int[] invSize();

    /**
     * Check whether an item can fit in the given slot.
     *
     * @param slot  The slot of the item
     * @param stack The ItemStack in the slot.
     */
    public abstract boolean canItemFit(int slot, ItemStack stack);

    /**
     * The amount of FE consumed per tick.
     */
    protected abstract int getEnergyUsage();

    /**
     * The capacity of the energy buffer.
     */
    public abstract int getEnergyCapacity();

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
     * Gets executed regardless of the state of the machine
     */
    public abstract void executeExtra();

    /**
     * The range of the machine. Unless overriding, use {@link #getRangeFinal()}.
     *
     * @return Initial range of machine in blocks.
     */
    protected int getRange() {
        return -1;
    }

    /**
     * The maximum amount of energy that can be extracted from the machine.
     *
     * @return Initial extract of machine.
     */
    public int getMaxExtract() {
        return 0;
    }

    @Override
    public void tick() {
        if (level != null && level.isClientSide) {
            return;
        }

        R recipe = getRecipe();
        if (recipe != null && shouldRun()) {
            if (energyStorage.getEnergyStored() >= getEnergyUsageFinal() && requisites()) {
                processTime = getProcessTime();
                progress += getProgressSpeedFinal();
                energyStorage.consumeEnergy(getEnergyUsageFinal());
                info = "key.clib.working";
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
                if (energyStorage.getEnergyStored() < getEnergyUsageFinal()) {
                    info = "key.clib.error_insufficient_energy";
                }
                if (progress > 0) {
                    progress--;
                }
            }
        } else {
            if (recipe == null) {
                for (int i : inputSlots()) {
                    info = !wrapper.getItem(i).isEmpty() ? "key.clib.error_invalid_recipe" : "key.clib.idle";
                }
            }
            for (int i : inputSlots()) {
                if (wrapper.getItem(i) == null) {
                    progress = 0;
                }
            }
            sendUpdate(getInactiveState());
        }

        //TODO: Send out items
        if (getMaxExtract() > 0) {
            sendOutEnergy();
        }
        executeExtra();
    }

    /**
     * The item results of this machine.
     *
     * @return A {@link Collection} of {@link ItemStack}s.
     */
    @Nullable
    public Collection<ItemStack> getItemResults() {
        return Objects.requireNonNull(getRecipe()).getItemResults();
    }

    /**
     * Checks whether the block entity should run.
     */
    public boolean shouldRun() {
        return true;
    }

    /**
     * Puts the resulting item stack in the output slots.
     *
     * @param results A collection of {@link ItemStack}s, usually used with {@link #getItemResults()}.
     */
    protected void storeResultItem(Collection<ItemStack> results) {
        results.forEach(itemStack -> {
            for (int i : outputSlots()) {
                ItemStack output = wrapper.getItem(i);
                if (canItemsStack(itemStack, output)) {
                    if (output.isEmpty()) {
                        wrapper.setItem(i, itemStack);
                    } else {
                        output.setCount(output.getCount() + itemStack.getCount());
                    }
                }
            }
        });
    }

    /**
     * The amount of ticks it takes for the machine to complete a process
     */
    public int getProcessTime() {
        return Objects.requireNonNull(getRecipe()).getProcessTime();
    }

    /**
     * Calculates the range that the machine will function in.
     *
     * @return The range in blocks.
     */
    public int getRangeFinal() {
        int[] slots = new int[]{invSize().length - 1, invSize().length - 2, invSize().length - 3, invSize().length - 4};
        int range = getRange();
        for (int i = 0; i < slots.length; i++) {
            if (itemHandler.getStackInSlot(i).getItem() instanceof UpgradeItem) {
                if (((UpgradeItem) itemHandler.getStackInSlot(i).getItem()).getType().equals(UpgradeItem.UpgradeType.RANGE)) {
                    range += getRange() / 2;
                }
            }
        }
        return range;
    }

    /**
     * Calculates how much the {@link #progress} will be increased each tick.
     *
     * @return the progress per tick.
     */
    public int getProgressSpeedFinal() {
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

    public int getEnergyUsageFinal() {//TODO
        int usage = getEnergyUsage();
        return usage;
    }

    /**
     * Outputs energy from the block
     */
    protected void sendOutEnergy() {
        AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                assert level != null;
                BlockEntity be = level.getBlockEntity(getBlockPos().offset(new Vec3i(0, 0, 0).relative(direction)));
                if (be != null) {
                    boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                                if (handler.canReceive()) {
                                    int received = handler.receiveEnergy(Math.min(capacity.get(), getMaxExtract()), false);
                                    capacity.addAndGet(-received);
                                    energyStorage.consumeEnergy(received);
                                    setChanged();
                                    return capacity.get() > 0;
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

    public void fillEnergySlot(int... slots) {
        for (int i : slots) {
            ItemStack slot = wrapper.getItem(i);
            slot.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> {
                if (iEnergyStorage.getEnergyStored() + 4 < iEnergyStorage.getMaxEnergyStored() && energyStorage.getEnergyStored() > 4) {
                    iEnergyStorage.receiveEnergy(4, false);
                    energyStorage.consumeEnergy(4);
                } else if (iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored() && energyStorage.getEnergyStored() > 0) {
                    iEnergyStorage.receiveEnergy(1, false);
                    energyStorage.consumeEnergy(1);
                }
            });
        }
    }

    /**
     * Override for custom slot limits.
     *
     * @param slot The index of the slot.
     */
    public int getSlotLimits(int slot) {
        if (slot >= invSize().length - 4) {
            return 1;
        } else {
            return 64;
        }
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

            @Override
            public int getSlotLimit(int slot) {
                return getSlotLimits(slot);
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
     * Determines if the given stacks can stack.
     */
    public static boolean canItemsStack(ItemStack a, ItemStack b) {
        if (a.isEmpty() || b.isEmpty()) return true;
        return ItemHandlerHelper.canItemStacksStack(a, b) && a.getCount() + b.getCount() <= a.getMaxStackSize();
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
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);//TODO: Change which side can accept a capability
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        energy.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("Progress");
        this.processTime = nbt.getInt("ProcessTime");
        energyStorage.deserializeNBT(nbt.getCompound("energy"));

        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("Progress", (int) this.progress);
        pTag.putInt("ProcessTime", this.processTime);
        pTag.put("energy", energyStorage.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(this.getBlockState().getBlock().getDescriptionId());
    }
}
