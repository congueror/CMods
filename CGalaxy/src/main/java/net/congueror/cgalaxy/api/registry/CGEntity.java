package net.congueror.cgalaxy.api.registry;

public interface CGEntity {//TODO: Migrate to events?
    default boolean canBreath(CGDimensionBuilder.DimensionObject object) {
        return object.getBreathable();
    }

    default boolean canSurviveTemperature(int temperature) {
        return temperature < 60 && temperature > -60;
    }

    default boolean canSurviveRadiation(float radiation) {
        return radiation < 100;
    }
}
