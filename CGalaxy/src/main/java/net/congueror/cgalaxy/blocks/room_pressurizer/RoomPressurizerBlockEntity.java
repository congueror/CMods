package net.congueror.cgalaxy.blocks.room_pressurizer;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.cgalaxy.util.events.AddSealedBlocksEvent;
import net.congueror.cgalaxy.util.saved_data.WorldSavedData;
import net.congueror.clib.util.recipe.FluidRecipe;
import net.congueror.clib.blocks.machine_base.machine.AbstractFluidMachineBlockEntity;
import net.congueror.clib.items.UpgradeItem;
import net.congueror.clib.util.TagHelper;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.IntStream;

public class RoomPressurizerBlockEntity extends AbstractFluidMachineBlockEntity {
    private List<LivingEntity> entities;
    Map<Direction, Integer> range = Util.make(() -> {
        Map<Direction, Integer> map = new HashMap<>();
        for (Direction i : Direction.values()) {
            if (i.equals(Direction.DOWN)) {
                map.put(i, 1);
                continue;
            }
            map.put(i, getRange());
        }
        return map;
    });

    private AABB aabb;
    public static final Map<ResourceKey<Level>, List<AABB>> AABBS = new HashMap<>();

    public RoomPressurizerBlockEntity(BlockPos pos, BlockState state) {
        super(CGBlockEntityInit.ROOM_PRESSURIZER.get(), pos, state);
        this.tanks = IntStream.range(0, 2).mapToObj(value -> new FluidTank(15000)).toArray(FluidTank[]::new);
        sendOutFluid = false;
    }

