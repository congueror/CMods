package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncMap {
    private final int windowId;
    private final boolean unlocked;
    private final String name;

    public PacketSyncMap(FriendlyByteBuf buf) {
        this.windowId = buf.readInt();
        this.unlocked = buf.readBoolean();
        this.name = buf.readUtf();
    }

    public PacketSyncMap(int id, boolean unlocked, String name) {
        this.windowId = id;
        this.unlocked = unlocked;
        this.name = name;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(windowId);
        buf.writeBoolean(unlocked);
        buf.writeUtf(name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Player player = mc.player;
                if (windowId != -1 && player != null) {
                    if (player.containerMenu instanceof GalaxyMapContainer container) {
                        container.sync(unlocked, name);
                    }
                }
            });
        }
        return true;
    }
}
