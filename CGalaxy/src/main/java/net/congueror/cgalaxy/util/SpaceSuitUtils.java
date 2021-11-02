package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SpaceSuitUtils {
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
                ListTag tagList = stack.getOrCreateTag().getCompound("inventory").getList("Items", Constants.NBT.TAG_COMPOUND);
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
        if (temperature < 60) {
            flag.set(true);
        } else {
            deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof HeatProtectionUnitItem).forEach(itemStack -> {
                if (((HeatProtectionUnitItem) itemStack.getItem()).getEnergy(itemStack) > 0) {
                    flag.set(true);
                }
            });
        }
        return flag.get();
    }

    public static boolean hasColdProtection(LivingEntity entity, int temperature) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if (temperature > -60) {
            flag.set(true);
        } else {
            deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof ColdProtectionUnitItem).forEach(itemStack -> {
                if (((ColdProtectionUnitItem) itemStack.getItem()).getEnergy(itemStack) > 0) {
                    flag.set(true);
                }
            });
        }
        return flag.get();
    }

    public static boolean hasRadiationProtection(LivingEntity entity, float radiation) {
        AtomicBoolean flag = new AtomicBoolean(false);
        if (radiation < 100) {
            flag.set(true);
        } else {
            deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof RadiationProtectionUnitItem).forEach(itemStack -> {
                if (((RadiationProtectionUnitItem) itemStack.getItem()).getEnergy(itemStack) > 0) {
                    flag.set(true);
                }
            });
        }
        return flag.get();
    }

    public static boolean hasOxygen(LivingEntity entity) {
        boolean flag = SpaceSuitUtils.isEquipped(entity);
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicBoolean flag2 = new AtomicBoolean(false);
        SpaceSuitUtils.deserializeContents(entity).forEach(itemStack -> {
            if (itemStack.getItem() instanceof OxygenGearItem) {
                flag1.set(true);
            }
            if (itemStack.getItem() instanceof OxygenTankItem tank) {
                flag2.set(tank.getOxygen(itemStack) > 0);
            }
        });
        return flag && flag1.get() && flag2.get();
    }

    public static void drainProtection(LivingEntity entity, int temperature, float radiation) {
        List<ItemStack> stacks = SpaceSuitUtils.deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof AbstractProtectionUnitItem && ((AbstractProtectionUnitItem) itemStack.getItem()).getEnergy(itemStack) > 0).collect(Collectors.toList());
        for (ItemStack unit : stacks) {
            if (!unit.isEmpty()) {
                if (temperature > 60 && unit.getItem() instanceof HeatProtectionUnitItem item) {
                    if (entity.getPersistentData().getInt(CGalaxy.LIVING_HEAT_TICK) > 200) {
                        if (entity instanceof Player && ((Player) entity).isCreative()) {
                            return;
                        } else {
                            item.drain(unit, 1 + (temperature / 80));
                            entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                int index = -1;
                                if (iItemHandler.getStackInSlot(2).getItem() instanceof HeatProtectionUnitItem &&
                                        ((HeatProtectionUnitItem) iItemHandler.getStackInSlot(2).getItem()).getEnergy(iItemHandler.getStackInSlot(2)) > 0) {
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
                            item.drain(unit, 1 + (temperature / -80));
                            entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                int index = -1;
                                if (iItemHandler.getStackInSlot(3).getItem() instanceof ColdProtectionUnitItem &&
                                        ((ColdProtectionUnitItem) iItemHandler.getStackInSlot(3).getItem()).getEnergy(iItemHandler.getStackInSlot(3)) > 0) {
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
                            item.drain(unit, (int) (1 + (radiation / 120)));
                            entity.getItemBySlot(EquipmentSlot.CHEST).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                int index = -1;
                                if (iItemHandler.getStackInSlot(4).getItem() instanceof RadiationProtectionUnitItem &&
                                        ((RadiationProtectionUnitItem) iItemHandler.getStackInSlot(4).getItem()).getEnergy(iItemHandler.getStackInSlot(4)) > 0) {
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