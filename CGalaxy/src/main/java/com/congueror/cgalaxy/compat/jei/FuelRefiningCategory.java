package com.congueror.cgalaxy.compat.jei;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.cgalaxy.recipes.FuelRefineryRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
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

public class FuelRefiningCategory implements IRecipeCategory<FuelRefineryRecipe> {

    IGuiHelper helper;
    private final IDrawableAnimated arrow;
    private final IDrawableStatic energy_glass;
    private final IDrawableAnimated energy;
    private final String localized;

    public FuelRefiningCategory(IGuiHelper helper) {
        this.helper = helper;
        int processTime = ((FuelRefineryRecipe) JeiCompat.getRecipes(RecipeSerializerInit.Types.FUEL_REFINING).get(0)).getProcessTime();
        arrow = helper.drawableBuilder(FuelRefineryScreen.GUI, 196, 0, 24, 17).buildAnimated(processTime, IDrawableAnimated.StartDirection.LEFT, false);
        energy_glass = helper.drawableBuilder(FuelRefineryScreen.GUI, 212, 17, 16, 60).build();
        energy = helper.drawableBuilder(FuelRefineryScreen.GUI, 196, 17, 16, 60).buildAnimated(666, IDrawableAnimated.StartDirection.TOP, true);
        localized = I18n.format("recipe.cgalaxy.fuel_refining");
    }

    @Override
    public ResourceLocation getUid() {
        return JeiCompat.FUEL_REFINING;
    }

    @Override
    public Class<? extends FuelRefineryRecipe> getRecipeClass() {
        return FuelRefineryRecipe.class;
    }

    @Override
    public String getTitle() {
        return localized;
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(FuelRefineryScreen.GUI, 21, 7, 172, 63);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(BlockInit.FUEL_REFINERY.get()));
    }

    @Override
    public void setIngredients(FuelRefineryRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getIngredient().getFluidStacks()));
        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidResults().stream().map(Collections::singletonList).collect(Collectors.toList()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FuelRefineryRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(0, true, 45, 11, 16, 50, 1000, false, null);
        fluidStacks.init(1, false, 90, 11, 16, 50, 1000, false, null);

        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
        List<List<FluidStack>> outputs = ingredients.getOutputs(VanillaTypes.FLUID);
        for (int i = 0; i < outputs.size(); ++i) {
            fluidStacks.set(i + 1, outputs.get(i));
        }
    }

    @Override
    public void draw(FuelRefineryRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        arrow.draw(matrixStack, 63, 28);
        energy.draw(matrixStack, 151, 2);
        energy_glass.draw(matrixStack, 151, 2);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(FuelRefineryRecipe recipe, double mouseX, double mouseY) {
        List<ITextComponent> list = new ArrayList<>();
        if (mouseX >= 151 && mouseX <= 167 && mouseY >= 2 && mouseY <= 62) {
            list.add(new TranslationTextComponent("tooltip.cgalaxy.screen.energy_usage").appendString(": 60FE/t"));
        }
        return list;
    }
}
