package net.congueror.clib.util;

import net.congueror.clib.CLib;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemGroups {
    public static class ItemsIG extends CreativeModeTab {

        public static final ItemsIG instance = new ItemsIG(CreativeModeTab.TABS.length, "clibitems");
        public ItemsIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("clib:tin_ingot")));
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

    public static class BlocksIG extends CreativeModeTab {

        public static final BlocksIG instance = new BlocksIG(CreativeModeTab.TABS.length, "clibblocks");
        public BlocksIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ForgeRegistries.BLOCKS.getValue(new ResourceLocation("clib:tin_ore")).asItem());
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

    public static class CGalaxyIG extends CreativeModeTab {
        public static final CGalaxyIG instance = new CGalaxyIG(CreativeModeTab.TABS.length, "cgalaxy");
        public CGalaxyIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack makeIcon() {
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

    public static class MachinesIG extends CreativeModeTab {
        public static final MachinesIG instance = new MachinesIG(CreativeModeTab.TABS.length, "cmachinery");
        public MachinesIG(int index, String label) {
            super(index, label);
            this.setBackgroundImage(new ResourceLocation("textures/gui/container/creative_inventory/tab_item_search.png"));
        }

        @Override
        public ItemStack makeIcon() {
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
