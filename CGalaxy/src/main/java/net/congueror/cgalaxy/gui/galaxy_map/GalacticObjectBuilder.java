package net.congueror.cgalaxy.gui.galaxy_map;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public abstract class GalacticObjectBuilder<T extends GalacticObjectBuilder<T>> {

    public final String name;
    private String diameter;
    private double age;

    private String atmosphere;
    private int moons;
    private double gravity;
    private int tier;

    /**width, leftPos, x*/
    private BiFunction<Integer, Integer, Integer> x;
    /**height, topPos, y*/
    private BiFunction<Integer, Integer, Integer> y;

    public static final Map<GalacticObject<?>, GalacticObject<?>> OBJECTS = new HashMap<>();

    public GalacticObjectBuilder(String name) {
        this.name = name;
    }//TODO: Translation key generation.

    public static GalacticObject<?> getObjectFromName(String name) {
        return OBJECTS.keySet().stream().filter(galacticObject -> galacticObject.getName().equals(name)).findAny().orElse(null);
    }

    /*
    public static <T extends GalacticObjectBuilder<T>, U extends GalacticObjectBuilder<U>, A extends GalacticObject<? extends GalacticObjectBuilder<T>>, B extends GalacticObject<? extends GalacticObjectBuilder<U>>>
    List<Map.Entry<A, B>> getObjectsOfType() {
        List<Map.Entry<A, B>> list = new ArrayList<>();
        List<A> a = OBJECTS.keySet().stream()
                .filter(object -> object.getClass().isInstance(A)).map(object -> (A) object).collect(Collectors.toList());
        List<GalacticObject<Galaxy>> galaxies = OBJECTS.values().stream()
                .filter(object -> object.getType() instanceof Galaxy).map(object -> (GalacticObject<Galaxy>) object).collect(Collectors.toList());
        for (GalacticObject<SolarSystem> system : a) {
            for (GalacticObject<Galaxy> galaxy : galaxies) {
                list.add(new AbstractMap.SimpleEntry<>(system, galaxy));
            }
        }
        return list;
    }*/

    @SuppressWarnings("unchecked")
    public static List<GalacticObject<Galaxy>> galaxies() {
        return OBJECTS.keySet().stream().filter(object -> object.getType() instanceof Galaxy).map(object -> (GalacticObject<Galaxy>) object).collect(Collectors.toList());
    }

    public static List<Map.Entry<GalacticObject<SolarSystem>, GalacticObject<Galaxy>>> solarSystems() {
        List<Map.Entry<GalacticObject<SolarSystem>, GalacticObject<Galaxy>>> list = new ArrayList<>();
        List<GalacticObject<SolarSystem>> systems = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof SolarSystem).map(object -> (GalacticObject<SolarSystem>) object).collect(Collectors.toList());
        List<GalacticObject<Galaxy>> galaxies = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof Galaxy).map(object -> (GalacticObject<Galaxy>) object).collect(Collectors.toList());
        for (GalacticObject<SolarSystem> system : systems) {
            for (GalacticObject<Galaxy> galaxy : galaxies) {
                list.add(new AbstractMap.SimpleEntry<>(system, galaxy));
            }
        }
        return list;
    }

    public static List<Map.Entry<GalacticObject<Planet>, GalacticObject<SolarSystem>>> planets() {
        List<Map.Entry<GalacticObject<Planet>, GalacticObject<SolarSystem>>> list = new ArrayList<>();
        List<GalacticObject<Planet>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof Planet).map(object -> (GalacticObject<Planet>) object).collect(Collectors.toList());
        List<GalacticObject<SolarSystem>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof SolarSystem).map(object -> (GalacticObject<SolarSystem>) object).collect(Collectors.toList());
        for (GalacticObject<Planet> system : a) {
            for (GalacticObject<SolarSystem> galaxy : b) {
                list.add(new AbstractMap.SimpleEntry<>(system, galaxy));
            }
        }
        return list;
    }

    public static List<Map.Entry<GalacticObject<Moon>, GalacticObject<Planet>>> moons() {
        List<Map.Entry<GalacticObject<Moon>, GalacticObject<Planet>>> list = new ArrayList<>();
        List<GalacticObject<Moon>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof Moon).map(object -> (GalacticObject<Moon>) object).collect(Collectors.toList());
        List<GalacticObject<Planet>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof Planet).map(object -> (GalacticObject<Planet>) object).collect(Collectors.toList());
        for (GalacticObject<Moon> system : a) {
            for (GalacticObject<Planet> galaxy : b) {
                list.add(new AbstractMap.SimpleEntry<>(system, galaxy));
            }
        }
        return list;
    }

    public static List<Map.Entry<GalacticObject<MoonMoon>, GalacticObject<Moon>>> moonMoons() {
        List<Map.Entry<GalacticObject<MoonMoon>, GalacticObject<Moon>>> list = new ArrayList<>();
        List<GalacticObject<MoonMoon>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof MoonMoon).map(object -> (GalacticObject<MoonMoon>) object).collect(Collectors.toList());
        List<GalacticObject<Moon>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof Moon).map(object -> (GalacticObject<Moon>) object).collect(Collectors.toList());
        for (GalacticObject<MoonMoon> system : a) {
            for (GalacticObject<Moon> galaxy : b) {
                list.add(new AbstractMap.SimpleEntry<>(system, galaxy));
            }
        }
        return list;
    }

    public GalacticObject<T> build() {
        GalacticObject<T> obj = new GalacticObject<>(self());
        if (OBJECTS.keySet().stream().map(GalacticObject::getName).anyMatch(s -> s.equals(obj.getName()))) {
            throw new IllegalStateException("Duplicate galactic objects found!");
        }
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

    public BiFunction<Integer, Integer, Integer> getX() {
        return x;
    }

    public BiFunction<Integer, Integer, Integer> getY() {
        return y;
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

    public T withX(BiFunction<Integer, Integer, Integer> x) {
        this.x = x;
        return self();
    }

    public T withY(BiFunction<Integer, Integer, Integer> y) {
        this.y = y;
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

        private final GalacticObject<Galaxy> galaxy;
        private int celestialObjects;

        public SolarSystem(String name, GalacticObject<Galaxy> galaxy) {
            super(name);
            this.galaxy = galaxy;
        }

        public GalacticObject<Galaxy> getGalaxy() {
            return galaxy;
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

        private final GalacticObject<SolarSystem> solarSystem;

        public Planet(String name, GalacticObject<SolarSystem> solarSystem) {
            super(name);
            this.solarSystem = solarSystem;
        }

        public GalacticObject<SolarSystem> getSolarSystem() {
            return solarSystem;
        }

        @Override
        public double getAge() {
            return solarSystem.getAge();
        }
    }

    public static class Moon extends GalacticObjectBuilder<Moon> {

        private final GalacticObject<Planet> planet;

        public Moon(String name, GalacticObject<Planet> planet) {
            super(name);
            this.planet = planet;
        }

        public GalacticObject<Planet> getPlanet() {
            return planet;
        }

        @Override
        public double getAge() {
            return planet.getAge();
        }
    }

    public static class MoonMoon extends GalacticObjectBuilder<MoonMoon> {

        private final GalacticObject<Moon> moon;

        public MoonMoon(String name, GalacticObject<Moon> moon) {
            super(name);
            this.moon = moon;
        }

        public GalacticObject<Moon> getMoon() {
            return moon;
        }

        @Override
        public double getAge() {
            return moon.getAge();
        }
    }

    public static class GalacticObject<G extends GalacticObjectBuilder<G>> {
        private final G builder;

        public GalacticObject(G builder) {
            this.builder = builder;
        }

        public String getName() {
            return builder.getName();
        }

        public G getType() {
            return this.builder;
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

        public BiFunction<Integer, Integer, Integer> getX() {
            return builder.getX();
        }

        public BiFunction<Integer, Integer, Integer> getY() {
            return builder.getY();
        }

        public double getStars() {
            if (builder instanceof Galaxy) {
                return ((Galaxy) builder).getStars();
            }
            return -1;
        }
    }
}
