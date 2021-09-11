package net.congueror.cgalaxy.block.meteorite;

import net.congueror.cgalaxy.util.DamageSources;
import net.congueror.clib.api.objects.machine_objects.tickable.AbstractTickableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MeteoriteBlock extends AbstractTickableBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE1 = Block.box(0.0D, 2.0D, 2.0D, 2.0D, 14.0D, 14.0D);
    protected static final VoxelShape SHAPE2 = Block.box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 2.0D);
    protected static final VoxelShape SHAPE3 = Block.box(2.0D, 2.0D, 2.0D, 2.0D, 14.0D, 14.0D);
    protected static final VoxelShape SHAPE4 = Block.box(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 2.0D);
    private static final VoxelShape AABB = Shapes.or(SHAPE, SHAPE1, SHAPE2, SHAPE3, SHAPE4);

    public MeteoriteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.LIT, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
        return new MeteoriteBlockEntity(pPos, pState);
    }

    @Override
    public void stepOn(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, Entity pEntity) {
        if (!pEntity.fireImmune() && pEntity instanceof LivingEntity && pLevel.getBlockState(pPos).getValue(BlockStateProperties.LIT)) {
            pEntity.hurt(DamageSources.HOT_FLOOR, 1.0f);
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        return AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.LIT);
    }

    @Override
    public boolean canCreatureSpawn(BlockState state, BlockGetter world, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return false;
    }
}
