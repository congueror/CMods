package net.congueror.cgalaxy.gui.galaxy_map;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GalaxyMapScreenNEW extends AbstractContainerScreen<GalaxyMapContainer> {

    MapModes mode;

    Minecraft mc = Minecraft.getInstance();
    LocalPlayer player = mc.player;
    @Nullable
    Entity entity = player != null ? player.getVehicle() : null;
    GalaxyMapContainer container;

    boolean unlocked;

    public GalaxyMapScreenNEW(GalaxyMapContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.mode = MapModes.SOLAR_SYSTEMS;
        this.imageWidth = 528;
        this.imageHeight = 498;
        this.unlocked = pMenu.unlocked;
        this.container = pMenu;
    }

    @Override
    protected void init() {
        super.init();
        if (this.mode.equals(MapModes.GALAXIES)) {

        } else {
            blankMapButton(this.leftPos - 25, this.topPos + 320, 15, 10, this.mode.ordinal() - 1);
            if (this.mode.equals(MapModes.SOLAR_SYSTEMS)) {

            }
            if (this.mode.equals(MapModes.PLANETS)) {

            }
            if (this.mode.equals(MapModes.MOONS)) {

            }
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

        this.unlocked = container.unlocked;
    }

    private Button blankMapButton(int x, int y, int width, int height, int mapOrdinal) {
        return addRenderableWidget(new ImageButton(x, y, width, height, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), width, height, p_onPress_1_ -> {
            for (MapModes mode : MapModes.values()) {
                if (mode.ordinal() == mapOrdinal) {
                    this.mode = mode;
                }
            }
        }));
    }

    public enum MapModes {
        GALAXIES,
        SOLAR_SYSTEMS,
        PLANETS,
        MOONS
    }
}
