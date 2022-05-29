package net.congueror.cgalaxy.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderRecipe;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.CLib;
import net.congueror.clib.compat.jei.AbstractJeiCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FuelLoadingCategory extends AbstractJeiCategory<FuelLoaderRecipe> {

    public FuelLoadingCategory(IGuiHelper helper) {
        super(helper, "recipe.cgalaxy.fuel_loading",
                ((FuelLoaderRecipe) net.congueror.clib.compat.jei.JEICompat.getRecipes(CGRecipeSerializerInit.FUEL_LOADING_TYPE.get()).get(0)).getProcessTime(),
                40000, 30, false);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return JEICompat.FUEL_LOADING;
    }

    @Nonnull
    @Override
    public Class<? extends FuelLoaderRecipe> getRecipeClass() {
        return FuelLoaderRecipe.class;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.FUEL_LOADER.get());
    }

    @Override
    public void setIngredients(FuelLoaderRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getIngredient().getFluidStacks()));
        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidResults().stream().map(Collections::singletonList).collect(Collectors.toList()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull FuelLoaderRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(0, true, 79, 11, 16, 50, 1000, false, null);
        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
    }

    @Override
    public void draw(@Nonnull FuelLoaderRecipe recipe, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
        super.draw(recipe, poseStack, mouseX, mouseY);
        arrow_vertical.draw(poseStack, 61, 22);
        arrow_vertical_anim.draw(poseStack, 61, 22);
        tank.draw(poseStack, 78, 10);
    }
}
