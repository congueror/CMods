package net.congueror.clib.world;

import net.congueror.clib.CLib;
import net.congueror.clib.init.CLBlockInit;
import net.congueror.clib.init.CLMaterialInit;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
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
        RUBBER_TREE_PLACEMENT = registerTreePlacement(CLib.MODID, "rubber_tree", RUBBER_TREE, CLBlockInit.RUBBER_SAPLING.get(), 100);

        TIN = registerConfiguredOre(CLMaterialInit.TIN.getOre().get(), CLMaterialInit.TIN.getDeepslateOre().get(), 8, -24, 56, 25);
        ALUMINUM = registerConfiguredOre(CLMaterialInit.ALUMINUM.getOre().get(), CLMaterialInit.ALUMINUM.getDeepslateOre().get(), 7, -24, 63, 8);
        LEAD = registerConfiguredOre(CLMaterialInit.LEAD.getOre().get(), CLMaterialInit.LEAD.getDeepslateOre().get(), 7, -24, 31, 4);
        RUBY = registerConfiguredOre(nether, CLMaterialInit.RUBY.getOre().get(), 3, 0, 18, 1);
        SILVER = registerConfiguredOre(CLMaterialInit.SILVER.getOre().get(), CLMaterialInit.SILVER.getDeepslateOre().get(), 0, -24, 16, 3);
        NICKEL = registerConfiguredOre(CLMaterialInit.NICKEL.getOre().get(), CLMaterialInit.NICKEL.getDeepslateOre().get(), 7, -24, 31, 4);
        PLATINUM = registerConfiguredOre(CLMaterialInit.PLATINUM.getOre().get(), CLMaterialInit.PLATINUM.getDeepslateOre().get(), 6, -24, 8, 1);
        TUNGSTEN = registerConfiguredOre(CLMaterialInit.TUNGSTEN.getOre().get(), CLMaterialInit.TUNGSTEN.getDeepslateOre().get(), 6, -24, 16, 2);
        OPAL = registerConfiguredOre(end, CLMaterialInit.OPAL.getOre().get(), 4, 0, 30, 1);
        TITANIUM = registerConfiguredOre(nether, CLMaterialInit.TITANIUM.getOre().get(), 7, 0, 7, 1);
        URANIUM = registerConfiguredOre(CLMaterialInit.URANIUM.getOre().get(), CLMaterialInit.URANIUM.getDeepslateOre().get(), 7, -24, 16, 1);
        COBALT = registerConfiguredOre(nether, CLMaterialInit.COBALT.getOre().get(), 7, 0, 32, 1);
        ZINC = registerConfiguredOre(CLMaterialInit.ZINC.getOre().get(), CLMaterialInit.ZINC.getDeepslateOre().get(), 7, -24, 32, 8);
        CHROMIUM = registerConfiguredOre(CLMaterialInit.CHROMIUM.getOre().get(), CLMaterialInit.CHROMIUM.getDeepslateOre().get(), 7, -24, 22, 6);
        THORIUM = registerConfiguredOre(CLMaterialInit.THORIUM.getOre().get(), CLMaterialInit.THORIUM.getDeepslateOre().get(), 7, -24, 8, 1);


        SALTPETRE = registerConfiguredOre(overworld, CLBlockInit.SALTPETRE_ORE.get(), 3, 0, 100, 1);
        SULFUR = registerConfiguredOre(nether, CLBlockInit.SULFUR_ORE.get(), 5, 0, 100, 4);

        SAPPHIRE_GEODE = registerConfiguredGeode(
                CLMaterialInit.SAPPHIRE.getBlock().get(),
                CLMaterialInit.SAPPHIRE.getBudding().get(),
                CLMaterialInit.SAPPHIRE.getSmallBud().get(),
                CLMaterialInit.SAPPHIRE.getMediumBud().get(),
                CLMaterialInit.SAPPHIRE.getLargeBud().get(),
                CLMaterialInit.SAPPHIRE.getCluster().get(),
                46);

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
