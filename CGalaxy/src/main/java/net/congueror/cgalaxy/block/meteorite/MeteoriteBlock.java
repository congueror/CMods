package net.congueror.cgalaxy.block.meteorite;

import net.congueror.cgalaxy.util.DamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

public class MeteoriteBlock extends FallingBlock implements EntityBlock {//TODO: Fix Particles
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE1 = Block.box(0.0D, 2.0D, 2.0D, 2.0D, 14.0D, 14.0D);
    protected static final VoxelShape SHAPE2 = Block.box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 2.0D);
    protected static final VoxelShape SHAPE3 = Block.box(2.0D, 2.0D, 2.0D, 2.0D, 14.0D, 14.0D);
    protected static final VoxelShape SHAPE4 = Block.box(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 2.0D);

    public MeteoriteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.LIT, false));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level pLevel, @Nonnull BlockState pState, @Nonnull BlockEntityType<T> pBlockEntityType) {
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof MeteoriteBlockEntity tile) {
                tile.tick();
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
        return new MeteoriteBlockEntity(pPos, pState);
    }

    @Override
    public void stepOn(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, Entity pEntity) {
        if (!pEntity.fireImmune() && pEntity instanceof LivingEntity && pLevel.getBlockState(pPos).getValue(BlockStateProperties.LIT)) {
            pEntity.hurt(DamageSources.ON_FIRE, 1.0f);
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        ArrayList<VoxelShape> shapes = new ArrayList<>();
        shapes.add(box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D));
        shapes.add(box(0.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
        shapes.add(box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 16.0D));

        VoxelShape shape = Shapes.empty();
        for (VoxelShape i : shapes) {
            shape = Shapes.joinUnoptimized(shape, i, BooleanOp.OR);
        }
        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.LIT);
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, BlockGetter world, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return false;
    }

    @Override
    public void animateTick(BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull Random pRand) {
        if (pState.getValue(BlockStateProperties.LIT)) {
            double d0 = pPos.getX() + 0.5D;
            double d1 = pPos.getY() + 0.5D;
            double d2 = pPos.getZ() + 0.5D;
            if (pRand.nextDouble() < 0.1D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1.0F, 1.0f,
                        false);
            }
            if (pRand.nextDouble() < 0.1D) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0f,
                        false);
            }
            double f0 = pRand.nextDouble();
            double f1 = pRand.nextDouble();
            double f2 = pRand.nextDouble();
            double f3 = pRand.nextBoolean() ? -f0 : f0;
            double f4 = pRand.nextBoolean() ? -f1 : f1;
            double f5 = pRand.nextBoolean() ? -f2 : f2;
            pLevel.addParticle(ParticleTypes.FLAME, d0 + f3, d1 + f4, d2 + f5, 0.0D, 0.0D, 0.0D);
        }
    }
}
