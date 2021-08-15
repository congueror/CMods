package com.congueror.cgalaxy.compat.jei;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import com.congueror.cgalaxy.block.oxygen_compressor.OxygenCompressorScreen;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.cgalaxy.recipes.OxygenCompressorRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class JeiCompat implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(CGalaxy.MODID, "plugin/main");
    protected static final ResourceLocation FUEL_REFINING = new ResourceLocation(CGalaxy.MODID, "fuel_refining");
    protected static final ResourceLocation OXYGEN_COMPRESSING = new ResourceLocation(CGalaxy.MODID, "oxygen_compressing");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FuelRefiningCategory(guiHelper));
        registration.addRecipeCategories(new OxygenCompressingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(getRecipes(RecipeSerializerInit.Types.FUEL_REFINING), FUEL_REFINING);
        registration.addRecipes(getRecipes(RecipeSerializerInit.Types.OXYGEN_COMPRESSING), OXYGEN_COMPRESSING);
    }

    public static List<IRecipe<?>> getRecipes(IRecipeType<?> recipe) {
        assert Minecraft.getInstance().world != null;
        return Minecraft.getInstance().world.getRecipeManager().getRecipes().stream().filter(iRecipe -> iRecipe.getType() == recipe).collect(Collectors.toList());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FuelRefineryScreen.class, 85, 35, 24, 23, FUEL_REFINING);
        registration.addRecipeClickArea(OxygenCompressorScreen.class, 77, 38, 24, 23, OXYGEN_COMPRESSING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockInit.FUEL_REFINERY.get()), FUEL_REFINING);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.OXYGEN_COMPRESSOR.get()), OXYGEN_COMPRESSING);
    }
}
