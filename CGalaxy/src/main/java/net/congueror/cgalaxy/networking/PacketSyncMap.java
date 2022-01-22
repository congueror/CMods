package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncMap {
    private final int windowId;
    private final boolean unlocked;
    private final ResourceLocation name;

    public PacketSyncMap(FriendlyByteBuf buf) {
        this.windowId = buf.readInt();
        this.unlocked = buf.readBoolean();
        this.name = buf.readResourceLocation();
    }

    public PacketSyncMap(int id, boolean unlocked, ResourceLocation name) {
        this.windowId = id;
        this.unlocked = unlocked;
        this.name = name;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(windowId);
        buf.writeBoolean(unlocked);
        buf.writeResourceLocation(name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
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
