package net.congueror.clib.mixins;

import net.congueror.clib.util.events.BlockOnPlacedEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockBehaviour {

    @Shadow
    public abstract Block getBlock();

    @Inject(at = @At(value = "HEAD"), method = "onPlace", cancellable = true)
    private void onPlaceHead(Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new BlockOnPlacedEvent.Pre(pLevel, pPos, getBlock(), pOldState, pIsMoving))) {
            ci.cancel();
        }
    }

    @Inject(at = @At(value = "TAIL"), method = "onPlace")
    private void onPlaceTail(Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new BlockOnPlacedEvent.Post(pLevel, pPos, getBlock(), pOldState, pIsMoving));
    }
}
