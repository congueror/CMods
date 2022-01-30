package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlockEntity;
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

public class RoomPressurizerSavedData extends SavedData {
    private static final String NBT = "CGalaxyAffectedBlocks";
    Level level;

    public RoomPressurizerSavedData(Level level) {
        this.level = level;
    }

    public RoomPressurizerSavedData(Level level, CompoundTag compoundTag) {
        this.level = level;

        List<BlockPos> blockPos = RoomPressurizerBlockEntity.AFFECTED_BLOCKS.get(level.dimension());
        if (blockPos == null) {
            blockPos = new ArrayList<>();
        }
        ListTag tag = compoundTag.getList(NBT, Tag.TAG_LIST);
        for (var value : tag) {
            var pair = BlockPos.CODEC.decode(NbtOps.INSTANCE, value).result().orElse(null);
            if (pair != null) {
                if (blockPos.contains(pair.getFirst())) {
                    break;
                }
                blockPos.add(pair.getFirst());
            }
        }
        RoomPressurizerBlockEntity.AFFECTED_BLOCKS.put(level.dimension(), blockPos);
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag list = new ListTag();
        List<BlockPos> positions = RoomPressurizerBlockEntity.AFFECTED_BLOCKS.get(level);
        if (positions != null) {
            for (int i = 0; i < positions.size(); i++) {
                list.add(BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, positions.get(i)).result().get());
            }
        }
        pCompoundTag.put(NBT, list);
        return pCompoundTag;
    }

    public static void makeDirty(ServerLevel level) {
        level.getDataStorage().computeIfAbsent(compoundTag -> new RoomPressurizerSavedData(level, compoundTag), () -> new RoomPressurizerSavedData(level), "cgalaxy:room_pressurizer_" + level.dimension().location());
    }
}
