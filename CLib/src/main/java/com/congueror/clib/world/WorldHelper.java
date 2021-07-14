package com.congueror.clib.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldHelper {
    public static final RuleTest overworld = OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD;
    public static final RuleTest nether = OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER;
    public static final RuleTest end = new TagMatchRuleTest(Tags.Blocks.END_STONES);

    public static RegistryKey<World> registerDim(String modid, String name) {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(modid, name));
    }

    public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerTree(String modid, String name, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), feature);
    }

    public static ConfiguredFeature<?, ?> registerConfiguredOre(RuleTest filler, Block block, int veinSize, int maxHeight, int count) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, block.getRegistryName(), Feature.ORE.withConfiguration(
                new OreFeatureConfig(filler, block.getDefaultState(), veinSize)).range(maxHeight).square().count(count));
    }

    public static ConfiguredFeature<?, ?> registerConfiguredDisk(Block block) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, block.getRegistryName(), Feature.DISK.withConfiguration(
                new SphereReplaceConfig(block.getDefaultState(), FeatureSpread.create(2, 1), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), block.getDefaultState())))
                .withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    }

    public static void genore(BiomeLoadingEvent e, ConfiguredFeature<?, ?> feature) {
        e.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
    }

    public static boolean isOverworld(BiomeLoadingEvent e) {
        return !e.getCategory().equals(Biome.Category.NETHER) && !e.getCategory().equals(Biome.Category.THEEND);
    }

    public static boolean isNether(BiomeLoadingEvent e) {
        return e.getCategory().equals(Biome.Category.NETHER);
    }

    public static boolean isEnd(BiomeLoadingEvent e) {
        return e.getCategory().equals(Biome.Category.THEEND);
    }
}
