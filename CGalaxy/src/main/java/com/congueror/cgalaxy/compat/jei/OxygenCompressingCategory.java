package com.congueror.cgalaxy.compat.jei;

import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import com.congueror.cgalaxy.block.oxygen_compressor.OxygenCompressorScreen;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.cgalaxy.recipes.FuelRefineryRecipe;
import com.congueror.cgalaxy.recipes.OxygenCompressorRecipe;
import com.congueror.clib.recipe.FluidIngredient;
import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.floats.Float2CharArrayMap;
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
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OxygenCompressingCategory implements IRecipeCategory<OxygenCompressorRecipe> {

    IGuiHelper helper;
    private final IDrawableAnimated arrow;
    private final IDrawableStatic energy_glass;
    private final IDrawableAnimated energy;
    private final String localized;

    public OxygenCompressingCategory(IGuiHelper helper) {
        this.helper = helper;
        int processTime = ((FuelRefineryRecipe) JeiCompat.getRecipes(RecipeSerializerInit.Types.FUEL_REFINING).get(0)).getProcessTime();
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
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());

        ingredients.setInputLists(VanillaTypes.FLUID, Collections.emptyList());
        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidResults().stream().map(Collections::singletonList).collect(Collectors.toList()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, OxygenCompressorRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 36, 31);
        //itemStacks.init(1, false, 36, 31);
        itemStacks.set(0, Arrays.asList(recipe.getIngredient().getMatchingStacks()));

        fluidStacks.init(0, false, 81, 11, 16, 50, 15000, true, null);
        //fluidStacks.init(1, true, 81, 11, 1, 1, 1, false, null);
        //fluidStacks.set(1, Collections.singletonList(FluidStack.EMPTY));
        List<List<FluidStack>> outputs = ingredients.getOutputs(VanillaTypes.FLUID);
        for (int i = 0; i < outputs.size(); ++i) {
            fluidStacks.set(i + 1, outputs.get(i));
        }
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
