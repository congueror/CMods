package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGFeatureInit;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.congueror.clib.world.WorldHelper.*;

@Mod.EventBusSubscriber(modid = CGalaxy.MODID)
public class CGFeatureGen {

    public static PlacedFeature MOON_IRON;
    public static PlacedFeature MOON_SILICON;
    public static PlacedFeature MOON_ALUMINUM;
    public static PlacedFeature MOON_TITANIUM;//TODO: Glowstone ore, remove titanium
    public static PlacedFeature MOON_ICE_CLUSTER;

    public static PlacedFeature OIL_LAKE;
    public static PlacedFeature METEORITE;
    public static PlacedFeature SAPPHIRE_METEORITE;
    public static PlacedFeature METEORITE_OVERWORLD;

    public static void registerFeatures() {//TODO: Cheese Ore
        RuleTest moon = blockRuleTest(CGBlockInit.MOON_STONE.get());
        MOON_IRON = registerConfiguredOre(moon, CGBlockInit.MOON_IRON_ORE.get(), 8, 0, 64, 12);
        MOON_SILICON = registerConfiguredOre(moon, CGBlockInit.MOON_ALUMINUM_ORE.get(), 7, 0, 64, 10);
        MOON_ALUMINUM = registerConfiguredOre(moon, CGBlockInit.MOON_SILICON_ORE.get(), 4, 0, 64, 8);
        MOON_TITANIUM = registerConfiguredOre(moon, CGBlockInit.MOON_TITANIUM_ORE.get(), 2, 0, 16, 1);

        MOON_ICE_CLUSTER = registerConfiguredOre(moon, Blocks.ICE, 3, 50, 70, 4);

        OIL_LAKE = registerConfiguredLake(CGBlockInit.OIL.get(), 14, 34, 34);

        METEORITE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(CGalaxy.MODID, "meteorite"), CGFeatureInit.METEORITE.get().configured(new BlockStateConfiguration(CGBlockInit.METEORITE.get().defaultBlockState())))
                .placed(CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(10), BiomeFilter.biome(), InSquarePlacement.spread());

        SAPPHIRE_METEORITE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(CGalaxy.MODID, "astral_sapphire_meteorite"), CGFeatureInit.METEORITE.get().configured(new BlockStateConfiguration(CGBlockInit.ASTRAL_SAPPHIRE_ORE.get().defaultBlockState())))
                .placed(CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(40), BiomeFilter.biome(), InSquarePlacement.spread());;

        METEORITE_OVERWORLD = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(CGalaxy.MODID, "meteorite_overworld"), CGFeatureInit.METEORITE.get().configured(new BlockStateConfiguration(CGBlockInit.METEORITE.get().defaultBlockState())))
                .placed(CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(100), BiomeFilter.biome(), InSquarePlacement.spread());;
    }

    @SubscribeEvent
    public static void setupOreGen(BiomeLoadingEvent e) {
        if (isOverworld(e)) {
            e.getGeneration().addFeature(GenerationStep.Decoration.LAKES, OIL_LAKE);
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, METEORITE_OVERWORLD);
        }
        if (isBiome(e, CGBiomes.THE_MOON)) {
            addOre(e, MOON_IRON, MOON_SILICON, MOON_ALUMINUM, MOON_TITANIUM);

            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, METEORITE);
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, SAPPHIRE_METEORITE);
        }
        if (isBiome(e, CGBiomes.THE_MOON_SOUTH)) {
            addOre(e, MOON_ICE_CLUSTER);
        }
    }
}