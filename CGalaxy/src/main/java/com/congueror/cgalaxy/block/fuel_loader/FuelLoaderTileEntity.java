package com.congueror.cgalaxy.block.fuel_loader;

import com.congueror.cgalaxy.block.launch_pad.LaunchPadBlock;
import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.cgalaxy.init.TileEntityInit;
import com.congueror.cgalaxy.recipes.FuelLoaderRecipe;
import com.congueror.clib.blocks.tile_entity.AbstractFluidTileEntity;
import com.congueror.clib.items.UpgradeItem;
import com.congueror.clib.recipe.IFluidRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.IntStream;

public class FuelLoaderTileEntity extends AbstractFluidTileEntity {
    RocketEntity entity;

    public FuelLoaderTileEntity() {
        super(TileEntityInit.FUEL_LOADER.get());

        this.tanks = IntStream.range(0, 1).mapToObj(k -> new FluidTank(15000)).toArray(FluidTank[]::new);
    }

    @Nullable
    @Override
    public IFluidRecipe<?> getRecipe() {
        assert world != null;
        return world.getRecipeManager().getRecipe(RecipeSerializerInit.Types.FUEL_LOADING, wrapper, world).orElse(null);
    }

    @Override
    public int[] invSize() {
        return new int[]{0, 1, 2, 3, 4, 5};
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
    public OperationType getOperation() {
        return OperationType.FLUID_TO_WORLD;
    }

    @Override
    public boolean requisites() {
        for (Direction dir : Direction.values()) {
            if (dir.equals(Direction.DOWN) || dir.equals(Direction.UP)) {
                continue;
            }
            BlockPos pos = getPos().offset(dir);
            assert world != null;
            if (world.getBlockState(pos).matchesBlock(BlockInit.LAUNCH_PAD.get())) {
                LaunchPadBlock pad = (LaunchPadBlock) world.getBlockState(pos).getBlock();
                if (pad.getMidBlock(world, pos) != null) {
                    BlockPos mid = pad.getMidBlock(world, pos);
                    if (((LaunchPadBlock) world.getBlockState(mid).getBlock()).getRocket(world, mid) != null) {
                        entity = ((LaunchPadBlock) world.getBlockState(mid).getBlock()).getRocket(world, mid);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        if (entity.getFuel() < entity.getFuelCapacity()) {
            FuelLoaderRecipe recipe = (FuelLoaderRecipe) getRecipe();
            assert recipe != null;
            int amount = recipe.getIngredient().getFluidStacks().get(0).getAmount();
            int fill = entity.fill(Math.min(tanks[0].getFluidAmount(), amount + getFluidProcessSize()));
            tanks[0].drain(fill, FluidAction.EXECUTE);
        }
    }

    @Override
    public void executeSlot() {
        emptyBucketSlot(0, 0);
        fillBucketSlot(0, 1);
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new FuelLoaderContainer(p_createMenu_1_, p_createMenu_2_, this, data);
    }
}
