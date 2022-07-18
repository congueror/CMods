package net.congueror.clib.blocks.machine_base;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Set;

public interface ItemBlockEntity<T extends BlockEntity> {
    default ItemStackHandler createHandler() {
        return new ItemStackHandler(getInvSize()) {

            @Override
            protected void onContentsChanged(int slot) {
                onSlotChanged(slot);
                getBlockEntity().setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return canItemFit(slot, stack);
            }

            @Override
            public int getSlotLimit(int slot) {
                return ItemBlockEntity.this.getSlotLimit(slot);
            }

            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                return ItemBlockEntity.this.getStackLimit(slot, stack);
            }
        };
    }

    T getBlockEntity();

    ItemStackHandler getItemHandler();

    /**
     * The total amount of slots present in the block entity.
     */
    int getInvSize();

    /**
     * Check whether an item can fit in the given slot.
     *
     * @param slot  The slot of the item
     * @param stack The ItemStack in the slot.
     */
    boolean canItemFit(int slot, ItemStack stack);

    /**
     * Override for custom slot limits.
     *
     * @param slot The index of the slot.
     */
    default int getSlotLimit(int slot) {
        return 64;
    }

    default int getStackLimit(int slot, @NotNull ItemStack stack) {
        return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
    }

    /**
     * Override for custom on changed logic for item handler.
     */
    default void onSlotChanged(int slot) {

    }

    default Set<Direction> getActiveItemFaces() {
        return Set.of(Direction.values());
    }

    default <C> boolean isItemHandlerCapability(Capability<C> cap, Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (side == null || getActiveItemFaces().contains(side));
    }

    default void loadItemHandler(CompoundTag nbt) {
        getItemHandler().deserializeNBT(nbt.getCompound("inventory"));
    }

    default void saveItemHandler(CompoundTag tag) {
        tag.put("inventory", getItemHandler().serializeNBT());
    }

    /**
     * Outputs energy from the block in all active directions
     */
    default void sendOutItem(int... slots) {
        sendOutItem(slots, getActiveItemFaces().toArray(Direction[]::new));
    }

    /**
     * Outputs energy from the block in the given directions
     */
    default void sendOutItem(int[] slots, Direction... directions) {
        if (slots.length > 0) {
            ItemStack[] capacity = new ItemStack[slots.length];
            for (int i : slots)
                capacity[i] = getItemHandler().getStackInSlot(i);
            for (Direction direction : directions) {
                BlockEntity be = getBlockEntity().getLevel().getBlockEntity(getBlockEntity().getBlockPos().offset(new Vec3i(0, 0, 0).relative(direction)));
                if (be != null) {
                    boolean doContinue = be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).map(handler -> {
                                for (int i : slots) {
                                    for (int j = 0; j < handler.getSlots(); j++) {
                                        ItemStack stack = handler.insertItem(j, capacity[i], false);
                                        if (stack.isEmpty()) {
                                            getItemHandler().setStackInSlot(i, ItemStack.EMPTY);
                                        } else if (!stack.equals(capacity[i])) {
                                            capacity[i].setCount(stack.getCount());
                                            getItemHandler().setStackInSlot(i, capacity[i]);
                                        }
                                    }
                                }
                                return false;
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
