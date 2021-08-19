package com.congueror.cgalaxy.recipes;

import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.clib.api.recipe.FluidIngredient;
import com.congueror.clib.api.recipe.IFluidRecipe;
import com.congueror.clib.api.recipe.IItemFluidInventory;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class FuelLoaderRecipe implements IFluidRecipe<IItemFluidInventory> {

    final ResourceLocation id;
    int processTime;
    FluidIngredient ingredient;

    final int defaultProcess = 600;

    public FuelLoaderRecipe(ResourceLocation id) {
        this.id = id;
    }

    public FluidIngredient getIngredient() {
        return ingredient;
    }

    @Override
    public FluidStack getFluidResult() {
        return FluidStack.EMPTY;
    }

    @Override
    public int getProcessTime() {
        return processTime;
    }

    @Override
    public boolean fluidMatches(IItemFluidInventory handler) {
        return this.ingredient.test(handler.getFluidInTank(0));
    }

    @Override
    public boolean matches(IItemFluidInventory inv, World worldIn) {
        return fluidMatches(inv);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.FUEL_LOADING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeSerializerInit.Types.FUEL_LOADING;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FuelLoaderRecipe> {

        @Override
        public FuelLoaderRecipe read(ResourceLocation recipeId, JsonObject json) {
            FuelLoaderRecipe recipe = new FuelLoaderRecipe(recipeId);
            recipe.processTime = JSONUtils.getInt(json, "process_time", recipe.defaultProcess);
            recipe.ingredient = FluidIngredient.deserialize(json.get("ingredient"));
            return recipe;
        }

        @Nullable
        @Override
        public FuelLoaderRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            FuelLoaderRecipe recipe = new FuelLoaderRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = FluidIngredient.read(buffer);
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, FuelLoaderRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
        }
    }
}
