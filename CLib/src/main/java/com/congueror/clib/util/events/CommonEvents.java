package com.congueror.clib.util.events;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.util.CLTags;
import com.congueror.clib.util.ModItemGroups;
import com.congueror.clib.util.Strippables;
import com.congueror.clib.world.OreGen;
import com.congueror.clib.world.tree_gen.TreeGen;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class CommonEvents {
    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent e) {
            e.enqueueWork(() -> {
                TreeGen.registerTreeFeature();
                OreGen.registerFeatures();
            });
        }

        @SubscribeEvent
        public static void loadComplete(FMLLoadCompleteEvent e) {
            Strippables.strippableLogs();
        }

        @SubscribeEvent
        public static void onRegisterItem(final RegistryEvent.Register<Item> e) {
            final IForgeRegistry<Item> registry = e.getRegistry();
            BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> block != BlockInit.RUBBER_SAPLING.get()).forEach(block ->
            {
                final Item.Properties properties = new Item.Properties().group(ModItemGroups.BlocksIG.instance);
                final BlockItem blockItem;
                blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
            });
        }
    }

    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {

    }
}
