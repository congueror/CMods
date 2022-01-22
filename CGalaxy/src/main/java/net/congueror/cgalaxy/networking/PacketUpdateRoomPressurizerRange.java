package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerContainer;
import net.congueror.clib.networking.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateRoomPressurizerRange implements IPacket {
    int containerId;
    boolean isBack;
    int dirOrdinal;

    public PacketUpdateRoomPressurizerRange(FriendlyByteBuf buf) {
        this.containerId = buf.readInt();
        this.isBack = buf.readBoolean();
        this.dirOrdinal = buf.readInt();
    }

    public PacketUpdateRoomPressurizerRange(int containerId, boolean isBack, int dirOrdinal) {
        this.containerId = containerId;
        this.isBack = isBack;
        this.dirOrdinal = dirOrdinal;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(containerId);
        buf.writeBoolean(isBack);
        buf.writeInt(dirOrdinal);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (containerId != -1 && player != null) {
                if (containerId == player.containerMenu.containerId) {
                    if (player.containerMenu instanceof RoomPressurizerContainer) {
                        ((RoomPressurizerContainer) player.containerMenu).update(isBack, dirOrdinal);
                    }
                }
            }
        });
        return true;
    }
}
