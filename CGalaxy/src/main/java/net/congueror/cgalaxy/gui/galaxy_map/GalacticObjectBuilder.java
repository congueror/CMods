package net.congueror.cgalaxy.gui.galaxy_map;

import java.util.HashMap;
import java.util.Map;

public abstract class GalacticObjectBuilder<T extends GalacticObjectBuilder<T>> {

    public final String name;
    private String diameter;
    private double age;

    private String atmosphere;
    private int moons;
    private double gravity;
    private int tier;

    public static final Map<GalacticObject<?>, GalacticObject<?>> OBJECTS = new HashMap<>();

    public GalacticObjectBuilder(String name) {
        this.name = name;
    }//TODO: Translation key generation.

    public GalacticObject<T> build() {
        GalacticObject<T> obj = new GalacticObject<>(self());
        if (self() instanceof Galaxy) {
            OBJECTS.put(obj, null);
        } else if (self() instanceof SolarSystem system) {
            OBJECTS.put(obj, system.galaxy);
        } else if (self() instanceof Planet planet) {
            OBJECTS.put(obj, planet.solarSystem);
        } else if (self() instanceof Moon moon) {
            OBJECTS.put(obj, moon.planet);
        } else if (self() instanceof MoonMoon moonMoon) {
            OBJECTS.put(obj, moonMoon.moon);
        }
        return obj;
    }

    public String getName() {
        return name;
    }
    public String getDiameter() {
        return diameter;
    }
    public double getAge() {
        return age;
    }
    public String getAtmosphere() {
        return atmosphere;
    }
    public int getMoons() {
        return moons;
    }
    public double getGravity() {
        return gravity;
    }
    public int getTier() {
        return tier;
    }

    public T withDiameter(double diameter, String unit) {
        this.diameter = diameter + unit;
        return self();
    }

    public T withAge(double age) {
        this.age = age;
        return self();
    }

    public T withAtmosphere(String atmosphere) {
        this.atmosphere = atmosphere;
        return self();
    }

    public T withMoons(int moons) {
        this.moons = moons;
        return self();
    }

    public T withGravity(double gravity) {
        this.gravity = gravity;
        return self();
    }

    public T withTier(int tier) {
        this.tier = tier;
        return self();
    }

    @SuppressWarnings("unchecked")
    final T self() {
        return (T) this;
    }

    public static class Galaxy extends GalacticObjectBuilder<Galaxy> {

        private double stars;

        public Galaxy(String name) {
            super(name);
        }

        public double getStars() {
            return stars;
        }

        public Galaxy withStars(double stars) {
            this.stars = stars;
            return this;
        }
    }

    public static class SolarSystem extends GalacticObjectBuilder<SolarSystem> {

        public final GalacticObject<Galaxy> galaxy;
        private int celestialObjects;

        public SolarSystem(String name, GalacticObject<Galaxy> galaxy) {
            super(name);
            this.galaxy = galaxy;
        }

        public int getCelestialObjects() {
            return celestialObjects;
        }

        public SolarSystem withCelestialObjects(int celestialObjects) {
            this.celestialObjects = celestialObjects;
            return this;
        }
    }

    public static class Planet extends GalacticObjectBuilder<Planet> {

        public final GalacticObject<SolarSystem> solarSystem;

        public Planet(String name, GalacticObject<SolarSystem> solarSystem) {
            super(name);
            this.solarSystem = solarSystem;
        }

        @Override
        public double getAge() {
            return solarSystem.getAge();
        }
    }

    public static class Moon extends GalacticObjectBuilder<Moon> {

        public final GalacticObject<Planet> planet;

        public Moon(String name, GalacticObject<Planet> planet) {
            super(name);
            this.planet = planet;
        }

        @Override
        public double getAge() {
            return planet.getAge();
        }
    }

    public static class MoonMoon extends GalacticObjectBuilder<MoonMoon> {

        public final GalacticObject<Moon> moon;

        public MoonMoon(String name, GalacticObject<Moon> moon) {
            super(name);
            this.moon = moon;
        }

        @Override
        public double getAge() {
            return moon.getAge();
        }
    }

    public static class GalacticObject<G extends GalacticObjectBuilder<G>> {
        final G builder;

        public GalacticObject(G builder) {
            this.builder = builder;
        }

        public String getName() {
            return builder.getName();
        }
        public String getDiameter() {
            return builder.getDiameter();
        }
        public double getAge() {
            return builder.getAge();
        }
        public String getAtmosphere() {
            return builder.getAtmosphere();
        }
        public int getMoons() {
            return builder.getMoons();
        }
        public double getGravity() {
            return builder.getGravity();
        }
        public int getTier() {
            return builder.getTier();
        }
    }
}
