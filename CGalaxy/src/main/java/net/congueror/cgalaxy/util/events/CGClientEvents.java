package net.congueror.cgalaxy.util.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderScreen;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryScreen;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorScreen;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerScreen;
import net.congueror.cgalaxy.blocks.sealed_cable.client.SealedCableBakedModel;
import net.congueror.cgalaxy.blocks.sealed_cable.client.SealedCableRenderer;
import net.congueror.cgalaxy.blocks.space_station_creator.SpaceStationCreatorScreen;
import net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreScreen;
import net.congueror.cgalaxy.client.AbstractRocketModel;
import net.congueror.cgalaxy.client.effects.MarsSkyEffects;
import net.congueror.cgalaxy.client.effects.MoonOrbitEffects;
import net.congueror.cgalaxy.client.effects.MoonSkyEffects;
import net.congueror.cgalaxy.client.effects.OverworldOrbitEffects;
import net.congueror.cgalaxy.client.models.*;
import net.congueror.cgalaxy.client.overlays.RocketHUDOverlay;
import net.congueror.cgalaxy.client.renderers.*;
import net.congueror.cgalaxy.client.renderers.layers.SpaceSuitLayer;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapScreen;
import net.congueror.cgalaxy.gui.space_suit.SpaceSuitScreen;
import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.items.AbstractRocketItem;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketLaunchSequence;
import net.congueror.cgalaxy.networking.PacketOpenSpaceSuitMenu;
import net.congueror.cgalaxy.util.KeyMappings;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.cgalaxy.blocks.solar_generator.SolarGeneratorScreen;
import net.congueror.clib.util.events.PlayerSetupAnimationEvent;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class CGClientEvents {

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent e) {
            e.enqueueWork(() -> {
                new MoonSkyEffects().register();
                new OverworldOrbitEffects().register();
                new MoonOrbitEffects().register();
                new MarsSkyEffects().register();
            });
            ClientRegistry.registerKeyBinding(KeyMappings.LAUNCH_ROCKET);
            ClientRegistry.registerKeyBinding(KeyMappings.OPEN_SPACE_SUIT_MENU);

            MenuScreens.register(CGContainerInit.SOLAR_GENERATOR.get(), SolarGeneratorScreen::new);

            MenuScreens.register(CGContainerInit.FUEL_LOADER.get(), FuelLoaderScreen::new);
            MenuScreens.register(CGContainerInit.FUEL_REFINERY.get(), FuelRefineryScreen::new);
            MenuScreens.register(CGContainerInit.GAS_EXTRACTOR.get(), GasExtractorScreen::new);
            MenuScreens.register(CGContainerInit.ROOM_PRESSURIZER.get(), RoomPressurizerScreen::new);
            MenuScreens.register(CGContainerInit.GALAXY_MAP.get(), GalaxyMapScreen::new);
            MenuScreens.register(CGContainerInit.SPACE_SUIT.get(), SpaceSuitScreen::new);
            MenuScreens.register(CGContainerInit.SPACE_STATION.get(), SpaceStationCoreScreen::new);
            MenuScreens.register(CGContainerInit.SPACE_STATION_CREATOR.get(), SpaceStationCreatorScreen::new);

            OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "cgalaxy_rocket_y_element", new RocketHUDOverlay());
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers e) {
            e.registerEntityRenderer(CGEntityTypeInit.ROCKET_TIER_1.get(), p_174010_ -> new RocketTier1Renderer(p_174010_, new RocketTier1Model(p_174010_.bakeLayer(RocketTier1Model.LAYER_LOCATION))));
            e.registerEntityRenderer(CGEntityTypeInit.ASTRO_ENDERMAN.get(), AstroEndermanRenderer::new);
            e.registerEntityRenderer(CGEntityTypeInit.ASTRO_ZOMBIE.get(), AstroZombieRenderer::new);
            e.registerEntityRenderer(CGEntityTypeInit.LUNAR_VILLAGER.get(), LunarVillagerRenderer::new);
            e.registerEntityRenderer(CGEntityTypeInit.LUNAR_ZOMBIE_VILLAGER.get(), LunarZombieVillagerRenderer::new);
            e.registerEntityRenderer(CGEntityTypeInit.GALACTIC_VISITOR.get(), p_174010_ -> new GalacticVisitorRenderer(p_174010_, new GalacticVisitorModel(p_174010_.bakeLayer(GalacticVisitorModel.LAYER_LOCATION))));
            e.registerEntityRenderer(CGEntityTypeInit.LASER_BLAST.get(), LaserBlastRenderer::new);
            e.registerEntityRenderer(CGEntityTypeInit.MARTIAN_SKELETON.get(), MartianSkeletonRenderer::new);

            e.registerBlockEntityRenderer(CGBlockEntityInit.SEALED_ENERGY_CABLE.get(), pContext -> new SealedCableRenderer<>(pContext, CGalaxy.location("block/sealed_energy_cable"), CGalaxy.location("block/sealed_energy_cable_1")));
            e.registerBlockEntityRenderer(CGBlockEntityInit.SEALED_ITEM_CABLE.get(), pContext -> new SealedCableRenderer<>(pContext, CGalaxy.location("block/sealed_item_cable"), CGalaxy.location("block/sealed_item_cable_1")));
            e.registerBlockEntityRenderer(CGBlockEntityInit.SEALED_FLUID_CABLE.get(), pContext -> new SealedCableRenderer<>(pContext, CGalaxy.location("block/sealed_fluid_cable"), CGalaxy.location("block/sealed_fluid_cable_1")));
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
            e.registerLayerDefinition(RocketTier1Model.LAYER_LOCATION, RocketTier1Model::createBodyLayer);
            e.registerLayerDefinition(LunarVillagerModel.LAYER_LOCATION, LunarVillagerModel::createBodyLayer);
            e.registerLayerDefinition(LunarZombieVillagerModel.LAYER_LOCATION, LunarZombieVillagerModel::createBodyLayer);
            e.registerLayerDefinition(GalacticVisitorModel.LAYER_LOCATION, () -> GalacticVisitorModel.LAYER);

            e.registerLayerDefinition(OxygenTankModels.Light.LAYER_LOCATION, OxygenTankModels.Light::createBodyLayer);
            e.registerLayerDefinition(OxygenTankModels.Medium.LAYER_LOCATION, OxygenTankModels.Medium::createBodyLayer);
            e.registerLayerDefinition(OxygenTankModels.Heavy.LAYER_LOCATION, OxygenTankModels.Heavy::createBodyLayer);
            e.registerLayerDefinition(OxygenGearModel.Body.LAYER_LOCATION, OxygenGearModel.Body::createBodyLayer);
            e.registerLayerDefinition(OxygenGearModel.Head.LAYER_LOCATION, OxygenGearModel.Head::createBodyLayer);
            e.registerLayerDefinition(OxygenMaskModel.LAYER_LOCATION, OxygenMaskModel::createBodyLayer);
        }

        @SuppressWarnings({"ConstantConditions", "unchecked", "rawtypes"})
        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers e) {
            e.getSkin("default").addLayer(new SpaceSuitLayer(e.getSkin("default")));
            e.getSkin("slim").addLayer(new SpaceSuitLayer(e.getSkin("slim")));
        }

        @SubscribeEvent
        public static void bakeModels(ModelBakeEvent e) {
            RenderingHelper.registerBakedModel(e, CGBlockInit.SEALED_ENERGY_CABLE.get(), new SealedCableBakedModel());
            RenderingHelper.registerBakedModel(e, CGBlockInit.SEALED_ITEM_CABLE.get(), new SealedCableBakedModel());
            RenderingHelper.registerBakedModel(e, CGBlockInit.SEALED_FLUID_CABLE.get(), new SealedCableBakedModel());
        }

        @SubscribeEvent
        public static void stitchTextures(TextureStitchEvent.Pre e) {
            if (e.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
                e.addSprite(CGalaxy.location("block/sealed_energy_cable"));
                e.addSprite(CGalaxy.location("block/sealed_energy_cable_1"));
                e.addSprite(CGalaxy.location("block/sealed_item_cable"));
                e.addSprite(CGalaxy.location("block/sealed_item_cable_1"));
                e.addSprite(CGalaxy.location("block/sealed_fluid_cable"));
                e.addSprite(CGalaxy.location("block/sealed_fluid_cable_1"));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeClientEvents {
        @SubscribeEvent
        public static void clientTick(TickEvent.ClientTickEvent e) {
            AbstractRocketModel.setupItemRotation();
        }

        @SubscribeEvent
        public static void renderLevelLast(RenderLevelLastEvent e) {
            PoseStack poseStack = e.getPoseStack();

            /*
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            assert mc.level != null;
            assert player != null;
            ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (heldItem.is(CGItemInit.WRENCH.get()) && heldItem.getOrCreateTag().contains("LaunchPadLink")) {
                int x = heldItem.getOrCreateTag().getCompound("LaunchPadLink").getInt("X");
                int y = heldItem.getOrCreateTag().getCompound("LaunchPadLink").getInt("Y");
                int z = heldItem.getOrCreateTag().getCompound("LaunchPadLink").getInt("Z");
                BlockPos pos = new BlockPos(x, y, z);

                BlockState blockstate = mc.level.getBlockState(pos);
                VoxelShape shape = blockstate.getShape(mc.level, pos, CollisionContext.of(player));

                //VertexConsumer vertexconsumer = mc.renderBuffers().bufferSource().getBuffer(RenderType.lines());
                Vec3 vec3 = mc.gameRenderer.getMainCamera().getPosition();
                double camX = vec3.x();
                double camY = vec3.y();
                double camZ = vec3.z();

                RenderSystem.enableDepthTest();
                RenderSystem.disableBlend();
                RenderSystem.disableTexture();
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.lineWidth(1.0F);
                BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                bufferbuilder.begin(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                bufferbuilder.vertex(poseStack.last().pose(), (float)(x - camX), (float)(y - camY), (float)(z - camZ)).color(0.0f, 1.0f, 0.1f, 0.0f).endVertex();
                bufferbuilder.vertex(poseStack.last().pose(), (float)(x - camX + 2), (float)(y - camY + 10), (float)(z - camZ + 4)).color(0.0f, 1.0f, 0.1f, 0.0f).endVertex();
                bufferbuilder.end();
                BufferUploader.end(bufferbuilder);
                RenderSystem.lineWidth(1.0F);
                RenderSystem.enableBlend();
                RenderSystem.enableTexture();
            }*/
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent e) {
            if (Minecraft.getInstance().screen == null) {
                if (e.getAction() == GLFW.GLFW_PRESS) {
                    if (e.getKey() == KeyMappings.LAUNCH_ROCKET.getKey().getValue()) {
                        CGNetwork.sendToServer(new PacketLaunchSequence());
                    }
                    if (e.getKey() == KeyMappings.OPEN_SPACE_SUIT_MENU.getKey().getValue()) {
                        LocalPlayer player = Minecraft.getInstance().player;
                        assert player != null;
                        if (SpaceSuitUtils.isEquipped(player)) {
                            CGNetwork.sendToServer(new PacketOpenSpaceSuitMenu());
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void setupPlayerRotationAngles(PlayerSetupAnimationEvent.Post e) {
            if (e.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof AbstractRocketItem) {
                e.getModelPlayer().leftArm.xRot = 172.8F;
                e.getModelPlayer().rightArm.xRot = 172.8F;
            }
        }
    }
}
