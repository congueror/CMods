package net.congueror.cgalaxy.mixins;

import net.congueror.cgalaxy.util.json_managers.DimensionManager;
import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(method = "calculateFallDamage", at = @At(value = "RETURN"), cancellable = true)
    private void calculateFallDamage(float pFallDistance, float pDamageMultiplier, CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = ((LivingEntity)((Object) this));
        CGDimensionBuilder.DimensionObject obj = DimensionManager.getObjectFromKey(entity.getLevel().dimension());
        if (obj != null) {
            MobEffectInstance mobeffectinstance = entity.getEffect(MobEffects.JUMP);
            float f = mobeffectinstance == null ? 0.0F : (float)(mobeffectinstance.getAmplifier() + 1);
            cir.setReturnValue(Mth.ceil((pFallDistance - 3.0F * (0.08 / obj.getGravity()) - f) * pDamageMultiplier));
        }
    }
}
