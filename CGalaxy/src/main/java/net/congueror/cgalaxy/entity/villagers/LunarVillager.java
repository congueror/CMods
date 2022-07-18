package net.congueror.cgalaxy.entity.villagers;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.util.registry.CGEntity;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.world.CGDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class LunarVillager extends AbstractVillager implements CGEntity {
    public LunarVillagerProfession profession;

    public LunarVillager(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
        this.profession = LunarVillagerProfession.professions.get(getRandom().nextInt(LunarVillagerProfession.professions.size()));
    }

    public LunarVillagerProfession getProfession() {
        return profession;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@Nonnull ServerLevelAccessor pLevel, @Nonnull DifficultyInstance pDifficulty, @Nonnull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        if (pDataTag != null && pDataTag.contains("Profession")) {
            this.profession = LunarVillagerProfession.getProfessionFromString(pDataTag.getString("Profession"));
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Zombie.class, 8.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("Profession")) {
            this.profession = LunarVillagerProfession.getProfessionFromString(pCompound.getString("Profession"));
        }
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("Profession", this.profession.name);
    }

    @Override
    protected void rewardTradeXp(@Nonnull MerchantOffer pOffer) {
        if (pOffer.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    @Override
    protected void updateTrades() {
        int maxTrades = 5;
        Int2ObjectMap<VillagerTrades.ItemListing[]> trades = new Int2ObjectOpenHashMap<>(this.profession.trades);

        ArrayList<Integer> nums = new ArrayList<>();

        for (int i = 1; i <= maxTrades; i++) {
            int r = this.random.nextInt(trades.size());
            if (!nums.contains(r)) {
                VillagerTrades.ItemListing[] listings = trades.get(r);
                VillagerTrades.ItemListing listing = listings[this.random.nextInt(listings.length)];
                MerchantOffer offer = listing.getOffer(this, this.random);
                this.getOffers().add(offer);
                nums.add(r);
            } else {
                i--;
                if (nums.size() == trades.size()) {
                    break;
                }
            }
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@Nonnull ServerLevel p_146743_, @Nonnull AgeableMob p_146744_) {
        return null;
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.5D).add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    public void die(@Nonnull DamageSource pCause) {
        super.die(pCause);
        if (pCause.getEntity() instanceof Zombie) {
            this.convertTo(CGEntityTypeInit.LUNAR_ZOMBIE_VILLAGER.get(), false);
        }
    }

    protected SoundEvent getAmbientSound() {
        return this.isTrading() ? SoundEvents.WANDERING_TRADER_TRADE : SoundEvents.WANDERING_TRADER_AMBIENT;
    }

    protected SoundEvent getHurtSound(@Nonnull DamageSource pDamageSource) {
        return SoundEvents.WANDERING_TRADER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WANDERING_TRADER_DEATH;
    }

    @Nonnull
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.WANDERING_TRADER_YES;
    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Nonnull
    @Override
    public InteractionResult mobInteract(Player pPlayer, @Nonnull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!itemstack.is(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (pHand == InteractionHand.MAIN_HAND) {
                pPlayer.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.getOffers().isEmpty()) {
                if (!this.level.isClientSide) {
                    this.setTradingPlayer(pPlayer);
                    this.openTradingScreen(pPlayer, this.getDisplayName(), 1);
                }
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

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
