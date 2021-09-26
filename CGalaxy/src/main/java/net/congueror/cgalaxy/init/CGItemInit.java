package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.item.OxygenGearItem;
import net.congueror.cgalaxy.item.OxygenTankItem;
import net.congueror.cgalaxy.item.RocketTier1Item;
import net.congueror.cgalaxy.item.SpaceSuitItem;
import net.congueror.clib.api.objects.items.CLBucketItem;
import net.congueror.clib.api.objects.items.CLItem;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.items.UpgradeItem;
import net.congueror.clib.util.ModCreativeTabs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
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
            new CLItem(new Item.Properties().tab(ModCreativeTabs.ItemsIG.instance)))
            .withTranslation("Lunarite Ingot")
            .build(ITEMS);

    public static final RegistryObject<Item> SPACE_SUIT_HELM = new ItemBuilder("oxygen_mask",
            new SpaceSuitItem(EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Oxygen Mask")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_CHEST = new ItemBuilder("space_suit_chest",
            new SpaceSuitItem(EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Top")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_LEGS = new ItemBuilder("space_suit_legs",
            new SpaceSuitItem(EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Bottom")
            .build(ITEMS);
    public static final RegistryObject<Item> SPACE_SUIT_BOOTS = new ItemBuilder("space_suit_boots",
            new SpaceSuitItem(EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance)))
            .withTranslation("Space Suit Boots")
            .build(ITEMS);

    public static final RegistryObject<Item> LIGHT_OXYGEN_TANK = new ItemBuilder("light_oxygen_tank",
            new OxygenTankItem(new Item.Properties().tab(ModCreativeTabs.CGalaxyIG.instance), 1000))
            .withTranslation("Light Oxygen Tank")
            .build(ITEMS);
    public static final RegistryObject<Item> OXYGEN_GEAR = new ItemBuilder("oxygen_gear",
            new OxygenGearItem(new Item.Properties()))
            .withTranslation("Oxygen Gear")
            .build(ITEMS);
}
