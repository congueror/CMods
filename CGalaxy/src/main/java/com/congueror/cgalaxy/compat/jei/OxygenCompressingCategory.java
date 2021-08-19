package com.congueror.cgalaxy.compat.jei;

import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import com.congueror.cgalaxy.block.oxygen_compressor.OxygenCompressorScreen;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.cgalaxy.recipes.FuelRefineryRecipe;
import com.congueror.cgalaxy.recipes.OxygenCompressorRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OxygenCompressingCategory implements IRecipeCategory<OxygenCompressorRecipe> {

    IGuiHelper helper;
    private final IDrawableAnimated arrow;
    private final IDrawableStatic energy_glass;
    private final IDrawableAnimated energy;
    private final String localized;

    public OxygenCompressingCategory(IGuiHelper helper) {
        this.helper = helper;
        int processTime = ((OxygenCompressorRecipe) JeiCompat.getRecipes(RecipeSerializerInit.Types.OXYGEN_COMPRESSING).get(0)).getProcessTime();
        arrow = helper.drawableBuilder(FuelRefineryScreen.GUI, 196, 0, 24, 17).buildAnimated(processTime, IDrawableAnimated.StartDirection.LEFT, false);
        energy_glass = helper.drawableBuilder(FuelRefineryScreen.GUI, 212, 17, 16, 60).build();
        energy = helper.drawableBuilder(FuelRefineryScreen.GUI, 196, 17, 16, 60).buildAnimated(800, IDrawableAnimated.StartDirection.TOP, true);
        localized = I18n.format("recipe.cgalaxy.oxygen_compressing");
    }

    @Override
    public ResourceLocation getUid() {
        return JeiCompat.OXYGEN_COMPRESSING;
    }

    @Override
    public Class<? extends OxygenCompressorRecipe> getRecipeClass() {
        return OxygenCompressorRecipe.class;
    }

    @Override
    public String getTitle() {
        return localized;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(OxygenCompressorScreen.GUI, 21, 7, 172, 63);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(BlockInit.OXYGEN_COMPRESSOR.get()));
    }

    @Override
    public void setIngredients(OxygenCompressorRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getIngredient()));
        ingredients.setOutputs(VanillaTypes.FLUID, new ArrayList<>(recipe.getFluidResults()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, OxygenCompressorRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 35, 30);
        itemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        fluidStacks.init(0, false, 81, 11, 16, 50, 1000, false, null);
        fluidStacks.set(0, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
    }

    @Override
    public void draw(OxygenCompressorRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        arrow.draw(matrixStack, 55, 31);
        energy.draw(matrixStack, 151, 2);
        energy_glass.draw(matrixStack, 151, 2);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(OxygenCompressorRecipe recipe, double mouseX, double mouseY) {
        List<ITextComponent> list = new ArrayList<>();
        if (mouseX >= 151 && mouseX <= 167 && mouseY >= 2 && mouseY <= 62) {
            list.add(new TranslationTextComponent("tooltip.cgalaxy.screen.energy_usage").appendString(": 50FE/t"));
        }
        return list;
    }
}
