package net.congueror.clib.items.generic;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class CLItem extends Item {
    protected int burnTime;
    protected int containerType;

    public CLItem(Properties pProperties, int burnTime, int containerType) {
        super(pProperties);
        this.burnTime = burnTime;
        this.containerType = containerType;
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
        if (containerType == 2) {
            ItemStack stack1 = new ItemStack(this.asItem());
            stack1.setDamageValue(itemStack.getDamageValue() + 1);
            if (stack1.getDamageValue() > stack1.getMaxDamage()) {
                return new ItemStack(Items.AIR);
            } else {
                return stack1;
            }
        }
        return new ItemStack(this.getCraftingRemainingItem());
    }
}
