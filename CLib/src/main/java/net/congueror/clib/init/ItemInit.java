package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.api.objects.items.CLItem;
import net.congueror.clib.items.HammerItem;
import net.congueror.clib.util.ModItemGroups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CLib.MODID);

    public static final RegistryObject<Item> TIN_INGOT = registerItem("tin_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/tin").build(ITEMS);
    public static final RegistryObject<Item> TIN_NUGGET = registerItem("tin_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/tin").build(ITEMS);
    public static final RegistryObject<Item> TIN_DUST = registerItem("tin_dust", new CLItem(properties())).withNewItemTag("forge:dusts/tin").build(ITEMS);
    public static final RegistryObject<Item> TIN_GEAR = registerItem("tin_gear", new CLItem(properties())).withNewItemTag("forge:gears/tin").build(ITEMS);

    public static final RegistryObject<Item> STEEL_INGOT = registerItem("steel_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/steel").build(ITEMS);
    public static final RegistryObject<Item> STEEL_NUGGET = registerItem("steel_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/steel").build(ITEMS);
    public static final RegistryObject<Item> STEEL_DUST = registerItem("steel_dust", new CLItem(properties())).withNewItemTag("forge:dusts/steel").build(ITEMS);
    public static final RegistryObject<Item> STEEL_GEAR = registerItem("steel_gear", new CLItem(properties())).withNewItemTag("forge:gears/steel").build(ITEMS);
    public static final RegistryObject<Item> STEEL_BLEND = registerItem("steel_blend", new CLItem(properties())).withNewItemTag("forge:ores/steel").build(ITEMS);

    public static final RegistryObject<Item> ALUMINUM_INGOT = registerItem("aluminum_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/aluminum").build(ITEMS);
    public static final RegistryObject<Item> ALUMINUM_NUGGET = registerItem("aluminum_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/aluminum").build(ITEMS);
    public static final RegistryObject<Item> ALUMINUM_DUST = registerItem("aluminum_dust", new CLItem(properties())).withNewItemTag("forge:dusts/aluminum").build(ITEMS);
    public static final RegistryObject<Item> ALUMINUM_GEAR = registerItem("aluminum_gear", new CLItem(properties())).withNewItemTag("forge:gears/aluminum").build(ITEMS);

    public static final RegistryObject<Item> LEAD_INGOT = registerItem("lead_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/lead").build(ITEMS);
    public static final RegistryObject<Item> LEAD_NUGGET = registerItem("lead_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/lead").build(ITEMS);
    public static final RegistryObject<Item> LEAD_DUST = registerItem("lead_dust", new CLItem(properties())).withNewItemTag("forge:dusts/lead").build(ITEMS);
    public static final RegistryObject<Item> LEAD_GEAR = registerItem("lead_gear", new CLItem(properties())).withNewItemTag("forge:gears/lead").build(ITEMS);

    public static final RegistryObject<Item> RUBY = registerItem("ruby", new CLItem(properties())).withNewItemTag("forge:gems/ruby").build(ITEMS);
    public static final RegistryObject<Item> RUBY_DUST = registerItem("ruby_dust", new CLItem(properties())).withNewItemTag("forge:dusts/ruby").build(ITEMS);
    public static final RegistryObject<Item> RUBY_GEAR = registerItem("ruby_gear", new CLItem(properties())).withNewItemTag("forge:gears/ruby").build(ITEMS);

    public static final RegistryObject<Item> SILVER_INGOT = registerItem("silver_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/silver").build(ITEMS);
    public static final RegistryObject<Item> SILVER_NUGGET = registerItem("silver_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/silver").build(ITEMS);
    public static final RegistryObject<Item> SILVER_DUST = registerItem("silver_dust", new CLItem(properties())).withNewItemTag("forge:dusts/silver").build(ITEMS);
    public static final RegistryObject<Item> SILVER_GEAR = registerItem("silver_gear", new CLItem(properties())).withNewItemTag("forge:gears/silver").build(ITEMS);

    public static final RegistryObject<Item> LUMIUM_INGOT = registerItem("lumium_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/lumium").build(ITEMS);
    public static final RegistryObject<Item> LUMIUM_NUGGET = registerItem("lumium_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/lumium").build(ITEMS);
    public static final RegistryObject<Item> LUMIUM_DUST = registerItem("lumium_dust", new CLItem(properties())).withNewItemTag("forge:dusts/lumium").build(ITEMS);
    public static final RegistryObject<Item> LUMIUM_GEAR = registerItem("lumium_gear", new CLItem(properties())).withNewItemTag("forge:gears/lumium").build(ITEMS);
    public static final RegistryObject<Item> LUMIUM_BLEND = registerItem("lumium_blend", new CLItem(properties())).withNewItemTag("forge:ores/lumium").build(ITEMS);

    public static final RegistryObject<Item> NICKEL_INGOT = registerItem("nickel_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/nickel").build(ITEMS);
    public static final RegistryObject<Item> NICKEL_NUGGET = registerItem("nickel_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/nickel").build(ITEMS);
    public static final RegistryObject<Item> NICKEL_DUST = registerItem("nickel_dust", new CLItem(properties())).withNewItemTag("forge:dusts/nickel").build(ITEMS);
    public static final RegistryObject<Item> NICKEL_GEAR = registerItem("nickel_gear", new CLItem(properties())).withNewItemTag("forge:gears/nickel").build(ITEMS);

    public static final RegistryObject<Item> INVAR_INGOT = registerItem("invar_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/invar").build(ITEMS);
    public static final RegistryObject<Item> INVAR_NUGGET = registerItem("invar_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/invar").build(ITEMS);
    public static final RegistryObject<Item> INVAR_DUST = registerItem("invar_dust", new CLItem(properties())).withNewItemTag("forge:dusts/invar").build(ITEMS);
    public static final RegistryObject<Item> INVAR_GEAR = registerItem("invar_gear", new CLItem(properties())).withNewItemTag("forge:gears/invar").build(ITEMS);
    public static final RegistryObject<Item> INVAR_BLEND = registerItem("invar_blend", new CLItem(properties())).withNewItemTag("forge:ores/invar").build(ITEMS);

    public static final RegistryObject<Item> ELECTRUM_INGOT = registerItem("electrum_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/electrum").build(ITEMS);
    public static final RegistryObject<Item> ELECTRUM_NUGGET = registerItem("electrum_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/electrum").build(ITEMS);
    public static final RegistryObject<Item> ELECTRUM_DUST = registerItem("electrum_dust", new CLItem(properties())).withNewItemTag("forge:dusts/electrum").build(ITEMS);
    public static final RegistryObject<Item> ELECTRUM_GEAR = registerItem("electrum_gear", new CLItem(properties())).withNewItemTag("forge:gears/electrum").build(ITEMS);
    public static final RegistryObject<Item> ELECTRUM_BLEND = registerItem("electrum_blend", new CLItem(properties())).withNewItemTag("forge:ores/electrum").build(ITEMS);

    public static final RegistryObject<Item> PLATINUM_INGOT = registerItem("platinum_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/platinum").build(ITEMS);
    public static final RegistryObject<Item> PLATINUM_NUGGET = registerItem("platinum_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/platinum").build(ITEMS);
    public static final RegistryObject<Item> PLATINUM_DUST = registerItem("platinum_dust", new CLItem(properties())).withNewItemTag("forge:dusts/platinum").build(ITEMS);
    public static final RegistryObject<Item> PLATINUM_GEAR = registerItem("platinum_gear", new CLItem(properties())).withNewItemTag("forge:gears/platinum").build(ITEMS);

    public static final RegistryObject<Item> ENDERIUM_INGOT = registerItem("enderium_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/enderium").build(ITEMS);
    public static final RegistryObject<Item> ENDERIUM_NUGGET = registerItem("enderium_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/enderium").build(ITEMS);
    public static final RegistryObject<Item> ENDERIUM_DUST = registerItem("enderium_dust", new CLItem(properties())).withNewItemTag("forge:dusts/enderium").build(ITEMS);
    public static final RegistryObject<Item> ENDERIUM_GEAR = registerItem("enderium_gear", new CLItem(properties())).withNewItemTag("forge:gears/enderium").build(ITEMS);
    public static final RegistryObject<Item> ENDERIUM_BLEND = registerItem("enderium_blend", new CLItem(properties())).withNewItemTag("forge:ores/enderium").build(ITEMS);

    public static final RegistryObject<Item> SIGNALUM_INGOT = registerItem("signalum_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/signalum").build(ITEMS);
    public static final RegistryObject<Item> SIGNALUM_NUGGET = registerItem("signalum_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/signalum").build(ITEMS);
    public static final RegistryObject<Item> SIGNALUM_DUST = registerItem("signalum_dust", new CLItem(properties())).withNewItemTag("forge:dusts/signalum").build(ITEMS);
    public static final RegistryObject<Item> SIGNALUM_GEAR = registerItem("signalum_gear", new CLItem(properties())).withNewItemTag("forge:gears/signalum").build(ITEMS);
    public static final RegistryObject<Item> SIGNALUM_BLEND = registerItem("signalum_blend", new CLItem(properties())).withNewItemTag("forge:ores/signalum").build(ITEMS);

    public static final RegistryObject<Item> TUNGSTEN_INGOT = registerItem("tungsten_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/tungsten").build(ITEMS);
    public static final RegistryObject<Item> TUNGSTEN_NUGGET = registerItem("tungsten_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/tungsten").build(ITEMS);
    public static final RegistryObject<Item> TUNGSTEN_DUST = registerItem("tungsten_dust", new CLItem(properties())).withNewItemTag("forge:dusts/tungsten").build(ITEMS);
    public static final RegistryObject<Item> TUNGSTEN_GEAR = registerItem("tungsten_gear", new CLItem(properties())).withNewItemTag("forge:gears/tungsten").build(ITEMS);

    public static final RegistryObject<Item> BRONZE_INGOT = registerItem("bronze_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/bronze").build(ITEMS);
    public static final RegistryObject<Item> BRONZE_NUGGET = registerItem("bronze_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/bronze").build(ITEMS);
    public static final RegistryObject<Item> BRONZE_DUST = registerItem("bronze_dust", new CLItem(properties())).withNewItemTag("forge:dusts/bronze").build(ITEMS);
    public static final RegistryObject<Item> BRONZE_GEAR = registerItem("bronze_gear", new CLItem(properties())).withNewItemTag("forge:gears/bronze").build(ITEMS);
    public static final RegistryObject<Item> BRONZE_BLEND = registerItem("bronze_blend", new CLItem(properties())).withNewItemTag("forge:ores/bronze").build(ITEMS);

    public static final RegistryObject<Item> SAPPHIRE_SHARD = registerItem("sapphire_shard", new CLItem(properties())).withNewItemTag("forge:gems/sapphire").build(ITEMS);
    public static final RegistryObject<Item> SAPPHIRE_DUST = registerItem("sapphire_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/sapphire")
            .withNewItemTag("forge:shards/sapphire")
            .build(ITEMS);

    public static final RegistryObject<Item> OPAL = registerItem("opal", new CLItem(properties())).withNewItemTag("forge:gems/opal").build(ITEMS);
    public static final RegistryObject<Item> OPAL_DUST = registerItem("opal_dust", new CLItem(properties())).withNewItemTag("forge:dusts/opal").build(ITEMS);
    public static final RegistryObject<Item> OPAL_GEAR = registerItem("opal_gear", new CLItem(properties())).withNewItemTag("forge:gears/opal").build(ITEMS);

    public static final RegistryObject<Item> TITANIUM_INGOT = registerItem("titanium_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/titanium").build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_NUGGET = registerItem("titanium_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/titanium").build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_DUST = registerItem("titanium_dust", new CLItem(properties())).withNewItemTag("forge:dusts/titanium").build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_GEAR = registerItem("titanium_gear", new CLItem(properties())).withNewItemTag("forge:gears/titanium").build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_SCRAP = registerItem("titanium_scrap", new CLItem(properties())).withNewItemTag("forge:scraps/titanium").build(ITEMS);

    public static final RegistryObject<Item> URANIUM_INGOT = registerItem("uranium_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/uranium").build(ITEMS);
    public static final RegistryObject<Item> URANIUM_NUGGET = registerItem("uranium_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/uranium").build(ITEMS);
    public static final RegistryObject<Item> URANIUM_DUST = registerItem("uranium_dust", new CLItem(properties())).withNewItemTag("forge:dusts/uranium").build(ITEMS);

    public static final RegistryObject<Item> COBALT_INGOT = registerItem("cobalt_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/cobalt").build(ITEMS);
    public static final RegistryObject<Item> COBALT_NUGGET = registerItem("cobalt_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/cobalt").build(ITEMS);
    public static final RegistryObject<Item> COBALT_DUST = registerItem("cobalt_dust", new CLItem(properties())).withNewItemTag("forge:dusts/cobalt").build(ITEMS);
    public static final RegistryObject<Item> COBALT_GEAR = registerItem("cobalt_gear", new CLItem(properties())).withNewItemTag("forge:gears/cobalt").build(ITEMS);

    public static final RegistryObject<Item> ZINC_INGOT = registerItem("zinc_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/zinc").build(ITEMS);
    public static final RegistryObject<Item> ZINC_NUGGET = registerItem("zinc_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/zinc").build(ITEMS);
    public static final RegistryObject<Item> ZINC_DUST = registerItem("zinc_dust", new CLItem(properties())).withNewItemTag("forge:dusts/zinc").build(ITEMS);
    public static final RegistryObject<Item> ZINC_GEAR = registerItem("zinc_gear", new CLItem(properties())).withNewItemTag("forge:gears/zinc").build(ITEMS);

    public static final RegistryObject<Item> BRASS_INGOT = registerItem("brass_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/brass").build(ITEMS);
    public static final RegistryObject<Item> BRASS_NUGGET = registerItem("brass_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/brass").build(ITEMS);
    public static final RegistryObject<Item> BRASS_DUST = registerItem("brass_dust", new CLItem(properties())).withNewItemTag("forge:dusts/brass").build(ITEMS);
    public static final RegistryObject<Item> BRASS_GEAR = registerItem("brass_gear", new CLItem(properties())).withNewItemTag("forge:gears/brass").build(ITEMS);
    public static final RegistryObject<Item> BRASS_BLEND = registerItem("brass_blend", new CLItem(properties())).withNewItemTag("forge:ores/brass").build(ITEMS);

    public static final RegistryObject<Item> CHROMIUM_INGOT = registerItem("chromium_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/chromium").build(ITEMS);
    public static final RegistryObject<Item> CHROMIUM_NUGGET = registerItem("chromium_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/chromium").build(ITEMS);
    public static final RegistryObject<Item> CHROMIUM_DUST = registerItem("chromium_dust", new CLItem(properties())).withNewItemTag("forge:dusts/chromium").build(ITEMS);
    public static final RegistryObject<Item> CHROMIUM_GEAR = registerItem("chromium_gear", new CLItem(properties())).withNewItemTag("forge:gears/chromium").build(ITEMS);

    public static final RegistryObject<Item> THORIUM_INGOT = registerItem("thorium_ingot", new CLItem(properties())).withNewItemTag("forge:ingots/thorium").build(ITEMS);
    public static final RegistryObject<Item> THORIUM_NUGGET = registerItem("thorium_nugget", new CLItem(properties())).withNewItemTag("forge:nuggets/thorium").build(ITEMS);
    public static final RegistryObject<Item> THORIUM_DUST = registerItem("thorium_dust", new CLItem(properties())).withNewItemTag("forge:dusts/thorium").build(ITEMS);

    public static final RegistryObject<Item> COAL_NUGGET = new ItemBuilder(new ResourceLocation(CLib.MODID, "coal_nugget"), new CLItem(properties()))
            .withBurnTime(178)
            .withNewItemTag("forge:nuggets/coal")
            .build(ITEMS);

    public static final RegistryObject<Item> SILICA = registerItem("silica", new CLItem(properties())).withNewItemTag("forge:dusts/silica").build(ITEMS);
    public static final RegistryObject<Item> SILICON = registerItem("silicon", new CLItem(properties())).withNewItemTag("forge:silicon").build(ITEMS);

    public static final RegistryObject<Item> SALTPETRE_DUST = registerItem("saltpetre_dust", new CLItem(properties())).withNewItemTag("forge:dusts/saltpetre").build(ITEMS);
    public static final RegistryObject<Item> SULFUR_DUST = registerItem("sulfur_dust", new CLItem(properties())).withNewItemTag("forge:dusts/sulfur").build(ITEMS);

    public static final RegistryObject<Item> SALT = registerItem("salt", new CLItem(properties()))
            .withNewItemTag("forge:dusts/salt")
            .withNewItemTag("forge:salt")
            .build(ITEMS);

    public static final RegistryObject<Item> PYROTHEUM_DUST = new ItemBuilder(new ResourceLocation(CLib.MODID, "pyrotheum_dust"), new CLItem(properties()))
            .withBurnTime(3200)
            .withNewItemTag("forge:dusts/pyrotheum")
            .build(ITEMS);

    public static final RegistryObject<Item> IRON_HAMMER = new ItemBuilder(new ResourceLocation(CLib.MODID, "iron_hammer"), new HammerItem(properties().defaultDurability(16)))
            .withDamageableContainerItem()
            .withNewItemTag("clib:hammer")
            .build(ITEMS);

    public static final RegistryObject<Item> WOOD_DUST = registerItem("wood_dust", new CLItem(properties())).withNewItemTag("forge:dusts/wood").build(ITEMS);
    public static final RegistryObject<Item> COAL_DUST = registerItem("coal_dust", new CLItem(properties())).withNewItemTag("forge:dusts/coal").build(ITEMS);
    public static final RegistryObject<Item> IRON_DUST = registerItem("iron_dust", new CLItem(properties())).withNewItemTag("forge:dusts/iron").build(ITEMS);
    public static final RegistryObject<Item> COPPER_DUST = registerItem("copper_dust", new CLItem(properties())).withNewItemTag("forge:dusts/copper").build(ITEMS);
    public static final RegistryObject<Item> GOLD_DUST = registerItem("gold_dust", new CLItem(properties())).withNewItemTag("forge:dusts/gold").build(ITEMS);
    public static final RegistryObject<Item> LAPIS_DUST = registerItem("lapis_dust", new CLItem(properties())).withNewItemTag("forge:dusts/lapis").build(ITEMS);
    public static final RegistryObject<Item> QUARTZ_DUST = registerItem("quartz_dust", new CLItem(properties())).withNewItemTag("forge:dusts/quartz").build(ITEMS);
    public static final RegistryObject<Item> AMETHYST_DUST = registerItem("amethyst_dust", new CLItem(properties())).withNewItemTag("forge:dusts/amethyst").build(ITEMS);
    public static final RegistryObject<Item> DIAMOND_DUST = registerItem("diamond_dust", new CLItem(properties())).withNewItemTag("forge:dusts/diamond").build(ITEMS);
    public static final RegistryObject<Item> EMERALD_DUST = registerItem("emerald_dust", new CLItem(properties())).withNewItemTag("forge:dusts/emerald").build(ITEMS);
    public static final RegistryObject<Item> NETHERITE_DUST = registerItem("netherite_dust", new CLItem(properties())).withNewItemTag("forge:dusts/netherite").build(ITEMS);
    public static final RegistryObject<Item> OBSIDIAN_DUST = registerItem("obsidian_dust", new CLItem(properties())).withNewItemTag("forge:dusts/obsidian").build(ITEMS);

    public static final RegistryObject<Item> WOOD_GEAR = registerItem("wood_gear", new CLItem(properties())).withNewItemTag("forge:gears/wood").build(ITEMS);
    public static final RegistryObject<Item> STONE_GEAR = registerItem("stone_gear", new CLItem(properties())).withNewItemTag("forge:gears/stone").build(ITEMS);
    public static final RegistryObject<Item> IRON_GEAR = registerItem("iron_gear", new CLItem(properties())).withNewItemTag("forge:gears/iron").build(ITEMS);
    public static final RegistryObject<Item> COPPER_GEAR = registerItem("copper_gear", new CLItem(properties())).withNewItemTag("forge:gears/copper").build(ITEMS);
    public static final RegistryObject<Item> GOLD_GEAR = registerItem("gold_gear", new CLItem(properties())).withNewItemTag("forge:gears/iron").build(ITEMS);
    public static final RegistryObject<Item> LAPIS_GEAR = registerItem("lapis_gear", new CLItem(properties())).withNewItemTag("forge:gears/lapis").build(ITEMS);
    public static final RegistryObject<Item> QUARTZ_GEAR = registerItem("quartz_gear", new CLItem(properties())).withNewItemTag("forge:gears/quartz").build(ITEMS);
    public static final RegistryObject<Item> DIAMOND_GEAR = registerItem("diamond_gear", new CLItem(properties())).withNewItemTag("forge:gears/diamond").build(ITEMS);
    public static final RegistryObject<Item> EMERALD_GEAR = registerItem("emerald_gear", new CLItem(properties())).withNewItemTag("forge:gears/emerald").build(ITEMS);
    public static final RegistryObject<Item> NETHERITE_GEAR = registerItem("netherite_gear", new CLItem(properties())).withNewItemTag("forge:gears/netherite").build(ITEMS);

    public static final RegistryObject<Item> RUBBER = registerItem("rubber", new CLItem(properties())).withNewItemTag("forge:rubber").build(ITEMS);
    public static final RegistryObject<Item> TREE_TAP = new ItemBuilder(new ResourceLocation(CLib.MODID, "tree_tap"), new CLItem(properties().defaultDurability(5)))
            .withDamageableContainerItem()
            .withNewItemTag("clib:tree_tap")
            .build(ITEMS);

    public static ItemBuilder registerItem(String name, CLItem item) {
        return new ItemBuilder(new ResourceLocation(CLib.MODID, name), item);
    }

    public static Item.Properties properties() {
        return new Item.Properties().tab(ModItemGroups.ItemsIG.instance);
    }
}
