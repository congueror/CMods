package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.items.OxygenGearItem;
import com.congueror.cgalaxy.items.OxygenTankItem;
import com.congueror.cgalaxy.items.RocketItem;
import com.congueror.cgalaxy.items.SpaceSuitItem;
import com.congueror.clib.items.UpgradeItem;
import com.congueror.clib.util.ModItemGroups;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CGalaxy.MODID);

    public static final RegistryObject<Item> KEROSENE_BUCKET = ITEMS.register("kerosene_bucket", () -> new BucketItem(FluidInit.KEROSENE, com.congueror.clib.init.ItemInit.properties().maxStackSize(1).containerItem(Items.BUCKET)));
    public static final RegistryObject<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(FluidInit.OIL, com.congueror.clib.init.ItemInit.properties().maxStackSize(1).containerItem(Items.BUCKET)));
    public static final RegistryObject<Item> OXYGEN_BUCKET = ITEMS.register("oxygen_bucket", () -> new BucketItem(FluidInit.OXYGEN, com.congueror.clib.init.ItemInit.properties().maxStackSize(1).containerItem(Items.BUCKET)));

    public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", () -> new UpgradeItem(new Item.Properties().group(ModItemGroups.MachinesIG.instance), UpgradeItem.UpgradeType.SPEED));

    public static final RegistryObject<Item> SPACESUIT_HELM = ITEMS.register("space_suit_helm", () -> new SpaceSuitItem(EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.CGalaxyIG.instance)));
    public static final RegistryObject<Item> SPACESUIT_CHEST = ITEMS.register("space_suit_chest", () -> new SpaceSuitItem(EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.CGalaxyIG.instance)));
    public static final RegistryObject<Item> SPACESUIT_LEGS = ITEMS.register("space_suit_legs", () -> new SpaceSuitItem(EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.CGalaxyIG.instance)));
    public static final RegistryObject<Item> SPACESUIT_BOOTS = ITEMS.register("space_suit_boots", () -> new SpaceSuitItem(EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.CGalaxyIG.instance)));

    public static final RegistryObject<Item> LIGHT_OXYGEN_TANK = ITEMS.register("light_oxygen_tank", () -> new OxygenTankItem(new Item.Properties().group(ModItemGroups.CGalaxyIG.instance), 1000));
    public static final RegistryObject<Item> OXYGEN_GEAR = ITEMS.register("oxygen_gear", () -> new OxygenGearItem(new Item.Properties().group(ModItemGroups.CGalaxyIG.instance)));

    public static final RegistryObject<Item> ROCKET_TIER_1 = ITEMS.register("rocket_tier_1", () -> new RocketItem(new Item.Properties().group(ModItemGroups.CGalaxyIG.instance).maxStackSize(1)));
}
