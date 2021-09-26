package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.world.MoonCarver;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGCarverInit {

    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, CGalaxy.MODID);

    public static final RegistryObject<WorldCarver<CaveCarverConfiguration>> MOON_CARVER = CARVERS.register("moon_carver", () ->
            new MoonCarver(CaveCarverConfiguration.CODEC));

    public static ConfiguredWorldCarver<CaveCarverConfiguration> MOON_CAVE;

    private static <WC extends CarverConfiguration> ConfiguredWorldCarver<WC> register(String name, ConfiguredWorldCarver<WC> carver) {
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_CARVER, new ResourceLocation(CGalaxy.MODID, name), carver);
    }

    //Gets called in an enqueue work in my common setup event.
    public static void registerCarvers() {
        MOON_CAVE = register("moon_carver", MOON_CARVER.get().configured(new CaveCarverConfiguration(0.14285715F, BiasedToBottomHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.absolute(127), 8), ConstantFloat.of(0.5F), VerticalAnchor.aboveBottom(10), false, CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), ConstantFloat.of(1.0F), ConstantFloat.of(1.0F), ConstantFloat.of(-0.7F))));
    }
}
