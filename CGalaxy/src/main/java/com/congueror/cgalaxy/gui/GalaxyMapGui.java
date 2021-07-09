package com.congueror.cgalaxy.gui;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.entities.rocket_entity.RocketEntity;
import com.congueror.cgalaxy.network.Networking;
import com.congueror.cgalaxy.network.PacketTeleport;
import com.congueror.cgalaxy.world.dimension.Dimensions;
import com.congueror.clib.CLib;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Dimension;
import net.minecraft.world.World;

public class GalaxyMapGui extends Screen {
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
    private Button BACK_SL_PLANET;

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
    Entity entity = player.getRidingEntity();

    public GalaxyMapGui() {
        super(new StringTextComponent("map"));
    }

    @Override
    protected void init() {
        //x, y, width, height, uOffset, vOffset, noClue, texture, action
        this.BACK_SOLAR_SYSTEM = addButton(new ImageButton(26, 190, 15, 10, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 15, 10, p_onPress_1_ -> {
            CURRENT_MAP = 1;
        }));

        this.SOLAR_SYSTEM = addButton(new ImageButton(490, 150, 7, 7, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select.png"), 7, 7, p_onPress_1_ -> {
            CURRENT_MAP = 2;
        }));


        this.MERCURY = addButton(new ImageButton(404, 161, 16, 16, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 16, 16, p_onPress_1_ -> {
            CURRENT_MAP = 2.1;
        }));

