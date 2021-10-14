package net.congueror.cgalaxy.api.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.ArrayList;

public class CGDimensionBuilder {
    public static final ArrayList<DimensionObject> OBJECTS = new ArrayList<>();
    protected final ResourceKey<Level> dim;
    private boolean breathable = true;
    private double gravity = ForgeMod.ENTITY_GRAVITY.get().getDefaultValue();
    private int dayTemp = 30;
    private int nightTemp = 10;
    private float radiation = 2.4F;

    public CGDimensionBuilder(ResourceKey<Level> dim) {
        this.dim = dim;
    }

    public ResourceKey<Level> getDim() {
        return dim;
    }

    public boolean getIsBreathable() {
        return breathable;
    }

    public double getGravity() {
        return gravity;
    }

    public int getDayTemp() {
        return dayTemp;
    }

    public int getNightTemp() {
        return nightTemp;
    }

    public float getRadiation() {
        return radiation;
    }

    public final DimensionObject build() {
        DimensionObject obj = new DimensionObject(this);
        OBJECTS.add(obj);
        return obj;
    }

    /**
     * Whether you need oxygen equipment to breath in the dimension.
     */
    public CGDimensionBuilder withBreathableAtmosphere(boolean breathable) {
        this.breathable = breathable;
        return this;
    }

    /**
     * The gravity of the dimension in m/s^2, divided by 100, e.g. Earth Gravity: 9.81 m/s^2, Parameter: 9.81 / 100 = 0.0981.
     */
    public CGDimensionBuilder withGravity(double gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * The temperature at daytime in celsius.
     */
    public CGDimensionBuilder withDayTemperature(int celsius) {
        this.dayTemp = celsius;
        return this;
    }

    /**
     * The temperature at nighttime in celsius.
     */
    public CGDimensionBuilder withNightTemperature(int celsius) {
        this.nightTemp = celsius;
        return this;
    }

    /**
     * The annual radiation one is exposed to in the environment, measured in mSv
     */
    public CGDimensionBuilder withRadiation(float radiation) {
        this.radiation = radiation;
        return this;
    }

    public static class DimensionObject {
        private final CGDimensionBuilder builder;

        public DimensionObject(CGDimensionBuilder builder) {
            this.builder = builder;
        }

        public ResourceKey<Level> getDim() {
            return builder.getDim();
        }

        public boolean getBreathable() {
            return builder.getIsBreathable();
        }

        public double getGravity() {
            return builder.getGravity();
        }

        public int getDayTemp() {
            return builder.getDayTemp();
        }

        public int getNightTemp() {
            return builder.getNightTemp();
        }

        public float getRadiation() {
            return builder.getRadiation();
        }
    }
}