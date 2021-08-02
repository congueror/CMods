package com.congueror.cgalaxy.network;

import com.congueror.cgalaxy.entities.RocketEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketLaunchSequence {

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                Entity entity = player.getRidingEntity();
                if (entity instanceof RocketEntity) {
                    if (((RocketEntity) entity).getFuel() < 500) {
                        player.sendStatusMessage(new TranslationTextComponent("text.cgalaxy.insufficient_fuel"), false);
                    } else {
                        if (entity.getPersistentData().getInt("Powered") < 1) {
                            player.sendStatusMessage(new TranslationTextComponent("text.cgalaxy.about_to_launch"), false);
                            player.sendStatusMessage(new TranslationTextComponent("text.cgalaxy.about_to_launch1"), false);
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
