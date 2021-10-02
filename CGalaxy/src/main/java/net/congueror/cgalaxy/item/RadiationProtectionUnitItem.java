package net.congueror.cgalaxy.item;

import net.congueror.clib.util.ModCreativeTabs;

public class RadiationProtectionUnitItem extends AbstractProtectionUnitItem {
    public RadiationProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties.stacksTo(1).tab(ModCreativeTabs.CGalaxyIG.instance), capacity);
    }
}
