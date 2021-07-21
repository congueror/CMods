package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.world.carver.MoonCarver;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.ICarverConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CarverInit {

    public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, CGalaxy.MODID);

    public static final RegistryObject<WorldCarver<ProbabilityConfig>> MOON_CARVER = WORLD_CARVERS.register("moon_carver", () ->
            new MoonCarver(ProbabilityConfig.CODEC, 256));

    public static ConfiguredCarver<ProbabilityConfig> MOON_CAVE;

    private static <WC extends ICarverConfig> ConfiguredCarver<WC> register(String name, ConfiguredCarver<WC> carver) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_CARVER, new ResourceLocation(CGalaxy.MODID, name), carver);
    }

    //Gets called in an enqueue work in my common setup event.
    public static void registerCarvers() {
        MOON_CAVE = register("moon_carver", MOON_CARVER.get().func_242761_a(new ProbabilityConfig(0.14285715F)));
    }
}
