package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class CGNetwork {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CGalaxy.MODID, "main"), () -> "1.0", s -> true, s -> true);
        INSTANCE.messageBuilder(PacketUpdateSolarGenerator.class, nextID())
                .encoder(PacketUpdateSolarGenerator::toBytes)
                .decoder(PacketUpdateSolarGenerator::new)
                .consumer(PacketUpdateSolarGenerator::handle)
                .add();
        INSTANCE.messageBuilder(PacketSyncMap.class, nextID())
                .encoder(PacketSyncMap::toBytes)
                .decoder(PacketSyncMap::new)
                .consumer(PacketSyncMap::handle)
                .add();
        INSTANCE.messageBuilder(PacketTeleport.class, nextID())
                .encoder(PacketTeleport::toBytes)
                .decoder(PacketTeleport::new)
                .consumer(PacketTeleport::handle)
                .add();
        INSTANCE.messageBuilder(PacketLaunchSequence.class, nextID())
                .encoder((packetLaunchSequence, friendlyByteBuf) -> {})
                .decoder(friendlyByteBuf -> new PacketLaunchSequence())
                .consumer(PacketLaunchSequence::handle)
                .add();
        INSTANCE.messageBuilder(PacketOpenSpaceSuitMenu.class, nextID())
                .encoder(PacketOpenSpaceSuitMenu::toBytes)
                .decoder(PacketOpenSpaceSuitMenu::new)
                .consumer(PacketOpenSpaceSuitMenu::handle)
                .add();
        INSTANCE.messageBuilder(PacketChangeMap.class, nextID())
                .encoder(PacketChangeMap::toBytes)
                .decoder(PacketChangeMap::new)
                .consumer(PacketChangeMap::handle)
                .add();
        INSTANCE.messageBuilder(PacketUpdateRoomPressurizer.class, nextID())
                .encoder(PacketUpdateRoomPressurizer::toBytes)
                .decoder(PacketUpdateRoomPressurizer::new)
                .consumer(PacketUpdateRoomPressurizer::handle)
                .add();
        INSTANCE.messageBuilder(PacketUpdateRoomPressurizerRange.class, nextID())
                .encoder(PacketUpdateRoomPressurizerRange::toBytes)
                .decoder(PacketUpdateRoomPressurizerRange::new)
                .consumer(PacketUpdateRoomPressurizerRange::handle)
                .add();
        INSTANCE.messageBuilder(PacketUpdateSSCore.class, nextID())
                .encoder(PacketUpdateSSCore::toBytes)
                .decoder(PacketUpdateSSCore::new)
                .consumer(PacketUpdateSSCore::handle)
                .add();
        INSTANCE.messageBuilder(PacketCreateSpaceStation.class, nextID())
                .encoder(PacketCreateSpaceStation::toBytes)
                .decoder(PacketCreateSpaceStation::new)
                .consumer(PacketCreateSpaceStation::handle)
                .add();
        INSTANCE.messageBuilder(PacketUpdateSSCreator.class, nextID())
                .encoder(PacketUpdateSSCreator::toBytes)
                .decoder(PacketUpdateSSCreator::new)
                .consumer(PacketUpdateSSCreator::handle)
                .add();
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
