package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUnlockMap {
    private final boolean unlocked;

    public PacketUnlockMap(FriendlyByteBuf buf) {
        this.unlocked = buf.readBoolean();
    }

    public PacketUnlockMap(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(unlocked);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Player player = mc.player;
                assert player != null;
                if (player.containerMenu instanceof GalaxyMapContainer container) {
                    container.setUnlocked(unlocked);
                }
            });
        }
        return true;
    }
}
