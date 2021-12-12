package net.congueror.clib.items;

import net.minecraft.world.item.Item;

public class UpgradeItem extends Item {
    UpgradeType type;
    public UpgradeItem(Properties properties, UpgradeType type) {
        super(properties.stacksTo(4));
        this.type = type;
    }

    public UpgradeType getType() {
        return type;
    }

    public enum UpgradeType {
        SPEED, //Increases Progress per tick
        STACK, //Increases stack per operation
        RANGE, //Increases range of machine
    }
}
