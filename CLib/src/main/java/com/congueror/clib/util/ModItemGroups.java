package com.congueror.clib.util;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ModItemGroups {
    public static class ItemsIG extends ItemGroup {

        public static final ItemsIG instance = new ItemsIG(ItemGroup.GROUPS.length, "clibitems");
        public ItemsIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.TIN_INGOT.get());
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

    public static class BlocksIG extends ItemGroup {

        public static final BlocksIG instance = new BlocksIG(ItemGroup.GROUPS.length, "clibblocks");
        public BlocksIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockInit.TIN_ORE.get().asItem());
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
