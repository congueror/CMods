package net.congueror.cgalaxy.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Random;

public class CGDamageSource extends DamageSource {
    int deathMessages;

    public CGDamageSource(String damageTypeIn, int deathMessages) {
        super(damageTypeIn);
        this.deathMessages = deathMessages;
    }

    public static final DamageSource NO_OXYGEN = new CGDamageSource("cgalaxy.noOxygen", 4).bypassArmor();
    public static final DamageSource HEAT = new CGDamageSource("cgalaxy.heat", 2).bypassArmor();
    public static final DamageSource COLD = new CGDamageSource("cgalaxy.cold", 2).bypassArmor();
    public static final DamageSource RADIATION = new CGDamageSource("cgalaxy.radiation", 2).bypassArmor();
    public static final DamageSource METEOR_HIT = new CGDamageSource("cgalaxy.meteorHit", 1).setScalesWithDifficulty().setExplosion();

    public static DamageSource laserBlast(Entity attacker) {
        return new CGEntityDamageSource("cgalaxy.laserBlast", 2, attacker).setProjectile();
    }

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

    public static class CGEntityDamageSource extends CGDamageSource {
        private final Entity attacker;

        public CGEntityDamageSource(String damageTypeIn, int deathMessages, Entity attacker) {
            super(damageTypeIn, deathMessages);
            this.attacker = attacker;
        }

        @NotNull
        @Override
        public Component getLocalizedDeathMessage(@NotNull LivingEntity entityLivingBaseIn) {
            ItemStack itemstack = this.attacker instanceof LivingEntity ? ((LivingEntity)this.attacker).getMainHandItem() : ItemStack.EMPTY;
            ArrayList<String> key = new ArrayList<>();
            Random rand = new Random();
            int num = rand.nextInt(deathMessages);
            for (int i = 0; i < deathMessages; i++) {
                key.add("death.attack." + this.msgId + i);
            }
            return new TranslatableComponent(key.get(num), entityLivingBaseIn.getDisplayName(), this.attacker.getDisplayName(), itemstack.getDisplayName());
        }
    }
}
