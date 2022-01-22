package net.congueror.cgalaxy.gui.space_suit;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.item.ColdProtectionUnitItem;
import net.congueror.cgalaxy.item.HeatProtectionUnitItem;
import net.congueror.cgalaxy.item.RadiationProtectionUnitItem;
import net.congueror.cgalaxy.util.CGConfig;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.clib.CLib;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SpaceSuitScreen extends AbstractContainerScreen<SpaceSuitContainer> {
    int rotation;
    private Button rotateButton;
    private Button settingsButton;
    public static ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/space_suit/space_suit_gui.png");

    public SpaceSuitScreen(SpaceSuitContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        rotateButton = addRenderableWidget(new ImageButton(this.leftPos + 28, this.topPos + 10, 8, 9, 0, 0, 0, new ResourceLocation(CLib.MODID, "textures/gui/blank.png"), width, height, p_onPress_1_ -> {
            if (rotation < 360) {
                rotation += 90;
            }
            if (rotation == 360) {
                rotation = 0;
            }
        }));

        settingsButton = addRenderableWidget(new ImageButton(this.leftPos - 21, this.topPos + 9, 23, 19, 0, 0, 0, new ResourceLocation(CLib.MODID, "textures/gui/blank.png"), width, height, p_onPress_1_ -> {
            Minecraft mc = Minecraft.getInstance();
            mc.pushGuiLayer(new SpaceSuitSettingsScreen());
        }));
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        String title = new TranslatableComponent("gui.cgalaxy.space_suit").getString();
        this.font.draw(pPoseStack, title, ((float) (imageWidth / 2 - font.width(title) / 2)) + 5, 4, CGConfig.GUI_COLOR.get().rgb);

        String inv = new TranslatableComponent("key.categories.inventory").getString();
        this.font.draw(pPoseStack, inv, ((float) (imageWidth / 2 - font.width(inv) / 2)) - 55, 86, CGConfig.GUI_COLOR.get().rgb);
    }

    @Override
    public void render(@Nonnull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1, 1, 1, 1);
        String textureName = CGConfig.GUI_COLOR.get().toString().equals("GREEN") ? "space_suit_gui.png" : CGConfig.GUI_COLOR.get().toString().toLowerCase() + "/" + CGConfig.GUI_COLOR.get().toString().toLowerCase() + "_space_suit_gui.png";
        RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/space_suit/" + textureName));
        this.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight + 11);
        this.blit(pPoseStack, this.leftPos - 22, this.topPos + 9, 177, 3, 23, 19);
        this.blit(pPoseStack, this.leftPos + 44, this.topPos + 14, 176, 23, 16, 16);
        this.blit(pPoseStack, this.leftPos + 44, this.topPos + 33, 190, 23, 16, 16);
        this.blit(pPoseStack, this.leftPos + 44, this.topPos + 52, 190, 23, 16, 16);
        assert minecraft != null;
        List<Item> items = SpaceSuitUtils.deserializeContents(minecraft.player).stream().map(ItemStack::getItem).collect(Collectors.toList());
        AtomicBoolean flag1 = new AtomicBoolean(true);
        AtomicBoolean flag2 = new AtomicBoolean(true);
        AtomicBoolean flag3 = new AtomicBoolean(true);
        items.forEach(item -> {
            if (item instanceof HeatProtectionUnitItem) {
                flag1.set(false);
            }
            if (item instanceof ColdProtectionUnitItem) {
                flag2.set(false);
            }
            if (item instanceof RadiationProtectionUnitItem) {
                flag3.set(false);
            }
        });
        if (flag1.get()) this.blit(pPoseStack, this.leftPos + 153, this.topPos + 24, 206, 38, 16, 16);
        if (flag2.get()) this.blit(pPoseStack, this.leftPos + 153, this.topPos + 43, 191, 38, 16, 16);
        if (flag3.get()) this.blit(pPoseStack, this.leftPos + 153, this.topPos + 62, 176, 38, 16, 16);
        rotateButton.visible = true;
        settingsButton.visible = true;

        assert this.minecraft != null;
        assert this.minecraft.player != null;
        RenderingHelper.renderEntityInInventoryWithRotations(this.leftPos + 20, this.topPos + 80, 30, this.leftPos + 20 - pMouseX, this.topPos + 30 - pMouseY, this.minecraft.player, rotation);
    }
}
