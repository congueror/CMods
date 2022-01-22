package net.congueror.cgalaxy.item;

import net.congueror.clib.util.ModCreativeTabs;
import net.minecraft.world.item.ItemStack;

public class ColdProtectionUnitItem extends AbstractProtectionUnitItem {
    public ColdProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties.stacksTo(1).tab(ModCreativeTabs.CGalaxyIG.instance), capacity);
    }
}
