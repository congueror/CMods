package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.AstroEndermanEntity;
import net.congueror.cgalaxy.entity.AstroZombieEntity;
import net.congueror.cgalaxy.entity.rockets.RocketTier1Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGEntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, CGalaxy.MODID);

    public static final RegistryObject<EntityType<RocketTier1Entity>> ROCKET_TIER_1 = ENTITY_TYPES.register("rocket_tier_1", () ->
            EntityType.Builder.of(RocketTier1Entity::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(100)
                    .setUpdateInterval(3)
                    .sized(1f, 3f)
                    .build(new ResourceLocation(CGalaxy.MODID, "rocket_tier_1").toString()));

    public static final RegistryObject<EntityType<AstroEndermanEntity>> ASTRO_ENDERMAN = ENTITY_TYPES.register("astro_enderman", () ->
            EntityType.Builder.of(AstroEndermanEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(CGalaxy.MODID, "astro_enderman").toString()));
    public static final RegistryObject<EntityType<AstroZombieEntity>> ASTRO_ZOMBIE = ENTITY_TYPES.register("astro_zombie", () ->
            EntityType.Builder.of(AstroZombieEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(CGalaxy.MODID, "astro_zombie").toString()));
}
