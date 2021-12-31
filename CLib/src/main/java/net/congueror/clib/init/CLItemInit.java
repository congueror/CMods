package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.api.objects.items.CLItem;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.items.HammerItem;
import net.congueror.clib.util.ModCreativeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CLItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CLib.MODID);

    public static final RegistryObject<Item> GHOST_ITEM = new ItemBuilder("ghost", new Item(new Item.Properties()))
            .withItemModel(null).build(ITEMS);

    public static final RegistryObject<Item> TIN_INGOT = registerItem("tin_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/tin").withTranslation("Tin Ingot").build(ITEMS);
    public static final RegistryObject<Item> TIN_NUGGET = registerItem("tin_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/tin").withTranslation("Tin Nugget").build(ITEMS);
    public static final RegistryObject<Item> TIN_DUST = registerItem("tin_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/tin").withTranslation("Tin Dust").build(ITEMS);
    public static final RegistryObject<Item> TIN_GEAR = registerItem("tin_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/tin").withTranslation("Tin Gear").build(ITEMS);

    public static final RegistryObject<Item> STEEL_INGOT = registerItem("steel_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/steel").withTranslation("Steel Ingot").build(ITEMS);
    public static final RegistryObject<Item> STEEL_NUGGET = registerItem("steel_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/steel").withTranslation("Steel Nugget").build(ITEMS);
    public static final RegistryObject<Item> STEEL_DUST = registerItem("steel_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/steel").withTranslation("Steel Dust").build(ITEMS);
    public static final RegistryObject<Item> STEEL_GEAR = registerItem("steel_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/steel").withTranslation("Steel Gear").build(ITEMS);
    public static final RegistryObject<Item> STEEL_BLEND = registerItem("steel_blend", new CLItem(properties()))
            .withNewItemTag("forge:ores/steel").withTranslation("Steel Blend").build(ITEMS);

    public static final RegistryObject<Item> ALUMINUM_INGOT = registerItem("aluminum_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/aluminum").withTranslation("Aluminum Ingot").build(ITEMS);
    public static final RegistryObject<Item> ALUMINUM_NUGGET = registerItem("aluminum_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/aluminum").withTranslation("Aluminum Nugget").build(ITEMS);
    public static final RegistryObject<Item> ALUMINUM_DUST = registerItem("aluminum_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/aluminum").withTranslation("Aluminum Dust").build(ITEMS);
    public static final RegistryObject<Item> ALUMINUM_GEAR = registerItem("aluminum_gear", new CLItem(properties())).
            withNewItemTag("forge:gears/aluminum").withTranslation("Aluminum Gear").build(ITEMS);

    public static final RegistryObject<Item> LEAD_INGOT = registerItem("lead_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/lead").withTranslation("Lead Ingot").build(ITEMS);
    public static final RegistryObject<Item> LEAD_NUGGET = registerItem("lead_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/lead").withTranslation("Lead Nugget").build(ITEMS);
    public static final RegistryObject<Item> LEAD_DUST = registerItem("lead_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/lead").withTranslation("Lead Dust").build(ITEMS);
    public static final RegistryObject<Item> LEAD_GEAR = registerItem("lead_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/lead").withTranslation("Lead Gear").build(ITEMS);

    public static final RegistryObject<Item> RUBY = registerItem("ruby", new CLItem(properties()))
            .withNewItemTag("forge:gems/ruby").withTranslation("Ruby").build(ITEMS);
    public static final RegistryObject<Item> RUBY_DUST = registerItem("ruby_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/ruby").withTranslation("Ruby Dust").build(ITEMS);
    public static final RegistryObject<Item> RUBY_GEAR = registerItem("ruby_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/ruby").withTranslation("Ruby Gear").build(ITEMS);

    public static final RegistryObject<Item> SILVER_INGOT = registerItem("silver_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/silver").withTranslation("Silver Ingot").build(ITEMS);
    public static final RegistryObject<Item> SILVER_NUGGET = registerItem("silver_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/silver").withTranslation("Silver Nugget").build(ITEMS);
    public static final RegistryObject<Item> SILVER_DUST = registerItem("silver_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/silver").withTranslation("Silver Dust").build(ITEMS);
    public static final RegistryObject<Item> SILVER_GEAR = registerItem("silver_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/silver").withTranslation("Silver Gear").build(ITEMS);

    public static final RegistryObject<Item> LUMIUM_INGOT = registerItem("lumium_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/lumium").withTranslation("Lumium Ingot").build(ITEMS);
    public static final RegistryObject<Item> LUMIUM_NUGGET = registerItem("lumium_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/lumium").withTranslation("Lumium Nugget").build(ITEMS);
    public static final RegistryObject<Item> LUMIUM_DUST = registerItem("lumium_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/lumium").withTranslation("Lumium Dust").build(ITEMS);
    public static final RegistryObject<Item> LUMIUM_GEAR = registerItem("lumium_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/lumium").withTranslation("Lumium Gear").build(ITEMS);
    public static final RegistryObject<Item> LUMIUM_BLEND = registerItem("lumium_blend", new CLItem(properties()))
            .withNewItemTag("forge:ores/lumium").withTranslation("Lumium Blend").build(ITEMS);

    public static final RegistryObject<Item> NICKEL_INGOT = registerItem("nickel_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/nickel").withTranslation("Nickel Ingot").build(ITEMS);
    public static final RegistryObject<Item> NICKEL_NUGGET = registerItem("nickel_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/nickel").withTranslation("Nickel Nugget").build(ITEMS);
    public static final RegistryObject<Item> NICKEL_DUST = registerItem("nickel_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/nickel").withTranslation("Nickel Dust").build(ITEMS);
    public static final RegistryObject<Item> NICKEL_GEAR = registerItem("nickel_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/nickel").withTranslation("Nickel Gear").build(ITEMS);

    public static final RegistryObject<Item> INVAR_INGOT = registerItem("invar_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/invar").withTranslation("Invar Ingot").build(ITEMS);
    public static final RegistryObject<Item> INVAR_NUGGET = registerItem("invar_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/invar").withTranslation("Invar Nugget").build(ITEMS);
    public static final RegistryObject<Item> INVAR_DUST = registerItem("invar_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/invar").withTranslation("Invar Dust").build(ITEMS);
    public static final RegistryObject<Item> INVAR_GEAR = registerItem("invar_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/invar").withTranslation("Invar Gear").build(ITEMS);
    public static final RegistryObject<Item> INVAR_BLEND = registerItem("invar_blend", new CLItem(properties()))
            .withNewItemTag("forge:ores/invar").withTranslation("Invar Blend").build(ITEMS);

    public static final RegistryObject<Item> ELECTRUM_INGOT = registerItem("electrum_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/electrum").withTranslation("Electrum Ingot").build(ITEMS);
    public static final RegistryObject<Item> ELECTRUM_NUGGET = registerItem("electrum_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/electrum").withTranslation("Electrum Nugget").build(ITEMS);
    public static final RegistryObject<Item> ELECTRUM_DUST = registerItem("electrum_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/electrum").withTranslation("Electrum Dust").build(ITEMS);
    public static final RegistryObject<Item> ELECTRUM_GEAR = registerItem("electrum_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/electrum").withTranslation("Electrum Gear").build(ITEMS);
    public static final RegistryObject<Item> ELECTRUM_BLEND = registerItem("electrum_blend", new CLItem(properties()))
            .withNewItemTag("forge:ores/electrum").withTranslation("Electrum Blend").build(ITEMS);

    public static final RegistryObject<Item> PLATINUM_INGOT = registerItem("platinum_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/platinum").withTranslation("Platinum Ingot").build(ITEMS);
    public static final RegistryObject<Item> PLATINUM_NUGGET = registerItem("platinum_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/platinum").withTranslation("Platinum Nugget").build(ITEMS);
    public static final RegistryObject<Item> PLATINUM_DUST = registerItem("platinum_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/platinum").withTranslation("Platinum Dust").build(ITEMS);
    public static final RegistryObject<Item> PLATINUM_GEAR = registerItem("platinum_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/platinum").withTranslation("Platinum Gear").build(ITEMS);

    public static final RegistryObject<Item> ENDERIUM_INGOT = registerItem("enderium_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/enderium").withTranslation("Enderium Ingot").build(ITEMS);
    public static final RegistryObject<Item> ENDERIUM_NUGGET = registerItem("enderium_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/enderium").withTranslation("Enderium Nugget").build(ITEMS);
    public static final RegistryObject<Item> ENDERIUM_DUST = registerItem("enderium_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/enderium").withTranslation("Enderium Dust").build(ITEMS);
    public static final RegistryObject<Item> ENDERIUM_GEAR = registerItem("enderium_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/enderium").withTranslation("Enderium Gear").build(ITEMS);
    public static final RegistryObject<Item> ENDERIUM_BLEND = registerItem("enderium_blend", new CLItem(properties()))
            .withNewItemTag("forge:ores/enderium").withTranslation("Enderium Blend").build(ITEMS);

    public static final RegistryObject<Item> SIGNALUM_INGOT = registerItem("signalum_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/signalum").withTranslation("Signalum Ingot").build(ITEMS);
    public static final RegistryObject<Item> SIGNALUM_NUGGET = registerItem("signalum_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/signalum").withTranslation("Signalum Nugget").build(ITEMS);
    public static final RegistryObject<Item> SIGNALUM_DUST = registerItem("signalum_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/signalum").withTranslation("Signalum Dust").build(ITEMS);
    public static final RegistryObject<Item> SIGNALUM_GEAR = registerItem("signalum_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/signalum").withTranslation("Signalum Gear").build(ITEMS);
    public static final RegistryObject<Item> SIGNALUM_BLEND = registerItem("signalum_blend", new CLItem(properties()))
            .withNewItemTag("forge:ores/signalum").withTranslation("Signalum Blend").build(ITEMS);

    public static final RegistryObject<Item> TUNGSTEN_INGOT = registerItem("tungsten_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/tungsten").withTranslation("Tungsten Ingot").build(ITEMS);
    public static final RegistryObject<Item> TUNGSTEN_NUGGET = registerItem("tungsten_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/tungsten").withTranslation("Tungsten Nugget").build(ITEMS);
    public static final RegistryObject<Item> TUNGSTEN_DUST = registerItem("tungsten_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/tungsten").withTranslation("Tungsten Dust").build(ITEMS);
    public static final RegistryObject<Item> TUNGSTEN_GEAR = registerItem("tungsten_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/tungsten").withTranslation("Tungsten Gear").build(ITEMS);

    public static final RegistryObject<Item> BRONZE_INGOT = registerItem("bronze_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/bronze").withTranslation("Bronze Ingot").build(ITEMS);
    public static final RegistryObject<Item> BRONZE_NUGGET = registerItem("bronze_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/bronze").withTranslation("Bronze Nugget").build(ITEMS);
    public static final RegistryObject<Item> BRONZE_DUST = registerItem("bronze_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/bronze").withTranslation("Bronze Dust").build(ITEMS);
    public static final RegistryObject<Item> BRONZE_GEAR = registerItem("bronze_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/bronze").withTranslation("Bronze Gear").build(ITEMS);
    public static final RegistryObject<Item> BRONZE_BLEND = registerItem("bronze_blend", new CLItem(properties()))
            .withNewItemTag("forge:ores/bronze").withTranslation("Bronze Blend").build(ITEMS);

    public static final RegistryObject<Item> SAPPHIRE_SHARD = registerItem("sapphire_shard", new CLItem(properties()))
            .withNewItemTag("forge:gems/sapphire")
            .withNewItemTag("forge:shards/sapphire")
            .withTranslation("Sapphire Shard")
            .build(ITEMS);
    public static final RegistryObject<Item> SAPPHIRE_DUST = registerItem("sapphire_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/sapphire")
            .withTranslation("Sapphire Dust")
            .build(ITEMS);

    public static final RegistryObject<Item> OPAL = registerItem("opal", new CLItem(properties()))
            .withNewItemTag("forge:gems/opal").withTranslation("Opal").build(ITEMS);
    public static final RegistryObject<Item> OPAL_DUST = registerItem("opal_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/opal").withTranslation("Opal Dust").build(ITEMS);
    public static final RegistryObject<Item> OPAL_GEAR = registerItem("opal_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/opal").withTranslation("Opal Gear").build(ITEMS);

    public static final RegistryObject<Item> TITANIUM_INGOT = registerItem("titanium_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/titanium").withTranslation("Titanium Ingot").build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_NUGGET = registerItem("titanium_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/titanium").withTranslation("Titanium Nugget").build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_DUST = registerItem("titanium_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/titanium").withTranslation("Titanium Dust").build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_GEAR = registerItem("titanium_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/titanium").withTranslation("Titanium Gear").build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_SCRAP = registerItem("titanium_scrap", new CLItem(properties()))
            .withNewItemTag("forge:scraps/titanium").withTranslation("Titanium Scrap").build(ITEMS);

    public static final RegistryObject<Item> URANIUM_INGOT = registerItem("uranium_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/uranium").withTranslation("Uranium Ingot").build(ITEMS);
    public static final RegistryObject<Item> URANIUM_NUGGET = registerItem("uranium_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/uranium").withTranslation("Uranium Nugget").build(ITEMS);
    public static final RegistryObject<Item> URANIUM_DUST = registerItem("uranium_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/uranium").withTranslation("Uranium Dust").build(ITEMS);

    public static final RegistryObject<Item> COBALT_INGOT = registerItem("cobalt_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/cobalt").withTranslation("Cobalt Ingot").build(ITEMS);
    public static final RegistryObject<Item> COBALT_NUGGET = registerItem("cobalt_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/cobalt").withTranslation("Cobalt Nugget").build(ITEMS);
    public static final RegistryObject<Item> COBALT_DUST = registerItem("cobalt_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/cobalt").withTranslation("Cobalt Dust").build(ITEMS);
    public static final RegistryObject<Item> COBALT_GEAR = registerItem("cobalt_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/cobalt").withTranslation("Cobalt Gear").build(ITEMS);

    public static final RegistryObject<Item> ZINC_INGOT = registerItem("zinc_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/zinc").withTranslation("Zinc Ingot").build(ITEMS);
    public static final RegistryObject<Item> ZINC_NUGGET = registerItem("zinc_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/zinc").withTranslation("Zinc Nugget").build(ITEMS);
    public static final RegistryObject<Item> ZINC_DUST = registerItem("zinc_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/zinc").withTranslation("Zinc Dust").build(ITEMS);
    public static final RegistryObject<Item> ZINC_GEAR = registerItem("zinc_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/zinc").withTranslation("Zinc Gear").build(ITEMS);

    public static final RegistryObject<Item> BRASS_INGOT = registerItem("brass_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/brass").withTranslation("Brass Ingot").build(ITEMS);
    public static final RegistryObject<Item> BRASS_NUGGET = registerItem("brass_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/brass").withTranslation("Brass Nugget").build(ITEMS);
    public static final RegistryObject<Item> BRASS_DUST = registerItem("brass_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/brass").withTranslation("Brass Dust").build(ITEMS);
    public static final RegistryObject<Item> BRASS_GEAR = registerItem("brass_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/brass").withTranslation("Brass Gear").build(ITEMS);
    public static final RegistryObject<Item> BRASS_BLEND = registerItem("brass_blend", new CLItem(properties()))
            .withNewItemTag("forge:ores/brass").withTranslation("Brass Blend").build(ITEMS);

    public static final RegistryObject<Item> CHROMIUM_INGOT = registerItem("chromium_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/chromium").withTranslation("Chromium Ingot").build(ITEMS);
    public static final RegistryObject<Item> CHROMIUM_NUGGET = registerItem("chromium_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/chromium").withTranslation("Chromium Nugget").build(ITEMS);
    public static final RegistryObject<Item> CHROMIUM_DUST = registerItem("chromium_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/chromium").withTranslation("Chromium Dust").build(ITEMS);
    public static final RegistryObject<Item> CHROMIUM_GEAR = registerItem("chromium_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/chromium").withTranslation("Chromium Gear").build(ITEMS);

    public static final RegistryObject<Item> THORIUM_INGOT = registerItem("thorium_ingot", new CLItem(properties()))
            .withNewItemTag("forge:ingots/thorium").withTranslation("Thorium Ingot").build(ITEMS);
    public static final RegistryObject<Item> THORIUM_NUGGET = registerItem("thorium_nugget", new CLItem(properties()))
            .withNewItemTag("forge:nuggets/thorium").withTranslation("Thorium Nugget").build(ITEMS);
    public static final RegistryObject<Item> THORIUM_DUST = registerItem("thorium_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/thorium").withTranslation("Thorium Dust").build(ITEMS);

    public static final RegistryObject<Item> COAL_NUGGET = new ItemBuilder("coal_nugget", new CLItem(properties()))
            .withBurnTime(178)
            .withNewItemTag("forge:nuggets/coal")
            .withTranslation("Coal Nugget")
            .build(ITEMS);

    public static final RegistryObject<Item> SILICA = registerItem("silica", new CLItem(properties()))
            .withNewItemTag("forge:dusts/silica").withTranslation("Silica").build(ITEMS);
    public static final RegistryObject<Item> SILICON = registerItem("silicon", new CLItem(properties()))
            .withNewItemTag("forge:silicon").withTranslation("Silicon").build(ITEMS);

    public static final RegistryObject<Item> SALTPETRE_DUST = registerItem("saltpetre_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/saltpetre").withTranslation("Saltpetre Dust").build(ITEMS);
    public static final RegistryObject<Item> SULFUR_DUST = registerItem("sulfur_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/sulfur").withTranslation("Sulfur Dust").build(ITEMS);

    public static final RegistryObject<Item> SALT = registerItem("salt", new CLItem(properties()))
            .withNewItemTag("forge:dusts/salt")
            .withNewItemTag("forge:salt")
            .withTranslation("Salt")
            .build(ITEMS);

    public static final RegistryObject<Item> PYROTHEUM_DUST = new ItemBuilder("pyrotheum_dust", new CLItem(properties()))
            .withBurnTime(3200)
            .withNewItemTag("forge:dusts/pyrotheum")
            .withTranslation("Pyrotheum Dust")
            .build(ITEMS);

    public static final RegistryObject<Item> IRON_HAMMER = new ItemBuilder("iron_hammer", new HammerItem(properties().defaultDurability(16)))
            .withDamageableContainerItem()
            .withNewItemTag("clib:hammer")
            .withTranslation("Iron Hammer")
            .build(ITEMS);

    public static final RegistryObject<Item> WOOD_DUST = registerItem("wood_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/wood").withTranslation("Wood Dust").build(ITEMS);
    public static final RegistryObject<Item> COAL_DUST = registerItem("coal_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/coal").withTranslation("Coal Dust").build(ITEMS);
    public static final RegistryObject<Item> IRON_DUST = registerItem("iron_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/iron").withTranslation("Iron Dust").build(ITEMS);
    public static final RegistryObject<Item> COPPER_DUST = registerItem("copper_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/copper").withTranslation("Copper Dust").build(ITEMS);
    public static final RegistryObject<Item> GOLD_DUST = registerItem("gold_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/gold").withTranslation("Gold Dust").build(ITEMS);
    public static final RegistryObject<Item> LAPIS_DUST = registerItem("lapis_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/lapis").withTranslation("Lapis Lazuli Dust").build(ITEMS);
    public static final RegistryObject<Item> QUARTZ_DUST = registerItem("quartz_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/quartz").withTranslation("Quartz Dust").build(ITEMS);
    public static final RegistryObject<Item> AMETHYST_DUST = registerItem("amethyst_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/amethyst").withTranslation("Amethyst Dust").build(ITEMS);
    public static final RegistryObject<Item> DIAMOND_DUST = registerItem("diamond_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/diamond").withTranslation("Diamond Dust").build(ITEMS);
    public static final RegistryObject<Item> EMERALD_DUST = registerItem("emerald_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/emerald").withTranslation("Emerald Dust").build(ITEMS);
    public static final RegistryObject<Item> NETHERITE_DUST = registerItem("netherite_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/netherite").withTranslation("Netherite Dust").build(ITEMS);
    public static final RegistryObject<Item> OBSIDIAN_DUST = registerItem("obsidian_dust", new CLItem(properties()))
            .withNewItemTag("forge:dusts/obsidian").withTranslation("Obsidian Dust").build(ITEMS);

    public static final RegistryObject<Item> WOOD_GEAR = registerItem("wood_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/wood").withTranslation("Wood Gear").build(ITEMS);
    public static final RegistryObject<Item> STONE_GEAR = registerItem("stone_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/stone").withTranslation("Stone Gear").build(ITEMS);
    public static final RegistryObject<Item> IRON_GEAR = registerItem("iron_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/iron").withTranslation("Iron Gear").build(ITEMS);
    public static final RegistryObject<Item> COPPER_GEAR = registerItem("copper_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/copper").withTranslation("Copper Gear").build(ITEMS);
    public static final RegistryObject<Item> GOLD_GEAR = registerItem("gold_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/iron").withTranslation("Gold Gear").build(ITEMS);
    public static final RegistryObject<Item> LAPIS_GEAR = registerItem("lapis_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/lapis").withTranslation("Lapis Lazuli Gear").build(ITEMS);
    public static final RegistryObject<Item> QUARTZ_GEAR = registerItem("quartz_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/quartz").withTranslation("Quartz Gear").build(ITEMS);
    public static final RegistryObject<Item> DIAMOND_GEAR = registerItem("diamond_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/diamond").withTranslation("Diamond Gear").build(ITEMS);
    public static final RegistryObject<Item> EMERALD_GEAR = registerItem("emerald_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/emerald").withTranslation("Emerald Gear").build(ITEMS);
    public static final RegistryObject<Item> NETHERITE_GEAR = registerItem("netherite_gear", new CLItem(properties()))
            .withNewItemTag("forge:gears/netherite").withTranslation("Netherite Gear").build(ITEMS);

    public static final RegistryObject<Item> RUBBER = registerItem("rubber", new CLItem(properties()))
            .withNewItemTag("forge:rubber").withTranslation("Rubber").build(ITEMS);
    public static final RegistryObject<Item> TREE_TAP = new ItemBuilder("tree_tap", new CLItem(properties().defaultDurability(5)))
            .withDamageableContainerItem()
            .withNewItemTag("clib:tree_tap")
            .withTranslation("Tree Tap")
            .build(ITEMS);

    public static ItemBuilder registerItem(String name, CLItem item) {
        return new ItemBuilder(name, item);
    }

    public static Item.Properties properties() {
        return new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance);
    }
}
