package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.world.features.DryIceFeature;
import net.congueror.cgalaxy.world.features.MeteoriteFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CGFeatureInit {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, CGalaxy.MODID);

    public static final RegistryObject<MeteoriteFeature> METEORITE = FEATURES.register("meteorite", () -> new MeteoriteFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<DryIceFeature> DRY_ICE = FEATURES.register("dry_ice", () -> new DryIceFeature(NoneFeatureConfiguration.CODEC));
}
