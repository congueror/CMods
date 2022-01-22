package net.congueror.clib.items.generic;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CLBucketItem extends BucketItem implements ICLibItem {
    protected int burnTime = -1;
    protected int containerType;

    public CLBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder.stacksTo(1));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return this.containerType > 0;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return containerItem(containerType, this, itemStack);
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public void setContainerType(int containerType) {
        this.containerType = containerType;
    }
}
