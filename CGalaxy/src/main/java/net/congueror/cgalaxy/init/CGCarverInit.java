package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.world.MoonCarver;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CGCarverInit {

    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, CGalaxy.MODID);

    public static final RegistryObject<WorldCarver<CaveCarverConfiguration>> MOON_CARVER = CARVERS.register("moon_carver", () ->
            new MoonCarver(CaveCarverConfiguration.CODEC));

    public static Holder<ConfiguredWorldCarver<CaveCarverConfiguration>> MOON_CAVE;

    private static <WC extends CarverConfiguration> Holder<ConfiguredWorldCarver<WC>> register(String name, ConfiguredWorldCarver<WC> carver) {
        return Holder.direct(Registry.register(BuiltinRegistries.CONFIGURED_CARVER, new ResourceLocation(name), carver));
    }

    //Gets called in an enqueue work in my common setup event.
    public static void registerCarvers() {
        MOON_CAVE = register(CGalaxy.MODID + ":moon_carver", MOON_CARVER.get().configured(new CaveCarverConfiguration(0.15F,
                UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)), //y
                UniformFloat.of(0.1F, 0.9F), //yScale
                VerticalAnchor.absolute(8), //lavaLevel
                CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()),
                UniformFloat.of(0.7F, 1.4F),
                UniformFloat.of(0.8F, 1.3F),
                UniformFloat.of(-1.0F, -0.4F))));
    }
}