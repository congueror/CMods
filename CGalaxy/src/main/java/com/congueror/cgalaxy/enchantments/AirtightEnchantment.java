package com.congueror.cgalaxy.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class AirtightEnchantment extends Enchantment {
    public AirtightEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public boolean canGenerateInLoot() {
        return false;
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return false;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1000;
    }
}
