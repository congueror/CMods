package net.congueror.clib.blocks.machine_base.machine;

import net.congueror.clib.blocks.machine_base.FluidBlockEntity;
import net.congueror.clib.items.UpgradeItem;
import net.congueror.clib.util.recipe.FluidRecipe;
import net.congueror.clib.util.recipe.FluidRecipeWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractFluidMachineBlockEntity extends AbstractRecipeItemBlockEntity<FluidRecipe<?>> implements FluidBlockEntity<AbstractFluidMachineBlockEntity> {
    protected FluidRecipeWrapper wrapper = new FluidRecipeWrapper(itemHandler, this);

    public FluidTank[] tanks = createFluidTanks();
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this);
    private Set<Direction> activeFluidFaces = Set.of(Direction.values());
    protected boolean sendOutFluid = true;

    public AbstractFluidMachineBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
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
    public @NotNull AbstractFluidMachineBlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return tanks;
    }

    @Override
    public Set<Direction> getActiveFluidFaces() {
        return activeFluidFaces;
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
                sendUpdate(getInactiveState());
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

        if (sendOutItems) {
            sendOutItem(outputSlots());
        }

        if (getMaxExtract() > 0 && sendOutEnergy) {
            sendOutEnergy();
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
        int[] slots = new int[]{getInvSize() - 1, getInvSize() - 2, getInvSize() - 3, getInvSize() - 4};
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
     * Empties the bucket at the given slots in the given tank. Usually used with {@link #executeExtra()}
     *
     * @param tank  The tank to be filled
     * @param slots The slots to be emptied
     */
    public void emptyFluidSlot(int tank, int... slots) {
        for (int i : slots) {
            ItemStack slot = itemHandler.getStackInSlot(i);
            if (!slot.isEmpty()) {
                slot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(iFluidHandlerItem -> {
                    int amount = tanks[tank].getCapacity() - tanks[tank].getFluidAmount();
                    FluidStack drain = iFluidHandlerItem.drain(amount, FluidAction.EXECUTE);
                    tanks[tank].fill(drain, FluidAction.EXECUTE);
                    itemHandler.setStackInSlot(i, iFluidHandlerItem.getContainer());
                });
            }
        }
    }

    /**
     * Fills the bucket at the given slots with the fluid in the given tank.
     *
     * @param tank  The tank to be emptied.
     * @param slots The slots to be filled
     */
    public void fillFluidSlot(int tank, int... slots) {
        for (int i : slots) {
            ItemStack slot1 = itemHandler.getStackInSlot(i);
            if (!slot1.isEmpty()) {
                slot1.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(iFluidHandlerItem -> {
                    if (!tanks[tank].isEmpty()) {
                        int fill = iFluidHandlerItem.fill(tanks[tank].getFluid(), FluidAction.EXECUTE);
                        tanks[tank].drain(fill, FluidAction.EXECUTE);
                        itemHandler.setStackInSlot(i, iFluidHandlerItem.getContainer());
                    }
                });
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (isFluidHandlerCapability(cap, side)) {
            return fluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidHandler.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        loadFluidTanks(nbt);
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        saveFluidTanks(pTag);
        super.saveAdditional(pTag);
    }
}
