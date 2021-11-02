package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.AstroEnderman;
import net.congueror.cgalaxy.entity.AstroZombie;
import net.congueror.cgalaxy.entity.rockets.RocketTier1;
import net.congueror.cgalaxy.entity.villagers.LunarVillager;
import net.congueror.cgalaxy.entity.villagers.LunarZombieVillager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGEntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, CGalaxy.MODID);

    public static final RegistryObject<EntityType<RocketTier1>> ROCKET_TIER_1 = ENTITY_TYPES.register("rocket_tier_1", () ->
            EntityType.Builder.of(RocketTier1::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(100)
                    .setUpdateInterval(3)
                    .sized(1f, 3f)
                    .build(new ResourceLocation(CGalaxy.MODID, "rocket_tier_1").toString()));

    public static final RegistryObject<EntityType<AstroEnderman>> ASTRO_ENDERMAN = ENTITY_TYPES.register("astro_enderman", () ->
            EntityType.Builder.of(AstroEnderman::new, MobCategory.MONSTER)
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(CGalaxy.MODID, "astro_enderman").toString()));
    public static final RegistryObject<EntityType<AstroZombie>> ASTRO_ZOMBIE = ENTITY_TYPES.register("astro_zombie", () ->
            EntityType.Builder.of(AstroZombie::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build(new ResourceLocation(CGalaxy.MODID, "astro_zombie").toString()));

    public static final RegistryObject<EntityType<LunarVillager>> LUNAR_VILLAGER = ENTITY_TYPES.register("lunar_villager", () ->
            EntityType.Builder.of(LunarVillager::new, MobCategory.AMBIENT)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(CGalaxy.MODID, "lunar_villager").toString()));

    public static final RegistryObject<EntityType<LunarZombieVillager>> LUNAR_ZOMBIE_VILLAGER = ENTITY_TYPES.register("lunar_zombie_villager", () ->
            EntityType.Builder.of(LunarZombieVillager::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f)
                    .clientTrackingRange(10)
                    .build(new ResourceLocation(CGalaxy.MODID, "lunar_zombie_villager").toString()));
}
