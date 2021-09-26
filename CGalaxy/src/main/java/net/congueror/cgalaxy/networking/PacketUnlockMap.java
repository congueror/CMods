package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.congueror.clib.api.machine.fluid.AbstractFluidContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUnlockMap {
    private final int windowId;
    private final boolean unlocked;

    public PacketUnlockMap(FriendlyByteBuf buf) {
        this.windowId = buf.readInt();
        this.unlocked = buf.readBoolean();
    }

    public PacketUnlockMap(int id, boolean unlocked) {
        this.windowId = id;
        this.unlocked = unlocked;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(windowId);
        buf.writeBoolean(unlocked);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Player player = mc.player;
                if (windowId != -1 && player != null) {
                    if (player.containerMenu instanceof GalaxyMapContainer container) {
                        container.setUnlocked(unlocked);
                    }
                }
            });
        }
        return true;
    }
}
