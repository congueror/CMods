package com.congueror.cgalaxy.recipes;

import com.congueror.cgalaxy.CGalaxy;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;

/**
 * An extension of IRecipe for fluids
 */
public interface IFluidRecipe<F extends IFluidInv> extends IRecipe<F> {

    /**
     * A list of Fluids that result from the recipe.
     * @param inv Inventory
     * @return List of FluidStacks
     */
    List<FluidStack> getFluidResults(F inv);

    /**
     * getFluidResults but ignores the inventory.
     * @return List of FluidStacks
     */
    List<FluidStack> getFluidResults();

    /**
     * A list of ingredients/inputs.
     * @return List of FluidStacks
     */
    List<FluidStack> getFluidIngredients();

    @Override
    default ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    default ItemStack getCraftingResult(F inv) {
        return getRecipeOutput();
    }

    @Override
    default boolean canFit(int width, int height) {
        return true;
    }

    /**
     * Reads a FluidStack from the PacketBuffer.
     * @param buf The PacketBuffer
     * @return A new FluidStack.
     */
    static FluidStack read(PacketBuffer buf) {
        ResourceLocation fluidId = buf.readResourceLocation();
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        int amount = buf.readVarInt();
        if (fluid == null) {
            CGalaxy.LOGGER.error("Unrecognizable Fluid: " + fluidId);
            return FluidStack.EMPTY;
        }
        return new FluidStack(fluid, amount);
    }

    /**
     * Writes a FluidStack to the PacketBuffer.
     * @param buf The PacketBuffer
     * @param stack A new FluidStack
     */
    static void write(PacketBuffer buf, FluidStack stack) {
        buf.writeResourceLocation(Objects.requireNonNull(stack.getFluid().getRegistryName()));
        buf.writeVarInt(stack.getAmount());
    }

    /**
     * Deserializes a FluidStack from a JSON Object.
     * @param obj JSON Object
     * @return A FluidStack
     */
    static FluidStack deserializeFluidStack(JsonObject obj) {
        ResourceLocation fluidId = new ResourceLocation(JSONUtils.getString(obj, "fluid"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        int amount = JSONUtils.getInt(obj, "amount", 1000);
        if (fluid == null) {
            throw new JsonSyntaxException("Unrecognizable Fluid: " + fluidId);
        }
        return new FluidStack(fluid, amount);
    }
}
