package net.congueror.cgalaxy.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
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
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorRecipe;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorScreen;
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

public class GasExtractingCategory extends AbstractJeiCategory<GasExtractorRecipe> {

    public GasExtractingCategory(IGuiHelper helper) {
        super(helper, "recipe.cgalaxy.gas_extracting",
                ((GasExtractorRecipe) net.congueror.clib.compat.jei.JEICompat.getRecipes(CGRecipeSerializerInit.GAS_EXTRACTING_TYPE.get()).get(0)).getProcessTime(),
                40000, 50, false);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return JEICompat.OXYGEN_COMPRESSING;
    }

    @Nonnull
    @Override
    public Class<? extends GasExtractorRecipe> getRecipeClass() {
        return GasExtractorRecipe.class;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.GAS_EXTRACTOR.get());
    }

    @Override
    public void setIngredients(GasExtractorRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getIngredient()));
        ingredients.setOutputs(VanillaTypes.FLUID, new ArrayList<>(recipe.getFluidResults()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull GasExtractorRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 35, 30);
        itemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        fluidStacks.init(0, false, 81, 11, 16, 50, 1000, false, null);
        fluidStacks.set(0, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
    }

    @Override
    public void draw(@Nonnull GasExtractorRecipe recipe, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
        super.draw(recipe, poseStack, mouseX, mouseY);
        arrow.draw(poseStack, 55, 31);
        arrow_anim.draw(poseStack, 55, 31);
        tank.draw(poseStack, 80, 10);
        slot.draw(poseStack, 35, 30);
    }
}
