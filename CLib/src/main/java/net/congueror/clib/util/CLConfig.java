package net.congueror.clib.util;

import net.congueror.clib.CLib;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CLConfig {
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.ConfigValue<Integer> RUBBER_TREE_RARITY;

    public static ForgeConfigSpec.ConfigValue<Integer> TIN_VEIN;
    public static ForgeConfigSpec.ConfigValue<Integer> TIN_MIN;
    public static ForgeConfigSpec.ConfigValue<Integer> TIN_MAX;
    public static ForgeConfigSpec.ConfigValue<Integer> TIN_COUNT;

    public static ForgeConfigSpec.ConfigValue<Integer> ALUMINUM_VEIN;
    public static ForgeConfigSpec.ConfigValue<Integer> ALUMINUM_MIN;
    public static ForgeConfigSpec.ConfigValue<Integer> ALUMINUM_MAX;
    public static ForgeConfigSpec.ConfigValue<Integer> ALUMINUM_COUNT;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("WorldGen Settings").push("worldgen_settings");
        RUBBER_TREE_RARITY = SERVER_BUILDER.comment("The rarity of rubber trees").define("rubberTree", 100);

        TIN_VEIN = SERVER_BUILDER.comment("The vein size of the tin ore").define("tinVein", 8);
        TIN_MIN = SERVER_BUILDER.comment("The minimum height of the tin ore").define("tinMin", -24);
        TIN_MAX = SERVER_BUILDER.comment("The maximum height of the tin ore").define("tinMax", 56);
        TIN_COUNT = SERVER_BUILDER.comment("The count of the tin ore").define("tinCount", 25);

        ALUMINUM_VEIN = SERVER_BUILDER.comment("The vein size of the aluminum ore").define("aluminumVein", 7);
        ALUMINUM_MIN = SERVER_BUILDER.comment("The minimum height of the aluminum ore").define("aluminumMin", -24);
        ALUMINUM_MAX = SERVER_BUILDER.comment("The maximum height of the aluminum ore").define("aluminumMax", 63);
        ALUMINUM_COUNT = SERVER_BUILDER.comment("The count of the aluminum ore").define("aluminumCount", 8);


        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading e) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading e) {
    }
}
