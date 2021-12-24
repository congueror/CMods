package net.congueror.clib.world;

import net.congueror.clib.CLib;
import net.congueror.clib.init.CLBlockInit;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.congueror.clib.world.WorldHelper.*;

@Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FeatureGen {
    public static ConfiguredFeature<TreeConfiguration, ?> RUBBER_TREE;
    public static PlacedFeature RUBBER_TREE_PLACEMENT;

    public static PlacedFeature TIN;
    public static PlacedFeature ALUMINUM;
    public static PlacedFeature LEAD;
    public static PlacedFeature RUBY;
    public static PlacedFeature SILVER;
    public static PlacedFeature NICKEL;
    public static PlacedFeature PLATINUM;
    public static PlacedFeature TUNGSTEN;
    public static PlacedFeature OPAL;
    public static PlacedFeature TITANIUM;
    public static PlacedFeature URANIUM;
    public static PlacedFeature COBALT;
    public static PlacedFeature ZINC;
    public static PlacedFeature CHROMIUM;
    public static PlacedFeature THORIUM;
    public static PlacedFeature SALTPETRE;
    public static PlacedFeature SULFUR;

    public static PlacedFeature SAPPHIRE_GEODE;

    public static PlacedFeature SALT;

    public static void registerFeatures() {
        RUBBER_TREE = registerTree(CLib.MODID, "rubber_tree", new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(CLBlockInit.RUBBER_LOG.get().defaultBlockState()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(CLBlockInit.RUBBER_LEAVES.get().defaultBlockState()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        RUBBER_TREE_PLACEMENT = registerTreePlacement(CLib.MODID, "rubber_tree", RUBBER_TREE, CLBlockInit.RUBBER_SAPLING.get(), 2);

        TIN = registerConfiguredOre(overworld, CLBlockInit.TIN_ORE.get(), 8, 0, 63, 25);
        ALUMINUM = registerConfiguredOre(overworld, CLBlockInit.ALUMINUM_ORE.get(), 7, 0, 63, 8);
        LEAD = registerConfiguredOre(overworld, CLBlockInit.LEAD_ORE.get(), 7, 0, 31, 4);
        RUBY = registerConfiguredOre(nether, CLBlockInit.RUBY_ORE.get(), 3, 0, 18, 1);
        SILVER = registerConfiguredOre(overworld, CLBlockInit.SILVER_ORE.get(), 0, 7, 16, 3);
        NICKEL = registerConfiguredOre(overworld, CLBlockInit.NICKEL_ORE.get(), 7, 0, 31, 4);
        PLATINUM = registerConfiguredOre(overworld, CLBlockInit.PLATINUM_ORE.get(), 6, 0, 8, 1);
        TUNGSTEN = registerConfiguredOre(overworld, CLBlockInit.TUNGSTEN_ORE.get(), 6, 0, 16, 2);
        OPAL = registerConfiguredOre(end, CLBlockInit.OPAL_ORE.get(), 4, 0, 30, 1);
        TITANIUM = registerConfiguredOre(nether, CLBlockInit.TITANIUM_ORE.get(), 7, 0, 7, 1);
        URANIUM = registerConfiguredOre(overworld, CLBlockInit.URANIUM_ORE.get(), 7, 0, 16, 1);
        COBALT = registerConfiguredOre(nether, CLBlockInit.COBALT_ORE.get(), 7, 0, 32, 1);
        ZINC = registerConfiguredOre(overworld, CLBlockInit.ZINC_ORE.get(), 7, 0, 32, 8);
        CHROMIUM = registerConfiguredOre(overworld, CLBlockInit.CHROMIUM_ORE.get(), 7, 0, 22, 6);
        THORIUM = registerConfiguredOre(overworld, CLBlockInit.THORIUM_ORE.get(), 7, 0, 8, 1);
        SALTPETRE = registerConfiguredOre(overworld, CLBlockInit.SALTPETRE_ORE.get(), 3, 0, 100, 1);
        SULFUR = registerConfiguredOre(nether, CLBlockInit.SULFUR_ORE.get(), 5, 0, 100, 4);

        SAPPHIRE_GEODE = registerConfiguredGeode(CLBlockInit.SAPPHIRE_BLOCK.get(), CLBlockInit.SAPPHIRE_BUDDING.get(), CLBlockInit.SAPPHIRE_SMALL_BUD.get(), CLBlockInit.SAPPHIRE_MEDIUM_BUD.get(), CLBlockInit.SAPPHIRE_LARGE_BUD.get(), CLBlockInit.SAPPHIRE_CLUSTER.get(), 46);

        SALT = registerConfiguredDisk(CLBlockInit.SALT_BLOCK.get());
    }

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent e) {
        if (isOverworld(e)) {
            if (isBiome(e, Biomes.FOREST.location())) {
                addVegetalDecor(e, RUBBER_TREE_PLACEMENT);
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
