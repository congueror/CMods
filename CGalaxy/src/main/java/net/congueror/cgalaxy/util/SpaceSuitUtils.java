package net.congueror.cgalaxy.util;

import net.congueror.cgalaxy.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
        deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof HeatProtectionUnitItem).forEach(itemStack -> {
            if (temperature < 60) {
                flag.set(true);
            } else if (((HeatProtectionUnitItem) itemStack.getItem()).getAmount(itemStack) > 0) {
                flag.set(true);
            }
        });
        return flag.get();
    }

    public static boolean hasColdProtection(LivingEntity entity, int temperature) {
        AtomicBoolean flag = new AtomicBoolean(false);
        deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof ColdProtectionUnitItem).forEach(itemStack -> {
            if (temperature > -60) {
                flag.set(true);
            } else if (((ColdProtectionUnitItem) itemStack.getItem()).getAmount(itemStack) > 0) {
                flag.set(true);
            }
        });
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

    public static void drainTanks(LivingEntity entity) {
        ItemStack tank = SpaceSuitUtils.deserializeContents(entity).stream().filter(itemStack -> itemStack.getItem() instanceof OxygenTankItem && ((OxygenTankItem) itemStack.getItem()).getOxygen(itemStack) > 0).findFirst().orElse(ItemStack.EMPTY);
        if (!tank.isEmpty()) {
            if (entity.getPersistentData().getInt("Oxygen") > 80) {
                if (entity instanceof Player && ((Player) entity).isCreative()) {
                    return;
                } else {
                    ((OxygenTankItem) tank.getItem()).drain(tank, 1);
                    entity.getPersistentData().putInt("Oxygen", 0);
                }
            } else {
                entity.getPersistentData().putInt("Oxygen", entity.getPersistentData().getInt("Oxygen") + 1);
            }
        }
    }
}
