package com.congueror.cgalaxy.entities;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class RocketEntity extends CreatureEntity {

    protected int fuel;
    /**Set value in subclass constructor*/
    protected int capacity;
    int i, k = 0;

    public RocketEntity(EntityType<? extends RocketEntity> entity, World world) {
        super(entity, world);
        this.preventEntitySpawning = true;
        setNoAI(false);
        enablePersistence();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public int getFuel() {
        return fuel;
    }

    public int getFuelCapacity() {
        return capacity;
    }

    public abstract Item getItem();

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

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Fuel", fuel);
        compound.putInt("FuelCapacity", capacity);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        fuel = compound.getInt("Fuel");
        capacity = compound.getInt("FuelCapacity");
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        super.getEntityInteractionResult(playerIn, hand);
        if (playerIn.isSneaking()) {
            ItemStack stack = new ItemStack(getItem());
            stack.getOrCreateTag().putInt("Fuel", fuel);
            for (ItemStack stack1 : playerIn.inventory.mainInventory) {
                if (playerIn.isCreative() && stack1.equals(stack, false)) {
                    this.remove();
                    return ActionResultType.CONSUME;
                }
            }
            this.remove();
            playerIn.addItemStackToInventory(stack);
            return ActionResultType.CONSUME;
        }
        playerIn.startRiding(this);
        return ActionResultType.SUCCESS;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.getPersistentData().getInt("Powered") == 2) {
            if (world instanceof ServerWorld) {
                if (this.isBeingRidden() && world.canBlockSeeSky(this.getPosition().down())) {
                    i++;
                    ServerPlayerEntity player = (ServerPlayerEntity) world.getClosestPlayer(this, 1);
                    if (player != null) {
                        if (i == 20) {
                            player.connection.sendPacket(new STitlePacket(5, 10, 10));
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("10").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 40) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("9").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 60) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("8").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 80) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("7").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 100) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("6").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 120) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("5").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 140) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("4").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 160) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("3").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 180) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("2").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        } else if (i == 200) {
                            player.connection.sendPacket(new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent("1").mergeStyle(TextFormatting.DARK_RED, TextFormatting.BOLD)));
                        }
                    }
                    if (i <= 200) {
                        ((ServerWorld) world).spawnParticle(ParticleTypes.POOF, this.getPosX(), this.getPosY() - 0.5, this.getPosZ(), 35, 4, 0, 0, 0.03);
                        ((ServerWorld) world).spawnParticle(ParticleTypes.POOF, this.getPosX(), this.getPosY() - 0.5, this.getPosZ(), 35, 0, 0, 4, 0.03);
                        k++;
                        if (k == 2) {
                            k = 0;
                            for (float j = 0; j <= 8; j += 0.5) {
                                ((ServerWorld) world).spawnParticle(ParticleTypes.POOF, this.getPosX() + j, this.getPosY() - 0.5, this.getPosZ() + j, 1, 0, 0, 0, 0.03);
                            }
                            for (float j = 0; j <= 8; j += 0.5) {
                                ((ServerWorld) world).spawnParticle(ParticleTypes.POOF, this.getPosX() + j, this.getPosY() - 0.5, this.getPosZ() - j, 1, 0, 0, 0, 0.03);
                            }
                            for (float j = 0; j <= 8; j += 0.5) {
                                ((ServerWorld) world).spawnParticle(ParticleTypes.POOF, this.getPosX() - j, this.getPosY() - 0.5, this.getPosZ() + j, 1, 0, 0, 0, 0.03);
                            }
                            for (float j = 0; j <= 8; j += 0.5) {
                                ((ServerWorld) world).spawnParticle(ParticleTypes.POOF, this.getPosX() - j, this.getPosY() - 0.5, this.getPosZ() - j, 1, 0, 0, 0, 0.03);
                            }
                        }
                    }
                    if (i >= 200) {
                        if (((this.getMotion().getY()) <= 0.5)) {
                            this.setMotion((this.getMotion().getX()), ((this.getMotion().getY()) + 0.1), (this.getMotion().getZ()));
                        }
                        if (((this.getMotion().getY()) >= 0.5)) {
                            this.setMotion((this.getMotion().getX()), 0.63, (this.getMotion().getZ()));
                        }
                        ((ServerWorld) world).spawnParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY() - 2.2, this.getPosZ(), 100, 0.1, 0.1, 0.1, 0.001);
                        ((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() - 3.2, this.getPosZ(), 50, 0.1, 0.1, 0.1, 0.04);
                    }
                } else {
                    i = 0;
                    this.getPersistentData().putInt("Powered", 0);
                }
            }
        }
    }

    @Override
    public boolean canCollide(Entity entity) {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() + -1.7;
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

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (amount > this.getMaxHealth()) {
            ItemStack stack = new ItemStack(getItem());
            stack.getOrCreateTag().putInt("Fuel", fuel);
            world.addEntity(new ItemEntity(world, this.getPosX(), this.getPosY(), this.getPosZ(), stack));
        }
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
}