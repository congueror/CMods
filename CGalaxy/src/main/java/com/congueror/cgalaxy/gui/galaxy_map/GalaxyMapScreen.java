package com.congueror.cgalaxy.gui.galaxy_map;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.entities.rockets.RocketTier1Entity;
import com.congueror.cgalaxy.network.Networking;
import com.congueror.cgalaxy.network.PacketTeleport;
import com.congueror.cgalaxy.world.dimension.Dimensions;
import com.congueror.clib.util.MathHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class GalaxyMapScreen extends ContainerScreen<GalaxyMapContainer> {

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
    ClientPlayerEntity player = mc.player;
    Entity entity = player != null ? player.getRidingEntity() : null;

    boolean unlocked;
    boolean tier0 = entity instanceof RocketEntity || unlocked;
    boolean tier1 = entity instanceof RocketTier1Entity || unlocked;
    boolean tier2 = entity instanceof RocketTier1Entity || unlocked;//TODO: Tier 2 check
    boolean tier3 = entity instanceof RocketTier1Entity || unlocked;//TODO: Tier 3 check
    boolean tier4 = entity instanceof RocketTier1Entity || unlocked;//TODO: Tier 4 check

    public GalaxyMapScreen(GalaxyMapContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        CURRENT_MAP = 1;
        this.xSize = 528;
        this.ySize = 498;
        this.unlocked = screenContainer.unlocked;
    }

    @Override
    protected void init() {
        super.init();
        SOLAR_SYSTEM = blankMapButton(this.guiLeft + 420, this.guiTop + 260, 7, 7, 2);

        BACK_SOLAR_SYSTEM = blankMapButton(this.guiLeft - 25, this.guiTop + 320, 15, 10, 1);
        BACK_PLANET = blankMapButton(this.guiLeft - 25, this.guiTop + 320, 15, 10, 2);
        {
            MERCURY = blankMapButton(this.guiLeft + 349, this.guiTop + 241, 16, 16, 2.1);
            LAUNCH_MERCURY = launchButton(tier3, null);
        }
        {
            VENUS = blankMapButton(this.guiLeft + 240, this.guiTop + 280, 16, 16, 2.2);
            LAUNCH_VENUS = launchButton(tier4, null);
        }
        {
            EARTH = blankMapButton(this.guiLeft + 433, this.guiTop + 281, 16, 16, 2.3);
            LAUNCH_EARTH = launchButton(tier0, World.OVERWORLD);
            LAUNCH_MOON = launchButton(tier1, Dimensions.MOON);
            EARTH_SEL = blankMapButton(this.guiLeft + 275, this.guiTop + 213, 96, 96, 2.3);
            MOON_SEL = blankMapButton(this.guiLeft + 245, this.guiTop + 340, 32, 32, 2.31);
        }
        {
            MARS = blankMapButton(this.guiLeft + 195, this.guiTop + 180, 16, 16, 2.4);
            LAUNCH_MARS = launchButton(tier2, null);
            LAUNCH_DEIMOS = launchButton(tier2, null);
            LAUNCH_PHOBOS = launchButton(tier2, null);
            MARS_SEL = blankMapButton(this.guiLeft + 275, this.guiTop + 213, 96, 96, 2.4);
            PHOBOS_SEL = blankMapButton(this.guiLeft + 245, this.guiTop + 335, 32, 32, 2.41);
            DEIMOS_SEL = blankMapButton(this.guiLeft + 460, this.guiTop + 300, 32, 32, 2.42);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        int width = mc.getMainWindow().getScaledWidth();
        int height = mc.getMainWindow().getScaledHeight();
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
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, this.guiLeft - 30, this.guiTop + 180, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select.png"));
            this.blit(matrixStack, this.guiLeft + 420, this.guiTop + 260, 0, 0, 7, 7, 7, 7);

            if (SOLAR_SYSTEM.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, this.guiLeft + 378, this.guiTop + 221, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system"), this.guiLeft + 282, this.guiTop + 196, MathHelper.calculateRGB(249, 215, 28));
                matrixStack.pop();
            }

            //matrixStack, this.font, text, x, y, color
            matrixStack.push();
            matrixStack.scale(2f, 2f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.milky_way"), this.guiLeft - 38, this.guiTop + 136, MathHelper.calculateRGB(255, 0, 255));
            matrixStack.pop();

            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.milky_way.diameter"), this.guiLeft - 25, this.guiTop + 230, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.milky_way.age"), this.guiLeft - 25, this.guiTop + 240, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.milky_way.stars"), this.guiLeft - 25, this.guiTop + 250, infoColor);

            matrixStack.push();
            matrixStack.scale(0.8f, 0.8f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.hover_tip1"), this.guiLeft - 19, this.guiTop + 320, MathHelper.calculateRGB(0, 100, 255));
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.hover_tip2"), this.guiLeft - 19, this.guiTop + 330, MathHelper.calculateRGB(0, 100, 255));
            matrixStack.pop();
        }
        if (CURRENT_MAP == 2) {
            BACK_SOLAR_SYSTEM.visible = true;
            MERCURY.visible = true;
            VENUS.visible = true;
            EARTH.visible = true;
            MARS.visible = true;

            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, this.guiLeft - 30, this.guiTop + 180, 0, 0, 160, 160, 160, 160);

            //Sun
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/sky/sun.png"));
            this.blit(matrixStack, this.guiLeft + 304, this.guiTop + 242, 0, 0, 32, 32, 32, 32);

            //RINGS START
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring1.png"));
            this.blit(matrixStack, this.guiLeft + 145, this.guiTop + 160, 0, 0, 350, 200, 350, 200);
            if (tier3) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring1_ul.png"));
                this.blit(matrixStack, this.guiLeft + 145, this.guiTop + 160, 0, 0, 350, 200, 350, 200);
            }

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring2.png"));
            this.blit(matrixStack, this.guiLeft + 145, this.guiTop + 160, 0, 0, 350, 200, 350, 200);
            if (tier4) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring2_ul.png"));
                this.blit(matrixStack, this.guiLeft + 145, this.guiTop + 160, 0, 0, 350, 200, 350, 200);
            }

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3.png"));
            this.blit(matrixStack, this.guiLeft + 145, this.guiTop + 160, 0, 0, 350, 200, 350, 200);
            if (tier1) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3_ul.png"));
                this.blit(matrixStack, this.guiLeft + 145, this.guiTop + 160, 0, 0, 350, 200, 350, 200);
            }

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring4.png"));
            this.blit(matrixStack, this.guiLeft + 145, this.guiTop + 160, 0, 0, 350, 200, 350, 200);
            if (tier2) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring4_ul.png"));
                this.blit(matrixStack, this.guiLeft + 145, this.guiTop + 160, 0, 0, 350, 200, 350, 200);
            }
            //RINGS END


            //Mercury
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/mercury_small.png"));
            this.blit(matrixStack, this.guiLeft + 349, this.guiTop + 241, 0, 0, 16, 16, 16, 16);
            if (MERCURY.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, this.guiLeft + 311, this.guiTop + 204, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury"), this.guiLeft + 230, this.guiTop + 184, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();
            }


            //Venus
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/venus_small.png"));
            this.blit(matrixStack, this.guiLeft + 240, this.guiTop + 280, 0, 0, 16, 16, 16, 16);
            if (VENUS.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, this.guiLeft + 202, this.guiTop + 243, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus"), this.guiLeft + 146, this.guiTop + 214, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();
            }


            //Earth
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/earth_small.png"));
            this.blit(matrixStack, this.guiLeft + 433, this.guiTop + 281, 0, 0, 16, 16, 16, 16);
            if (EARTH.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, this.guiLeft + 395, this.guiTop + 244, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth"), this.guiLeft + 295, this.guiTop + 215, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();
            }


            //Mars
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/mars_small.png"));
            this.blit(matrixStack, this.guiLeft + 195, this.guiTop + 180, 0, 0, 16, 16, 16, 16);
            if (MARS.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, this.guiLeft + 157, this.guiTop + 143, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars"), this.guiLeft + 112, this.guiTop + 137, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();
            }


            //Back Button
            if (BACK_SOLAR_SYSTEM.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 25, this.guiTop + 320, 0, 0, 15, 10, 15, 10);


            matrixStack.push();
            matrixStack.scale(1.3f, 1.3f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system"), this.guiLeft - 28, this.guiTop + 169, MathHelper.calculateRGB(249, 215, 28));
            matrixStack.pop();

            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system.diameter"), this.guiLeft - 25, this.guiTop + 230, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system.age"), this.guiLeft - 25, this.guiTop + 240, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system.planets"), this.guiLeft - 25, this.guiTop + 250, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system.bodies"), this.guiLeft - 25, this.guiTop + 260, infoColor);
        }
        if (CURRENT_MAP == 2.1) {
            LAUNCH_MERCURY.visible = true;
            BACK_PLANET.visible = true;
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, this.guiLeft - 30, this.guiTop + 180, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/mercury_big.png"));
            this.blit(matrixStack, this.guiLeft + 275, this.guiTop + 213, 0, 0, 96, 96, 96, 96);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select1.png"));
            this.blit(matrixStack, this.guiLeft + 271, this.guiTop + 209, 0, 0, 104, 104, 104, 104);

            //Back Button
            if (BACK_PLANET.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 25, this.guiTop + 320, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if (tier3) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button_ul.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 2, this.guiTop + 313, 0, 0, 85, 20, 85, 20);

            matrixStack.push();
            matrixStack.scale(2f, 2f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury"), this.guiLeft - 38, this.guiTop + 136, MathHelper.calculateRGB(255, 255, 255));
            matrixStack.pop();

            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.type"), this.guiLeft - 25, this.guiTop + 230, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.diameter"), this.guiLeft - 25, this.guiTop + 240, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.age"), this.guiLeft - 25, this.guiTop + 250, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.moons"), this.guiLeft - 25, this.guiTop + 260, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.gravity"), this.guiLeft - 25, this.guiTop + 270, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.tier"), this.guiLeft - 25, this.guiTop + 280, infoColor);
        }
        if (CURRENT_MAP == 2.2) {
            LAUNCH_VENUS.visible = true;
            BACK_PLANET.visible = true;
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, this.guiLeft - 30, this.guiTop + 180, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/venus_big.png"));
            this.blit(matrixStack, this.guiLeft + 275, this.guiTop + 213, 0, 0, 96, 96, 96, 96);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select1.png"));
            this.blit(matrixStack, this.guiLeft + 271, this.guiTop + 209, 0, 0, 104, 104, 104, 104);

            //Back Button
            if (BACK_PLANET.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 25, this.guiTop + 320, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if (tier4) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button_ul.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 2, this.guiTop + 313, 0, 0, 85, 20, 85, 20);

            matrixStack.push();
            matrixStack.scale(2f, 2f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus"), this.guiLeft - 38, this.guiTop + 136, MathHelper.calculateRGB(255, 255, 255));
            matrixStack.pop();

            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.type"), this.guiLeft - 25, this.guiTop + 230, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.diameter"), this.guiLeft - 25, this.guiTop + 240, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.age"), this.guiLeft - 25, this.guiTop + 250, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.moons"), this.guiLeft - 25, this.guiTop + 260, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.gravity"), this.guiLeft - 25, this.guiTop + 270, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.tier"), this.guiLeft - 25, this.guiTop + 280, infoColor);
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
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, this.guiLeft - 30, this.guiTop + 180, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3.png"));
            this.blit(matrixStack, this.guiLeft + 155, this.guiTop + 190, 0, 0, 350, 200, 350, 200);
            if (tier1) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3_ul.png"));
                this.blit(matrixStack, this.guiLeft + 155, this.guiTop + 190, 0, 0, 350, 200, 350, 200);
            }

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/earth_big.png"));
            this.blit(matrixStack, this.guiLeft + 275, this.guiTop + 213, 0, 0, 96, 96, 96, 96);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/moon.png"));
            this.blit(matrixStack, this.guiLeft + 245, this.guiTop + 340, 0, 0, 32, 32, 32, 32);

            //Select
            if (CURRENT_MAP == 2.31) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select2.png"));
                this.blit(matrixStack, this.guiLeft + 242, this.guiTop + 337, 0, 0, 38, 38, 38, 38);
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select1.png"));
                this.blit(matrixStack, this.guiLeft + 271, this.guiTop + 209, 0, 0, 104, 104, 104, 104);
            }

            //Back Button
            if (BACK_PLANET.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 25, this.guiTop + 320, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if ((CURRENT_MAP == 2.3 && tier0) || (CURRENT_MAP == 2.31 && tier1)) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button_ul.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 2, this.guiTop + 313, 0, 0, 85, 20, 85, 20);

            if (CURRENT_MAP == 2.31) {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon"), this.guiLeft - 38, this.guiTop + 136, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.type"), this.guiLeft - 25, this.guiTop + 230, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.diameter"), this.guiLeft - 25, this.guiTop + 240, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.age"), this.guiLeft - 25, this.guiTop + 250, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.moons"), this.guiLeft - 25, this.guiTop + 260, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.gravity"), this.guiLeft - 25, this.guiTop + 270, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.tier"), this.guiLeft - 25, this.guiTop + 280, infoColor);
            } else {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth"), this.guiLeft - 38, this.guiTop + 136, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.type"), this.guiLeft - 25, this.guiTop + 230, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.diameter"), this.guiLeft - 25, this.guiTop + 240, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.age"), this.guiLeft - 25, this.guiTop + 250, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.moons"), this.guiLeft - 25, this.guiTop + 260, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.gravity"), this.guiLeft - 25, this.guiTop + 270, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.tier"), this.guiLeft - 25, this.guiTop + 280, infoColor);
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
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, this.guiLeft - 30, this.guiTop + 180, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3.png"));
            this.blit(matrixStack, this.guiLeft + 155, this.guiTop + 190, 0, 0, 350, 200, 350, 200);
            if (tier2) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3_ul.png"));
                this.blit(matrixStack, this.guiLeft + 155, this.guiTop + 190, 0, 0, 350, 200, 350, 200);
            }

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3.png"));
            this.blit(matrixStack, this.guiLeft + 130, this.guiTop + 165, 0, 0, 400, 250, 400, 250);
            if (tier2) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3_ul.png"));
                this.blit(matrixStack, this.guiLeft + 130, this.guiTop + 165, 0, 0, 400, 250, 400, 250);
            }

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/mars_big.png"));
            this.blit(matrixStack, this.guiLeft + 275, this.guiTop + 213, 0, 0, 96, 96, 96, 96);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/phobos.png"));
            this.blit(matrixStack, this.guiLeft + 245, this.guiTop + 335, 0, 0, 32, 32, 32, 32);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/deimos.png"));
            this.blit(matrixStack, this.guiLeft + 460, this.guiTop + 300, 0, 0, 32, 32, 32, 32);

            //Select
            if (CURRENT_MAP == 2.41) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select2.png"));
                this.blit(matrixStack, this.guiLeft + 242, this.guiTop + 332, 0, 0, 38, 38, 38, 38);
            } else if (CURRENT_MAP == 2.42) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select2.png"));
                this.blit(matrixStack, this.guiLeft + 457, this.guiTop + 297, 0, 0, 38, 38, 38, 38);
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select1.png"));
                this.blit(matrixStack, this.guiLeft + 271, this.guiTop + 209, 0, 0, 104, 104, 104, 104);
            }

            //Back Button
            if (BACK_PLANET.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 25, this.guiTop + 320, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if ((CURRENT_MAP == 2.4 && tier2) || (CURRENT_MAP == 2.41 && tier2) || (CURRENT_MAP == 2.42 && tier2)) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button_ul.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button.png"));
            }
            this.blit(matrixStack, this.guiLeft - 2, this.guiTop + 313, 0, 0, 85, 20, 85, 20);

            if (CURRENT_MAP == 2.41) {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos"), this.guiLeft - 38, this.guiTop + 136, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.type"), this.guiLeft - 25, this.guiTop + 230, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.diameter"), this.guiLeft - 25, this.guiTop + 240, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.age"), this.guiLeft - 25, this.guiTop + 250, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.moons"), this.guiLeft - 25, this.guiTop + 260, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.gravity"), this.guiLeft - 25, this.guiTop + 270, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.tier"), this.guiLeft - 25, this.guiTop + 280, infoColor);
            } else if (CURRENT_MAP == 2.42) {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos"), this.guiLeft - 38, this.guiTop + 136, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.type"), this.guiLeft - 25, this.guiTop + 230, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.diameter"), this.guiLeft - 25, this.guiTop + 240, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.age"), this.guiLeft - 25, this.guiTop + 250, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.moons"), this.guiLeft - 25, this.guiTop + 260, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.gravity"), this.guiLeft - 25, this.guiTop + 270, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.tier"), this.guiLeft - 25, this.guiTop + 280, infoColor);
            } else {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars"), this.guiLeft - 38, this.guiTop + 136, MathHelper.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.type"), this.guiLeft - 25, this.guiTop + 230, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.diameter"), this.guiLeft - 25, this.guiTop + 240, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.age"), this.guiLeft - 25, this.guiTop + 250, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.moons"), this.guiLeft - 25, this.guiTop + 260, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.gravity"), this.guiLeft - 25, this.guiTop + 270, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.tier"), this.guiLeft - 25, this.guiTop + 280, infoColor);
            }
        }
    }

    private Button blankMapButton(int x, int y, int width, int height, double map) {
        return addButton(new ImageButton(x, y, width, height, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), width, height, p_onPress_1_ -> CURRENT_MAP = map));
    }

    private Button launchButton(boolean tier, RegistryKey<World> dimension) {
        return addButton(new ImageButton(this.guiLeft - 2, this.guiTop + 313, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            if (tier && player.worldClient.getDimensionKey() != dimension) {
                Minecraft.getInstance().displayGuiScreen(null);
                Networking.sendToServer(new PacketTeleport(dimension.getLocation()));
            }
        }));
    }
}
