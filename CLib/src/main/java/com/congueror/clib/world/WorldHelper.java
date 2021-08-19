package com.congueror.clib.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.ITag;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class WorldHelper {
    public static final RuleTest overworld = OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD;
    public static final RuleTest nether = OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER;
    public static final RuleTest end = new TagMatchRuleTest(Tags.Blocks.END_STONES);

    /**
     * A new Rule Test for blocks to use in ore generation
     * @param block The Block to test
     * @return a new block rule test
     */
    public static RuleTest blockRuleTest(Block block) {
        return new BlockMatchRuleTest(block);
    }

    /**
     * A new Rule Test for block tags to use in ore generation
     * @param tag The block tag to test
     * @return a new block tag rule test
     */
    public static RuleTest tagRuleTest(ITag<Block> tag) {
        return new TagMatchRuleTest(tag);
    }

    /**
     * Used to register a dimension world key to the {@link Registry}.
     * @param modid Your mod id
     * @param name The id of the dimension
     * @return The registry key
     */
    public static RegistryKey<World> registerDim(String modid, String name) {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(modid, name));
    }

    /**
     * Used to register a configured tree feature to the {@link WorldGenRegistries}
     * @param modid Your mod id
     * @param name The id of your feature
     * @param feature The Tree Feature
     */
    public static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> registerTree(String modid, String name, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), feature);
    }

    /**
     * Used to register a configured ore feature to the {@link WorldGenRegistries}
     * @param filler The block that will be replaced by the ore. See {@link #blockRuleTest(Block)} or {@link #tagRuleTest(ITag)}
     * @param block The ore block
     * @param veinSize The maximum amount of blocks that will be adjacent to the ore. Must be greater than 1 for some reason
     * @param maxHeight The maximum y value that the ore can spawn at. (Minimum is always 0)
     * @param count How many attempts it will make to spawn the ore each chunk.
     */
    public static ConfiguredFeature<?, ?> registerConfiguredOre(RuleTest filler, Block block, int veinSize, int maxHeight, int count) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, block.getRegistryName(), Feature.ORE.withConfiguration(
                new OreFeatureConfig(filler, block.getDefaultState(), veinSize)).range(maxHeight).square().count(count));
    }

    /**
     * Used to register a configured disk feature to the {@link WorldGenRegistries}. (Similar to clay)
     * @param block The disk block
     */
    public static ConfiguredFeature<?, ?> registerConfiguredDisk(Block block) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, block.getRegistryName(), Feature.DISK.withConfiguration(
                new SphereReplaceConfig(block.getDefaultState(), FeatureSpread.create(2, 1), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(), block.getDefaultState())))
                .withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    }

    /**
     * Used to register a configured lake feature to the {@link WorldGenRegistries}.
     * @param fluid The fluid's block you want the lake to be filled with.
     * @param maxHeight The maximum height the lake will spawn at.
     * @param chance The chance that this lake has to spawn. The greater the number the more rare it is.
     */
    public static ConfiguredFeature<?, ?> registerConfiguredLake(Block fluid, int maxHeight, int chance) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, fluid.getRegistryName() + "_lake", Feature.LAKE.withConfiguration(
                new BlockStateFeatureConfig(fluid.getDefaultState())
        ).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(chance))).range(maxHeight).square());
    }

    /**
     * Generates the configured feature in the world. Use in a {@link BiomeLoadingEvent}.
     * @param e The biome loading event
     * @param feature The configured feature to be generated.
     */
    public static void genOre(BiomeLoadingEvent e, ConfiguredFeature<?, ?> feature) {
        e.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, feature);
    }

    /**
     * Checks whether the event is in the overworld. Use inside a {@link BiomeLoadingEvent}
     * @param e The biome loading event
     * @return True if overworld
     */
    public static boolean isOverworld(BiomeLoadingEvent e) {
        return !e.getCategory().equals(Biome.Category.NETHER) && !e.getCategory().equals(Biome.Category.THEEND) && !e.getCategory().equals(Biome.Category.NONE);
    }

    /**
     * Checks whether the event is in the nether. Use inside a {@link BiomeLoadingEvent}
     * @param e The biome loading event
     * @return True if nether
     */
    public static boolean isNether(BiomeLoadingEvent e) {
        return e.getCategory().equals(Biome.Category.NETHER);
    }

    /**
     * Checks whether the event is in the end. Use inside a {@link BiomeLoadingEvent}
     * @param e The biome loading event
     * @return True if the end
     */
    public static boolean isEnd(BiomeLoadingEvent e) {
        return e.getCategory().equals(Biome.Category.THEEND);
    }

    /**
     * Checks whether the event is in the given biome. Use inside a {@link BiomeLoadingEvent}
     * @param e The biome loading event
     * @param name The resource location of the custom biome.
     * @return True if given biome.
     */
    public static boolean isBiome(BiomeLoadingEvent e, ResourceLocation name) {
        if (e.getName() != null) {
            if (e.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
