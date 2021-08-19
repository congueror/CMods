package com.congueror.cgalaxy.recipes;

import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.clib.api.recipe.FluidIngredient;
import com.congueror.clib.api.recipe.IFluidRecipe;
import com.congueror.clib.api.recipe.IItemFluidInventory;
import com.google.gson.JsonElement;
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
import java.util.Objects;

public class FuelRefineryRecipe implements IFluidRecipe<IItemFluidInventory> {
    final ResourceLocation id;
    int processTime;
    FluidIngredient ingredient;
    FluidStack result;

    final int defaultProcess = 1000;

    public FuelRefineryRecipe(ResourceLocation id) {
        this.id = id;
    }

    public FluidIngredient getIngredient() {
        return ingredient;
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
    public FluidStack getFluidResult() {
        return result;
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
        return RecipeSerializerInit.FUEL_REFINING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeSerializerInit.Types.FUEL_REFINING;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FuelRefineryRecipe> {

        @Override
        public FuelRefineryRecipe read(ResourceLocation recipeId, JsonObject json) {
            FuelRefineryRecipe recipe = new FuelRefineryRecipe(recipeId);
            recipe.processTime = JSONUtils.getInt(json, "process_time", recipe.defaultProcess);
            recipe.ingredient = FluidIngredient.deserialize(json.get("ingredient"));
            JsonElement result = json.get("result");
            recipe.result = recipe.deserialize(result, 100);
            return recipe;
        }

        @Nullable
        @Override
        public FuelRefineryRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            FuelRefineryRecipe recipe = new FuelRefineryRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = FluidIngredient.read(buffer);
            recipe.result = recipe.read(buffer);
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, FuelRefineryRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
            buffer.writeResourceLocation(Objects.requireNonNull(recipe.result.getFluid().getRegistryName()));
            buffer.writeVarInt(recipe.result.getAmount());
        }
    }
}
