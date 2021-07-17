package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryTileEntity;
import com.congueror.cgalaxy.block.launch_pad.LaunchPadTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CGalaxy.MODID);

    public static final RegistryObject<TileEntityType<FuelRefineryTileEntity>> FUEL_REFINERY = TILE_ENTITY_TYPES.register("fuel_refinery", () ->
            TileEntityType.Builder.create(FuelRefineryTileEntity::new, BlockInit.FUEL_REFINERY.get()).build(null));

    public static final RegistryObject<TileEntityType<LaunchPadTileEntity>> LAUNCH_PAD = TILE_ENTITY_TYPES.register("launch_pad", () ->
            TileEntityType.Builder.create(LaunchPadTileEntity::new, BlockInit.LAUNCH_PAD.get()).build(null));
}
