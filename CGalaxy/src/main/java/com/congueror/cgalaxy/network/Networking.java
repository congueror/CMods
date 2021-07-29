package com.congueror.cgalaxy.network;

import com.congueror.cgalaxy.CGalaxy;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CGalaxy.MODID, "cgalaxy"), () -> "1.0", s -> true, s -> true);

        INSTANCE.messageBuilder(PacketOpenGalaxyMap.class, nextID())
                .encoder((packetOpenGalaxyMap, packetBuffer) -> {})
                .decoder(packetBuffer -> new PacketOpenGalaxyMap())
                .consumer(PacketOpenGalaxyMap::handle)
                .add();
        INSTANCE.messageBuilder(PacketTeleport.class, nextID())
                .encoder(PacketTeleport::toBytes)
                .decoder(PacketTeleport::new)
                .consumer(PacketTeleport::handle)
                .add();
        INSTANCE.messageBuilder(PacketLaunchSequence.class, nextID())
                .encoder((packetLaunchSequence, packetBuffer) -> {})
                .decoder(packetBuffer -> new PacketLaunchSequence())
                .consumer(PacketLaunchSequence::handle)
                .add();
        INSTANCE.messageBuilder(PacketUpdateFluidTanks.class, nextID())
                .encoder(PacketUpdateFluidTanks::toBytes)
                .decoder(PacketUpdateFluidTanks::new)
                .consumer(PacketUpdateFluidTanks::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
