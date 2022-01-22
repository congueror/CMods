package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.init.CGBlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nullable;
import java.util.Map;

public class CoalTorchBlockItem extends BlockItem {

    public CoalTorchBlockItem(Block pStandingBlock, Properties pProperties) {
        super(pStandingBlock, pProperties);
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext pContext) {
        BlockState blockstate = CGBlockInit.COAL_WALL_TORCH.get().getStateForPlacement(pContext);
        BlockState blockstate1 = null;
        LevelReader levelreader = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();

        for(Direction direction : pContext.getNearestLookingDirections()) {
            if (direction != Direction.UP) {
                BlockState blockstate2 = direction == Direction.DOWN ? this.getBlock().getStateForPlacement(pContext) : blockstate;
                if (blockstate2 != null && blockstate2.canSurvive(levelreader, blockpos)) {
                    blockstate1 = blockstate2;
                    break;
                }
            }
        }

        return blockstate1 != null && levelreader.isUnobstructed(blockstate1, blockpos, CollisionContext.empty()) ? blockstate1 : null;
    }

    public void registerBlocks(Map<Block, Item> pBlockToItemMap, Item pItem) {
        super.registerBlocks(pBlockToItemMap, pItem);
        pBlockToItemMap.put(CGBlockInit.COAL_WALL_TORCH.get(), pItem);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.remove(CGBlockInit.COAL_WALL_TORCH.get());
    }
}
