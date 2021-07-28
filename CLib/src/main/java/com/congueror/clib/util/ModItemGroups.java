package com.congueror.clib.util;

import com.congueror.clib.CLib;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemGroups {
    public static class ItemsIG extends ItemGroup {

        public static final ItemsIG instance = new ItemsIG(ItemGroup.GROUPS.length, "clibitems");
        public ItemsIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack createIcon() {
            Item logo;
            if (CLib.isCOresLoaded()) {
                logo = ForgeRegistries.ITEMS.getValue(new ResourceLocation("cores:tin_ingot"));
            } else {
                logo = Items.BARRIER;
            }
            return new ItemStack(logo);
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
            Item logo;
            if (CLib.isCOresLoaded()) {
                logo = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("cores:tin_ore")).asItem();
            } else {
                logo = Items.BARRIER;
            }
            return new ItemStack(logo);
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

    public static class CGalaxyIG extends ItemGroup {
        public static final CGalaxyIG instance = new CGalaxyIG(ItemGroup.GROUPS.length, "cgalaxy");
        public CGalaxyIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack createIcon() {
            Item logo;
            if (CLib.isCGalaxyLoaded()) {
                logo = ForgeRegistries.ITEMS.getValue(new ResourceLocation("cgalaxy:rocket_tier_1"));
            } else {
                logo = Items.BARRIER;
            }
            return new ItemStack(logo);
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

    public static class MachinesIG extends ItemGroup {
        public static final MachinesIG instance = new MachinesIG(ItemGroup.GROUPS.length, "clibmachines");
        public MachinesIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack createIcon() {
            Item logo;
            if (CLib.isCGalaxyLoaded()) {
                logo = ForgeRegistries.ITEMS.getValue(new ResourceLocation("cgalaxy:speed_upgrade"));
            } else {
                logo = Items.BARRIER;
            }
            return new ItemStack(logo);
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
