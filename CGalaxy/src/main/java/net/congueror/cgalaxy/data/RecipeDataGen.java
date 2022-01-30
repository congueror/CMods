package net.congueror.cgalaxy.data;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.data.RecipeDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class RecipeDataGen extends RecipeDataProvider {
    public RecipeDataGen(DataGenerator pGenerator) {
        super(pGenerator, CGalaxy.MODID);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        super.buildCraftingRecipes(pFinishedRecipeConsumer);
    }
}
