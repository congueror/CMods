package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.items.RocketItem;
import com.congueror.clib.util.ModItemGroups;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CGalaxy.MODID);

    public static final RegistryObject<Item> KEROSENE_BUCKET = ITEMS.register("kerosene_bucket", () -> new BucketItem(FluidInit.KEROSENE, com.congueror.cores.init.ItemInit.properties().maxStackSize(1)));
    public static final RegistryObject<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(FluidInit.OIL, com.congueror.cores.init.ItemInit.properties().maxStackSize(1)));

    public static final RegistryObject<Item> ROCKET_TIER_1 = ITEMS.register("rocket_tier_1", () -> new RocketItem(new Item.Properties().group(ModItemGroups.CGalaxyIG.instance).maxStackSize(1)));
}
