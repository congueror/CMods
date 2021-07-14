package com.congueror.cores.world;

import com.congueror.clib.world.WorldHelper;
import com.congueror.cores.COres;
import com.congueror.cores.init.BlockInit;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.congueror.clib.world.WorldHelper.*;

@Mod.EventBusSubscriber(modid = COres.MODID)
public class OreGen {

    public static ConfiguredFeature<?, ?> TIN;
    public static ConfiguredFeature<?, ?> ALUMINUM;
    public static ConfiguredFeature<?, ?> LEAD;
    public static ConfiguredFeature<?, ?> COPPER;
    public static ConfiguredFeature<?, ?> RUBY;
    public static ConfiguredFeature<?, ?> SILVER;
    public static ConfiguredFeature<?, ?> NICKEL;
    public static ConfiguredFeature<?, ?> PLATINUM;
    public static ConfiguredFeature<?, ?> TUNGSTEN;
    public static ConfiguredFeature<?, ?> AMETHYST;
    public static ConfiguredFeature<?, ?> SAPPHIRE;
    public static ConfiguredFeature<?, ?> OPAL;
    public static ConfiguredFeature<?, ?> TITANIUM;
    public static ConfiguredFeature<?, ?> URANIUM;
    public static ConfiguredFeature<?, ?> COBALT;
    public static ConfiguredFeature<?, ?> ZINC;
    public static ConfiguredFeature<?, ?> CHROMIUM;
    public static ConfiguredFeature<?, ?> THORIUM;
    public static ConfiguredFeature<?, ?> SALTPETRE;
    public static ConfiguredFeature<?, ?> SULFUR;

    public static ConfiguredFeature<?, ?> SALT;

    public static void registerFeatures() {
        TIN = registerConfiguredOre(overworld, BlockInit.TIN_ORE.get(), 8, 63, 25);
        ALUMINUM = registerConfiguredOre(overworld, BlockInit.ALUMINUM_ORE.get(), 7, 63, 8);
        LEAD = registerConfiguredOre(overworld, BlockInit.LEAD_ORE.get(), 7, 31, 4);
        COPPER = registerConfiguredOre(overworld, BlockInit.COPPER_ORE.get(), 8, 63, 20);
        RUBY = registerConfiguredOre(nether, BlockInit.RUBY_ORE.get(), 3, 18, 1);
        SILVER = registerConfiguredOre(overworld, BlockInit.SILVER_ORE.get(), 7, 16, 3);
        NICKEL = registerConfiguredOre(overworld, BlockInit.NICKEL_ORE.get(), 7, 31, 4);
        PLATINUM = registerConfiguredOre(overworld, BlockInit.PLATINUM_ORE.get(), 6, 8, 1);
        TUNGSTEN = registerConfiguredOre(overworld, BlockInit.TUNGSTEN_ORE.get(), 6, 16, 2);
        AMETHYST = registerConfiguredOre(overworld, BlockInit.AMETHYST_ORE.get(), 6, 16, 1);
        SAPPHIRE = registerConfiguredOre(overworld, BlockInit.SAPPHIRE_ORE.get(), 8, 16, 2);
        OPAL = registerConfiguredOre(WorldHelper.end, BlockInit.OPAL_ORE.get(), 4, 30, 1);
        TITANIUM = registerConfiguredOre(nether, BlockInit.TITANIUM_ORE.get(), 7, 7, 1);
        URANIUM = registerConfiguredOre(overworld, BlockInit.URANIUM_ORE.get(), 7, 16, 1);
        COBALT = registerConfiguredOre(nether, BlockInit.COBALT_ORE.get(), 7, 32, 1);
        ZINC = registerConfiguredOre(overworld, BlockInit.ZINC_ORE.get(), 7, 32, 8);
        CHROMIUM = registerConfiguredOre(overworld, BlockInit.CHROMIUM_ORE.get(), 7, 22, 6);
        THORIUM = registerConfiguredOre(overworld, BlockInit.THORIUM_ORE.get(), 7, 8, 1);
        SALTPETRE = registerConfiguredOre(overworld, BlockInit.SALTPETRE_ORE.get(), 3, 100, 1);
        SULFUR = registerConfiguredOre(nether, BlockInit.SULFUR_ORE.get(), 5, 100, 4);

        SALT = registerConfiguredDisk(BlockInit.SALT_BLOCK.get());
    }

    @SubscribeEvent
    public static void setupOreGen(BiomeLoadingEvent e) {
        if (isOverworld(e)) {
            genore(e, TIN);
            genore(e, ALUMINUM);
            genore(e, LEAD);
            genore(e, COPPER);
            genore(e, SILVER);
            genore(e, NICKEL);
            genore(e, PLATINUM);
            genore(e, TUNGSTEN);
            genore(e, AMETHYST);
            genore(e, SAPPHIRE);
            genore(e, URANIUM);
            genore(e, ZINC);
            genore(e, CHROMIUM);
            genore(e, THORIUM);
            genore(e, SALTPETRE);
            genore(e, SALT);
        }
        if (isNether(e)) {
            genore(e, RUBY);
            genore(e, TITANIUM);
            genore(e, COBALT);
        }
        if (isEnd(e)) {
            genore(e, OPAL);
        }
    }
}

