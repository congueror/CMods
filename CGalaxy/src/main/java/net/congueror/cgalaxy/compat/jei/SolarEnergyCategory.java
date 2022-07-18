package net.congueror.cgalaxy.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.congueror.cgalaxy.blocks.solar_generator.SolarGeneratorRecipe;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.compat.jei.AbstractJeiCategory;
import net.congueror.clib.compat.jei.CLJEICompat;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class SolarEnergyCategory extends AbstractJeiCategory<SolarGeneratorRecipe> {

    public SolarEnergyCategory(RecipeType<SolarGeneratorRecipe> recipeType, IGuiHelper helper) {
        super(recipeType, helper, "recipe.clib.solar_energy",
                CLJEICompat.getRecipes(CGRecipeSerializerInit.SOLAR_ENERGY_TYPE.get()).get(0).getProcessTime(),
                40000, 10, true);
    }

    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.SOLAR_GENERATOR_1.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SolarGeneratorRecipe recipe, IFocusGroup focuses) {

    }

    int i;

    @Override
    public void draw(SolarGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        energy_slot.draw(poseStack, 97, 0);
        energy.draw(poseStack, 98, 1);
        energy_glass.draw(poseStack, 98, 1);

        slot.draw(poseStack, 38, 24);
        ResourceLocation rl = new ResourceLocation(recipe.getTexture());
        int v = 0;
        i++;
        if (i > 200) {
            v = 16;
            if (i > 400) {
                i = 0;
            }
        }
        helper.drawableBuilder(rl, 0, v, 16, 16).setTextureSize(16, 32).build().draw(poseStack, 39, 25);

        arrow.draw(poseStack, 58, 25);
    }

    @Override
    public List<Component> getTooltipStrings(SolarGeneratorRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if (mouseX >= 98 && mouseX <= 114 && mouseY >= 1 && mouseY <= 61) {
            list.add(new TextComponent("Day ").append(new TranslatableComponent("key.clib.energy_generation")).append(": " + (int)(fePerTick * recipe.getDayIntensity()) + "FE/t"));
            list.add(new TextComponent("Night ").append(new TranslatableComponent("key.clib.energy_generation")).append(": " + (int)(fePerTick * recipe.getNightIntensity()) + "FE/t"));
        }
        if (mouseX >= 39 && mouseX <= 55 && mouseY >= 25 && mouseY <= 41) {
            list.add(new TextComponent(recipe.getDimension().location().toString()));
        }
        return list;
    }
}
