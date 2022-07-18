package net.congueror.cgalaxy.entity.villagers;

import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.util.registry.CGEntity;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.init.CGItemInit;
import net.congueror.cgalaxy.world.CGDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nonnull;
import java.util.UUID;

public class LunarZombieVillager extends Zombie implements CGEntity {
    private int villagerConversionTime;
    private UUID conversionSource;

    public LunarZombieVillager(EntityType<? extends Zombie> p_34271_, Level p_34272_) {
        super(p_34271_, p_34272_);
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && this.isAlive() && villagerConversionTime > 0) {
            this.villagerConversionTime -= 1;
            if (this.villagerConversionTime <= 0 && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, CGEntityTypeInit.LUNAR_VILLAGER.get(), (timer) -> this.villagerConversionTime = timer)) {
                this.finishConversion((ServerLevel) this.level);
            }
        }
        super.tick();
    }

    @Nonnull
    @Override
    protected InteractionResult mobInteract(@Nonnull Player pPlayer, @Nonnull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(CGItemInit.DIAMOND_APPLE.get())) {
            if (this.hasEffect(MobEffects.WEAKNESS)) {
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (!this.level.isClientSide) {
                    this.startConverting(pPlayer.getUUID(), this.random.nextInt(2401) + 3600);
                }

                this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("ConversionTime", this.villagerConversionTime > 0 ? this.villagerConversionTime : -1);
        if (this.conversionSource != null) {
            pCompound.putUUID("ConversionPlayer", this.conversionSource);
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ConversionTime", 99) && pCompound.getInt("ConversionTime") > -1) {
            this.startConverting(pCompound.hasUUID("ConversionPlayer") ? pCompound.getUUID("ConversionPlayer") : null, pCompound.getInt("ConversionTime"));
        }
    }

    private void startConverting(UUID pConversionStarter, int pConversionTime) {
        this.conversionSource = pConversionStarter;
        this.villagerConversionTime = pConversionTime;
        this.removeEffect(MobEffects.WEAKNESS);
        this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, pConversionTime, Math.min(this.level.getDifficulty().getId() - 1, 0)));
        this.level.broadcastEntityEvent(this, (byte) 16);
    }

    private void finishConversion(ServerLevel p_34399_) {
        LunarVillager villager = this.convertTo(CGEntityTypeInit.LUNAR_VILLAGER.get(), false);

        assert villager != null;
        for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            ItemStack itemstack = this.getItemBySlot(equipmentslot);
            if (!itemstack.isEmpty()) {
                if (EnchantmentHelper.hasBindingCurse(itemstack)) {
                    villager.getSlot(equipmentslot.getIndex() + 300).set(itemstack);
                } else {
                    double d0 = this.getEquipmentDropChance(equipmentslot);
                    if (d0 > 1.0D) {
                        this.spawnAtLocation(itemstack);
                    }
                }
            }
        }

        villager.finalizeSpawn(p_34399_, p_34399_.getCurrentDifficultyAt(villager.blockPosition()), MobSpawnType.CONVERSION, null, null);
        if (this.conversionSource != null) {
            Player player = p_34399_.getPlayerByUUID(this.conversionSource);
            if (player instanceof ServerPlayer) {
                //TODO: Advancement
            }
        }
        villager.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        if (!this.isSilent()) {
            p_34399_.levelEvent(null, 1027, this.blockPosition(), 0);
        }
        net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, villager);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_VILLAGER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return SoundEvents.ZOMBIE_VILLAGER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_VILLAGER_DEATH;
    }

    @Nonnull
    @Override
    public SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_VILLAGER_STEP;
    }

    @Nonnull
    @Override
    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }//todo

    @Override
    public boolean canBreath(CGDimensionBuilder.DimensionObject object) {
        return object.getDim().equals(CGDimensions.MOON);
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
