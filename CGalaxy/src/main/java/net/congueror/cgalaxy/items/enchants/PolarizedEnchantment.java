package net.congueror.cgalaxy.items.enchants;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PolarizedEnchantment extends Enchantment {

    public PolarizedEnchantment(Rarity pRarity) {
        super(pRarity, EnchantmentCategory.create("COMPASS", item -> item.equals(Items.COMPASS)), new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinCost(int pEnchantmentLevel) {
        return 25;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 1;
    }
}
