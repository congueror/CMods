package net.congueror.cgalaxy.gui.space_suit;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGItemInit;
import net.congueror.cgalaxy.util.CGConfig;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class SpaceSuitScreen extends AbstractContainerScreen<SpaceSuitContainer> {
    int rotation;
    private Button rotateButton;
    private Button settingsButton;
    private boolean isSettings;
    public static ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/space_suit/space_suit_gui.png");

    public SpaceSuitScreen(SpaceSuitContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        rotateButton = addRenderableWidget(new ImageButton(this.leftPos + 28, this.topPos + 10, 8, 9, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), width, height, p_onPress_1_ -> {
            if (rotation < 360) {
                rotation += 90;
            }
            if (rotation == 360) {
                rotation = 0;
            }
        }));

        settingsButton = addRenderableWidget(new ImageButton(this.leftPos - 21, this.topPos + 9, 23, 19, 0, 0, 0, new ResourceLocation(CGalaxy.MODID, "textures/gui/galaxy_map/blank.png"), width, height, p_onPress_1_ -> {
            isSettings = true;
            Minecraft mc = Minecraft.getInstance();
            mc.pushGuiLayer(new SpaceSuitSettingsScreen());
        }));
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        String title = new TranslatableComponent("gui.cgalaxy.space_suit").getString();
        this.font.draw(pPoseStack, title, ((float) (imageWidth / 2 - font.width(title) / 2)) + 5, 6, CGConfig.GUI_COLOR.get().rgb);

        String inv = new TranslatableComponent("key.categories.inventory").getString();
        this.font.draw(pPoseStack, inv, ((float) (imageWidth / 2 - font.width(inv) / 2)) - 52, 86, CGConfig.GUI_COLOR.get().rgb);
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        String textureName = CGConfig.GUI_COLOR.get().toString().equals("GREEN") ? "space_suit_gui.png" : CGConfig.GUI_COLOR.get().toString().toLowerCase() + "/" + CGConfig.GUI_COLOR.get().toString().toLowerCase() + "_space_suit_gui.png";
        RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/space_suit/" + textureName));
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight + 11);
        this.blit(pPoseStack, this.leftPos - 22, this.topPos + 9, 177, 3, 23, 29);
        rotateButton.visible = true;
        settingsButton.visible = true;

        assert this.minecraft != null;
        assert this.minecraft.player != null;
        RenderingHelper.renderEntityInInventoryWithRotations(this.leftPos + 20, this.topPos + 80, 30, this.leftPos + 20 - pMouseX, this.topPos + 30 - pMouseY, this.minecraft.player, rotation);
        minecraft.getItemRenderer().renderAndDecorateFakeItem(new ItemStack(CGItemInit.OXYGEN_GEAR.get()), this.leftPos + 44, this.topPos + 14);
    }
}
