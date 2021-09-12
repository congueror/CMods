package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.rockets.RocketTier1Entity;
import net.congueror.cgalaxy.item.RocketItem;
import net.congueror.cgalaxy.item.RocketTier1Item;
import net.congueror.clib.api.objects.items.CLBucketItem;
import net.congueror.clib.api.objects.items.CLItem;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.util.ModItemGroups;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CGalaxy.MODID);

    public static final RegistryObject<Item> KEROSENE_BUCKET = new ItemBuilder("kerosene_bucket",
            new CLBucketItem(CGFluidInit.KEROSENE::getStill, new Item.Properties().tab(ModItemGroups.ItemsIG.instance)))
            .withTranslation("Kerosene Bucket")
            .build(ITEMS);
    public static final RegistryObject<Item> OIL_BUCKET = new ItemBuilder("oil_bucket",
            new CLBucketItem(CGFluidInit.OIL::getStill, new Item.Properties().tab(ModItemGroups.ItemsIG.instance)))
            .withTranslation("Oil Bucket")
            .build(ITEMS);
    public static final RegistryObject<Item> OXYGEN_BUCKET = new ItemBuilder("oxygen_bucket",
            new CLBucketItem(CGFluidInit.OXYGEN::getStill, new Item.Properties().tab(ModItemGroups.ItemsIG.instance)))
            .withTranslation("Oxygen Bucket")
            .build(ITEMS);

    public static final RegistryObject<Item> ROCKET_TIER_1 = new ItemBuilder("rocket_tier_1",
            new RocketTier1Item(new Item.Properties().tab(ModItemGroups.CGalaxyIG.instance)))
            .withTranslation("Rocket Tier 1")
            .build(ITEMS);

    public static final RegistryObject<Item> METEORITE_CHUNK = new ItemBuilder("meteorite_chunk",
            new CLItem(new Item.Properties().tab(ModItemGroups.ItemsIG.instance)))
            .withTranslation("Meteorite Chunk")
            .build(ITEMS);

    public static final RegistryObject<Item> RAW_LUNARITE = new ItemBuilder("raw_lunarite",
            new CLItem(new Item.Properties().tab(ModItemGroups.ItemsIG.instance)))
            .withTranslation("Raw Lunarite")
            .build(ITEMS);
    public static final RegistryObject<Item> LURANITE_INGOT = new ItemBuilder("lunarite_ingot",
            new CLItem(new Item.Properties().tab(ModItemGroups.ItemsIG.instance)))
            .withTranslation("Lunarite Ingot")
            .build(ITEMS);
}
