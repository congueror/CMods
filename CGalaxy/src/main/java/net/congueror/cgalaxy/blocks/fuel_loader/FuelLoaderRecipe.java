package net.congueror.cgalaxy.blocks.fuel_loader;

import com.google.gson.JsonObject;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.util.recipe.FluidIngredient;
import net.congueror.clib.util.recipe.FluidRecipe;
import net.congueror.clib.util.recipe.IItemFluidInventory;
import net.congueror.clib.util.recipe.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FuelLoaderRecipe implements FluidRecipe<IItemFluidInventory> {
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
    public boolean matches(@Nonnull IItemFluidInventory pContainer, @Nonnull Level pLevel) {
        return this.ingredient.test(pContainer.getFluidInTank(0));
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
        return CGRecipeSerializerInit.FUEL_LOADING.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return CGRecipeSerializerInit.FUEL_LOADING_TYPE.get();
    }

    public static class Serializer extends RecipeSerializer<FuelLoaderRecipe> {

        @Nonnull
        @Override
        public FuelLoaderRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pSerializedRecipe) {
            FuelLoaderRecipe recipe = new FuelLoaderRecipe(pRecipeId);
            recipe.processTime = GsonHelper.getAsInt(pSerializedRecipe, "process_time", recipe.defaultProcess);
            recipe.ingredient = FluidIngredient.deserialize(pSerializedRecipe.get("ingredient"));
            return recipe;
        }

        @Nullable
        @Override
        public FuelLoaderRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf pBuffer) {
            FuelLoaderRecipe recipe = new FuelLoaderRecipe(pRecipeId);
            recipe.processTime = pBuffer.readVarInt();
            recipe.ingredient = FluidIngredient.read(pBuffer);
            return recipe;
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, @Nonnull FuelLoaderRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.processTime);
            pRecipe.ingredient.write(pBuffer);
        }
    }
}
