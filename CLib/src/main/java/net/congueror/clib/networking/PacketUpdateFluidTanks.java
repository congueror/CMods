package net.congueror.clib.networking;

import net.congueror.clib.api.objects.machine_objects.fluid.AbstractFluidContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateFluidTanks {
    private final int windowId;
    private final ResourceLocation fluid;
    private final int amount;
    private final int tankId;

    public PacketUpdateFluidTanks(FriendlyByteBuf buf) {
        this.windowId = buf.readInt();
        this.fluid = buf.readResourceLocation();
        this.amount = buf.readInt();
        this.tankId = buf.readInt();
    }

    public PacketUpdateFluidTanks(int windowId, ResourceLocation fluid, int amount, int tankId) {
        this.windowId = windowId;
        this.fluid = fluid;
        this.amount = amount;
        this.tankId = tankId;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(windowId);
        buffer.writeResourceLocation(fluid);
        buffer.writeInt(amount);
        buffer.writeInt(tankId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Player player = mc.player;
                if (windowId != -1 && player != null) {
                    if (windowId == player.containerMenu.containerId) {
                        if (player.containerMenu instanceof AbstractFluidContainer) {
                            ((AbstractFluidContainer) player.containerMenu).updateTanks(fluid, amount, tankId);
                        }
                    }
                }
            });
        }
        return true;
    }
}
