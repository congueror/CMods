package net.congueror.cgalaxy.item;

import net.congueror.clib.util.ModCreativeTabs;
import net.minecraft.world.item.Item;

public class OxygenGearItem extends Item {
    public OxygenGearItem(Properties pProperties) {
        super(pProperties.tab(ModCreativeTabs.CGalaxyIG.instance).stacksTo(1));
    }
}
