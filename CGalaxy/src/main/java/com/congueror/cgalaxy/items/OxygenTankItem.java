package com.congueror.cgalaxy.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class OxygenTankItem extends Item {
    int capacity;

    public OxygenTankItem(Properties properties, int capacity) {
        super(properties.maxStackSize(1));
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOxygen(ItemStack stack) {
        if (stack.getItem() instanceof OxygenTankItem) {
            return stack.getOrCreateTag().getInt("Oxygen");
        } else {
            return 0;
        }
    }

    public int drain(ItemStack stack, int amount) {
        int oxygen = stack.getOrCreateTag().getInt("Oxygen");
        if (oxygen > 0 && oxygen - amount >= 0) {
            stack.getOrCreateTag().putInt("Oxygen", oxygen - amount);
            return amount;
        }
        return 0;
    }

    public int fill(ItemStack stack, int amount) {
        int filled = capacity - getOxygen(stack);
        if (amount < filled) {
            stack.getOrCreateTag().putInt("Oxygen", getOxygen(stack) + amount);
            filled = amount;
        } else {
            stack.getOrCreateTag().putInt("Oxygen", capacity);
        }
        return filled;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        OxygenTankItem item = (OxygenTankItem) stack.getItem();
        return (double) (item.getCapacity() - item.getOxygen(stack)) / (double) item.getCapacity();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("fluid.cgalaxy.oxygen_still").appendString(": ").mergeStyle(TextFormatting.AQUA).appendString(getOxygen(stack) + "/" + capacity).mergeStyle(TextFormatting.GREEN));
    }
}
