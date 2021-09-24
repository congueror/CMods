package net.congueror.cgalaxy.gui.galaxy_map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.entity.rockets.RocketTier1Entity;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketTeleport;
import net.congueror.cgalaxy.world.Dimensions;
import net.congueror.clib.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class GalaxyMapScreen extends AbstractContainerScreen<GalaxyMapContainer> {
    /**
     * 1 = Milky Way,
     * 2 = Solar System,
     * 2.1 = Mercury,
     * 2.2 = Venus,
     * 2.3 = Earth,
     * 2.31 = Moon,
     * 2.4 = Mars,
     * 2.41 = Phobos,
     * 2.42 = Deimos
     */
    public static double CURRENT_MAP;

    private Button BACK_SOLAR_SYSTEM;
    private Button BACK_PLANET;

    private Button SOLAR_SYSTEM;
    private Button MERCURY;
    private Button VENUS;
    private Button EARTH;
    private Button MARS;

    private Button EARTH_SEL;
    private Button MOON_SEL;
    private Button MARS_SEL;
    private Button PHOBOS_SEL;
    private Button DEIMOS_SEL;

    private Button LAUNCH_MERCURY;
    private Button LAUNCH_VENUS;
    private Button LAUNCH_EARTH;
    private Button LAUNCH_MOON;
    private Button LAUNCH_MARS;
    private Button LAUNCH_PHOBOS;
    private Button LAUNCH_DEIMOS;

    Minecraft mc = Minecraft.getInstance();
    LocalPlayer player = mc.player;
    Entity entity = player != null ? player.getVehicle() : null;

    boolean unlocked;
    boolean tier0 = entity instanceof RocketEntity || unlocked;
    boolean tier1 = entity instanceof RocketTier1Entity || unlocked;
    boolean tier2 = entity instanceof RocketTier1Entity || unlocked;//TODO: Tier 2 check
    boolean tier3 = entity instanceof RocketTier1Entity || unlocked;//TODO: Tier 3 check
    boolean tier4 = entity instanceof RocketTier1Entity || unlocked;//TODO: Tier 4 check

    public GalaxyMapScreen(GalaxyMapContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        CURRENT_MAP = 1;
        this.imageWidth = 528;
        this.imageHeight = 498;
        this.unlocked = pMenu.unlocked;
    }

    @Override
    protected void init() {
        super.init();
        SOLAR_SYSTEM = blankMapButton(this.leftPos + 420, this.topPos + 260, 7, 7, 2);

        BACK_SOLAR_SYSTEM = blankMapButton(this.leftPos - 25, this.topPos + 320, 15, 10, 1);
        BACK_PLANET = blankMapButton(this.leftPos - 25, this.topPos + 320, 15, 10, 2);
        {
            MERCURY = blankMapButton(this.leftPos + 349, this.topPos + 241, 16, 16, 2.1);
            LAUNCH_MERCURY = launchButton(tier3, null);
        }
        {
            VENUS = blankMapButton(this.leftPos + 240, this.topPos + 280, 16, 16, 2.2);
            LAUNCH_VENUS = launchButton(tier4, null);
        }
        {
            EARTH = blankMapButton(this.leftPos + 433, this.topPos + 281, 16, 16, 2.3);
            LAUNCH_EARTH = launchButton(tier0, Level.OVERWORLD);
            LAUNCH_MOON = launchButton(tier1, Dimensions.MOON);
            EARTH_SEL = blankMapButton(this.leftPos + 275, this.topPos + 213, 96, 96, 2.3);
            MOON_SEL = blankMapButton(this.leftPos + 245, this.topPos + 340, 32, 32, 2.31);
        }
        {
            MARS = blankMapButton(this.leftPos + 195, this.topPos + 180, 16, 16, 2.4);
            LAUNCH_MARS = launchButton(tier2, null);
            LAUNCH_DEIMOS = launchButton(tier2, null);
            LAUNCH_PHOBOS = launchButton(tier2, null);
            MARS_SEL = blankMapButton(this.leftPos + 275, this.topPos + 213, 96, 96, 2.4);
            PHOBOS_SEL = blankMapButton(this.leftPos + 245, this.topPos + 335, 32, 32, 2.41);
            DEIMOS_SEL = blankMapButton(this.leftPos + 460, this.topPos + 300, 32, 32, 2.42);
        }
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();
        int infoColor = MathHelper.calculateRGB(0, 150, 255);

        SOLAR_SYSTEM.visible = false;

        BACK_SOLAR_SYSTEM.visible = false;
        BACK_PLANET.visible = false;

        MERCURY.visible = false;
        VENUS.visible = false;
        EARTH.visible = false;
        MARS.visible = false;

        EARTH_SEL.visible = false;
        MOON_SEL.visible = false;
        MARS_SEL.visible = false;
        PHOBOS_SEL.visible = false;
        DEIMOS_SEL.visible = false;

        LAUNCH_MERCURY.visible = false;
        LAUNCH_VENUS.visible = false;
        LAUNCH_EARTH.visible = false;
        LAUNCH_MOON.visible = false;
        LAUNCH_MARS.visible = false;
        LAUNCH_PHOBOS.visible = false;
        LAUNCH_DEIMOS.visible = false;

        if (CURRENT_MAP == 1) {
            SOLAR_SYSTEM.visible = true;
            RenderSystem.setShaderColor(1, 1, 1, 1);
            shaderTexture("textures/gui/galaxy_map/galaxy_map_background.png");
            blit(pPoseStack, 0, 0, 0, 0, width, height, width, height);

            shaderTexture("textures/gui/galaxy_map/galaxy_map_info.png");
            blit(pPoseStack, this.leftPos - 30, this.topPos + 180, 0, 0, 160, 160, 160, 160);

            shaderTexture("textures/gui/galaxy_map/galaxy_map_select.png");
            blit(pPoseStack, this.leftPos + 420, this.topPos + 260, 0, 0, 7, 7, 7, 7);

            if (SOLAR_SYSTEM.isHovered()) {
                RenderSystem.setShaderColor(1, 1, 1, 1);
                shaderTexture("textures/gui/galaxy_map/galaxy_map_title.png");
                blit(pPoseStack, this.leftPos + 378, this.topPos + 221, 0, 0, 96, 35, 96, 35);
                pPoseStack.pushPose();
                pPoseStack.scale(1.3f, 1.3f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.solar_system"), this.leftPos + 282, this.topPos + 196, MathHelper.calculateRGB(249, 215, 28));
                pPoseStack.popPose();
            }

            //pPoseStack, this.font, text, x, y, color
            pPoseStack.pushPose();
            pPoseStack.scale(2f, 2f, 0);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.milky_way"), this.leftPos - 38, this.topPos + 136, MathHelper.calculateRGB(255, 0, 255));
            pPoseStack.popPose();

            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.milky_way.diameter"), this.leftPos - 25, this.topPos + 230, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.milky_way.age"), this.leftPos - 25, this.topPos + 240, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.milky_way.stars"), this.leftPos - 25, this.topPos + 250, infoColor);

            pPoseStack.pushPose();
            pPoseStack.scale(0.8f, 0.8f, 0);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.hover_tip1"), this.leftPos - 19, this.topPos + 320, MathHelper.calculateRGB(0, 100, 255));
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.hover_tip2"), this.leftPos - 19, this.topPos + 330, MathHelper.calculateRGB(0, 100, 255));
            pPoseStack.popPose();
        }
        if (CURRENT_MAP == 2) {
            BACK_SOLAR_SYSTEM.visible = true;
            MERCURY.visible = true;
            VENUS.visible = true;
            EARTH.visible = true;
            MARS.visible = true;

            RenderSystem.setShaderColor(1, 1, 1, 1);
            shaderTexture("textures/gui/galaxy_map/galaxy_map_empty_background.png");
            blit(pPoseStack, 0, 0, 0, 0, width, height, width, height);

            shaderTexture("textures/gui/galaxy_map/galaxy_map_info.png");
            blit(pPoseStack, this.leftPos - 30, this.topPos + 180, 0, 0, 160, 160, 160, 160);

            //Sun
            shaderTexture("textures/sky/sun.png");
            blit(pPoseStack, this.leftPos + 304, this.topPos + 242, 0, 0, 32, 32, 32, 32);

            //RINGS START
            shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring1.png");
            blit(pPoseStack, this.leftPos + 145, this.topPos + 160, 0, 0, 350, 200, 350, 200);
            if (tier3) {
                shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring1_ul.png");
                blit(pPoseStack, this.leftPos + 145, this.topPos + 160, 0, 0, 350, 200, 350, 200);
            }

            shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring2.png");
            blit(pPoseStack, this.leftPos + 145, this.topPos + 160, 0, 0, 350, 200, 350, 200);
            if (tier4) {
                shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring2_ul.png");
                blit(pPoseStack, this.leftPos + 145, this.topPos + 160, 0, 0, 350, 200, 350, 200);
            }

            shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring3.png");
            blit(pPoseStack, this.leftPos + 145, this.topPos + 160, 0, 0, 350, 200, 350, 200);
            if (tier1) {
                shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring3_ul.png");
                blit(pPoseStack, this.leftPos + 145, this.topPos + 160, 0, 0, 350, 200, 350, 200);
            }

            shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring4.png");
            blit(pPoseStack, this.leftPos + 145, this.topPos + 160, 0, 0, 350, 200, 350, 200);
            if (tier2) {
                shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring4_ul.png");
                blit(pPoseStack, this.leftPos + 145, this.topPos + 160, 0, 0, 350, 200, 350, 200);
            }
            //RINGS END


            //Mercury
            shaderTexture("textures/gui/galaxy_map/mercury_small.png");
            blit(pPoseStack, this.leftPos + 349, this.topPos + 241, 0, 0, 16, 16, 16, 16);
            if (MERCURY.isHovered()) {
                RenderSystem.setShaderColor(1, 1, 1, 1);
                shaderTexture("textures/gui/galaxy_map/galaxy_map_title.png");
                blit(pPoseStack, this.leftPos + 311, this.topPos + 204, 0, 0, 96, 35, 96, 35);
                pPoseStack.pushPose();
                pPoseStack.scale(1.3f, 1.3f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mercury"), this.leftPos + 230, this.topPos + 184, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();
            }


            //Venus
            shaderTexture("textures/gui/galaxy_map/venus_small.png");
            blit(pPoseStack, this.leftPos + 240, this.topPos + 280, 0, 0, 16, 16, 16, 16);
            if (VENUS.isHovered()) {
                RenderSystem.setShaderColor(1, 1, 1, 1);
                shaderTexture("textures/gui/galaxy_map/galaxy_map_title.png");
                blit(pPoseStack, this.leftPos + 202, this.topPos + 243, 0, 0, 96, 35, 96, 35);
                pPoseStack.pushPose();
                pPoseStack.scale(1.3f, 1.3f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.venus"), this.leftPos + 146, this.topPos + 214, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();
            }


            //Earth
            shaderTexture("textures/gui/galaxy_map/earth_small.png");
            blit(pPoseStack, this.leftPos + 433, this.topPos + 281, 0, 0, 16, 16, 16, 16);
            if (EARTH.isHovered()) {
                RenderSystem.setShaderColor(1, 1, 1, 1);
                shaderTexture("textures/gui/galaxy_map/galaxy_map_title.png");
                blit(pPoseStack, this.leftPos + 395, this.topPos + 244, 0, 0, 96, 35, 96, 35);
                pPoseStack.pushPose();
                pPoseStack.scale(1.3f, 1.3f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.earth"), this.leftPos + 295, this.topPos + 215, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();
            }


            //Mars
            shaderTexture("textures/gui/galaxy_map/mars_small.png");
            blit(pPoseStack, this.leftPos + 195, this.topPos + 180, 0, 0, 16, 16, 16, 16);
            if (MARS.isHovered()) {
                RenderSystem.setShaderColor(1, 1, 1, 1);
                shaderTexture("textures/gui/galaxy_map/galaxy_map_title.png");
                blit(pPoseStack, this.leftPos + 157, this.topPos + 143, 0, 0, 96, 35, 96, 35);
                pPoseStack.pushPose();
                pPoseStack.scale(1.3f, 1.3f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mars"), this.leftPos + 112, this.topPos + 137, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();
            }


            //Back Button
            if (BACK_SOLAR_SYSTEM.isHovered()) {
                shaderTexture("textures/gui/galaxy_map/back_button_selected.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/back_button.png");
            }
            blit(pPoseStack, this.leftPos - 25, this.topPos + 320, 0, 0, 15, 10, 15, 10);


            pPoseStack.pushPose();
            pPoseStack.scale(1.3f, 1.3f, 0);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.solar_system"), this.leftPos - 28, this.topPos + 169, MathHelper.calculateRGB(249, 215, 28));
            pPoseStack.popPose();

            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.solar_system.diameter"), this.leftPos - 25, this.topPos + 230, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.solar_system.age"), this.leftPos - 25, this.topPos + 240, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.solar_system.planets"), this.leftPos - 25, this.topPos + 250, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.solar_system.bodies"), this.leftPos - 25, this.topPos + 260, infoColor);
        }
        if (CURRENT_MAP == 2.1) {
            LAUNCH_MERCURY.visible = true;
            drawBackground(pPoseStack);

            shaderTexture("textures/gui/galaxy_map/mercury_big.png");
            blit(pPoseStack, this.leftPos + 275, this.topPos + 213, 0, 0, 96, 96, 96, 96);

            shaderTexture("textures/gui/galaxy_map/galaxy_map_select1.png");
            blit(pPoseStack, this.leftPos + 271, this.topPos + 209, 0, 0, 104, 104, 104, 104);

            //Back Button
            if (BACK_PLANET.isHovered()) {
                shaderTexture("textures/gui/galaxy_map/back_button_selected.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/back_button.png");
            }
            blit(pPoseStack, this.leftPos - 25, this.topPos + 320, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if (tier3) {
                shaderTexture("textures/gui/galaxy_map/launch_button_ul.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/launch_button.png");
            }
            blit(pPoseStack, this.leftPos - 2, this.topPos + 313, 0, 0, 85, 20, 85, 20);

            pPoseStack.pushPose();
            pPoseStack.scale(2f, 2f, 0);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mercury"), this.leftPos - 38, this.topPos + 136, MathHelper.calculateRGB(255, 255, 255));
            pPoseStack.popPose();

            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mercury.type"), this.leftPos - 25, this.topPos + 230, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mercury.diameter"), this.leftPos - 25, this.topPos + 240, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mercury.age"), this.leftPos - 25, this.topPos + 250, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mercury.moons"), this.leftPos - 25, this.topPos + 260, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mercury.gravity"), this.leftPos - 25, this.topPos + 270, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mercury.tier"), this.leftPos - 25, this.topPos + 280, infoColor);
        }
        if (CURRENT_MAP == 2.2) {
            LAUNCH_VENUS.visible = true;
            drawBackground(pPoseStack);

            shaderTexture("textures/gui/galaxy_map/venus_big.png");
            blit(pPoseStack, this.leftPos + 275, this.topPos + 213, 0, 0, 96, 96, 96, 96);

            shaderTexture("textures/gui/galaxy_map/galaxy_map_select1.png");
            blit(pPoseStack, this.leftPos + 271, this.topPos + 209, 0, 0, 104, 104, 104, 104);

            //Back Button
            if (BACK_PLANET.isHovered()) {
                shaderTexture("textures/gui/galaxy_map/back_button_selected.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/back_button.png");
            }
            blit(pPoseStack, this.leftPos - 25, this.topPos + 320, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if (tier4) {
                shaderTexture("textures/gui/galaxy_map/launch_button_ul.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/launch_button.png");
            }
            blit(pPoseStack, this.leftPos - 2, this.topPos + 313, 0, 0, 85, 20, 85, 20);

            pPoseStack.pushPose();
            pPoseStack.scale(2f, 2f, 0);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.venus"), this.leftPos - 38, this.topPos + 136, MathHelper.calculateRGB(255, 255, 255));
            pPoseStack.popPose();

            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.venus.type"), this.leftPos - 25, this.topPos + 230, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.venus.diameter"), this.leftPos - 25, this.topPos + 240, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.venus.age"), this.leftPos - 25, this.topPos + 250, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.venus.moons"), this.leftPos - 25, this.topPos + 260, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.venus.gravity"), this.leftPos - 25, this.topPos + 270, infoColor);
            drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.venus.tier"), this.leftPos - 25, this.topPos + 280, infoColor);
        }
        if (CURRENT_MAP >= 2.3 && CURRENT_MAP < 2.4) {
            if (CURRENT_MAP == 2.31) {
                LAUNCH_MOON.visible = true;
            } else {
                LAUNCH_EARTH.visible = true;
            }
            BACK_PLANET.visible = true;
            EARTH_SEL.visible = true;
            MOON_SEL.visible = true;
            drawBackground1(pPoseStack, tier1);

            shaderTexture("textures/gui/galaxy_map/earth_big.png");
            blit(pPoseStack, this.leftPos + 275, this.topPos + 213, 0, 0, 96, 96, 96, 96);

            shaderTexture("textures/gui/galaxy_map/moon.png");
            blit(pPoseStack, this.leftPos + 245, this.topPos + 340, 0, 0, 32, 32, 32, 32);

            //Select
            if (CURRENT_MAP == 2.31) {
                shaderTexture("textures/gui/galaxy_map/galaxy_map_select2.png");
                blit(pPoseStack, this.leftPos + 242, this.topPos + 337, 0, 0, 38, 38, 38, 38);
            } else {
                shaderTexture("textures/gui/galaxy_map/galaxy_map_select1.png");
                blit(pPoseStack, this.leftPos + 271, this.topPos + 209, 0, 0, 104, 104, 104, 104);
            }

            //Back Button
            if (BACK_PLANET.isHovered()) {
                shaderTexture("textures/gui/galaxy_map/back_button_selected.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/back_button.png");
            }
            blit(pPoseStack, this.leftPos - 25, this.topPos + 320, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if ((CURRENT_MAP == 2.3 && tier0) || (CURRENT_MAP == 2.31 && tier1)) {
                shaderTexture("textures/gui/galaxy_map/launch_button_ul.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/launch_button.png");
            }
            blit(pPoseStack, this.leftPos - 2, this.topPos + 313, 0, 0, 85, 20, 85, 20);

            if (CURRENT_MAP == 2.31) {
                pPoseStack.pushPose();
                pPoseStack.scale(2f, 2f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.moon"), this.leftPos - 38, this.topPos + 136, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.moon.type"), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.moon.diameter"), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.moon.age"), this.leftPos - 25, this.topPos + 250, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.moon.moons"), this.leftPos - 25, this.topPos + 260, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.moon.gravity"), this.leftPos - 25, this.topPos + 270, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.moon.tier"), this.leftPos - 25, this.topPos + 280, infoColor);
            } else {
                pPoseStack.pushPose();
                pPoseStack.scale(2f, 2f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.earth"), this.leftPos - 38, this.topPos + 136, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.earth.type"), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.earth.diameter"), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.earth.age"), this.leftPos - 25, this.topPos + 250, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.earth.moons"), this.leftPos - 25, this.topPos + 260, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.earth.gravity"), this.leftPos - 25, this.topPos + 270, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.earth.tier"), this.leftPos - 25, this.topPos + 280, infoColor);
            }
        }
        if (CURRENT_MAP >= 2.4 && CURRENT_MAP < 2.5) {
            if (CURRENT_MAP == 2.41) {
                LAUNCH_PHOBOS.visible = true;
            } else if (CURRENT_MAP == 2.42) {
                LAUNCH_DEIMOS.visible = true;
            } else {
                LAUNCH_MARS.visible = true;
            }
            BACK_PLANET.visible = true;
            MARS_SEL.visible = true;
            PHOBOS_SEL.visible = true;
            DEIMOS_SEL.visible = true;
            drawBackground1(pPoseStack, tier2);

            shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring3.png");
            blit(pPoseStack, this.leftPos + 130, this.topPos + 165, 0, 0, 400, 250, 400, 250);
            if (tier2) {
                shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring3_ul.png");
                blit(pPoseStack, this.leftPos + 130, this.topPos + 165, 0, 0, 400, 250, 400, 250);
            }

            shaderTexture("textures/gui/galaxy_map/mars_big.png");
            blit(pPoseStack, this.leftPos + 275, this.topPos + 213, 0, 0, 96, 96, 96, 96);

            shaderTexture("textures/gui/galaxy_map/phobos.png");
            blit(pPoseStack, this.leftPos + 245, this.topPos + 335, 0, 0, 32, 32, 32, 32);

            shaderTexture("textures/gui/galaxy_map/deimos.png");
            blit(pPoseStack, this.leftPos + 460, this.topPos + 300, 0, 0, 32, 32, 32, 32);

            //Select
            if (CURRENT_MAP == 2.41) {
                shaderTexture("textures/gui/galaxy_map/galaxy_map_select2.png");
                blit(pPoseStack, this.leftPos + 242, this.topPos + 332, 0, 0, 38, 38, 38, 38);
            } else if (CURRENT_MAP == 2.42) {
                shaderTexture("textures/gui/galaxy_map/galaxy_map_select2.png");
                blit(pPoseStack, this.leftPos + 457, this.topPos + 297, 0, 0, 38, 38, 38, 38);
            } else {
                shaderTexture("textures/gui/galaxy_map/galaxy_map_select1.png");
                blit(pPoseStack, this.leftPos + 271, this.topPos + 209, 0, 0, 104, 104, 104, 104);
            }

            //Back Button
            if (BACK_PLANET.isHovered()) {
                shaderTexture("textures/gui/galaxy_map/back_button_selected.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/back_button.png");
            }
            blit(pPoseStack, this.leftPos - 25, this.topPos + 320, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if ((CURRENT_MAP == 2.4 && tier2) || (CURRENT_MAP == 2.41 && tier2) || (CURRENT_MAP == 2.42 && tier2)) {
                shaderTexture("textures/gui/galaxy_map/launch_button_ul.png");
            } else {
                shaderTexture("textures/gui/galaxy_map/launch_button.png");
            }
            blit(pPoseStack, this.leftPos - 2, this.topPos + 313, 0, 0, 85, 20, 85, 20);

            if (CURRENT_MAP == 2.41) {
                pPoseStack.pushPose();
                pPoseStack.scale(2f, 2f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.phobos"), this.leftPos - 38, this.topPos + 136, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.phobos.type"), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.phobos.diameter"), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.phobos.age"), this.leftPos - 25, this.topPos + 250, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.phobos.moons"), this.leftPos - 25, this.topPos + 260, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.phobos.gravity"), this.leftPos - 25, this.topPos + 270, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.phobos.tier"), this.leftPos - 25, this.topPos + 280, infoColor);
            } else if (CURRENT_MAP == 2.42) {
                pPoseStack.pushPose();
                pPoseStack.scale(2f, 2f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.deimos"), this.leftPos - 38, this.topPos + 136, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.deimos.type"), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.deimos.diameter"), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.deimos.age"), this.leftPos - 25, this.topPos + 250, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.deimos.moons"), this.leftPos - 25, this.topPos + 260, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.deimos.gravity"), this.leftPos - 25, this.topPos + 270, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.deimos.tier"), this.leftPos - 25, this.topPos + 280, infoColor);
            } else {
                pPoseStack.pushPose();
                pPoseStack.scale(2f, 2f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mars"), this.leftPos - 38, this.topPos + 136, MathHelper.calculateRGB(255, 255, 255));
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mars.type"), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mars.diameter"), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mars.age"), this.leftPos - 25, this.topPos + 250, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mars.moons"), this.leftPos - 25, this.topPos + 260, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mars.gravity"), this.leftPos - 25, this.topPos + 270, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.mars.tier"), this.leftPos - 25, this.topPos + 280, infoColor);
            }
        }
    }

    public void drawBackground(PoseStack pPoseStack) {
        BACK_PLANET.visible = true;
        RenderSystem.setShaderColor(1, 1, 1, 1);
        shaderTexture("textures/gui/galaxy_map/galaxy_map_empty_background.png");
        blit(pPoseStack, 0, 0, 0, 0, width, height, width, height);

        shaderTexture("textures/gui/galaxy_map/galaxy_map_info.png");
        blit(pPoseStack, this.leftPos - 30, this.topPos + 180, 0, 0, 160, 160, 160, 160);
    }

    public void drawBackground1(PoseStack pPoseStack, boolean tier) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        shaderTexture("textures/gui/galaxy_map/galaxy_map_empty_background.png");
        blit(pPoseStack, 0, 0, 0, 0, width, height, width, height);

        shaderTexture("textures/gui/galaxy_map/galaxy_map_info.png");
        blit(pPoseStack, this.leftPos - 30, this.topPos + 180, 0, 0, 160, 160, 160, 160);

        shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring3.png");
        blit(pPoseStack, this.leftPos + 155, this.topPos + 190, 0, 0, 350, 200, 350, 200);
        if (tier) {
            shaderTexture("textures/gui/galaxy_map/rings/galaxy_map_ring3_ul.png");
            blit(pPoseStack, this.leftPos + 155, this.topPos + 190, 0, 0, 350, 200, 350, 200);
        }
    }

    private Button blankMapButton(int x, int y, int width, int height, double map) {
        return addRenderableWidget(new ImageButton(x, y, width, height, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), width, height, p_onPress_1_ -> CURRENT_MAP = map));
    }

    private Button launchButton(boolean tier, ResourceKey<Level> dimension) {
        return addRenderableWidget(new ImageButton(this.leftPos - 2, this.topPos + 313, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            if (tier && player.clientLevel.dimension() != dimension) {
                Minecraft.getInstance().setScreen(null);
                CGNetwork.sendToServer(new PacketTeleport(dimension.location()));
            }
        }));
    }

    private void shaderTexture(String loc) {
        RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, loc));
    }
}
