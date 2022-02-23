package net.congueror.cgalaxy.blocks.station_core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class SpaceStationCoreSelectScreen extends Screen {
    public static final ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/space_station_core_selection.png");
    List<SSCoreButton> buttonList = new ArrayList<>();
    UUID owner;
    Set<UUID> list;
    int visibility;
    int scrollAmount = 0;

    protected SpaceStationCoreSelectScreen(UUID owner, int visibility, Set<UUID> list) {
        super(TextComponent.EMPTY);
        this.owner = owner;
        this.list = list;
        this.visibility = visibility;
    }

    @Override
    protected void init() {
        super.init();

        for (PlayerInfo playerInfo : Objects.requireNonNull(minecraft.getConnection()).getOnlinePlayers()) {
            if (!playerInfo.getProfile().getId().equals(owner)) {
                buttonList.add(addRenderableWidget(new SSCoreButton((this.width - 236) / 2 + 11, 64 + 8, pButton -> {
                    SSCoreButton b = ((SSCoreButton) pButton);
                    if (b.isListed) {
                        b.setListed(false);
                        list.remove(b.getInfo().getProfile().getId());
                    } else {
                        b.setListed(true);
                        list.add(b.getInfo().getProfile().getId());
                    }
                }, playerInfo, visibility)));
            }
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        assert minecraft != null;


        for (int i = 0; i <= 4; i++) {
            if (i + scrollAmount < buttonList.size()) {
                buttonList.get(i + scrollAmount).setY(64 + 8 + 28 * i);
                if (list.contains(buttonList.get(i + scrollAmount).getInfo().getProfile().getId())) {
                    buttonList.get(i + scrollAmount).setListed(true);
                }
                buttonList.get(i + scrollAmount).render(pPoseStack, pMouseX, pMouseY, pPartialTick);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (scrollAmount + 4 < buttonList.size())
            scrollAmount++;
        else
            scrollAmount = 0;
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
    }

    @Override
    public void onClose() {
        super.onClose();
        assert minecraft != null;
        SpaceStationCoreScreen screen = (SpaceStationCoreScreen) minecraft.screen;
        if (screen != null)
            screen.list = list;
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

    public static class SSCoreButton extends Button {

        PlayerInfo info;
        boolean isListed;
        int visibility;
        Minecraft mc;

        public SSCoreButton(int pX, int pY, OnPress pOnPress, PlayerInfo info, int visibility) {
            super(pX, pY, 220, 28, TextComponent.EMPTY, pOnPress);
            this.info = info;
            this.mc = Minecraft.getInstance();
            this.visibility = visibility;
        }

        public void setListed(boolean listed) {
            this.isListed = listed;
        }

        public void setY(int y) {
            this.y = y;
        }

        public PlayerInfo getInfo() {
            return info;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            if (this.visible)
                this.isHovered = pMouseX >= this.x && pMouseY >= this.y && pMouseX < this.x + this.width && pMouseY < this.y + this.height;
            if (this.isHoveredOrFocused()) {
                fillGradient(pPoseStack, x, y, x + width, y + height, 0xFF707070, 0xFF707070);
            }
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.setShaderTexture(0, info.getSkinLocation());
            blit(pPoseStack, x + 10, y + (this.height / 2 - 8), 16, 16, 8.0F, 8.0F, 8, 8, 64, 64);

            if (this.isListed) {
                RenderSystem.setShaderTexture(0, GUI);
                int vOffset = 12;
                if (visibility == 3) {
                } else if (visibility == 4) {
                    vOffset = 25;
                }
                blit(pPoseStack, x + width - 25, y + (this.height / 2 - 6), 14, 13, 236, vOffset, 14, 13, 256, 256);
            }

            drawString(pPoseStack, mc.font, info.getProfile().getName(), x + 30, y + (this.height / 2 - 4), 0xFFFFFF);
        }
    }
}
