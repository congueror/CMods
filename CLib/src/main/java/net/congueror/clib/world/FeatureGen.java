package net.congueror.clib.world;

import net.congueror.clib.CLib;
import net.congueror.clib.init.CLBlockInit;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.congueror.clib.world.WorldHelper.*;

@Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FeatureGen {
    public static ConfiguredFeature<TreeConfiguration, ?> RUBBER_TREE;

    public static ConfiguredFeature<?, ?> TIN;
    public static ConfiguredFeature<?, ?> ALUMINUM;
    public static ConfiguredFeature<?, ?> LEAD;
    public static ConfiguredFeature<?, ?> RUBY;
    public static ConfiguredFeature<?, ?> SILVER;
    public static ConfiguredFeature<?, ?> NICKEL;
    public static ConfiguredFeature<?, ?> PLATINUM;
    public static ConfiguredFeature<?, ?> TUNGSTEN;
    public static ConfiguredFeature<?, ?> OPAL;
    public static ConfiguredFeature<?, ?> TITANIUM;
    public static ConfiguredFeature<?, ?> URANIUM;
    public static ConfiguredFeature<?, ?> COBALT;
    public static ConfiguredFeature<?, ?> ZINC;
    public static ConfiguredFeature<?, ?> CHROMIUM;
    public static ConfiguredFeature<?, ?> THORIUM;
    public static ConfiguredFeature<?, ?> SALTPETRE;
    public static ConfiguredFeature<?, ?> SULFUR;

    public static ConfiguredFeature<?, ?> SAPPHIRE_GEODE;

    public static ConfiguredFeature<?, ?> SALT;

    public static void registerFeatures() {
        RUBBER_TREE = registerTree(CLib.MODID, "rubber_tree", Feature.TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(
                new SimpleStateProvider(CLBlockInit.RUBBER_LOG.get().defaultBlockState()),
                new StraightTrunkPlacer(4, 2, 0),
                new SimpleStateProvider(CLBlockInit.RUBBER_LEAVES.get().defaultBlockState()),
                new SimpleStateProvider(CLBlockInit.RUBBER_SAPLING.get().defaultBlockState()),
                new FancyFoliagePlacer(UniformInt.of(2, 2), UniformInt.of(0, 0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()));

        TIN = registerConfiguredOre(overworld, CLBlockInit.TIN_ORE.get(), 8, 63, 25);
        ALUMINUM = registerConfiguredOre(overworld, CLBlockInit.ALUMINUM_ORE.get(), 7, 63, 8);
        LEAD = registerConfiguredOre(overworld, CLBlockInit.LEAD_ORE.get(), 7, 31, 4);
        RUBY = registerConfiguredOre(nether, CLBlockInit.RUBY_ORE.get(), 3, 18, 1);
        SILVER = registerConfiguredOre(overworld, CLBlockInit.SILVER_ORE.get(), 7, 16, 3);
        NICKEL = registerConfiguredOre(overworld, CLBlockInit.NICKEL_ORE.get(), 7, 31, 4);
        PLATINUM = registerConfiguredOre(overworld, CLBlockInit.PLATINUM_ORE.get(), 6, 8, 1);
        TUNGSTEN = registerConfiguredOre(overworld, CLBlockInit.TUNGSTEN_ORE.get(), 6, 16, 2);
        OPAL = registerConfiguredOre(WorldHelper.end, CLBlockInit.OPAL_ORE.get(), 4, 30, 1);
        TITANIUM = registerConfiguredOre(nether, CLBlockInit.TITANIUM_ORE.get(), 7, 7, 1);
        URANIUM = registerConfiguredOre(overworld, CLBlockInit.URANIUM_ORE.get(), 7, 16, 1);
        COBALT = registerConfiguredOre(nether, CLBlockInit.COBALT_ORE.get(), 7, 32, 1);
        ZINC = registerConfiguredOre(overworld, CLBlockInit.ZINC_ORE.get(), 7, 32, 8);
        CHROMIUM = registerConfiguredOre(overworld, CLBlockInit.CHROMIUM_ORE.get(), 7, 22, 6);
        THORIUM = registerConfiguredOre(overworld, CLBlockInit.THORIUM_ORE.get(), 7, 8, 1);
        SALTPETRE = registerConfiguredOre(overworld, CLBlockInit.SALTPETRE_ORE.get(), 3, 100, 1);
        SULFUR = registerConfiguredOre(nether, CLBlockInit.SULFUR_ORE.get(), 5, 100, 4);

        SAPPHIRE_GEODE = registerConfiguredGeode(CLBlockInit.SAPPHIRE_BLOCK.get(), CLBlockInit.SAPPHIRE_BUDDING.get(), CLBlockInit.SAPPHIRE_SMALL_BUD.get(), CLBlockInit.SAPPHIRE_MEDIUM_BUD.get(), CLBlockInit.SAPPHIRE_LARGE_BUD.get(), CLBlockInit.SAPPHIRE_CLUSTER.get(), 46, 49);

        SALT = registerConfiguredDisk(CLBlockInit.SALT_BLOCK.get());
    }

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent e) {
        if (isOverworld(e)) {
            if (isBiome(e, Biomes.FOREST.getRegistryName())) {
                addVegetalDecor(e, RUBBER_TREE, 10);
            }
            addOre(e,
                    TIN,
                    ALUMINUM,
                    LEAD,
                    SILVER,
                    NICKEL,
                    PLATINUM,
                    TUNGSTEN,
                    URANIUM,
                    ZINC,
                    CHROMIUM,
                    THORIUM,
                    SALTPETRE,
                    SALT);
            addLocalMod(e, SAPPHIRE_GEODE);
        }
        if (isNether(e)) {
            addOre(e,
                    RUBY,
                    TITANIUM,
                    COBALT);
        }
        if (isEnd(e)) {
            addOre(e, OPAL);
        }
    }
}
