package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.BlockHolderBlockEntity;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderBlockEntity;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryBlockEntity;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorBlockEntity;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlockEntity;
import net.congueror.cgalaxy.blocks.sealed_cable.SealedEnergyCableBlockEntity;
import net.congueror.cgalaxy.blocks.sealed_cable.SealedFluidCableBlockEntity;
import net.congueror.cgalaxy.blocks.sealed_cable.SealedItemCableBlockEntity;
import net.congueror.cgalaxy.blocks.space_station_creator.SpaceStationCreatorBlockEntity;
import net.congueror.cgalaxy.blocks.solar_generator.SolarGeneratorBlockEntity;
import net.congueror.clib.init.CLBlockInit;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public class CGBlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CGalaxy.MODID);

    public static final RegistryObject<BlockEntityType<SolarGeneratorBlockEntity>> SOLAR_GENERATOR = BLOCK_ENTITY_TYPES.register("solar_generator", () ->
            BlockEntityType.Builder.of(SolarGeneratorBlockEntity::new,
                    CGBlockInit.SOLAR_GENERATOR_1.get(),
                    CGBlockInit.SOLAR_GENERATOR_2.get(),
                    CGBlockInit.SOLAR_GENERATOR_3.get(),
                    CGBlockInit.SOLAR_GENERATOR_CREATIVE.get()).build(null));

    public static final RegistryObject<BlockEntityType<FuelLoaderBlockEntity>> FUEL_LOADER = BLOCK_ENTITY_TYPES.register("fuel_loader", () ->
            BlockEntityType.Builder.of(FuelLoaderBlockEntity::new, CGBlockInit.FUEL_LOADER.get()).build(null));

    public static final RegistryObject<BlockEntityType<FuelRefineryBlockEntity>> FUEL_REFINERY = BLOCK_ENTITY_TYPES.register("fuel_refinery", () ->
            BlockEntityType.Builder.of(FuelRefineryBlockEntity::new, CGBlockInit.FUEL_REFINERY.get()).build(null));

    public static final RegistryObject<BlockEntityType<GasExtractorBlockEntity>> GAS_EXTRACTOR = BLOCK_ENTITY_TYPES.register("gas_extractor", () ->
            BlockEntityType.Builder.of(GasExtractorBlockEntity::new, CGBlockInit.GAS_EXTRACTOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<RoomPressurizerBlockEntity>> ROOM_PRESSURIZER = BLOCK_ENTITY_TYPES.register("room_pressurizer", () ->
            BlockEntityType.Builder.of(RoomPressurizerBlockEntity::new, CGBlockInit.ROOM_PRESSURIZER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SpaceStationCreatorBlockEntity>> SPACE_STATION_CREATOR = BLOCK_ENTITY_TYPES.register("space_station_creator", () ->
            BlockEntityType.Builder.of(SpaceStationCreatorBlockEntity::new, CGBlockInit.SPACE_STATION_CREATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<BlockHolderBlockEntity>> BLOCK_HOLDER = BLOCK_ENTITY_TYPES.register("block_holder", () ->
            BlockEntityType.Builder.of(BlockHolderBlockEntity::new, CGBlockInit.BLOCK_HOLDER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SealedEnergyCableBlockEntity>> SEALED_ENERGY_CABLE = BLOCK_ENTITY_TYPES.register("sealed_energy_cable", () ->
            BlockEntityType.Builder.of(SealedEnergyCableBlockEntity::new, CGBlockInit.SEALED_ENERGY_CABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SealedItemCableBlockEntity>> SEALED_ITEM_CABLE = BLOCK_ENTITY_TYPES.register("sealed_item_cable", () ->
            BlockEntityType.Builder.of(SealedItemCableBlockEntity::new, CGBlockInit.SEALED_ITEM_CABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SealedFluidCableBlockEntity>> SEALED_FLUID_CABLE = BLOCK_ENTITY_TYPES.register("sealed_fluid_cable", () ->
            BlockEntityType.Builder.of(SealedFluidCableBlockEntity::new, CGBlockInit.SEALED_FLUID_CABLE.get()).build(null));
}
