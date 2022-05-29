package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.world.structures.LunarVillageStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CGStructureInit {

    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, CGalaxy.MODID);

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> LUNAR_VILLAGE = STRUCTURES.register("lunar_village", () -> new LunarVillageStructure(JigsawConfiguration.CODEC));
}
