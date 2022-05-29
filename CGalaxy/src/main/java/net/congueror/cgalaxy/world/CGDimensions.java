package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.clib.world.WorldHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class CGDimensions {
    public static final CGDimensionBuilder.DimensionObject OVERWORLD = new CGDimensionBuilder(Holder.direct(Level.OVERWORLD))
            .build();
    public static final CGDimensionBuilder.DimensionObject NETHER = new CGDimensionBuilder(Holder.direct(Level.NETHER))
            .build();
    public static final CGDimensionBuilder.DimensionObject END = new CGDimensionBuilder(Holder.direct(Level.END))
            .build();

    public static final CGDimensionBuilder.DimensionObject MOON = new CGDimensionBuilder(WorldHelper.registerDim(CGalaxy.MODID, "moon"))
            .withGravity(1.62)
            .withBreathableAtmosphere(false)
            .withDayTemperature(127)
            .withNightTemperature(-173)
            .withRadiation(380.0F)
            .withAirPressure(0.0000000003)
            .withYOverlayTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/rocket_y_hud_moon.png"))
            .build();
    public static final CGDimensionBuilder.DimensionObject OVERWORLD_ORBIT = new CGDimensionBuilder(WorldHelper.registerDim(CGalaxy.MODID, "overworld_orbit"))
            .withGravity(0)
            .withBreathableAtmosphere(false)
            .withDayTemperature(121)
            .withNightTemperature(-157)
            .withRadiation(300.0F)
            .withAirPressure(0)
            .withYOverlayTexture(CGalaxy.location("textures/gui/rocket_y_hud_moon"))//TODO
            .withOrbit(true)
            .build();

    /**
     * Called from an {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent#enqueueWork(Runnable)}
     */
    public static void registerDimensions() {
        WorldHelper.registerBiomeSource(CGalaxy.MODID, "moon", MoonBiomeSource.CODEC);
    }
}