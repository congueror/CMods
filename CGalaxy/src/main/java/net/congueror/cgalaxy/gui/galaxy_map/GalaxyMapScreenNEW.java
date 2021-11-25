package net.congueror.cgalaxy.gui.galaxy_map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketChangeMap;
import net.congueror.clib.util.MathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.minecraft.util.Mth.TWO_PI;

public class GalaxyMapScreenNEW extends AbstractContainerScreen<GalaxyMapContainer> {
    Minecraft mc = Minecraft.getInstance();
    int width;
    int height;
    LocalPlayer player = mc.player;
    @Nullable
    Entity entity = player != null ? player.getVehicle() : null;
    GalaxyMapContainer container;

    boolean unlocked;
    @Nullable
    GalacticObjectBuilder.GalacticObject<?> currentObj;

    float scroll = 1.0F;

    //Current Object, Button
    List<MutablePair<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>, Button>> nullButtons = new ArrayList<>();
    List<MutablePair<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>, Button>> galaxyButtons = new ArrayList<>();
    List<MutablePair<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>, Button>> solarSystemButtons = new ArrayList<>();
    List<MutablePair<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet>, Button>> planetButtons = new ArrayList<>();
    List<MutablePair<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Moon>, Button>> moonButtons = new ArrayList<>();
    List<MutablePair<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.MoonMoon>, Button>> moonMoonButtons = new ArrayList<>();

    public GalaxyMapScreenNEW(GalaxyMapContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        this.imageWidth = 528;
        this.imageHeight = 498;
        this.container = pMenu;
        this.currentObj = pMenu.map;
        if (pMenu.unlocked) {
            this.unlocked = true;
        } else {
            this.unlocked = false;
        }
    }

    @Override
    protected void init() {
        super.init();
        nullButtons.clear();
        galaxyButtons.clear();
        solarSystemButtons.clear();
        planetButtons.clear();
        moonButtons.clear();
        moonMoonButtons.clear();

        List<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>> galaxies = GalacticObjectBuilder.galaxies();
        for (int i = 0; i < galaxies.size(); i++) {
            nullButtons.add(new MutablePair<>(null,
                    addGalaxySelButton(this.width / 2 - 100, this.height / 2 - 50 - (i * 21), 200, 20, 200, 60,
                            "textures/gui/galaxy_map/galaxy_button.png",
                            new TranslatableComponent(galaxies.get(i).getName()).withStyle(ChatFormatting.DARK_PURPLE),
                            galaxies.get(i))));

            galaxyButtons.add(new MutablePair<>(galaxies.get(i), addBackButton(this.width / 24, this.topPos + 320, null)));
        }
        List<Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>>> solarSystems = GalacticObjectBuilder.solarSystems();
        for (Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>> solarSystem : solarSystems) {
            galaxyButtons.add(new MutablePair<>(solarSystem.getValue(),
                    addMapButton(solarSystem.getKey().getX(this.width, this.leftPos), solarSystem.getKey().getY(this.height, this.topPos), 7, 7, 7, 7, "textures/gui/galaxy_map/galaxy_map_select.png", solarSystem.getKey())));

            solarSystemButtons.add(new MutablePair<>(solarSystem.getKey(),
                    addBackButton(this.width / 24, this.topPos + 320, solarSystem.getKey().getType().getGalaxy())));
        }
        List<Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>>> planets = GalacticObjectBuilder.planets();
        for (Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>> planet : planets) {
            solarSystemButtons.add(new MutablePair<>(planet.getValue(),
                    addMapButton(calculateX(planet.getKey()), calculateY(planet.getKey()), calculateSize(16), calculateSize(16), calculateSize(16), calculateSize(16), planet.getKey().getTexture().getPath(), planet.getKey())));

            planetButtons.add(new MutablePair<>(planet.getKey(),
                    addBackButton(this.width / 24, this.topPos + 320, planet.getKey().getType().getSolarSystem())));
        }
        List<Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Moon>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet>>> moons = GalacticObjectBuilder.moons();
        for (Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Moon>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet>> moon : moons) {
            planetButtons.add(new MutablePair<>(moon.getValue(),
                    addMapButton(moon.getKey().getX(width, leftPos), moon.getKey().getY(height, topPos), calculateSize(16), calculateSize(16), calculateSize(16), calculateSize(16), moon.getKey().getTexture().getPath(), moon.getKey())));

            moonButtons.add(new MutablePair<>(moon.getKey(),
                    addMapButton(calculateMidX(), calculateMidY(), calculateSize(32), calculateSize(32), calculateSize(32), calculateSize(32), moon.getValue().getTexture().getPath(), moon.getValue())));
            moonButtons.add(new MutablePair<>(moon.getKey(),
                    addBackButton(this.width / 24, this.topPos + 320, moon.getKey().getType().getPlanet().getType().getSolarSystem())));
        }
    }

