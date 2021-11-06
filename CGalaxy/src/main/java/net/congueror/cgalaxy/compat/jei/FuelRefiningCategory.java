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
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryRecipe;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
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
        int processTime = ((FuelRefineryRecipe) JEICompat.getRecipes(CGRecipeSerializerInit.Types.FUEL_REFINING).get(0)).getProcessTime();
        arrow = helper.drawableBuilder(FuelRefineryScreen.GUI, 196, 0, 24, 17).buildAnimated(processTime, IDrawableAnimated.StartDirection.LEFT, false);
        energy_glass = helper.drawableBuilder(FuelRefineryScreen.GUI, 212, 17, 16, 60).build();
        energy = helper.drawableBuilder(FuelRefineryScreen.GUI, 196, 17, 16, 60).buildAnimated(666, IDrawableAnimated.StartDirection.TOP, true);
        localized = I18n.get("recipe.cgalaxy.fuel_refining");
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
    public Component getTitle() {
        return new TextComponent(localized);
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(FuelRefineryScreen.GUI, 21, 7, 172, 63);
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(CGBlockInit.FUEL_REFINERY.get()));
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
        arrow.draw(poseStack, 63, 28);
        energy.draw(poseStack, 151, 2);
        energy_glass.draw(poseStack, 151, 2);
    }

    @Nonnull
    @Override
    public List<Component> getTooltipStrings(@Nonnull FuelRefineryRecipe recipe, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if (mouseX >= 151 && mouseX <= 167 && mouseY >= 2 && mouseY <= 62) {
            list.add(new TranslatableComponent("tooltip.cgalaxy.screen.energy_usage").append(": 60FE/t"));
        }
        return list;
    }
}
