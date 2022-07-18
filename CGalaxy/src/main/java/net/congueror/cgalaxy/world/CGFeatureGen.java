package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.init.CGFeatureInit;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
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
    public static Holder<PlacedFeature> MOON_ALUMINUM;
    public static Holder<PlacedFeature> MOON_SILICON;
    public static Holder<PlacedFeature> MOON_GLOWSTONE;
    public static Holder<PlacedFeature> MOON_SOUTH_ICE_CLUSTER;
    public static Holder<PlacedFeature> MOON_SOUTH_CHEESE_CLUSTER;

    public static Holder<PlacedFeature> MARS_ANDESITE;
    public static Holder<PlacedFeature> MARS_BASALT;
    public static Holder<PlacedFeature> MARS_BASALT_SOIL;
    public static Holder<PlacedFeature> MARS_BASALT_ANDESITE;
    public static Holder<PlacedFeature> MARS_SOUTH_ICE;

    public static Holder<PlacedFeature> OIL_LAKE;
    public static Holder<PlacedFeature> METEORITE;
    public static Holder<PlacedFeature> SAPPHIRE_METEORITE;
    public static Holder<PlacedFeature> METEORITE_OVERWORLD;

    public static MobSpawnSettings.SpawnerData ASTRO_ZOMBIE;
    public static MobSpawnSettings.SpawnerData ASTRO_ENDERMAN;
    public static MobSpawnSettings.SpawnerData MARTIAN_SKELETON;

    public static void registerFeatures() {
        RuleTest moon = blockRuleTest(CGBlockInit.MOON_STONE.get());
        MOON_IRON = registerConfiguredOre(moon, CGBlockInit.MOON_IRON_ORE.get(), 8, 0, 78, 12);
        MOON_ALUMINUM = registerConfiguredOre(moon, CGBlockInit.MOON_ALUMINUM_ORE.get(), 7, 0, 78, 10);
        MOON_SILICON = registerConfiguredOre(moon, CGBlockInit.MOON_SILICON_ORE.get(), 4, 0, 78, 8);
        MOON_GLOWSTONE = registerConfiguredOre(moon, CGBlockInit.MOON_GLOWSTONE_ORE.get(), 4, 0, 32, 10);

        MOON_SOUTH_ICE_CLUSTER = registerConfiguredOre(moon, Blocks.ICE, 3, 50, 70, 4);
        MOON_SOUTH_CHEESE_CLUSTER = registerConfiguredOre(moon, CGBlockInit.MOON_CHEESE.get(), 5, 0, 10, 10);

        RuleTest mars = blockRuleTest(CGBlockInit.MARTIAN_STONE.get());
        MARS_ANDESITE = registerConfiguredOre(List.of(
                OreConfiguration.target(mars, Blocks.ANDESITE.defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.ANDESITE.defaultBlockState())), CGalaxy.location("mars_andesite"),
                21, -43, 80, 10);
        MARS_BASALT = registerConfiguredOre(List.of(
                        OreConfiguration.target(mars, Blocks.BASALT.defaultBlockState()),
                        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.BASALT.defaultBlockState())), CGalaxy.location("mars_basalt"),
                21, -43, 80, 10);
        MARS_BASALT_SOIL = registerConfiguredOre(blockRuleTest(Blocks.BASALT), CGBlockInit.MARTIAN_SOIL.get(), 48, 60, 100, 10);
        MARS_BASALT_ANDESITE = registerConfiguredOre(blockRuleTest(Blocks.BASALT), Blocks.ANDESITE, 32, 60, 100, 10);
        MARS_SOUTH_ICE = registerConfiguredFeature(CGalaxy.location("mars_dry_ice"), CGFeatureInit.DRY_ICE.get(),
                new NoneFeatureConfiguration(), BiomeFilter.biome());

        OIL_LAKE = registerConfiguredLake(CGBlockInit.OIL.get(), 14, 34, 34);

        METEORITE = registerConfiguredFeature(new ResourceLocation(CGalaxy.MODID, "meteorite"), CGFeatureInit.METEORITE.get(),
                new BlockStateConfiguration(CGBlockInit.METEORITE.get().defaultBlockState()),
                CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(10), BiomeFilter.biome(), InSquarePlacement.spread());

        SAPPHIRE_METEORITE = registerConfiguredFeature(new ResourceLocation(CGalaxy.MODID, "astral_sapphire_meteorite"), CGFeatureInit.METEORITE.get(),
                new BlockStateConfiguration(CGBlockInit.ASTRAL_SAPPHIRE_ORE.get().defaultBlockState()),
                CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(300), BiomeFilter.biome(), InSquarePlacement.spread());

        METEORITE_OVERWORLD = registerConfiguredFeature(new ResourceLocation(CGalaxy.MODID, "meteorite_overworld"), CGFeatureInit.METEORITE.get(),
                new BlockStateConfiguration(CGBlockInit.METEORITE.get().defaultBlockState()),
                CountPlacement.of(1), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE), RarityFilter.onAverageOnceEvery(100), BiomeFilter.biome(), InSquarePlacement.spread());

        ASTRO_ZOMBIE = new MobSpawnSettings.SpawnerData(CGEntityTypeInit.ASTRO_ZOMBIE.get(), 1, 0, 1);
        ASTRO_ENDERMAN = new MobSpawnSettings.SpawnerData(CGEntityTypeInit.ASTRO_ENDERMAN.get(), 1, 0, 1);
        MARTIAN_SKELETON = new MobSpawnSettings.SpawnerData(CGEntityTypeInit.MARTIAN_SKELETON.get(), 1, 0, 1);
    }

    @SubscribeEvent
    public static void setupOreGen(BiomeLoadingEvent e) {
        if (isOverworld(e)) {
            e.getGeneration().addFeature(GenerationStep.Decoration.LAKES, OIL_LAKE);
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, METEORITE_OVERWORLD);
        }
        if (isBiome(e, CGBiomes.THE_MOON) || isBiome(e, CGBiomes.THE_MOON_SOUTH)) {
            addOre(e, MOON_IRON, MOON_ALUMINUM, MOON_SILICON, MOON_GLOWSTONE);
            addMonster(e, ASTRO_ZOMBIE, ASTRO_ENDERMAN);

            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, METEORITE);
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, SAPPHIRE_METEORITE);
        }
        if (isBiome(e, CGBiomes.THE_MOON_SOUTH)) {
            addOre(e, MOON_SOUTH_ICE_CLUSTER, MOON_SOUTH_CHEESE_CLUSTER);
        }
        if (isBiome(e, CGBiomes.MARS) || isBiome(e, CGBiomes.MARS_BASALT) || isBiome(e, CGBiomes.MARS_SOUTH)) {
            addOre(e, MARS_ANDESITE, MARS_BASALT);
            addMonster(e, ASTRO_ZOMBIE, ASTRO_ENDERMAN, MARTIAN_SKELETON);
        }
        if (isBiome(e, CGBiomes.MARS_BASALT)) {
            addOre(e, MARS_BASALT_SOIL, MARS_BASALT_ANDESITE);
        }
        if (isBiome(e, CGBiomes.MARS_SOUTH)) {
            e.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MARS_SOUTH_ICE);
        }
    }
}