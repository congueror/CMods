package net.congueror.cgalaxy.entity.ai;

import net.congueror.cgalaxy.entity.AstroEnderman;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class JumpMeleeAttackGoal extends MeleeAttackGoal {
    public JumpMeleeAttackGoal(PathfinderMob mob, double speedIn, boolean useLongMemory) {
        super(mob, speedIn, useLongMemory);
    }

    @Override
    protected void checkAndPerformAttack(@Nonnull LivingEntity enemy, double distToEnemySqr) {
        super.checkAndPerformAttack(enemy, distToEnemySqr);
        if (enemy.getY() > mob.getY() &&
                ((int) enemy.getX() == (int) mob.getX()) && ((int) enemy.getZ() == (int) mob.getZ()) &&
                getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();
            if (this.mob instanceof AstroEnderman && enemy instanceof Player) {
                ((AstroEnderman) this.mob).teleportToPlayer((Player) enemy);
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(enemy);
            }
        }
    }

    @Override
    protected double getAttackReachSqr(@Nonnull LivingEntity attackTarget) {
        return super.getAttackReachSqr(attackTarget) + 10;
    }
}