    private void resetButtons() {
        for (Button button : nullButtons.stream().map(pair -> pair.right).collect(Collectors.toList())) {
            button.visible = false;
        }
        for (Button button : galaxyButtons.stream().map(pair -> pair.right).collect(Collectors.toList())) {
            button.visible = false;
        }
        for (Button button : solarSystemButtons.stream().map(pair -> pair.right).collect(Collectors.toList())) {
            button.visible = false;
        }
        for (Button button : planetButtons.stream().map(pair -> pair.right).collect(Collectors.toList())) {
            button.visible = false;
        }
        for (Button button : moonButtons.stream().map(pair -> pair.right).collect(Collectors.toList())) {
            button.visible = false;
        }
        for (Button button : moonMoonButtons.stream().map(pair -> pair.right).collect(Collectors.toList())) {
            button.visible = false;
        }
    }

    private <G extends GalacticObjectBuilder<G>> void setVisible(List<MutablePair<GalacticObjectBuilder.GalacticObject<G>, Button>> list) {
        if (list.stream().anyMatch(pair -> pair.left == null)) {
            for (Button button : list.stream().map(pair -> pair.right).collect(Collectors.toList())) {
                button.visible = true;
            }
            return;
        }
        for (Button button : list.stream().filter(pair -> pair.left.equals(currentObj)).map(pair -> pair.right).collect(Collectors.toList())) {
            button.visible = true;
        }
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY) {
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        int infoColor = MathHelper.calculateRGB(0, 150, 255);

        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        this.unlocked = container.unlocked;
        this.currentObj = container.map;
        resetButtons();

        if (this.currentObj == null) {
            setVisible(nullButtons);
            RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);
        } else {
            if (this.currentObj.getType() instanceof GalacticObjectBuilder.Galaxy) {
                setVisible(galaxyButtons);
                RenderSystem.setShaderTexture(0, this.currentObj.getTexture());
                blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);

                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
                blit(pPoseStack, this.width / 30, this.topPos + 175, 0, 0, 160, 160, 160, 160);

                pPoseStack.pushPose();
                pPoseStack.scale(1.5f, 1.5f, 0);
                drawString(pPoseStack, font, new TranslatableComponent(currentObj.getName()).withStyle(currentObj.getColor()), this.width / 30, this.topPos + 155, 0);
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.diameter").append(currentObj.getDiameter()), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.age").append(MathHelper.simplifyNumber(currentObj.getAge())), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.stars").append(MathHelper.simplifyNumber(currentObj.getStars())), this.leftPos - 25, this.topPos + 250, infoColor);

                pPoseStack.pushPose();
                pPoseStack.scale(0.8f, 0.8f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.hover_tip1"), this.leftPos - 19, this.topPos + 320, MathHelper.calculateRGB(0, 100, 255));
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.hover_tip2"), this.leftPos - 19, this.topPos + 330, MathHelper.calculateRGB(0, 100, 255));
                pPoseStack.popPose();
            }
            if (this.currentObj.getType() instanceof GalacticObjectBuilder.SolarSystem) {
                setVisible(solarSystemButtons);
                RenderSystem.setShaderTexture(0, CGalaxy.location("textures/gui/galaxy_map/galaxy_map_empty_background.png"));
                blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);

                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
                blit(pPoseStack, this.width / 30, this.topPos + 175, 0, 0, 160, 160, 160, 160);

