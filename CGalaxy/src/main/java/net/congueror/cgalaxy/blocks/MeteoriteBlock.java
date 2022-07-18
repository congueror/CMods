package net.congueror.cgalaxy.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class MeteoriteBlock extends FallingBlock {
    public MeteoriteBlock(Properties properties) {
        super(properties);
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
    public boolean isValidSpawn(BlockState state, BlockGetter world, BlockPos pos, SpawnPlacements.Type type, EntityType<?> entityType) {
        return false;
    }
}
