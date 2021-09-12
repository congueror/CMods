package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.RocketEntity;
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
}
