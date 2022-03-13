package net.congueror.cgalaxy.blocks;

import net.congueror.cgalaxy.api.registry.CGCropBlock;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LunarCarrotsBlock extends CarrotBlock implements CGCropBlock {

    public LunarCarrotsBlock(Properties p_52247_) {
        super(p_52247_);
    }

    @Override
    public boolean canSurvive(CGDimensionBuilder.DimensionObject object, int temperature) {
        return object.getRadiation() > 100.0f;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(CGBlockInit.MOON_REGOLITH.get());
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CGBlockInit.LUNAR_CARROTS.get();
    }
}
