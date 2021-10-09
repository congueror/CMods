package net.congueror.clib.util.events;

import net.congueror.clib.CLib;
import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.FluidBuilder;
import net.congueror.clib.networking.CLNetwork;
import net.congueror.clib.world.FeatureGen;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

public class CLCommonEvents {

    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent e) {
            CLNetwork.registerMessages();
            e.enqueueWork(FeatureGen::registerFeatures);
        }

        @SubscribeEvent
        public static void interModProcess(InterModProcessEvent e) {
            FluidBuilder.registerBlockFluidFix();
        }

        @SubscribeEvent
        public static void onRegisterItem(final RegistryEvent.Register<Item> e) {
            BlockBuilder.registerBlockItems(e, CLib.MODID);
        }
    }

    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {

    }
}
