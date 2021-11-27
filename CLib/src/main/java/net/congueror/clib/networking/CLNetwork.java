package net.congueror.clib.networking;

import net.congueror.clib.CLib;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class CLNetwork {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CLib.MODID, "main"), () -> "1.0", s -> true, s -> true);
        INSTANCE.messageBuilder(PacketUpdateFluidTanks.class, nextID())
                .encoder(PacketUpdateFluidTanks::toBytes)
                .decoder(PacketUpdateFluidTanks::new)
                .consumer(PacketUpdateFluidTanks::handle)
                .add();
        INSTANCE.messageBuilder(PacketUpdateInfo.class, nextID())
                .encoder(PacketUpdateInfo::toBytes)
                .decoder(PacketUpdateInfo::new)
                .consumer(PacketUpdateInfo::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
