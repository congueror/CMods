package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.world.WorldHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class Dimensions {
    public static final ResourceKey<Level> MOON = WorldHelper.registerDim(CGalaxy.MODID, "moon");

    /**
     * Called from an {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent#enqueueWork(Runnable)}
     */
    public static void setupDims() {}
}
