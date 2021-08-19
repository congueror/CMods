package com.congueror.cgalaxy.entities;

import com.congueror.cgalaxy.entities.ai.JumpMeleeAttackGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class AstroEndermanEntity extends EndermanEntity {
    public AstroEndermanEntity(EntityType<? extends EndermanEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new EndermanEntity.StareGoal(this));
        this.goalSelector.addGoal(2, new JumpMeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(10, new EndermanEntity.PlaceBlockGoal(this));
        this.goalSelector.addGoal(11, new EndermanEntity.TakeBlockGoal(this));
        this.targetSelector.addGoal(1, new EndermanEntity.FindPlayerGoal(this, this::func_233680_b_));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, EndermiteEntity.class, 10, true, false, field_213627_bA));
        this.targetSelector.addGoal(4, new ResetAngerGoal<>(this, false));
    }
}
