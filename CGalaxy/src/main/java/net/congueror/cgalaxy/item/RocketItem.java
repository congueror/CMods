package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.block.launch_pad.LaunchPadBlock;
import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.clib.api.objects.items.CLItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.function.Function;

public abstract class RocketItem extends CLItem {

    public RocketItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (level.isClientSide) {
            return InteractionResult.PASS;
        }
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (state.is(CGBlockInit.LAUNCH_PAD.get())) {
            LaunchPadBlock launchPad = (LaunchPadBlock) (state.getBlock());
            ItemStack stack = pContext.getItemInHand();
            RocketEntity entity = rocketEntity(level);
            int fuel = stack.getOrCreateTag().getInt("Fuel");
            if (launchPad.spawnRocket(level, pos, entity, fuel)) {
                stack.shrink(1);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    public abstract RocketEntity rocketEntity(Level level);
}
