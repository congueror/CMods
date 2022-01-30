package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.items.generic.CLItem;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.items.HammerItem;
import net.congueror.clib.util.CreativeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CLItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CLib.MODID);

    public static final RegistryObject<Item> GHOST_ITEM = new ItemBuilder("ghost", new Item(new Item.Properties()))
            .withItemModel(null).build(ITEMS);

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

    public static final RegistryObject<Item> IRON_HAMMER = new ItemBuilder("iron_hammer", new HammerItem(new Item.Properties().tab(CreativeTabs.BlocksIG.instance).defaultDurability(16)))
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
    public static final RegistryObject<Item> TREE_TAP = new ItemBuilder("tree_tap", new CLItem(new Item.Properties().tab(CreativeTabs.BlocksIG.instance).defaultDurability(5)))
            .withDamageableContainerItem()
            .withNewItemTag("clib:tree_tap")
            .withTranslation("Tree Tap")
            .build(ITEMS);

    public static ItemBuilder registerItem(String name, CLItem item) {
        return new ItemBuilder(name, item);
    }

    public static Item.Properties properties() {
        return new Item.Properties().tab(CreativeTabs.ResourcesIG.instance);
    }
}
