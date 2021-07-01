package com.congueror.clib.world;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DimensionHelper {
    public static RegistryKey<World> registerDim(String modid, String name) {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(modid, name));
    }
}
