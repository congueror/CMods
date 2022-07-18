package net.congueror.cgalaxy.util.registry;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.json_managers.DimensionManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class CGDimensionBuilder {
    @Nonnull
    private final ResourceKey<Level> dim;
    private boolean breathable = true;
    private double gravity = ForgeMod.ENTITY_GRAVITY.get().getDefaultValue();
    private int dayTemp = 30;
    private int nightTemp = 10;
    private float radiation = 2.4F;
    private double airPressure = 101352.9D;
    @Nonnull
    private ResourceLocation yOverlay = new ResourceLocation(CGalaxy.MODID, "textures/gui/rocket_y_hud.png");
    private boolean isOrbit;
    @Nullable
    private ResourceKey<Biome> south;

    public CGDimensionBuilder(@NotNull ResourceKey<Level> dim) {
        this.dim = dim;
    }

    public final DimensionObject build() {
        DimensionObject obj = new DimensionObject(this);
        DimensionManager.OBJECTS.add(obj);
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
     * The y overlay modTexture shown when inside a rocket.
     */
    public CGDimensionBuilder withYOverlayTexture(ResourceLocation texture) {
        this.yOverlay = texture;
        return this;
    }

    /**
     * Whether this dimension is an orbit of another dimension.
     */
    public CGDimensionBuilder isOrbit(boolean orbit) {
        this.isOrbit = orbit;
        return this;
    }

    public CGDimensionBuilder withSouthBiome(ResourceKey<Biome> biome) {
        this.south = biome;
        return this;
    }

    public static class DimensionObject {
        private final CGDimensionBuilder builder;

        public DimensionObject(CGDimensionBuilder builder) {
            this.builder = builder;
        }

        @Nonnull
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

        public double getAirPressure() {
            return builder.airPressure;
        }

        @Nonnull
        public ResourceLocation getYOverlayTexture() {
            return builder.yOverlay;
        }

        public boolean isOrbit() {
            return builder.isOrbit;
        }

        @Nullable
        public ResourceKey<Biome> getSouth() {
            return builder.south;
        }

        public boolean isRadiationSafe() {
            return getRadiation() < 100;
        }

        public boolean isDayTemperatureSafe() {
            return getDayTemp() < 60 && getDayTemp() > -60;
        }

        public boolean isNightTemperatureSafe() {
            return getNightTemp() < 60 && getNightTemp() > -60;
        }
    }
}