package net.congueror.clib.items.generic;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class CLItem extends Item implements ICLibItem {
    protected int burnTime = -1;
    protected int containerType;

    public CLItem(Properties pProperties) {
        super(pProperties);
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
