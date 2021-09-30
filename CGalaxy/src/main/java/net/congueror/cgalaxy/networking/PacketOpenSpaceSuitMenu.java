package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.gui.space_suit.SpaceSuitContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PacketOpenSpaceSuitMenu {

    public PacketOpenSpaceSuitMenu(FriendlyByteBuf buffer) {
    }

    public PacketOpenSpaceSuitMenu() {
    }

    public void toBytes(FriendlyByteBuf buffer) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null && player.containerMenu instanceof InventoryMenu) {
                NetworkHooks.openGui(player, new MenuProvider() {
                    @Nonnull
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("gui.cgalaxy.space_suit");
                    }

                    @Nonnull
                    @Override
                    public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
                        return new SpaceSuitContainer(pContainerId, pInventory);
                    }
                });
            }
        });
        return true;
    }
}
