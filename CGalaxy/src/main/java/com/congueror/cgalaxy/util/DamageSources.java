package com.congueror.cgalaxy.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DamageSources extends DamageSource {
    int deathMessages;

    public DamageSources(String damageTypeIn, int deathMessages) {
        super(damageTypeIn);
        this.deathMessages = deathMessages;
    }

    public static final DamageSource NO_OXYGEN = new DamageSources("noOxygen", 4).setDamageBypassesArmor();

    @Override
    public ITextComponent getDeathMessage(LivingEntity entityLivingBaseIn) {
        ArrayList<String> key = new ArrayList<>();
        Random rand = new Random();
        int num = rand.nextInt(deathMessages);
        for (int i = 0; i < deathMessages; i++) {
            key.add("death.attack." + this.damageType + i);
        }
        return new TranslationTextComponent(key.get(num), entityLivingBaseIn.getDisplayName());
    }
}
