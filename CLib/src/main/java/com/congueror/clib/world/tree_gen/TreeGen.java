package com.congueror.clib.world.tree_gen;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.congueror.clib.world.WorldHelper.registerTree;

@Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TreeGen {
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> RUBBER;

    //Gets called from ModCommonEvents#commonSetup in an enqueueWork
    public static void registerTreeFeature() {
        RUBBER = registerTree(CLib.MODID, "rubber_tree", Feature.TREE.withConfiguration(new BaseTreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockInit.RUBBER_LOG.get().getDefaultState()),
                new SimpleBlockStateProvider(BlockInit.RUBBER_LEAVES.get().getDefaultState()),
                new FancyFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3),
                new StraightTrunkPlacer(4, 2, 0),
                new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build()));
    }

    @SubscribeEvent
    public static void setupTreeGen(BiomeLoadingEvent e) {
        BiomeGenerationSettingsBuilder generation = e.getGeneration();
        if (e.getName().toString().startsWith("minecraft:")) {
            if (doesBiomeMatch(e.getName(), Biomes.FOREST)) {
                generation.getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> RUBBER);
            }
        }
    }

    public static boolean doesBiomeMatch(ResourceLocation biomeNameIn, RegistryKey<Biome> biomeIn) {
        return biomeNameIn.getPath().matches(biomeIn.getLocation().getPath());
    }
}
