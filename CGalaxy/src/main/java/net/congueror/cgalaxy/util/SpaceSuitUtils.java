package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.events.OxygenCheckEvent;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.api.registry.CGEntity;
import net.congueror.cgalaxy.item.*;
import net.congueror.cgalaxy.world.CGDimensions;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
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

        List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)));
        for (Entity entity : entities) {
            CGDimensionBuilder.DimensionObject obj = CGDimensionBuilder.getObjectFromKey(level.dimension());
            if (obj != null) {
                if (entity instanceof LivingEntity le) {
                    boolean hasOxygen = SpaceSuitUtils.hasOxygen(le, obj);
                    SpaceSuitUtils.drainTanks(le);
                    SpaceSuitUtils.drainProtection(le, player.getPersistentData().getInt(CGalaxy.LIVING_TEMPERATURE), player.getPersistentData().getFloat(CGalaxy.LIVING_RADIATION));

                    AttributeMap manager = le.getAttributes();
                    Objects.requireNonNull(manager.getInstance(ForgeMod.ENTITY_GRAVITY.get())).setBaseValue(obj.getGravity());
                    entity.fallDistance = 1;
                    if (!hasOxygen) {
                        entity.hurt(DamageSources.NO_OXYGEN, 2.0f);
                    }

                    le.getPersistentData().putDouble(CGalaxy.LIVING_AIR_PRESSURE, obj.getAirPressure());
                    if (RenderingHelper.isDayTime(level)) {
                        le.getPersistentData().putInt(CGalaxy.LIVING_TEMPERATURE, obj.getDayTemp());
                    } else {
                        le.getPersistentData().putInt(CGalaxy.LIVING_TEMPERATURE, obj.getNightTemp());
                    }
                    le.getPersistentData().putFloat(CGalaxy.LIVING_RADIATION, obj.getRadiation());
                    if (!SpaceSuitUtils.hasHeatProtection(le, le.getPersistentData().getInt(CGalaxy.LIVING_TEMPERATURE))) {
                        le.hurt(DamageSources.HEAT, 3.0f);
                    }
                    if (!SpaceSuitUtils.hasColdProtection(le, le.getPersistentData().getInt(CGalaxy.LIVING_TEMPERATURE))) {
                        le.hurt(DamageSources.COLD, 3.0f);
                    }
                    if (!SpaceSuitUtils.hasRadiationProtection(le, le.getPersistentData().getFloat(CGalaxy.LIVING_RADIATION))) {
                        le.hurt(DamageSources.RADIATION, 1.0f);
                    }

                    if (obj.getRadiation() < 100) {
                        entity.getPersistentData().putInt(CGalaxy.LIVING_RADIATION_TICK, 0);
                    }
                    if (obj.getBreathable()) {
                        entity.getPersistentData().putInt(CGalaxy.LIVING_OXYGEN_TICK, 0);
                    }
                    if (le.getPersistentData().getInt(CGalaxy.LIVING_TEMPERATURE) < 60) {
                        entity.getPersistentData().putInt(CGalaxy.LIVING_HEAT_TICK, 0);
                    }
                    if (le.getPersistentData().getInt(CGalaxy.LIVING_TEMPERATURE) > -60) {
                        entity.getPersistentData().putInt(CGalaxy.LIVING_COLD_TICK, 0);
                    }
                }

                if (entity instanceof ItemEntity) {
                    //entity.setDeltaMovement(0.0D, -obj.getGravity() / 4.0D, 0.0D);
                    if (level.dimension() == CGDimensions.MOON.getDim() && entity.getPersistentData().getDouble(CGalaxy.ITEM_GRAVITY) <= 1 && entity.getDeltaMovement().y() <= -0.1) {
                        entity.getPersistentData().putDouble(CGalaxy.ITEM_GRAVITY, 2);
                        entity.setDeltaMovement((entity.getDeltaMovement().x()), ((entity.getDeltaMovement().y()) + (obj.getGravity() * 2.5)),
                                (entity.getDeltaMovement().z()));
                        entity.getPersistentData().putDouble(CGalaxy.ITEM_GRAVITY, 0);
                    }
                }
            }
        }
    }

    public static boolean isEquipped(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SpaceSuitItem &&
                entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpaceSuitItem &&
                entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SpaceSuitItem &&
                entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SpaceSuitItem;
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

    public static boolean hasHeatProtection(LivingEntity entity, int temperature) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if (entity instanceof CGEntity) {
            flag.set(((CGEntity) entity).canSurviveTemperature(temperature));
        } else if (temperature < 60) {
            flag.set(true);
        } else {
            if (entity.getPersistentData().getBoolean(CGalaxy.LIVING_PRESSURIZED)) {
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
            if (entity.getPersistentData().getBoolean(CGalaxy.LIVING_PRESSURIZED)) {
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
            if (entity.getPersistentData().getBoolean(CGalaxy.LIVING_PRESSURIZED)) {
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

            if (SpaceSuitUtils.isEquipped(entity)) {
                AtomicBoolean flag2 = new AtomicBoolean();
                AtomicBoolean flag3 = new AtomicBoolean();
                SpaceSuitUtils.deserializeContents(entity).forEach(itemStack -> {
                    if (itemStack.getItem() instanceof OxygenGearItem) {
                        flag2.set(true);
                    }
                    if (itemStack.getItem() instanceof OxygenTankItem tank) {
                        flag3.set(tank.getOxygen(itemStack) > 0);
                    }
                });
                return flag2.get() && flag3.get();
            }

            if (entity.getPersistentData().getBoolean(CGalaxy.LIVING_PRESSURIZED)) {
                return true;
            }

            OxygenCheckEvent event = new OxygenCheckEvent();
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
                    if (entity.getPersistentData().getInt(CGalaxy.LIVING_HEAT_TICK) > 200) {
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
                            entity.getPersistentData().putInt(CGalaxy.LIVING_HEAT_TICK, 0);
                        }
                    } else {
                        entity.getPersistentData().putInt(CGalaxy.LIVING_HEAT_TICK, entity.getPersistentData().getInt(CGalaxy.LIVING_HEAT_TICK) + 1);
                    }
                } else if (temperature < -60 && unit.getItem() instanceof ColdProtectionUnitItem item) {
                    if (entity.getPersistentData().getInt(CGalaxy.LIVING_COLD_TICK) > 200) {
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
                            entity.getPersistentData().putInt(CGalaxy.LIVING_COLD_TICK, 0);
                        }
                    } else {
                        entity.getPersistentData().putInt(CGalaxy.LIVING_COLD_TICK, entity.getPersistentData().getInt(CGalaxy.LIVING_COLD_TICK) + 1);
                    }
                }

                if (radiation > 100 && unit.getItem() instanceof RadiationProtectionUnitItem item) {
                    if (entity.getPersistentData().getInt(CGalaxy.LIVING_RADIATION_TICK) > 250) {
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
                            entity.getPersistentData().putInt(CGalaxy.LIVING_RADIATION_TICK, 0);
                        }
                    } else {
                        entity.getPersistentData().putInt(CGalaxy.LIVING_RADIATION_TICK, entity.getPersistentData().getInt(CGalaxy.LIVING_RADIATION_TICK) + 1);
                    }
                }
            }
        }
    }

    public static void drainTanks(LivingEntity entity) {
        ItemStack tank = SpaceSuitUtils.deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof OxygenTankItem && ((OxygenTankItem) itemStack.getItem()).getOxygen(itemStack) > 0).findFirst().orElse(ItemStack.EMPTY);
        if (!tank.isEmpty()) {
            if (entity.getPersistentData().getInt(CGalaxy.LIVING_OXYGEN_TICK) > 90) {
                if (entity instanceof Player && ((Player) entity).isCreative()) {
                    return;
                } else {
                    ((OxygenTankItem) tank.getItem()).drain(tank, 1);
                    entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                        int index = -1;
                        if (iItemHandler.getStackInSlot(0).getItem() instanceof OxygenTankItem &&
                                ((OxygenTankItem) iItemHandler.getStackInSlot(0).getItem()).getOxygen(iItemHandler.getStackInSlot(0)) > 0) {
                            index = 0;
                        } else if (iItemHandler.getStackInSlot(1).getItem() instanceof OxygenTankItem && (
                                (OxygenTankItem) iItemHandler.getStackInSlot(1).getItem()).getOxygen(iItemHandler.getStackInSlot(1)) > 0) {
                            index = 1;
                        }

                        if (iItemHandler instanceof ItemStackHandler && index > -1) {
                            ((ItemStackHandler) iItemHandler).setStackInSlot(index, tank.copy());
                        }
                    });

                    entity.getPersistentData().putInt(CGalaxy.LIVING_OXYGEN_TICK, 0);
                }
            } else {
                entity.getPersistentData().putInt(CGalaxy.LIVING_OXYGEN_TICK, entity.getPersistentData().getInt(CGalaxy.LIVING_OXYGEN_TICK) + 1);
            }
        }
    }
}