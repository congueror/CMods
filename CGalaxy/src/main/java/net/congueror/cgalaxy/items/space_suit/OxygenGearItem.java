package net.congueror.cgalaxy.items.space_suit;

import net.congueror.clib.util.CreativeTabs;
import net.minecraft.world.item.Item;

public class OxygenGearItem extends Item {
    public OxygenGearItem(Properties pProperties) {
        super(pProperties.tab(CreativeTabs.CGalaxyIG.instance).stacksTo(1));
    }
}
