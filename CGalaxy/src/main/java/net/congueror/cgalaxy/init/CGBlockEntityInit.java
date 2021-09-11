package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.block.fuel_loader.FuelLoaderBlockEntity;
import net.congueror.cgalaxy.block.fuel_refinery.FuelRefineryBlockEntity;
import net.congueror.cgalaxy.block.launch_pad.LaunchPadBlockEntity;
import net.congueror.cgalaxy.block.meteorite.MeteoriteBlockEntity;
import net.congueror.cgalaxy.block.oxygen_compressor.OxygenCompressorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("ConstantConditions")
public class CGBlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CGalaxy.MODID);

    public static final RegistryObject<BlockEntityType<MeteoriteBlockEntity>> METEORITE = BLOCK_ENTITY_TYPES.register("meteorite", () ->
            BlockEntityType.Builder.of(MeteoriteBlockEntity::new, CGBlockInit.METEORITE.get()).build(null));

    public static final RegistryObject<BlockEntityType<LaunchPadBlockEntity>> LAUNCH_PAD = BLOCK_ENTITY_TYPES.register("launch_pad", () ->
            BlockEntityType.Builder.of(LaunchPadBlockEntity::new, CGBlockInit.LAUNCH_PAD.get()).build(null));

    public static final RegistryObject<BlockEntityType<FuelLoaderBlockEntity>> FUEL_LOADER = BLOCK_ENTITY_TYPES.register("fuel_loader", () ->
            BlockEntityType.Builder.of(FuelLoaderBlockEntity::new, CGBlockInit.FUEL_LOADER.get()).build(null));

    public static final RegistryObject<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY = BLOCK_ENTITY_TYPES.register("fuel_refinery", () ->
            BlockEntityType.Builder.of(FuelRefineryBlockEntity::new, CGBlockInit.FUEL_REFINERY.get()).build(null));

    public static final RegistryObject<BlockEntityType<OxygenCompressorBlockEntity>> OXYGEN_COMPRESSOR = BLOCK_ENTITY_TYPES.register("oxygen_compressor", () ->
            BlockEntityType.Builder.of(OxygenCompressorBlockEntity::new, CGBlockInit.OXYGEN_COMPRESSOR.get()).build(null));
}
