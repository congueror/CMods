package net.congueror.clib.mixins;

import net.congueror.clib.util.events.CropGrowEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CropBlock.class)
public class MixinCropBlock {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"), method = "randomTick", cancellable = true)
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom, CallbackInfo ci) {
        CropGrowEvent event = new CropGrowEvent(((CropBlock)(Object) this), pState, pLevel, pPos, pRandom);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult().equals(Event.Result.DENY)) {
            ci.cancel();
        }
    }
}
