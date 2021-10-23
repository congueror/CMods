package net.congueror.cgalaxy.api.registry;

public interface CGEntity {//TODO: Migrate to events?
    default boolean canBreath(CGDimensionBuilder.DimensionObject object) {
        return object.getBreathable();
    }
}
