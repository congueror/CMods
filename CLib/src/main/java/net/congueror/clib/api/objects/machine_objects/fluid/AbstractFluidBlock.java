package net.congueror.clib.api.objects.machine_objects.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;

public abstract class AbstractFluidBlock extends Block implements EntityBlock {

    public AbstractFluidBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.LIT, false));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide) {
            return null;
        }
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof AbstractFluidTileEntity tile) {
                tile.tickServer();
            }
        };
    }

    public InteractionResult blockRightClick(Player player, AbstractFluidTileEntity te) {
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

    @Override
    public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof AbstractFluidTileEntity) {
                Containers.dropContents(worldIn, pos, ((AbstractFluidTileEntity) tileentity).getDrops());
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, state, te, stack);
        worldIn.removeBlock(pos, false);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT);
    }
}
