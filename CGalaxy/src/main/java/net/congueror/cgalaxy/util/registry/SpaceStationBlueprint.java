package net.congueror.cgalaxy.util.registry;

import net.congueror.clib.util.PairList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public abstract class SpaceStationBlueprint extends ForgeRegistryEntry<SpaceStationBlueprint> {

    public final ResourceLocation nbtLoc;

    public SpaceStationBlueprint(ResourceLocation nbtLoc) {
        this.nbtLoc = nbtLoc;
    }

    /**
     * Location of the nbt that holds the space station structure
     */
    public ResourceLocation getNbtLoc() {
        return nbtLoc;
    }

    /**
     * A map that holds the ingredients of the space station item.
     * @return A map which contains the item or block as key, and as value, the count of blocks/items needed <br>
     */
    public abstract PairList.Unique<Item, Integer> getIngredients();

    /**
     * @return A list which contains the amount needed for each optional block/item.
     */
    @Nonnull
    public List<Integer> getOptionalIngredients() {
        return Collections.emptyList();
    }

    /**
     * Whether this blueprint's ingredients contains an optional ingredient.
     * @return true if {@link #getOptionalIngredients()} is not empty.
     * @see #getOptionalIngredients()
     */
    public boolean hasOptionalIngredient() {
        return !getOptionalIngredients().isEmpty();
    }

    @Override
    public String toString() {
        return "SpaceStationBlueprint{" +
                "nbtLoc=" + nbtLoc +
                ",ingredients=" + getIngredients() +
                ",optional=" + getOptionalIngredients() +
                '}';
    }
}