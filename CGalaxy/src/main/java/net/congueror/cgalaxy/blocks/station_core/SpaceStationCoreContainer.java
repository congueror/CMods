package net.congueror.cgalaxy.blocks.station_core;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.util.WorldSavedData;
import net.congueror.clib.blocks.abstract_machine.AbstractInventoryContainer;
import net.congueror.clib.util.ListMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.*;

public class SpaceStationCoreContainer extends AbstractInventoryContainer {
    public static final ListMap<ResourceKey<Level>, SpaceStationCoreObject> CORE_LOCATIONS = new ListMap<>();
    SpaceStationCoreObject currentObject;
    Level level;
    Player player;

    public SpaceStationCoreContainer(int id, BlockPos pos, Inventory playerInventory) {
        super(CGContainerInit.SPACE_STATION.get(), id, playerInventory);
        layoutPlayerInventorySlots(28, 84);
        this.player = playerInventory.player;
        this.level = playerInventory.player.level;
        var list = CORE_LOCATIONS.get(level.dimension());
        this.currentObject = list != null ?
                list.stream().filter(spaceStationCoreObject -> spaceStationCoreObject.position.equals(pos)).findFirst()
                        .orElse(new SpaceStationCoreObject(playerInventory.player.getUUID(), pos, 1, new HashSet<>()))
                : new SpaceStationCoreObject(playerInventory.player.getUUID(), pos, 1, new HashSet<>());
    }

    public void setCore(UUID owner, int visibility, Set<UUID> list) {
        currentObject.owner = owner;
        currentObject.visibility = visibility;
        currentObject.list = list;

        CORE_LOCATIONS.stream(level.dimension()).filter(o -> o.owner.equals(owner)).findFirst()
                .ifPresent(tempOwner -> CORE_LOCATIONS.removeEntry(level.dimension(), tempOwner));
        CORE_LOCATIONS.addEntry(level.dimension(), currentObject);

        if (level instanceof ServerLevel)
            WorldSavedData.makeDirty((ServerLevel) level);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public static class SpaceStationCoreObject {
        UUID owner;
        BlockPos position;
        /**
         * 1 = private, 2 = public, 3 = blacklist, 4 = whitelist
         */
        int visibility;
        Set<UUID> list;

        public SpaceStationCoreObject(UUID owner, BlockPos position, int visibility, Set<UUID> list) {
            this.owner = owner;
            this.position = position;
            this.visibility = visibility;
            this.list = list;
        }

        public UUID getOwner() {
            return owner;
        }

        public BlockPos getPosition() {
            return position;
        }

        public int getVisibility() {
            return visibility;
        }

        public Set<UUID> getBlackOrWhiteList() {
            return list;
        }

        public boolean canPlayerAccess(UUID player) {
            switch (visibility) {
                case 1 -> {
                    return false;
                }
                case 2 -> {
                    return true;
                }
                case 3 -> {
                    return !list.contains(player);
                }
                case 4 -> {
                    return list.contains(player);
                }
                default -> throw new IllegalArgumentException("Illegal visibility value found, fix the space station core.");
            }
        }

        public SpaceStationCoreObject(CompoundTag tag) {
            this.owner = tag.getUUID("owner");
            this.position = BlockPos.CODEC.decode(NbtOps.INSTANCE, tag.get("position")).result().get().getFirst();
            this.visibility = tag.getInt("visibility");
            Set<UUID> list = new HashSet<>();
            CompoundTag tag1 = tag.getCompound("list");
            for (int i = 0; i < tag1.size(); i++) {
                list.add(tag1.getUUID(String.valueOf(i)));
            }
            this.list = list;
        }

        public CompoundTag serialize() {
            CompoundTag tag = new CompoundTag();
            tag.putUUID("owner", owner);
            tag.put("position", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, position).result().get());
            tag.putInt("visibility", visibility);

            CompoundTag tag1 = new CompoundTag();
            int i = 0;
            for (UUID id : list) {
                tag1.putUUID(String.valueOf(i), id);
                i++;
            }
            tag.put("list", tag1);

            return tag;
        }
    }
}
