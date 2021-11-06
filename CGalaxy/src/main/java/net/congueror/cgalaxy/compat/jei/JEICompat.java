package net.congueror.cgalaxy.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.blocks.oxygen_compressor.OxygenCompressorScreen;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JEICompat implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(CGalaxy.MODID, "plugin/main");
    public static final ResourceLocation FUEL_REFINING = new ResourceLocation(CGalaxy.MODID, "fuel_refining");
    public static final ResourceLocation OXYGEN_COMPRESSING = new ResourceLocation(CGalaxy.MODID, "oxygen_compressing");
    public static final ResourceLocation FUEL_LOADING = new ResourceLocation(CGalaxy.MODID, "fuel_loading");

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FuelRefiningCategory(guiHelper));
        registration.addRecipeCategories(new OxygenCompressingCategory(guiHelper));
        registration.addRecipeCategories(new FuelLoadingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(getRecipes(CGRecipeSerializerInit.Types.FUEL_REFINING), FUEL_REFINING);
        registration.addRecipes(getRecipes(CGRecipeSerializerInit.Types.OXYGEN_COMPRESSING), OXYGEN_COMPRESSING);
        registration.addRecipes(getRecipes(CGRecipeSerializerInit.Types.FUEL_LOADING), FUEL_LOADING);
    }

    public static List<Recipe<?>> getRecipes(RecipeType<?> recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(iRecipe -> iRecipe.getType() == recipe).collect(Collectors.toList());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FuelRefineryScreen.class, 85, 35, 24, 23, FUEL_REFINING);
        registration.addRecipeClickArea(OxygenCompressorScreen.class, 77, 38, 24, 23, OXYGEN_COMPRESSING);
        registration.addRecipeClickArea(FuelLoaderScreen.class, 82, 30, 15, 29, FUEL_LOADING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.FUEL_REFINERY.get()), FUEL_REFINING);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.OXYGEN_COMPRESSOR.get()), OXYGEN_COMPRESSING);
        registration.addRecipeCatalyst(new ItemStack(CGBlockInit.FUEL_LOADER.get()), FUEL_LOADING);
    }
}
