package net.congueror.cgalaxy.item;

import net.congueror.clib.items.AbstractEnergyItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractProtectionUnitItem extends AbstractEnergyItem {
    int capacity;
    public AbstractProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties);
        this.capacity = capacity;
    }

    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        return capacity;
    }
}
