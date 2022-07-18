package net.congueror.cgalaxy.entity;

import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.util.registry.CGEntity;
import net.congueror.cgalaxy.entity.villagers.LunarVillager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GalacticVisitor extends Monster implements CGEntity, RangedAttackMob, CrossbowAttackMob {
    private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(GalacticVisitor.class, EntityDataSerializers.BOOLEAN);
    private final RangedBowAttackGoal<GalacticVisitor> bowGoal = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
    private final RangedCrossbowAttackGoal<GalacticVisitor> crossbowGoal = new RangedCrossbowAttackGoal<>(this, 1.0D, 15.0F);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.8D, false) {
        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        @Override
        public void stop() {
            super.stop();
            GalacticVisitor.this.setAggressive(false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void start() {
            super.start();
            GalacticVisitor.this.setAggressive(true);
        }
    };

    private static final List<Item> POSSIBLE_WEAPONS = new ArrayList<>() {{
        this.add(Items.STONE_SWORD);
        this.add(Items.IRON_SWORD);
        this.add(Items.GOLDEN_SWORD);
        this.add(Items.DIAMOND_SWORD);
        this.add(Items.NETHERITE_SWORD);

        this.add(Items.BOW);
        this.add(Items.CROSSBOW);
    }};

    private static final List<Item> POSSIBLE_ARROWS = new ArrayList<>() {{
        this.add(Items.ARROW);
    }};

    public GalacticVisitor(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.reassessWeaponGoal();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LunarVillager.class, true));
    }

    public void reassessWeaponGoal() {
        if (!this.level.isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            this.goalSelector.removeGoal(this.crossbowGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem || item instanceof CrossbowItem));
            if (itemstack.is(Items.BOW)) {
                int i = 10;
                if (this.level.getDifficulty() != Difficulty.HARD) {
                    i = 20;
                }

                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(2, this.bowGoal);
            } else if (itemstack.is(Items.CROSSBOW)) {
                this.goalSelector.addGoal(2, this.crossbowGoal);
            } else {
                this.goalSelector.addGoal(2, this.meleeGoal);
            }
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CHARGING_CROSSBOW, false);
    }

    public boolean isChargingCrossbow() {
        return this.entityData.get(IS_CHARGING_CROSSBOW);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public void rideTick() {
        super.rideTick();
        if (this.getVehicle() instanceof PathfinderMob pathfindermob) {
            this.yBodyRot = pathfindermob.yBodyRot;
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        this.populateDefaultEquipmentSlots(pDifficulty);
        this.reassessWeaponGoal();
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pDifficulty);
        ItemStack stack = new ItemStack(POSSIBLE_WEAPONS.get(this.random.nextInt(POSSIBLE_WEAPONS.size())));
        for (int i = 0; i < this.random.nextInt(3); i++) {
            EnchantmentHelper.enchantItem(this.random, stack, 5 + this.random.nextInt(15), true);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, stack);
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        super.setItemSlot(pSlot, pStack);
        if (!this.level.isClientSide)
            this.reassessWeaponGoal();
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
        if (this.getItemInHand(InteractionHand.MAIN_HAND).is(Items.BOW)) {
            ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
            AbstractArrow abstractarrow = ProjectileUtil.getMobArrow(this, itemstack, pDistanceFactor);
            if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
                abstractarrow = ((net.minecraft.world.item.BowItem) this.getMainHandItem().getItem()).customArrow(abstractarrow);
            double d0 = pTarget.getX() - this.getX();
            double d1 = pTarget.getY(0.3333333333333333D) - abstractarrow.getY();
            double d2 = pTarget.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            abstractarrow.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level.getDifficulty().getId() * 4));
            this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level.addFreshEntity(abstractarrow);
        } else if (this.getItemInHand(InteractionHand.MAIN_HAND).is(Items.CROSSBOW)) {
            this.performCrossbowAttack(this, 1.6f);
        }
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem p_21430_) {
        return p_21430_.equals(Items.BOW) || p_21430_.equals(Items.CROSSBOW);
    }

    @Override
    public void setChargingCrossbow(boolean pIsCharging) {
        this.entityData.set(IS_CHARGING_CROSSBOW, pIsCharging);
    }

    @Override
    public void shootCrossbowProjectile(LivingEntity entity, ItemStack stack, Projectile projectile, float p_32331_) {
        this.shootCrossbowProjectile(this, entity, projectile, p_32331_, 1.6F);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.PLAYER_HURT;
    }

    @Override
    public boolean canBreath(CGDimensionBuilder.DimensionObject object) {
        return true;
    }

    @Override
    public boolean canSurviveRadiation(float radiation) {
        return true;
    }

    @Override
    public boolean canSurviveTemperature(int temperature) {
        return true;
    }
}
