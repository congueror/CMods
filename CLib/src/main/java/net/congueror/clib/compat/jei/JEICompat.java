package net.congueror.clib.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.congueror.clib.CLib;
import net.congueror.clib.blocks.solar_generator.SolarGeneratorScreen;
import net.congueror.clib.init.CLBlockInit;
import net.congueror.clib.init.CLItemInit;
import net.congueror.clib.init.CLRecipeSerializerInit;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//Ticks per cycle for energy buffer: energy capacity / (fe/t)
@JeiPlugin
public class JEICompat implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(CLib.MODID, "plugin/main");
    public static final ResourceLocation SOLAR_ENERGY = new ResourceLocation(CLib.MODID, "solar_energy");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new SolarEnergyCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, Collections.singletonList(new ItemStack(CLItemInit.GHOST_ITEM.get())));
        registration.addRecipes(getRecipes(CLRecipeSerializerInit.Types.SOLAR_ENERGY), SOLAR_ENERGY);
    }

    public static List<Recipe<?>> getRecipes(RecipeType<?> recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getRecipes().stream().filter(iRecipe -> iRecipe.getType() == recipe).collect(Collectors.toList());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SolarGeneratorScreen.class, 88, 33, 24, 23, SOLAR_ENERGY);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(CLBlockInit.SOLAR_GENERATOR.get()), SOLAR_ENERGY);
    }
}
