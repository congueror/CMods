package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.util.MathHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CGConfig {
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static final String CATEGORY_SPACE_SUIT_CLIENT = "client_space_suit";

    public static ForgeConfigSpec.EnumValue<TemperatureUnits> TEMPERATURE;
    public static ForgeConfigSpec.EnumValue<RadiationUnits> RADIATION;
    public static ForgeConfigSpec.EnumValue<AirPressureUnits> AIR_PRESSURE;
    public static ForgeConfigSpec.EnumValue<GuiColors> GUI_COLOR;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("Space Suit Settings").push(CATEGORY_SPACE_SUIT_CLIENT);
        TEMPERATURE = CLIENT_BUILDER.comment("The measuring unit of temperature displayed on the space suit hud").defineEnum("temperatureUnit", TemperatureUnits.CELCIUS);
        RADIATION = CLIENT_BUILDER.comment("The measuring unit of radiation displayed on the space suit hud").defineEnum("radiationUnit", RadiationUnits.SIEVERT);
        AIR_PRESSURE = CLIENT_BUILDER.comment("The measuring unit of air pressure displayed on the space suit hud").defineEnum("airPressureUnit", AirPressureUnits.PASCALS);
        GUI_COLOR = CLIENT_BUILDER.comment("The color of the space suit gui and hud").defineEnum("suitColor", GuiColors.GREEN);
        CLIENT_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
    }

    public enum TemperatureUnits {
        CELCIUS,
        KELVIN,
        FARRENHEIT
    }

    public enum RadiationUnits {
        SIEVERT,
        REM
    }

    public enum AirPressureUnits {
        PASCALS,
        PSI,
        TORR,
        BARS,
        ATMOSPHERES
    }

    public enum GuiColors {//TODO

        BLACK(MathHelper.calculateRGB(30, 30, 30)),//Brightness: -100 Contrast: 100
        GREEN(MathHelper.calculateRGB(223, 255, 0)),
        /*
        DARK_BLUE,
        DARK_GREEN,
        DARK_AQUA,
        DARK_RED,
        DARK_PURPLE,
        GOLD,
        GRAY,
        DARK_GRAY,
        BLUE,
        GREEN,
        AQUA,
        RED,
        LIGHT_PURPLE,
        YELLOW,
        WHITE*/
        ;

        public final int rgb;

        GuiColors(int rgb) {
            this.rgb = rgb;
        }
    }
}
