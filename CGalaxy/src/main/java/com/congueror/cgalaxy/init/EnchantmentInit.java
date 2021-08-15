package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.enchantments.AirtightEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentInit {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CGalaxy.MODID);

    public static final RegistryObject<Enchantment> AIRTIGHT = ENCHANTMENTS.register("airtight", () -> new AirtightEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR, new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET}));
}
