package net.congueror.cgalaxy.world;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.world.WorldHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.ArrayList;

public class Dimensions {
    public static final DimensionObject MOON = new CGDimensionBuilder(WorldHelper.registerDim(CGalaxy.MODID, "moon"))
            .withGravity(0.0162)
            .withBreathableAtmosphere(false)
            .withDayTemperature(127)
            .withNightTemperature(-173).build();

    /**
     * Called from an {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent#enqueueWork(Runnable)}
     */
    public static void registerDimensions() {}

    public static class CGDimensionBuilder {//TODO: Docs
        private final ResourceKey<Level> dim;
        public static final ArrayList<DimensionObject> OBJECTS = new ArrayList<>();
        private boolean breathable = true;
        private double gravity = ForgeMod.ENTITY_GRAVITY.get().getDefaultValue();
        private int dayTemp = 30;
        private int nightTemp = 10;
        private float radiation = 0.62F;

        public CGDimensionBuilder(ResourceKey<Level> dim) {
            this.dim = dim;
        }

        public final DimensionObject build() {
            DimensionObject obj = new DimensionObject(this);
            OBJECTS.add(obj);
            return obj;
        }

        public CGDimensionBuilder withBreathableAtmosphere(boolean breathable) {
            this.breathable = breathable;
            return this;
        }

        public CGDimensionBuilder withGravity(double gravity) {
            this.gravity = gravity;
            return this;
        }

        public CGDimensionBuilder withDayTemperature(int celsius) {
            this.dayTemp = celsius;
            return this;
        }

        public CGDimensionBuilder withNightTemperature(int celsius) {
            this.nightTemp = celsius;
            return this;
        }

        public CGDimensionBuilder withRadiation(float radiation) {
            this.radiation = radiation;
            return this;
        }
    }

    public static class DimensionObject {
        private final CGDimensionBuilder builder;

        public DimensionObject(CGDimensionBuilder builder) {
            this.builder = builder;
        }

        public ResourceKey<Level> getDim() {
            return builder.dim;
        }

        public boolean getBreathable() {
            return builder.breathable;
        }

        public double getGravity() {
            return builder.gravity;
        }

        public int getDayTemp() {
            return builder.dayTemp;
        }

        public int getNightTemp() {
            return builder.nightTemp;
        }

        public float getRadiation() {
            return builder.radiation;
        }
    }
}
