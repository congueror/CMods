package net.congueror.cgalaxy.gui.galaxy_map;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.*;
import java.util.stream.Collectors;

public abstract class GalacticObjectBuilder<T extends GalacticObjectBuilder<T>> {

    public final ResourceLocation id;
    private MutablePair<Double, String> diameter;
    private double age;

    private ResourceLocation texture;
    public final Map<String, String> locale = new HashMap<>();

    //sub, super
    public static final Map<GalacticObject<?>, GalacticObject<?>> OBJECTS = new HashMap<>();

    public GalacticObjectBuilder(ResourceLocation id) {
        this.id = id;
    }//TODO: Translation key generation.

    public static GalacticObject<?> getObjectFromId(ResourceLocation name) {
        return OBJECTS.keySet().stream().filter(galacticObject -> galacticObject.getId().equals(name)).findAny().orElse(null);
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
        if (OBJECTS.keySet().stream().map(GalacticObject::getId).anyMatch(s -> s.equals(obj.getId()))) {
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

    public ResourceLocation getId() {
        return id;
    }

    public MutablePair<Double, String> getDiameter() {
        return diameter;
    }

    public double getAge() {
        return age;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    protected abstract String getType();

    public String getTranslationKey() {
        return getType() + "." + getId().getNamespace() + "." + getId().getPath();
    }

    public T withDiameter(double diameter, String unit) {
        this.diameter = new MutablePair<>(diameter, unit);
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

    /**
     * Adds a translation to this object in the en_us locale. If you wish to change locale use {@link #withTranslation(String, String)}
     *
     * @param translation The name of the translated object, e.g. "My Ore"
     */
    public T withTranslation(String translation) {
        this.locale.put("en_us", translation);
        return self();
    }

    /**
     * Adds a translation to this block in the given locale.
     *
     * @param translation The name of the translated block, e.g. "My Ore"
     * @param locale      The localization this translation will be added to, e.g. "en_us"
     */
    public T withTranslation(String translation, String locale) {
        this.locale.put(locale, translation);
        return self();
    }

    @SuppressWarnings("unchecked")
    private T self() {
        return (T) this;
    }

    public static class Galaxy extends GalacticObjectBuilder<Galaxy> {

        private double stars;

        public Galaxy(ResourceLocation name) {
            super(name);
        }

        @Override
        protected String getType() {
            return "galaxy";
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

        public SolarSystem(ResourceLocation name, GalacticObject<Galaxy> galaxy) {
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

        @Override
        protected String getType() {
            return "solar_system";
        }
    }

    public static abstract class AbstractTerrestrialObject<T extends GalacticObjectBuilder<T>> extends GalacticObjectBuilder<T> {
        private int ringIndex;
        private float angle;
        private float daysPerYear;
        private String atmosphere;
        private int moons;
        private double gravity;
        private int tier;
        private ResourceKey<Level> dim;

        public AbstractTerrestrialObject(ResourceLocation id) {
            super(id);
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

        public float getDaysPerYear() {
            return daysPerYear;
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

        public T withRingIndex(int ringIndex) {
            this.ringIndex = ringIndex;
            return self();
        }

        public T withAngle(float angle) {
            this.angle = angle;
            return self();
        }

        public T withDim(ResourceKey<Level> dim) {
            this.dim = dim;
            return self();
        }

        public T withDaysPerYear(float days) {
            this.daysPerYear = days;
            return self();
        }

        private T self() {
            //noinspection unchecked
            return (T) this;
        }
    }

    public static class Planet extends AbstractTerrestrialObject<Planet> {

        private final GalacticObject<SolarSystem> solarSystem;

        public Planet(ResourceLocation name, GalacticObject<SolarSystem> solarSystem) {
            super(name);
            this.solarSystem = solarSystem;
            this.withAge(solarSystem.getAge());
        }

        public GalacticObject<SolarSystem> getSolarSystem() {
            return solarSystem;
        }

        @Override
        protected String getType() {
            return "planet";
        }
    }

    public static class Moon extends AbstractTerrestrialObject<Moon> {

        private final GalacticObject<Planet> planet;

        public Moon(ResourceLocation name, GalacticObject<Planet> planet) {
            super(name);
            this.planet = planet;
            this.withAge(planet.getAge());
        }

        public GalacticObject<Planet> getPlanet() {
            return planet;
        }

        @Override
        protected String getType() {
            return "moon";
        }
    }

    public static class MoonMoon extends AbstractTerrestrialObject<MoonMoon> {

        private final GalacticObject<Moon> moon;

        public MoonMoon(ResourceLocation name, GalacticObject<Moon> moon) {
            super(name);
            this.moon = moon;
            this.withAge(moon.getAge());
        }

        public GalacticObject<Moon> getMoon() {
            return moon;
        }

        @Override
        protected String getType() {
            return "moonmoon";
        }
    }

    public static class GalacticObject<G extends GalacticObjectBuilder<G>> {
        private final G builder;

        public GalacticObject(G builder) {
            this.builder = builder;
        }

        public ResourceLocation getId() {
            return builder.getId();
        }

        public G getType() {
            return this.builder;
        }

        public MutablePair<Double, String> getDiameter() {
            return builder.getDiameter();
        }

        public double getAge() {
            return builder.getAge();
        }

        public ResourceLocation getTexture() {
            return builder.getTexture();
        }

        public String getTranslationKey() {
            return builder.getTranslationKey();
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
                return ((Planet) builder).getTier();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getTier();
            } else if (builder instanceof MoonMoon) {
                return ((MoonMoon) builder).getTier();
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

        public float getDaysPerYear() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getDaysPerYear();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getDaysPerYear();
            } else if (builder instanceof MoonMoon) {
                return ((MoonMoon) builder).getDaysPerYear();
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
