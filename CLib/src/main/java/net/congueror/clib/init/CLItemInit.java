package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.items.HammerItem;
import net.congueror.clib.items.UpgradeItem;
import net.congueror.clib.items.WrenchItem;
import net.congueror.clib.items.generic.CLItem;
import net.congueror.clib.util.CLTags;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.congueror.clib.util.registry.data.RecipeDataProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CLItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CLib.MODID);
    private static final ItemBuilder.ItemDeferredRegister REGISTER = new ItemBuilder.ItemDeferredRegister(ITEMS);

    public static final RegistryObject<Item> GHOST_ITEM = REGISTER.create("ghost", () -> new Item(new Item.Properties()))
            .withItemModel(null).build();

    public static final RegistryObject<CLItem> COAL_NUGGET = REGISTER.create("coal_nugget", () -> new CLItem(properties(), 178, 0))
            .withItemTags(CLTags.Items.NUGGETS_COAL)
            .withTranslation("Coal Nugget")
            .withTranslation("\u7164\u70ad\u7c92", "zh_cn")
            .build();

    public static final RegistryObject<Item> SILICON = REGISTER.create("silicon", () -> new Item(properties()))
            .withItemTags(CLTags.Items.SILICON)
            .withTranslation("Silicon")
            .build();

    public static final RegistryObject<Item> SALTPETRE_DUST = REGISTER.create("saltpetre_dust", () -> new Item(properties()))
            .withItemTags(CLTags.Items.DUSTS_SALTPETRE)
            .withTranslation("Saltpetre Dust")
            .withTranslation("\u785d\u77f3\u7c89", "zh_cn")
            .build();
    public static final RegistryObject<Item> SULFUR_DUST = REGISTER.create("sulfur_dust", () -> new Item(properties()))
            .withItemTags(CLTags.Items.DUSTS_SULFUR)
            .withTranslation("Sulfur Dust")
            .withTranslation("\u786b\u7c89", "zh_cn")
            .build();

    public static final RegistryObject<Item> SALT = REGISTER.create("salt", () -> new Item(properties()))
            .withItemTags(CLTags.Items.DUSTS_SALT, CLTags.Items.SALT)
            .withTranslation("Salt")
            .build();

    public static final RegistryObject<CLItem> PYROTHEUM_DUST = REGISTER.create("pyrotheum_dust", () -> new CLItem(properties(), 3200, 0))
            .withItemTags(CLTags.Items.DUSTS_PYROTHEUM)
            .withTranslation("Pyrotheum Dust")
            .withTranslation("\u70bd\u7131\u7c89", "zh_cn")
            .build();

    public static final RegistryObject<HammerItem> IRON_HAMMER = REGISTER.create("iron_hammer", () -> new HammerItem(new Item.Properties().tab(CreativeTabs.AssortmentsIG.instance).defaultDurability(16)))
            .withItemTags(CLTags.Items.HAMMERS)
            .withTranslation("Iron Hammer")
            .build();

    public static final RegistryObject<Item> WOOD_DUST = REGISTER.create("wood_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/wood").withTranslation("Wood Dust").build();
    public static final RegistryObject<Item> COAL_DUST = REGISTER.create("coal_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/coal").withTranslation("Coal Dust").build();
    public static final RegistryObject<Item> IRON_DUST = REGISTER.create("iron_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/iron").withTranslation("Iron Dust").build();
    public static final RegistryObject<Item> COPPER_DUST = REGISTER.create("copper_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/copper").withTranslation("Copper Dust").build();
    public static final RegistryObject<Item> GOLD_DUST = REGISTER.create("gold_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/gold").withTranslation("Gold Dust").build();
    public static final RegistryObject<Item> LAPIS_DUST = REGISTER.create("lapis_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/lapis").withTranslation("Lapis Lazuli Dust").build();
    public static final RegistryObject<Item> QUARTZ_DUST = REGISTER.create("quartz_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/quartz").withTranslation("Quartz Dust").build();
    public static final RegistryObject<Item> AMETHYST_DUST = REGISTER.create("amethyst_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/amethyst").withTranslation("Amethyst Dust").build();
    public static final RegistryObject<Item> DIAMOND_DUST = REGISTER.create("diamond_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/diamond").withTranslation("Diamond Dust").build();
    public static final RegistryObject<Item> EMERALD_DUST = REGISTER.create("emerald_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/emerald").withTranslation("Emerald Dust").build();
    public static final RegistryObject<Item> NETHERITE_DUST = REGISTER.create("netherite_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/netherite").withTranslation("Netherite Dust").build();
    public static final RegistryObject<Item> OBSIDIAN_DUST = REGISTER.create("obsidian_dust", () -> new Item(properties()))
            .withItemTag("forge:dusts/obsidian").withTranslation("Obsidian Dust").build();

    public static final RegistryObject<Item> WOOD_GEAR = REGISTER.create("wood_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/wood").withTranslation("Wood Gear").build();
    public static final RegistryObject<Item> STONE_GEAR = REGISTER.create("stone_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/stone").withTranslation("Stone Gear").build();
    public static final RegistryObject<Item> IRON_GEAR = REGISTER.create("iron_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/iron").withTranslation("Iron Gear").build();
    public static final RegistryObject<Item> COPPER_GEAR = REGISTER.create("copper_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/copper").withTranslation("Copper Gear").build();
    public static final RegistryObject<Item> GOLD_GEAR = REGISTER.create("gold_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/iron").withTranslation("Gold Gear").build();
    public static final RegistryObject<Item> LAPIS_GEAR = REGISTER.create("lapis_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/lapis").withTranslation("Lapis Lazuli Gear").build();
    public static final RegistryObject<Item> QUARTZ_GEAR = REGISTER.create("quartz_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/quartz").withTranslation("Quartz Gear").build();
    public static final RegistryObject<Item> DIAMOND_GEAR = REGISTER.create("diamond_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/diamond").withTranslation("Diamond Gear").build();
    public static final RegistryObject<Item> EMERALD_GEAR = REGISTER.create("emerald_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/emerald").withTranslation("Emerald Gear").build();
    public static final RegistryObject<Item> NETHERITE_GEAR = REGISTER.create("netherite_gear", () -> new Item(properties()))
            .withItemTag("forge:gears/netherite").withTranslation("Netherite Gear").build();

    public static final RegistryObject<Item> RUBBER = REGISTER.create("rubber", () -> new Item(properties()))
            .withItemTag("forge:rubber").withTranslation("Rubber").build();
    public static final RegistryObject<CLItem> TREE_TAP = REGISTER.create("tree_tap", () -> new CLItem(new Item.Properties().tab(CreativeTabs.AssortmentsIG.instance)
                    .defaultDurability(5), -1, 2))
            .withItemTag("clib:tree_tap")
            .withTranslation("Tree Tap")
            .build();

    public static final RegistryObject<Item> COPPER_CIRCUIT = REGISTER.create("copper_circuit", () -> new Item(
            new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Copper Circuit")
            .withRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer, item, 1,
                    Tags.Items.INGOTS_COPPER,
                    IRON_HAMMER, () -> Items.COPPER_INGOT, () -> Items.COPPER_INGOT))
            .build();

    public static final RegistryObject<Item> SILICON_CIRCUIT = REGISTER.create("silicon_circuit", () -> new Item(
            new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Silicon Circuit")
            .withRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer, item, 1,
                    CLTags.Items.SILICON,
                    IRON_HAMMER, SILICON, SILICON))
            .build();

    public static final RegistryObject<Item> SAPPHIRE_CIRCUIT_CORE = REGISTER.create("sapphire_circuit_core", () -> new Item(
            new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Sapphire Circuit Core")
            .withRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer, item, 1,
                    CLTags.Items.SHARDS_SAPPHIRE,
                    IRON_HAMMER, PYROTHEUM_DUST, CLMaterialInit.SAPPHIRE.shard(), CLMaterialInit.SAPPHIRE.shard(), CLMaterialInit.SAPPHIRE.shard(), () -> Items.REDSTONE_BLOCK))
            .build();

    public static final RegistryObject<Item> DIAMOND_CIRCUIT_CORE = REGISTER.create("diamond_circuit_core", () -> new Item(
            new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Diamond Circuit Core")
            .withRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer, item, 1,
                    Tags.Items.GEMS_DIAMOND,
                    PYROTHEUM_DUST, () -> Items.DIAMOND, () -> Items.DIAMOND, () -> Items.DIAMOND, IRON_HAMMER, SAPPHIRE_CIRCUIT_CORE, () -> Items.REDSTONE_BLOCK))
            .build();

    public static final RegistryObject<Item> RUBY_CIRCUIT_CORE = REGISTER.create("ruby_circuit_core", () -> new Item(
            new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Ruby Circuit Core")
            .withRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer, item, 1,
                    CLTags.Items.GEMS_RUBY,
                    PYROTHEUM_DUST, CLMaterialInit.RUBY.gem(), CLMaterialInit.RUBY.gem(), CLMaterialInit.RUBY.gem(), IRON_HAMMER, DIAMOND_CIRCUIT_CORE, () -> Items.REDSTONE_BLOCK))
            .build();

    public static final RegistryObject<Item> TIER_1_CIRCUIT_BOARD = REGISTER.create("tier_1_circuit_board", () -> new Item(
            new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Tier 1 Circuit Board")
            .withRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer, item, 1,
                    CLTags.Items.HAMMERS,
                    IRON_HAMMER, COPPER_CIRCUIT, COPPER_CIRCUIT, SILICON_CIRCUIT, SILICON_CIRCUIT, SAPPHIRE_CIRCUIT_CORE))
            .build();

    public static final RegistryObject<Item> TIER_2_CIRCUIT_BOARD = REGISTER.create("tier_2_circuit_board", () -> new Item(
            new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Tier 2 Circuit Board")
            .withRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer, item, 1,
                    CLTags.Items.HAMMERS,
                    IRON_HAMMER, COPPER_CIRCUIT, COPPER_CIRCUIT, SILICON_CIRCUIT, SILICON_CIRCUIT, DIAMOND_CIRCUIT_CORE))
            .build();

    public static final RegistryObject<Item> TIER_3_CIRCUIT_BOARD = REGISTER.create("tier_3_circuit_board", () -> new Item(
            new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Tier 3 Circuit Board")
            .withRecipe((finishedRecipeConsumer, item) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer, item, 1,
                    CLTags.Items.HAMMERS,
                    IRON_HAMMER, COPPER_CIRCUIT, COPPER_CIRCUIT, SILICON_CIRCUIT, SILICON_CIRCUIT, RUBY_CIRCUIT_CORE))
            .build();

    public static final RegistryObject<UpgradeItem> SPEED_UPGRADE = REGISTER.create("speed_upgrade", () ->
            new UpgradeItem(new Item.Properties().tab(CreativeTabs.MachinesIG.instance), UpgradeItem.UpgradeType.SPEED))
            .withTranslation("Speed Upgrade")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("DGD")
                    .pattern("RBR")
                    .pattern("DGD")
                    .define('D', Items.GLOWSTONE_DUST)
                    .define('G', GOLD_GEAR.get())
                    .define('R', Items.REDSTONE)
                    .define('B', TIER_1_CIRCUIT_BOARD.get())
                    .unlockedBy("has_tier_1_circuit_board", RecipeDataProvider.has(TIER_1_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build();

    public static final RegistryObject<WrenchItem> WRENCH = REGISTER.create("wrench", () ->
                    new WrenchItem(new Item.Properties().tab(CreativeTabs.MachinesIG.instance)))
            .withTranslation("Wrench")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern(" II")
                    .pattern(" RI")
                    .pattern("I  ")
                    .define('I', CLMaterialInit.CHROMIUM.ingot().get())
                    .define('R', Items.RED_DYE)
                    .unlockedBy("has_chromium", RecipeDataProvider.has(CLMaterialInit.CHROMIUM.ingot().get()))
                    .save(finishedRecipeConsumer))
            .build();

    public static Item.Properties properties() {
        return new Item.Properties().tab(CreativeTabs.ResourcesIG.instance);
    }
}
