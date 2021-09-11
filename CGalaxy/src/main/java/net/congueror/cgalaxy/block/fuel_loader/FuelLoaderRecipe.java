package net.congueror.cgalaxy.block.fuel_loader;

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
    public boolean matches(@Nonnull IItemFluidInventory pContainer, @Nonnull Level pLevel) {
        return fluidMatches(pContainer);
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
        return CGRecipeSerializerInit.FUEL_LOADING.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return CGRecipeSerializerInit.Types.FUEL_LOADING;
    }

    public static class Serializer extends FluidRecipeSerializer<FuelLoaderRecipe> {

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