                pPoseStack.pushPose();
                pPoseStack.scale(1.5f, 1.5f, 0);
                drawString(pPoseStack, font, new TranslatableComponent(currentObj.getName()).withStyle(currentObj.getColor()), this.width / 30, this.topPos + 155, 0);
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.diameter").append(currentObj.getDiameter()), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.age").append(MathHelper.simplifyNumber(currentObj.getAge())), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.bodies").append(currentObj.getTerrestrialObjects() + ""), this.leftPos - 25, this.topPos + 250, infoColor);

                RenderSystem.enableBlend();
                RenderSystem.disableTexture();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                Tesselator tessellator = Tesselator.getInstance();
                BufferBuilder buffer = tessellator.getBuilder();
                buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                for (int i = 0; i < GalacticObjectBuilder.planets().stream().filter(entry -> entry.getValue().equals(currentObj)).count(); i++) {
                    int radius = 40 + (49 * i);
                    drawRing(this.width / 2 + 56, this.height / 2 + 9, radius - 3, radius, buffer);
                }
                tessellator.end();
                RenderSystem.enableTexture();
                RenderSystem.disableBlend();

                RenderSystem.setShaderTexture(0, this.currentObj.getTexture());
                int size = calculateSize(32);
                blit(pPoseStack, calculateMidX(), calculateMidY(), 0, 0, size, size, size, size);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (currentObj != null && !(currentObj.getType() instanceof GalacticObjectBuilder.Galaxy)) {
            if (pDelta > 0 && this.scroll < 1.9f) {
                this.scroll += 0.1f;
                runInit();
            } else if (pDelta < 0 && this.scroll > 0.4f) {
                this.scroll -= 0.1f;
                runInit();
            }
            return true;
        }
        return false;
    }

    public void runInit() {
        this.init(mc, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
    }

    public int calculateX(GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet> obj) {
        int x = obj.getX(width, leftPos);
        int y = obj.getY(height, topPos);
        int xo = this.width / 2 + 56;
        double a1 = Math.atan(y / (float)x);
        double r1 = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double cos = Math.cos(a1);
        return (int) (cos * r1 * scroll);
    }

    public int calculateY(GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet> obj) {
        int x = obj.getX(width, leftPos);
        int y = obj.getY(height, topPos);
        int yo = this.height / 2 + 9;
        double a1 = Math.atan(y / (float)x);
        double r1 = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double sin = Math.sin(a1);
        return (int) (sin * r1 * scroll * 0.6);
    }

    public int calculateMidX() {
        return this.width / 2 + 56 - (calculateSize(32) / 2);
    }

    public int calculateMidY() {
        return this.height / 2 + 9 - (calculateSize(32) / 2);
    }

    public int calculateSize(int size) {
        return (int) (size * scroll);
    }

    private Button addBackButton(int x, int y, GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new ImageButton(x, y, 15, 10, 0, 0, 10
                , new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"), 15, 20,
                p_onPress_1_ -> changeMap(object), TextComponent.EMPTY));
    }

    private Button addMapButton(int x, int y, int width, int height, int textureWidth, int textureHeight, String texture, GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new ImageButton(x, y, width, height, 0, 0, height
                , new ResourceLocation(CGalaxy.MODID, texture), textureWidth, textureHeight,
                p_onPress_1_ -> changeMap(object), (pButton, pPoseStack, pMouseX, pMouseY) -> {
            this.renderMapTooltip(pPoseStack, pMouseX, pMouseY, object);
        }, TextComponent.EMPTY) {
            /*
            @Override
            public void renderButton(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
                pMatrixStack.pushPose();
                pMatrixStack.scale(scroll, scroll, 0);
                pMatrixStack.translate((-(this.x / scroll) + this.x) * -1.2, (-(this.y / scroll) + this.y) * -1.2, 0);
                super.renderButton(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
                pMatrixStack.popPose();
            }*/
        });
    }

    private Button addGalaxySelButton(int x, int y, int width, int height, int textureWidth, int textureHeight, String texture, Component message, GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new ImageButton(x, y, width, height, 0, 0, height
                , new ResourceLocation(CGalaxy.MODID, texture), textureWidth, textureHeight,
                p_onPress_1_ -> changeMap(object), message) {
            @Override
            public void renderButton(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
                super.renderButton(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
                drawCenteredString(pMatrixStack, mc.font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, MathHelper.calculateRGB(255, 0, 255));
            }
        });
    }

    private void changeMap(GalacticObjectBuilder.GalacticObject<?> obj) {
        CGNetwork.sendToServer(new PacketChangeMap(obj == null ? "null" : obj.getName()));
    }

    public void renderMapTooltip(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, GalacticObjectBuilder.GalacticObject<?> object) {
        if (object != null) {
            int i = mc.font.width(new TranslatableComponent(object.getName()));
            int j = 8;

            int j2 = pMouseX + 12;
            int k2 = pMouseY - 12;
            if (j2 + i > this.width) {
                j2 -= 28 + i;
            }

            if (k2 + j + 6 > this.height) {
                k2 = this.height - j - 6;
            }

            pPoseStack.pushPose();
            this.itemRenderer.blitOffset = 400.0F;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            Matrix4f matrix4f = pPoseStack.last().pose();
            int colorA = 0x800031b3;
            int colorB = 0xFF0031AF;
            int colorC = 0x800031b3;
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 4, j2 + i + 3, k2 - 3, 400, colorA, colorA);
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 + j + 3, j2 + i + 3, k2 + j + 4, 400, colorA, colorA);
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 + j + 3, 400, colorA, colorA);
            fillGradient(matrix4f, bufferbuilder, j2 - 4, k2 - 3, j2 - 3, k2 + j + 3, 400, colorA, colorA);
            fillGradient(matrix4f, bufferbuilder, j2 + i + 3, k2 - 3, j2 + i + 4, k2 + j + 3, 400, colorA, colorA);
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + j + 3 - 1, 400, colorB, colorC);
            fillGradient(matrix4f, bufferbuilder, j2 + i + 2, k2 - 3 + 1, j2 + i + 3, k2 + j + 3 - 1, 400, colorB, colorC);
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 - 3, j2 + i + 3, k2 - 3 + 1, 400, colorB, colorB);
            fillGradient(matrix4f, bufferbuilder, j2 - 3, k2 + j + 2, j2 + i + 3, k2 + j + 3, 400, colorC, colorC);
            RenderSystem.enableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            bufferbuilder.end();
            BufferUploader.end(bufferbuilder);
            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
            MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            pPoseStack.translate(0.0D, 0.0D, 400.0D);

            int l1 = k2;
            this.font.drawInBatch(new TranslatableComponent(object.getName()).withStyle(object.getColor()), j2, l1, -1, true, pPoseStack.last().pose(), multibuffersource$buffersource, false, 0, 15728880);
            RenderSystem.enableBlend();
            multibuffersource$buffersource.endBatch();
            pPoseStack.popPose();
        }
    }

    public void drawRing(int x, int y, int radiusIn, int radiusOut, BufferBuilder buffer) {
        float endAngle = (float) (0.75 * TWO_PI + Math.PI);
        float startAngle = (float) (-0.25 * TWO_PI + Math.PI);
        float angle = endAngle - startAngle;
        int sections = Math.max(1, Mth.ceil(angle / (2.5f / 360.0f)));
        float slice = angle / sections;

        int[] color = {210, 0, 0, 255};
        if (this.unlocked) {
            color = new int[]{0, 210, 0, 255};
        }

        for (int i = 0; i < sections; i++) {
            float angle1 = startAngle + i * slice;
            float angle2 = startAngle + (i + 1) * slice;
            float xCoefficient = 1.0F;
            float yCoefficient = 0.6F;

            float pos1InX = x + (radiusIn * scroll) * (float) Math.cos(angle1) * xCoefficient;
            float pos1InY = y + (radiusIn * scroll) * (float) Math.sin(angle1) * yCoefficient;
            float pos2InX = x + (radiusIn * scroll) * (float) Math.cos(angle2) * xCoefficient;
            float pos2InY = y + (radiusIn * scroll) * (float) Math.sin(angle2) * yCoefficient;

            float pos1OutX = x + (radiusOut * scroll) * (float) Math.cos(angle1) * xCoefficient;
            float pos1OutY = y + (radiusOut * scroll) * (float) Math.sin(angle1) * yCoefficient;
            float pos2OutX = x + (radiusOut * scroll) * (float) Math.cos(angle2) * xCoefficient;
            float pos2OutY = y + (radiusOut * scroll) * (float) Math.sin(angle2) * yCoefficient;

            buffer.vertex(pos1OutX, pos1OutY, 0).color(color[0], color[1], color[2], color[3]).endVertex();
            buffer.vertex(pos1InX, pos1InY, 0).color(color[0], color[1], color[2], color[3]).endVertex();
            buffer.vertex(pos2InX, pos2InY, 0).color(color[0], color[1], color[2], color[3]).endVertex();
            buffer.vertex(pos2OutX, pos2OutY, 0).color(color[0], color[1], color[2], color[3]).endVertex();
        }
    }
}
