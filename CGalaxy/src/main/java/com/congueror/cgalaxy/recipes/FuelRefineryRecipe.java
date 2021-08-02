package com.congueror.cgalaxy.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class FuelRefineryRecipe implements IFluidRecipe<IFluidInv> {
    private final ResourceLocation id;

    public FuelRefineryRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public List<FluidStack> getFluidResults(IFluidInv inv) {
        return null;
    }

    @Override
    public List<FluidStack> getFluidResults() {
        return null;
    }

    @Override
    public List<FluidStack> getFluidIngredients() {
        return null;
    }

    @Override
    public boolean matches(IFluidInv inv, World worldIn) {
        return false;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }
}
