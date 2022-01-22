package net.congueror.clib.mixins;

import net.congueror.clib.api.events.SaplingAdvanceEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SaplingBlock.class)
public class MixinSaplingBlock {

    @Final
    @Shadow
    private AbstractTreeGrower treeGrower;

    @Inject(at = @At("HEAD"), method = "advanceTree", cancellable = true)
    public void advanceTree(ServerLevel pLevel, BlockPos pPos, BlockState pState, Random pRand, CallbackInfo ci) {
        SaplingAdvanceEvent event = new SaplingAdvanceEvent(pLevel, pState, pPos, pRand, treeGrower);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult().equals(Event.Result.DENY)) {
            ci.cancel();
        }
    }
}
