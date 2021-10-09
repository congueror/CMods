package net.congueror.cgalaxy.blocks.fuel_loader;

import net.congueror.cgalaxy.blocks.launch_pad.LaunchPadBlock;
import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.api.machine.fluid.AbstractFluidBlockEntity;
import net.congueror.clib.api.recipe.IFluidRecipe;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.IntStream;

public class FuelLoaderBlockEntity extends AbstractFluidBlockEntity {
    RocketEntity entity;

    public FuelLoaderBlockEntity(BlockPos pos, BlockState state) {
        super(CGBlockEntityInit.FUEL_LOADER.get(), pos, state);
        this.tanks = IntStream.range(0, 1).mapToObj(value -> new FluidTank(15000)).toArray(FluidTank[]::new);
    }

    @Override
    public int getSlotLimits(int slot) {
        if (slot == 1) {
            return 1;
        }
        return super.getSlotLimits(slot);
    }

    @Nullable
    @Override
    public IFluidRecipe<?> getRecipe() {
        assert level != null;
        return level.getRecipeManager().getRecipeFor(CGRecipeSerializerInit.Types.FUEL_LOADING, wrapper, level).orElse(null);
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
        return 30;
    }

    @Override
    public int getEnergyCapacity() {
        return 40000;
    }

    @Override
    public int getProcessTime() {
        return Objects.requireNonNull(getRecipe()).getProcessTime();
    }

    @Nullable
    @Override
    public Collection<FluidStack> getFluidResults(IFluidRecipe<?> recipe) {
        return recipe.getFluidResults();
    }

    @Nullable
    @Override
    public Collection<ItemStack> getItemResults(IFluidRecipe<?> recipe) {
        return recipe.getItemResults();
    }

    @Override
    public boolean requisites() {
        for (Direction dir : Direction.values()) {
            if (dir.equals(Direction.DOWN) || dir.equals(Direction.UP)) {
                continue;
            }
            BlockPos pos = getBlockPos().relative(dir);
            assert level != null;
            if (level.getBlockState(pos).is(CGBlockInit.LAUNCH_PAD.get())) {
                LaunchPadBlock pad = (LaunchPadBlock) level.getBlockState(pos).getBlock();
                if (pad.getMidBlock(level, pos) != null) {
                    BlockPos mid = pad.getMidBlock(level, pos);
                    if (((LaunchPadBlock) level.getBlockState(mid).getBlock()).getRocket(level, mid) != null) {
                        entity = ((LaunchPadBlock) level.getBlockState(mid).getBlock()).getRocket(level, mid);
                        return true;
                    } else {
                        info = "key.cgalaxy.error_missing_rocket";
                    }
                } else {
                    info = "key.cgalaxy.error_incomplete_pad";
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if (entity.getFuel() < entity.getCapacity()) {
            FuelLoaderRecipe recipe = (FuelLoaderRecipe) getRecipe();
            assert recipe != null;
            int amount = recipe.getIngredient().getFluidStacks().get(0).getAmount();
            int fill = entity.fill(Math.min(tanks[0].getFluidAmount(), amount + getFluidProcessSize()));
            tanks[0].drain(fill, FluidAction.EXECUTE);
        } else {
            info = "key.cgalaxy.idle_rocket_full";
        }
    }

    @Override
    public void executeSlot() {
        emptyBucketSlot(0, 0);
        fillBucketSlot(0, 1);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
        return new FuelLoaderContainer(pContainerId, (ServerPlayer) pPlayer, pInventory, this, data);
    }
}
