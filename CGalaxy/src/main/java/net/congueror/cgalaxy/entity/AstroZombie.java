package net.congueror.cgalaxy.entity;

import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.api.registry.CGEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class AstroZombie extends Zombie implements CGEntity {
    public AstroZombie(EntityType<? extends Zombie> p_34271_, Level p_34272_) {
        super(p_34271_, p_34272_);
    }

    public static AttributeSupplier.Builder entityAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0D).add(Attributes.MOVEMENT_SPEED, (double)0.36F).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 2.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    @Override
    public boolean canBreath(CGDimensionBuilder.DimensionObject object) {
        return true;
    }

    @Override
    public boolean canSurviveTemperature(int temperature) {
        return true;
    }

    @Override
    public boolean canSurviveRadiation(float radiation) {
        return true;
    }
}
