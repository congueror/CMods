package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.objects.items.CLItem;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.util.ModItemGroups;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CGalaxy.MODID);

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
