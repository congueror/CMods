package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.clib.world.WorldHelper;

public class Dimensions {
    public static final CGDimensionBuilder.DimensionObject MOON = new CGDimensionBuilder(WorldHelper.registerDim(CGalaxy.MODID, "moon"))
            .withGravity(0.0162)
            .withBreathableAtmosphere(false)
            .withDayTemperature(127)
            .withNightTemperature(-173)
            .withRadiation(380.0F).build();

    /**
     * Called from an {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent#enqueueWork(Runnable)}
     */
    public static void registerDimensions() {}

}
