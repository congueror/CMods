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

public class FuelLoadingCategory implements IRecipeCategory<FuelLoaderRecipe> {

    IGuiHelper helper;
    private final IDrawableAnimated arrow;
    private final IDrawableStatic energy_glass;
    private final IDrawableAnimated energy;
    private final String localized;

    public FuelLoadingCategory(IGuiHelper helper) {
        this.helper = helper;
        int processTime = ((FuelLoaderRecipe) JEICompat.getRecipes(CGRecipeSerializerInit.Types.FUEL_LOADING).get(0)).getProcessTime();
        arrow = helper.drawableBuilder(FuelLoaderScreen.GUI, 228, 0, 17, 31).buildAnimated(processTime, IDrawableAnimated.StartDirection.BOTTOM, false);
        energy_glass = helper.drawableBuilder(FuelLoaderScreen.GUI, 212, 0, 16, 60).build();
        energy = helper.drawableBuilder(FuelLoaderScreen.GUI, 196, 0, 16, 60).buildAnimated(1333, IDrawableAnimated.StartDirection.TOP, true);
        localized = I18n.get("recipe.cgalaxy.fuel_loading");
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
    public Component getTitle() {
        return new TextComponent(localized);
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(FuelLoaderScreen.GUI, 21, 7, 172, 63);
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(CGBlockInit.FUEL_LOADER.get()));
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
        arrow.draw(poseStack, 61, 22);
        energy.draw(poseStack, 151, 2);
        energy_glass.draw(poseStack, 151, 2);
    }

    @Nonnull
    @Override
    public List<Component> getTooltipStrings(@Nonnull FuelLoaderRecipe recipe, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if (mouseX >= 151 && mouseX <= 167 && mouseY >= 2 && mouseY <= 62) {
            list.add(new TranslatableComponent("key.cgalaxy.energy_usage").append(": 30FE/t"));
        }
        return list;
    }
}
