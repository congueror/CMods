package net.congueror.cgalaxy.blocks.room_pressurizer;

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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RoomPressurizerBlockEntity extends AbstractFluidBlockEntity {
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
        for (int i = -range.get(Direction.DOWN); i <= range.get(Direction.UP); i++) {
            for (int j = -range.get(Direction.WEST); j <= range.get(Direction.EAST); j++) {
                BlockPos pos = getBlockPos().north(range.get(Direction.NORTH)).above(i).east(j);
                BlockPos pos1 = getBlockPos().south(range.get(Direction.SOUTH)).above(i).east(j);
                assert level != null;
                level.setBlock(pos, Blocks.BEDROCK.defaultBlockState(), 2);
                level.setBlock(pos1, Blocks.BEDROCK.defaultBlockState(), 2);
                if (blockValid(pos)) {
                    return false;
                }
            }
            for (int j = -range.get(Direction.SOUTH); j <= range.get(Direction.NORTH); j++) {
                BlockPos pos = getBlockPos().east(range.get(Direction.EAST)).above(i).north(j);
                BlockPos pos1 = getBlockPos().west(range.get(Direction.WEST)).above(i).north(j);
                assert level != null;
                level.setBlock(pos, Blocks.BEDROCK.defaultBlockState(), 2);
                level.setBlock(pos1, Blocks.BEDROCK.defaultBlockState(), 2);
                if (blockValid(pos)) {
                    return false;
                }
            }
        }
        for (int i = -range.get(Direction.WEST); i <= range.get(Direction.EAST); i++) {
            for (int j = -range.get(Direction.SOUTH); j <= range.get(Direction.NORTH); j++) {
                BlockPos pos = getBlockPos().below(range.get(Direction.DOWN)).east(i).north(j);
                BlockPos pos1 = getBlockPos().above(range.get(Direction.UP)).east(i).north(j);
                assert level != null;
                level.setBlock(pos, Blocks.BEDROCK.defaultBlockState(), 2);
                level.setBlock(pos1, Blocks.BEDROCK.defaultBlockState(), 2);
                if (blockValid(pos)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean blockValid(BlockPos pos) {
        assert level != null;
        BlockState s = level.getBlockState(pos);
        return s.isAir() || (s.hasProperty(BlockStateProperties.OPEN) && s.getValue(BlockStateProperties.OPEN)) || !s.isSolidRender(level, pos);
    }

    @Override
    public void execute() {

    }

    @Override
    protected int getRange() {
        return 4;
    }

    @Override
    public void executeSlot() {
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
            for (Direction j : Direction.values())
                range.put(j, tag.getInt(j.getName()));
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
