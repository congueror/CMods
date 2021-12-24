package net.congueror.clib.networking;

import net.congueror.clib.api.machine.item.AbstractItemContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateInfo implements IPacket {
    private final int windowId;
    private final String info;

    public PacketUpdateInfo(FriendlyByteBuf buf) {
        this.windowId = buf.readInt();
        this.info = buf.readUtf();
    }

    public PacketUpdateInfo(int windowId, String info) {
        this.windowId = windowId;
        this.info = info;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(windowId);
        buf.writeUtf(info);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Player player = mc.player;
                if (windowId != -1 && player != null) {
                    if (windowId == player.containerMenu.containerId) {
                        if (player.containerMenu instanceof AbstractItemContainer) {
                            ((AbstractItemContainer<?>) player.containerMenu).updateInfo(info);
                        }
                    }
                }
            });
        }
        return true;
    }
}
