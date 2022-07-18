package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.items.space_suit.*;
import net.congueror.cgalaxy.util.events.OxygenCheckEvent;
import net.congueror.cgalaxy.util.json_managers.DimensionManager;
import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.util.registry.CGEntity;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SpaceSuitUtils {

    public static void tickPlayer(TickEvent.PlayerTickEvent e) {
        Player player = e.player;
        Level level = player.getCommandSenderWorld();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        CGDimensionBuilder.DimensionObject obj = DimensionManager.getObjectFromKey(level.dimension());
        if (obj != null) {
            if (obj.isOrbit()) {
                if (y < 0 && !level.isClientSide) {
                    ServerLevel world = Objects.requireNonNull(player.level.getServer()).getLevel(Level.OVERWORLD);
                    ((ServerPlayer) player).teleportTo(world, x, 600, z, 0, 0);
                }

                if (!player.isCreative()) {
                    if (!isPressurized(player)) {
                        if (player.tickCount % 90 == 0) {
                            player.setDeltaMovement(0.01, player.getDeltaMovement().y, player.getDeltaMovement().z);
                        }
                    }
                }
                if (player.isCrouching()) {
                    player.setDeltaMovement(player.getDeltaMovement().x, -0.05, player.getDeltaMovement().z);
                }
                if (player.jumping) {
                    player.setDeltaMovement(player.getDeltaMovement().x, 0.05, player.getDeltaMovement().z);
                }
            }

            //noinspection ConstantConditions
            int temperature = getTemperature(level);
            float radiation = obj.getRadiation();
            double gravity = obj.getGravity();

            List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(x - 96, y - 96, z - 96, x + 96, y + 96, z + 96));
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity le) {
                    boolean hasOxygen = SpaceSuitUtils.hasOxygen(le, obj);
                    SpaceSuitUtils.drainTanks(le);
                    SpaceSuitUtils.drainProtection(le, temperature, radiation);

                    AttributeInstance instance = le.getAttributes().getInstance(ForgeMod.ENTITY_GRAVITY.get());
                    if (instance.getBaseValue() != gravity)
                        instance.setBaseValue(gravity);

                    if (!hasOxygen) {
                        entity.hurt(CGDamageSource.NO_OXYGEN, 2.0f);
                    }
                    if (!SpaceSuitUtils.hasHeatProtection(le, temperature)) {
                        le.hurt(CGDamageSource.HEAT, 3.0f);
                    }
                    if (!SpaceSuitUtils.hasColdProtection(le, temperature)) {
                        le.hurt(CGDamageSource.COLD, 3.0f);
                    }
                    if (!SpaceSuitUtils.hasRadiationProtection(le, radiation)) {
                        le.hurt(CGDamageSource.RADIATION, 1.0f);
                    }

                    if (obj.getRadiation() < 100) {
                        setRadiationTick(le, 0);
                    }
                    if (obj.getBreathable()) {
                        setOxygenTick(le, 0);
                    }
                    if (temperature < 60) {
                        setHeatTick(le, 0);
                    }
                    if (temperature > -60) {
                        setColdTick(le, 0);
                    }
                } else if (entity instanceof ItemEntity ie) {
                    ie.setDeltaMovement(ie.getDeltaMovement().multiply(1, obj.getGravity() / 0.08d, 1));

                    //ie.setDeltaMovement(ie.getDeltaMovement().add(0, -obj.getGravity() / 2d, 0));
                    //ie.setNoGravity(true);
                }
            }
        } else {
            AttributeInstance instance = player.getAttributes().getInstance(ForgeMod.ENTITY_GRAVITY.get());
            if (instance.getBaseValue() != instance.getAttribute().getDefaultValue())
                instance.setBaseValue(instance.getAttribute().getDefaultValue());
        }
    }

    public static boolean isEquipped(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SpaceSuitItem &&
                entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpaceSuitItem &&
                entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SpaceSuitItem &&
                entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SpaceSuitItem;
    }

    public static boolean upgradeSpaceSuit(LivingEntity entity, SpaceSuitUpgradeItem upgrade) {
        return ((SpaceSuitItem) entity.getItemBySlot(EquipmentSlot.HEAD).getItem()).upgradeArmor(entity.getItemBySlot(EquipmentSlot.HEAD), upgrade) &&
                ((SpaceSuitItem) entity.getItemBySlot(EquipmentSlot.CHEST).getItem()).upgradeArmor(entity.getItemBySlot(EquipmentSlot.CHEST), upgrade) &&
                ((SpaceSuitItem) entity.getItemBySlot(EquipmentSlot.LEGS).getItem()).upgradeArmor(entity.getItemBySlot(EquipmentSlot.LEGS), upgrade) &&
                ((SpaceSuitItem) entity.getItemBySlot(EquipmentSlot.FEET).getItem()).upgradeArmor(entity.getItemBySlot(EquipmentSlot.FEET), upgrade);
    }

    @Nullable
    public static Integer getTemperature(Level level) {
        CGDimensionBuilder.DimensionObject obj = DimensionManager.getObjectFromKey(level.dimension());
        return obj != null ? (RenderingHelper.isDayTime(level) ? obj.getDayTemp() : obj.getNightTemp()) : null;
    }

    @Nullable
    public static Float getRadiation(Level level) {
        CGDimensionBuilder.DimensionObject obj = DimensionManager.getObjectFromKey(level.dimension());
        return obj != null ? obj.getRadiation() : null;
    }

    @Deprecated
    public static double getAirPressure(LivingEntity entity) {
        return entity.getPersistentData().getDouble("AirPressure");
    }

    public static int getHeatTick(LivingEntity entity) {
        return entity.getPersistentData().getInt("HeatTick");
    }

    public static void setHeatTick(LivingEntity entity, int amount) {
        entity.getPersistentData().putInt("HeatTick", amount);
    }

    public static int getColdTick(LivingEntity entity) {
        return entity.getPersistentData().getInt("ColdTick");
    }

    public static void setColdTick(LivingEntity entity, int amount) {
        entity.getPersistentData().putInt("ColdTick", amount);
    }

    public static int getOxygenTick(LivingEntity entity) {
        return entity.getPersistentData().getInt("OxygenTick");
    }

    public static void setOxygenTick(LivingEntity entity, int amount) {
        entity.getPersistentData().putInt("OxygenTick", amount);
    }

    public static int getRadiationTick(LivingEntity entity) {
        return entity.getPersistentData().getInt("RadiationTick");
    }

    public static void setRadiationTick(LivingEntity entity, int amount) {
        entity.getPersistentData().putInt("RadiationTick", amount);
    }

    public static boolean isPressurized(LivingEntity entity) {
        return entity.getPersistentData().getBoolean("Pressurized");
    }

    public static void setPressurized(LivingEntity entity, boolean pressurized) {
        entity.getPersistentData().putBoolean("Pressurized", pressurized);
    }

    public static ArrayList<ItemStack> deserializeContents(LivingEntity entity) {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            if (equipmentSlot.equals(EquipmentSlot.MAINHAND) || equipmentSlot.equals(EquipmentSlot.OFFHAND)) {
                continue;
            }
            if (isEquipped(entity)) {
                ItemStack stack = entity.getItemBySlot(equipmentSlot);
                ListTag tagList = stack.getOrCreateTag().getCompound("inventory").getList("Items", Tag.TAG_COMPOUND);
                for (int i = 0; i < tagList.size(); i++) {
                    CompoundTag itemTags = tagList.getCompound(i);
                    stacks.add(ItemStack.of(itemTags));
                }
            }
        }
        return stacks;
    }

    public static ItemStack[] getTanks(LivingEntity entity) {
        return deserializeContents(entity).stream().filter(stack -> stack.getItem() instanceof OxygenTankItem).toArray(ItemStack[]::new);
    }

    public static ItemStack getFirstNonEmptyTank(LivingEntity entity) {
        return deserializeContents(entity).stream().filter(stack -> stack.getItem() instanceof OxygenTankItem && ((OxygenTankItem) stack.getItem()).getFluid(stack).getAmount() > 0).findFirst().orElse(ItemStack.EMPTY);
    }

    public static boolean hasHeatProtection(LivingEntity entity, int temperature) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if (entity instanceof CGEntity) {
            flag.set(((CGEntity) entity).canSurviveTemperature(temperature));
        } else if (temperature < 60) {
            flag.set(true);
        } else {
            if (isPressurized(entity)) {
                flag.set(true);
            } else {
                deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof HeatProtectionUnitItem).forEach(itemStack -> {
                    if (((HeatProtectionUnitItem) itemStack.getItem()).getEnergyStored(itemStack) > 0) {
                        flag.set(true);
                    }
                });
            }
        }
        return flag.get();
    }

    public static boolean hasColdProtection(LivingEntity entity, int temperature) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if (entity instanceof CGEntity) {
            flag.set(((CGEntity) entity).canSurviveTemperature(temperature));
        } else if (temperature > -60) {
            flag.set(true);
        } else {
            if (isPressurized(entity)) {
                flag.set(true);
            } else {
                deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof ColdProtectionUnitItem).forEach(itemStack -> {
                    if (((ColdProtectionUnitItem) itemStack.getItem()).getEnergyStored(itemStack) > 0) {
                        flag.set(true);
                    }
                });
            }
        }
        return flag.get();
    }

    public static boolean hasRadiationProtection(LivingEntity entity, float radiation) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if (entity instanceof CGEntity) {
            flag.set(((CGEntity) entity).canSurviveRadiation(radiation));
        } else if (radiation < 100) {
            flag.set(true);
        } else {
            if (isPressurized(entity)) {
                flag.set(true);
            } else {
                deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof RadiationProtectionUnitItem).forEach(itemStack -> {
                    if (((RadiationProtectionUnitItem) itemStack.getItem()).getEnergyStored(itemStack) > 0) {
                        flag.set(true);
                    }
                });
            }
        }
        return flag.get();
    }

    public static boolean hasOxygen(LivingEntity entity, CGDimensionBuilder.DimensionObject obj) {
        if (!obj.getBreathable()) {
            if (entity instanceof CGEntity i) {
                return i.canBreath(obj);
            }

            if (isPressurized(entity)) {
                return true;
            }

            if (SpaceSuitUtils.isEquipped(entity)) {
                AtomicBoolean flag2 = new AtomicBoolean();
                AtomicBoolean flag3 = new AtomicBoolean();
                SpaceSuitUtils.deserializeContents(entity).forEach(itemStack -> {
                    if (itemStack.getItem() instanceof OxygenGearItem) {
                        flag2.set(true);
                    }
                    if (itemStack.getItem() instanceof OxygenTankItem tank) {
                        flag3.set(tank.getFluid(itemStack).getAmount() > 0);
                    }
                });
                return flag2.get() && flag3.get();
            }

            OxygenCheckEvent event = new OxygenCheckEvent(entity, obj);
            MinecraftForge.EVENT_BUS.post(event);
            return event.hasOxygen();
        }
        return obj.getBreathable();
    }

    public static void drainProtection(LivingEntity entity, int temperature, float radiation) {
        List<ItemStack> stacks = SpaceSuitUtils.deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof AbstractProtectionUnitItem && ((AbstractProtectionUnitItem) itemStack.getItem()).getEnergyStored(itemStack) > 0).collect(Collectors.toList());
        for (ItemStack unit : stacks) {
            if (!unit.isEmpty()) {
                if (temperature > 60 && unit.getItem() instanceof HeatProtectionUnitItem item) {
                    if (getHeatTick(entity) > 200) {
                        if (entity instanceof Player && ((Player) entity).isCreative()) {
                            return;
                        } else {
                            item.extractEnergy(unit, 1 + (temperature / 80), false);
                            entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                int index = -1;
                                if (iItemHandler.getStackInSlot(2).getItem() instanceof HeatProtectionUnitItem &&
                                        ((HeatProtectionUnitItem) iItemHandler.getStackInSlot(2).getItem()).getEnergyStored(iItemHandler.getStackInSlot(2)) > 0) {
                                    index = 2;
                                }
                                if (iItemHandler instanceof ItemStackHandler && index > -1) {
                                    ((ItemStackHandler) iItemHandler).setStackInSlot(index, unit.copy());
                                }
                            });
                            setHeatTick(entity, 0);
                        }
                    } else {
                        setHeatTick(entity, getHeatTick(entity) + 1);
                    }
                } else if (temperature < -60 && unit.getItem() instanceof ColdProtectionUnitItem item) {
                    if (getColdTick(entity) > 200) {
                        if (entity instanceof Player && ((Player) entity).isCreative()) {
                            return;
                        } else {
                            item.extractEnergy(unit, 1 + (temperature / -80), false);
                            entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                int index = -1;
                                if (iItemHandler.getStackInSlot(3).getItem() instanceof ColdProtectionUnitItem &&
                                        ((ColdProtectionUnitItem) iItemHandler.getStackInSlot(3).getItem()).getEnergyStored(iItemHandler.getStackInSlot(3)) > 0) {
                                    index = 3;
                                }
                                if (iItemHandler instanceof ItemStackHandler && index > -1) {
                                    ((ItemStackHandler) iItemHandler).setStackInSlot(index, unit.copy());
                                }
                            });
                            setColdTick(entity, 0);
                        }
                    } else {
                        setColdTick(entity, getColdTick(entity) + 1);
                    }
                }

                if (radiation > 100 && unit.getItem() instanceof RadiationProtectionUnitItem item) {
                    if (getRadiationTick(entity) > 250) {
                        if (entity instanceof Player && ((Player) entity).isCreative()) {
                            return;
                        } else {
                            item.extractEnergy(unit, (int) (1 + (radiation / 120)), false);
                            entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                int index = -1;
                                if (iItemHandler.getStackInSlot(4).getItem() instanceof RadiationProtectionUnitItem &&
                                        ((RadiationProtectionUnitItem) iItemHandler.getStackInSlot(4).getItem()).getEnergyStored(iItemHandler.getStackInSlot(4)) > 0) {
                                    index = 4;
                                }
                                if (iItemHandler instanceof ItemStackHandler && index > -1) {
                                    ((ItemStackHandler) iItemHandler).setStackInSlot(index, unit.copy());
                                }
                            });
                            setRadiationTick(entity, 0);
                        }
                    } else {
                        setRadiationTick(entity, getRadiationTick(entity) + 1);
                    }
                }
            }
        }
    }

    public static void drainTanks(LivingEntity entity) {
        ItemStack tank = getFirstNonEmptyTank(entity);
        if (!tank.isEmpty()) {
            if (getOxygenTick(entity) > 90) {
                if (entity instanceof Player && ((Player) entity).isCreative()) {
                } else {
                    ((OxygenTankItem) tank.getItem()).drain(tank, 1);
                    entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                        int index = -1;
                        if (iItemHandler.getStackInSlot(0).getItem() instanceof OxygenTankItem &&
                                ((OxygenTankItem) iItemHandler.getStackInSlot(0).getItem()).getFluid(iItemHandler.getStackInSlot(0)).getAmount() > 0) {
                            index = 0;
                        } else if (iItemHandler.getStackInSlot(1).getItem() instanceof OxygenTankItem && (
                                (OxygenTankItem) iItemHandler.getStackInSlot(1).getItem()).getFluid(iItemHandler.getStackInSlot(1)).getAmount() > 0) {
                            index = 1;
                        }

                        if (iItemHandler instanceof ItemStackHandler && index > -1) {
                            ((ItemStackHandler) iItemHandler).setStackInSlot(index, tank.copy());
                        }
                    });

                    setOxygenTick(entity, 0);
                }
            } else {
                setOxygenTick(entity, getOxygenTick(entity) + 1);
            }
        }
    }
}