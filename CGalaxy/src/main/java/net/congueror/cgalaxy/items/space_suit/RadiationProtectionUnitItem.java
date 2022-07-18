package net.congueror.cgalaxy.items.space_suit;

import net.congueror.clib.util.CreativeTabs;

public class RadiationProtectionUnitItem extends AbstractProtectionUnitItem {
    public RadiationProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties.stacksTo(1).tab(CreativeTabs.CGalaxyIG.instance), capacity);
    }
}
