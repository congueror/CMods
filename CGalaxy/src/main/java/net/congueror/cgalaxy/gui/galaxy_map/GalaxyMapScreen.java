package net.congueror.cgalaxy.gui.galaxy_map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreContainer;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketChangeMap;
import net.congueror.cgalaxy.networking.PacketTeleport;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GalaxyMapScreen extends AbstractContainerScreen<GalaxyMapContainer> {
    final Inventory inv;
    Minecraft mc = Minecraft.getInstance();
    int width;
    int height;
    LocalPlayer player = mc.player;
    GalaxyMapContainer container;

    boolean unlocked;
    @Nullable
    GalacticObjectBuilder.GalacticObject<?> currentObj;
    SpaceStationCoreContainer.SpaceStationCoreObject selectedSpaceStation;

    float zoom = 1.0F;
    int moveX = 0;
    int moveY = 0;
    boolean spaceStationScreenOpen;

    public static final int startRadius = 55;
    private int xSel, ySel, sizeSel;

    //Current Object, Button
    List<MutablePair<String, Button>> nullButtons = new ArrayList<>();
    List<MutablePair<String, Button>> galaxyButtons = new ArrayList<>();
    List<MutablePair<String, Button>> solarSystemButtons = new ArrayList<>();
    List<MutablePair<String, Button>> planetButtons = new ArrayList<>();
    List<MutablePair<String, Button>> moonButtons = new ArrayList<>();
    List<MutablePair<String, Button>> moonMoonButtons = new ArrayList<>();

    public GalaxyMapScreen(GalaxyMapContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.inv = pPlayerInventory;
        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        this.imageWidth = 528;
        this.imageHeight = 498;
        this.container = pMenu;
        this.currentObj = pMenu.map;
        this.unlocked = pMenu.unlocked;
    }

    @Override
    protected void init() {
        //The currentObj is unavailable here, so we have to register all buttons.
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
                            new TranslatableComponent(galaxies.get(i).getTranslationKey()).withStyle(galaxies.get(i).getColor()),
                            galaxies.get(i))));

            galaxyButtons.add(new MutablePair<>(galaxies.get(i).getId().toString(), addBackButton(null)));
        }
        List<Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>>> solarSystems = GalacticObjectBuilder.solarSystems();
        for (var solarSystem : solarSystems) {
            galaxyButtons.add(new MutablePair<>(solarSystem.getValue().getId().toString(),
                    addMapButton(solarSystem.getKey().getSolarSystemX(this.width, this.leftPos), solarSystem.getKey().getSolarSystemY(this.height, this.topPos), 7, 7, 7, 7, "textures/gui/galaxy_map/galaxy_map_select.png", solarSystem.getKey())));

            solarSystemButtons.add(new MutablePair<>(solarSystem.getKey().getId().toString(),
                    addBackButton(solarSystem.getKey().getType().getGalaxy())));
        }

        List<List<ResourceLocation>> moonList = new ArrayList<>();

        List<Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>>> planets = GalacticObjectBuilder.planets();
        for (var planet : planets) {
            solarSystemButtons.add(new MutablePair<>(planet.getValue().getId().toString(),
                    addButton(GalacticMapButton.Type.ORBITER, calculateX(planet.getKey()), calculateY(planet.getKey()), calculateSize(16), calculateSize(16), calculateSize(16), calculateSize(16), planet.getKey().getTexture().getPath(), planet.getKey())));

            planetButtons.add(new MutablePair<>(planet.getKey().getId().toString(),
                    addBackButton(planet.getKey().getType().getSolarSystem())));
            planetButtons.add(new MutablePair<>(planet.getKey().getId().toString(),
                    addButton(GalacticMapButton.Type.ORBITEE, calculateMidX(), calculateMidY(), calculateSize(32), calculateSize(32), calculateSize(32), calculateSize(32), planet.getKey().getTexture().getPath(), planet.getKey())));
            planetButtons.add(new MutablePair<>(planet.getKey().getId().toString(),
                    addLaunchButton(planet.getKey())));
            planetButtons.add(new MutablePair<>(planet.getKey().getId().toString(),
                    addSpaceStationButton()));

            List<ResourceLocation> tempList = new ArrayList<>();
            GalacticObjectBuilder.moons().stream()
                    .filter(e -> e.getKey().getType().getPlanet().getId().equals(planet.getKey().getId()))
                    .map(Map.Entry::getKey)
                    .forEach(o -> tempList.add(o.getId()));
            moonList.add(tempList);
        }
        List<Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Moon>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Planet>>> moons = GalacticObjectBuilder.moons();
        for (var moon : moons) {
            planetButtons.add(new MutablePair<>(moon.getValue().getId().toString(),
                    addButton(GalacticMapButton.Type.ORBITER, calculateX(moon.getKey()), calculateY(moon.getKey()), calculateSize(16), calculateSize(16), calculateSize(16), calculateSize(16), moon.getKey().getTexture().getPath(), moon.getKey())));

            for (List<ResourceLocation> strings : moonList) {
                if (strings.contains(moon.getKey().getId())) {
                    for (ResourceLocation string : strings) {
                        GalacticObjectBuilder.GalacticObject<?> obj = GalacticObjectBuilder.getObjectFromId(string);
                        var pair = new MutablePair<>(moon.getKey().getId().toString(),
                                addButton(GalacticMapButton.Type.ORBITER, calculateX(obj), calculateY(obj), calculateSize(16), calculateSize(16), calculateSize(16), calculateSize(16), obj.getTexture().getPath(), obj));
                        if (!moonButtons.contains(pair)) {
                            moonButtons.add(pair);
                        }
                    }
                }
            }
            moonButtons.add(new MutablePair<>(moon.getKey().getId().toString(),
                    addLaunchButton(moon.getKey())));
            moonButtons.add(new MutablePair<>(moon.getKey().getId().toString(),
                    addButton(GalacticMapButton.Type.ORBITEE, calculateMidX(), calculateMidY(), calculateSize(32), calculateSize(32), calculateSize(32), calculateSize(32), moon.getValue().getTexture().getPath(), moon.getValue())));
            moonButtons.add(new MutablePair<>(moon.getKey().getId().toString(),
                    addBackButton(moon.getKey().getType().getPlanet().getType().getSolarSystem())));
            moonButtons.add(new MutablePair<>(moon.getKey().getId().toString(),
                    addSpaceStationButton()));
        }
    }

    private void resetButtons() {
        for (Button button : nullButtons.stream().map(pair -> pair.right).toList()) {
            button.visible = false;
        }
        for (Button button : galaxyButtons.stream().map(pair -> pair.right).toList()) {
            button.visible = false;
        }
        for (Button button : solarSystemButtons.stream().map(pair -> pair.right).toList()) {
            button.visible = false;
        }
        for (Button button : planetButtons.stream().map(pair -> pair.right).toList()) {
            button.visible = false;
        }
        for (Button button : moonButtons.stream().map(pair -> pair.right).toList()) {
            button.visible = false;
        }
        for (Button button : moonMoonButtons.stream().map(pair -> pair.right).toList()) {
            button.visible = false;
        }
    }

    private void updateButtons(List<MutablePair<String, Button>> list) {
        if (list.stream().anyMatch(pair -> pair.left == null)) {
            for (Button button : list.stream().map(pair -> pair.right).toList()) {
                button.visible = true;
            }
            return;
        }
        for (MutablePair<String, Button> pair : list.stream().filter(pair -> {
            assert currentObj != null;
            return pair.left.equals(currentObj.getId().toString());
        }).toList()) {
            pair.right.visible = true;
            if (pair.right instanceof GalacticMapButton button) {
                var obj = button.getObject();
                if (button.getType().equals(GalacticMapButton.Type.ORBITER)) {
                    button.updateOrbiter(obj, width, height, calculateSize(16), zoom, moveX, moveY, (pButton, pPoseStack, pMouseX, pMouseY) -> this.renderMapTooltip(pPoseStack, pMouseX, pMouseY, obj));
                    if (button.getObject().equals(this.currentObj)) {
                        this.xSel = button.xSel;
                        this.ySel = button.ySel;
                        this.sizeSel = button.sizeSel;
                    }
                } else if (button.getType().equals(GalacticMapButton.Type.LAUNCH)) {
                    int yOffset = 0;
                    Component message = TextComponent.EMPTY;
                    if (spaceStationScreenOpen) {
                        if (selectedSpaceStation == null) {
                            if (inv.contains(new ItemStack(Items.GLOW_ITEM_FRAME))) {//TODO: Placeholder
                                yOffset = 60;
                            } else {
                                yOffset = 40;
                                message = new TranslatableComponent("key.cgalaxy.map_no_material").withStyle(ChatFormatting.RED);
                            }
                            button.setOnPress(pButton -> {
                                //TODO: Space Station Creation
                            });
                        } else {
                            String l = canLaunch(obj, true);
                            if (l.contains("nope")) {
                                int i = l.indexOf(" ");
                                message = new TranslatableComponent(l.substring(i)).withStyle(ChatFormatting.RED);
                            } else if (l.contains("yep")) {
                                yOffset = 20;
                            }
                            button.setOnPress(pButton -> {
                                if (canLaunch(obj, true).contains("yep")) {
                                    Minecraft.getInstance().setScreen(null);
                                    CGNetwork.sendToServer(new PacketTeleport(obj.getOrbitDimension().location(), selectedSpaceStation.getPosition(), true));
                                }
                            });
                        }
                    } else {
                        String l = canLaunch(obj, false);
                        if (l.contains("nope")) {
                            int i = l.indexOf(" ");
                            message = new TranslatableComponent(l.substring(i)).withStyle(ChatFormatting.RED);
                        } else if (l.contains("yep")) {
                            yOffset = 20;
                        }
                        button.setOnPress(pButton -> onLaunchButtonPress(obj));
                    }
                    Component finalMessage = message;
                    button.setYTexStart(yOffset);
                    button.setOnTooltip((pButton, pPoseStack, pMouseX, pMouseY) -> this.renderErrorTooltip(pPoseStack, pMouseX, pMouseY, finalMessage));
                } else if (button.getType().equals(GalacticMapButton.Type.ORBITEE)) {
                    button.setPosition(calculateMidX(), calculateMidY());
                    button.setWidth(calculateSize(32));
                    button.setHeight(calculateSize(32));
                    button.setTextureWidth(calculateSize(32));
                    button.setTextureHeight(calculateSize(32));
                    button.setOnTooltip((pButton, pPoseStack, pMouseX, pMouseY) -> this.renderMapTooltip(pPoseStack, pMouseX, pMouseY, obj));
                } else if (button.getType().equals(GalacticMapButton.Type.SPACE_STATION)) {
                    if (spaceStationScreenOpen) {
                        button.setXTexStart(141);
                        button.setPosition(button.x, this.topPos + 187 + 122);
                        button.setOnPress(pButton -> {
                            spaceStationScreenOpen = false;
                            selectedSpaceStation = null;
                        });
                    } else {
                        button.setXTexStart(129);
                        button.setPosition(button.x, this.topPos + 187);
                        button.setOnPress(pButton -> onSpaceStationButtonPress());
                    }
                }
            }
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
        int infoColor = MathHelper.RGBtoDecimalRGB(0, 150, 255);

        this.width = mc.getWindow().getGuiScaledWidth();
        this.height = mc.getWindow().getGuiScaledHeight();
        if (this.unlocked != container.unlocked) {
            this.unlocked = container.unlocked;
        }
        this.currentObj = container.map;
        resetButtons();

        if (this.currentObj == null) {
            updateButtons(nullButtons);
            RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);
        } else {
            if (this.currentObj.getType() instanceof GalacticObjectBuilder.Galaxy) {
                updateButtons(galaxyButtons);
                RenderSystem.setShaderTexture(0, this.currentObj.getTexture());
                blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);

                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
                blit(pPoseStack, this.width / 30, this.topPos + 175, 0, 0, 129, 160, 197, 160);

                pPoseStack.pushPose();
                pPoseStack.scale(1.5f, 1.5f, 0);
                drawString(pPoseStack, font, new TranslatableComponent(currentObj.getTranslationKey()).withStyle(currentObj.getColor()), this.width / 30, this.topPos + 155, 0);
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.diameter").append(MathHelper.simplifyNumber(currentObj.getDiameter().left) + currentObj.getDiameter().right), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.age").append(MathHelper.simplifyNumber(currentObj.getAge())), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.stars").append(MathHelper.simplifyNumber(currentObj.getStars())), this.leftPos - 25, this.topPos + 250, infoColor);

                pPoseStack.pushPose();
                pPoseStack.scale(0.8f, 0.8f, 0);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.hover_tip1"), this.leftPos - 19, this.topPos + 320, MathHelper.RGBtoDecimalRGB(0, 100, 255));
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.hover_tip2"), this.leftPos - 19, this.topPos + 330, MathHelper.RGBtoDecimalRGB(0, 100, 255));
                pPoseStack.popPose();
            }
            if (this.currentObj.getType() instanceof GalacticObjectBuilder.SolarSystem) {
                updateButtons(solarSystemButtons);
                RenderSystem.setShaderTexture(0, CGalaxy.location("textures/gui/galaxy_map/galaxy_map_empty_background.png"));
                blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);

                RenderSystem.enableBlend();
                RenderSystem.disableTexture();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                Tesselator tessellator = Tesselator.getInstance();
                BufferBuilder buffer = tessellator.getBuilder();
                buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                var list = GalacticObjectBuilder.planets().stream().filter(entry -> entry.getValue().equals(currentObj)).map(Map.Entry::getKey).sorted(Comparator.comparingInt(GalacticObjectBuilder.GalacticObject::getRingIndex)).collect(Collectors.toList());
                for (int i = 0; i < list.size(); i++) {
                    GalacticObjectBuilder.GalacticObject<?> obj = list.get(i);
                    int radius = startRadius + (49 * i);
                    drawRing(this.width / 2 + 56 + moveX, this.height / 2 + 9 + moveY, radius - 3, radius, buffer, obj);
                }
                tessellator.end();
                RenderSystem.enableTexture();
                RenderSystem.enableBlend();

                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
                blit(pPoseStack, this.width / 30, this.topPos + 175, 0, 0, 129, 160, 197, 160);

                pPoseStack.pushPose();
                pPoseStack.scale(1.5f, 1.5f, 0);
                drawString(pPoseStack, font, new TranslatableComponent(currentObj.getTranslationKey()).withStyle(currentObj.getColor()), this.width / 30, this.topPos + 155, 0);
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.diameter").append(MathHelper.simplifyNumber(currentObj.getDiameter().left) + currentObj.getDiameter().right), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.age").append(MathHelper.simplifyNumber(currentObj.getAge())), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.bodies").append(currentObj.getTerrestrialObjects() + ""), this.leftPos - 25, this.topPos + 250, infoColor);

                RenderSystem.setShaderTexture(0, this.currentObj.getTexture());
                int size = calculateSize(32);
                blit(pPoseStack, calculateMidX(), calculateMidY(), 0, 0, size, size, size, size);
            }
            if (this.currentObj.getType() instanceof GalacticObjectBuilder.Planet || this.currentObj.getType() instanceof GalacticObjectBuilder.Moon) {
                if (this.currentObj.getType() instanceof GalacticObjectBuilder.Planet) {
                    updateButtons(planetButtons);
                } else {
                    updateButtons(moonButtons);
                }
                RenderSystem.setShaderTexture(0, CGalaxy.location("textures/gui/galaxy_map/galaxy_map_empty_background.png"));
                blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);

                RenderSystem.enableBlend();
                RenderSystem.disableTexture();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                Tesselator tessellator = Tesselator.getInstance();
                BufferBuilder buffer = tessellator.getBuilder();
                buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                if (this.currentObj.getType() instanceof GalacticObjectBuilder.Planet) {
                    var list = GalacticObjectBuilder.moons().stream().filter(entry -> entry.getValue().equals(currentObj)).map(Map.Entry::getKey).sorted(Comparator.comparingInt(GalacticObjectBuilder.GalacticObject::getRingIndex)).collect(Collectors.toList());
                    for (int i = 0; i < list.size(); i++) {
                        GalacticObjectBuilder.GalacticObject<?> obj = list.get(i);
                        int radius = startRadius + (49 * i);
                        drawRing(this.width / 2 + 56 + moveX, this.height / 2 + 9 + moveY, radius - 3, radius, buffer, obj);
                    }
                } else if (this.currentObj.getType() instanceof GalacticObjectBuilder.Moon moon) {
                    var list = GalacticObjectBuilder.moons().stream().filter(entry -> entry.getValue().equals(moon.getPlanet())).map(Map.Entry::getKey).sorted(Comparator.comparingInt(GalacticObjectBuilder.GalacticObject::getRingIndex)).collect(Collectors.toList());
                    for (int i = 0; i < list.size(); i++) {
                        GalacticObjectBuilder.GalacticObject<?> obj = list.get(i);
                        int radius = startRadius + (49 * i);
                        drawRing(this.width / 2 + 56 + moveX, this.height / 2 + 9 + moveY, radius - 3, radius, buffer, obj);
                    }
                }
                tessellator.end();
                RenderSystem.enableTexture();
                RenderSystem.enableBlend();

                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
                blit(pPoseStack, this.width / 30, this.topPos + 175, 0, 0, 129, 160, 197, 160);
                if (spaceStationScreenOpen) {
                    RenderSystem.setShaderTexture(0, CGalaxy.location("textures/gui/galaxy_map/galaxy_map_info.png"));
                    blit(pPoseStack, this.width / 30 + 129, this.topPos + 187, 129, 15, 68, 122, 197, 160);

                    if (selectedSpaceStation != null) {
                        RenderSystem.setShaderTexture(0, player.connection.getPlayerInfo(selectedSpaceStation.getOwner()).getSkinLocation());
                        blit(pPoseStack, this.leftPos + 113, this.topPos + 202, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
                        drawString(pPoseStack, this.font, player.connection.getPlayerInfo(selectedSpaceStation.getOwner()).getProfile().getName(), this.leftPos + 98, this.topPos + 240, infoColor);
                        drawString(pPoseStack, this.font, "x: " + selectedSpaceStation.getPosition().getX() + ", z: " + selectedSpaceStation.getPosition().getZ(), this.leftPos + 98, this.topPos + 250, infoColor);
                    }
                }

                pPoseStack.pushPose();
                pPoseStack.scale(1.5f, 1.5f, 0);
                drawString(pPoseStack, font, new TranslatableComponent(currentObj.getTranslationKey()).withStyle(currentObj.getColor()), this.width / 30, this.topPos + 155, 0);
                pPoseStack.popPose();

                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.diameter").append(MathHelper.simplifyNumber(currentObj.getDiameter().left) + currentObj.getDiameter().right), this.leftPos - 25, this.topPos + 230, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.age").append(MathHelper.simplifyNumber(currentObj.getAge())), this.leftPos - 25, this.topPos + 240, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.atmosphere").append(currentObj.getAtmosphere()), this.leftPos - 25, this.topPos + 250, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.gravity").append(new DecimalFormat("#.##").format(currentObj.getGravity()) + "m/s\u00b2"), this.leftPos - 25, this.topPos + 260, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.moons").append(currentObj.getMoons() + ""), this.leftPos - 25, this.topPos + 270, infoColor);
                drawString(pPoseStack, this.font, new TranslatableComponent("gui.cgalaxy.tier").append(currentObj.getTier() + ""), this.leftPos - 25, this.topPos + 280, infoColor);

                if (this.currentObj.getType() instanceof GalacticObjectBuilder.Planet) {
                    RenderSystem.setShaderTexture(0, CGalaxy.location("textures/gui/galaxy_map/galaxy_map_select1.png"));
                    int size = calculateSize(37);
                    blit(pPoseStack, this.width / 2 + 56 - (size / 2) + moveX, this.height / 2 + 9 - (size / 2) + moveY, 0, 0, size, size, size, size);
                } else {
                    RenderSystem.setShaderTexture(0, CGalaxy.location("textures/gui/galaxy_map/galaxy_map_select1.png"));
                    blit(pPoseStack, this.xSel, this.ySel, 0, 0, this.sizeSel, this.sizeSel, this.sizeSel, this.sizeSel);
                }
            }
        }
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (this.currentObj != null && (this.currentObj.getType() instanceof GalacticObjectBuilder.SolarSystem || this.currentObj.getType() instanceof GalacticObjectBuilder.Planet || this.currentObj.getType() instanceof GalacticObjectBuilder.Moon)) {
            moveX += pDragX;
            moveY += pDragY;
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (currentObj != null && !(currentObj.getType() instanceof GalacticObjectBuilder.Galaxy)) {
            if (pDelta > 0 && this.zoom < 1.9f) {
                this.zoom += 0.1f;
            } else if (pDelta < 0 && this.zoom > 0.4f) {
                this.zoom -= 0.1f;
            }
            return true;
        }
        return false;
    }

    public int calculateX(GalacticObjectBuilder.GalacticObject<?> obj) {
        float angle = obj.getAngle();
        double r1 = startRadius + (49 * obj.getRingIndex());
        return (int) ((this.width / 2 + 56) + zoom * r1 * Math.cos(angle) - (calculateSize(16) / 2) + moveX);
    }

    public int calculateY(GalacticObjectBuilder.GalacticObject<?> obj) {
        float angle = obj.getAngle();
        double r1 = 0.6 * (startRadius + (49 * obj.getRingIndex()));
        return (int) ((this.height / 2 + 9) + zoom * r1 * Math.sin(angle) - (calculateSize(16) / 2) + moveY);
    }

    public int calculateMidX() {
        return this.width / 2 + 56 - (calculateSize(32) / 2) + moveX;
    }

    public int calculateMidY() {
        return this.height / 2 + 9 - (calculateSize(32) / 2) + moveY;
    }

    public int calculateSize(int size) {
        return (int) (size * zoom);
    }

    private Button addSpaceStationButton() {
        return addRenderableWidget(new GalacticMapButton(GalacticMapButton.Type.SPACE_STATION, this.width / 30 + 129, this.topPos + 187, 12, 15, 129, 0, 0,
                CGalaxy.location("textures/gui/galaxy_map/galaxy_map_info.png"), 197, 160,
                onPress -> onSpaceStationButtonPress(), currentObj));
    }

    private void onSpaceStationButtonPress() {
        spaceStationScreenOpen = true;
        this.minecraft.pushGuiLayer(new SpaceStationSelectScreen(player.getUUID(), currentObj.getOrbitDimension()));
    }

    private Button addBackButton(GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new ImageButton(this.width / 24, this.topPos + 320, 15, 10, 0, 0, 10
                , new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/back_button.png"), 15, 20,
                p_onPress_1_ -> changeMap(object), TextComponent.EMPTY));
    }

    private Button addMapButton(int x, int y, int width, int height, int textureWidth, int textureHeight, String texture, GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new ImageButton(x, y, width, height, 0, 0, height
                , new ResourceLocation(CGalaxy.MODID, texture), textureWidth, textureHeight,
                p_onPress_1_ -> changeMap(object), (pButton, pPoseStack, pMouseX, pMouseY) -> {
            this.renderMapTooltip(pPoseStack, pMouseX, pMouseY, object);
        }, TextComponent.EMPTY));
    }

    private Button addGalaxySelButton(int x, int y, int width, int height, int textureWidth, int textureHeight, String texture, Component message, GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new ImageButton(x, y, width, height, 0, 0, height
                , new ResourceLocation(CGalaxy.MODID, texture), textureWidth, textureHeight,
                p_onPress_1_ -> changeMap(object), message) {
            @Override
            public void renderButton(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
                super.renderButton(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
                drawCenteredString(pMatrixStack, mc.font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, MathHelper.RGBtoDecimalRGB(255, 0, 255));
            }
        });
    }

    private Button addButton(GalacticMapButton.Type type, int x, int y, int width, int height, int textureWidth, int textureHeight, String texture, GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new GalacticMapButton(type, x, y, width, height, 0, 0, 0
                , new ResourceLocation(CGalaxy.MODID, texture), textureWidth, textureHeight,
                p_onPress_1_ -> changeMap(object), object));
    }

    private Button addLaunchButton(GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new GalacticMapButton(GalacticMapButton.Type.LAUNCH, this.leftPos - 2, this.topPos + 311, 85, 20,
                0, 0, 0,
                CGalaxy.location("textures/gui/galaxy_map/button.png"), 85, 80,
                pButton -> {
                    onLaunchButtonPress(object);
                }, object));
    }

    private void onLaunchButtonPress(GalacticObjectBuilder.GalacticObject<?> object) {
        if (canLaunch(object, false).contains("yep") && player.level.dimension() != object.getDimension()) {
            Minecraft.getInstance().setScreen(null);
            CGNetwork.sendToServer(new PacketTeleport(object.getDimension().location(), BlockPos.ZERO, false));
        }
    }

    private String canLaunch(GalacticObjectBuilder.GalacticObject<?> object, boolean spaceStation) {
        if ((spaceStation && object.getOrbitDimension() != null) || object.getDimension() != null) {
            if (this.unlocked) {
                return "yep";
            }
            if (player.getVehicle() != null && player.getVehicle() instanceof AbstractRocket) {
                if (((AbstractRocket) player.getVehicle()).tier >= object.getTier()) {
                    return "yep";
                } else {
                    return "nope: Insufficient rocket tier.";//TODO: Translations plz
                }
            } else {
                return "nope: You are not inside a rocket.";
            }
        }
        return "nope: You cannot visit this planet.";
    }

    private void changeMap(GalacticObjectBuilder.GalacticObject<?> obj) {
        CGNetwork.sendToServer(new PacketChangeMap(obj == null ? new ResourceLocation("null") : obj.getId()));
    }

    public void renderMapTooltip(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, GalacticObjectBuilder.GalacticObject<?> object) {
        if (object != null) {
            RenderingHelper.renderTooltip(pPoseStack, pMouseX, pMouseY, new TranslatableComponent(object.getTranslationKey()).withStyle(object.getColor()), 0x800031b3, 0xFF0031AF);
        }
    }

    public void renderErrorTooltip(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, Component mes) {
        if (mes != TextComponent.EMPTY) {
            RenderingHelper.renderTooltip(pPoseStack, pMouseX, pMouseY, mes, 0x808b0000, 0xFF7C0D0E);
        }
    }

    public void drawRing(int x, int y, int radiusIn, int radiusOut, BufferBuilder buffer, GalacticObjectBuilder.GalacticObject<?> obj) {
        int[] color = {210, 0, 0, 255};
        if (canLaunch(obj, false).contains("yep")) {
            color = new int[]{0, 210, 0, 255};
        }
        RenderingHelper.drawEllipse(buffer, x, y, radiusIn * zoom, radiusOut * zoom, color, color, 1.0f, 0.6f);
    }

    public void drawAsteroidBelt(int x, int y, int radiusIn, int radiusOut, BufferBuilder buffer, GalacticObjectBuilder.GalacticObject<?> obj) {
        int[] out = {210, 0, 0, 255};
        int[] in = {210, 0, 0, 180};
        if (canLaunch(obj, false).contains("yep")) {
            out = new int[]{0, 210, 0, 255};
            in = new int[]{0, 210, 0, 180};
        }
        RenderingHelper.drawEllipse(buffer, x, y, radiusIn * zoom, radiusOut * zoom, out, in, 1.0f, 0.6f);
    }
}
