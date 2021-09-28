package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.block.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.block.oxygen_compressor.OxygenCompressorScreen;
import net.congueror.cgalaxy.client.AbstractRocketModel;
import net.congueror.cgalaxy.client.models.RocketTier1Model;
import net.congueror.cgalaxy.client.models.SpaceSuitModel;
import net.congueror.cgalaxy.client.overlays.RocketYOverlay;
import net.congueror.cgalaxy.client.renderers.MoonSkyRenderer;
import net.congueror.cgalaxy.client.renderers.RocketTier1Renderer;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapScreen;
import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketLaunchSequence;
import net.congueror.cgalaxy.util.KeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class CGClientEvents {
    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent e) {
            ClientRegistry.registerKeyBinding(KeyMappings.LAUNCH_ROCKET);

            MenuScreens.register(CGContainerInit.FUEL_LOADER.get(), FuelLoaderScreen::new);
            MenuScreens.register(CGContainerInit.FUEL_REFINERY.get(), FuelRefineryScreen::new);
            MenuScreens.register(CGContainerInit.OXYGEN_COMPRESSOR.get(), OxygenCompressorScreen::new);
            MenuScreens.register(CGContainerInit.GALAXY_MAP.get(), GalaxyMapScreen::new);

            OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "cgalaxy_rocket_y_element", new RocketYOverlay());

            MoonSkyRenderer.render();
        }

        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers e) {
            e.registerEntityRenderer(CGEntityTypeInit.ROCKET_TIER_1.get(), p_174010_ -> new RocketTier1Renderer(p_174010_, new RocketTier1Model(p_174010_.bakeLayer(RocketTier1Model.LAYER_LOCATION))));
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
            e.registerLayerDefinition(RocketTier1Model.LAYER_LOCATION, RocketTier1Model::createBodyLayer);
        }
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeClientEvents {
        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent e) {
            AbstractRocketModel.setupItemRotation();
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent e) {
            if (Minecraft.getInstance().screen == null) {
                if (e.getKey() == KeyMappings.LAUNCH_ROCKET.getKey().getValue()) {
                    if (e.getAction() == GLFW.GLFW_PRESS) {
                        CGNetwork.sendToServer(new PacketLaunchSequence());
                    }
                }
            }
        }
    }
}
