package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderBlockEntity;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryBlockEntity;
import net.congueror.cgalaxy.blocks.launch_pad.LaunchPadBlockEntity;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorBlockEntity;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public class CGBlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CGalaxy.MODID);

    public static final RegistryObject<BlockEntityType<LaunchPadBlockEntity>> LAUNCH_PAD = BLOCK_ENTITY_TYPES.register("launch_pad", () ->
            BlockEntityType.Builder.of(LaunchPadBlockEntity::new, CGBlockInit.LAUNCH_PAD.get()).build(null));

    public static final RegistryObject<BlockEntityType<FuelLoaderBlockEntity>> FUEL_LOADER = BLOCK_ENTITY_TYPES.register("fuel_loader", () ->
            BlockEntityType.Builder.of(FuelLoaderBlockEntity::new, CGBlockInit.FUEL_LOADER.get()).build(null));

    public static final RegistryObject<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY = BLOCK_ENTITY_TYPES.register("fuel_refinery", () ->
            BlockEntityType.Builder.of(FuelRefineryBlockEntity::new, CGBlockInit.FUEL_REFINERY.get()).build(null));

    public static final RegistryObject<BlockEntityType<GasExtractorBlockEntity>> GAS_EXTRACTOR = BLOCK_ENTITY_TYPES.register("gas_extractor", () ->
            BlockEntityType.Builder.of(GasExtractorBlockEntity::new, CGBlockInit.GAS_EXTRACTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<RoomPressurizerBlockEntity>> ROOM_PRESSURIZER = BLOCK_ENTITY_TYPES.register("room_pressurizer", () ->
            BlockEntityType.Builder.of(RoomPressurizerBlockEntity::new, CGBlockInit.ROOM_PRESSURIZER.get()).build(null));
}
