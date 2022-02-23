package net.congueror.cgalaxy.blocks.room_pressurizer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.gui.MutableImageButton;
import net.congueror.clib.blocks.abstract_machine.fluid.AbstractFluidScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class RoomPressurizerScreen extends AbstractFluidScreen<RoomPressurizerContainer> {
    public static ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/room_pressurizer.png");
    MutableImageButton button;

    public RoomPressurizerScreen(RoomPressurizerContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.container = pMenu;
    }

    @Override
    public String getKey() {
        return "block.cgalaxy.room_pressurizer";
    }

    @Override
    protected void init() {
        super.init();
        button = addRenderableWidget(new MutableImageButton(this.leftPos - 1, this.topPos + 83, 22, 19, 196, 0, 0, GUI, 256, 256, p_onPress_1_ -> {
            Minecraft mc = Minecraft.getInstance();
            mc.pushGuiLayer(new RoomPressurizerSettingsScreen(container.containerId, container.getRange()));
        }));
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTicks);
        if (Minecraft.getInstance().screen instanceof RoomPressurizerSettingsScreen screen) {
            screen.update(container.getRange());
        }
        //Updates the range reference inside the lambda.
        button.setOnPress(pButton -> Minecraft.getInstance().pushGuiLayer(new RoomPressurizerSettingsScreen(container.containerId, container.getRange())));
        renderEnergyTooltip(pPoseStack, pMouseX, pMouseY);
        renderFluidTankTooltip(pPoseStack, pMouseX, pMouseY, 72, 17, 0);
        renderFluidTankTooltip(pPoseStack, pMouseX, pMouseY, 109, 17, 1);
        renderStatusTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        renderBackground(pPoseStack, GUI);
        renderVerticalArrow(pPoseStack, 92, 59);
        renderEnergyBuffer(pPoseStack, 172, 9);
        renderFluidTank(pPoseStack, 73, 18, 0);
        renderFluidTank(pPoseStack, 110, 18, 1);
        renderStatusLight(pPoseStack, 154, 8);
    }
}
