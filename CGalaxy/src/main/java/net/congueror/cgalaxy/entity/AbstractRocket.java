package net.congueror.cgalaxy.entity;

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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;

public abstract class AbstractRocket extends Entity {

    /**
     * Set value in subclass constructor
     */
    protected int capacity;
    public final int tier;
    int i, k = 0;

    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(AbstractRocket.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> DATA_ID_DROPS = SynchedEntityData.defineId(AbstractRocket.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_FUEL = SynchedEntityData.defineId(AbstractRocket.class, EntityDataSerializers.INT);
    /**
     * 0=Idle, 1=launching, 2=in air, 3=Landing
     */
    private static final EntityDataAccessor<Integer> DATA_MODE = SynchedEntityData.defineId(AbstractRocket.class, EntityDataSerializers.INT);

    double velocity;

    protected AbstractRocket(EntityType<? extends Entity> entity, Level level, int tier) {
        super(entity, level);
        this.blocksBuilding = true;
        this.tier = tier;
    }

    public abstract Item getItem();

    public int getMode() {
        return this.entityData.get(DATA_MODE);
    }

    public void setMode(int mode) {
        this.entityData.set(DATA_MODE, mode);
    }

    public int getFuel() {
        return this.entityData.get(DATA_FUEL);
    }

    public int getCapacity() {
        return capacity;
    }

    public int drain(int amount) {
        int fuel = getFuel();
        if (fuel > 0 && fuel - amount >= 0) {
            this.entityData.set(DATA_FUEL, fuel - amount);
            return amount;
        }
        return 0;
    }

    public int fill(int amount) {
        int filled = capacity - getFuel();
        if (amount < filled) {
            this.entityData.set(DATA_FUEL, getFuel() + amount);
            filled = amount;
        } else {
            this.entityData.set(DATA_FUEL, capacity);
        }
        return filled;
    }

    public void setFuel(int fuel) {
        this.entityData.set(DATA_FUEL, fuel);
    }

    public ItemStack createItemStack() {
        ItemStack stack = new ItemStack(getItem());
        stack.getOrCreateTag().putInt("Fuel", getFuel());
        return stack;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_DAMAGE, 0.0f);
        this.entityData.define(DATA_ID_DROPS, true);
        this.entityData.define(DATA_FUEL, 0);
        this.entityData.define(DATA_MODE, 0);
    }

    public boolean getRocketLaunch() {
        return this.getPersistentData().getBoolean("Launch");
    }

    public void setRocketLaunch(boolean rocketLaunch) {
        this.getPersistentData().putBoolean("Launch", rocketLaunch);
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

        if (this.getMode() != 3) {
            if (getRocketLaunch()) {
                if (((this.getDeltaMovement().y()) <= 0.5)) {
                    this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
                }
                if (((this.getDeltaMovement().y()) >= 0.5)) {
                    this.setDeltaMovement(this.getDeltaMovement().x, 0.65, this.getDeltaMovement().z);
                }
            } else if (this.getDeltaMovement().y <= 0) {
                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.7D, 0.0D));
                }
            }
        } else {
            if (this.isVehicle()) {
                Player player = level.getNearestPlayer(this, 1);
                if (player != null && player.jumping) {
                    velocity = -0.5D;
                } else {
                    velocity = -1.8D;
                }
            }
            this.setDeltaMovement(0.0d, velocity, 0.0d);
        }
        if (!level.isClientSide && this.getDeltaMovement().y < -0.6 && fallDistance > 18 && this.isOnGround()) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 8.0f, Explosion.BlockInteraction.BREAK);
            this.remove(RemovalReason.KILLED);
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

        if (this.getMode() == 3) {
            if (this.isVehicle() && player != null) {
                //player.connection.send(new ClientboundSetTitlesAnimationPacket(5, 10, 10));
                //player.connection.send(new ClientboundSetTitleTextPacket(new TextComponent("10").withStyle(ChatFormatting.DARK_RED)));

            }
        } else if (this.getMode() == 2) {
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
                    setRocketLaunch(true);
                    ((ServerLevel) level).sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() - 2.2, this.getZ(), 100, 0.1, 0.1, 0.1, 0.001);
                    ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() - 3.2, this.getZ(), 50, 0.1, 0.1, 0.1, 0.04);
                }
            } else {
                i = 0;
                k = 0;
                this.setMode(0);
                setRocketLaunch(false);
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

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        return new Vec3(this.getX(), this.getY(), this.getZ());
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
        capacity = pCompound.getInt("Capacity");
        this.setFuel(pCompound.getInt("Fuel"));
        this.setMode(pCompound.getInt("Mode"));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag pCompound) {
        pCompound.putInt("Capacity", capacity);
        pCompound.putInt("Fuel", getFuel());
        pCompound.putInt("Mode", getMode());
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
