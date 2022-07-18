package net.congueror.cgalaxy.networking;

import net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreContainer;
import net.congueror.clib.networking.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public class PacketUpdateSSCore implements IPacket {
    int containerId;
    UUID owner;
    BlockPos launchPadPos;
    int visibility;
    Set<UUID> blacklist;

    public PacketUpdateSSCore(int containerId, UUID owner, BlockPos launchPadPos, int visibility, Set<UUID> blacklist) {
        this.containerId = containerId;
        this.owner = owner;
        this.launchPadPos = launchPadPos;
        this.visibility = visibility;
        this.blacklist = blacklist;
    }

    public PacketUpdateSSCore(FriendlyByteBuf buf) {
        this.containerId = buf.readInt();
        this.owner = buf.readUUID();
        this.launchPadPos = buf.readBlockPos();
        this.visibility = buf.readVarInt();
        this.blacklist = new HashSet<>();
        int setSize = buf.readInt();
        for (int i = 0; i < setSize; i++) {
            this.blacklist.add(buf.readUUID());
        }
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.containerId);
        buf.writeUUID(this.owner);
        buf.writeBlockPos(this.launchPadPos);
        buf.writeVarInt(this.visibility);
        buf.writeInt(this.blacklist.size());
        for (UUID id : blacklist) {
            buf.writeUUID(id);
        }
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (containerId != -1 && player != null) {
                if (containerId == player.containerMenu.containerId) {
                    if (player.containerMenu instanceof SpaceStationCoreContainer c) {
                        c.setCore(owner, launchPadPos, visibility, blacklist);
                    }
                }
            }
        });
        return true;
    }
}
