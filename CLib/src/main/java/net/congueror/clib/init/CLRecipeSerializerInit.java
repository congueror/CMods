package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.blocks.SolarGeneratorRecipe;
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

    public static final RegistryObject<RecipeSerializer<SolarGeneratorRecipe>> SOLAR_ENERGY = RECIPE_SERIALIZERS.register("solar_energy", SolarGeneratorRecipe.Serializer::new);

    public static final class Types {
        public static final RecipeType<SolarGeneratorRecipe> SOLAR_ENERGY = registerType("solar_energy");
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String name) {
        return Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CLib.MODID, name), new RecipeType<T>() {
            @Override
            public String toString() {
                return new ResourceLocation(CLib.MODID, name).toString();
            }
        });
    }
}
