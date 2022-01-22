package net.congueror.clib.blocks.generic;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class CLOreBlock extends OreBlock implements ICLibBlock {

    private final Map<ToolAction, Supplier<? extends Block>> modifiedState = new HashMap<>();

    public CLOreBlock(Properties properties, int exp) {
        super(properties, exp == 0 ? UniformInt.of(0, 0) : UniformInt.of(exp, exp + 2));
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
