package com.congueror.cgalaxy.compat.jei;

import com.congueror.cgalaxy.block.fuel_loader.FuelLoaderScreen;
import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.cgalaxy.recipes.FuelLoaderRecipe;
import com.congueror.cgalaxy.recipes.FuelRefineryRecipe;
import com.congueror.cgalaxy.recipes.OxygenCompressorRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FuelLoadingCategory implements IRecipeCategory<FuelLoaderRecipe> {

    IGuiHelper helper;
    private final IDrawableAnimated arrow;
    private final IDrawableStatic energy_glass;
    private final IDrawableAnimated energy;
    private final String localized;

    public FuelLoadingCategory(IGuiHelper helper) {
        this.helper = helper;
        int processTime = ((FuelLoaderRecipe) JeiCompat.getRecipes(RecipeSerializerInit.Types.FUEL_LOADING).get(0)).getProcessTime();
        arrow = helper.drawableBuilder(FuelLoaderScreen.GUI, 228, 0, 17, 31).buildAnimated(processTime, IDrawableAnimated.StartDirection.BOTTOM, false);
        energy_glass = helper.drawableBuilder(FuelLoaderScreen.GUI, 212, 0, 16, 60).build();
        energy = helper.drawableBuilder(FuelLoaderScreen.GUI, 196, 0, 16, 60).buildAnimated(1333, IDrawableAnimated.StartDirection.TOP, true);
        localized = I18n.format("recipe.cgalaxy.fuel_loading");
    }

    @Override
    public ResourceLocation getUid() {
        return JeiCompat.FUEL_LOADING;
    }

    @Override
    public Class<? extends FuelLoaderRecipe> getRecipeClass() {
        return FuelLoaderRecipe.class;
    }

    @Override
    public String getTitle() {
        return localized;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(FuelLoaderScreen.GUI, 21, 7, 172, 63);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(BlockInit.FUEL_LOADER.get()));
    }

    @Override
    public void setIngredients(FuelLoaderRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getIngredient().getFluidStacks()));
        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidResults().stream().map(Collections::singletonList).collect(Collectors.toList()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FuelLoaderRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(0, true, 79, 11, 16, 50, 1000, false, null);
        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
    }

    @Override
    public void draw(FuelLoaderRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        arrow.draw(matrixStack, 61, 22);
        energy.draw(matrixStack, 151, 2);
        energy_glass.draw(matrixStack, 151, 2);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(FuelLoaderRecipe recipe, double mouseX, double mouseY) {
        List<ITextComponent> list = new ArrayList<>();
        if (mouseX >= 151 && mouseX <= 167 && mouseY >= 2 && mouseY <= 62) {
            list.add(new TranslationTextComponent("tooltip.cgalaxy.screen.energy_usage").appendString(": 30FE/t"));
        }
        return list;
    }
}
