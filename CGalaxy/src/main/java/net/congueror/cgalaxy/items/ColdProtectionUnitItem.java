package net.congueror.cgalaxy.items;

import net.congueror.clib.util.CreativeTabs;

public class ColdProtectionUnitItem extends AbstractProtectionUnitItem {
    public ColdProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties.stacksTo(1).tab(CreativeTabs.CGalaxyIG.instance), capacity);
    }
}