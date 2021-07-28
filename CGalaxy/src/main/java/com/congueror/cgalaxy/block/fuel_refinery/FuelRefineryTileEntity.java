package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.cgalaxy.init.FluidInit;
import com.congueror.cgalaxy.init.TileEntityInit;
import com.congueror.clib.items.UpgradeItem;
import com.congueror.clib.blocks.AbstractFluidTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
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
        if (tanks[0].getFluid().getAmount() == getProcessSize()) {
            tanks[0].setFluid(FluidStack.EMPTY);
        } else {
            tanks[0].getFluid().shrink(100);
        }
        if (tanks[1].getFluid().isEmpty()) {
            tanks[1].setFluid(new FluidStack(FluidInit.KEROSENE.get(), 100));//TODO recipe plz
        } else {
            tanks[1].getFluid().grow(100);
        }
    }

    public void executeSlot() {
        ItemStack slot = itemHandler.getStackInSlot(0);
        if (slot.getItem() instanceof BucketItem) {
            if (((BucketItem) slot.getItem()).getFluid() != Fluids.EMPTY) {
                if (tanks[0].isEmpty()) {
                    tanks[0].setFluid(new FluidStack(((BucketItem) slot.getItem()).getFluid(), 1000));
                } else if (tanks[0].getFluid().getFluid().equals(((BucketItem) slot.getItem()).getFluid())) {
                    tanks[0].getFluid().grow(1000);
                } else {
                    return;
                }
                itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
            }
        }

        ItemStack slot1 = itemHandler.getStackInSlot(1);
        if (slot1.getItem().equals(Items.BUCKET)) {
            if (tanks[1].isEmpty()) {
                return;
            } else if (tanks[1].getFluid().getAmount() >= 1000) {
                tanks[1].getFluid().shrink(1000);
            }
            itemHandler.setStackInSlot(1, tanks[1].getFluid().getFluid().getAttributes().getBucket(tanks[1].getFluid()));
        }
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new FuelRefineryContainer(p_createMenu_1_, p_createMenu_2_, this, data);
    }
}