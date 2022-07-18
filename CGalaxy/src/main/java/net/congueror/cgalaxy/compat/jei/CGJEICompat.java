package net.congueror.cgalaxy.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderRecipe;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryRecipe;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorRecipe;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorScreen;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerRecipe;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerScreen;
import net.congueror.cgalaxy.blocks.solar_generator.SolarGeneratorRecipe;
import net.congueror.cgalaxy.blocks.solar_generator.SolarGeneratorScreen;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.CLib;
import net.congueror.clib.util.recipe.IItemFluidInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static net.congueror.clib.compat.jei.CLJEICompat.getRecipes;

@JeiPlugin
public class CGJEICompat implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(CGalaxy.MODID, "plugin/main");
    public static final RecipeType<FuelRefineryRecipe> FUEL_REFINING = new RecipeType<>(new ResourceLocation(CGalaxy.MODID, "fuel_refining"), FuelRefineryRecipe.class);
    public static final RecipeType<GasExtractorRecipe> GAS_EXTRACTING = new RecipeType<>(new ResourceLocation(CGalaxy.MODID, "gas_extracting"), GasExtractorRecipe.class);
    public static final RecipeType<FuelLoaderRecipe> FUEL_LOADING = new RecipeType<>(new ResourceLocation(CGalaxy.MODID, "fuel_loading"), FuelLoaderRecipe.class);
    public static final RecipeType<RoomPressurizerRecipe> ROOM_PRESSURIZING = new RecipeType<>(new ResourceLocation(CGalaxy.MODID, "room_pressurizing"), RoomPressurizerRecipe.class);
    public static final RecipeType<SolarGeneratorRecipe> SOLAR_ENERGY = new RecipeType<>(new ResourceLocation(CLib.MODID, "solar_energy"), SolarGeneratorRecipe.class);

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FuelRefiningCategory(FUEL_REFINING, guiHelper));
        registration.addRecipeCategories(new GasExtractingCategory(GAS_EXTRACTING, guiHelper));
        registration.addRecipeCategories(new FuelLoadingCategory(FUEL_LOADING, guiHelper));
        registration.addRecipeCategories(new RoomPressurizingCategory(ROOM_PRESSURIZING, guiHelper));
        registration.addRecipeCategories(new SolarEnergyCategory(SOLAR_ENERGY, guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(FUEL_REFINING, getRecipes(CGRecipeSerializerInit.FUEL_REFINING_TYPE.get()));
        registration.addRecipes(GAS_EXTRACTING, getRecipes(CGRecipeSerializerInit.GAS_EXTRACTING_TYPE.get()));
        registration.addRecipes(FUEL_LOADING, getRecipes(CGRecipeSerializerInit.FUEL_LOADING_TYPE.get()));
        registration.addRecipes(ROOM_PRESSURIZING, getRecipes(CGRecipeSerializerInit.ROOM_PRESSURIZING_TYPE.get()));
        registration.addRecipes(SOLAR_ENERGY, getRecipes(CGRecipeSerializerInit.SOLAR_ENERGY_TYPE.get()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FuelRefineryScreen.class, 85, 35, 24, 23, FUEL_REFINING);
        registration.addRecipeClickArea(GasExtractorScreen.class, 77, 38, 24, 23, GAS_EXTRACTING);
        registration.addRecipeClickArea(FuelLoaderScreen.class, 82, 30, 15, 29, FUEL_LOADING);
        registration.addRecipeClickArea(RoomPressurizerScreen.class, 92, 30, 15, 29, ROOM_PRESSURIZING);
        registration.addRecipeClickArea(SolarGeneratorScreen.class, 88, 33, 24, 23, SOLAR_ENERGY);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.FUEL_REFINERY.get()), FUEL_REFINING);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.GAS_EXTRACTOR.get()), GAS_EXTRACTING);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.FUEL_LOADER.get()), FUEL_LOADING);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.ROOM_PRESSURIZER.get()), ROOM_PRESSURIZING);

        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.SOLAR_GENERATOR_1.get()), SOLAR_ENERGY);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.SOLAR_GENERATOR_2.get()), SOLAR_ENERGY);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.SOLAR_GENERATOR_3.get()), SOLAR_ENERGY);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.SOLAR_GENERATOR_CREATIVE.get()), SOLAR_ENERGY);
    }
}
