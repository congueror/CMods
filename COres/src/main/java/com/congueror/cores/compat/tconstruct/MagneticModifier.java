package com.congueror.cores.compat.tconstruct;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;

public class MagneticModifier extends Modifier {

    public MagneticModifier() {
        super(0xFF0000);
    }

    /**
     * Called when the stack updates in the player inventory
     *
     * @param tool          Current tool instance
     * @param level         Modifier level
     * @param world         World containing tool
     * @param holder        Entity holding tool
     * @param itemSlot      Slot containing this tool
     * @param isSelected    If true, this item is currently in the player's main hand
     * @param isCorrectSlot If true, this item is in the proper slot. For tools, that is main hand or off hand. For armor, this means its in the correct armor slot
     * @param stack         Item stack instance to check other slots for the tool. Do not modify
     */
    @Override
    public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isSelected && tool.getCurrentDurability() > 0) {
            int r = level + 2;
            AxisAlignedBB area = new AxisAlignedBB(holder.getPositionVec().add(-r, -r, -r), holder.getPositionVec().add(r, r, r));
            List<ItemEntity> items = world.getEntitiesWithinAABB(EntityType.ITEM, area, itemEntity -> !itemEntity.cannotPickup());
            for (ItemEntity item : items) {
                if (!item.cannotPickup()) {
                    item.setPosition(holder.getPosX(), holder.getPosY(), holder.getPosZ());
                    if (!tool.isUnbreakable()) {
                        tool.setDamage(tool.getDamage() + 2);//TODO: Use Energy instead
                    }
                }
            }
        }
    }
}