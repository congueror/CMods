package net.congueror.cgalaxy.blocks.room_pressurizer;

import com.google.gson.JsonObject;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.api.recipe.FluidIngredient;
import net.congueror.clib.api.recipe.FluidRecipeSerializer;
import net.congueror.clib.api.recipe.FluidRecipe;
import net.congueror.clib.api.recipe.IItemFluidInventory;
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

public class RoomPressurizerRecipe implements FluidRecipe<IItemFluidInventory> {
    final ResourceLocation id;
    int processTime;
    FluidIngredient ingredient;

    final int defaultProcess = 600;

    public RoomPressurizerRecipe(ResourceLocation id) {
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
        return CGRecipeSerializerInit.ROOM_PRESSURIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return CGRecipeSerializerInit.Types.ROOM_PRESSURIZING;
    }

    public static class Serializer extends FluidRecipeSerializer<RoomPressurizerRecipe> {

        @Nonnull
        @Override
        public RoomPressurizerRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pSerializedRecipe) {
            RoomPressurizerRecipe recipe = new RoomPressurizerRecipe(pRecipeId);
            recipe.processTime = GsonHelper.getAsInt(pSerializedRecipe, "process_time", recipe.defaultProcess);
            recipe.ingredient = FluidIngredient.deserialize(pSerializedRecipe.get("ingredient"));
            return recipe;
        }

        @Nullable
        @Override
        public RoomPressurizerRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            RoomPressurizerRecipe recipe = new RoomPressurizerRecipe(pRecipeId);
            recipe.processTime = pBuffer.readVarInt();
            recipe.ingredient = FluidIngredient.read(pBuffer);
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, RoomPressurizerRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.processTime);
            pRecipe.ingredient.write(pBuffer);
        }
    }
}
