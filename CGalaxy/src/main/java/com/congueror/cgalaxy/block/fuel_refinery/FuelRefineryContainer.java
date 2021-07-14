package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.cgalaxy.init.ContainerInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;

public class FuelRefineryContainer extends ModContainer<FuelRefineryTileEntity> {
    public FuelRefineryContainer(int id, PlayerInventory playerInventory, FuelRefineryTileEntity tile, IIntArray dataIn) {
        super(ContainerInit.FUEL_REFINERY.get(), id, playerInventory, tile, dataIn);
    }
}
