package com.congueror.cgalaxy.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ModItemGroups {
    public static class ItemsIG extends ItemGroup {

        public static final ModItemGroups.ItemsIG instance = new ModItemGroups.ItemsIG(ItemGroup.GROUPS.length, "cgalaxyitems");
        public ItemsIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.CHAIN);
        }

        @Override
        public boolean hasSearchBar() {
            return true;
        }

        @Override
        public int getSearchbarWidth() {
            return 88;
        }

        @Override
        public ResourceLocation getTabsImage() {
            return new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
        }
    }
}
