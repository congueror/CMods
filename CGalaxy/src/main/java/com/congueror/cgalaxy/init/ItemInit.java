package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.items.RocketItem;
import com.congueror.cgalaxy.util.ModItemGroups;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CGalaxy.MODID);

    public static final RegistryObject<Item> ROCKET_TIER_1 = ITEMS.register("rocket_tier_1", () -> new RocketItem(new Item.Properties().group(ModItemGroups.ItemsIG.instance).maxStackSize(1)));
}
