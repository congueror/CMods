package net.congueror.clib.util.capability;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemFluidWrapper implements IFluidHandlerItem, ICapabilityProvider {
    private final ItemStack stack;
    private final IFluidItem fluid;

    public ItemFluidWrapper(ItemStack stack, IFluidItem fluid) {
        this.stack = stack;
        this.fluid = fluid;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this));
    }

    @NotNull
    @Override
    public ItemStack getContainer() {
        return stack;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return fluid.getFluid(stack);
    }

    @Override
    public int getTankCapacity(int tank) {
        return fluid.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return fluid.isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return fluid.fill(stack, resource, action);
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return fluid.drain(stack, resource, action);
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return fluid.drain(stack, maxDrain, action);
    }
}
