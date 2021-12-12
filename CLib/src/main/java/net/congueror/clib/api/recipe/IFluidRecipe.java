package net.congueror.clib.api.recipe;

import com.google.common.collect.Iterables;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.congueror.clib.CLib;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

public interface IFluidRecipe<C extends IItemFluidInventory> extends Recipe<C> {
    /**
     * How many ticks it takes for an operation to be completed
     */
    int getProcessTime();

    /**
     * Tests whether the fluid in a tank matches the recipe. You can do this by returning {@link FluidIngredient#test(FluidStack)}.
     * If your recipe's ingredient is a fluid make sure to call this method in the {@link #matches(net.minecraft.world.Container, net.minecraft.world.level.Level)} method otherwise you can just return true.
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
     * If you have a single item output then use {@link Recipe#getResultItem()}.
     * @return A {@link Collection} of {@link ItemStack}s.
     */
    default Collection<ItemStack> getItemResults() {
        Collection<ItemStack> list = new ArrayList<>();
        list.add(getResultItem());
        return list;
    }

    @Nonnull
    @Override
    default ItemStack assemble(@Nonnull C inv) {
        return getResultItem();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    default boolean isSpecial() {
        return true;
    }

    default FluidStack deserialize(JsonElement json, int amountFallback) {
        ResourceLocation fluidId = new ResourceLocation(GsonHelper.getAsString(json.getAsJsonObject(), "fluid"));
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        int amount = GsonHelper.getAsInt(json.getAsJsonObject(), "amount", amountFallback);
        if (fluid == null || fluid.equals(Fluids.EMPTY)) {
            throw new JsonSyntaxException("Fluid not found: " + fluidId);
        }
        return new FluidStack(fluid, amount);
    }

    default FluidStack read(FriendlyByteBuf buf) {
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
