package net.congueror.clib.api.machine.fluid;

import net.congueror.clib.api.machine.item.AbstractItemBlock;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public abstract class AbstractFluidBlock extends AbstractItemBlock {//TODO: Energy nbt when block is broken.

    public AbstractFluidBlock(Properties properties) {
        super(properties);
    }

    public InteractionResult blockRightClick(Player player, AbstractFluidBlockEntity te) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.MAINHAND);
        if (stack.getItem() instanceof BucketItem) {
            Fluid fluid = ((BucketItem) stack.getItem()).getFluid();
            if (te.tanks[0].isEmpty() || te.tanks[0].getFluid().getFluid().equals(fluid)) {
                te.tanks[0].fill(new FluidStack(fluid, 1000), IFluidHandler.FluidAction.EXECUTE);
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
