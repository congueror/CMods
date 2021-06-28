package com.congueror.cgalaxy.entities.rocket_entity;

import com.congueror.cgalaxy.init.EntityTypeInit;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class RocketEntity extends CreatureEntity {

    public RocketEntity(EntityType<? extends RocketEntity> type, World world) {
        super(type, world);
        this.preventEntitySpawning = true;
    }

    public RocketEntity(FMLPlayMessages.SpawnEntity packet, World worldIn) {
        this(EntityTypeInit.ROCKET.get(), worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean canCollide(Entity entity) {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    //TODO
    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() + -1.7000000000000002;
    }

    @Override
    protected void collideWithEntity(Entity p_82167_1_) {
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    //TODO
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getImmediateSource() instanceof ArrowEntity)
            return false;
        if (source.getImmediateSource() instanceof PlayerEntity)
            return false;
        if (source.getImmediateSource() instanceof PotionEntity)
            return false;
        if (source == DamageSource.FALL)
            return false;
        if (source == DamageSource.CACTUS)
            return false;
        if (source == DamageSource.DROWN)
            return false;
        if (source == DamageSource.LIGHTNING_BOLT)
            return false;
        if (source.isExplosion())
            return false;
        if (source.getDamageType().equals("trident"))
            return false;
        if (source == DamageSource.ANVIL)
            return false;
        if (source == DamageSource.DRAGON_BREATH)
            return false;
        if (source == DamageSource.WITHER)
            return false;
        if (source.getDamageType().equals("witherSkull"))
            return false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        super.getEntityInteractionResult(playerIn, hand);
        return ActionResultType.FAIL;
    }
}
