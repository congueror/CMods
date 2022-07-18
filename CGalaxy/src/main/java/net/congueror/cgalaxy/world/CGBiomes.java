package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.world.WorldHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import static net.congueror.clib.world.WorldHelper.registerBiome;

public class CGBiomes {

    public static final ResourceKey<Biome> THE_MOON = registerBiome(CGalaxy.MODID, "the_moon");
    public static final ResourceKey<Biome> THE_MOON_SOUTH = registerBiome(CGalaxy.MODID, "the_moon_south");
    public static final ResourceKey<Biome> ORBIT = registerBiome(CGalaxy.MODID, "orbit");
    public static final ResourceKey<Biome> MARS = registerBiome(CGalaxy.MODID, "mars");
    public static final ResourceKey<Biome> MARS_BASALT = registerBiome(CGalaxy.MODID, "mars_basalt");
    public static final ResourceKey<Biome> MARS_SOUTH = registerBiome(CGalaxy.MODID, "mars_south");

    public static void registerBiomes() {
        WorldHelper.registerBiomeSource(CGalaxy.MODID, "planet", PlanetBiomeSource.CODEC);
    }
}
