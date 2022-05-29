package net.congueror.cgalaxy.blocks.station_core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.gui.MutableImageButton;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketUpdateSSCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Set;
import java.util.UUID;

public class SpaceStationCoreScreen extends AbstractContainerScreen<SpaceStationCoreContainer> {
    public static final ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/space_station_core.png");
    MutableImageButton infoButton;
    MutableImageButton setButton;
    MutableImageButton visibilityButton;

    LocalPlayer player;
    int visibility;
    Set<UUID> list;


    public SpaceStationCoreScreen(SpaceStationCoreContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        player = (LocalPlayer) pPlayerInventory.player;
        visibility = pMenu.currentObject.visibility;
        list = pMenu.currentObject.list;
    }

    @Override
    protected void init() {
        super.init();
        infoButton = addRenderableWidget(new MutableImageButton(this.leftPos - 1, this.topPos + 106, 22, 19, 196, 0, 0, GUI, 256, 256, p_onPress_1_ -> {
            Minecraft mc = Minecraft.getInstance();
            //mc.pushGuiLayer(new RoomPressurizerSettingsScreen(container.containerId, container.getRange()));
        }));

        //Send to container to do all the logic.
        setButton = addRenderableWidget(new MutableImageButton(this.leftPos + 163, this.topPos + 65, 26, 16, 196, 19, 16, GUI, 256, 256, pButton -> {
            CGNetwork.INSTANCE.sendToServer(new PacketUpdateSSCore(menu.containerId, player.getUUID(), visibility, list));
        }));

        visibilityButton = addRenderableWidget(new MutableImageButton(this.leftPos + 65, this.topPos + 32, 34, 20, 222, 0, 20, GUI, 256, 256,
                new TranslatableComponent("key.cgalaxy.private"), pButton -> privateButton()));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);

        PlayerInfo info = player.connection.getPlayerInfo(player.getUUID());
        assert info != null;
        RenderSystem.setShaderTexture(0, info.getSkinLocation());
        blit(pPoseStack, this.leftPos + 56, this.topPos + 10, 16, 16, 8.0F, 8.0F, 8, 8, 64, 64);

        if (!menu.currentObject.owner.equals(player.getUUID())) {
            setButton.visible = false;
            RenderSystem.setShaderTexture(0, GUI);
            blit(pPoseStack, this.leftPos + 163, this.topPos + 65, 26, 16, 196, 51, 26, 16, 256, 256);
        }

        drawString(pPoseStack, font, player.getDisplayName(), this.leftPos + 77, this.topPos + 13, 0xFFFFFF);

        if (visibility == 1) {
            visibilityButton.setOnTooltip(Button.NO_TOOLTIP);
            visibilityButton.setMessage(new TranslatableComponent("key.cgalaxy.private"));
        } else if (visibility == 2) {
            visibilityButton.setOnTooltip(Button.NO_TOOLTIP);
            visibilityButton.setMessage(new TranslatableComponent("key.cgalaxy.public"));
        } else if (visibility == 3) {
            visibilityButton.setOnTooltip((pButton, pPoseStack1, pMouseX1, pMouseY1) -> renderTooltip(pPoseStack1,
                    new TranslatableComponent("key.cgalaxy.selected_shift"), pMouseX1, pMouseY1));
            visibilityButton.setMessage(new TranslatableComponent("key.cgalaxy.blacklist"));
        } else if (visibility == 4) {
            visibilityButton.setOnTooltip((pButton, pPoseStack1, pMouseX1, pMouseY1) -> renderTooltip(pPoseStack1,
                    new TranslatableComponent("key.cgalaxy.selected_shift"), pMouseX1, pMouseY1));
            visibilityButton.setMessage(new TranslatableComponent("key.cgalaxy.whitelist"));
        }
    }

    public void privateButton() {
        assert minecraft != null;
        if (visibility >= 3 && visibility <= 4 && hasShiftDown()) {
            minecraft.pushGuiLayer(new SpaceStationCoreSelectScreen(menu.currentObject.owner, visibility, list));
        } else {
            if (visibility < 4) {
                visibility++;
            } else {
                visibility = 1;
            }
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI);
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth + 20, this.imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {

    }
}