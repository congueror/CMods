package net.congueror.cgalaxy.entity;

import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.util.CGDamageSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LaserBlast extends Projectile {
    Vec3 startPos;

    public LaserBlast(EntityType<LaserBlast> entityType, Level level) {
        super(entityType, level);
    }

    public LaserBlast(Level level, double x, double y, double z, Entity owner) {
        this(CGEntityTypeInit.LASER_BLAST.get(), level);
        this.setPos(x, y, z);
        this.startPos = this.position();
        this.setOwner(owner);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {

    }

    /*
    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize().add(0.0075F, 0.0075F, 0.0075F);
        this.setDeltaMovement(vec3.scale(pVelocity));
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }*/

    public void shoot(Entity shooter, float yaw, float pitch) {
        float f = Mth.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = Mth.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -Mth.cos(-pitch * 0.017453292F);
        float f3 = Mth.sin(-pitch * 0.017453292F);
        Vec3 vec = new Vec3(f1 * f2, f3, f * f2);
        this.setDeltaMovement(vec.x * 1.0f, vec.y * 1.0f, vec.z * 1.0f);
        this.updateHeading();

        /* Spawn the projectile half way between the previous and current position */
        double posX = shooter.xOld + (shooter.getX() - shooter.xOld) / 2.0;
        double posY = shooter.yOld + (shooter.getY() - shooter.yOld) / 2.0 + shooter.getEyeHeight();
        double posZ = shooter.zOld + (shooter.getZ() - shooter.zOld) / 2.0;
        this.setPos(posX, posY, posZ);
    }

    @Override
    public void tick() {
        super.tick();
        this.updateHeading();

        if (startPos != null && startPos.distanceTo(this.position()) > 20) {
            this.discard();
        }

        for (int i = 0; i < 5; i++) {
            double nextPosX = this.getX() + this.getDeltaMovement().x();
            double nextPosY = this.getY() + this.getDeltaMovement().y();
            double nextPosZ = this.getZ() + this.getDeltaMovement().z();
            this.setPos(nextPosX, nextPosY, nextPosZ);

            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
                this.discard();
            }
        }
    }

    public void updateHeading() {
        double horizontalDistance = this.getDeltaMovement().horizontalDistance();
        this.setYRot((float) (Mth.atan2(this.getDeltaMovement().x(), this.getDeltaMovement().z()) * (180D / Math.PI)));
        this.setXRot((float) (Mth.atan2(this.getDeltaMovement().y(), horizontalDistance) * (180D / Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_37258_) {
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        Entity entity1 = this.getOwner();
        entity.hurt(CGDamageSource.laserBlast(entity1), 15.0F);
    }
}
