package net.congueror.cgalaxy.blocks.space_station_creator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.gui.MutableImageButton;
import net.congueror.cgalaxy.util.CGConfig;
import net.congueror.clib.blocks.machine_base.machine.AbstractItemMachineScreen;
import net.congueror.clib.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class SpaceStationCreatorScreen extends AbstractItemMachineScreen<SpaceStationCreatorContainer> {
    public static ResourceLocation GUI = new ResourceLocation(CGalaxy.MODID, "textures/gui/space_station_creator.png");
    MutableImageButton button;
    MutableImageButton up;
    MutableImageButton down;
    int scroll, i;


    public SpaceStationCreatorScreen(SpaceStationCreatorContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public String getKey() {
        return "block.cgalaxy.space_station_creator";
    }

    @Override
    protected void init() {
        super.init();
        button = addRenderableWidget(new MutableImageButton(this.leftPos - 1, this.topPos + 83, 22, 19, 196, 0, 0, GUI, 256, 256, p_onPress_1_ -> {
            Minecraft mc = Minecraft.getInstance();
            //mc.pushGuiLayer(new RoomPressurizerSettingsScreen(container.containerId, container.getRange()));
        }));
        up = addRenderableWidget(new MutableImageButton(this.leftPos + 145, this.topPos + 16, 15, 20, 0, 0, 0, null, 256, 256,
                new TextComponent("\u2191"), pButton -> {
            if (scroll > 0)
                scroll--;
        }));
        down = addRenderableWidget(new MutableImageButton(this.leftPos + 145, this.topPos + 36, 15, 20, 0, 0, 0, null, 256, 256,
                new TextComponent("\u2193"), pButton -> {
            if (scroll < (container.getBlueprint().getIngredients().size() + container.getBlueprint().getOptionalIngredients().size()) / 3)
                scroll++;
        }));
        up.visible = false;
        down.visible = false;
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        String inv = new TranslatableComponent("key.categories.inventory").getString();
        this.font.draw(pPoseStack, inv, ((float) (imageWidth / 2 - font.width(inv) / 2)) - 30, 74, 4210752);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderEnergyTooltip(pPoseStack, pMouseX, pMouseY);
        renderStatusTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pPoseStack, GUI);
        renderEnergyBuffer(pPoseStack, 172, 9);
        renderStatusLight(pPoseStack, 154, 8);

        RenderSystem.setShaderTexture(0, GUI);

        int progress = container.getProgress();
        int processTime = container.getProcessTime();
        int length = processTime > 0 && progress > 0 ? progress * 49 / processTime : 0;
        blit(pPoseStack, this.leftPos + 50, this.topPos + 57 - length, 196, 95 - length, 24, length + 1, 256, 256);

        int[] backgroundColor = MathHelper.DecimalRGBtoRGB(CGConfig.GUI_COLOR.get().rgb);
        RenderSystem.setShaderColor(backgroundColor[0] / 255f, backgroundColor[1] / 255f, backgroundColor[2] / 255f, 1f);
        blit(pPoseStack, this.leftPos + 20, this.topPos, 26, 26, 196, 19, 26, 26, 256, 256);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        if (container.getBlueprint() != null) {
            up.visible = true;
            down.visible = true;

            final int top = 15;
            final int tp = 75;

            i = 0;
            for (int j = scroll; j < container.getBlueprint().getIngredients().size(); j++) {
                if (i == 0 || i % 3 != 0) {
                    ItemStack stack = new ItemStack(container.getBlueprint().getIngredients().get(j).getKey());
                    minecraft.getItemRenderer().renderAndDecorateItem(stack, this.leftPos + 75, this.topPos + top + 16 * i);
                    renderTooltip(pPoseStack, pMouseX, pMouseY, tp, 10 + 16 * i, 16, 16, this.getTooltipFromItem(stack).toArray(Component[]::new));
                    i++;
                }
            }

            for (int j = Math.max(scroll - i, 0); j < container.getBlueprint().getOptionalIngredients().size(); j++) {
                if (i == 0 || i % 3 != 0) {
                    ItemStack stack;
                    if (container.getOptionalIngredients().size() <= j) {
                        stack = new ItemStack(Items.BARRIER);
                    } else {
                        stack = new ItemStack(container.getOptionalIngredients().get(j).getKey());
                    }
                    minecraft.getItemRenderer().renderAndDecorateItem(stack, this.leftPos + 75, this.topPos + top + 16 * i);
                    if (container.getOptionalIngredients().size() <= j) {
                        renderTooltip(pPoseStack, pMouseX, pMouseY, tp, 10 + 16 * i, 16, 16, new TranslatableComponent("key.cgalaxy.optional_item"));
                    } else {
                        renderTooltip(pPoseStack, pMouseX, pMouseY, tp, 10 + 16 * i, 16, 16, this.getTooltipFromItem(stack).toArray(Component[]::new));
                    }
                    i++;
                }
            }

            i = 0;
            for (int j = scroll; j < container.getBlueprint().getIngredients().size(); j++) {
                if (i == 0 || i % 3 != 0) {
                    int n = container.getStoredIngredients().get(container.getBlueprint().getIngredients().get(j).getKey()) == null ? 0 : container.getStoredIngredients().get(container.getBlueprint().getIngredients().get(j).getKey());
                    drawString(pPoseStack, font, n + "/" + container.getBlueprint().getIngredients().get(j).getValue(), this.leftPos + 95, this.topPos + top + 1 + 16 * i, 0xFFFFFFFF);
                    i++;
                }
            }

            for (int j = Math.max(scroll - i, 0); j < container.getBlueprint().getOptionalIngredients().size(); j++) {
                if (i == 0 || i % 3 != 0) {
                    int n;
                    try {
                        n = container.getOptionalIngredients().get(j) == null ? 0 : container.getOptionalIngredients().get(j).getValue();
                    } catch (IndexOutOfBoundsException e) {
                        n = 0;
                    }
                    drawString(pPoseStack, font, n + "/" + container.getBlueprint().getOptionalIngredients().get(j), this.leftPos + 95, this.topPos + top + 1 + 16 * i, 0xFFFFFFFF);
                    i++;
                }
            }
        } else {
            up.visible = false;
            down.visible = false;
        }
    }

    public static class IngredientBox extends AbstractWidget {

        public IngredientBox(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
            super(pX, pY, pWidth, pHeight, pMessage);
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            if (this.visible) {

            }
        }

        @Override
        public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

        }
    }
}
