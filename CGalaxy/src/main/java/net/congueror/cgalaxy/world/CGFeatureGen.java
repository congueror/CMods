package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGFeatureInit;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.congueror.clib.world.WorldHelper.*;

@Mod.EventBusSubscriber(modid = CGalaxy.MODID)
public class CGFeatureGen {

    public static ConfiguredFeature<?, ?> MOON_IRON;
    public static ConfiguredFeature<?, ?> MOON_SILICON;
    public static ConfiguredFeature<?, ?> MOON_ALUMINUM;
    public static ConfiguredFeature<?, ?> MOON_TITANIUM;
    public static ConfiguredFeature<?, ?> MOON_ICE_CLUSTER;

    public static ConfiguredFeature<?, ?> OIL_LAKE;
    public static ConfiguredFeature<?, ?> METEORITE;
    public static ConfiguredFeature<?, ?> SAPPHIRE_METEORITE;
    public static ConfiguredFeature<?, ?> METEORITE_OVERWORLD;

    public static void registerFeatures() {//TODO: Cheese Ore
        RuleTest moon = blockRuleTest(CGBlockInit.MOON_STONE.get());
        MOON_IRON = registerConfiguredOre(moon, CGBlockInit.MOON_IRON_ORE.get(), 8, 64, 12);
        MOON_SILICON = registerConfiguredOre(moon, CGBlockInit.MOON_ALUMINUM_ORE.get(), 7, 64, 10);
        MOON_ALUMINUM = registerConfiguredOre(moon, CGBlockInit.MOON_SILICON_ORE.get(), 4, 64, 8);
        MOON_TITANIUM = registerConfiguredOre(moon, CGBlockInit.MOON_TITANIUM_ORE.get(), 2, 16, 1);

        MOON_ICE_CLUSTER = registerConfiguredOre(moon, Blocks.ICE, 3, 50, 70, 4);

        OIL_LAKE = registerConfiguredLake(CGBlockInit.OIL.get(), 34, 100);

        METEORITE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(CGalaxy.MODID, "meteorite"), CGFeatureInit.METEORITE.get().configured(new BlockStateConfiguration(CGBlockInit.METEORITE.get().defaultBlockState()))
                .decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE)
                .count(1)
                .rarity(25)
                .squared());

        SAPPHIRE_METEORITE = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(CGalaxy.MODID, "astral_sapphire_meteorite"), CGFeatureInit.METEORITE.get().configured(new BlockStateConfiguration(CGBlockInit.ASTRAL_SAPPHIRE_ORE.get().defaultBlockState()))
                .decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE)
                .count(1)
                .rarity(110)
                .squared());

        METEORITE_OVERWORLD = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(CGalaxy.MODID, "meteorite_overworld"), CGFeatureInit.METEORITE.get().configured(new BlockStateConfiguration(CGBlockInit.METEORITE.get().defaultBlockState()))
                .decorated(Features.Decorators.HEIGHTMAP_WORLD_SURFACE)
                .count(1)
                .rarity(100)
                .squared());
    }

    @SubscribeEvent
    public static void setupOreGen(BiomeLoadingEvent e) {
        if (isOverworld(e)) {
            e.getGeneration().addFeature(GenerationStep.Decoration.LAKES, OIL_LAKE);
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, METEORITE_OVERWORLD);
        }
        if (isBiome(e, CGBiomes.THE_MOON)) {
            addOre(e, MOON_IRON, MOON_SILICON, MOON_ALUMINUM, MOON_TITANIUM);

            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, METEORITE);
            e.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, SAPPHIRE_METEORITE);
        }
        if (isBiome(e, CGBiomes.THE_MOON_SOUTH)) {
            addOre(e, MOON_ICE_CLUSTER);
        }
    }
}