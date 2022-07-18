package net.congueror.cgalaxy.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MartianSkeleton extends AbstractSkeleton {
    public MartianSkeleton(EntityType<? extends AbstractSkeleton> p_33570_, Level p_33571_) {
        super(p_33570_, p_33571_);
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {

    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override
    protected @NotNull SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }
}
