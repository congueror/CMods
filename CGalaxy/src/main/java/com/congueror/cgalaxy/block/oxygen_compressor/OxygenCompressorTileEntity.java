package com.congueror.cgalaxy.block.oxygen_compressor;

import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.cgalaxy.init.TileEntityInit;
import com.congueror.cgalaxy.items.OxygenTankItem;
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

public class OxygenCompressorTileEntity extends AbstractFluidTileEntity {
    public OxygenCompressorTileEntity() {
        super(TileEntityInit.OXYGEN_COMPRESSOR.get());
        tanks = IntStream.range(0, 1).mapToObj(k -> new FluidTank(15000)).toArray(FluidTank[]::new);
    }

    @Override
    public IFluidRecipe<?> getRecipe() {
        assert world != null;
        return world.getRecipeManager().getRecipe(RecipeSerializerInit.Types.OXYGEN_COMPRESSING, wrapper, world).orElse(null);
    }

    @Override
    public int[] invSize() {
        return new int[]{0, 1, 2, 3, 4, 5, 6};
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{0});
        map.put("slots", new int[]{2});
        return map;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0) {
            return true;
        }
        if (slot == 1) {
            return stack.getItem() instanceof BucketItem;
        }
        if (slot == 2) {
            return stack.getItem().equals(Items.BUCKET) || stack.getItem() instanceof OxygenTankItem;
        }
        if (slot >= 3 && slot <= 6) {
            return stack.getItem() instanceof UpgradeItem;
        }
        return false;
    }

    @Override
    public int getEnergyUsage() {
        return 50;
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
        return OperationType.ITEM_TO_FLUID;
    }

    @Override
    public boolean requisites() {
        return true;
    }

    @Override
    public void execute() {
        Objects.requireNonNull(getFluidResults(Objects.requireNonNull(getRecipe()))).forEach(this::storeResultFluid);
        wrapper.getStackInSlot(0).shrink(1);
    }

    @Override
    public void executeSlot() {
        emptyBucketSlot(0, 1);
        fillBucketSlot(0, 2);
        ItemStack slot1 = wrapper.getStackInSlot(2);
        if (slot1.getItem() instanceof OxygenTankItem) {
            if (tanks[0].getFluid().getAmount() > 0) {
                int filled = ((OxygenTankItem) slot1.getItem()).fill(slot1, 1);
                tanks[0].drain(filled, FluidAction.EXECUTE);
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new OxygenCompressorContainer(p_createMenu_1_, p_createMenu_2_, this, data);
    }
}
