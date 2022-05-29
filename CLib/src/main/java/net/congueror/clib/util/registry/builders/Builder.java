package net.congueror.clib.util.registry.builders;

import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public interface Builder<B extends IForgeRegistryEntry<? super B>> {

    RegistryObject<B> build();
}
