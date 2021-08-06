package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.block.fuel_loader.FuelLoaderContainer;
import com.congueror.cgalaxy.block.fuel_loader.FuelLoaderTileEntity;
import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryContainer;
import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryTileEntity;
import com.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, CGalaxy.MODID);

    public static final RegistryObject<ContainerType<FuelRefineryContainer>> FUEL_REFINERY = CONTAINER_TYPES.register("fuel_refinery", () ->
            IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                TileEntity tile = inv.player.getEntityWorld().getTileEntity(pos);
                FuelRefineryTileEntity te = (FuelRefineryTileEntity) tile;
                return new FuelRefineryContainer(windowId, inv, te, new IntArray(FuelRefineryTileEntity.FIELDS_COUNT));
            }));

    public static final RegistryObject<ContainerType<FuelLoaderContainer>> FUEL_LOADER = CONTAINER_TYPES.register("fuel_loader", () ->
            IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                TileEntity tile = inv.player.getEntityWorld().getTileEntity(pos);
                FuelLoaderTileEntity te = (FuelLoaderTileEntity) tile;
                return new FuelLoaderContainer(windowId, inv, te, new IntArray(FuelLoaderTileEntity.FIELDS_COUNT));
            }));

    public static final RegistryObject<ContainerType<GalaxyMapContainer>> GALAXY_MAP = CONTAINER_TYPES.register("galaxy_map", () ->
            IForgeContainerType.create((windowId, inv, data) -> new GalaxyMapContainer(windowId, false)));
}
