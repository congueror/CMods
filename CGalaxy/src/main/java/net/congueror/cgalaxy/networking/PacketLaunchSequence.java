package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.clib.networking.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketLaunchSequence implements IPacket {
    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                Entity entity = player.getVehicle();
                if (entity instanceof AbstractRocket) {
                    if (((AbstractRocket) entity).getFuel() < 500) {
                        player.displayClientMessage(new TranslatableComponent("text.cgalaxy.insufficient_fuel"), false);
                    } else {
                        if (entity.getPersistentData().getInt(CGalaxy.ROCKET_POWERED) < 1) {
                            player.displayClientMessage(new TranslatableComponent("text.cgalaxy.about_to_launch"), false);
                            player.displayClientMessage(new TranslatableComponent("text.cgalaxy.about_to_launch1"), false);
                        }
                        if (((AbstractRocket) entity).getFuel() >= 500) {
                            entity.getPersistentData().putInt(CGalaxy.ROCKET_POWERED, entity.getPersistentData().getInt(CGalaxy.ROCKET_POWERED) + 1);
                            if (entity.getPersistentData().getInt(CGalaxy.ROCKET_POWERED) > 2) {
                                entity.getPersistentData().putInt(CGalaxy.ROCKET_POWERED, 0);
                            }
                        }
                    }
                }
            }
        });
        return true;
    }
}