package net.congueror.cgalaxy.gui.galaxy_map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import org.apache.commons.lang3.tuple.MutablePair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    List<Button> nullButtons = new ArrayList<>();
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
        this.unlocked = pMenu.unlocked;
        this.currentObj = pMenu.map;
        this.container = pMenu;
    }

    @Override
    protected void init() {
        super.init();
        List<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>> galaxies = GalacticObjectBuilder.galaxies();
        for (int i = 0; i < galaxies.size(); i++) {
            nullButtons.add(addGalaxySelButton(this.width / 2 - 100, this.height / 2 - 50 - (i * 21), 200, 20, 200, 60,
                    "textures/gui/galaxy_map/galaxy_button.png",
                    new TranslatableComponent(galaxies.get(i).getName()).withStyle(ChatFormatting.DARK_PURPLE),
                    galaxies.get(i)));

            galaxyButtons.add(new MutablePair<>(galaxies.get(i), addBackButton(this.width / 24, this.topPos + 320, null)));
        }
        List<Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>>> solarSystems = GalacticObjectBuilder.solarSystems();
        for (Map.Entry<GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.SolarSystem>, GalacticObjectBuilder.GalacticObject<GalacticObjectBuilder.Galaxy>> solarSystem : solarSystems) {
            galaxyButtons.add(new MutablePair<>(solarSystem.getValue(),
                    addMapButton(solarSystem.getKey().getX().apply(this.width, this.leftPos), solarSystem.getKey().getY().apply(this.height, this.topPos), 7, 7, 7, 7, "textures/gui/galaxy_map/galaxy_map_select.png", solarSystem.getKey())));

            solarSystemButtons.add(new MutablePair<>(solarSystem.getKey(),
                    addBackButton(this.width / 24, this.topPos + 320, solarSystem.getKey().getType().getGalaxy())));
        }

        /*
        if (this.currentObj.getType() instanceof GalacticObjectBuilder.Planet planet) {
            planetButtons.add(addBackButton(this.leftPos - 25, this.topPos + 320, planet.getSolarSystem()));
        }
        if (this.currentObj.getType() instanceof GalacticObjectBuilder.Moon moon) {
            moonButtons.add(addBackButton(this.leftPos - 25, this.topPos + 320, moon.getPlanet().getType().getSolarSystem()));
        }
        if (this.currentObj.getType() instanceof GalacticObjectBuilder.MoonMoon moonMoon) {
            moonMoonButtons.add(addBackButton(this.leftPos - 25, this.topPos + 320, moonMoon.getMoon().getType().getPlanet()));
        }*/
    }

    private void resetButtons() {
        for (Button button : nullButtons) {
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

    private void setVisible() {
        for (Button button : nullButtons) {
            button.visible = true;
        }
    }

    private <G extends GalacticObjectBuilder<G>> void setVisible(List<MutablePair<GalacticObjectBuilder.GalacticObject<G>, Button>> list) {
        for (Button button : list.stream().map(pair -> pair.right).collect(Collectors.toList())) {
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
            setVisible();
            RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_empty_background.png"));
            blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);
        } else {
            if (this.currentObj.getType() instanceof GalacticObjectBuilder.Galaxy) {
                setVisible(galaxyButtons);
                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/milky_way.png"));
                blit(pPoseStack, 0, 0, 0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight(), width, height);

                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/galaxy_map_info.png"));
                blit(pPoseStack, this.width / 30, this.topPos + 175, 0, 0, 160, 160, 160, 160);

                pPoseStack.pushPose();
                pPoseStack.scale(1.5f, 1.5f, 0);
                drawString(pPoseStack, font, new TranslatableComponent(currentObj.getName()).withStyle(ChatFormatting.DARK_PURPLE), this.width / 30, this.topPos + 155, 0);
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
        }
    }

    private Button addBackButton(int x, int y, GalacticObjectBuilder.GalacticObject<?> object) {
        return addMapButton(x, y, 15, 10, 15, 20, "textures/gui/galaxy_map/back_button.png", object);
    }

    private Button addMapButton(int x, int y, int width, int height, int textureWidth, int textureHeight, String texture, GalacticObjectBuilder.GalacticObject<?> object) {
        return addRenderableWidget(new ImageButton(x, y, width, height, 0, 0, height
                , new ResourceLocation(CGalaxy.MODID, texture), textureWidth, textureHeight,
                p_onPress_1_ -> changeMap(object), TextComponent.EMPTY));
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
}
