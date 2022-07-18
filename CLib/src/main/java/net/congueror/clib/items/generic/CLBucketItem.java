package net.congueror.clib.items.generic;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CLBucketItem extends BucketItem {
    protected int burnTime = -1;

    public CLBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder.stacksTo(1));
    }

    public CLBucketItem withBurnTime(int burnTime) {
        this.burnTime = burnTime;
        return this;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(this.getCraftingRemainingItem());
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @org.jetbrains.annotations.Nullable CompoundTag nbt) {
        return new FluidBucketWrapper(stack);
    }
}
