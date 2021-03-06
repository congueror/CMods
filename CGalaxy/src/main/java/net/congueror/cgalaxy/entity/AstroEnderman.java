package net.congueror.cgalaxy.entity;

import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.api.registry.CGEntity;
import net.congueror.cgalaxy.entity.ai.JumpMeleeAttackGoal;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AstroEnderman extends EnderMan implements CGEntity {
    public AstroEnderman(EntityType<? extends EnderMan> p_32485_, Level p_32486_) {
        super(p_32485_, p_32486_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new JumpMeleeAttackGoal(this, 1.0D, false));
    }

    public void teleportToPlayer(Player player) {
        this.level.playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
        this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        this.teleportTo(player.getX(), player.getY(), player.getZ());
    }

    @Override
    public boolean canBreath(CGDimensionBuilder.DimensionObject object) {
        return true;
    }
}
