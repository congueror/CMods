package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.block.fuel_loader.FuelLoaderRecipe;
import net.congueror.cgalaxy.block.fuel_refinery.FuelRefineryRecipe;
import net.congueror.cgalaxy.block.oxygen_compressor.OxygenCompressorRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGRecipeSerializerInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CGalaxy.MODID);

    public static final RegistryObject<RecipeSerializer<?>> FUEL_LOADING = RECIPE_SERIALIZERS.register("fuel_loading", FuelLoaderRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> FUEL_REFINING = RECIPE_SERIALIZERS.register("fuel_refining", FuelRefineryRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> OXYGEN_COMPRESSING = RECIPE_SERIALIZERS.register("oxygen_compressing", OxygenCompressorRecipe.Serializer::new);

    public static final class Types {
        public static final RecipeType<FuelLoaderRecipe> FUEL_LOADING = registerType("fuel_loading");
        public static final RecipeType<FuelRefineryRecipe> FUEL_REFINING = registerType("fuel_refining");
        public static final RecipeType<OxygenCompressorRecipe> OXYGEN_COMPRESSING = registerType("oxygen_compressing");
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String name) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CGalaxy.MODID, name), new RecipeType<T>() {
            @Override
            public String toString() {
                return new ResourceLocation(CGalaxy.MODID, name).toString();
            }
        });
    }
}
