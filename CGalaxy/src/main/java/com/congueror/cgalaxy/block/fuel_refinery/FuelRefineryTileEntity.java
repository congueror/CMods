package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.cgalaxy.init.FluidInit;
import com.congueror.cgalaxy.init.TileEntityInit;
import com.congueror.clib.blocks.AbstractFluidTileEntity;
import com.congueror.clib.util.ModEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
        return false;
    }

    public int getEnergyUsage() {
        return 60;
    }

    public int getCapacity() {
        return 40000;
    }

    public int getMaxReceive() {
        return 1000;
    }

    public int getProcessTime() {
        return 1000;
    }

    public void execute() {
        if (tanks[0].getFluid().getAmount() == 100) {
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