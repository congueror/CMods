package net.congueror.clib.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class MixinBlockBehaviour {

    @Inject(at = @At(value = "TAIL"), method = "onPlace")
    private void onPlace(Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving, CallbackInfo ci) {
        //BlockBehaviour.BlockStateBase c = (BlockBehaviour.BlockStateBase) ((Object)this);
        //c.getBlock().onPlace(c.asState(), pLevel, pPos, pOldState, pIsMoving);
        System.out.println("TEST");
    }
}
