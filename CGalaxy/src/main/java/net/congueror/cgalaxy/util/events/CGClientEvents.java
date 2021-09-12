package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.block.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.block.oxygen_compressor.OxygenCompressorScreen;
import net.congueror.cgalaxy.client.models.RocketTier1Model;
import net.congueror.cgalaxy.client.renderers.RocketTier1Renderer;
import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.EntityRenderersEvent;
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

        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers e) {
            e.registerEntityRenderer(CGEntityTypeInit.ROCKET_TIER_1.get(), RocketTier1Renderer::new);
        }

        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
            e.registerLayerDefinition(RocketTier1Model.LAYER_LOCATION, RocketTier1Model::createBodyLayer);
        }
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeClientEvents {

    }
}
