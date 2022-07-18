package net.congueror.cgalaxy.mixins;

public class MixinItemEntity {
    /*
    double gravity = 0.08d;

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V")
    public void constructorTail(EntityType<? extends ItemEntity> type, Level level, CallbackInfo ci) {
        CGDimensionBuilder.DimensionObject obj = CGDimensionBuilder.getObjectFromKey(level.dimension());
        if (obj != null)
            this.gravity = obj.getGravity();
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"), method = "tick")
    public void tick(CallbackInfo ci) {
        ItemEntity e = ((ItemEntity) (Object) this);
        Vec3 vec3 = e.getDeltaMovement();
        e.setDeltaMovement(e.getDeltaMovement().add(0.0D, -gravity / 2d, 0.0D));
        e.setNoGravity(true);
    }*/
}