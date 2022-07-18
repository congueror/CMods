package net.congueror.cgalaxy.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorRecipe;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.compat.jei.AbstractJeiCategory;
import net.congueror.clib.compat.jei.CLJEICompat;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GasExtractingCategory extends AbstractJeiCategory<GasExtractorRecipe> {

    public GasExtractingCategory(RecipeType<GasExtractorRecipe> recipeType, IGuiHelper helper) {
        super(recipeType, helper, "recipe.cgalaxy.gas_extracting",
                CLJEICompat.getRecipes(CGRecipeSerializerInit.GAS_EXTRACTING_TYPE.get()).get(0).getProcessTime(),
                40000, 50, false);
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.GAS_EXTRACTOR.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, GasExtractorRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 36, 31)
                .addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 11)
                .setFluidRenderer(1000L, false, 16, 50)
                .addIngredients(ForgeTypes.FLUID_STACK, recipe.getFluidResults().stream().toList());
    }

    @Override
    public void draw(GasExtractorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
        arrow.draw(poseStack, 55, 31);
        arrow_anim.draw(poseStack, 55, 31);
        tank.draw(poseStack, 80, 10);
        slot.draw(poseStack, 35, 30);
    }
}
