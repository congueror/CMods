package com.congueror.clib.recipe;

import com.congueror.clib.CLib;
import com.google.common.collect.Iterables;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;

public interface IFluidRecipe<C extends IItemFluidInventory> extends IRecipe<C> {
    /**
     * How many ticks it takes for an operation to be completed
     */
    int getProcessTime();

    /**
     * Tests whether the fluid in a tank matches the recipe. You can do this by returning {@link FluidIngredient#test(FluidStack)}.
     * If your recipe's ingredient is a fluid make sure to call this method in the {@link #matches(IInventory, World)} method otherwise you can just return true.
     * @return Whether the {@link FluidStack} in the given {@link IItemFluidInventory} matches the recipe's {@link FluidIngredient}.
     */
    boolean fluidMatches(C handler);

    /**
     * A collection of fluid stacks which is the result of the recipe. Override this if you have multiple outputs.
     * See {@link #getFluidResult()} for more information. Do not override both {@link #getFluidResult()} and this
     * @return A {@link Collection} of {@link FluidStack}s
     */
    default Collection<FluidStack> getFluidResults() {
        Collection<FluidStack> list = new ArrayList<>();
        list.add(getFluidResult());
        return list;
    }

    /**
     * A single fluid stack that is the result of this recipe. Override this if you have a single output.
     * See {@link #getFluidResults()} for more Information. Do not override both {@link #getFluidResults()} and this.
     * @return A single {@link FluidStack} or the first entry in {@link #getFluidResults()}
     */
    default FluidStack getFluidResult() {
        return Iterables.get(getFluidResults(), 0);
    }

    /**
     * A collection of item stacks that result from this recipe. Override this if you have multiple item outputs.
     * If you have a single item output then use {@link IRecipe#getRecipeOutput()}.
     * @return A {@link Collection} of {@link ItemStack}s.
     */
    default Collection<ItemStack> getItemResults() {
        Collection<ItemStack> list = new ArrayList<>();
        list.add(getRecipeOutput());
        return list;
    }

    @Override
    default ItemStack getCraftingResult(C inv) {
        return getRecipeOutput();
    }

    @Override
    default boolean canFit(int width, int height) {
        return true;
    }

    @Override
    default boolean isDynamic() {
        return true;
    }

    default FluidStack deserialize(JsonElement json, int amountFallback) {
        ResourceLocation fluidId = new ResourceLocation(JSONUtils.getString(json.getAsJsonObject(), "fluid"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        int amount = JSONUtils.getInt(json.getAsJsonObject(), "amount", amountFallback);
        if (fluid == null || fluid.equals(Fluids.EMPTY)) {
            throw new JsonSyntaxException("Fluid not found: " + fluidId);
        }
        return new FluidStack(fluid, amount);
    }

    default FluidStack read(PacketBuffer buf) {
        ResourceLocation fluidId = buf.readResourceLocation();
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        int amount = buf.readVarInt();
        if (fluid == null || fluid.equals(Fluids.EMPTY)) {
            CLib.LOGGER.error("Fluid not found: " + fluidId);
            return FluidStack.EMPTY;
        }
        return new FluidStack(fluid, amount);
    }
}