    @Override
    public int getInvSize() {
        return 8;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot <= 4) {
            return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
        } else {
            return stack.getItem() instanceof UpgradeItem;
        }
    }

    @Override
    public int getTanks() {
        return 2;
    }

    @Override
    public int getTankCapacity(int tank) {
        return 10000;
    }

    @Nullable
    @Override
    public FluidRecipe<?> getRecipe() {
        assert level != null;
        return level.getRecipeManager().getRecipeFor(CGRecipeSerializerInit.ROOM_PRESSURIZING_TYPE.get(), wrapper, level).orElse(null);
    }

    @Override
    public HashMap<String, int[]> inputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{0, 1});
        map.put("slots", new int[]{0, 2});
        return map;
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{});
        map.put("slots", new int[]{1, 3});
        return map;
    }

    @Override
    public int getEnergyUsage() {
        return 160;
    }

    @Override
    public int getEnergyCapacity() {
        return 40000;
    }

    @Override
    public boolean requisites() {
        BlockPos posA = getBlockPos().below(range.get(Direction.DOWN)).north(range.get(Direction.NORTH)).west(range.get(Direction.WEST));
        BlockPos posB = getBlockPos().above(range.get(Direction.UP)).south(range.get(Direction.SOUTH)).east(range.get(Direction.EAST));
        assert level != null;
        aabb = new AABB(posA, posB);

        entities = level.getEntities(EntityTypeTest.forClass(LivingEntity.class), aabb, Objects::nonNull);
        if (wrapper.getFluidInTank(0).getAmount() < 10 * entities.size() * getPercentages()[0] || wrapper.getFluidInTank(1).getAmount() < 10 * entities.size() * getPercentages()[1]) {
            info = "key.clib.error_insufficient_ingredients";
            return false;
        }
        if (!hasOxygenReceiver()) {
            info = "key.cgalaxy.idle_no_entities";
            return false;
        } else {
            {
                BlockPos pos = getBlockPos().north(range.get(Direction.NORTH)).above(range.get(Direction.UP)).east(range.get(Direction.EAST));
                BlockPos pos1 = getBlockPos().north(range.get(Direction.NORTH)).below(range.get(Direction.DOWN)).west(range.get(Direction.WEST));
                BlockPos invalidPos = BlockPos.betweenClosedStream(pos, pos1).filter(blockPos -> isBlockInvalid(blockPos, Direction.NORTH)).findFirst().orElse(null);
                if (invalidPos != null) {
                    info = "key.cgalaxy.error_open_room" + ", x:" + invalidPos.getX() + ", y:" + invalidPos.getY() + ", z:" + invalidPos.getZ();
                    return false;
                }

                BlockPos pos2 = getBlockPos().south(range.get(Direction.SOUTH)).above(range.get(Direction.UP)).east(range.get(Direction.EAST));
                BlockPos pos3 = getBlockPos().south(range.get(Direction.SOUTH)).below(range.get(Direction.DOWN)).west(range.get(Direction.EAST));
                BlockPos invalidPos1 = BlockPos.betweenClosedStream(pos2, pos3).filter(blockPos -> isBlockInvalid(blockPos, Direction.SOUTH)).findFirst().orElse(null);
                if (invalidPos1 != null) {
                    info = "key.cgalaxy.error_open_room" + ", x:" + invalidPos1.getX() + ", y:" + invalidPos1.getY() + ", z:" + invalidPos1.getZ();
                    return false;
                }
            }

            {
                BlockPos pos = getBlockPos().east(range.get(Direction.EAST)).above(range.get(Direction.UP)).north(range.get(Direction.NORTH));
                BlockPos pos1 = getBlockPos().east(range.get(Direction.EAST)).below(range.get(Direction.DOWN)).south(range.get(Direction.SOUTH));
                BlockPos invalidPos = BlockPos.betweenClosedStream(pos, pos1).filter(blockPos -> isBlockInvalid(blockPos, Direction.EAST)).findFirst().orElse(null);
                if (invalidPos != null) {
                    info = "key.cgalaxy.error_open_room" + ", x:" + invalidPos.getX() + ", y:" + invalidPos.getY() + ", z:" + invalidPos.getZ();
                    return false;
                }

                BlockPos pos2 = getBlockPos().west(range.get(Direction.WEST)).above(range.get(Direction.UP)).north(range.get(Direction.NORTH));
                BlockPos pos3 = getBlockPos().west(range.get(Direction.WEST)).below(range.get(Direction.DOWN)).south(range.get(Direction.SOUTH));
                BlockPos invalidPos1 = BlockPos.betweenClosedStream(pos2, pos3).filter(blockPos -> isBlockInvalid(blockPos, Direction.WEST)).findFirst().orElse(null);
                if (invalidPos1 != null) {
                    info = "key.cgalaxy.error_open_room" + ", x:" + invalidPos1.getX() + ", y:" + invalidPos1.getY() + ", z:" + invalidPos1.getZ();
                    return false;
                }
            }

            {
                BlockPos pos = getBlockPos().below(range.get(Direction.DOWN)).east(range.get(Direction.EAST)).north(range.get(Direction.NORTH));
                BlockPos pos1 = getBlockPos().below(range.get(Direction.DOWN)).west(range.get(Direction.WEST)).south(range.get(Direction.SOUTH));
                BlockPos invalidPos = BlockPos.betweenClosedStream(pos, pos1).filter(blockPos -> isBlockInvalid(blockPos, Direction.DOWN)).findFirst().orElse(null);
                if (invalidPos != null) {
                    info = "key.cgalaxy.error_open_room" + ", x:" + invalidPos.getX() + ", y:" + invalidPos.getY() + ", z:" + invalidPos.getZ();
                    return false;
                }

                BlockPos pos2 = getBlockPos().above(range.get(Direction.UP)).east(range.get(Direction.EAST)).north(range.get(Direction.NORTH));
                BlockPos pos3 = getBlockPos().above(range.get(Direction.UP)).west(range.get(Direction.WEST)).south(range.get(Direction.SOUTH));
                BlockPos invalidPos1 = BlockPos.betweenClosedStream(pos2, pos3).filter(blockPos -> isBlockInvalid(blockPos, Direction.UP)).findFirst().orElse(null);
                if (invalidPos1 != null) {
                    info = "key.cgalaxy.error_open_room" + ", x:" + invalidPos1.getX() + ", y:" + invalidPos1.getY() + ", z:" + invalidPos1.getZ();
                    return false;
                }
            }
        }

        for (LivingEntity entity : entities) {
            SpaceSuitUtils.setPressurized(entity, true);
        }
        return true;
    }

    private boolean isBlockInvalid(BlockPos pos, Direction direction) {
        assert level != null;
        BlockState s = level.getBlockState(pos);
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        if (s.isAir()) {
            flag = true;
        }
        AddSealedBlocksEvent event = new AddSealedBlocksEvent();
        MinecraftForge.EVENT_BUS.post(event);
        if (!flag && (!s.isCollisionShapeFullBlock(level, pos)) || !event.getBlocks().contains(s.getBlock())) {
            flag1 = !s.isFaceSturdy(level, pos, direction);
            flag2 = !s.isFaceSturdy(level, pos, direction.getOpposite());
            BlockPos pos1 = pos.relative(direction);
            if (flag2) {
                flag2 = !level.getBlockState(pos1).isFaceSturdy(level, pos1, direction.getOpposite());
            }
        }
        return flag || (flag1 && flag2);
    }

    @Override
    public void execute() {
        int pSize = 10 * entities.size();
        tanks[0].drain((int) (pSize * getPercentages()[0]), FluidAction.EXECUTE);
        tanks[1].drain((int) (pSize * getPercentages()[1]), FluidAction.EXECUTE);
    }

    public float[] getPercentages() {
        int[] pers = ((RoomPressurizerRecipe) Objects.requireNonNull(getRecipe())).getPercentages();
        return new float[]{pers[0] / 100f, pers[1] / 100f};
    }

    @Override
    protected int getRange() {
        return 5;
    }

    @Override
    public void executeExtra() {
        emptyFluidSlot(0, 0);
        emptyFluidSlot(1, 2);
        fillFluidSlot(0, 1);
        fillFluidSlot(1, 3);
        if (info.contains("working")) {
            var list = AABBS.get(level.dimension());
            if (list == null) {
                list = new ArrayList<>();
                list.add(aabb);
                AABBS.put(level.dimension(), list);
            } else if (!list.contains(aabb)) {
                list.add(aabb);
                AABBS.put(level.dimension(), list);
            }
        }
        if ((info.contains("idle") || info.contains("error")) && hasOxygenReceiver()) {
            resetAABB();
        }
    }

    public boolean hasOxygenReceiver() {
        boolean flag = false;
        var list = WorldSavedData.AFFECTED_BLOCKS.get(level.dimension());
        if (list != null && aabb != null) {
            for (BlockPos pos : list) {
                flag = aabb.contains(pos.getX(), pos.getY(), pos.getZ());
                if (flag) break;
            }
        }
        return entities != null && (flag || !entities.isEmpty());
    }

    public static void updateAffectedBlocks(BlockState state, BlockPos pos, Level level) {
        Block block = state.getBlock();
        if (TagHelper.tagContains(BlockTags.FIRE, block))
            level.removeBlock(pos, false);

        else if (TagHelper.tagContains(BlockTags.CAMPFIRES, block) && CampfireBlock.isLitCampfire(state)) {
            level.levelEvent(null, 1009, pos, 0);
            CampfireBlock.dowse(null, level, pos, state);
            level.setBlockAndUpdate(pos, state.setValue(CampfireBlock.LIT, Boolean.FALSE));
        } else if (TagHelper.tagContains(BlockTags.CANDLES, block) && AbstractCandleBlock.isLit(state))
            AbstractCandleBlock.extinguish(null, state, level, pos);

        else if (TagHelper.tagContains(BlockTags.create(CGalaxy.location("torches")), block)) {
            BlockState state1 = state.hasProperty(BlockStateProperties.HORIZONTAL_FACING) ?
                    CGBlockInit.COAL_WALL_TORCH.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING)) :
                    CGBlockInit.COAL_TORCH.get().defaultBlockState();
            level.setBlock(pos, state1, 2);
        }
    }

    public void resetAABB() {
        if (entities != null) {
            entities.stream().filter(SpaceSuitUtils::isPressurized).forEach(livingEntity -> {
                SpaceSuitUtils.setPressurized(livingEntity, false);
            });
        }

        var list = WorldSavedData.AFFECTED_BLOCKS.get(level.dimension());
        if (list != null) {
            List<BlockPos> list1 = new ArrayList<>(WorldSavedData.AFFECTED_BLOCKS.get(level.dimension()));
            for (BlockPos pos : list) {
                if (aabb.contains(pos.getX(), pos.getY(), pos.getZ())) {
                    list1.remove(pos);
                    BlockState state = level.getBlockState(pos);
                    updateAffectedBlocks(state, pos, level);
                }
            }

            WorldSavedData.AFFECTED_BLOCKS.put(level.dimension(), list1);
            if (level instanceof ServerLevel)
                WorldSavedData.makeDirty((ServerLevel) level);
        }

        var list1 = AABBS.get(level.dimension());
        if (list1 == null) {
            return;
        }
        list1.remove(aabb);
        AABBS.put(level.dimension(), list1);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
        return new RoomPressurizerContainer(pContainerId, pPlayer, pInventory, this, this.data);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ListTag list = nbt.getList("Dimensions", Tag.TAG_COMPOUND);
        range = deserializeRange(list);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Dimensions", serializeRange(range));
    }

    public static ListTag serializeRange(Map<Direction, Integer> range) {
        ListTag list = new ListTag();
        for (Direction i : Direction.values()) {
            CompoundTag tag = new CompoundTag();
            tag.putInt(i.getName(), range.get(i));
            list.add(tag);
        }
        return list;
    }

    public static Map<Direction, Integer> deserializeRange(ListTag list) {
        Map<Direction, Integer> range = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            for (Direction j : Direction.values()) {
                if (tag.getAllKeys().contains(j.getName())) {
                    range.put(j, tag.getInt(j.getName()));
                }
            }
        }
        return range;
    }
}
