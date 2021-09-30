package net.congueror.cgalaxy.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemHandlerProvider implements ICapabilityProvider {
    protected final ItemStackHandler itemHandler;
    private final LazyOptional<IItemHandler> handler;
    private final ItemStack stack;
    @Nullable
    private CompoundTag nbt;

    public ItemHandlerProvider(ItemStack stack, @Nullable CompoundTag nbt, ItemStackHandler handler) {
        this.stack = stack;
        this.nbt = nbt;
        this.itemHandler = handler;

        this.handler = LazyOptional.of(() -> itemHandler);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return handler.cast();
        }
        return null;
    }
}
