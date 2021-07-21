package com.congueror.cgalaxy.network;

import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateFuelRefinery {

    private final int windowId;
    private final ResourceLocation fluid;
    private final int amount;
    private final int tankId;

    public PacketUpdateFuelRefinery(PacketBuffer buf) {
        this.windowId = buf.readInt();
        this.fluid = buf.readResourceLocation();
        this.amount = buf.readInt();
        this.tankId = buf.readInt();
    }

    public PacketUpdateFuelRefinery(int windowId, ResourceLocation fluid, int amount, int tankId) {
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
                if (windowId != -1) {
                    if (windowId == player.openContainer.windowId) {
                        if (player.openContainer instanceof FuelRefineryContainer) {
                            ((FuelRefineryContainer) player.openContainer).updateTanks(fluid, amount, tankId);
                        }
                    }
                }
            });
        }
        return true;
    }
}
