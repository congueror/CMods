package net.congueror.cgalaxy.gui.galaxy_map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreSelectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static net.congueror.cgalaxy.util.saved_data.WorldSavedData.CORE_LOCATIONS;

public class SpaceStationSelectScreen extends Screen {
    public static final ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/space_station_core_selection.png");
    List<SpaceStationCoreSelectScreen.SSCoreButton> list = new ArrayList<>();
    UUID user;
    ResourceKey<Level> orbitDim;
    int scrollAmount = 0;


    protected SpaceStationSelectScreen(UUID user, ResourceKey<Level> orbitDim) {
        super(TextComponent.EMPTY);
        this.user = user;
        this.orbitDim = orbitDim;
    }

    @Override
    protected void init() {
        super.init();
        assert this.minecraft != null;

        var cores = CORE_LOCATIONS.get(orbitDim);
        if (cores != null) {
            cores.stream().filter(o -> o.canPlayerAccess(user)).forEach(o -> {
                list.add(addRenderableWidget(new SpaceStationCoreSelectScreen.SSCoreButton((this.width - 236) / 2 + 11, 64 + 8, pButton -> {
                    this.minecraft.popGuiLayer();
                    GalaxyMapScreen screen = (GalaxyMapScreen) minecraft.screen;
                    if (screen != null)
                        screen.selectedSpaceStation = o;
                }, Objects.requireNonNull(minecraft.getConnection()).getPlayerInfo(user), 0)));
            });
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        assert minecraft != null;


        for (int i = 0; i <= 4; i++) {
            if (i + scrollAmount < list.size()) {
                list.get(i + scrollAmount).setY(64 + 8 + 28 * i);
                list.get(i + scrollAmount).render(pPoseStack, pMouseX, pMouseY, pPartialTick);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (scrollAmount + 4 < list.size())
            scrollAmount++;
        else
            scrollAmount = 0;
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public void renderBackground(PoseStack pPoseStack) {
        int i = (this.width - 236) / 2 + 3;
        super.renderBackground(pPoseStack);
        RenderSystem.setShaderTexture(0, GUI);
        this.blit(pPoseStack, i, 64, 0, 0, 236, 156);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
