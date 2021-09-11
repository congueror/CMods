package net.congueror.clib.api.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Convenience class. Extend this class in your recipe serializer classes if you want to.
 * @param <T> {@link IFluidRecipe}
 */
public abstract class FluidRecipeSerializer<T extends IFluidRecipe<?>> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
}
