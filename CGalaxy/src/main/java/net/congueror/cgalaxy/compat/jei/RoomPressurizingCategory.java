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
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerRecipe;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.compat.jei.AbstractJeiCategory;
import net.congueror.clib.compat.jei.CLJEICompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RoomPressurizingCategory extends AbstractJeiCategory<RoomPressurizerRecipe> {
    public RoomPressurizingCategory(RecipeType<RoomPressurizerRecipe> recipeType, IGuiHelper helper) {
        super(recipeType, helper, "recipe.cgalaxy.room_pressurizing",
                CLJEICompat.getRecipes(CGRecipeSerializerInit.ROOM_PRESSURIZING_TYPE.get()).get(0).getProcessTime(),
                40000, 160, false);
    }

    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.ROOM_PRESSURIZER.get());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RoomPressurizerRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 52, 11)
                .setFluidRenderer(1000L, false, 16, 50)
                .addIngredients(ForgeTypes.FLUID_STACK, recipe.getFluidIngredients()[0].getFluidStacks());
        builder.addSlot(RecipeIngredientRole.INPUT, 89, 11)
                .setFluidRenderer(1000L, false, 16, 50)
                .addIngredients(ForgeTypes.FLUID_STACK, recipe.getFluidIngredients()[1].getFluidStacks());
    }

    @Override
    public void draw(RoomPressurizerRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
        arrow_vertical.draw(poseStack, 71, 22);
        arrow_vertical_anim.draw(poseStack, 71, 22);
        tank.draw(poseStack, 51, 10);
        tank.draw(poseStack, 88, 10);
    }
}
