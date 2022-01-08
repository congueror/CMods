package net.congueror.clib.api.machine.fluid;

import net.congueror.clib.api.machine.item.AbstractItemBlockEntity;
import net.congueror.clib.api.recipe.FluidRecipe;
import net.congueror.clib.api.recipe.FluidRecipeWrapper;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractFluidBlockEntity extends AbstractItemBlockEntity<FluidRecipe<?>> implements IFluidHandler {
    protected FluidRecipeWrapper wrapper = new FluidRecipeWrapper(itemHandler, this);

    /**
     * Initialization in subclass constructor like so:<p>
     * <pre>tanks = IntStream.range(0, [Amount of tanks]).mapToObj(k -> new FluidTank([Capacity])).toArray(FluidTank[]::new);</pre>
     */
    public FluidTank[] tanks;
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this);

    protected boolean sendOutFluid = true;

    public AbstractFluidBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    /**
     * The recipe that the tile entity uses. This can be acquired by using the recipe manager like so:
     * <pre>level.getRecipeManager().getRecipe([RECIPE], wrapper, world).orElse(null)</pre>
     */
    @Nullable
    public abstract FluidRecipe<?> getRecipe();

    /**
     * A map of all input slot and tank indexes. String should be either "tanks" or "slots" and the array of integers should be the indexes of all input slots/tanks.
     */
    public abstract HashMap<String, int[]> inputSlotsAndTanks();

    /**
     * A map of all output slot and tank indexes. String should be either "tanks" or "slots" and the array of integers should be the indexes of all output slots/tanks.
     */
    public abstract HashMap<String, int[]> outputSlotsAndTanks();

    @Override
    public int[] inputSlots() {
        return inputSlotsAndTanks().get("slots");
    }

    @Override
    public int[] outputSlots() {
        return outputSlotsAndTanks().get("slots");
    }

    @Override
    public void tick() {
        if (level != null && level.isClientSide) {
            return;
        }

        FluidStack tank = tanks[0].getFluid();
        FluidRecipe<?> recipe = getRecipe();
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
                if (energyStorage.getEnergyStored() <= getEnergyUsageFinal()) {
                    info = "key.clib.error_insufficient_energy";
                }
                if (progress > 0) {
                    progress--;
                }
            }
        } else {
            if (recipe == null) {
                for (Map.Entry<String, int[]> entry : inputSlotsAndTanks().entrySet()) {
                    boolean flag = false;
                    boolean flag1 = false;
                    for (int i : entry.getValue()) {
                        if (entry.getKey().equals("slots") && !wrapper.getItem(i).isEmpty()) {
                            flag = true;
                        }
                        if (entry.getKey().equals("tanks") && !tanks[i].isEmpty()) {
                            flag1 = true;
                        }
                    }
                    info = flag || flag1 ? "key.clib.error_invalid_recipe" : "key.clib.idle";
                }
            }
            if (tank.getAmount() < 100) {
                progress = 0;
            }
            sendUpdate(getInactiveState());
        }

        if (sendOutFluid)
            sendOutFluid(outputSlotsAndTanks().get("tanks"));
        executeExtra();
    }

    /**
     * The fluid results of the given recipe.
     *
     * @return A {@link Collection} of {@link FluidStack}s.
     */
    @Nullable
    public Collection<FluidStack> getFluidResults() {
        return Objects.requireNonNull(getRecipe()).getFluidResults();
    }

    /**
     * Checks whether the tile entity should run.
     */
    @Override
    public boolean shouldRun() {
        boolean flag = outputSlotsAndTanks().get("tanks") == null;
        boolean flag1 = outputSlotsAndTanks().get("slots") == null;
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
     *
     * @param results A collection of {@link FluidStack}s, usually used with {@link #getFluidResults()}.
     */
    protected void storeResultFluid(Collection<FluidStack> results) {
        results.forEach(fluidStack -> {
            for (int i : outputSlotsAndTanks().get("tanks")) {
                FluidStack output = tanks[i].getFluid();
                if (getFluidProcessSize() + output.getAmount() <= tanks[i].getCapacity()) {
                    tanks[i].fill(fluidStack, FluidAction.EXECUTE);
                }
            }
        });
    }

    /**
     * Calculates how many mB per operation will be consumed.
     *
     * @return The mB per operation.
     */
    public int getFluidProcessSize() {
        int[] slots = new int[]{invSize().length - 1, invSize().length - 2, invSize().length - 3, invSize().length - 4};
        int stack = 0;
        for (FluidStack i : Objects.requireNonNull(getFluidResults())) {
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

    /**
     * Sends the fluid in the given tanks to other block entities that have the {@link IFluidHandler} capability around it.
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
     * Empties the bucket at the given slots in the given tank. Usually used with {@link #executeExtra()}
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
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandler.cast();
        }
        return super.getCapability(cap, side);//TODO: Change which side can accept a capability
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidHandler.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        for (int i = 0; i < tanks.length; i++) {
            if (nbt.contains("tank" + i)) {
                tanks[i].setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("tank" + i)));
            }
        }

        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        for (int i = 0; i < tanks.length; i++) {
            if (!tanks[i].isEmpty()) {
                pTag.put("tank" + i, tanks[i].getFluid().writeToNBT(new CompoundTag()));
            }
        }

        super.saveAdditional(pTag);
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
