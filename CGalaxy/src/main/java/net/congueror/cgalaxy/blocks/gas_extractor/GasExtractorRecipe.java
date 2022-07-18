package net.congueror.cgalaxy.blocks.gas_extractor;

import com.google.gson.JsonObject;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.util.recipe.FluidRecipe;
import net.congueror.clib.util.recipe.RecipeSerializer;
import net.congueror.clib.util.recipe.IItemFluidInventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class GasExtractorRecipe implements FluidRecipe<IItemFluidInventory> {
    final ResourceLocation id;
    int processTime;
    Ingredient ingredient;
    FluidStack[] result = new FluidStack[2];
    int percentage;

    final int defaultProcess = 200;

    public GasExtractorRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public int getProcessTime() {
        return processTime;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public FluidStack getResult(double chance) {
        if (chance < (percentage / 100D)) {
            return result[1];
        }
        return result[0];
    }

    @Override
    public Collection<FluidStack> getFluidResults() {
        return Arrays.asList(result);
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
    public net.minecraft.world.item.crafting.RecipeSerializer<?> getSerializer() {
        return CGRecipeSerializerInit.GAS_EXTRACTING.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return CGRecipeSerializerInit.GAS_EXTRACTING_TYPE.get();
    }

    public static class Serializer extends RecipeSerializer<GasExtractorRecipe> {

        @Nonnull
        @Override
        public GasExtractorRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            GasExtractorRecipe recipe = new GasExtractorRecipe(recipeId);
            recipe.processTime = GsonHelper.getAsInt(json, "process_time", recipe.defaultProcess);
            recipe.ingredient = Ingredient.fromJson(json.get("ingredient"));
            recipe.result[0] = recipe.deserialize(json.getAsJsonObject("result").get("fluid1"), 10);
            if (json.getAsJsonObject("result").get("fluid2") != null) {
                recipe.result[1] = recipe.deserialize(json.getAsJsonObject("result").get("fluid2"), 5);
            } else {
                recipe.result[1] = FluidStack.EMPTY;
            }
            recipe.percentage = GsonHelper.getAsInt(json.get("result").getAsJsonObject(), "percentage", 0);
            return recipe;
        }

        @Nullable
        @Override
        public GasExtractorRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf buffer) {
            GasExtractorRecipe recipe = new GasExtractorRecipe(pRecipeId);
            recipe.processTime = buffer.readVarInt();
            recipe.ingredient = Ingredient.fromNetwork(buffer);
            recipe.result[0] = recipe.read(buffer);
            recipe.result[1] = recipe.read(buffer);
            recipe.percentage = buffer.readInt();
            return recipe;
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull GasExtractorRecipe recipe) {
            buffer.writeVarInt(recipe.processTime);
            recipe.ingredient.toNetwork(buffer);
            buffer.writeResourceLocation(Objects.requireNonNull(recipe.result[0].getFluid().getRegistryName()));
            buffer.writeVarInt(recipe.result[0].getAmount());
            buffer.writeResourceLocation(Objects.requireNonNull(recipe.result[1].getFluid().getRegistryName()));
            buffer.writeVarInt(recipe.result[1].getAmount());
            buffer.writeInt(recipe.percentage);
        }
    }
}
