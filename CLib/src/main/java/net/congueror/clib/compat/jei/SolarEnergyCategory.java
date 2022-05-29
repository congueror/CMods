package net.congueror.clib.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.congueror.clib.CLib;
import net.congueror.clib.blocks.solar_generator.SolarGeneratorRecipe;
import net.congueror.clib.blocks.solar_generator.SolarGeneratorScreen;
import net.congueror.clib.init.CLBlockInit;
import net.congueror.clib.init.CLItemInit;
import net.congueror.clib.init.CLRecipeSerializerInit;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static net.congueror.clib.blocks.abstract_machine.item.AbstractItemScreen.elementHeight;
import static net.congueror.clib.blocks.abstract_machine.item.AbstractItemScreen.elementWidth;

public class SolarEnergyCategory implements IRecipeCategory<SolarGeneratorRecipe> {
    IGuiHelper helper;
    private final IDrawableStatic arrow;
    private final IDrawableStatic energy_glass;
    private final IDrawableAnimated energy;
    private final String localized;

    public SolarEnergyCategory(IGuiHelper helper) {
        this.helper = helper;
        int processTime = ((SolarGeneratorRecipe) JEICompat.getRecipes(CLRecipeSerializerInit.SOLAR_ENERGY_TYPE.get()).get(0)).getProcessTime();
        ResourceLocation e = new ResourceLocation(CLib.MODID, "textures/gui/screen_elements.png");
        arrow = helper.drawableBuilder(e, 32, 43, elementWidth, elementHeight).build();
        energy_glass = helper.drawableBuilder(e, 16, 0, elementWidth, elementHeight).build();
        energy = helper.drawableBuilder(e, 0, 0, elementWidth, elementHeight).buildAnimated(1000, IDrawableAnimated.StartDirection.BOTTOM, true);
        localized = I18n.get("recipe.clib.solar_energy");
    }

    @Override
    public ResourceLocation getUid() {
        return JEICompat.SOLAR_ENERGY;
    }

    @Override
    public Class<? extends SolarGeneratorRecipe> getRecipeClass() {
        return SolarGeneratorRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent(localized);
    }

    @Override
    public IDrawable getBackground() {
        return helper.createDrawable(SolarGeneratorScreen.GUI, 21, 7, 172, 63);
    }

    @Override
    public IDrawable getIcon() {
        return helper.createDrawableIngredient(new ItemStack(CLBlockInit.SOLAR_GENERATOR_1.get()));
    }

    @Override
    public void setIngredients(SolarGeneratorRecipe recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, new ItemStack(CLItemInit.GHOST_ITEM.get()));
        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(CLItemInit.GHOST_ITEM.get()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SolarGeneratorRecipe recipe, IIngredients ingredients) {

    }

    @Override
    public void draw(@Nonnull SolarGeneratorRecipe recipe, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
        arrow.draw(poseStack, 68, 13, 43, 0, 32, 0);
        energy.draw(poseStack, 98, 0);
        energy_glass.draw(poseStack, 98, 0, 0, 0, 16, 24);
    }

    @Nonnull
    @Override
    public List<Component> getTooltipStrings(@Nonnull SolarGeneratorRecipe recipe, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if (mouseX >= 97 && mouseX <= 114 && mouseY >= 2 && mouseY <= 62) {
            list.add(new TranslatableComponent("key.clib.energy_generation").append(": 10FE/t"));
        }
        return list;
    }
}
