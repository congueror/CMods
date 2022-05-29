package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.items.enchants.PolarizedEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CGEnchantmentInit {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CGalaxy.MODID);

    public static final RegistryObject<Enchantment> POLARIZED = ENCHANTMENTS.register("polarized", () -> new PolarizedEnchantment(Enchantment.Rarity.VERY_RARE));
}
