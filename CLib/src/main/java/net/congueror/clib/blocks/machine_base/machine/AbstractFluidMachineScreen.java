package net.congueror.clib.blocks.machine_base.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFluidMachineScreen<T extends AbstractFluidMachineContainer<?>> extends AbstractItemMachineScreen<T> {

    public AbstractFluidMachineScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    /**
     * Renders a tooltip consisting of the fluid tank's fluid and amount at the coordinates given.
     *
     * @param x The starting x position of the tank.
     * @param y The starting y position (bottom) of the tank.
     */
    public void renderFluidTankTooltip(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y, int tankIndex) {
        FluidTank tank = container.getFluidTanks()[tankIndex];
        List<Component> tanks = new ArrayList<>();
        if (!tank.getFluid().isEmpty()) {
            tanks.add(new TranslatableComponent(tank.getFluid().getTranslationKey()).append(": " + tank.getFluid().getAmount() + "mB"));
        } else {
            tanks.add(new TranslatableComponent("key.clib.empty"));
        }
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
        if (pMouseX > leftPos + x && pMouseX < leftPos + (x + 16) && pMouseY > topPos + y && pMouseY < topPos + (y + 52)) {
            this.renderComponentTooltip(pPoseStack, tanks, pMouseX, pMouseY);
        }
    }

    /**
     * Renders a fluid tank with the fluid at the given coordinates.
     *
     * @param x The starting x position of the tank.
     * @param y The starting y position (bottom) of the tank.
     */
    public void renderFluidTank(PoseStack pPoseStack, int x, int y, int tankIndex) {
        if (!container.getFluidTanks()[tankIndex].getFluid().isEmpty()) {
            Matrix4f matrix = pPoseStack.last().pose();
            int x1 = this.leftPos + x;
            int x2 = x1 + 16;
            int a = 50 - (50 * container.getFluidTanks()[tankIndex].getFluidAmount() / container.getFluidTanks()[tankIndex].getCapacity());
            int y1 = this.topPos + y + a;
            int y2 = y1 + (50 - a);
            RenderingHelper.renderFluid(matrix, container.getFluidTanks()[tankIndex].getFluid(), x1, x2, y1, y2, this.getBlitOffset());
        }
    }
}
