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
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderRecipe;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.compat.jei.AbstractJeiCategory;
import net.congueror.clib.compat.jei.CLJEICompat;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.stream.Collectors;

public class FuelLoadingCategory extends AbstractJeiCategory<FuelLoaderRecipe> {

    public FuelLoadingCategory(RecipeType<FuelLoaderRecipe> recipeType, IGuiHelper helper) {
        super(recipeType, helper, "recipe.cgalaxy.fuel_loading",
                CLJEICompat.getRecipes(CGRecipeSerializerInit.FUEL_LOADING_TYPE.get()).get(0).getProcessTime(),
                40000, 30, false);
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.FUEL_LOADER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FuelLoaderRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 79, 11)
                .setFluidRenderer(1000L, false, 16, 50)
                .addIngredients(ForgeTypes.FLUID_STACK, recipe.getIngredient().getFluidStacks());
    }

    @Override
    public void draw(FuelLoaderRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
        arrow_vertical.draw(poseStack, 61, 22);
        arrow_vertical_anim.draw(poseStack, 61, 22);
        tank.draw(poseStack, 78, 10);
    }
}
