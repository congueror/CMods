package net.congueror.clib.world;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;
import java.util.Objects;

public class WorldHelper {

    public WorldHelper() throws IllegalAccessException {
        throw new IllegalAccessException("Utility Class");
    }

    public static final RuleTest overworld = OreFeatures.NATURAL_STONE;
    public static final RuleTest nether = OreFeatures.NETHER_ORE_REPLACEABLES;
    public static final RuleTest end = new TagMatchTest(Tags.Blocks.END_STONES);

    /**
     * A new Rule Test for blocks to use in ore generation
     *
     * @param block The Block to test
     * @return a new block rule test
     */
    public static RuleTest blockRuleTest(Block block) {
        return new BlockMatchTest(block);
    }

    /**
     * A new Rule Test for block tags to use in ore generation
     *
     * @param tag The block tag to test
     * @return a new block tag rule test
     */
    public static RuleTest tagRuleTest(Tag<Block> tag) {
        return new TagMatchTest(tag);
    }

    /**
     * Used to register a dimension resource key to the {@link Registry}.
     *
     * @param modid Your mod id
     * @param name  The id of the dimension
     * @return The registry key
     */
    public static ResourceKey<Level> registerDim(String modid, String name) {
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(modid, name));
    }

    /**
     * Used to register a biome resource key to the {@link Registry}
     *
     * @param modid Your mod id
     * @param name  The id of the dimension
     * @return The registry key
     */
    public static ResourceKey<Biome> registerBiome(String modid, String name) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(modid, name));
    }

    /**
     * Used to register a biome source to the {@link Registry}
     *
     * @param modid Your mod id
     * @param name  The id of the biome source
     * @param codec The codec field in your biome source
     */
    public static void registerBiomeSource(String modid, String name, Codec<? extends BiomeSource> codec) {
        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(modid, name), codec);
    }

    /**
     * Used to register a configured tree feature to the {@link BuiltinRegistries}
     *
     * @param modid   Your mod's id
     * @param name    The id of your feature
     * @param feature The Tree Feature
     */
    public static ConfiguredFeature<TreeConfiguration, ?> registerTree(String modid, String name, TreeConfiguration feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), Feature.TREE.configured(feature));
    }

    /**
     * Registers a placement for the given tree feature.
     *
     * @param modid   Your mod's id.
     * @param name    The id of your placement.
     * @param feature The tree configured feature.
     * @param sapling The sapling block of your tree.
     * @param chance  The chance that this tree has to spawn.
     */
    public static PlacedFeature registerTreePlacement(String modid, String name, ConfiguredFeature<TreeConfiguration, ?> feature, Block sapling, int chance) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(modid, name),
                feature.placed(
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(sapling.defaultBlockState(), BlockPos.ZERO)),
                        CountPlacement.of(chance)));
    }

    /**
     * Used to register a configured ore feature to the {@link BuiltinRegistries}.
     *
     * @param filler    The block that will be replaced by the ore. See {@link #blockRuleTest(Block)} or {@link #tagRuleTest(Tag)}
     * @param block     The ore block
     * @param veinSize  The maximum amount of blocks that will be adjacent to the ore. Must be greater than 1 for some reason
     * @param maxHeight The maximum y value that the ore can spawn at. (Minimum is always the bottom)
     * @param count     How many attempts it will make to spawn the ore each chunk.
     */
    public static PlacedFeature registerConfiguredOre(RuleTest filler, Block block, int veinSize, int minHeight, int maxHeight, int count) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Feature.ORE.configured(
                        new OreConfiguration(filler, block.defaultBlockState(), veinSize)))
                .placed(List.of(CountPlacement.of(count), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)), BiomeFilter.biome())));
    }

    /**
     * Used to register a configured disk feature to the {@link BuiltinRegistries}. (Similar to clay)
     *
     * @param block The disk block
     */
    public static PlacedFeature registerConfiguredDisk(Block block) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Feature.DISK.configured(
                        new DiskConfiguration(block.defaultBlockState(), UniformInt.of(2, 3), 1, ImmutableList.of(Blocks.DIRT.defaultBlockState(), block.defaultBlockState()))))
                .placed(List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome())));
    }

    /**
     * Used to register a configured geode feature to the {@link BuiltinRegistries}. (Similar to amethyst)
     *
     * @param block   The main block.
     * @param budding The block the cluster will generate on
     * @param small   Small bud block
     * @param medium  Medium bud block
     * @param large   Large bud block
     * @param cluster Cluster block
     * @param rarity  The rarity of this geode. (normal = 24)
     * @return
     */
    public static PlacedFeature registerConfiguredGeode(Block block, Block budding, Block small, Block medium, Block large, Block cluster, int rarity) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, block.getRegistryName() + "_geode", Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, block.getRegistryName() + "_geode", Feature.GEODE.configured(
                        new GeodeConfiguration(new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR.defaultBlockState()),
                                BlockStateProvider.simple(block.defaultBlockState()), BlockStateProvider.simple(budding.defaultBlockState()),
                                BlockStateProvider.simple(Blocks.CALCITE.defaultBlockState()), BlockStateProvider.simple(Blocks.SMOOTH_BASALT.defaultBlockState()),
                                ImmutableList.of(small.defaultBlockState(), medium.defaultBlockState(), large.defaultBlockState(), cluster.defaultBlockState()),
                                BlockTags.FEATURES_CANNOT_REPLACE.getName(), BlockTags.GEODE_INVALID_BLOCKS.getName()), new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                                new GeodeCrackSettings(0.0D, 2.0D, 2), 0.35D, 0.083D, true,
                                UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2),
                                -16, 16, 0.05D, 1)))
                .placed(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)), BiomeFilter.biome()));
    }

    /**
     * Used to register a configured lake feature to the {@link BuiltinRegistries}.
     *
     * @param fluid     The fluid's block you want the lake to be filled with.
     * @param maxHeight The maximum height the lake will spawn at.
     * @param chance    The chance that this lake has to spawn. The greater the number the more rare it is.
     * @return
     */
    public static PlacedFeature registerConfiguredLake(Block fluid, int minHeight, int maxHeight, int chance) {
        return Registry.register(BuiltinRegistries.PLACED_FEATURE, fluid.getRegistryName() + "_lake", Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, fluid.getRegistryName() + "_lake", Feature.LAKE.configured(
                        new LakeFeature.Configuration(
                                BlockStateProvider.simple(fluid.defaultBlockState()), BlockStateProvider.simple(Blocks.STONE.defaultBlockState()))))
                .placed(RarityFilter.onAverageOnceEvery(chance),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight))),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.allOf(BlockPredicate.not(BlockPredicate.ONLY_IN_AIR_PREDICATE), BlockPredicate.insideWorld(new BlockPos(0, -5, 0))), 32),
                        SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -5), BiomeFilter.biome()));
    }

    /**
     * Generates an ore feature in the world. Use in a {@link BiomeLoadingEvent}.
     *
     * @param e       The {@link BiomeLoadingEvent}
     * @param feature The ore configured feature to be generated.
     */
    public static void addOre(BiomeLoadingEvent e, PlacedFeature... feature) {
        for (PlacedFeature feature1 : feature) {
            e.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature1);
        }
    }

    /**
     * Generates a tree feature in the world. Use in a {@link BiomeLoadingEvent}.
     *
     * @param e       The {@link BiomeLoadingEvent}
     * @param feature A tree {@link ConfiguredFeature}
     */
    public static void addVegetalDecor(BiomeLoadingEvent e, PlacedFeature... feature) {
        for (PlacedFeature feature1 : feature)
            e.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> feature1);
    }

    /**
     * Generates a local modification feature in the world. Use in a {@link BiomeLoadingEvent}.
     *
     * @param e       The {@link BiomeLoadingEvent}
     * @param feature The local modification configured feature to be generated.
     */
    public static void addLocalMod(BiomeLoadingEvent e, PlacedFeature... feature) {
        for (PlacedFeature feature1 : feature) {
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, feature1);
        }
    }

    /**
     * Checks whether the event is in the overworld. Use inside a {@link BiomeLoadingEvent}
     *
     * @param e The biome loading event
     * @return True if overworld
     */
    public static boolean isOverworld(BiomeLoadingEvent e) {
        return !e.getCategory().equals(Biome.BiomeCategory.NETHER) && !e.getCategory().equals(Biome.BiomeCategory.THEEND) && !e.getCategory().equals(Biome.BiomeCategory.NONE);
    }

    /**
     * Checks whether the event is in the nether. Use inside a {@link BiomeLoadingEvent}
     *
     * @param e The biome loading event
     * @return True if nether
     */
    public static boolean isNether(BiomeLoadingEvent e) {
        return e.getCategory().equals(Biome.BiomeCategory.NETHER);
    }

    /**
     * Checks whether the event is in the end. Use inside a {@link BiomeLoadingEvent}
     *
     * @param e The biome loading event
     * @return True if the end
     */
    public static boolean isEnd(BiomeLoadingEvent e) {
        return e.getCategory().equals(Biome.BiomeCategory.THEEND);
    }

    /**
     * Checks whether the event is in the given biome. Use inside a {@link BiomeLoadingEvent}
     *
     * @param e    The biome loading event
     * @param name The resource location of the custom biome.
     * @return True if given biome.
     */
    public static boolean isBiome(BiomeLoadingEvent e, ResourceLocation name) {
        if (e.getName() != null) {
            return e.getName().equals(name);
        }
        return false;
    }

    /**
     * Checks whether the event is in the given biome. Use inside a {@link BiomeLoadingEvent}
     *
     * @param e     The biome loading event
     * @param biome The resource key of the biome.
     * @return True if given biome.
     */
    public static boolean isBiome(BiomeLoadingEvent e, ResourceKey<Biome> biome) {
        if (e.getName() != null) {
            return e.getName().equals(biome.getRegistryName());
        }
        return false;
    }
}
