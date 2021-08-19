package com.congueror.cgalaxy.entities.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class JumpMeleeAttackGoal extends MeleeAttackGoal {
    public JumpMeleeAttackGoal(CreatureEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }

    @Override//TODO
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        super.checkAndPerformAttack(enemy, distToEnemySqr);
        if (enemy.getPosY() > attacker.getPosY() &&
                ((int) enemy.getPosX() == (int) attacker.getPosX()) && ((int) enemy.getPosZ() == (int) attacker.getPosZ()) &&
                getSwingCooldown() <= 0) {
            this.resetSwingCooldown();
            this.attacker.getJumpController().setJumping();
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return super.getAttackReachSqr(attackTarget) + 10;
    }
}
