package net.congueror.cgalaxy.blocks.fuel_refinery;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.api.recipe.FluidIngredient;
import net.congueror.clib.api.recipe.IFluidRecipe;
import net.congueror.clib.api.recipe.IItemFluidInventory;
import net.congueror.clib.api.recipe.FluidRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
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
    public boolean matches(@Nonnull IItemFluidInventory pContainer, @Nonnull Level pLevel) {
        return fluidMatches(pContainer);
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public FluidStack getFluidResult() {
        return result;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return CGRecipeSerializerInit.FUEL_REFINING.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return CGRecipeSerializerInit.Types.FUEL_REFINING;
    }

    public static class Serializer extends FluidRecipeSerializer<FuelRefineryRecipe> {

        @Nonnull
        @Override
        public FuelRefineryRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject json) {
            FuelRefineryRecipe recipe = new FuelRefineryRecipe(pRecipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", recipe.defaultProcess);
            recipe.ingredient = FluidIngredient.deserialize(json.get("ingredient"));
            JsonElement result = json.get("result");
            recipe.result = recipe.deserialize(result, 100);
            return recipe;
        }

        @Nullable
        @Override
        public FuelRefineryRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
            FuelRefineryRecipe recipe = new FuelRefineryRecipe(recipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = FluidIngredient.read(buffer);
            recipe.result = recipe.read(buffer);
            return recipe;
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull FuelRefineryRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.write(buffer);
            buffer.writeResourceLocation(Objects.requireNonNull(recipe.result.getFluid().getRegistryName()));
            buffer.writeVarInt(recipe.result.getAmount());
        }
    }
}
