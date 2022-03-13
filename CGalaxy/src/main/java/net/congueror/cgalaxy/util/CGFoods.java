package net.congueror.cgalaxy.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

import java.io.FileOutputStream;

public class CGFoods {
    public static final FoodProperties DIAMOND_APPLE = new FoodProperties.Builder()
            .nutrition(8)
            .saturationMod(2.4F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 3200, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 4500, 1), 1.0F)
            .alwaysEat().build();
    public static final FoodProperties LUNAR_CARROT = new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(1.8f)
            .build();
}
