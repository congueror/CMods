package net.congueror.cgalaxy.gui.galaxy_map;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.stream.Collectors;

public abstract class GalacticObjectBuilder<T extends GalacticObjectBuilder<T>> {

    public final String name;
    private String diameter;
    private double age;

    private ResourceLocation texture;

    //sub, super
    public static final Map<GalacticObject<?>, GalacticObject<?>> OBJECTS = new HashMap<>();

    public GalacticObjectBuilder(String name) {
        this.name = name;
    }//TODO: Translation key generation.

    public static GalacticObject<?> getObjectFromName(String name) {
        return OBJECTS.keySet().stream().filter(galacticObject -> galacticObject.getName().equals(name)).findAny().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public static List<GalacticObject<Galaxy>> galaxies() {
        return OBJECTS.keySet().stream().filter(object -> object.getType() instanceof Galaxy).map(object -> (GalacticObject<Galaxy>) object).collect(Collectors.toList());
    }

    public static List<Map.Entry<GalacticObject<SolarSystem>, GalacticObject<Galaxy>>> solarSystems() {
        List<Map.Entry<GalacticObject<SolarSystem>, GalacticObject<Galaxy>>> list = new ArrayList<>();
        List<GalacticObject<SolarSystem>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof SolarSystem).map(object -> (GalacticObject<SolarSystem>) object).collect(Collectors.toList());
        List<GalacticObject<Galaxy>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof Galaxy).map(object -> (GalacticObject<Galaxy>) object).collect(Collectors.toList());
        for (int i = 0; i < a.size(); i++) {
            list.add(new AbstractMap.SimpleEntry<>(a.get(i), b.get(i)));
        }
        return list;
    }

    public static List<Map.Entry<GalacticObject<Planet>, GalacticObject<SolarSystem>>> planets() {
        List<Map.Entry<GalacticObject<Planet>, GalacticObject<SolarSystem>>> list = new ArrayList<>();
        List<GalacticObject<Planet>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof Planet).map(object -> (GalacticObject<Planet>) object).collect(Collectors.toList());
        List<GalacticObject<SolarSystem>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof SolarSystem).map(object -> (GalacticObject<SolarSystem>) object).collect(Collectors.toList());
        for (int i = 0; i < a.size(); i++) {
            list.add(new AbstractMap.SimpleEntry<>(a.get(i), b.get(i)));
        }
        return list;
    }

    public static List<Map.Entry<GalacticObject<Moon>, GalacticObject<Planet>>> moons() {
        List<Map.Entry<GalacticObject<Moon>, GalacticObject<Planet>>> list = new ArrayList<>();
        List<GalacticObject<Moon>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof Moon).map(object -> (GalacticObject<Moon>) object).collect(Collectors.toList());
        List<GalacticObject<Planet>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof Planet).map(object -> (GalacticObject<Planet>) object).collect(Collectors.toList());
        for (int i = 0; i < a.size(); i++) {
            list.add(new AbstractMap.SimpleEntry<>(a.get(i), b.get(i)));
        }
        return list;
    }

    public static List<Map.Entry<GalacticObject<MoonMoon>, GalacticObject<Moon>>> moonMoons() {
        List<Map.Entry<GalacticObject<MoonMoon>, GalacticObject<Moon>>> list = new ArrayList<>();
        List<GalacticObject<MoonMoon>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof MoonMoon).map(object -> (GalacticObject<MoonMoon>) object).collect(Collectors.toList());
        List<GalacticObject<Moon>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof Moon).map(object -> (GalacticObject<Moon>) object).collect(Collectors.toList());
        for (int i = 0; i < a.size(); i++) {
            list.add(new AbstractMap.SimpleEntry<>(a.get(i), b.get(i)));
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

    public ResourceLocation getTexture() {
        return texture;
    }

    public T withDiameter(double diameter, String unit) {
        this.diameter = diameter + unit;
        return self();
    }

    public T withAge(double age) {
        this.age = age;
        return self();
    }

    public T withTexture(ResourceLocation location) {
        this.texture = location;
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
        private X x;
        private Y y;

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

        public X getX() {
            return x;
        }

        public Y getY() {
            return y;
        }

        public SolarSystem withCelestialObjects(int celestialObjects) {
            this.celestialObjects = celestialObjects;
            return this;
        }

        public SolarSystem withX(X x) {
            this.x = x;
            return this;
        }

        public SolarSystem withY(Y y) {
            this.y = y;
            return this;
        }
    }

    public static class Planet extends GalacticObjectBuilder<Planet> {

        private final GalacticObject<SolarSystem> solarSystem;
        private int ringIndex;
        private float angle;
        private String atmosphere;
        private int moons;
        private double gravity;
        private int tier;
        private ResourceKey<Level> dim;

        public Planet(String name, GalacticObject<SolarSystem> solarSystem) {
            super(name);
            this.solarSystem = solarSystem;
        }

        public GalacticObject<SolarSystem> getSolarSystem() {
            return solarSystem;
        }

        public int getRingIndex() {
            return ringIndex;
        }

        public float getAngle() {
            return angle;
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

        public ResourceKey<Level> getDim() {
            return dim;
        }

        @Override
        public double getAge() {
            return solarSystem.getAge();
        }

        public Planet withAtmosphere(String atmosphere) {
            this.atmosphere = atmosphere;
            return this;
        }

        public Planet withMoons(int moons) {
            this.moons = moons;
            return this;
        }

        public Planet withGravity(double gravity) {
            this.gravity = gravity;
            return this;
        }

        public Planet withTier(int tier) {
            this.tier = tier;
            return this;
        }

        public Planet withRingIndex(int ringIndex) {
            this.ringIndex = ringIndex;
            return this;
        }

        public Planet withAngle(float angle) {
            this.angle = angle;
            return this;
        }

        public Planet withDim(ResourceKey<Level> dim) {
            this.dim = dim;
            return this;
        }
    }

    public static class Moon extends GalacticObjectBuilder<Moon> {

        private final GalacticObject<Planet> planet;
        private int ringIndex;
        private float angle;
        private String atmosphere;
        private int moons;
        private double gravity;
        private int tier;
        private ResourceKey<Level> dim;

        public Moon(String name, GalacticObject<Planet> planet) {
            super(name);
            this.planet = planet;
        }

        public GalacticObject<Planet> getPlanet() {
            return planet;
        }

        public int getRingIndex() {
            return ringIndex;
        }

        public float getAngle() {
            return angle;
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

        public ResourceKey<Level> getDim() {
            return dim;
        }

        @Override
        public double getAge() {
            return planet.getAge();
        }

        public Moon withAtmosphere(String atmosphere) {
            this.atmosphere = atmosphere;
            return this;
        }

        public Moon withMoons(int moons) {
            this.moons = moons;
            return this;
        }

        public Moon withGravity(double gravity) {
            this.gravity = gravity;
            return this;
        }

        public Moon withTier(int tier) {
            this.tier = tier;
            return this;
        }

        public Moon withRingIndex(int ringIndex) {
            this.ringIndex = ringIndex;
            return this;
        }

        public Moon withAngle(float angle) {
            this.angle = angle;
            return this;
        }

        public Moon withDim(ResourceKey<Level> dim) {
            this.dim = dim;
            return this;
        }
    }

    public static class MoonMoon extends GalacticObjectBuilder<MoonMoon> {

        private final GalacticObject<Moon> moon;
        private int ringIndex;
        private float angle;
        private String atmosphere;
        private int moons;
        private double gravity;
        private int tier;

        public MoonMoon(String name, GalacticObject<Moon> moon) {
            super(name);
            this.moon = moon;
        }

        public GalacticObject<Moon> getMoon() {
            return moon;
        }

        public int getRingIndex() {
            return ringIndex;
        }

        public float getAngle() {
            return angle;
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

        @Override
        public double getAge() {
            return moon.getAge();
        }

        public MoonMoon withRingIndex(int ringIndex) {
            this.ringIndex = ringIndex;
            return this;
        }

        public MoonMoon withAngle(float angle) {
            this.angle = angle;
            return this;
        }

        public MoonMoon withAtmosphere(String atmosphere) {
            this.atmosphere = atmosphere;
            return this;
        }

        public MoonMoon withMoons(int moons) {
            this.moons = moons;
            return this;
        }

        public MoonMoon withGravity(double gravity) {
            this.gravity = gravity;
            return this;
        }

        public MoonMoon withTier(int tier) {
            this.tier = tier;
            return this;
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

        public ResourceLocation getTexture() {
            return builder.getTexture();
        }

        public String getAtmosphere() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getAtmosphere();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getAtmosphere();
            } else if (builder instanceof MoonMoon) {
                return ((MoonMoon) builder).getAtmosphere();
            }
            return "null";
        }

        public int getMoons() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getMoons();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getMoons();
            } else if (builder instanceof MoonMoon) {
                return ((MoonMoon) builder).getMoons();
            }
            return -1;
        }

        public double getGravity() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getGravity();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getGravity();
            } else if (builder instanceof MoonMoon) {
                return ((MoonMoon) builder).getGravity();
            }
            return -1;
        }

        public int getTier() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getMoons();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getMoons();
            } else if (builder instanceof MoonMoon) {
                return ((MoonMoon) builder).getMoons();
            }
            return -1;
        }

        public double getStars() {
            if (builder instanceof Galaxy) {
                return ((Galaxy) builder).getStars();
            }
            return -1;
        }

        public int getTerrestrialObjects() {
            if (builder instanceof SolarSystem) {
                return ((SolarSystem) builder).getCelestialObjects();
            }
            return -1;
        }

        public int getSolarSystemX(int width, int leftPos) {
            if (builder instanceof SolarSystem) {
                return ((SolarSystem) builder).getX().applyX(width, leftPos);
            }
            return -1;
        }

        public int getSolarSystemY(int height, int topPos) {
            if (builder instanceof SolarSystem) {
                return ((SolarSystem) builder).getY().applyY(height, topPos);
            }
            return -1;
        }

        public int getRingIndex() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getRingIndex();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getRingIndex();
            } else if (builder instanceof MoonMoon) {
                return ((MoonMoon) builder).getRingIndex();
            }
            return -1;
        }

        public float getAngle() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getAngle();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getAngle();
            } else if (builder instanceof MoonMoon) {
                return ((MoonMoon) builder).getAngle();
            }
            return -1;
        }

        public ResourceKey<Level> getDimension() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getDim();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getDim();
            }
            return null;
        }

        public ChatFormatting getColor() {
            if (builder instanceof Galaxy) {
                return ChatFormatting.DARK_PURPLE;
            } else if (builder instanceof SolarSystem) {
                return ChatFormatting.GOLD;
            } else if (builder instanceof Planet) {
                return ChatFormatting.GREEN;
            } else if (builder instanceof Moon) {
                return ChatFormatting.GRAY;
            }
            return ChatFormatting.WHITE;
        }
    }

    public interface X {
        int applyX(int width, int leftPos);
    }

    public interface Y {
        int applyY(int height, int topPos);
    }
}
