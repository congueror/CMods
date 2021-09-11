package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.block.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.block.oxygen_compressor.OxygenCompressorScreen;
import net.congueror.cgalaxy.init.CGContainerInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CGClientEvents {
    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent e) {
            MenuScreens.register(CGContainerInit.FUEL_LOADER.get(), FuelLoaderScreen::new);
            MenuScreens.register(CGContainerInit.FUEL_REFINERY.get(), FuelRefineryScreen::new);
            MenuScreens.register(CGContainerInit.OXYGEN_COMPRESSOR.get(), OxygenCompressorScreen::new);
        }
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeClientEvents {

    }
}
