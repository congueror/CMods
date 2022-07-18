package net.congueror.clib.util.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public interface IFluidItem {

    int getCapacity();

    default boolean isFluidValid(FluidStack stack) {
        return true;
    }

    default FluidStack getFluid(ItemStack stack) {
        CompoundTag tagCompound = stack.getTag();
        if (tagCompound == null || !tagCompound.contains("Fluid")) {
            return FluidStack.EMPTY;
        }
        return FluidStack.loadFluidStackFromNBT(tagCompound.getCompound("Fluid"));
    }

    default boolean setFluid(ItemStack stack, FluidStack fluid) {
        if (!isFluidValid(fluid))
            return false;

        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }

        CompoundTag fluidTag = new CompoundTag();
        fluid.writeToNBT(fluidTag);
        stack.getTag().put("Fluid", fluidTag);
        return true;
    }

    default void emptyFluid(ItemStack stack) {
        stack.removeTagKey("Fluid");
    }

    default int fill(ItemStack stack, FluidStack resource) {
        return fill(stack, resource, IFluidHandler.FluidAction.EXECUTE);
    }

    default int fill(ItemStack stack, FluidStack resource, IFluidHandler.FluidAction action) {
        if (stack.getCount() != 1 || resource.isEmpty() || !isFluidValid(resource)) return 0;

        FluidStack contained = getFluid(stack);
        if (contained.isEmpty()) {
            int fillAmount = Math.min(getCapacity(), resource.getAmount());
            if (action.execute()) {
                FluidStack filled = resource.copy();
                filled.setAmount(fillAmount);
                setFluid(stack, filled);
            }
            return fillAmount;
        } else {
            if (contained.isFluidEqual(resource)) {
                int fillAmount = Math.min(getCapacity() - contained.getAmount(), resource.getAmount());
                if (action.execute() && fillAmount > 0) {
                    contained.grow(fillAmount);
                    setFluid(stack, contained);
                }
                return fillAmount;
            }

            return 0;
        }
    }

    default FluidStack drain(ItemStack stack, int drain) {
        return drain(stack, drain, IFluidHandler.FluidAction.EXECUTE);
    }

    @Nonnull
    default FluidStack drain(ItemStack stack, FluidStack resource, IFluidHandler.FluidAction action) {
        if (stack.getCount() != 1 || resource.isEmpty() || !resource.isFluidEqual(getFluid(stack))) {
            return FluidStack.EMPTY;
        }
        return drain(stack, resource.getAmount(), action);
    }

    @Nonnull
    default FluidStack drain(ItemStack stack, int maxDrain, IFluidHandler.FluidAction action) {
        if (stack.getCount() != 1 || maxDrain <= 0) {
            return FluidStack.EMPTY;
        }

        FluidStack contained = getFluid(stack);
        if (contained.isEmpty()) {
            return FluidStack.EMPTY;
        }

        final int drainAmount = Math.min(contained.getAmount(), maxDrain);

        FluidStack drained = contained.copy();
        drained.setAmount(drainAmount);

        if (action.execute()) {
            contained.shrink(drainAmount);
            if (contained.isEmpty()) {
                emptyFluid(stack);
            } else {
                setFluid(stack, contained);
            }
        }

        return drained;
    }
}
