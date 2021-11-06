package net.congueror.cgalaxy.entity;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractRocket extends Entity {

    protected int fuel;
    /**
     * Set value in subclass constructor
     */
    protected int capacity;
    public final int tier;
    int i, k = 0;

    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(AbstractRocket.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_ID_DROPS = SynchedEntityData.defineId(AbstractRocket.class, EntityDataSerializers.BOOLEAN);

    protected AbstractRocket(EntityType<? extends Entity> entity, Level level, int tier) {
        super(entity, level);
        this.blocksBuilding = true;
        this.tier = tier;
    }

    public abstract Item getItem();

    public int getFuel() {
        return fuel;
    }

    public int getCapacity() {
        return capacity;
    }

    public int drain(int amount) {
        int fuel = this.fuel;
        if (fuel > 0 && fuel - amount >= 0) {
            this.fuel = -amount;
            return amount;
        }
        return 0;
    }

    public int fill(int amount) {
        int filled = capacity - fuel;
        if (amount < filled) {
            fuel += amount;
            filled = amount;
        } else {
            fuel = capacity;
        }
        return filled;
    }

    public ItemStack createItemStack() {
        ItemStack stack = new ItemStack(getItem());
        stack.getOrCreateTag().putInt("Fuel", fuel);
        return stack;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_DAMAGE, 0.0f);
        this.entityData.define(DATA_ID_DROPS, true);
    }

    @Nonnull
    @Override
    public InteractionResult interact(@Nonnull Player pPlayer, @Nonnull InteractionHand pHand) {
        if (pPlayer.isShiftKeyDown()) {
            ItemStack stack = createItemStack();
            for (ItemStack stack1 : pPlayer.inventoryMenu.getItems()) {
                if (pPlayer.isCreative() && stack1.equals(stack, false)) {
                    this.remove(RemovalReason.DISCARDED);
                    return InteractionResult.CONSUME;
                }
            }
            this.remove(RemovalReason.DISCARDED);
            pPlayer.addItem(stack);
            return InteractionResult.CONSUME;
        }
        pPlayer.startRiding(this);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getPersistentData().getInt(CGalaxy.ROCKET_POWERED) != 3) {
            if (this.getPersistentData().getBoolean(CGalaxy.ROCKET_LAUNCH)) {
                if (((this.getDeltaMovement().y()) <= 0.5)) {
                    this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
                }
                if (((this.getDeltaMovement().y()) >= 0.5)) {
                    this.setDeltaMovement(this.getDeltaMovement().x, 0.65, this.getDeltaMovement().z);
                }
            } else if (this.getDeltaMovement().y <= 0) {
                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
                }
            }
        } else {
            double d0 = -Objects.requireNonNull(CGDimensionBuilder.getObjectFromKey(this.level.dimension())).getGravity() / 4.0D;
            this.setDeltaMovement(0.0d, -0.04D, 0.0d);
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (this.level.isClientSide) {
            return;
        }
        if (entityData.get(DATA_ID_DAMAGE) >= 1.0f) {
            if (entityData.get(DATA_ID_DROPS)) {
                level.addFreshEntity(new ItemEntity(level, this.getX(), this.getY(), this.getZ(), createItemStack()));
            }
            this.remove(RemovalReason.KILLED);
        }
        ServerPlayer player = (ServerPlayer) level.getNearestPlayer(this, 1);

        if (this.getPersistentData().getInt(CGalaxy.ROCKET_POWERED) == 3) {
            if (this.isVehicle() && player != null) {
                //player.connection.send(new ClientboundSetTitlesAnimationPacket(5, 10, 10));
                //player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("10").withStyle(ChatFormatting.DARK_RED)));
            }
            if (this.getDeltaMovement().y == 0) {
                this.getPersistentData().putInt(CGalaxy.ROCKET_POWERED, 0);
            }
        }

        if (this.getPersistentData().getInt(CGalaxy.ROCKET_POWERED) == 2) {
            if (this.isVehicle()) {
                i++;
                if (player != null) {
                    switch (i) {
                        case 20 -> {
                            player.connection.send(new ClientboundSetTitlesAnimationPacket(5, 10, 10));
                            player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("10").withStyle(ChatFormatting.DARK_RED)));
                        }
                        case 40 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("9").withStyle(ChatFormatting.DARK_RED)));
                        case 60 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("8").withStyle(ChatFormatting.DARK_RED)));
                        case 80 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("7").withStyle(ChatFormatting.DARK_RED)));
                        case 100 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("6").withStyle(ChatFormatting.DARK_RED)));
                        case 120 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("5").withStyle(ChatFormatting.DARK_RED)));
                        case 140 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("4").withStyle(ChatFormatting.DARK_RED)));
                        case 160 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("3").withStyle(ChatFormatting.DARK_RED)));
                        case 180 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("2").withStyle(ChatFormatting.DARK_RED)));
                        case 200 -> player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("1").withStyle(ChatFormatting.DARK_RED)));
                        case 210 -> {
                            for (ServerPlayer player1 : ((ServerLevel) level).getServer().getPlayerList().getPlayers()) {
                                player1.sendMessage(new TextComponent(player.getDisplayName().getString())
                                        .append(new TranslatableComponent("chat.cgalaxy.launch_off")).withStyle(ChatFormatting.GOLD), Util.NIL_UUID);
                            }
                        }
                    }
                }
                if (i <= 200) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.POOF, this.getX(), this.getY() - 0.5, this.getZ(), 35, 4, 0, 0, 0.03);
                    ((ServerLevel) level).sendParticles(ParticleTypes.POOF, this.getX(), this.getY() - 0.5, this.getZ(), 35, 0, 0, 4, 0.03);
                    k++;

                    if (k == 2) {
                        k = 0;
                        for (float j = 0; j <= 8; j += 0.5) {
                            ((ServerLevel) level).sendParticles(ParticleTypes.POOF, this.getX() + j, this.getY() - 0.5, this.getZ() + j, 1, 0, 0, 0, 0.03);
                        }
                        for (float j = 0; j <= 8; j += 0.5) {
                            ((ServerLevel) level).sendParticles(ParticleTypes.POOF, this.getX() + j, this.getY() - 0.5, this.getZ() - j, 1, 0, 0, 0, 0.03);
                        }
                        for (float j = 0; j <= 8; j += 0.5) {
                            ((ServerLevel) level).sendParticles(ParticleTypes.POOF, this.getX() - j, this.getY() - 0.5, this.getZ() + j, 1, 0, 0, 0, 0.03);
                        }
                        for (float j = 0; j <= 8; j += 0.5) {
                            ((ServerLevel) level).sendParticles(ParticleTypes.POOF, this.getX() - j, this.getY() - 0.5, this.getZ() - j, 1, 0, 0, 0, 0.03);
                        }
                    }
                }
                if (i >= 200) {
                    this.getPersistentData().putBoolean(CGalaxy.ROCKET_LAUNCH, true);
                    ((ServerLevel) level).sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() - 2.2, this.getZ(), 100, 0.1, 0.1, 0.1, 0.001);
                    ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() - 3.2, this.getZ(), 50, 0.1, 0.1, 0.1, 0.04);
                }
            } else {
                i = 0;
                k = 0;
                this.getPersistentData().putInt(CGalaxy.ROCKET_POWERED, 0);
                this.getPersistentData().putBoolean(CGalaxy.ROCKET_LAUNCH, false);
            }
        } else {
            i = 0;
            k = 0;
        }
    }

    @Override
    public boolean hurt(@Nonnull DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof Player player) {
            if (player.isCreative()) {
                entityData.set(DATA_ID_DAMAGE, 1.0f);
                entityData.set(DATA_ID_DROPS, false);
            } else {
                entityData.set(DATA_ID_DAMAGE, entityData.get(DATA_ID_DAMAGE) + 0.2f);
                entityData.set(DATA_ID_DROPS, true);
            }
        }
        return super.hurt(pSource, pAmount);
    }

    public boolean canSeeSky() {
        if (this.level.isDay() && !this.level.isClientSide) {
            float f = this.getBrightness();
            BlockPos blockpos = new BlockPos(this.getX(), this.getEyeY(), this.getZ());
            return f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.level.canSeeSky(blockpos);
        }

        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() - 1.7;
    }

    @Override
    public void push(@Nonnull Entity pEntity) {
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        fuel = pCompound.getInt("Fuel");
        capacity = pCompound.getInt("Capacity");
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag pCompound) {
        pCompound.putInt("Fuel", fuel);
        pCompound.putInt("Capacity", capacity);
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
