package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.OxygenTankModels;
import net.congueror.cgalaxy.item.*;
import net.congueror.cgalaxy.util.CGFoods;
import net.congueror.clib.api.data.ItemModelDataProvider;
import net.congueror.clib.api.objects.items.CLBucketItem;
import net.congueror.clib.api.objects.items.CLItem;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.items.UpgradeItem;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.ModCreativeTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CGalaxy.MODID);

    public static final RegistryObject<Item> SPEED_UPGRADE = new ItemBuilder("speed_upgrade",
            new UpgradeItem(new Item.Properties().tab(ModCreativeTabs.MachinesIG.instance), UpgradeItem.UpgradeType.SPEED))
            .withTranslation("Speed Upgrade")
            .build(ITEMS);
    public static final RegistryObject<Item> KEROSENE_BUCKET = new ItemBuilder("kerosene_bucket",
            new CLBucketItem(CGFluidInit.KEROSENE::getStill, new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance)))
            .withTranslation("Kerosene Bucket")
            .build(ITEMS);
    public static final RegistryObject<Item> OIL_BUCKET = new ItemBuilder("oil_bucket",
            new CLBucketItem(CGFluidInit.OIL::getStill, new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance)))
            .withTranslation("Oil Bucket")
            .build(ITEMS);
    public static final RegistryObject<Item> OXYGEN_BUCKET = new ItemBuilder("oxygen_bucket",
            new CLBucketItem(CGFluidInit.OXYGEN::getStill, new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance)))
            .withTranslation("Oxygen Bucket")
            .build(ITEMS);

    public static final RegistryObject<Item> ASTRO_ENDERMAN_EGG = new ItemBuilder("astro_enderman_spawn_egg",
            new ForgeSpawnEggItem(CGEntityTypeInit.ASTRO_ENDERMAN, MathHelper.calculateRGB(0, 0, 139), 0,
                    new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Astro Enderman Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build(ITEMS);
    public static final RegistryObject<Item> ASTRO_ZOMBIE_EGG = new ItemBuilder("astro_zombie_spawn_egg",
            new ForgeSpawnEggItem(CGEntityTypeInit.ASTRO_ZOMBIE, MathHelper.calculateRGB(0, 0, 139), 7969893,
                    new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Astro Zombie Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build(ITEMS);
    public static final RegistryObject<Item> LUNAR_VILLAGER_EGG = new ItemBuilder("lunar_villager_spawn_egg",
            new ForgeSpawnEggItem(CGEntityTypeInit.LUNAR_VILLAGER, MathHelper.calculateRGB(153,50,204), 12422002,
                    new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Lunar Villager Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build(ITEMS);
    public static final RegistryObject<Item> LUNAR_ZOMBIE_VILLAGER_EGG = new ItemBuilder("lunar_zombie_villager_spawn_egg",
            new ForgeSpawnEggItem(CGEntityTypeInit.LUNAR_ZOMBIE_VILLAGER, MathHelper.calculateRGB(153,50,204), 7969893,
                    new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Lunar Zombie Villager Spawn Egg")
            .withItemModel(ItemModelDataProvider::spawnEggTexture)
            .build(ITEMS);

    public static final RegistryObject<Item> DIAMOND_APPLE = new ItemBuilder("diamond_apple",
            new Item(new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance).food(CGFoods.DIAMOND_APPLE).rarity(Rarity.RARE)))
            .withTranslation("Diamond Apple")
            .build(ITEMS);

    public static final RegistryObject<Item> ROCKET_TIER_1 = new ItemBuilder("rocket_tier_1",
            new RocketTier1Item(new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Rocket Tier 1")
            .withItemModel(null)
            .withNewItemTag("cgalaxy:rocket")
            .build(ITEMS);

    public static final RegistryObject<Item> METEORITE_CHUNK = new ItemBuilder("meteorite_chunk",
            new CLItem(new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance)))
            .withTranslation("Meteorite Chunk")
            .build(ITEMS);

    public static final RegistryObject<Item> RAW_LUNARITE = new ItemBuilder("raw_lunarite",
            new CLItem(new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance)))
            .withTranslation("Raw Lunarite")
            .build(ITEMS);
    public static final RegistryObject<Item> LURANITE_INGOT = new ItemBuilder("lunarite_ingot",
            new CLItem(new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance).rarity(Rarity.create("purple", ChatFormatting.DARK_PURPLE))))
            .withTranslation("Lunarite Ingot")
            .build(ITEMS);

    public static final RegistryObject<Item> ASTRAL_SAPPHIRE = new ItemBuilder("astral_sapphire",
            new CLItem(new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance).rarity(Rarity.create("dark_blue", ChatFormatting.DARK_BLUE))))
            .withTranslation("Astral Sapphire")
            .build(ITEMS);
    public static final RegistryObject<Item> ASTRAL_SAPPHIRE_DUST = new ItemBuilder("astral_sapphire_dust",
            new CLItem(new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance).rarity(Rarity.create("dark_blue", ChatFormatting.DARK_BLUE))))
            .withTranslation("Astral Sapphire Dust")
            .build(ITEMS);

    public static final RegistryObject<Item> SPACE_SUIT_HELM = new ItemBuilder("oxygen_mask",
            new OxygenMaskItem(SpaceSuitItem.SpaceSuitMaterial.MNORMAL, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withItemModel(null)
            .withTranslation("Oxygen Mask")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_CHEST = new ItemBuilder("space_suit_chest",
            new SpaceSuitItem(SpaceSuitItem.SpaceSuitMaterial.NORMAL, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Top")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_LEGS = new ItemBuilder("space_suit_legs",
            new SpaceSuitItem(SpaceSuitItem.SpaceSuitMaterial.NORMAL, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Bottom")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_BOOTS = new ItemBuilder("space_suit_boots",
            new SpaceSuitItem(SpaceSuitItem.SpaceSuitMaterial.NORMAL, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Boots")
            .build(ITEMS);

    public static final RegistryObject<Item> LIGHT_OXYGEN_TANK = new ItemBuilder("light_oxygen_tank",
            new OxygenTankItem(new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance), 1000,
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
}
