package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.clib.world.WorldHelper;
import net.minecraft.world.level.Level;

public class CGDimensions {
    public static final CGDimensionBuilder.DimensionObject OVERWORLD = new CGDimensionBuilder(Level.OVERWORLD)
            .build();
    public static final CGDimensionBuilder.DimensionObject NETHER = new CGDimensionBuilder(Level.NETHER)
            .build();
    public static final CGDimensionBuilder.DimensionObject END = new CGDimensionBuilder(Level.END)
            .build();

    public static final CGDimensionBuilder.DimensionObject MOON = new CGDimensionBuilder(WorldHelper.registerDim(CGalaxy.MODID, "moon"))
            .withGravity(1.62)
            .withBreathableAtmosphere(false)
            .withDayTemperature(127)
            .withNightTemperature(-173)
            .withRadiation(380.0F)
            .build();

    /**
     * Called from an {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent#enqueueWork(Runnable)}
     */
    public static void registerDimensions() {
        WorldHelper.registerBiomeSource(CGalaxy.MODID, "moon", MoonBiomeSource.CODEC);
    }
}