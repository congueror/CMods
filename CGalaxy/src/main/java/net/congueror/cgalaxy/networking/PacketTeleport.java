package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.item.AbstractRocketItem;
import net.congueror.clib.networking.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

public class PacketTeleport implements IPacket {
    private final ResourceLocation level;
    private final BlockPos pos;
    private final boolean isSpaceStation;

    public PacketTeleport(FriendlyByteBuf buffer) {
        this.level = buffer.readResourceLocation();
        this.pos = buffer.readBlockPos();
        this.isSpaceStation = buffer.readBoolean();
    }

    public PacketTeleport(ResourceLocation level, BlockPos pos, boolean isSpaceStation) {
        this.level = level;
        this.pos = pos;
        this.isSpaceStation = isSpaceStation;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(level);
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(isSpaceStation);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ResourceKey<Level> dim = ResourceKey.create(Registry.DIMENSION_REGISTRY, level);
                ServerLevel world = Objects.requireNonNull(player.level.getServer()).getLevel(dim);
                int x = isSpaceStation ? pos.getX() : player.getBlockX();
                int z = isSpaceStation ? pos.getZ() : player.getBlockZ();
                if (world != null && player.getVehicle() instanceof AbstractRocket) {
                    int fuel = ((AbstractRocket) player.getVehicle()).getFuel() - 500;
                    AbstractRocket rocket = ((AbstractRocketItem) ((AbstractRocket) player.getVehicle()).getItem()).newRocketEntity(world, fuel);
                    if (player.getVehicle() != null) {
                        player.getVehicle().remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
                    }
                    player.teleportTo(world, x, 600, z, 0, 0);
                    rocket.setPos(x + 0.5, 600, z + 0.5);
                    world.addFreshEntity(rocket);
                    player.startRiding(rocket, true);
                    rocket.setMode(3);
                } else if (world != null) {
                    player.teleportTo(world, x, 200, z, 0, 0);
                }
            }
        });
        return true;
    }
}
