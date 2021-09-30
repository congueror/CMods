package net.congueror.cgalaxy.gui.space_suit;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.clib.api.machine.AbstractInventoryContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SpaceSuitContainer extends AbstractInventoryContainer {
    public SpaceSuitContainer(int pContainerId, Inventory inv) {
        super(CGContainerInit.SPACE_SUIT.get(), pContainerId, inv);

        inv.player.getItemBySlot(EquipmentSlot.HEAD).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 40, 40));
        });
        layoutPlayerInventorySlots(28, 84);
    }

    @Override
    public boolean stillValid(@Nonnull Player pPlayer) {
        return true;
    }
}
