package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.entities.rockets.RocketTier1Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES  = DeferredRegister.create(ForgeRegistries.ENTITIES, CGalaxy.MODID);

    public static final RegistryObject<EntityType<RocketEntity>> ROCKET_TIER_1 = ENTITY_TYPES.register("rocket_tier_1", () ->
            EntityType.Builder.<RocketEntity>create(RocketTier1Entity::new, EntityClassification.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .setTrackingRange(100)
                    .setUpdateInterval(3)
                    .size(1f, 3f)
                    .build(new ResourceLocation(CGalaxy.MODID, "rocket_tier_1").toString()));
}
