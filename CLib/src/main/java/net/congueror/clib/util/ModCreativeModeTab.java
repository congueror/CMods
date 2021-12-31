package net.congueror.clib.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public abstract class ModCreativeModeTab extends CreativeModeTab {
    public ModCreativeModeTab(int index, String label) {
        super(index, label);
        this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
    }

    /**
     * The registry key of the item you want as the icon of this item group, e.g. "clib:tin_ingot"
     */
    protected abstract String getItemIconRegistryKey();

    /**
     * The nbt that will be applied to the itemstack which is rendered as an icon.
     */
    protected CompoundTag getIconNBT() {
        return new CompoundTag();
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(getItemIconRegistryKey()));
        if (item != null) {
            ItemStack stack = new ItemStack(item);
            stack.setTag(getIconNBT());
            return stack;
        }
        return new ItemStack(Items.BARRIER);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }

    @Override
    public int getSearchbarWidth() {
        return 88;
    }

    @Nonnull
    @Override
    public ResourceLocation getTabsImage() {
        return new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    }
}
