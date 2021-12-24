package net.congueror.clib.items;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class UpgradeItem extends Item {
    UpgradeType type;

    public UpgradeItem(Properties properties, UpgradeType type) {
        super(properties.stacksTo(4));
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.addAll(Arrays.asList(type.tooltip));
    }

    public UpgradeType getType() {
        return type;
    }

    public enum UpgradeType {
        //Increases Progress per tick/Increases energy outcome
        SPEED(new TranslatableComponent("key.clib.upgrade_speed1"), new TranslatableComponent("key.clib.upgrade_speed2")),
        //Increases stack per operation
        STACK(new TranslatableComponent("key.clib.upgrade_stack")),
        //Increases range of machine
        RANGE(new TranslatableComponent("key.clib.upgrade_range"))
        ;

        Component[] tooltip;

        UpgradeType(Component... tooltip) {
            this.tooltip = tooltip;
        }
    }
}
