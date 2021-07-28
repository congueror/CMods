package com.congueror.cgalaxy.world;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.clib.world.WorldHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.congueror.clib.world.WorldHelper.*;

@Mod.EventBusSubscriber(modid = CGalaxy.MODID)
public class PlanetOreGen {

    public static ConfiguredFeature<?, ?> MOON_IRON;
    public static ConfiguredFeature<?, ?> MOON_SILICON;
    public static ConfiguredFeature<?, ?> MOON_ALUMINUM;
    public static ConfiguredFeature<?, ?> MOON_TITANIUM;

    public static void registerFeatures() {
        RuleTest moon = blockRuleTest(BlockInit.MOON_STONE.get());
        MOON_IRON = registerConfiguredOre(moon, BlockInit.MOON_IRON_ORE.get(), 8, 64, 12);
        MOON_SILICON = registerConfiguredOre(moon, BlockInit.MOON_ALUMINUM_ORE.get(), 7, 64, 10);
        MOON_ALUMINUM = registerConfiguredOre(moon, BlockInit.MOON_SILICON_ORE.get(), 4, 64, 8);
        MOON_TITANIUM = registerConfiguredOre(moon, BlockInit.MOON_TITANIUM_ORE.get(), 1, 16, 1);
    }

    @SubscribeEvent
    public static void setupOreGen(BiomeLoadingEvent e) {
        if (isBiome(e, new ResourceLocation(CGalaxy.MODID, "the_moon"))) {
            genOre(e, MOON_IRON);
            genOre(e, MOON_SILICON);
            genOre(e, MOON_ALUMINUM);
            genOre(e, MOON_TITANIUM);
        }
    }
}
