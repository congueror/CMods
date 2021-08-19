package com.congueror.cgalaxy.world;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.FeatureInit;
import com.congueror.clib.world.WorldHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import static com.congueror.clib.world.WorldHelper.*;

@Mod.EventBusSubscriber(modid = CGalaxy.MODID)
public class FeatureGen {

    public static ConfiguredFeature<?, ?> MOON_IRON;
    public static ConfiguredFeature<?, ?> MOON_SILICON;
    public static ConfiguredFeature<?, ?> MOON_ALUMINUM;
    public static ConfiguredFeature<?, ?> MOON_TITANIUM;

    public static ConfiguredFeature<?, ?> OIL_LAKE;
    public static ConfiguredFeature<?, ?> TEST;
    public static Feature<BlockStateFeatureConfig> METEORITE;

    public static void registerFeatures() {
        RuleTest moon = blockRuleTest(BlockInit.MOON_STONE.get());
        MOON_IRON = registerConfiguredOre(moon, BlockInit.MOON_IRON_ORE.get(), 8, 64, 12);
        MOON_SILICON = registerConfiguredOre(moon, BlockInit.MOON_ALUMINUM_ORE.get(), 7, 64, 10);
        MOON_ALUMINUM = registerConfiguredOre(moon, BlockInit.MOON_SILICON_ORE.get(), 4, 64, 8);
        MOON_TITANIUM = registerConfiguredOre(moon, BlockInit.MOON_TITANIUM_ORE.get(), 2, 16, 1);

        OIL_LAKE = registerConfiguredLake(BlockInit.OIL.get(), 34, 100);
        TEST = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(CGalaxy.MODID, "test"), FeatureInit.METEORITE.get().withConfiguration(new BlockStateFeatureConfig(BlockInit.MOON_CHISELED_STONE_BRICKS.get().getDefaultState())).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).variableCount(1));
    }

    @SubscribeEvent
    public static void setupOreGen(BiomeLoadingEvent e) {
        if (isOverworld(e)) {
            e.getGeneration().withFeature(GenerationStage.Decoration.LAKES, OIL_LAKE);
        }
        if (isBiome(e, new ResourceLocation(CGalaxy.MODID, "the_moon"))) {
            genOre(e, MOON_IRON);
            genOre(e, MOON_SILICON);
            genOre(e, MOON_ALUMINUM);
            genOre(e, MOON_TITANIUM);

            e.getGeneration().withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, TEST);
        }
    }
}