        this.LAUNCH_MERCURY = addButton(new ImageButton(26, 160, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            if (entity instanceof RocketEntity) {
                //tp to mercury
            }
        }));


        this.VENUS = addButton(new ImageButton(295, 200, 16, 16, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 16, 16, p_onPress_1_ -> {
            CURRENT_MAP = 2.2;
        }));

        this.LAUNCH_VENUS = addButton(new ImageButton(26, 160, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            if (entity instanceof RocketEntity) {
                //tp to venus
            }
        }));


        this.EARTH = addButton(new ImageButton(488, 201, 16, 16, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 16, 16, p_onPress_1_ -> {
            CURRENT_MAP = 2.3;
        }));

        this.LAUNCH_EARTH = addButton(new ImageButton(26, 160, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            if (player.worldClient.getDimensionKey() != World.OVERWORLD) {
                Minecraft.getInstance().displayGuiScreen(null);
                Networking.sendToServer(new PacketTeleport(World.OVERWORLD.getLocation()));
            }
        }));

        this.EARTH_SEL = addButton(new ImageButton(330, 133, 96, 96, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 96, 96, p_onPress_1_ -> {
            CURRENT_MAP = 2.3;
        }));

        this.MOON_SEL = addButton(new ImageButton(300, 260, 32, 32, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 32, 32, p_onPress_1_ -> {
            CURRENT_MAP = 2.31;
        }));

        this.LAUNCH_MOON = addButton(new ImageButton(26, 160, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            if (entity instanceof RocketEntity && player.worldClient.getDimensionKey() != Dimensions.MOON) {
                Minecraft.getInstance().displayGuiScreen(null);
                Networking.sendToServer(new PacketTeleport(Dimensions.MOON.getLocation()));
            }
        }));


        this.MARS = addButton(new ImageButton(250, 100, 16, 16, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 16, 16, p_onPress_1_ -> {
            CURRENT_MAP = 2.4;
        }));

        this.MARS_SEL = addButton(new ImageButton(330, 133, 96, 96, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 96, 96, p_onPress_1_ -> {
            CURRENT_MAP = 2.4;
        }));

        this.PHOBOS_SEL = addButton(new ImageButton(300, 255, 32, 32, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 32, 32, p_onPress_1_ -> {
            CURRENT_MAP = 2.41;
        }));

        this.DEIMOS_SEL = addButton(new ImageButton(515, 220, 32, 32, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 32, 32, p_onPress_1_ -> {
            CURRENT_MAP = 2.42;
        }));

        this.LAUNCH_MARS = addButton(new ImageButton(26, 160, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            //tp to mars
        }));

        this.LAUNCH_PHOBOS = addButton(new ImageButton(26, 160, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            //tp to mars
        }));

        this.LAUNCH_DEIMOS = addButton(new ImageButton(26, 160, 85, 20, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 85, 20, p_onPress_1_ -> {
            //tp to mars
        }));


        this.BACK_SL_PLANET = addButton(new ImageButton(26, 190, 15, 10, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), 15, 10, p_onPress_1_ -> {
            CURRENT_MAP = 2;
        }));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int width = mc.getMainWindow().getScaledWidth();
        int height = mc.getMainWindow().getScaledHeight();
        int infoColor = CLib.calculateRGB(0, 150, 255);

        SOLAR_SYSTEM.visible = false;

        BACK_SOLAR_SYSTEM.visible = false;
        BACK_SL_PLANET.visible = false;
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
            this.blit(matrixStack, 20, 50, 0, 0, 160, 160, 160, 160);

            if (SOLAR_SYSTEM.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, 448, 113, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system"), 348, 96, CLib.calculateRGB(249, 215, 28));
                matrixStack.pop();
            }

            //matrixStack, this.font, text, x, y, color
            matrixStack.push();
            matrixStack.scale(2f, 2f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.milky_way"), 15, 31, CLib.calculateRGB(255, 0, 255)/*16711935*/);
            matrixStack.pop();

            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.milky_way.diameter"), 26, 99, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.milky_way.age"), 26, 109, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.milky_way.stars"), 26, 119, infoColor);

            matrixStack.push();
            matrixStack.scale(0.9f, 0.9f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.hover_tip1"), 26, 160, CLib.calculateRGB(0, 100, 255));
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.hover_tip2"), 26, 170, CLib.calculateRGB(0, 100, 255));
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
            this.blit(matrixStack, 20, 50, 0, 0, 160, 160, 160, 160);


            //Sun
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/sky/sun.png"));
            this.blit(matrixStack, 359, 163, 0, 0, 32, 32, 32, 32);


            //RINGS START
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring1.png"));
            this.blit(matrixStack, 200, 80, 0, 0, 350, 200, 350, 200);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring2.png"));
            this.blit(matrixStack, 200, 80, 0, 0, 350, 200, 350, 200);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3.png"));
            this.blit(matrixStack, 200, 80, 0, 0, 350, 200, 350, 200);
            if (entity instanceof RocketEntity) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3_ul.png"));
                this.blit(matrixStack, 200, 80, 0, 0, 350, 200, 350, 200);
            }

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring4.png"));
            this.blit(matrixStack, 200, 80, 0, 0, 350, 200, 350, 200);
            //RINGS END


            //Mercury
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/mercury_small.png"));
            this.blit(matrixStack, 404, 161, 0, 0, 16, 16, 16, 16);
            if (MERCURY.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, 366, 124, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury"), 285, 104, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();
            }


            //Venus
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/venus_small.png"));
            this.blit(matrixStack, 295, 200, 0, 0, 16, 16, 16, 16);
            if (VENUS.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, 257, 163, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus"), 201, 134, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();
            }


            //Earth
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/earth_small.png"));
            this.blit(matrixStack, 488, 201, 0, 0, 16, 16, 16, 16);
            if (EARTH.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, 450, 164, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth"), 350, 135, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();
            }


            //Mars
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/mars_small.png"));
            this.blit(matrixStack, 250, 100, 0, 0, 16, 16, 16, 16);
            if (MARS.isHovered()) {
                RenderSystem.color4f(1, 1, 1, 1);
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_title.png"));
                this.blit(matrixStack, 212, 63, 0, 0, 96, 35, 96, 35);
                matrixStack.push();
                matrixStack.scale(1.3f, 1.3f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars"), 167, 57, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();
            }


            //Back Button
            if (!BACK_SOLAR_SYSTEM.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            }
            this.blit(matrixStack, 26, 190, 0, 0, 15, 10, 15, 10);


            matrixStack.push();
            matrixStack.scale(1.4f, 1.4f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system"), 21, 50, CLib.calculateRGB(249, 215, 28));
            matrixStack.pop();

            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system.diameter"), 26, 99, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system.age"), 26, 109, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system.planets"), 26, 119, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.solar_system.bodies"), 26, 129, infoColor);

        }
        if (CURRENT_MAP == 2.1) {
            LAUNCH_MERCURY.visible = true;
            BACK_SL_PLANET.visible = true;
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, 20, 50, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/mercury_big.png"));
            this.blit(matrixStack, 330, 133, 0, 0, 96, 96, 96, 96);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select1.png"));
            this.blit(matrixStack, 326, 129, 0, 0, 104, 104, 104, 104);

            //Back Button
            if (!BACK_SL_PLANET.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            }
            this.blit(matrixStack, 26, 190, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if (entity instanceof RocketEntity /*TODO tier3*/) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button_ul.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button.png"));
            }
            this.blit(matrixStack, 26, 160, 0, 0, 85, 20, 85, 20);

            matrixStack.push();
            matrixStack.scale(2f, 2f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury"), 17, 32, CLib.calculateRGB(255, 255, 255));
            matrixStack.pop();

            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.type"), 26, 99, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.diameter"), 26, 109, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.age"), 26, 119, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.moons"), 26, 129, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.gravity"), 26, 139, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mercury.tier"), 26, 149, infoColor);
        }
        if (CURRENT_MAP == 2.2) {
            LAUNCH_VENUS.visible = true;
            BACK_SL_PLANET.visible = true;
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, 20, 50, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/venus_big.png"));
            this.blit(matrixStack, 330, 133, 0, 0, 96, 96, 96, 96);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select1.png"));
            this.blit(matrixStack, 326, 129, 0, 0, 104, 104, 104, 104);

            //Back Button
            if (!BACK_SL_PLANET.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            }
            this.blit(matrixStack, 26, 190, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if (entity instanceof RocketEntity /*TODO tier4*/) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button_ul.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button.png"));
            }
            this.blit(matrixStack, 26, 160, 0, 0, 85, 20, 85, 20);

            matrixStack.push();
            matrixStack.scale(2f, 2f, 0);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus"), 17, 32, CLib.calculateRGB(255, 255, 255));
            matrixStack.pop();

            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.type"), 26, 99, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.diameter"), 26, 109, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.age"), 26, 119, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.moons"), 26, 129, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.gravity"), 26, 139, infoColor);
            drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.venus.tier"), 26, 149, infoColor);
        }
        if (CURRENT_MAP >= 2.3 && CURRENT_MAP < 2.4) {
            if (CURRENT_MAP == 2.31) {
                LAUNCH_MOON.visible = true;
            } else {
                LAUNCH_EARTH.visible = true;
            }
            BACK_SL_PLANET.visible = true;
            EARTH_SEL.visible = true;
            MOON_SEL.visible = true;
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, 20, 50, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3.png"));
            this.blit(matrixStack, 210, 110, 0, 0, 350, 200, 350, 200);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/earth_big.png"));
            this.blit(matrixStack, 330, 133, 0, 0, 96, 96, 96, 96);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/moon.png"));
            this.blit(matrixStack, 300, 260, 0, 0, 32, 32, 32, 32);

            //Select
            if (CURRENT_MAP == 2.31) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select2.png"));
                this.blit(matrixStack, 297, 257, 0, 0, 38, 38, 38, 38);
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select1.png"));
                this.blit(matrixStack, 326, 129, 0, 0, 104, 104, 104, 104);
            }

            //Back Button
            if (!BACK_SL_PLANET.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            }
            this.blit(matrixStack, 26, 190, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if (LAUNCH_EARTH.visible || entity instanceof RocketEntity /*TODO tier1*/) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button_ul.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button.png"));
            }
            this.blit(matrixStack, 26, 160, 0, 0, 85, 20, 85, 20);

            if (CURRENT_MAP == 2.31) {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon"), 17, 32, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.type"), 26, 99, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.diameter"), 26, 109, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.age"), 26, 119, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.moons"), 26, 129, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.gravity"), 26, 139, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.moon.tier"), 26, 149, infoColor);
            } else {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth"), 17, 32, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.type"), 26, 99, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.diameter"), 26, 109, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.age"), 26, 119, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.moons"), 26, 129, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.gravity"), 26, 139, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.earth.tier"), 26, 149, infoColor);
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
            BACK_SL_PLANET.visible = true;
            MARS_SEL.visible = true;
            PHOBOS_SEL.visible = true;
            DEIMOS_SEL.visible = true;
            RenderSystem.color4f(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            this.blit(matrixStack, 0, 0, 0, 0, width, height, width, height);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
            this.blit(matrixStack, 20, 50, 0, 0, 160, 160, 160, 160);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3.png"));
            this.blit(matrixStack, 210, 110, 0, 0, 350, 200, 350, 200);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/rings/galaxy_map_ring3.png"));
            this.blit(matrixStack, 185, 85, 0, 0, 400, 250, 400, 250);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/mars_big.png"));
            this.blit(matrixStack, 330, 133, 0, 0, 96, 96, 96, 96);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/phobos.png"));
            this.blit(matrixStack, 300, 255, 0, 0, 32, 32, 32, 32);

            mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/deimos.png"));
            this.blit(matrixStack, 515, 220, 0, 0, 32, 32, 32, 32);

            //Select
            if (CURRENT_MAP == 2.41) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select2.png"));
                this.blit(matrixStack, 297, 252, 0, 0, 38, 38, 38, 38);
            } else if (CURRENT_MAP == 2.42) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select2.png"));
                this.blit(matrixStack, 512, 217, 0, 0, 38, 38, 38, 38);
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_select1.png"));
                this.blit(matrixStack, 326, 129, 0, 0, 104, 104, 104, 104);
            }

            //Back Button
            if (!BACK_SL_PLANET.isHovered()) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button_selected.png"));
            }
            this.blit(matrixStack, 26, 190, 0, 0, 15, 10, 15, 10);

            //Launch Button
            if (entity instanceof RocketEntity /*TODO tier3*/) {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button_ul.png"));
            } else {
                mc.getTextureManager().bindTexture(new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/launch_button.png"));
            }
            this.blit(matrixStack, 26, 160, 0, 0, 85, 20, 85, 20);

            if (CURRENT_MAP == 2.41) {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos"), 17, 32, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.type"), 26, 99, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.diameter"), 26, 109, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.age"), 26, 119, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.moons"), 26, 129, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.gravity"), 26, 139, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.phobos.tier"), 26, 149, infoColor);
            } else if (CURRENT_MAP == 2.42) {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos"), 17, 32, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.type"), 26, 99, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.diameter"), 26, 109, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.age"), 26, 119, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.moons"), 26, 129, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.gravity"), 26, 139, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.deimos.tier"), 26, 149, infoColor);
            } else {
                matrixStack.push();
                matrixStack.scale(2f, 2f, 0);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars"), 17, 32, CLib.calculateRGB(255, 255, 255));
                matrixStack.pop();

                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.type"), 26, 99, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.diameter"), 26, 109, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.age"), 26, 119, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.moons"), 26, 129, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.gravity"), 26, 139, infoColor);
                drawString(matrixStack, this.font, new TranslationTextComponent("gui.cgalaxy.mars.tier"), 26, 149, infoColor);
            }
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public static void open() {
        CURRENT_MAP = 1;
        Minecraft.getInstance().displayGuiScreen(new GalaxyMapGui());
    }
}
