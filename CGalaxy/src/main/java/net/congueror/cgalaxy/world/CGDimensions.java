package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.world.WorldHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class CGDimensions {

    public static final ResourceKey<Level> MOON = WorldHelper.registerDim(CGalaxy.location("moon"));
    public static final ResourceKey<Level> OVERWORLD_ORBIT = WorldHelper.registerDim(CGalaxy.MODID, "overworld_orbit");
    public static final ResourceKey<Level> MOON_ORBIT = WorldHelper.registerDim(CGalaxy.MODID, "moon_orbit");

    /**
     * Called from an {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent#enqueueWork(Runnable)}
     */
    public static void registerDimensions() {}
}