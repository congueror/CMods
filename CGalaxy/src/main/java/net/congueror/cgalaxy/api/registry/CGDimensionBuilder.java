package net.congueror.cgalaxy.api.registry;

import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class CGDimensionBuilder {
    public static final ArrayList<DimensionObject> OBJECTS = new ArrayList<>();
    private final ResourceKey<Level> dim;
    private boolean breathable = true;
    private double gravity = ForgeMod.ENTITY_GRAVITY.get().getDefaultValue();
    private int dayTemp = 30;
    private int nightTemp = 10;
    private float radiation = 2.4F;
    private double airPressure = 101352.9D;
    private ResourceLocation yOverlay = new ResourceLocation(CGalaxy.MODID, "textures/gui/rocket_y_hud.png");

    public CGDimensionBuilder(ResourceKey<Level> dim) {
        this.dim = dim;
    }

    private ResourceKey<Level> getDim() {
        return dim;
    }
    private boolean getIsBreathable() {
        return breathable;
    }
    private double getGravity() {
        return gravity;
    }
    private int getDayTemp() {
        return dayTemp;
    }
    private int getNightTemp() {
        return nightTemp;
    }
    private float getRadiation() {
        return radiation;
    }
    public double getAirPressure() {
        return airPressure;
    }
    public ResourceLocation getyOverlay() {
        return yOverlay;
    }

    /**
     * Gets the dimension object that matches the given key.
     * @return A {@link DimensionObject} if key belongs to any of the registered objects, otherwise null.
     */
    @Nullable
    public static DimensionObject getObjectFromKey(ResourceKey<Level> key) {
        return OBJECTS.stream().filter(object -> object.getDim().equals(key)).findAny().orElse(null);
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
     * The gravity of the dimension in m/s^2, e.g. Earth Gravity: 9.81 m/s^2, Param: 9.81.
     */
    public CGDimensionBuilder withGravity(double gravity) {
        this.gravity = gravity / 100;
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

    /**
     * The atmospheric pressure of the dimension in Pascals(Pa)
     */
    public CGDimensionBuilder withAirPressure(double airPressure) {
        this.airPressure = airPressure;
        return this;
    }

    /**
     * The y overlay texture shown when inside a rocket.
     */
    public CGDimensionBuilder withYOverlayTexture(ResourceLocation texture) {
        this.yOverlay = texture;
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

        public double getAirPressure() {
            return builder.getAirPressure();
        }

        public ResourceLocation getYOverlayTexture() {
            return builder.getyOverlay();
        }
    }
}