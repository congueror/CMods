package com.congueror.cgalaxy.entities.rocket_entity;

import com.congueror.cgalaxy.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class RocketEntity extends CreatureEntity {

    int fuel;
    int fuel_capacity;

    int i;

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

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Fuel", getFuel());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        compound.getInt("Fuel");
    }

    @Override
    protected ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        super.getEntityInteractionResult(playerIn, hand);
        if (playerIn.isSneaking()) {
            if (playerIn.isCreative() && playerIn.inventory.hasItemStack(new ItemStack(ItemInit.ROCKET_TIER_1.get()))) {
                this.remove();
                return ActionResultType.CONSUME;
            }
            ItemStack stack = new ItemStack(ItemInit.ROCKET_TIER_1.get());
            stack.getOrCreateTag().putInt("Fuel", fuel);
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
        if (this.getPersistentData().getBoolean("Powered")) {
            if (world instanceof ServerWorld) {
                if (this.isBeingRidden()) {
                    i++;
                    {
                        if (i == 20) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p times 5 10 10");
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"10\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 40) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"9\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 60) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"8\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 80) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"7\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 100) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"6\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 120) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"5\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 140) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"4\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 160) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"3\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 180) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"2\",\"color\":\"red\",\"bold\":\"false\"}");
                        } else if (i == 200) {
                            world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                    , new Vector3d(getPosX()
                                    , getPosY(), getPosZ())
                                    , Vector2f.ZERO
                                    , (ServerWorld) world
                                    , 4
                                    , ""
                                    , new StringTextComponent("")
                                    , world.getServer(), null).withFeedbackDisabled(), "/title @p title {\"text\":\"1\",\"color\":\"red\",\"bold\":\"false\"}");
                        }
                    }
                    if (i <= 200) {
                        world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                , new Vector3d(getPosX()
                                , getPosY(), getPosZ())
                                , Vector2f.ZERO
                                , (ServerWorld) world
                                , 4
                                , ""
                                , new StringTextComponent("")
                                , world.getServer(), null).withFeedbackDisabled(), "/particle minecraft:campfire_cosy_smoke ~ ~-0.5 ~ 0.1 .1 0.1 .013 6 force");
                    }
                    if (i >= 200) {
                        if (((this.getMotion().getY()) <= 0.5)) {
                            this.setMotion((this.getMotion().getX()), ((this.getMotion().getY()) + 0.1), (this.getMotion().getZ()));
                        }
                        if (((this.getMotion().getY()) >= 0.5)) {
                            this.setMotion((this.getMotion().getX()), 0.63, (this.getMotion().getZ()));
                        }
                        world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                , new Vector3d(getPosX()
                                , getPosY(), getPosZ())
                                , Vector2f.ZERO
                                , (ServerWorld) world
                                , 4
                                , ""
                                , new StringTextComponent("")
                                , world.getServer(), null).withFeedbackDisabled(), "/particle minecraft:flame ~ ~-2.2 ~ .1 .1 .1 .001 100 force");
                        world.getServer().getCommandManager().handleCommand(new CommandSource(ICommandSource.DUMMY
                                , new Vector3d(getPosX()
                                , getPosY(), getPosZ())
                                , Vector2f.ZERO
                                , (ServerWorld) world
                                , 4
                                , ""
                                , new StringTextComponent("")
                                , world.getServer(), null).withFeedbackDisabled(), "/particle minecraft:smoke ~ ~-3.2 ~ .1 .1 .1 .04 50 force");
                    }
                } else {
                    this.getPersistentData().putBoolean("Powered", false);
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
}