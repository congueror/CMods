package net.congueror.clib.blocks.machine_base.machine;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.network.NetworkHooks;

import java.util.Arrays;

public abstract class AbstractFluidMachineBlock extends AbstractItemMachineBlock {//TODO: Energy nbt when block is broken.

    public AbstractFluidMachineBlock(Properties properties) {
        super(properties);
    }

    public InteractionResult blockRightClick(Player player, AbstractFluidMachineBlockEntity te) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.MAINHAND);
        if (stack.getItem() instanceof BucketItem) {
            Fluid fluid = ((BucketItem) stack.getItem()).getFluid();
            FluidTank tank = Arrays.stream(te.tanks).filter(FluidTank::isEmpty).findFirst().orElse(null);
            if (tank != null && tank.getFluid().getFluid().equals(fluid)) {
                tank.fill(new FluidStack(fluid, 1000), IFluidHandler.FluidAction.EXECUTE);
                te.setChanged();
                if (!player.isCreative()) {
                    player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BUCKET));
                    return InteractionResult.CONSUME;
                }
            }
        } else {
            NetworkHooks.openGui((ServerPlayer) player, te, te.getBlockPos());
        }
        return InteractionResult.SUCCESS;
    }
}
