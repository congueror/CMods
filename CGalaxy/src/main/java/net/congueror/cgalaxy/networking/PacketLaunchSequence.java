package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.entity.RocketEntity;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketLaunchSequence {
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                Entity entity = player.getVehicle();
                if (entity instanceof RocketEntity) {
                    if (((RocketEntity) entity).getFuel() < 500) {
                        player.displayClientMessage(new TranslatableComponent("text.cgalaxy.insufficient_fuel"), false);
                    } else {
                        if (entity.getPersistentData().getInt("Powered") < 1) {
                            player.displayClientMessage(new TranslatableComponent("text.cgalaxy.about_to_launch"), false);
                            player.displayClientMessage(new TranslatableComponent("text.cgalaxy.about_to_launch1"), false);
                        }
                        if (((RocketEntity) entity).getFuel() >= 500) {
                            entity.getPersistentData().putInt("Powered", entity.getPersistentData().getInt("Powered") + 1);
                        }
                    }
                }
            }
        });
        return true;
    }
}