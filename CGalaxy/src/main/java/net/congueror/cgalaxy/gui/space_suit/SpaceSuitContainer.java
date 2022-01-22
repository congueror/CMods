package net.congueror.cgalaxy.gui.space_suit;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.item.*;
import net.congueror.clib.blocks.abstract_machine.AbstractInventoryContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SpaceSuitContainer extends AbstractInventoryContainer {//TODO: Slot API
    public SpaceSuitContainer(int pContainerId, Inventory inv) {
        super(CGContainerInit.SPACE_SUIT.get(), pContainerId, inv);

        inv.player.getItemBySlot(EquipmentSlot.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 44, 14));
        });
        inv.player.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 44, 33));
            addSlot(new SlotItemHandler(iItemHandler, 1, 44, 52));

            addSlot(new SlotItemHandler(iItemHandler, 2, 153, 24));
            addSlot(new SlotItemHandler(iItemHandler, 3, 153, 43));
            addSlot(new SlotItemHandler(iItemHandler, 4, 153, 62));
        });
        inv.player.getItemBySlot(EquipmentSlot.LEGS).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 44, 71));
        });
        inv.player.getItemBySlot(EquipmentSlot.FEET).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 63, 71));
        });
        layoutPlayerInventorySlots(8, 96);
    }

    @Override
    public boolean stillValid(@Nonnull Player pPlayer) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < 8) {
                if (!this.moveItemStackTo(itemstack1, 8, 44, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof OxygenGearItem) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof OxygenTankItem) {
                if (!this.moveItemStackTo(itemstack1, 1, 3, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof HeatProtectionUnitItem) {
                if (!this.moveItemStackTo(itemstack1, 3, 4, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof ColdProtectionUnitItem) {
                if (!this.moveItemStackTo(itemstack1, 4, 5, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof RadiationProtectionUnitItem) {
                if (!this.moveItemStackTo(itemstack1, 5, 6, true)) {
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
}
