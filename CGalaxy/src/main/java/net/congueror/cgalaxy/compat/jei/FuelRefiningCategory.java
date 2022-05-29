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
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryRecipe;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.CLib;
import net.congueror.clib.compat.jei.AbstractJeiCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FuelRefiningCategory extends AbstractJeiCategory<FuelRefineryRecipe> {

    public FuelRefiningCategory(IGuiHelper helper) {
        super(helper, "recipe.cgalaxy.fuel_refining",
                ((FuelRefineryRecipe) net.congueror.clib.compat.jei.JEICompat.getRecipes(CGRecipeSerializerInit.FUEL_REFINING_TYPE.get()).get(0)).getProcessTime(),
                40000, 60, false);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return JEICompat.FUEL_REFINING;
    }

    @Nonnull
    @Override
    public Class<? extends FuelRefineryRecipe> getRecipeClass() {
        return FuelRefineryRecipe.class;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.FUEL_REFINERY.get());
    }

    @Override
    public void setIngredients(FuelRefineryRecipe recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getIngredient().getFluidStacks()));
        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidResults().stream().map(Collections::singletonList).collect(Collectors.toList()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull FuelRefineryRecipe recipe, IIngredients ingredients) {
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
    public void draw(@Nonnull FuelRefineryRecipe recipe, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
        super.draw(recipe, poseStack, mouseX, mouseY);
        arrow.draw(poseStack, 63, 28);
        arrow_anim.draw(poseStack, 63, 28);
        tank.draw(poseStack, 44, 10);
        tank.draw(poseStack, 89, 10);
    }
}
