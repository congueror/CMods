package com.congueror.cgalaxy.compat.curios;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class CuriosCompat {
    /**
     * Gets all items in the curio slots of the given entity. the index of an entry is the index of the curios slot. Empty slots will be skipped. See also {@link #getAllCuriosItemStacks(LivingEntity)}
     */
    public static ArrayList<Item> getAllCuriosItems(LivingEntity entity) {
        return (ArrayList<Item>) getAllCuriosItemStacks(entity).stream().map(ItemStack::getItem).collect(Collectors.toList());
    }
    
    /**
     * Gets all ItemStacks in the curio slots of the given entity. the index of an entry is the index of the curios slot. Empty slots will be skipped.
     */
    public static ArrayList<ItemStack> getAllCuriosItemStacks(LivingEntity entity) {
        return CuriosApi.getCuriosHelper().getCuriosHandler(entity).map(handler -> {
            Map<String, ICurioStacksHandler> curios = handler.getCurios();
            ArrayList<ItemStack> list = new ArrayList<>();
            for (String id : curios.keySet()) {
                ICurioStacksHandler stacksHandler = curios.get(id);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();

                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack item = stackHandler.getStackInSlot(i);
                    if (!item.isEmpty()) {
                        list.add(i, item);
                    }
                }
            }
            return list;
        }).orElse(new ArrayList<>());
    }
}
