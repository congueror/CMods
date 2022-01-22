package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerContainer;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerSettingsScreen;
import net.congueror.clib.networking.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class PacketUpdateRoomPressurizer implements IPacket {

    private final int id;
    private final Map<Direction, Integer> map;

    public PacketUpdateRoomPressurizer(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.map = buf.readMap(b -> Direction.values()[b.readInt()], FriendlyByteBuf::readInt);
    }

    public PacketUpdateRoomPressurizer(int windowId, Map<Direction, Integer> map) {
        this.id = windowId;
        this.map = map;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeMap(map, (b, direction) -> b.writeInt(direction.ordinal()), FriendlyByteBuf::writeInt);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            ctx.get().enqueueWork(() -> {
                Player player = Minecraft.getInstance().player;
                if (id != -1 && player != null) {
                    if (id == player.containerMenu.containerId) {
                        if (player.containerMenu instanceof RoomPressurizerContainer) {
                            ((RoomPressurizerContainer) player.containerMenu).update(map);
                        }
                    }
                }
            });
        }
        return true;
    }
}
