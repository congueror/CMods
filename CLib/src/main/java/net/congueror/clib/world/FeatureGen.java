package net.congueror.clib.world;

import net.congueror.clib.CLib;
import net.congueror.clib.init.CLBlockInit;
import net.congueror.clib.init.CLMaterialInit;
import net.minecraft.core.Holder;
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
import static net.congueror.clib.util.CLConfig.*;

@Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FeatureGen {
    public static Holder<ConfiguredFeature<?, ?>> RUBBER_TREE;
    public static Holder<PlacedFeature> RUBBER_TREE_PLACEMENT;

    public static Holder<PlacedFeature> TIN;
    public static Holder<PlacedFeature> ALUMINUM;
    public static Holder<PlacedFeature> LEAD;
    public static Holder<PlacedFeature> RUBY;
    public static Holder<PlacedFeature> SILVER;
    public static Holder<PlacedFeature> NICKEL;
    public static Holder<PlacedFeature> PLATINUM;
    public static Holder<PlacedFeature> TUNGSTEN;
    public static Holder<PlacedFeature> OPAL;
    public static Holder<PlacedFeature> TITANIUM;
    public static Holder<PlacedFeature> URANIUM;
    public static Holder<PlacedFeature> COBALT;
    public static Holder<PlacedFeature> ZINC;
    public static Holder<PlacedFeature> CHROMIUM;
    public static Holder<PlacedFeature> THORIUM;
    public static Holder<PlacedFeature> SALTPETRE;
    public static Holder<PlacedFeature> SULFUR;

    public static Holder<PlacedFeature> SAPPHIRE_GEODE;

    public static Holder<PlacedFeature> SALT;

    public static void registerFeatures() {
        RUBBER_TREE = registerTree(CLib.MODID, "rubber_tree", new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(CLBlockInit.RUBBER_LOG.get().defaultBlockState()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(CLBlockInit.RUBBER_LEAVES.get().defaultBlockState()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());
        RUBBER_TREE_PLACEMENT = registerTreePlacement(CLib.MODID, "rubber_tree", RUBBER_TREE, CLBlockInit.RUBBER_SAPLING.get(), RUBBER_TREE_RARITY.get());

        TIN = registerConfiguredOre(CLMaterialInit.TIN.ore().get(), CLMaterialInit.TIN.deepslate().get(), TIN_VEIN.get(), TIN_MIN.get(), TIN_MAX.get(), TIN_COUNT.get());
        ALUMINUM = registerConfiguredOre(CLMaterialInit.ALUMINUM.ore().get(), CLMaterialInit.ALUMINUM.deepslate().get(), ALUMINUM_VEIN.get(), ALUMINUM_MIN.get(), ALUMINUM_MAX.get(), ALUMINUM_COUNT.get());
        LEAD = registerConfiguredOre(CLMaterialInit.LEAD.ore().get(), CLMaterialInit.LEAD.deepslate().get(), 7, -24, 31, 4);
        RUBY = registerConfiguredOre(nether, CLMaterialInit.RUBY.ore().get(), 3, 0, 18, 1);
        SILVER = registerConfiguredOre(CLMaterialInit.SILVER.ore().get(), CLMaterialInit.SILVER.deepslate().get(), 0, -24, 16, 3);
        NICKEL = registerConfiguredOre(CLMaterialInit.NICKEL.ore().get(), CLMaterialInit.NICKEL.deepslate().get(), 7, -24, 31, 4);
        PLATINUM = registerConfiguredOre(CLMaterialInit.PLATINUM.ore().get(), CLMaterialInit.PLATINUM.deepslate().get(), 6, -24, 8, 1);
        TUNGSTEN = registerConfiguredOre(CLMaterialInit.TUNGSTEN.ore().get(), CLMaterialInit.TUNGSTEN.deepslate().get(), 6, -24, 16, 2);
        OPAL = registerConfiguredOre(end, CLMaterialInit.OPAL.ore().get(), 4, 0, 30, 1);
        TITANIUM = registerConfiguredOre(nether, CLMaterialInit.TITANIUM.ore().get(), 7, 0, 7, 1);
        URANIUM = registerConfiguredOre(CLMaterialInit.URANIUM.ore().get(), CLMaterialInit.URANIUM.deepslate().get(), 7, -24, 16, 1);
        COBALT = registerConfiguredOre(nether, CLMaterialInit.COBALT.ore().get(), 7, 0, 32, 1);
        ZINC = registerConfiguredOre(CLMaterialInit.ZINC.ore().get(), CLMaterialInit.ZINC.deepslate().get(), 7, -24, 32, 8);
        CHROMIUM = registerConfiguredOre(CLMaterialInit.CHROMIUM.ore().get(), CLMaterialInit.CHROMIUM.deepslate().get(), 7, -24, 22, 6);
        THORIUM = registerConfiguredOre(CLMaterialInit.THORIUM.ore().get(), CLMaterialInit.THORIUM.deepslate().get(), 7, -24, 8, 1);


        SALTPETRE = registerConfiguredOre(overworld, CLBlockInit.SALTPETRE_ORE.get(), 3, 0, 100, 1);
        SULFUR = registerConfiguredOre(nether, CLBlockInit.SULFUR_ORE.get(), 5, 0, 100, 4);

        SAPPHIRE_GEODE = registerConfiguredGeode(
                CLMaterialInit.SAPPHIRE.block().get(),
                CLMaterialInit.SAPPHIRE.budding().get(),
                CLMaterialInit.SAPPHIRE.small_bud().get(),
                CLMaterialInit.SAPPHIRE.medium_bud().get(),
                CLMaterialInit.SAPPHIRE.large_bud().get(),
                CLMaterialInit.SAPPHIRE.cluster().get(),
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
