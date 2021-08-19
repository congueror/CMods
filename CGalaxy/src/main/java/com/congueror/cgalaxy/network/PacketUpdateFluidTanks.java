package com.congueror.cgalaxy.network;

import com.congueror.clib.api.machines.fluid_machine.AbstractFluidContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateFluidTanks {
    private final int windowId;
    private final ResourceLocation fluid;
    private final int amount;
    private final int tankId;

    public PacketUpdateFluidTanks(PacketBuffer buf) {
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

    public void toBytes(PacketBuffer buffer) {
        buffer.writeInt(windowId);
        buffer.writeResourceLocation(fluid);
        buffer.writeInt(amount);
        buffer.writeInt(tankId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = mc.player;
                if (windowId != -1 && player != null) {
                    if (windowId == player.openContainer.windowId) {
                        if (player.openContainer instanceof AbstractFluidContainer) {
                            ((AbstractFluidContainer) player.openContainer).updateTanks(fluid, amount, tankId);
                        }
                    }
                }
            });
        }
        return true;
    }
}
