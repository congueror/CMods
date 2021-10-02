package net.congueror.cgalaxy.item;

import net.congueror.clib.util.ModCreativeTabs;

public class HeatProtectionUnitItem extends AbstractProtectionUnitItem {
    public HeatProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties.stacksTo(1).tab(ModCreativeTabs.CGalaxyIG.instance), capacity);
    }
}
