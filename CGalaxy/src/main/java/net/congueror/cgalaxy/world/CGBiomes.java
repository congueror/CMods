package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import static net.congueror.clib.world.WorldHelper.registerBiome;

public class CGBiomes {

    public static final ResourceKey<Biome> THE_MOON = registerBiome(CGalaxy.MODID, "the_moon");
    public static final ResourceKey<Biome> THE_MOON_SOUTH = registerBiome(CGalaxy.MODID, "the_moon_south");

    public static void registerBiomes() {}
}
