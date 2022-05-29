package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.OxygenTankModels;
import net.congueror.cgalaxy.items.*;
import net.congueror.cgalaxy.util.CGFoods;
import net.congueror.clib.init.CLItemInit;
import net.congueror.clib.init.CLMaterialInit;
import net.congueror.clib.util.registry.ResourceBuilder;
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
    private static final ItemBuilder.ItemDeferredRegister REGISTER = new ItemBuilder.ItemDeferredRegister(ITEMS);

    public static final RegistryObject<CLBucketItem> KEROSENE_BUCKET = REGISTER.create("kerosene_bucket", () ->
            new CLBucketItem(CGFluidInit.KEROSENE.getStill(), new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Kerosene Bucket")
            .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.bucketTexture((BucketItem) item))
            .build();
    public static final RegistryObject<CLBucketItem> OIL_BUCKET = REGISTER.create("oil_bucket", () ->
            new CLBucketItem(CGFluidInit.OIL.getStill(), new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Oil Bucket")
            .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.bucketTexture((BucketItem) item))
            .build();
    public static final RegistryObject<CLBucketItem> OXYGEN_BUCKET = REGISTER.create("oxygen_bucket", () ->
            new CLBucketItem(CGFluidInit.OXYGEN.getStill(), new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Oxygen Bucket")
            .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.bucketTexture((BucketItem) item))
            .build();
    public static final RegistryObject<CLBucketItem> NITROGEN_BUCKET = REGISTER.create("nitrogen_bucket", () ->
            new CLBucketItem(CGFluidInit.NITROGEN.getStill(), new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Nitrogen Bucket")
            .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.bucketTexture((BucketItem) item))
            .build();

    public static final RegistryObject<ForgeSpawnEggItem> ASTRO_ENDERMAN_EGG = REGISTER.create("astro_enderman_spawn_egg", () ->
            new ForgeSpawnEggItem(CGEntityTypeInit.ASTRO_ENDERMAN, MathHelper.RGBtoDecimalRGB(0, 0, 139), 0,
                    new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Astro Enderman Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build();
    public static final RegistryObject<ForgeSpawnEggItem> ASTRO_ZOMBIE_EGG = REGISTER.create("astro_zombie_spawn_egg", () ->
            new ForgeSpawnEggItem(CGEntityTypeInit.ASTRO_ZOMBIE, MathHelper.RGBtoDecimalRGB(0, 0, 139), 7969893,
                    new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Astro Zombie Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build();
    public static final RegistryObject<ForgeSpawnEggItem> LUNAR_VILLAGER_EGG = REGISTER.create("lunar_villager_spawn_egg", () ->
            new ForgeSpawnEggItem(CGEntityTypeInit.LUNAR_VILLAGER, MathHelper.RGBtoDecimalRGB(153, 50, 204), 12422002,
                    new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Lunar Villager Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build();
    public static final RegistryObject<ForgeSpawnEggItem> LUNAR_ZOMBIE_VILLAGER_EGG = REGISTER.create("lunar_zombie_villager_spawn_egg", () ->
            new ForgeSpawnEggItem(CGEntityTypeInit.LUNAR_ZOMBIE_VILLAGER, MathHelper.RGBtoDecimalRGB(153, 50, 204), 7969893,
                    new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Lunar Zombie Villager Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build();

    public static final RegistryObject<Item> DIAMOND_APPLE = REGISTER.create("diamond_apple", () ->
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
            .build();

    public static final RegistryObject<RocketTier1Item> ROCKET_TIER_1 = REGISTER.create("rocket_tier_1", () ->
            new RocketTier1Item(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Rocket Tier 1")
            .withItemModel(null)
            .withNewItemTag("cgalaxy:rocket")
            .build();

    public static final RegistryObject<Item> METEORITE_CHUNK = REGISTER.create("meteorite_chunk", () ->
            new Item(new Item.Properties().tab(CreativeTabs.ResourcesIG.instance)))
            .withTranslation("Meteorite Chunk")
            .build();

    public static final RegistryObject<Item> LUNARITE_INGOT = REGISTER.create("lunarite_ingot", () ->
            new Item(new Item.Properties().tab(CreativeTabs.ResourcesIG.instance).rarity(Rarity.create("purple", ChatFormatting.DARK_PURPLE))))
            .withTranslation("Lunarite Ingot")
            .build();

    public static final RegistryObject<Item> ASTRAL_SAPPHIRE = REGISTER.create("astral_sapphire", () ->
            new Item(new Item.Properties().tab(CreativeTabs.ResourcesIG.instance).rarity(Rarity.create("dark_blue", ChatFormatting.DARK_BLUE))))
            .withTranslation("Astral Sapphire")
            .build();
    public static final RegistryObject<Item> ASTRAL_SAPPHIRE_DUST = REGISTER.create("astral_sapphire_dust", () ->
            new Item(new Item.Properties().tab(CreativeTabs.ResourcesIG.instance).rarity(Rarity.create("dark_blue", ChatFormatting.DARK_BLUE))))
            .withTranslation("Astral Sapphire Dust")
            .build();

    public static final RegistryObject<OxygenMaskItem> SPACE_SUIT_HELM = REGISTER.create("oxygen_mask", () ->
            new OxygenMaskItem(EquipmentSlot.HEAD, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withItemModel(null)
            .withTranslation("Oxygen Mask")
            .build();
    public static final RegistryObject<SpaceSuitItem> SPACE_SUIT_CHEST = REGISTER.create("space_suit_chest", () ->
            new SpaceSuitItem(EquipmentSlot.CHEST, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Top")
            .build();
    public static final RegistryObject<SpaceSuitItem> SPACE_SUIT_LEGS = REGISTER.create("space_suit_legs", () ->
            new SpaceSuitItem(EquipmentSlot.LEGS, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Bottom")
            .build();
    public static final RegistryObject<SpaceSuitItem> SPACE_SUIT_BOOTS = REGISTER.create("space_suit_boots", () ->
            new SpaceSuitItem(EquipmentSlot.FEET, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Boots")
            .build();

    public static final RegistryObject<OxygenTankItem> LIGHT_OXYGEN_TANK = REGISTER.create("light_oxygen_tank", () ->
            new OxygenTankItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 1000,
                    (entityModelSet, aBoolean) -> new OxygenTankModels.Light<>(entityModelSet.bakeLayer(OxygenTankModels.Light.LAYER_LOCATION), aBoolean)))
            .withTranslation("Light Oxygen Tank")
            .build();
    public static final RegistryObject<OxygenGearItem> OXYGEN_GEAR = REGISTER.create("oxygen_gear", () ->
            new OxygenGearItem(new Item.Properties()))
            .withTranslation("Oxygen Gear")
            .build();

    public static final RegistryObject<HeatProtectionUnitItem> HEAT_PROTECTION_1 = REGISTER.create("heat_protection_unit_tier_1", () ->
            new HeatProtectionUnitItem(new Item.Properties(), 1000))
            .withTranslation("Tier 1 Heat Protection Unit")
            .build();
    public static final RegistryObject<ColdProtectionUnitItem> COLD_PROTECTION_1 = REGISTER.create("cold_protection_unit_tier_1", () ->
            new ColdProtectionUnitItem(new Item.Properties(), 1000))
            .withTranslation("Tier 1 Cold Protection Unit")
            .build();
    public static final RegistryObject<RadiationProtectionUnitItem> RADIATION_PROTECTION_1 = REGISTER.create("radiation_protection_unit_tier_1", () ->
            new RadiationProtectionUnitItem(new Item.Properties(), 1500))
            .withTranslation("Tier 1 Radiation Protection Unit")
            .build();

    public static final RegistryObject<SpaceSuitUpgradeItem> COPPER_PLATING = REGISTER.create("copper_plating", () ->
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
            .build();
    public static final RegistryObject<SpaceSuitUpgradeItem> IRON_PLATING = REGISTER.create("iron_plating", () ->
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
            .build();
    public static final RegistryObject<SpaceSuitUpgradeItem> GOLD_PLATING = REGISTER.create("gold_plating", () ->
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
            .build();
    public static final RegistryObject<SpaceSuitUpgradeItem> DIAMOND_PLATING = REGISTER.create("diamond_plating", () ->
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
            .build();
    public static final RegistryObject<SpaceSuitUpgradeItem> NETHERITE_PLATING = REGISTER.create("netherite_plating", () ->
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
            .build();
    public static final RegistryObject<SpaceSuitUpgradeItem> TITANIUM_PLATING = REGISTER.create("titanium_plating", () ->
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 7, 3.0f, 0.2f, NETHERITE_PLATING))
            .withTranslation("Titanium Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CPC")
                    .pattern("MMM")
                    .define('M', CLMaterialInit.TITANIUM.ingot().get())
                    .define('C', CLItemInit.TIER_3_CIRCUIT_BOARD.get())
                    .define('P', CGItemInit.NETHERITE_PLATING.get())
                    .unlockedBy("has_tier_3_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_3_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build();
    public static final RegistryObject<SpaceSuitUpgradeItem> OPAL_PLATING = REGISTER.create("opal_plating", () ->
            new SpaceSuitUpgradeItem(new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance), 8, 4.0f, 0.3f, TITANIUM_PLATING))
            .withTranslation("Opal Plating")
            .withRecipe((finishedRecipeConsumer, item) -> ShapedRecipeBuilder.shaped(item)
                    .pattern("MMM")
                    .pattern("CPC")
                    .pattern("MMM")
                    .define('M', CLMaterialInit.OPAL.gem().get())
                    .define('C', CLItemInit.TIER_3_CIRCUIT_BOARD.get())
                    .define('P', CGItemInit.TITANIUM_PLATING.get())
                    .unlockedBy("has_tier_3_circuit_board", RecipeDataProvider.has(CLItemInit.TIER_3_CIRCUIT_BOARD.get()))
                    .save(finishedRecipeConsumer))
            .build();
}