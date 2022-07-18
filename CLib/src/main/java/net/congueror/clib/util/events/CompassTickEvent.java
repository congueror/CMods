package net.congueror.clib.util.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class CompassTickEvent extends Event {

    ItemStack stack;
    Level level;
    Entity entity;
    int itemSlot;
    boolean isSelected;

    public CompassTickEvent(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        this.stack = stack;
        this.level = level;
        this.entity = entity;
        this.itemSlot = itemSlot;
        this.isSelected = isSelected;
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public Level getLevel() {
        return level;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getItemSlot() {
        return itemSlot;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
