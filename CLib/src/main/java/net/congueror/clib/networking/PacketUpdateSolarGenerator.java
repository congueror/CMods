package net.congueror.clib.networking;

import net.congueror.clib.blocks.solar_generator.SolarGeneratorContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateSolarGenerator implements IPacket {
    private final int windowId;
    private final int generation;

    public PacketUpdateSolarGenerator(int windowId, int generation) {
        this.windowId = windowId;
        this.generation = generation;
    }

    public PacketUpdateSolarGenerator(FriendlyByteBuf buf) {
        this.windowId = buf.readInt();
        this.generation = buf.readInt();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(windowId);
        buf.writeInt(generation);
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Player player = mc.player;
                if (windowId != -1 && player != null) {
                    if (windowId == player.containerMenu.containerId) {
                        if (player.containerMenu instanceof SolarGeneratorContainer) {
                            ((SolarGeneratorContainer) player.containerMenu).updateData(generation);
                        }
                    }
                }
            });
        }
        return true;
    }
}
