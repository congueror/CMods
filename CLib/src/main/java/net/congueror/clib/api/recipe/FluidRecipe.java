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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface FluidRecipe<C extends IItemFluidInventory> extends ItemRecipe<C> {

    /**
     * A collection of fluid stacks which is the result of the recipe. Override this if you have multiple outputs.
     * See {@link #getFluidResult()} for more information. Do not override both {@link #getFluidResult()} and this
     * @return A {@link Collection} of {@link FluidStack}s
     */
    default Collection<FluidStack> getFluidResults() {
        return Collections.singletonList(getFluidResult());
    }

    /**
     * A single fluid stack that is the result of this recipe. Override this if you have a single output.
     * See {@link #getFluidResults()} for more Information. Do not override both {@link #getFluidResults()} and this.
     * @return A single {@link FluidStack} or the first entry in {@link #getFluidResults()}
     */
    default FluidStack getFluidResult() {
        return Iterables.get(getFluidResults(), 0);
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
