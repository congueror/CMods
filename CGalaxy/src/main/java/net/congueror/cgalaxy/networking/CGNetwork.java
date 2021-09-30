package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.CLib;
import net.congueror.clib.networking.PacketUpdateFluidTanks;
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
        INSTANCE.messageBuilder(PacketUnlockMap.class, nextID())
                .encoder(PacketUnlockMap::toBytes)
                .decoder(PacketUnlockMap::new)
                .consumer(PacketUnlockMap::handle)
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
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
