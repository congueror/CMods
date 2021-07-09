package com.congueror.cgalaxy.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketTeleport {

    private final ResourceLocation world;

    public PacketTeleport(PacketBuffer buffer) {
        this.world = buffer.readResourceLocation();
    }

    public PacketTeleport(ResourceLocation world) {
        this.world = world;
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(world);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                RegistryKey<World> dim = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, world);
                ServerWorld world = player.world.getServer().getWorld(dim);
                if (world != null) {
                    player.getRidingEntity().remove();
                    player.teleport(world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 0, 0);
                }
            }
        });
        return true;
    }
}
