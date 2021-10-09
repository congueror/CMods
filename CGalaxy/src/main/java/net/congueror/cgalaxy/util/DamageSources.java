package net.congueror.cgalaxy.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Random;

public class DamageSources extends DamageSource {
    int deathMessages;

    public DamageSources(String damageTypeIn, int deathMessages) {
        super(damageTypeIn);
        this.deathMessages = deathMessages;
    }

    public static final DamageSource NO_OXYGEN = new DamageSources("noOxygen", 4).bypassArmor();
    public static final DamageSource NO_HEAT = new DamageSources("noHeat", 2).bypassArmor();
    public static final DamageSource NO_COLD = new DamageSources("noCold", 2).bypassArmor();
    public static final DamageSource NO_RADIATION = new DamageSources("noRadiation", 2).bypassArmor();
    public static final DamageSource METEOR_HIT = new DamageSources("meteorHit", 1).setScalesWithDifficulty().setExplosion();

    @Nonnull
    @Override
    public Component getLocalizedDeathMessage(@Nonnull LivingEntity entityLivingBaseIn) {
        ArrayList<String> key = new ArrayList<>();
        Random rand = new Random();
        int num = rand.nextInt(deathMessages);
        for (int i = 0; i < deathMessages; i++) {
            key.add("death.attack." + this.msgId + i);
        }
        return new TranslatableComponent(key.get(num), entityLivingBaseIn.getDisplayName());
    }
}
