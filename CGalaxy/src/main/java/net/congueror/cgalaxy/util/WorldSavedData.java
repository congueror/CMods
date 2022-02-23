package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlockEntity;
import net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;

public class WorldSavedData extends SavedData {
    private static final String NBT = "CGalaxyAffectedBlocks";
    private static final String NBT1 = "CGalaxyStationLocations";
    Level level;

    public WorldSavedData(Level level) {
        this.level = level;
    }

    public WorldSavedData(Level level, CompoundTag compoundTag) {
        this.level = level;

        List<BlockPos> blockPos = RoomPressurizerBlockEntity.AFFECTED_BLOCKS.get(level.dimension());
        if (blockPos == null) {
            blockPos = new ArrayList<>();
        }
        ListTag tag = compoundTag.getList(NBT, Tag.TAG_LIST);
        for (Tag value : tag) {
            var pair = BlockPos.CODEC.decode(NbtOps.INSTANCE, value).result().orElse(null);
            if (pair != null) {
                if (blockPos.contains(pair.getFirst())) {
                    break;
                }
                blockPos.add(pair.getFirst());
            }
        }
        RoomPressurizerBlockEntity.AFFECTED_BLOCKS.put(level.dimension(), blockPos);

        //core loading
        ListTag tag1 = compoundTag.getList(NBT1, Tag.TAG_LIST);
        for (Tag value : tag1) {
            SpaceStationCoreContainer.CORE_LOCATIONS.addEntry(level.dimension(), new SpaceStationCoreContainer.SpaceStationCoreObject((CompoundTag) value));
        }
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {

        ListTag list = new ListTag();
        List<BlockPos> positions = RoomPressurizerBlockEntity.AFFECTED_BLOCKS.get(level.dimension());
        if (positions != null) {
            for (BlockPos position : positions) {
                list.add(BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, position).result().get());
            }
        }
        pCompoundTag.put(NBT, list);

        ListTag list1 = new ListTag();
        List<SpaceStationCoreContainer.SpaceStationCoreObject> objects = SpaceStationCoreContainer.CORE_LOCATIONS.get(level.dimension());
        if (objects != null) {
            for (SpaceStationCoreContainer.SpaceStationCoreObject o : objects) {
                list1.add(o.serialize());
            }
        }
        pCompoundTag.put(NBT1, list1);

        return pCompoundTag;
    }

    public static void makeDirty(ServerLevel level) {
        WorldSavedData data = level.getDataStorage().computeIfAbsent(compoundTag ->
                new WorldSavedData(level, compoundTag), () ->
                new WorldSavedData(level), "cgalaxySavedData");
        data.setDirty(true);
    }
}