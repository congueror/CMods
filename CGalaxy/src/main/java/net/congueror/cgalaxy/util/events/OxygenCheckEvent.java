package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;

public class OxygenCheckEvent extends Event {

    private boolean hasOxygen;
    private final LivingEntity entity;
    private final CGDimensionBuilder.DimensionObject obj;

    public OxygenCheckEvent(LivingEntity entity, CGDimensionBuilder.DimensionObject obj) {
        this.entity = entity;
        this.obj = obj;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public CGDimensionBuilder.DimensionObject getObj() {
        return obj;
    }

    public boolean hasOxygen() {
        return hasOxygen;
    }

    public void hasOxygen(boolean hasOxygen) {
        this.hasOxygen = hasOxygen;
    }
}
