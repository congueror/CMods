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
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.blocks.oxygen_compressor.OxygenCompressorRecipe;
import net.congueror.cgalaxy.blocks.oxygen_compressor.OxygenCompressorScreen;
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

public class OxygenCompressingCategory implements IRecipeCategory<OxygenCompressorRecipe> {

    IGuiHelper helper;
    private final IDrawableAnimated arrow;
    private final IDrawableStatic energy_glass;
    private final IDrawableAnimated energy;
    private final String localized;

    public OxygenCompressingCategory(IGuiHelper helper) {
        this.helper = helper;
        int processTime = ((OxygenCompressorRecipe) JEICompat.getRecipes(CGRecipeSerializerInit.Types.OXYGEN_COMPRESSING).get(0)).getProcessTime();
        arrow = helper.drawableBuilder(FuelRefineryScreen.GUI, 196, 0, 24, 17).buildAnimated(processTime, IDrawableAnimated.StartDirection.LEFT, false);
        energy_glass = helper.drawableBuilder(FuelRefineryScreen.GUI, 212, 17, 16, 60).build();
        energy = helper.drawableBuilder(FuelRefineryScreen.GUI, 196, 17, 16, 60).buildAnimated(800, IDrawableAnimated.StartDirection.TOP, true);
        localized = I18n.get("recipe.cgalaxy.oxygen_compressing");
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return JEICompat.OXYGEN_COMPRESSING;
    }

    @Nonnull
    @Override
    public Class<? extends OxygenCompressorRecipe> getRecipeClass() {
        return OxygenCompressorRecipe.class;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return new TextComponent(localized);
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(OxygenCompressorScreen.GUI, 21, 7, 172, 63);
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(CGBlockInit.OXYGEN_COMPRESSOR.get()));
    }

    @Override
    public void setIngredients(OxygenCompressorRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(Collections.singletonList(recipe.getIngredient()));
        ingredients.setOutputs(VanillaTypes.FLUID, new ArrayList<>(recipe.getFluidResults()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull OxygenCompressorRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 35, 30);
        itemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        fluidStacks.init(0, false, 81, 11, 16, 50, 1000, false, null);
        fluidStacks.set(0, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
    }

    @Override
    public void draw(@Nonnull OxygenCompressorRecipe recipe, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
        arrow.draw(poseStack, 55, 31);
        energy.draw(poseStack, 151, 2);
        energy_glass.draw(poseStack, 151, 2);
    }

    @Nonnull
    @Override
    public List<Component> getTooltipStrings(@Nonnull OxygenCompressorRecipe recipe, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if (mouseX >= 151 && mouseX <= 167 && mouseY >= 2 && mouseY <= 62) {
            list.add(new TranslatableComponent("key.cgalaxy.energy_usage").append(": 50FE/t"));
        }
        return list;
    }
}
