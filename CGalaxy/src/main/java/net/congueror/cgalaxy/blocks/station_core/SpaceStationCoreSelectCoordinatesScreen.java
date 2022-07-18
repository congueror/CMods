package net.congueror.cgalaxy.blocks.station_core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class SpaceStationCoreSelectCoordinatesScreen extends Screen {
    public static final ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/space_station_core_selection.png");
    BlockPos pos;
    BlockPos launchPadPos;
    final BlockPos launchPadPosStart;
    EditBox x;
    EditBox y;
    EditBox z;
    boolean error;

    int i = (this.width - 90) / 2 - 3;

    protected SpaceStationCoreSelectCoordinatesScreen(BlockPos corePos, BlockPos launchPadPos) {
        super(TextComponent.EMPTY);
        this.pos = corePos;
        this.launchPadPos = launchPadPos;
        this.launchPadPosStart = launchPadPos;
    }

    @Override
    public void tick() {
        super.tick();
        x.tick();
        y.tick();
        z.tick();
    }

    @Override
    protected void init() {
        super.init();
        Font font = minecraft.font;
        x = this.addRenderableWidget(new SSCoreEditBox(font, i + 18, 64 + 8, 68, 18, new TextComponent("X")));
        y = this.addRenderableWidget(new SSCoreEditBox(font, i + 18, 64 + 8 + 24, 68, 18, new TextComponent("Y")));
        z = this.addRenderableWidget(new SSCoreEditBox(font, i + 18, 64 + 8 + 24 + 24, 68, 18, new TextComponent("Z")));

        x.setBordered(false);
        y.setBordered(false);
        z.setBordered(false);

        x.setValue(launchPadPos.getX() + "");
        y.setValue(launchPadPos.getY() + "");
        z.setValue(launchPadPos.getZ() + "");

        x.setResponder(s -> {
            int num;
            try {
                num = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                error = true;
                return;
            }
            error = false;
            launchPadPos = new BlockPos(num, launchPadPos.getY(), launchPadPos.getZ());
        });
        y.setResponder(s -> {
            int num;
            try {
                num = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                error = true;
                return;
            }
            error = false;
            launchPadPos = new BlockPos(launchPadPos.getX(), num, launchPadPos.getZ());
        });
        z.setResponder(s -> {
            int num;
            try {
                num = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                error = true;
                return;
            }
            error = false;
            launchPadPos = new BlockPos(launchPadPos.getX(), launchPadPos.getY(), num);
        });
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        assert minecraft != null;
        int i = (this.width - 90) / 2 - 3;
        x.setX(i + 20);
        y.setX(i + 20);
        z.setX(i + 20);
        x.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        y.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        z.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

        drawString(pPoseStack, font, "X:", i + 6, 64 + 8, 0xFFFFFFFF);
        drawString(pPoseStack, font, "Y:", i + 6, 64 + 8 + 24, 0xFFFFFFFF);
        drawString(pPoseStack, font, "Z:", i + 6, 64 + 8 + 24 + 24, 0xFFFFFFFF);

        if (error) {
            drawString(pPoseStack, font, "Invalid Input!", i + 4, 64 + 3 + 24 + 24 + 24, 0xFF0000FF);
        } else {
            if (!(Math.sqrt(launchPadPos.distSqr(this.pos)) <= 50)) {
                error = true;
            }
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        if (error) {
            launchPadPos = launchPadPosStart;
        }

        assert minecraft != null;
        SpaceStationCoreScreen screen = (SpaceStationCoreScreen) minecraft.screen;
        if (screen != null)
            screen.launchPadPos = launchPadPos;
    }

    @Override
    public void renderBackground(PoseStack pPoseStack) {
        int i = (this.width - 90) / 2 - 3;
        super.renderBackground(pPoseStack);
        RenderSystem.setShaderTexture(0, GUI);
        this.blit(pPoseStack, i, 64, 0, 157, 90, 24);
        this.blit(pPoseStack, i, 64 + 24, 0, 157, 90, 24);
        this.blit(pPoseStack, i, 64 + 24 + 24, 0, 157, 90, 24);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static class SSCoreEditBox extends EditBox {

        public SSCoreEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, Component pMessage) {
            super(pFont, pX, pY, pWidth, pHeight, pMessage);
        }

        @Override
        public boolean charTyped(char pCodePoint, int pModifiers) {
            if (!this.canConsumeInput()) {
                return false;
            } else if (SharedConstants.isAllowedChatCharacter(pCodePoint)) {
                if (isAccepted(pCodePoint)) {
                    this.insertText(Character.toString(pCodePoint));
                }

                return true;
            } else {
                return false;
            }
        }

        private boolean isAccepted(char c) {
            return (c >= 48 && c <= 57) || c == 45;
        }
    }
}
