package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.gui.galaxy_map.GalacticObjectBuilder;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.congueror.clib.networking.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PacketChangeMap implements IPacket {
    private final ResourceLocation name;

    public PacketChangeMap(ResourceLocation name) {
        this.name = name;
    }

    public PacketChangeMap(FriendlyByteBuf buf) {
        this.name = buf.readResourceLocation();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(this.name);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                player.closeContainer();
                NetworkHooks.openGui(player, new MenuProvider() {
                    @Nonnull
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("gui.cgalaxy.galaxy_map");
                    }

                    @Nonnull
                    @Override
                    public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
                        return new GalaxyMapContainer(pContainerId, player, true, GalacticObjectBuilder.getObjectFromId(name));
                    }
                });
            }
        });
        return true;
    }
}
