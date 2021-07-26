package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.clib.blocks.AbstractFluidBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class FuelRefineryBlock extends AbstractFluidBlock {

    public FuelRefineryBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FuelRefineryTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof FuelRefineryTileEntity) {
                FuelRefineryTileEntity te = (FuelRefineryTileEntity) tileEntity;
                ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
                if (stack.getItem() instanceof BucketItem) {
                    Fluid fluid = ((BucketItem) stack.getItem()).getFluid();
                    if (te.tanks[0].isEmpty() || te.tanks[0].getFluid().getFluid().equals(fluid)) {
                        te.tanks[0].fill(new FluidStack(fluid, 1000), IFluidHandler.FluidAction.EXECUTE);
                        te.markDirty();
                        if (!player.isCreative()) {
                            stack.getContainerItem();
                            return ActionResultType.CONSUME;
                        }
                    }
                } else {
                    NetworkHooks.openGui((ServerPlayerEntity) player, te, te.getPos());
                }
            } else {
                throw new IllegalStateException("Named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }
}
