package net.congueror.clib.api.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class CLRotatedPillarBlock extends RotatedPillarBlock implements ICLibBlock {

    private final Map<ToolAction, Supplier<? extends Block>> modifiedState = new HashMap<>();

    public CLRotatedPillarBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
        if (this.modifiedState.containsKey(toolAction)) {
            return modifiedState.get(toolAction).get().defaultBlockState();
        }
        return null;
    }

    @Override
    public void setModifiedState(ToolAction action, Supplier<? extends Block> block) {
        modifiedState.put(action, block);
    }
}
