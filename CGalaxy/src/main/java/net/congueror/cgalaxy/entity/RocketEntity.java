package net.congueror.cgalaxy.entity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;

public abstract class RocketEntity extends Entity {

    protected int fuel;
    /**Set value in subclass constructor*/
    protected int capacity;
    int i, k = 0;

    protected RocketEntity(EntityType<? extends Entity> entity, Level level) {
        super(entity, level);
        this.blocksBuilding = true;
    }

    public abstract Item getItem();

    public int getFuel() {
        return fuel;
    }

    public int getCapacity() {
        return capacity;
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

    @Nonnull
    @Override
    public InteractionResult interact(@Nonnull Player pPlayer, @Nonnull InteractionHand pHand) {
        if (pPlayer.isShiftKeyDown()) {
            ItemStack stack = new ItemStack(getItem());
            stack.getOrCreateTag().putInt("Fuel", fuel);
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
    public void baseTick() {
        super.baseTick();
        if (this.getPersistentData().getInt("Powered") == 2) {
            if (level instanceof ServerLevel) {
                if (this.isVehicle()) {
                    i++;
                    ServerPlayer player = (ServerPlayer) level.getNearestPlayer(this, 1);
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
                            case 210 -> ((ServerLevel) level).getServer().getPlayerList().broadcastMessage(
                                    new TextComponent(player.getDisplayName().getString())
                                            .append(new TranslatableComponent("chat.cgalaxy.launch_off").withStyle(ChatFormatting.GOLD)), ChatType.GAME_INFO, player.getUUID());
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
                        if (((this.getDeltaMovement().y()) <= 0.5)) {
                            this.setDeltaMovement((this.getDeltaMovement().x()), ((this.getDeltaMovement().y()) + 0.1), (this.getDeltaMovement().z()));
                        }
                        if (((this.getDeltaMovement().y()) >= 0.5)) {
                            this.setDeltaMovement((this.getDeltaMovement().x()), 0.63, (this.getDeltaMovement().z()));
                        }

                        ((ServerLevel) level).sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() - 2.2, this.getZ(), 100, 0.1, 0.1, 0.1, 0.001);
                        ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() - 3.2, this.getZ(), 50, 0.1, 0.1, 0.1, 0.04);
                    }
                } else {
                    i = 0;
                    this.getPersistentData().putInt("Powered", 0);
                }
            }
        }
    }

    @Override
    protected void defineSynchedData() {

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
    public void push(@Nonnull Entity pEntity) {}

    @Override
    public boolean isPickable() {
        return false;
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
