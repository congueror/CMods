package net.congueror.cgalaxy.blocks.oxygen_compressor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.api.recipe.FluidRecipe;
import net.congueror.clib.api.recipe.IItemFluidInventory;
import net.congueror.clib.api.recipe.FluidRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class OxygenCompressorRecipe implements FluidRecipe<IItemFluidInventory> {
    final ResourceLocation id;
    int processTime;
    Ingredient ingredient;
    FluidStack result;

    final int defaultProcess = 500;

    public OxygenCompressorRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public int getProcessTime() {
        return processTime;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public FluidStack getFluidResult() {
        return result;
    }

    @Override
    public boolean matches(IItemFluidInventory pContainer, @Nonnull Level pLevel) {
        return this.ingredient.test(pContainer.getItem(0));
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return CGRecipeSerializerInit.OXYGEN_COMPRESSING.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return CGRecipeSerializerInit.Types.OXYGEN_COMPRESSING;
    }

    public static class Serializer extends FluidRecipeSerializer<OxygenCompressorRecipe> {

        @Nonnull
        @Override
        public OxygenCompressorRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            OxygenCompressorRecipe recipe = new OxygenCompressorRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", recipe.defaultProcess);
            recipe.ingredient = Ingredient.fromJson(json.get("ingredient"));
            JsonElement result = json.get("result");
            recipe.result = recipe.deserialize(result, 10);
            return recipe;
        }

        @Nullable
        @Override
        public OxygenCompressorRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf buffer) {
            OxygenCompressorRecipe recipe = new OxygenCompressorRecipe(pRecipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = Ingredient.fromNetwork(buffer);
            recipe.result = recipe.read(buffer);
            return recipe;
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull OxygenCompressorRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeResourceLocation(Objects.requireNonNull(recipe.result.getFluid().getRegistryName()));
            buffer.writeVarInt(recipe.result.getAmount());
        }
    }
}
