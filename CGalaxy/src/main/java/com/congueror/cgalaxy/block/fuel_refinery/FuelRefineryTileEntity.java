package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.cgalaxy.init.TileEntityInit;
import com.congueror.clib.api.machines.fluid_machine.AbstractFluidTileEntity;
import com.congueror.clib.items.UpgradeItem;
import com.congueror.clib.api.recipe.IFluidRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.IntStream;

public class FuelRefineryTileEntity extends AbstractFluidTileEntity {

    public FuelRefineryTileEntity() {
        super(TileEntityInit.FUEL_REFINERY.get());

        this.tanks = IntStream.range(0, 2).mapToObj(k -> new FluidTank(15000)).toArray(FluidTank[]::new);
    }

    @Override
    public IFluidRecipe<?> getRecipe() {
        assert world != null;
        return world.getRecipeManager().getRecipe(RecipeSerializerInit.Types.FUEL_REFINING, wrapper, world).orElse(null);
    }

    @Override
    public int[] invSize() {
        return new int[]{0, 1, 2, 3, 4, 5};
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{1});
        map.put("slots", new int[]{1});
        return map;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0) {
            return stack.getItem() instanceof BucketItem;
        }
        if (slot == 1) {
            return stack.getItem().equals(Items.BUCKET);
        }
        if (slot >= 2 && slot <= 5) {
            return stack.getItem() instanceof UpgradeItem;
        }
        return false;
    }

    @Override
    public int getEnergyUsage() {
        return 60;
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
        return OperationType.FLUID_TO_FLUID;
    }

    @Override
    public boolean requisites() {
        return true;
    }

    @Override
    public void execute() {
        Objects.requireNonNull(getFluidResults(Objects.requireNonNull(getRecipe()))).forEach(this::storeResultFluid);
        tanks[0].drain(getFluidProcessSize(), FluidAction.EXECUTE);
    }

    @Override
    public void executeSlot() {
        emptyBucketSlot(0, 0);
        fillBucketSlot(1, 1);
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new FuelRefineryContainer(p_createMenu_1_, p_createMenu_2_, this, data);
    }
}