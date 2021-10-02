package net.congueror.cgalaxy.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AbstractProtectionUnitItem extends Item {
    private final int capacity;

    public AbstractProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAmount(ItemStack stack) {
        if (stack.getItem() instanceof AbstractProtectionUnitItem) {
            return stack.getOrCreateTag().getInt("Amount");
        } else {
            return 0;
        }
    }

    public int drain(ItemStack stack, int amount) {
        int amount1 = stack.getOrCreateTag().getInt("Amount");
        if (amount1 > 0 && amount1 - amount >= 0) {
            stack.getOrCreateTag().putInt("Amount", amount1 - amount);
            return amount;
        }
        return 0;
    }

    public int fill(ItemStack stack, int amount) {
        int filled = capacity - getAmount(stack);
        if (amount < filled) {
            stack.getOrCreateTag().putInt("Amount", getAmount(stack) + amount);
            filled = amount;
        } else {
            stack.getOrCreateTag().putInt("Amount", capacity);
        }
        return filled;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        AbstractProtectionUnitItem item = (AbstractProtectionUnitItem) stack.getItem();
        return (double) (item.getCapacity() - item.getAmount(stack)) / (double) item.getCapacity();
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable net.minecraft.world.level.Level pLevel, java.util.List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("key.cgalaxy.fuel_remaining").append(": ").withStyle(ChatFormatting.AQUA).append(getAmount(pStack) + "/" + capacity).withStyle(ChatFormatting.GREEN));
    }
}
