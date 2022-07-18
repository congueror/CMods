package net.congueror.clib.blocks.machine_base;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public interface FluidBlockEntity<T extends BlockEntity> extends IFluidHandler {
    default FluidTank[] createFluidTanks() {
        return IntStream.range(0, getTanks()).mapToObj(value -> new FluidTank(getTankCapacity(value)).setValidator(fluidValidator(value))).toArray(FluidTank[]::new);
    }

    T getBlockEntity();

    FluidTank[] getFluidTanks();

    /**
     * The total amount of tanks present in the block entity.
     */
    int getTanks();

    int getTankCapacity(int tank);

    default Predicate<FluidStack> fluidValidator(int tank) {
        return fluidStack -> true;
    }

    default Set<Direction> getActiveFluidFaces() {
        return Set.of(Direction.values());
    }

    default <C> boolean isFluidHandlerCapability(Capability<C> cap, Direction side) {
        return cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && (side == null || getActiveFluidFaces().contains(side));
    }

    default void loadFluidTanks(CompoundTag nbt) {
        for (int i = 0; i < getFluidTanks().length; i++) {
            if (nbt.contains("tank" + i)) {
                getFluidTanks()[i].setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("tank" + i)));
            }
        }
    }

    default void saveFluidTanks(CompoundTag tag) {
        for (int i = 0; i < getFluidTanks().length; i++) {
            if (!getFluidTanks()[i].isEmpty()) {
                tag.put("tank" + i, getFluidTanks()[i].getFluid().writeToNBT(new CompoundTag()));
            }
        }
    }

    /**
     * Sends the fluid in the given tanks to other block entities that have the {@link IFluidHandler} capability around it.
     *
     * @param tank The tanks whose fluid will be extracted.
     */
    default void sendOutFluid(int[] tank, Direction... directions) {
        for (int i : tank) {
            FluidStack fluidStack = getFluidTanks()[i].getFluid();
            if (fluidStack.getAmount() > 0 && !fluidStack.isEmpty()) {
                for (Direction direction : directions) {
                    BlockEntity te = getBlockEntity().getLevel().getBlockEntity(getBlockEntity().getBlockPos().relative(direction));
                    if (te != null) {
                        boolean doContinue = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction).map(handler -> {
                                    if (handler.getTanks() >= 0) {
                                        int fill = handler.fill(fluidStack, FluidAction.EXECUTE);
                                        fluidStack.shrink(fill);
                                        getFluidTanks()[i].getFluid().shrink(fill);
                                        getBlockEntity().setChanged();
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

    @NotNull
    @Override
    default FluidStack getFluidInTank(int tank) {
        if (tank < 0 || tank >= getFluidTanks().length) {
            return FluidStack.EMPTY;
        }
        return getFluidTanks()[tank].getFluid();
    }

    @Override
    default boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        if (tank < 0 || tank >= getFluidTanks().length) {
            return false;
        }
        return getFluidTanks()[tank].isFluidValid(stack);
    }

    @Override
    default int fill(FluidStack resource, FluidAction action) {
        for (int i = 0; i < getFluidTanks().length; ++i) {
            FluidStack fluidInTank = getFluidTanks()[i].getFluid();
            if (isFluidValid(i, resource) && (fluidInTank.isEmpty() || resource.isFluidEqual(fluidInTank))) {
                return getFluidTanks()[i].fill(resource, action);
            }
        }
        return 0;
    }

    @Nonnull
    @Override
    default FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }

        for (int i = 0; i < 1; ++i) {
            if (resource.isFluidEqual(getFluidTanks()[i].getFluid())) {
                return getFluidTanks()[i].drain(resource, action);
            }
        }
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    default FluidStack drain(int maxDrain, FluidAction action) {
        for (int i = 0; i < 1; ++i) {
            if (getFluidTanks()[i].getFluidAmount() > 0) {
                return getFluidTanks()[i].drain(maxDrain, action);
            }
        }
        return FluidStack.EMPTY;
    }
}
