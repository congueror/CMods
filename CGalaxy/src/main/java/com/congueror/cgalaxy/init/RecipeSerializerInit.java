package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.recipes.FuelLoaderRecipe;
import com.congueror.cgalaxy.recipes.FuelRefineryRecipe;
import com.congueror.cgalaxy.recipes.OxygenCompressorRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerInit {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CGalaxy.MODID);

    public static final RegistryObject<IRecipeSerializer<?>> OXYGEN_COMPRESSING = RECIPE_SERIALIZERS.register("oxygen_compressing", OxygenCompressorRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> FUEL_REFINING = RECIPE_SERIALIZERS.register("fuel_refining", FuelRefineryRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> FUEL_LOADING = RECIPE_SERIALIZERS.register("fuel_loading", FuelLoaderRecipe.Serializer::new);

    public static final class Types {
        public static final IRecipeType<OxygenCompressorRecipe> OXYGEN_COMPRESSING = registerType("oxygen_compressing");
        public static final IRecipeType<FuelRefineryRecipe> FUEL_REFINING = registerType("fuel_refining");
        public static final IRecipeType<FuelLoaderRecipe> FUEL_LOADING = registerType("fuel_loading");
    }

    private static <T extends IRecipe<?>> IRecipeType<T> registerType(String name) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CGalaxy.MODID, name), new IRecipeType<T>() {
            @Override
            public String toString() {
                return new ResourceLocation(CGalaxy.MODID, name).toString();
            }
        });
    }
}
