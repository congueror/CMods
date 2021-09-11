package net.congueror.cgalaxy.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OxygenTankItem extends Item {
    int capacity;

    public OxygenTankItem(Properties properties, int capacity) {
        super(properties.stacksTo(1));
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
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable net.minecraft.world.level.Level pLevel, java.util.List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("fluid.cgalaxy.oxygen_still").append(": ").withStyle(ChatFormatting.AQUA).append(getOxygen(pStack) + "/" + capacity).withStyle(ChatFormatting.GREEN));
    }
}