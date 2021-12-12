package net.congueror.cgalaxy.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class ItemItemHandlerWrapper implements ICapabilityProvider {
    private final int slotCount;
    protected final ItemStackHandler itemHandler;
    private final LazyOptional<IItemHandler> handler;
    private final ItemStack stack;
    @Nullable
    private final CompoundTag nbt;
    @Nullable
    private final BiFunction<Integer, ItemStack, Boolean> isItemValid;

    public ItemItemHandlerWrapper(ItemStack stack, @Nullable CompoundTag nbt, int slotCount) {
        this.slotCount = slotCount;
        this.stack = stack;
        this.nbt = nbt;
        this.itemHandler = createHandler();
        this.handler = LazyOptional.of(() -> itemHandler);
        this.isItemValid = null;
    }

    public ItemItemHandlerWrapper(ItemStack stack, @Nullable CompoundTag nbt, int slotCount, @Nullable BiFunction<Integer, ItemStack, Boolean> isItemValid) {
        this.slotCount = slotCount;
        this.stack = stack;
        this.nbt = nbt;
        this.itemHandler = createHandler();
        this.handler = LazyOptional.of(() -> itemHandler);
        this.isItemValid = isItemValid;
    }

    private ItemStackHandler createHandler() {
        NonNullList<ItemStack> stacks = NonNullList.withSize(slotCount, ItemStack.EMPTY);
        ListTag tagList = stack.getOrCreateTag().getCompound("inventory").getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, ItemStack.of(itemTags));
            }
        }
        return new ItemStackHandler(stacks) {
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                stack.getOrCreateTag().put("inventory", this.serializeNBT());
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack1) {
                return isItemValid != null ? isItemValid.apply(slot, stack1) : true;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, handler);
    }
}
