package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.blocks.solar_generator.SolarGeneratorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CLBlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CLib.MODID);

    public static final RegistryObject<BlockEntityType<SolarGeneratorBlockEntity>> SOLAR_GENERATOR = BLOCK_ENTITY_TYPES.register("solar_generator", () ->
            BlockEntityType.Builder.of(SolarGeneratorBlockEntity::new,
                    CLBlockInit.SOLAR_GENERATOR_1.get(),
                    CLBlockInit.SOLAR_GENERATOR_2.get(),
                    CLBlockInit.SOLAR_GENERATOR_3.get(),
                    CLBlockInit.SOLAR_GENERATOR_CREATIVE.get()).build(null));
}