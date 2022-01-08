package net.congueror.cgalaxy.blocks.room_pressurizer;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.api.machine.fluid.AbstractFluidBlockEntity;
import net.congueror.clib.api.recipe.FluidRecipe;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class RoomPressurizerBlockEntity extends AbstractFluidBlockEntity {
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

    public RoomPressurizerBlockEntity(BlockPos pos, BlockState state) {
        super(CGBlockEntityInit.ROOM_PRESSURIZER.get(), pos, state);
        this.tanks = IntStream.range(0, 1).mapToObj(value -> new FluidTank(15000)).toArray(FluidTank[]::new);
        sendOutFluid = false;
    }

    @Nullable
    @Override
    public FluidRecipe<?> getRecipe() {
        assert level != null;
        return level.getRecipeManager().getRecipeFor(CGRecipeSerializerInit.Types.ROOM_PRESSURIZING, wrapper, level).orElse(null);
    }

    @Override
    public int[] invSize() {
        return new int[]{0, 1, 2, 3, 4, 5};
    }

    @Override
    public HashMap<String, int[]> inputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{0});
        map.put("slots", new int[]{0});
        return map;
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{0});
        map.put("slots", new int[]{1});
        return map;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0 || slot == 1) {
            return stack.getItem() instanceof BucketItem;
        } else if (slot >= 2) {
            return stack.getItem() instanceof UpgradeItem;
        }
        return false;
    }

    @Override
    public int getEnergyUsage() {
        return 0;
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
        entities = level.getEntities(EntityTypeTest.forClass(LivingEntity.class), new AABB(posA, posB), Objects::nonNull);
        if (entities.isEmpty()) {
            info = "key.cgalaxy.idle_no_entities";
            return false;
        } else {
            for (int i = -range.get(Direction.DOWN); i <= range.get(Direction.UP); i++) {
                for (int j = -range.get(Direction.WEST); j <= range.get(Direction.EAST); j++) {
                    BlockPos pos = getBlockPos().north(range.get(Direction.NORTH)).above(i).east(j);
                    BlockPos pos1 = getBlockPos().south(range.get(Direction.SOUTH)).above(i).east(j);
                    if (isBlockInvalid(pos, Direction.NORTH)) {
                        info = "key.cgalaxy.error_open_room" + ", x:" + pos.getX() + ", y:" + pos.getY() + ", z:" + pos.getZ();
                        return false;
                    }
                    if (isBlockInvalid(pos1, Direction.SOUTH)) {
                        info = "key.cgalaxy.error_open_room" + ", x:" + pos1.getX() + ", y:" + pos1.getY() + ", z:" + pos1.getZ();
                        return false;
                    }
                }
                for (int j = -range.get(Direction.SOUTH) + 1; j <= range.get(Direction.NORTH) - 1; j++) {
                    BlockPos pos = getBlockPos().east(range.get(Direction.EAST)).above(i).north(j);
                    BlockPos pos1 = getBlockPos().west(range.get(Direction.WEST)).above(i).north(j);
                    if (isBlockInvalid(pos, Direction.EAST)) {
                        info = "key.cgalaxy.error_open_room" + ", x:" + pos.getX() + ", y:" + pos.getY() + ", z:" + pos.getZ();
                        return false;
                    }
                    if (isBlockInvalid(pos1, Direction.WEST)) {
                        info = "key.cgalaxy.error_open_room" + ", x:" + pos1.getX() + ", y:" + pos1.getY() + ", z:" + pos1.getZ();
                        return false;
                    }
                }
            }
            for (int i = -range.get(Direction.WEST) + 1; i <= range.get(Direction.EAST) - 1; i++) {
                for (int j = -range.get(Direction.SOUTH) + 1; j <= range.get(Direction.NORTH) - 1; j++) {
                    BlockPos pos = getBlockPos().below(range.get(Direction.DOWN)).east(i).north(j);
                    BlockPos pos1 = getBlockPos().above(range.get(Direction.UP)).east(i).north(j);
                    if (isBlockInvalid(pos, Direction.DOWN)) {
                        info = "key.cgalaxy.error_open_room" + ", x:" + pos.getX() + ", y:" + pos.getY() + ", z:" + pos.getZ();
                        return false;
                    }
                    if (isBlockInvalid(pos1, Direction.UP)) {
                        info = "key.cgalaxy.error_open_room" + ", x:" + pos1.getX() + ", y:" + pos1.getY() + ", z:" + pos1.getZ();
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isBlockInvalid(BlockPos pos, Direction direction) {
        assert level != null;
        BlockState s = level.getBlockState(pos);
        boolean flag = false;
        boolean flag1 = false;
        if (s.isAir()) {
            flag = true;
        }
        if (!flag && !s.isCollisionShapeFullBlock(level, pos)) {
            if (s.hasProperty(BlockStateProperties.OPEN)) {
                //TODO: Check for facings
                BlockPos pos1 = pos.relative(direction);
                if (level.getBlockState(pos1).hasProperty(BlockStateProperties.OPEN)) {
                    flag1 = s.getValue(BlockStateProperties.OPEN) && level.getBlockState(pos1).getValue(BlockStateProperties.OPEN);
                } else {
                    flag1 = s.getValue(BlockStateProperties.OPEN);
                }
            } else {
                flag1 = true;
            }
        }
        return flag || flag1;
    }

    @Override
    public void execute() {
        for (LivingEntity entity : entities) {
            entity.getPersistentData().putBoolean(CGalaxy.LIVING_PRESSURIZED, true);
        }
        int pSize = 10 * entities.size();
        tanks[0].drain(pSize, FluidAction.EXECUTE);
    }

    @Override
    protected int getRange() {
        return 4;
    }

    @Override
    public void executeExtra() {
        emptyBucketSlot(0, 0);
        fillBucketSlot(0, 1);
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
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            for (Direction j : Direction.values()) {
                if (tag.getAllKeys().contains(j.getName())) {
                    range.put(j, tag.getInt(j.getName()));
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ListTag nbt = new ListTag();
        for (Direction i : Direction.values()) {
            CompoundTag tag = new CompoundTag();
            tag.putInt(i.getName(), range.get(i));
            nbt.add(tag);
        }
        pTag.put("Dimensions", nbt);
    }
}
