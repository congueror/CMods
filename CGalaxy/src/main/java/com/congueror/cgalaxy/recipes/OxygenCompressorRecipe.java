package com.congueror.cgalaxy.recipes;

import com.congueror.cgalaxy.init.RecipeSerializerInit;
import com.congueror.clib.recipe.IFluidRecipe;
import com.congueror.clib.recipe.IItemFluidInventory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Objects;

public class OxygenCompressorRecipe implements IFluidRecipe<IItemFluidInventory> {

    final ResourceLocation id;
    int processTime;
    Ingredient ingredient;
    FluidStack result;

    final int defaultProcess = 500;

    public OxygenCompressorRecipe(ResourceLocation id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public int getProcessTime() {
        return processTime;
    }

    @Override
    public boolean fluidMatches(IItemFluidInventory handler) {
        return true;
    }

    @Override
    public FluidStack getFluidResult() {
        return result;
    }

    @Override
    public boolean matches(IItemFluidInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
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
        return RecipeSerializerInit.OXYGEN_COMPRESSING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeSerializerInit.Types.OXYGEN_COMPRESSING;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<OxygenCompressorRecipe> {

        @Override
        public OxygenCompressorRecipe read(ResourceLocation recipeId, JsonObject json) {
            OxygenCompressorRecipe recipe = new OxygenCompressorRecipe(recipeId);
            recipe.processTime = JSONUtils.getInt(json, "process_time", recipe.defaultProcess);
            recipe.ingredient = Ingredient.deserialize(json.get("ingredient"));
            JsonElement result = json.get("result");
            recipe.result = recipe.deserialize(result, 10);
            return recipe;
        }

        @Nullable
        @Override
        public OxygenCompressorRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            OxygenCompressorRecipe recipe = new OxygenCompressorRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = Ingredient.read(buffer);
            recipe.result = recipe.read(buffer);
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, OxygenCompressorRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
            buffer.writeResourceLocation(Objects.requireNonNull(recipe.result.getFluid().getRegistryName()));
            buffer.writeVarInt(recipe.result.getAmount());
        }
    }
}
