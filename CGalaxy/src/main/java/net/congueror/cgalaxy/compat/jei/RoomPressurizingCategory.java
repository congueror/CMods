package net.congueror.cgalaxy.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerRecipe;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.compat.jei.AbstractJeiCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RoomPressurizingCategory extends AbstractJeiCategory<RoomPressurizerRecipe> {
    public RoomPressurizingCategory(IGuiHelper helper) {
        super(helper, "recipe.cgalaxy.room_pressurizing",
                ((RoomPressurizerRecipe) net.congueror.clib.compat.jei.JEICompat.getRecipes(CGRecipeSerializerInit.Types.ROOM_PRESSURIZING).get(0)).getProcessTime(),
                40000, 160, false);
    }

    @Override
    public ResourceLocation getUid() {
        return JEICompat.ROOM_PRESSURIZING;
    }

    @Override
    public Class<? extends RoomPressurizerRecipe> getRecipeClass() {
        return RoomPressurizerRecipe.class;
    }

    @Override
    public IDrawable getIcon() {
        return createIcon(CGBlockInit.ROOM_PRESSURIZER.get());
    }

    @Override
    public void setIngredients(RoomPressurizerRecipe recipe, IIngredients ingredients) {
        List<List<FluidStack>> list = new ArrayList<>();
        list.add(recipe.getFluidIngredients()[0].getFluidStacks());
        list.add(recipe.getFluidIngredients()[1].getFluidStacks());
        ingredients.setInputLists(VanillaTypes.FLUID, list);
        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidResults().stream().map(Collections::singletonList).collect(Collectors.toList()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RoomPressurizerRecipe recipe, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();
        fluidStacks.init(0, true, 52, 11, 16, 50, 1000, false, null);
        fluidStacks.init(1, true, 89, 11, 16, 50, 1000, false, null);
        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
        fluidStacks.set(1, ingredients.getInputs(VanillaTypes.FLUID).get(1));
    }

    @Override
    public void draw(RoomPressurizerRecipe recipe, @NotNull PoseStack stack, double mouseX, double mouseY) {
        super.draw(recipe, stack, mouseX, mouseY);
        arrow_vertical.draw(stack, 71, 22);
        arrow_vertical_anim.draw(stack, 71, 22);
        tank.draw(stack, 51, 10);
        tank.draw(stack, 88, 10);
    }
}
