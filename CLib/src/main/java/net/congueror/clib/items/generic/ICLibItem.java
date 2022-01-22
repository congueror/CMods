package net.congueror.clib.items.generic;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * An interface which can be implemented by an Item class to add additional functionality for the {@link net.congueror.clib.api.registry.ItemBuilder}. See {@link CLItem} for example on how to implement properly
 */
public interface ICLibItem {
    void setBurnTime(int burnTime);
    void setContainerType(int containerType);

    default ItemStack containerItem(int containerType, Item item, ItemStack itemStack) {
        if (containerType == 2) {
            ItemStack stack1 = new ItemStack(item.asItem());
            stack1.setDamageValue(itemStack.getDamageValue() + 1);
            if (stack1.getDamageValue() > stack1.getMaxDamage()) {
                return new ItemStack(Items.AIR);
            } else {
                return stack1;
            }
        }
        return new ItemStack(item.getCraftingRemainingItem());
    }
}
