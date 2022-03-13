package net.congueror.cgalaxy.blocks.room_pressurizer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketUpdateRoomPressurizerRange;
import net.congueror.clib.blocks.abstract_machine.AbstractSettingsScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Map;

public class RoomPressurizerSettingsScreen extends AbstractSettingsScreen {
    Map<Direction, Integer> range;
    int containerId;

    protected RoomPressurizerSettingsScreen(int containerId, Map<Direction, Integer> range) {
        super(new TranslatableComponent("gui.cgalaxy.room_pressurizer_settings"), 1);
        this.containerId = containerId;
        this.range = range;
    }

    public void update(Map<Direction, Integer> range) {
        this.range = range;
    }

    @Override
    protected void init() {
        super.init();

        for (int i = 0; i < Direction.values().length; i++) {
            int finalI = i;
            addRenderableWidget(new ImageButton(leftPos + 48, topPos + 24 + (11 * i), 5, 9, 110, 9, 0, TEXTURE, 115, 140, pButton -> {
                CGNetwork.INSTANCE.sendToServer(new PacketUpdateRoomPressurizerRange(containerId, true, finalI));
            }));
            addRenderableWidget(new ImageButton(leftPos + 98, topPos + 24 + (11 * i), 5, 9, 110, 0, 0, TEXTURE, 115, 140, pButton -> {
                CGNetwork.INSTANCE.sendToServer(new PacketUpdateRoomPressurizerRange(containerId, false, finalI));
            }));
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        assert minecraft != null;

        pPoseStack.pushPose();
        pPoseStack.scale(0.6f, 0.6f, 0.6f);
        int x = (int) ((leftPos) / 0.6 + (6 / 0.6));
        int y = (int) (((topPos) / 0.6) + (26 / 0.6));
        for (int i = 0; i < Direction.values().length; i++) {
            drawString(pPoseStack, minecraft.font, Direction.values()[i].getName() + ": ", x, y + (18 * i), 0);
            drawCenteredString(pPoseStack, minecraft.font, "" + range.get(Direction.values()[i]), x + 117, y + (18 * i), 0);
        }
        pPoseStack.popPose();
    }
}
