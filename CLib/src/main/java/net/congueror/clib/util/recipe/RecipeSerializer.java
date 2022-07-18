package net.congueror.clib.util.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Convenience class. Extend this class in your recipe serializer classes if you want to.
 * @param <T> {@link Recipe}, {@link ItemRecipe}, {@link FluidRecipe}
 */
public abstract class RecipeSerializer<T extends Recipe<?>> extends ForgeRegistryEntry<net.minecraft.world.item.crafting.RecipeSerializer<?>> implements net.minecraft.world.item.crafting.RecipeSerializer<T> {
}
