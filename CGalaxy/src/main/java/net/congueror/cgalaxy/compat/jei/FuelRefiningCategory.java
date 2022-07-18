package net.congueror.cgalaxy.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryRecipe;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.compat.jei.AbstractJeiCategory;
import net.congueror.clib.compat.jei.CLJEICompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FuelRefiningCategory extends AbstractJeiCategory<FuelRefineryRecipe> {

    public FuelRefiningCategory(RecipeType<FuelRefineryRecipe> recipeType, IGuiHelper helper) {
        super(recipeType, helper, "recipe.cgalaxy.fuel_refining",
                CLJEICompat.getRecipes(CGRecipeSerializerInit.FUEL_REFINING_TYPE.get()).get(0).getProcessTime(),
                40000, 60, false);
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.FUEL_REFINERY.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FuelRefineryRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 45, 11)
                .setFluidRenderer(1000L, false, 16, 50)
                .addIngredients(ForgeTypes.FLUID_STACK, recipe.getIngredient().getFluidStacks());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 11)
                .setFluidRenderer(1000L, false, 16, 50)
                .addIngredients(ForgeTypes.FLUID_STACK, recipe.getFluidResults().stream().toList());
    }

    @Override
    public void draw(FuelRefineryRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
        arrow.draw(poseStack, 63, 28);
        arrow_anim.draw(poseStack, 63, 28);
        tank.draw(poseStack, 44, 10);
        tank.draw(poseStack, 89, 10);
    }
}
