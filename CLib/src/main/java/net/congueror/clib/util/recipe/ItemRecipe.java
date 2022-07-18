package net.congueror.clib.util.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;

public interface ItemRecipe<C extends Container> extends Recipe<C> {
    /**
     * How many ticks it takes for an operation to be completed
     */
    int getProcessTime();

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

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    default boolean isSpecial() {
        return true;
    }

    @Nonnull
    @Override
    default ItemStack assemble(@Nonnull C inv) {
        return getResultItem();
    }
}
