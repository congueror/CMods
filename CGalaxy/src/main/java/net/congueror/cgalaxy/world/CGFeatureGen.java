package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGFeatureInit;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static net.congueror.clib.world.WorldHelper.*;

@Mod.EventBusSubscriber(modid = CGalaxy.MODID)
public class CGFeatureGen {

    public static Holder<PlacedFeature> MOON_IRON;
    public static Holder<PlacedFeature> MOON_SILICON;
    public static Holder<PlacedFeature> MOON_ALUMINUM;
    public static Holder<PlacedFeature> MOON_GLOWSTONE;
    public static Holder<PlacedFeature> MOON_ICE_CLUSTER;
    public static Holder<PlacedFeature> MOON_CHEESE_CLUSTER;

    public static Holder<PlacedFeature> OIL_LAKE;
    public static Holder<PlacedFeature> METEORITE;
    public static Holder<PlacedFeature> SAPPHIRE_METEORITE;
    public static Holder<PlacedFeature> METEORITE_OVERWORLD;

    public static void registerFeatures() {
        RuleTest moon = blockRuleTest(CGBlockInit.MOON_STONE.get());
        MOON_IRON = registerConfiguredOre(moon, CGBlockInit.MOON_IRON_ORE.get(), 8, 0, 78, 12);
        MOON_SILICON = registerConfiguredOre(moon, CGBlockInit.MOON_ALUMINUM_ORE.get(), 7, 0, 78, 10);
        MOON_ALUMINUM = registerConfiguredOre(moon, CGBlockInit.MOON_SILICON_ORE.get(), 4, 0, 78, 8);
        MOON_GLOWSTONE = registerConfiguredOre(moon, CGBlockInit.MOON_GLOWSTONE_ORE.get(), 4, 0, 32, 10);

        MOON_ICE_CLUSTER = registerConfiguredOre(moon, Blocks.ICE, 3, 50, 70, 4);
        MOON_CHEESE_CLUSTER = registerConfiguredOre(moon, CGBlockInit.MOON_CHEESE.get(), 5, 0, 10, 10);

        OIL_LAKE = registerConfiguredLake(CGBlockInit.OIL.get(), 14, 34, 34);

        METEORITE = registerConfiguredFeature(new ResourceLocation(CGalaxy.MODID, "meteorite").toString(), CGFeatureInit.METEORITE.get(),
                new BlockStateConfiguration(CGBlockInit.METEORITE.get().defaultBlockState()),
                CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(10), BiomeFilter.biome(), InSquarePlacement.spread());

        SAPPHIRE_METEORITE = registerConfiguredFeature(new ResourceLocation(CGalaxy.MODID, "astral_sapphire_meteorite").toString(), CGFeatureInit.METEORITE.get(),
                new BlockStateConfiguration(CGBlockInit.ASTRAL_SAPPHIRE_ORE.get().defaultBlockState()),
                CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(100), BiomeFilter.biome(), InSquarePlacement.spread());

        METEORITE_OVERWORLD = registerConfiguredFeature(new ResourceLocation(CGalaxy.MODID, "meteorite_overworld").toString(), CGFeatureInit.METEORITE.get(),
                new BlockStateConfiguration(CGBlockInit.METEORITE.get().defaultBlockState()),
                CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(100), BiomeFilter.biome(), InSquarePlacement.spread());
    }

    @SubscribeEvent
    public static void setupOreGen(BiomeLoadingEvent e) {
        if (isOverworld(e)) {
            e.getGeneration().addFeature(GenerationStep.Decoration.LAKES, OIL_LAKE);
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, METEORITE_OVERWORLD);
        }
        if (isBiome(e, CGBiomes.THE_MOON) || isBiome(e, CGBiomes.THE_MOON_SOUTH)) {
            addOre(e, MOON_IRON, MOON_SILICON, MOON_ALUMINUM, MOON_GLOWSTONE);

            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, METEORITE);
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, SAPPHIRE_METEORITE);
        }
        if (isBiome(e, CGBiomes.THE_MOON_SOUTH)) {
            addOre(e, MOON_ICE_CLUSTER, MOON_CHEESE_CLUSTER);
        }
    }
}