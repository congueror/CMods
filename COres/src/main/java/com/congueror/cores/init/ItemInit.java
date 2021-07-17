package com.congueror.cores.init;

import com.congueror.clib.items.DamageableContainerItem;
import com.congueror.clib.items.ModFuelItem;
import com.congueror.clib.util.ModItemGroups;
import com.congueror.cores.COres;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, COres.MODID);

    public static final RegistryObject<Item> TIN_INGOT = registerItem("tin_ingot");
    public static final RegistryObject<Item> TIN_NUGGET = registerItem("tin_nugget");
    public static final RegistryObject<Item> TIN_DUST = registerItem("tin_dust");
    public static final RegistryObject<Item> TIN_GEAR = registerItem("tin_gear");

    public static final RegistryObject<Item> STEEL_INGOT = registerItem("steel_ingot");
    public static final RegistryObject<Item> STEEL_NUGGET = registerItem("steel_nugget");
    public static final RegistryObject<Item> STEEL_DUST = registerItem("steel_dust");
    public static final RegistryObject<Item> STEEL_GEAR = registerItem("steel_gear");
    public static final RegistryObject<Item> STEEL_BLEND = registerItem("steel_blend");

    public static final RegistryObject<Item> ALUMINUM_INGOT = registerItem("aluminum_ingot");
    public static final RegistryObject<Item> ALUMINUM_NUGGET = registerItem("aluminum_nugget");
    public static final RegistryObject<Item> ALUMINUM_DUST = registerItem("aluminum_dust");
    public static final RegistryObject<Item> ALUMINUM_GEAR = registerItem("aluminum_gear");

    public static final RegistryObject<Item> LEAD_INGOT = registerItem("lead_ingot");
    public static final RegistryObject<Item> LEAD_NUGGET = registerItem("lead_nugget");
    public static final RegistryObject<Item> LEAD_DUST = registerItem("lead_dust");
    public static final RegistryObject<Item> LEAD_GEAR = registerItem("lead_gear");

    public static final RegistryObject<Item> COPPER_INGOT = registerItem("copper_ingot");
    public static final RegistryObject<Item> COPPER_NUGGET = registerItem("copper_nugget");
    public static final RegistryObject<Item> COPPER_DUST = registerItem("copper_dust");
    public static final RegistryObject<Item> COPPER_GEAR = registerItem("copper_gear");

    public static final RegistryObject<Item> RUBY = registerItem("ruby");
    public static final RegistryObject<Item> RUBY_DUST = registerItem("ruby_dust");
    public static final RegistryObject<Item> RUBY_GEAR = registerItem("ruby_gear");

    public static final RegistryObject<Item> SILVER_INGOT = registerItem("silver_ingot");
    public static final RegistryObject<Item> SILVER_NUGGET = registerItem("silver_nugget");
    public static final RegistryObject<Item> SILVER_DUST = registerItem("silver_dust");
    public static final RegistryObject<Item> SILVER_GEAR = registerItem("silver_gear");

    public static final RegistryObject<Item> LUMIUM_INGOT = registerItem("lumium_ingot");
    public static final RegistryObject<Item> LUMIUM_NUGGET = registerItem("lumium_nugget");
    public static final RegistryObject<Item> LUMIUM_DUST = registerItem("lumium_dust");
    public static final RegistryObject<Item> LUMIUM_GEAR = registerItem("lumium_gear");
    public static final RegistryObject<Item> LUMIUM_BLEND = registerItem("lumium_blend");

    public static final RegistryObject<Item> NICKEL_INGOT = registerItem("nickel_ingot");
    public static final RegistryObject<Item> NICKEL_NUGGET = registerItem("nickel_nugget");
    public static final RegistryObject<Item> NICKEL_DUST = registerItem("nickel_dust");
    public static final RegistryObject<Item> NICKEL_GEAR = registerItem("nickel_gear");

    public static final RegistryObject<Item> INVAR_INGOT = registerItem("invar_ingot");
    public static final RegistryObject<Item> INVAR_NUGGET = registerItem("invar_nugget");
    public static final RegistryObject<Item> INVAR_DUST = registerItem("invar_dust");
    public static final RegistryObject<Item> INVAR_GEAR = registerItem("invar_gear");
    public static final RegistryObject<Item> INVAR_BLEND = registerItem("invar_blend");

    public static final RegistryObject<Item> ELECTRUM_INGOT = registerItem("electrum_ingot");
    public static final RegistryObject<Item> ELECTRUM_NUGGET = registerItem("electrum_nugget");
    public static final RegistryObject<Item> ELECTRUM_DUST = registerItem("electrum_dust");
    public static final RegistryObject<Item> ELECTRUM_GEAR = registerItem("electrum_gear");
    public static final RegistryObject<Item> ELECTRUM_BLEND = registerItem("electrum_blend");

    public static final RegistryObject<Item> PLATINUM_INGOT = registerItem("platinum_ingot");
    public static final RegistryObject<Item> PLATINUM_NUGGET = registerItem("platinum_nugget");
    public static final RegistryObject<Item> PLATINUM_DUST = registerItem("platinum_dust");
    public static final RegistryObject<Item> PLATINUM_GEAR = registerItem("platinum_gear");

    public static final RegistryObject<Item> ENDERIUM_INGOT = registerItem("enderium_ingot");
    public static final RegistryObject<Item> ENDERIUM_NUGGET = registerItem("enderium_nugget");
    public static final RegistryObject<Item> ENDERIUM_DUST = registerItem("enderium_dust");
    public static final RegistryObject<Item> ENDERIUM_GEAR = registerItem("enderium_gear");
    public static final RegistryObject<Item> ENDERIUM_BLEND = registerItem("enderium_blend");

    public static final RegistryObject<Item> SIGNALUM_INGOT = registerItem("signalum_ingot");
    public static final RegistryObject<Item> SIGNALUM_NUGGET = registerItem("signalum_nugget");
    public static final RegistryObject<Item> SIGNALUM_DUST = registerItem("signalum_dust");
    public static final RegistryObject<Item> SIGNALUM_GEAR = registerItem("signalum_gear");
    public static final RegistryObject<Item> SIGNALUM_BLEND = registerItem("signalum_blend");

    public static final RegistryObject<Item> TUNGSTEN_INGOT = registerItem("tungsten_ingot");
    public static final RegistryObject<Item> TUNGSTEN_NUGGET = registerItem("tungsten_nugget");
    public static final RegistryObject<Item> TUNGSTEN_DUST = registerItem("tungsten_dust");
    public static final RegistryObject<Item> TUNGSTEN_GEAR = registerItem("tungsten_gear");

    public static final RegistryObject<Item> BRONZE_INGOT = registerItem("bronze_ingot");
    public static final RegistryObject<Item> BRONZE_NUGGET = registerItem("bronze_nugget");
    public static final RegistryObject<Item> BRONZE_DUST = registerItem("bronze_dust");
    public static final RegistryObject<Item> BRONZE_GEAR = registerItem("bronze_gear");
    public static final RegistryObject<Item> BRONZE_BLEND = registerItem("bronze_blend");

    public static final RegistryObject<Item> AMETHYST = registerItem("amethyst");
    public static final RegistryObject<Item> AMETHYST_DUST = registerItem("amethyst_dust");
    public static final RegistryObject<Item> AMETHYST_GEAR = registerItem("amethyst_gear");

    public static final RegistryObject<Item> SAPPHIRE = registerItem("sapphire");
    public static final RegistryObject<Item> SAPPHIRE_DUST = registerItem("sapphire_dust");
    public static final RegistryObject<Item> SAPPHIRE_GEAR = registerItem("sapphire_gear");

    public static final RegistryObject<Item> OPAL = registerItem("opal");
    public static final RegistryObject<Item> OPAL_DUST = registerItem("opal_dust");
    public static final RegistryObject<Item> OPAL_GEAR = registerItem("opal_gear");

    public static final RegistryObject<Item> TITANIUM_INGOT = registerItem("titanium_ingot");
    public static final RegistryObject<Item> TITANIUM_NUGGET = registerItem("titanium_nugget");
    public static final RegistryObject<Item> TITANIUM_DUST = registerItem("titanium_dust");
    public static final RegistryObject<Item> TITANIUM_GEAR = registerItem("titanium_gear");
    public static final RegistryObject<Item> TITANIUM_SCRAP = registerItem("titanium_scrap");

    public static final RegistryObject<Item> URANIUM_INGOT = registerItem("uranium_ingot");
    public static final RegistryObject<Item> URANIUM_NUGGET = registerItem("uranium_nugget");
    public static final RegistryObject<Item> URANIUM_DUST = registerItem("uranium_dust");

    public static final RegistryObject<Item> COBALT_INGOT = registerItem("cobalt_ingot");
    public static final RegistryObject<Item> COBALT_NUGGET = registerItem("cobalt_nugget");
    public static final RegistryObject<Item> COBALT_DUST = registerItem("cobalt_dust");
    public static final RegistryObject<Item> COBALT_GEAR = registerItem("cobalt_gear");

    public static final RegistryObject<Item> ZINC_INGOT = registerItem("zinc_ingot");
    public static final RegistryObject<Item> ZINC_NUGGET = registerItem("zinc_nugget");
    public static final RegistryObject<Item> ZINC_DUST = registerItem("zinc_dust");
    public static final RegistryObject<Item> ZINC_GEAR = registerItem("zinc_gear");

    public static final RegistryObject<Item> BRASS_INGOT = registerItem("brass_ingot");
    public static final RegistryObject<Item> BRASS_NUGGET = registerItem("brass_nugget");
    public static final RegistryObject<Item> BRASS_DUST = registerItem("brass_dust");
    public static final RegistryObject<Item> BRASS_GEAR = registerItem("brass_gear");
    public static final RegistryObject<Item> BRASS_BLEND = registerItem("brass_blend");

    public static final RegistryObject<Item> CHROMIUM_INGOT = registerItem("chromium_ingot");
    public static final RegistryObject<Item> CHROMIUM_NUGGET = registerItem("chromium_nugget");
    public static final RegistryObject<Item> CHROMIUM_DUST = registerItem("chromium_dust");
    public static final RegistryObject<Item> CHROMIUM_GEAR = registerItem("chromium_gear");

    public static final RegistryObject<Item> THORIUM_INGOT = registerItem("thorium_ingot");
    public static final RegistryObject<Item> THORIUM_NUGGET = registerItem("thorium_nugget");
    public static final RegistryObject<Item> THORIUM_DUST = registerItem("thorium_dust");

    public static final RegistryObject<Item> COAL_NUGGET = ITEMS.register("coal_nugget", () -> new ModFuelItem(properties(), 178));

    public static final RegistryObject<Item> SALTPETRE_DUST = registerItem("saltpetre_dust");
    public static final RegistryObject<Item> SULFUR_DUST = registerItem("sulfur_dust");

    public static final RegistryObject<Item> SALT = registerItem("salt");

    public static final RegistryObject<Item> PYROTHEUM_DUST = ITEMS.register("pyrotheum_dust", () -> new ModFuelItem(properties(), 3200));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new DamageableContainerItem(properties().defaultMaxDamage(16)));

    public static final RegistryObject<Item> WOOD_DUST = registerItem("wood_dust");
    public static final RegistryObject<Item> COAL_DUST = registerItem("coal_dust");
    public static final RegistryObject<Item> IRON_DUST = registerItem("iron_dust");
    public static final RegistryObject<Item> GOLD_DUST = registerItem("gold_dust");
    public static final RegistryObject<Item> LAPIS_DUST = registerItem("lapis_dust");
    public static final RegistryObject<Item> QUARTZ_DUST = registerItem("quartz_dust");
    public static final RegistryObject<Item> DIAMOND_DUST = registerItem("diamond_dust");
    public static final RegistryObject<Item> EMERALD_DUST = registerItem("emerald_dust");
    public static final RegistryObject<Item> NETHERITE_DUST = registerItem("netherite_dust");
    public static final RegistryObject<Item> OBSIDIAN_DUST = registerItem("obsidian_dust");

    public static final RegistryObject<Item> WOOD_GEAR = registerItem("wood_gear");
    public static final RegistryObject<Item> STONE_GEAR = registerItem("stone_gear");
    public static final RegistryObject<Item> IRON_GEAR = registerItem("iron_gear");
    public static final RegistryObject<Item> GOLD_GEAR = registerItem("gold_gear");
    public static final RegistryObject<Item> LAPIS_GEAR = registerItem("lapis_gear");
    public static final RegistryObject<Item> QUARTZ_GEAR = registerItem("quartz_gear");
    public static final RegistryObject<Item> DIAMOND_GEAR = registerItem("diamond_gear");
    public static final RegistryObject<Item> EMERALD_GEAR = registerItem("emerald_gear");
    public static final RegistryObject<Item> NETHERITE_GEAR = registerItem("netherite_gear");

    public static final RegistryObject<Item> RUBBER = registerItem("rubber");
    public static final RegistryObject<Item> TREE_TAP = ITEMS.register("tree_tap", () -> new DamageableContainerItem(properties().defaultMaxDamage(5)));
    public static final RegistryObject<BlockItem> RUBBER_SAPLING = ITEMS.register("rubber_sapling", () -> new BlockItem(BlockInit.RUBBER_SAPLING.get(), new Item.Properties().group(ModItemGroups.BlocksIG.instance)));

    public static RegistryObject<Item> registerItem(String name) {
        return ITEMS.register(name, () -> new Item(properties()));
    }

    public static Item.Properties properties() {
        return new Item.Properties().group(ModItemGroups.ItemsIG.instance);
    }
}
