package com.congueror.cgalaxy.util.events;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.block.fuel_loader.FuelLoaderScreen;
import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryScreen;
import com.congueror.cgalaxy.client.renderers.MoonSkyRenderer;
import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.entities.rockets.RocketTier1Renderer;
import com.congueror.cgalaxy.gui.GalaxyMapScreen;
import com.congueror.cgalaxy.init.ContainerInit;
import com.congueror.cgalaxy.init.EntityTypeInit;
import com.congueror.cgalaxy.util.KeyBindings;
import com.congueror.cgalaxy.network.Networking;
import com.congueror.cgalaxy.network.PacketLaunchSequence;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent e) {
            CGalaxy.LOGGER.info("Starting client setup for CGalaxy");

            ClientRegistry.registerKeyBinding(KeyBindings.LAUNCH_ROCKET);

            ScreenManager.registerFactory(ContainerInit.FUEL_REFINERY.get(), FuelRefineryScreen::new);
            ScreenManager.registerFactory(ContainerInit.FUEL_LOADER.get(), FuelLoaderScreen::new);
            ScreenManager.registerFactory(ContainerInit.GALAXY_MAP.get(), GalaxyMapScreen::new);

            RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.ROCKET_TIER_1.get(), renderManager -> {
                return new MobRenderer(renderManager, new RocketTier1Renderer(), 0.5f) {
                    @Override
                    public ResourceLocation getEntityTexture(Entity entity) {
                        return new ResourceLocation(CGalaxy.MODID, "textures/entity/rocket_tier_1.png");
                    }
                };
            });

            MoonSkyRenderer.render();
        }
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeClientEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent e) {
            if (Minecraft.getInstance().currentScreen == null) {
                if (e.getKey() == KeyBindings.LAUNCH_ROCKET.getKey().getKeyCode()) {
                    if (e.getAction() == GLFW.GLFW_PRESS) {
                        Networking.sendToServer(new PacketLaunchSequence());
                    }
                }
            }
        }

        @SubscribeEvent
        public static void renderGameOverlay(RenderGameOverlayEvent.Post e) {
            if (!e.isCancelable() && e.getType().equals(RenderGameOverlayEvent.ElementType.HELMET)) {
                Minecraft mc = Minecraft.getInstance();
                ClientPlayerEntity player = mc.player;
                MatrixStack mStack = e.getMatrixStack();
                if (player != null) {
                    if (player.getRidingEntity() instanceof RocketEntity) {
                        int width = e.getWindow().getWidth();
                        int height = e.getWindow().getHeight();
                        mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/rocket_y_hud.png"));
                        mc.ingameGUI.blit(mStack, width / 2, height / 2, 0, 0, 26, 102, 26, 102);//TODO: Fix this
                        double y = player.getPosY();
                    }
                }
            }
        }
    }
}
