package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.blocks.oxygen_compressor.OxygenCompressorScreen;
import net.congueror.cgalaxy.client.AbstractRocketModel;
import net.congueror.cgalaxy.client.models.OxygenGearModel;
import net.congueror.cgalaxy.client.models.OxygenMaskModel;
import net.congueror.cgalaxy.client.models.OxygenTankModel;
import net.congueror.cgalaxy.client.models.RocketTier1Model;
import net.congueror.cgalaxy.client.overlays.RocketYOverlay;
import net.congueror.cgalaxy.client.renderers.AstroEndermanRenderer;
import net.congueror.cgalaxy.client.renderers.AstroZombieRenderer;
import net.congueror.cgalaxy.client.renderers.MoonSkyRenderer;
import net.congueror.cgalaxy.client.renderers.RocketTier1Renderer;
import net.congueror.cgalaxy.client.renderers.layers.SpaceSuitLayer;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapScreen;
import net.congueror.cgalaxy.gui.space_suit.SpaceSuitScreen;
import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.item.SpaceSuitItem;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketLaunchSequence;
import net.congueror.cgalaxy.networking.PacketOpenSpaceSuitMenu;
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
            ClientRegistry.registerKeyBinding(KeyMappings.OPEN_SPACE_SUIT_MENU);

            MenuScreens.register(CGContainerInit.FUEL_LOADER.get(), FuelLoaderScreen::new);
            MenuScreens.register(CGContainerInit.FUEL_REFINERY.get(), FuelRefineryScreen::new);
            MenuScreens.register(CGContainerInit.OXYGEN_COMPRESSOR.get(), OxygenCompressorScreen::new);
            MenuScreens.register(CGContainerInit.GALAXY_MAP.get(), GalaxyMapScreen::new);
            MenuScreens.register(CGContainerInit.SPACE_SUIT.get(), SpaceSuitScreen::new);

            OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "cgalaxy_rocket_y_element", new RocketYOverlay());

            MoonSkyRenderer.render();
        }

        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers e) {
            e.registerEntityRenderer(CGEntityTypeInit.ROCKET_TIER_1.get(), p_174010_ -> new RocketTier1Renderer(p_174010_, new RocketTier1Model(p_174010_.bakeLayer(RocketTier1Model.LAYER_LOCATION))));
            e.registerEntityRenderer(CGEntityTypeInit.ASTRO_ENDERMAN.get(), AstroEndermanRenderer::new);
            e.registerEntityRenderer(CGEntityTypeInit.ASTRO_ZOMBIE.get(), AstroZombieRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
            e.registerLayerDefinition(RocketTier1Model.LAYER_LOCATION, RocketTier1Model::createBodyLayer);
            e.registerLayerDefinition(OxygenTankModel.LAYER_LOCATION, OxygenTankModel::createBodyLayer);
            e.registerLayerDefinition(OxygenGearModel.Body.LAYER_LOCATION, OxygenGearModel.Body::createBodyLayer);
            e.registerLayerDefinition(OxygenGearModel.Head.LAYER_LOCATION, OxygenGearModel.Head::createBodyLayer);
            e.registerLayerDefinition(OxygenMaskModel.LAYER_LOCATION, OxygenMaskModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers e) {
            e.getSkin("default").addLayer(new SpaceSuitLayer(e.getSkin("default")));
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
                if (e.getAction() == GLFW.GLFW_PRESS) {
                    if (e.getKey() == KeyMappings.LAUNCH_ROCKET.getKey().getValue()) {
                        CGNetwork.sendToServer(new PacketLaunchSequence());
                    }
                    if (e.getKey() == KeyMappings.OPEN_SPACE_SUIT_MENU.getKey().getValue()) {
                        assert Minecraft.getInstance().player != null;
                        if (SpaceSuitItem.isEquipped(Minecraft.getInstance().player)) {
                            CGNetwork.sendToServer(new PacketOpenSpaceSuitMenu());
                        }
                    }
                }
            }
        }
    }
}
