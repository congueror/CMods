package net.congueror.cgalaxy.util.saved_data;

import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.util.json_managers.DimensionManager;
import net.congueror.clib.util.ListMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldSavedData extends SavedData {
    public static final ListMap<ResourceKey<Level>, SpaceStationCoreObject> CORE_LOCATIONS = new ListMap<>();
    public static final Map<ResourceKey<Level>, List<BlockPos>> AFFECTED_BLOCKS = new HashMap<>();
    private static final String NBT = "CGalaxyAffectedBlocks";
    private static final String NBT1 = "CGalaxyStationLocations";
    Level level;

    public WorldSavedData(Level level) {
        this.level = level;
    }

    public WorldSavedData(Level level, CompoundTag compoundTag) {
        this.level = level;

        List<BlockPos> blockPos = AFFECTED_BLOCKS.get(level.dimension());
        if (blockPos == null) {
            blockPos = new ArrayList<>();
        }
        ListTag tag = compoundTag.getList(NBT, Tag.TAG_COMPOUND);
        for (Tag value : tag) {
            var pair = BlockPos.CODEC.decode(NbtOps.INSTANCE, value).result().orElse(null);
            if (pair != null) {
                if (blockPos.contains(pair.getFirst())) {
                    break;
                }
                blockPos.add(pair.getFirst());
            }
        }
        AFFECTED_BLOCKS.put(level.dimension(), blockPos);

        //core loading
        ListTag tag1 = compoundTag.getList(NBT1, Tag.TAG_COMPOUND);
        for (Tag value : tag1) {
            CORE_LOCATIONS.addEntry(level.dimension(), new SpaceStationCoreObject((CompoundTag) value));
        }
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {

        ListTag list = new ListTag();
        List<BlockPos> positions = AFFECTED_BLOCKS.get(level.dimension());
        if (positions != null) {
            for (BlockPos position : positions) {
                list.add(BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, position).result().get());
            }
        }
        pCompoundTag.put(NBT, list);

        ListTag list1 = new ListTag();
        List<SpaceStationCoreObject> objects = CORE_LOCATIONS.get(level.dimension());
        if (objects != null) {
            for (SpaceStationCoreObject o : objects) {
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

    public static void update(ServerLevel level) {
        level.getDataStorage().computeIfAbsent(compoundTag ->
                new WorldSavedData(level, compoundTag), () ->
                new WorldSavedData(level), "cgalaxySavedData");
    }

    public static void updateAll(ServerLevel level) {
        for (CGDimensionBuilder.DimensionObject object : DimensionManager.OBJECTS) {
            ServerLevel level1 = level.getServer().getLevel(object.getDim());
            level1.getDataStorage().computeIfAbsent(compoundTag ->
                    new WorldSavedData(level1, compoundTag), () ->
                    new WorldSavedData(level1), "cgalaxySavedData");
        }
    }
}