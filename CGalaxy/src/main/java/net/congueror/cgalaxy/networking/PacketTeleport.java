package net.congueror.cgalaxy.networking;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class PacketTeleport {
    private final ResourceLocation level;

    public PacketTeleport(FriendlyByteBuf buffer) {
        this.level = buffer.readResourceLocation();
    }

    public PacketTeleport(ResourceLocation level) {
        this.level = level;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(level);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ResourceKey<Level> dim = ResourceKey.create(Registry.DIMENSION_REGISTRY, level);
                ServerLevel world = Objects.requireNonNull(player.level.getServer()).getLevel(dim);
                if (world != null) {
                    if (player.getVehicle() != null) {
                        player.getVehicle().remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
                    }
                    player.teleportTo(world, player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ(), 0, 0);
                }
            }
        });
        return true;
    }
}
