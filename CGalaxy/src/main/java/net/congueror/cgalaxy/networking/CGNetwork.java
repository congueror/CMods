package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class CGNetwork {
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CGalaxy.MODID, "cgalaxy"), () -> "1.0", s -> true, s -> true);
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
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
