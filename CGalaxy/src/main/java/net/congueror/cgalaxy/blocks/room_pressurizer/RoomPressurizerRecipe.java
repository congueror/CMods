package net.congueror.cgalaxy.blocks.room_pressurizer;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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
import java.util.ArrayList;
import java.util.List;

public class RoomPressurizerRecipe implements FluidRecipe<IItemFluidInventory> {
    final ResourceLocation id;
    int processTime;
    FluidIngredient ingredient;
    FluidIngredient ingredient1;
    int percentage;
    int percentage1;

    final int defaultProcess = 600;

    public RoomPressurizerRecipe(ResourceLocation id) {
        this.id = id;
    }

    public FluidIngredient[] getFluidIngredients() {
        return new FluidIngredient[]{ingredient, ingredient1};
    }

    public int[] getPercentages() {
        return new int[]{percentage, percentage1};
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
        return this.ingredient.test(pContainer.getFluidInTank(0)) && this.ingredient1.test(pContainer.getFluidInTank(1));
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
        return CGRecipeSerializerInit.ROOM_PRESSURIZING_TYPE.get();
    }

    public static class Serializer extends FluidRecipeSerializer<RoomPressurizerRecipe> {

        @Nonnull
        @Override
        public RoomPressurizerRecipe fromJson(@Nonnull ResourceLocation pRecipeId, @Nonnull JsonObject pSerializedRecipe) {
            RoomPressurizerRecipe recipe = new RoomPressurizerRecipe(pRecipeId);
            recipe.processTime = GsonHelper.getAsInt(pSerializedRecipe, "process_time", recipe.defaultProcess);
            recipe.ingredient = FluidIngredient.deserialize(pSerializedRecipe.get("ingredient1"));
            recipe.ingredient1 = FluidIngredient.deserialize(pSerializedRecipe.get("ingredient2"));
            int per = GsonHelper.getAsInt(pSerializedRecipe, "percentage1", 20);
            int per1 = GsonHelper.getAsInt(pSerializedRecipe, "percentage2", 80);
            if (per + per1 != 100) {
                throw new JsonSyntaxException("The sum of percentages must be 100!");
            }
            recipe.percentage = per;
            recipe.percentage1 = per1;
            return recipe;
        }

        @Nullable
        @Override
        public RoomPressurizerRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            RoomPressurizerRecipe recipe = new RoomPressurizerRecipe(pRecipeId);
            recipe.processTime = pBuffer.readVarInt();
            recipe.ingredient = FluidIngredient.read(pBuffer);
            recipe.ingredient1 = FluidIngredient.read(pBuffer);
            recipe.percentage = pBuffer.readInt();
            recipe.percentage1 = pBuffer.readInt();
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, RoomPressurizerRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.processTime);
            pRecipe.ingredient.write(pBuffer);
            pRecipe.ingredient1.write(pBuffer);
            pBuffer.writeInt(pRecipe.percentage);
            pBuffer.writeInt(pRecipe.percentage1);
        }
    }
}
