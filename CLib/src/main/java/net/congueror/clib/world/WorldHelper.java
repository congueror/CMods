package net.congueror.clib.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Objects;

public class WorldHelper {

    public WorldHelper() throws IllegalAccessException {
        throw new IllegalAccessException("Utility Class");
    }

    public static final RuleTest overworld = OreConfiguration.Predicates.NATURAL_STONE;
    public static final RuleTest nether = OreConfiguration.Predicates.NETHER_ORE_REPLACEABLES;
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
     * Used to register a dimension world key to the {@link Registry}.
     *
     * @param modid Your mod id
     * @param name  The id of the dimension
     * @return The registry key
     */
    public static ResourceKey<Level> registerDim(String modid, String name) {
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(modid, name));
    }

    /**
     * Used to register a configured tree feature to the {@link BuiltinRegistries}
     *
     * @param modid   Your mod id
     * @param name    The id of your feature
     * @param feature The Tree Feature
     */
    public static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> registerTree(String modid, String name, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(modid, name), feature);
    }

    /**
     * Used to register a configured ore feature to the {@link BuiltinRegistries}
     *
     * @param filler    The block that will be replaced by the ore. See {@link #blockRuleTest(Block)} or {@link #tagRuleTest(Tag)}
     * @param block     The ore block
     * @param veinSize  The maximum amount of blocks that will be adjacent to the ore. Must be greater than 1 for some reason
     * @param maxHeight The maximum y value that the ore can spawn at. (Minimum is always 0)
     * @param count     How many attempts it will make to spawn the ore each chunk.
     */
    public static ConfiguredFeature<?, ?> registerConfiguredOre(RuleTest filler, Block block, int veinSize, int maxHeight, int count) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Feature.ORE.configured(
                        new OreConfiguration(filler, block.defaultBlockState(), veinSize))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(maxHeight)).squared().count(count));
    }

    /**
     * Used to register a configured disk feature to the {@link BuiltinRegistries}. (Similar to clay)
     *
     * @param block The disk block
     */
    public static ConfiguredFeature<?, ?> registerConfiguredDisk(Block block) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(block.getRegistryName()), Feature.DISK.configured(
                        new DiskConfiguration(block.defaultBlockState(), UniformInt.of(2, 3), 1, ImmutableList.of(Blocks.DIRT.defaultBlockState(), block.defaultBlockState())))
                .decorated(Features.Decorators.TOP_SOLID_HEIGHTMAP_SQUARE));
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
     * @param maxY    Maximum Y value this feature will generate at.
     * @param rarity  The rarity of this geode. The greater the number the more rare it is.
     */
    public static ConfiguredFeature<?, ?> registerConfiguredGeode(Block block, Block budding, Block small, Block medium, Block large, Block cluster, int maxY, int rarity) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, block.getRegistryName() + "_geode", Feature.GEODE.configured(
                new GeodeConfiguration(new GeodeBlockSettings(new SimpleStateProvider(Blocks.AIR.defaultBlockState()),
                        new SimpleStateProvider(block.defaultBlockState()), new SimpleStateProvider(budding.defaultBlockState()),
                        new SimpleStateProvider(Blocks.CALCITE.defaultBlockState()), new SimpleStateProvider(Blocks.SMOOTH_BASALT.defaultBlockState()),
                        ImmutableList.of(small.defaultBlockState(), medium.defaultBlockState(), large.defaultBlockState(), cluster.defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE.getName(), BlockTags.GEODE_INVALID_BLOCKS.getName()), new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D), new GeodeCrackSettings(0.0D, 2.0D, 2), 0.35D, 0.083D, true, UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2), -16, 16, 0.05D, 1)).rangeUniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(maxY)).squared().rarity(rarity));

    }

    /**
     * Used to register a configured lake feature to the {@link BuiltinRegistries}.
     *
     * @param fluid     The fluid's block you want the lake to be filled with.
     * @param maxHeight The maximum height the lake will spawn at.
     * @param chance    The chance that this lake has to spawn. The greater the number the more rare it is.
     */
    public static ConfiguredFeature<?, ?> registerConfiguredLake(Block fluid, int maxHeight, int chance) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, fluid.getRegistryName() + "_lake", Feature.LAKE.configured(
                        new BlockStateConfiguration(fluid.defaultBlockState()))
                .rarity(chance).range(new RangeDecoratorConfiguration(UniformHeight.of(VerticalAnchor.bottom(), VerticalAnchor.absolute(maxHeight)))).squared());
    }

    /**
     * Generates an ore feature in the world. Use in a {@link BiomeLoadingEvent}.
     *
     * @param e       The {@link BiomeLoadingEvent}
     * @param feature The ore configured feature to be generated.
     */
    public static void addOre(BiomeLoadingEvent e, ConfiguredFeature<?, ?>... feature) {
        for (ConfiguredFeature<?, ?> feature1 : feature) {
            e.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature1);
        }
    }

    /**
     * Generates a tree feature in the world. Use in a {@link BiomeLoadingEvent}.
     *
     * @param e       The {@link BiomeLoadingEvent}
     * @param feature A tree {@link ConfiguredFeature}
     * @param chance  The chance the tree has to generate in the world, higher the number the more rare.
     * @param <FC>    {@link TreeConfiguration}
     */
    public static <FC extends FeatureConfiguration> void addVegetalDecor(BiomeLoadingEvent e, ConfiguredFeature<FC, ?> feature, int chance) {
        e.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> feature.rarity(chance).squared());
    }

    /**
     * Generates a local modification feature in the world. Use in a {@link BiomeLoadingEvent}.
     *
     * @param e       The {@link BiomeLoadingEvent}
     * @param feature The local modification configured feature to be generated.
     */
    public static void addLocalMod(BiomeLoadingEvent e, ConfiguredFeature<?, ?>... feature) {
        for (ConfiguredFeature<?, ?> feature1 : feature) {
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
}
