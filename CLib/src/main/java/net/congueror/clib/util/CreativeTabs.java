package net.congueror.clib.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeTabs {
    public static class ResourcesIG extends ModCreativeModeTab {

        public static final ResourcesIG instance = new ResourcesIG(CreativeModeTab.TABS.length, "clibresources");
        public ResourcesIG(int index, String label) {
            super(index, label);
        }

        @Override
        protected String getItemIconRegistryKey() {
            return "clib:tin_ingot";
        }
    }

    public static class BlocksIG extends ModCreativeModeTab {

        public static final BlocksIG instance = new BlocksIG(CreativeModeTab.TABS.length, "clibblocks");
        public BlocksIG(int index, String label) {
            super(index, label);
        }

        @Override
        protected String getItemIconRegistryKey() {
            return "clib:rubber_log";
        }
    }

    public static class CGalaxyIG extends ModCreativeModeTab {
        public static final CGalaxyIG instance = new CGalaxyIG(CreativeModeTab.TABS.length, "cgalaxy");
        public CGalaxyIG(int index, String label) {
            super(index, label);
        }

        @Override
        protected String getItemIconRegistryKey() {
            return "cgalaxy:rocket_tier_1";
        }

        @Override
        protected CompoundTag getIconNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putInt("Fuel", 1000);
            return tag;
        }
    }

    public static class MachinesIG extends ModCreativeModeTab {
        public static final MachinesIG instance = new MachinesIG(CreativeModeTab.TABS.length, "cmachinery");
        public MachinesIG(int index, String label) {
            super(index, label);
        }

        @Override
        protected String getItemIconRegistryKey() {
            return "cgalaxy:speed_upgrade";
        }
    }
}
