package net.congueror.cgalaxy.blocks.room_pressurizer;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.api.machine.fluid.AbstractFluidBlockEntity;
import net.congueror.clib.api.recipe.FluidRecipe;
import net.congueror.clib.api.recipe.ItemRecipe;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RoomPressurizerBlockEntity extends AbstractFluidBlockEntity {
    public RoomPressurizerBlockEntity(BlockPos pos, BlockState state) {
        super(CGBlockEntityInit.ROOM_PRESSURIZER.get(), pos, state);
        this.tanks = IntStream.range(0, 1).mapToObj(value -> new FluidTank(15000)).toArray(FluidTank[]::new);
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
        int r = getRangeFinal();
        Map<Direction, Integer> rs = new HashMap<>();
        for (Direction i : Direction.values()) {
            switch (i) {
                case UP -> {
                    for (int j = 1; j <= r + 1; j++) {
                        assert level != null;
                        BlockPos pos = getBlockPos().above(j);
                        if (!level.getBlockState(pos).isAir()) {
                            rs.put(i, j);
                            break;
                        }
                    }
                }
                case DOWN -> {
                    for (int j = 1; j <= r + 1; j++) {
                        assert level != null;
                        BlockPos pos = getBlockPos().below(j);
                        if (!level.getBlockState(pos).isAir()) {
                            rs.put(i, j);
                            break;
                        }
                    }
                }
                case EAST -> {
                    for (int j = 1; j <= r + 1; j++) {
                        assert level != null;
                        BlockPos pos = getBlockPos().east(j);
                        if (!level.getBlockState(pos).isAir()) {
                            rs.put(i, j);
                            break;
                        }
                    }
                }
                case WEST -> {
                    for (int j = 1; j <= r + 1; j++) {
                        assert level != null;
                        BlockPos pos = getBlockPos().west(j);
                        if (!level.getBlockState(pos).isAir()) {
                            rs.put(i, j);
                            break;
                        }
                    }
                }
                case NORTH -> {
                    for (int j = 1; j <= r + 1; j++) {
                        assert level != null;
                        BlockPos pos = getBlockPos().north(j);
                        if (!level.getBlockState(pos).isAir()) {
                            rs.put(i, j);
                            break;
                        }
                    }
                }
                case SOUTH -> {
                    for (int j = 1; j <= r + 1; j++) {
                        assert level != null;
                        BlockPos pos = getBlockPos().south(j);
                        if (!level.getBlockState(pos).isAir()) {
                            rs.put(i, j);
                            break;
                        }
                    }
                }
            }
        }
        for (Integer integer : rs.values()) {
            if (integer == 0) return false;
        }

        for (int i = -rs.get(Direction.DOWN); i <= rs.get(Direction.UP); i++) {
            for (int j = -rs.get(Direction.WEST); j <= rs.get(Direction.EAST); j++) {
                BlockPos pos = getBlockPos().north(rs.get(Direction.NORTH)).above(i).east(j);
                assert level != null;
                if (level.getBlockState(pos).isAir()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void execute() {

    }

    @Override
    protected int getRange() {
        return 6;
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
}
