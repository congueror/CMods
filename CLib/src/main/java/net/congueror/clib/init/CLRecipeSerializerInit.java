package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CLRecipeSerializerInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CLib.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, CLib.MODID);

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerType(String name) {
        return RECIPE_TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return new ResourceLocation(CLib.MODID, name).toString();
            }
        });
    }
}
