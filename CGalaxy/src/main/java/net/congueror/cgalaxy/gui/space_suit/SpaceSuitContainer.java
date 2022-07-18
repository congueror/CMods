package net.congueror.cgalaxy.gui.space_suit;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.items.space_suit.*;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.clib.blocks.machine_base.AbstractInventoryContainer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class SpaceSuitContainer extends AbstractInventoryContainer {//TODO: Slot API
    private ContainerLevelAccess levelAccess;
    private Inventory inventory;
    public ItemStackHandler handler = createHandler();

    private final int containerSlots;

    public SpaceSuitContainer(int pContainerId, Inventory inv) {
        this(pContainerId, inv, ContainerLevelAccess.NULL);
    }

    public SpaceSuitContainer(int pContainerId, Inventory inv, ContainerLevelAccess levelAccess) {
        super(CGContainerInit.SPACE_SUIT.get(), pContainerId, inv);
        this.inventory = inv;
        this.levelAccess = levelAccess;

        addSlot(new SlotItemHandler(handler, 0, 153, 71));
        inv.player.getItemBySlot(EquipmentSlot.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 44, 14));
        });
        inv.player.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 44, 33));
            addSlot(new SlotItemHandler(iItemHandler, 1, 44, 52));

            addSlot(new SlotItemHandler(iItemHandler, 2, 153, 14));
            addSlot(new SlotItemHandler(iItemHandler, 3, 153, 33));
            addSlot(new SlotItemHandler(iItemHandler, 4, 153, 52));
        });
        inv.player.getItemBySlot(EquipmentSlot.LEGS).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 44, 71));
        });
        inv.player.getItemBySlot(EquipmentSlot.FEET).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 63, 71));
        });
        this.containerSlots = this.slots.size();
        layoutPlayerInventorySlots(8, 96);
    }

    @Override
    public boolean stillValid(@Nonnull Player pPlayer) {
        return SpaceSuitUtils.isEquipped(pPlayer);
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < containerSlots) {
                if (!this.moveItemStackTo(itemstack1, containerSlots, this.slots.size() - 1, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof SpaceSuitUpgradeItem) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof OxygenGearItem) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof OxygenTankItem) {
                if (!this.moveItemStackTo(itemstack1, 2, 4, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof HeatProtectionUnitItem) {
                if (!this.moveItemStackTo(itemstack1, 4, 5, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof ColdProtectionUnitItem) {
                if (!this.moveItemStackTo(itemstack1, 5, 6, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof RadiationProtectionUnitItem) {
                if (!this.moveItemStackTo(itemstack1, 6, 7, true)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }
        return itemstack;
    }

    @Override
    public void slotsChanged(Container pInventory) {
        this.levelAccess.execute((level, blockPos) -> {
            if (!level.isClientSide) {
                if (handler.getStackInSlot(0).getCount() == 4) {
                    Player player = inventory.player;
                    if (SpaceSuitUtils.upgradeSpaceSuit(player, ((SpaceSuitUpgradeItem) handler.getStackInSlot(0).getItem()))) {
                        player.playSound(SoundEvents.ANVIL_USE, 1.1f, 0.6f);
                        handler.setStackInSlot(0, ItemStack.EMPTY);
                    }
                }
            }
        });
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.levelAccess.execute((level, blockPos) -> {
            this.clearContainer(pPlayer, new RecipeWrapper(handler));
        });
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler() {
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() instanceof SpaceSuitUpgradeItem;
            }

            @Override
            protected void onContentsChanged(int slot) {
                slotsChanged(null);
            }
        };
    }
}
