package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.cgalaxy.init.FluidInit;
import com.congueror.cgalaxy.init.TileEntityInit;
import com.congueror.clib.blocks.AbstractFluidTileEntity;
import com.congueror.clib.items.UpgradeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.IntStream;

public class FuelRefineryTileEntity extends AbstractFluidTileEntity {

    public FuelRefineryTileEntity() {
        super(TileEntityInit.FUEL_REFINERY.get());

        this.tanks = IntStream.range(0, 2).mapToObj(k -> new FluidTank(15000)).toArray(FluidTank[]::new);
    }

    public int[] invSize() {
        return new int[]{0, 1, 2, 3, 4, 5};
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[] {1});
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

    public int getEnergyUsage() {
        return 60;
    }

    public int getEnergyCapacity() {
        return 40000;
    }

    public int getEnergyMaxReceive() {
        return 1000;
    }

    public int getProcessTime() {
        return 1000;
    }

    @Override
    public Collection<Fluid> fluidIngredients() {
        Collection<Fluid> collection = new ArrayList<>();
        collection.add(FluidInit.OIL.get());
        return collection;
    }

    @Override
    public boolean requisites() {
        return true;
    }

    public void execute() {
        if (tanks[1].getFluid().isEmpty()) {
            tanks[1].setFluid(new FluidStack(FluidInit.KEROSENE.get(), 100));//TODO recipe plz
        } else {
            tanks[1].getFluid().grow(100);
        }
        if (tanks[0].getFluid().getAmount() == getProcessSize()) {
            tanks[0].setFluid(FluidStack.EMPTY);
        } else {
            tanks[0].getFluid().shrink(100);
        }
    }

    public void executeSlot() {
        emptyBucketSlot(0);
        fillBucketSlot(1);
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new FuelRefineryContainer(p_createMenu_1_, p_createMenu_2_, this, data);
    }
}