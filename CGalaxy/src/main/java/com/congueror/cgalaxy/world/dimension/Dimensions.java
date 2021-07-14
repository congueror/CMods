package com.congueror.cgalaxy.world.dimension;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.clib.world.WorldHelper;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class Dimensions {
    public static final RegistryKey<World> MOON = WorldHelper.registerDim(CGalaxy.MODID, "moon");

    public static void setupDims() {}
}
