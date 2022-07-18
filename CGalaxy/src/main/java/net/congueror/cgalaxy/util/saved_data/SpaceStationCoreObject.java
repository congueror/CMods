package net.congueror.cgalaxy.util.saved_data;

import net.congueror.cgalaxy.util.registry.SpaceStationBlueprint;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class SpaceStationCoreObject {

    UUID owner;
    BlockPos position;
    BlockPos launchPadPos;
    /**
     * 1 = private, 2 = public, 3 = blacklist, 4 = whitelist
     */
    int visibility;
    Set<UUID> list;

    public SpaceStationCoreObject(UUID owner, BlockPos position, int visibility, Set<UUID> list) {
        this(owner, position, position.above(3), visibility, list);
    }

    public SpaceStationCoreObject(UUID owner, BlockPos position, BlockPos launchPadPos, int visibility, Set<UUID> list) {
        this.owner = owner;
        this.position = position;
        this.launchPadPos = launchPadPos;
        this.visibility = visibility;
        this.list = list;
    }

    public UUID getOwner() {
        return owner;
    }

    public BlockPos getPosition() {
        return position;
    }

    public BlockPos getLaunchPadPos() {
        return launchPadPos;
    }

    public int getVisibility() {
        return visibility;
    }

    public Set<UUID> getBlackOrWhiteList() {
        return list;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setPosition(BlockPos position) {
        this.position = position;
    }

    public void setLaunchPadPos(BlockPos launchPadPos) {
        this.launchPadPos = launchPadPos;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public void setList(Set<UUID> list) {
        this.list = list;
    }

    public boolean canPlayerAccess(UUID player) {
        switch (visibility) {
            case 1 -> {
                return this.owner.equals(player);
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
        this.launchPadPos = BlockPos.CODEC.decode(NbtOps.INSTANCE, tag.get("launchPadPos")).result().get().getFirst();
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
        tag.put("launchPadPos", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, position).result().get());
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

    /**
     * Calculates and creates a new space station object, as well as generates the structure.
     *
     * @param owner
     * @param playerPos
     * @param level
     * @return
     */
    public static SpaceStationCoreObject generateObject(UUID owner, BlockPos playerPos, ServerLevel level, SpaceStationBlueprint blueprint, List<String> optionals) {
        int x = Math.round(playerPos.getX() / 500f) * 500;
        int y = 104;
        int z = Math.round(playerPos.getZ() / 500f) * 500;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);

        Random rand = new Random();
        WorldSavedData.update(level);
        while (WorldSavedData.CORE_LOCATIONS.stream(level.dimension()).anyMatch(o -> o.getPosition().equals(pos))) {
            x += rand.nextInt(-1, 2) * 500;
            z += rand.nextInt(-1, 2) * 500;
            pos.setX(x);
            pos.setZ(z);
        }

        BlockPos pos1 = new BlockPos(pos);

        SpaceStationCoreObject obj = new SpaceStationCoreObject(owner, pos1, new BlockPos(pos1).below(3).east(9), 1, new HashSet<>());
        WorldSavedData.CORE_LOCATIONS.addEntry(level.dimension(), obj);
        WorldSavedData.makeDirty(level);

        StructureManager manager = level.getStructureManager();
        StructureTemplate temp = manager.get(blueprint.getNbtLoc()).orElse(null);
        if (temp != null) {
            StructurePlaceSettings settings = new StructurePlaceSettings().setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(true);
            BlockPos pos2 = new BlockPos(pos1).north(4).west(4).below(4);
            int i = -1;
            var list = temp.palettes.get(0).blocks();
            for (StructureTemplate.StructureBlockInfo info : list) {
                i++;
                if (info.state.is(CGBlockInit.BLOCK_HOLDER.get())) {
                    Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(optionals.get(info.nbt.getInt("Index"))));
                    if (block != null) {
                        info = new StructureTemplate.StructureBlockInfo(info.pos, block.defaultBlockState(), new CompoundTag());
                        list.set(i, info);
                    }
                }
            }
            temp.palettes.set(0, new StructureTemplate.Palette(list));
            temp.placeInWorld(level, pos2, pos2, settings, new Random(Util.getMillis()), 2);
        }

        return obj;
    }
}
