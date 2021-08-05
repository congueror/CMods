package com.congueror.cgalaxy.block.fuel_loader;

import com.congueror.cgalaxy.block.launch_pad.LaunchPadBlock;
import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.FluidInit;
import com.congueror.cgalaxy.init.TileEntityInit;
import com.congueror.clib.blocks.AbstractFluidTileEntity;
import com.congueror.clib.items.UpgradeItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.IntStream;

public class FuelLoaderTileEntity extends AbstractFluidTileEntity {
    RocketEntity entity;

    public FuelLoaderTileEntity() {
        super(TileEntityInit.FUEL_LOADER.get());

        this.tanks = IntStream.range(0, 1).mapToObj(k -> new FluidTank(15000)).toArray(FluidTank[]::new);
    }

    @Override
    public int[] invSize() {
        return new int[]{0, 1, 2, 3, 4};
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{0});
        return map;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0) {
            return stack.getItem() instanceof BucketItem;
        } else if (slot >= 1) {
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
        return 1000;
    }

    @Override
    public int getEnergyMaxReceive() {
        return 40;
    }

    @Override
    public int getProcessTime() {
        return 600;
    }

    @Override
    public Collection<Fluid> fluidIngredients() {
        Collection<Fluid> collection = new ArrayList<>();
        collection.add(FluidInit.KEROSENE.get());
        return collection;
    }

    @Override
    public boolean requisites() {
        for (Direction dir : Direction.values()) {
            if (dir.equals(Direction.DOWN) || dir.equals(Direction.UP)) {
                continue;
            }
            BlockPos pos = getPos().offset(dir);
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
            int fill = entity.fill(Math.min(tanks[0].getFluidAmount(), getProcessSize()));
            if (tanks[0].getFluid().getAmount() == fill) {
                tanks[0].setFluid(FluidStack.EMPTY);
            } else {
                tanks[0].getFluid().shrink(fill);
            }
        }
    }

    @Override
    public void executeSlot() {
        emptyBucketSlot(0);
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new FuelLoaderContainer(p_createMenu_1_, p_createMenu_2_, this, data);
    }
}
