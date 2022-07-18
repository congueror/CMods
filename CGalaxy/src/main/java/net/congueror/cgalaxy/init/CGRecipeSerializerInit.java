package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderRecipe;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryRecipe;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorRecipe;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class CGRecipeSerializerInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CGalaxy.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, CGalaxy.MODID);

    public static final RegistryObject<RecipeSerializer<FuelLoaderRecipe>> FUEL_LOADING = RECIPE_SERIALIZERS.register("fuel_loading", FuelLoaderRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<FuelRefineryRecipe>> FUEL_REFINING = RECIPE_SERIALIZERS.register("fuel_refining", FuelRefineryRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<GasExtractorRecipe>> GAS_EXTRACTING = RECIPE_SERIALIZERS.register("gas_extracting", GasExtractorRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<RoomPressurizerRecipe>> ROOM_PRESSURIZER = RECIPE_SERIALIZERS.register("room_pressurizing", RoomPressurizerRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<FuelLoaderRecipe>> FUEL_LOADING_TYPE = registerType("fuel_loading");
    public static final RegistryObject<RecipeType<FuelRefineryRecipe>> FUEL_REFINING_TYPE = registerType("fuel_refining");
    public static final RegistryObject<RecipeType<GasExtractorRecipe>> GAS_EXTRACTING_TYPE = registerType("gas_extracting");
    public static final RegistryObject<RecipeType<RoomPressurizerRecipe>> ROOM_PRESSURIZING_TYPE = registerType("room_pressurizing");
    public static final RegistryObject<RecipeType<SolarGeneratorRecipe>> SOLAR_ENERGY_TYPE = registerType("solar_energy");

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerType(String name) {
        return RECIPE_TYPES.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return new ResourceLocation(CGalaxy.MODID, name).toString();
            }
        });
    }
}
