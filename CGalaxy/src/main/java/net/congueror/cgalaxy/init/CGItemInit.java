package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.OxygenTankModels;
import net.congueror.cgalaxy.items.*;
import net.congueror.cgalaxy.util.CGFoods;
import net.congueror.clib.init.CLItemInit;
import net.congueror.clib.init.CLMaterialInit;
import net.congueror.clib.util.registry.data.ItemModelDataProvider;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.congueror.clib.items.generic.CLBucketItem;
import net.congueror.clib.items.generic.CLItem;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.registry.data.RecipeDataProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CGItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CGalaxy.MODID);

    public static final RegistryObject<Item> KEROSENE_BUCKET = new ItemBuilder("kerosene_bucket",
            new CLBucketItem(CGFluidInit.KEROSENE.getStill(), new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Kerosene Bucket")
            .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.bucketTexture((BucketItem) item))
            .build(ITEMS);
    public static final RegistryObject<Item> OIL_BUCKET = new ItemBuilder("oil_bucket",
            new CLBucketItem(CGFluidInit.OIL.getStill(), new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Oil Bucket")
            .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.bucketTexture((BucketItem) item))
            .build(ITEMS);
    public static final RegistryObject<Item> OXYGEN_BUCKET = new ItemBuilder("oxygen_bucket",
            new CLBucketItem(CGFluidInit.OXYGEN.getStill(), new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Oxygen Bucket")
            .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.bucketTexture((BucketItem) item))
            .build(ITEMS);
    public static final RegistryObject<Item> NITROGEN_BUCKET = new ItemBuilder("nitrogen_bucket",
            new CLBucketItem(CGFluidInit.NITROGEN.getStill(), new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Nitrogen Bucket")
            .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.bucketTexture((BucketItem) item))
            .build(ITEMS);

    public static final RegistryObject<Item> ASTRO_ENDERMAN_EGG = new ItemBuilder("astro_enderman_spawn_egg",
            new ForgeSpawnEggItem(CGEntityTypeInit.ASTRO_ENDERMAN, MathHelper.RGBtoDecimalRGB(0, 0, 139), 0,
                    new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Astro Enderman Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build(ITEMS);
    public static final RegistryObject<Item> ASTRO_ZOMBIE_EGG = new ItemBuilder("astro_zombie_spawn_egg",
            new ForgeSpawnEggItem(CGEntityTypeInit.ASTRO_ZOMBIE, MathHelper.RGBtoDecimalRGB(0, 0, 139), 7969893,
                    new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Astro Zombie Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build(ITEMS);
    public static final RegistryObject<Item> LUNAR_VILLAGER_EGG = new ItemBuilder("lunar_villager_spawn_egg",
            new ForgeSpawnEggItem(CGEntityTypeInit.LUNAR_VILLAGER, MathHelper.RGBtoDecimalRGB(153, 50, 204), 12422002,
                    new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Lunar Villager Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build(ITEMS);
    public static final RegistryObject<Item> LUNAR_ZOMBIE_VILLAGER_EGG = new ItemBuilder("lunar_zombie_villager_spawn_egg",
            new ForgeSpawnEggItem(CGEntityTypeInit.LUNAR_ZOMBIE_VILLAGER, MathHelper.RGBtoDecimalRGB(153, 50, 204), 7969893,
                    new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Lunar Zombie Villager Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build(ITEMS);

    public static final RegistryObject<Item> DIAMOND_APPLE = new ItemBuilder("diamond_apple",
            new Item(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance).food(CGFoods.DIAMOND_APPLE).rarity(Rarity.RARE)))
            .withTranslation("Diamond Apple")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("DDD")
                    .pattern("DAD")
                    .pattern("DDD")
                    .define('D', Items.DIAMOND)
                    .define('A', Items.GOLDEN_APPLE)
                    .unlockedBy("has_diamond", RecipeProvider.has(Items.DIAMOND))
                    .save(finishedRecipeConsumer))
            .build(ITEMS);

    public static final RegistryObject<Item> ROCKET_TIER_1 = new ItemBuilder("rocket_tier_1",
            new RocketTier1Item(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Rocket Tier 1")
            .withItemModel(null)
            .withNewItemTag("cgalaxy:rocket")
            .build(ITEMS);

    public static final RegistryObject<Item> METEORITE_CHUNK = new ItemBuilder("meteorite_chunk",
            new CLItem(new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Meteorite Chunk")
            .build(ITEMS);

    public static final RegistryObject<Item> LUNARITE_INGOT = new ItemBuilder("lunarite_ingot",
            new CLItem(new Item.Properties().tab(CreativeTabs.ResourcesIG.instance).rarity(Rarity.create("purple", ChatFormatting.DARK_PURPLE))))
            .withTranslation("Lunarite Ingot")
            .build(ITEMS);

    public static final RegistryObject<Item> ASTRAL_SAPPHIRE = new ItemBuilder("astral_sapphire",
            new CLItem(new Item.Properties().tab(CreativeTabs.ResourcesIG.instance).rarity(Rarity.create("dark_blue", ChatFormatting.DARK_BLUE))))
            .withTranslation("Astral Sapphire")
            .build(ITEMS);
    public static final RegistryObject<Item> ASTRAL_SAPPHIRE_DUST = new ItemBuilder("astral_sapphire_dust",
            new CLItem(new Item.Properties().tab(CreativeTabs.ResourcesIG.instance).rarity(Rarity.create("dark_blue", ChatFormatting.DARK_BLUE))))
            .withTranslation("Astral Sapphire Dust")
            .build(ITEMS);

    public static final RegistryObject<Item> SPACE_SUIT_HELM = new ItemBuilder("oxygen_mask",
            new OxygenMaskItem(EquipmentSlot.HEAD, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withItemModel(null)
            .withTranslation("Oxygen Mask")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_CHEST = new ItemBuilder("space_suit_chest",
            new SpaceSuitItem(EquipmentSlot.CHEST, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Top")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_LEGS = new ItemBuilder("space_suit_legs",
            new SpaceSuitItem(EquipmentSlot.LEGS, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Bottom")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_BOOTS = new ItemBuilder("space_suit_boots",
            new SpaceSuitItem(EquipmentSlot.FEET, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Boots")
            .build(ITEMS);

    public static final RegistryObject<Item> LIGHT_OXYGEN_TANK = new ItemBuilder("light_oxygen_tank",
            new OxygenTankItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 1000,
                    (entityModelSet, aBoolean) -> new OxygenTankModels.Light<>(entityModelSet.bakeLayer(OxygenTankModels.Light.LAYER_LOCATION), aBoolean)))
            .withTranslation("Light Oxygen Tank")
            .build(ITEMS);
    public static final RegistryObject<Item> OXYGEN_GEAR = new ItemBuilder("oxygen_gear",
            new OxygenGearItem(new Item.Properties()))
            .withTranslation("Oxygen Gear")
            .build(ITEMS);

    public static final RegistryObject<Item> HEAT_PROTECTION_1 = new ItemBuilder("heat_protection_unit_tier_1",
            new HeatProtectionUnitItem(new Item.Properties(), 1000))
            .withTranslation("Tier 1 Heat Protection Unit")
            .build(ITEMS);
    public static final RegistryObject<Item> COLD_PROTECTION_1 = new ItemBuilder("cold_protection_unit_tier_1",
            new ColdProtectionUnitItem(new Item.Properties(), 1000))
            .withTranslation("Tier 1 Cold Protection Unit")
            .build(ITEMS);
    public static final RegistryObject<Item> RADIATION_PROTECTION_1 = new ItemBuilder("radiation_protection_unit_tier_1",
            new RadiationProtectionUnitItem(new Item.Properties(), 1500))
            .withTranslation("Tier 1 Radiation Protection Unit")
            .build(ITEMS);

    public static final RegistryObject<Item> COPPER_PLATING = new ItemBuilder("copper_plating",
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 2, 0, 0, () -> null))
            .withTranslation("Copper Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CMC")
                    .pattern("MMM")
                    .define('M', Items.COPPER_INGOT)
                    .define('C', CLItemInit.TIER_1_CIRCUIT_BOARD.get())
                    .unlockedBy("has_tier_1_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_1_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build(ITEMS);
    public static final RegistryObject<Item> IRON_PLATING = new ItemBuilder("iron_plating",
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 3, 0, 0, COPPER_PLATING))
            .withTranslation("Iron Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CPC")
                    .pattern("MMM")
                    .define('M', Items.IRON_INGOT)
                    .define('C', CLItemInit.TIER_1_CIRCUIT_BOARD.get())
                    .define('P', CGItemInit.COPPER_PLATING.get())
                    .unlockedBy("has_tier_1_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_1_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build(ITEMS);
    public static final RegistryObject<Item> GOLD_PLATING = new ItemBuilder("gold_plating",
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 4, 0.2f, 0, IRON_PLATING))
            .withTranslation("Gold Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CPC")
                    .pattern("MMM")
                    .define('M', Items.GOLD_INGOT)
                    .define('C', CLItemInit.TIER_2_CIRCUIT_BOARD.get())
                    .define('P', CGItemInit.IRON_PLATING.get())
                    .unlockedBy("has_tier_2_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_2_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build(ITEMS);
    public static final RegistryObject<Item> DIAMOND_PLATING = new ItemBuilder("diamond_plating",
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 5, 1.0f, 0.02f, GOLD_PLATING))
            .withTranslation("Diamond Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CPC")
                    .pattern("MMM")
                    .define('M', Items.DIAMOND)
                    .define('C', CLItemInit.TIER_2_CIRCUIT_BOARD.get())
                    .define('P', CGItemInit.GOLD_PLATING.get())
                    .unlockedBy("has_tier_2_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_2_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build(ITEMS);
    public static final RegistryObject<Item> NETHERITE_PLATING = new ItemBuilder("netherite_plating",
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 6, 2.0f, 0.1f, DIAMOND_PLATING))
            .withTranslation("Netherite Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CPC")
                    .pattern("MMM")
                    .define('M', Items.NETHERITE_INGOT)
                    .define('C', CLItemInit.TIER_3_CIRCUIT_BOARD.get())
                    .define('P', CGItemInit.DIAMOND_PLATING.get())
                    .unlockedBy("has_tier_3_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_3_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build(ITEMS);
    public static final RegistryObject<Item> TITANIUM_PLATING = new ItemBuilder("titanium_plating",
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 7, 3.0f, 0.2f, NETHERITE_PLATING))
            .withTranslation("Titanium Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CPC")
                    .pattern("MMM")
                    .define('M', CLMaterialInit.TITANIUM.getIngot().get())
                    .define('C', CLItemInit.TIER_3_CIRCUIT_BOARD.get())
                    .define('P', CGItemInit.NETHERITE_PLATING.get())
                    .unlockedBy("has_tier_3_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_3_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build(ITEMS);
    public static final RegistryObject<Item> OPAL_PLATING = new ItemBuilder("opal_plating",
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 8, 4.0f, 0.3f, TITANIUM_PLATING))
            .withTranslation("Opal Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CPC")
                    .pattern("MMM")
                    .define('M', CLMaterialInit.OPAL.getGem().get())
                    .define('C', CLItemInit.TIER_3_CIRCUIT_BOARD.get())
                    .define('P', CGItemInit.TITANIUM_PLATING.get())
                    .unlockedBy("has_tier_3_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_3_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build(ITEMS);
}