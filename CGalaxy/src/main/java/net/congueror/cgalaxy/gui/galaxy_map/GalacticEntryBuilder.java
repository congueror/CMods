package net.congueror.cgalaxy.gui.galaxy_map;

import net.congueror.cgalaxy.util.json_managers.GalacticEntryManager;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.*;

public abstract class GalacticEntryBuilder<T extends GalacticEntryBuilder<T>> {

    public final ResourceLocation id;
    private MutablePair<Double, String> diameter;
    private double age;

    private ResourceLocation texture;
    public final Map<String, String> locale = new HashMap<>();

    public GalacticEntryBuilder(ResourceLocation id) {
        this.id = id;
    }//TODO: Translation key generation.

    public GalacticEntry<T> build() {
        GalacticEntry<T> obj = new GalacticEntry<>(self());
        if (GalacticEntryManager.OBJECTS.keySet().stream().map(GalacticEntry::getId).anyMatch(s -> s.equals(obj.getId()))) {
            throw new IllegalStateException("Duplicate galactic objects found!");
        }
        if (self() instanceof Galaxy) {
            GalacticEntryManager.OBJECTS.put(obj, null);
        } else if (self() instanceof SolarSystem system) {
            GalacticEntryManager.OBJECTS.put(obj, system.galaxy);
        } else if (self() instanceof Planet planet) {
            GalacticEntryManager.OBJECTS.put(obj, planet.solarSystem);
        } else if (self() instanceof Moon moon) {
            GalacticEntryManager.OBJECTS.put(obj, moon.planet);
        } else if (self() instanceof MoonMoon moonMoon) {
            GalacticEntryManager.OBJECTS.put(obj, moonMoon.moon);
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

    public static class Galaxy extends GalacticEntryBuilder<Galaxy> {

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

        public Galaxy withStars(double stars) {//TODO: convert to integer and string. ([number], [(e.g. "e9")])
            this.stars = stars;
            return this;
        }
    }

    public static class SolarSystem extends GalacticEntryBuilder<SolarSystem> {

        private final GalacticEntry<Galaxy> galaxy;
        private int celestialObjects;
        private X X;
        private Y Y;
        private int x;
        private int y;

        public SolarSystem(ResourceLocation name, GalacticEntry<Galaxy> galaxy) {
            super(name);
            this.galaxy = galaxy;
        }

        public GalacticEntry<Galaxy> getGalaxy() {
            return galaxy;
        }

        public int getCelestialObjects() {
            return celestialObjects;
        }

        public X getX() {
            return X;
        }

        public Y getY() {
            return Y;
        }

        public SolarSystem withCelestialObjects(int celestialObjects) {
            this.celestialObjects = celestialObjects;
            return this;
        }

        public SolarSystem withX(X x) {
            this.X = x;
            return this;
        }

        public SolarSystem withY(Y y) {
            this.Y = y;
            return this;
        }

        public SolarSystem withX(int x) {
            this.x = x;
            return this;
        }

        public SolarSystem withY(int y) {
            this.y = y;
            return this;
        }

        @Override
        protected String getType() {
            return "solar_system";
        }
    }

    public static abstract class AbstractTerrestrialEntry<T extends GalacticEntryBuilder<T>> extends GalacticEntryBuilder<T> {
        private int ringIndex;
        private float angle;
        private float daysPerYear;
        private String atmosphere;
        private int moons;
        private double gravity;
        private int tier;
        private ResourceKey<Level> dim;
        private ResourceKey<Level> orbitDim;

        public AbstractTerrestrialEntry(ResourceLocation id) {
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

        public ResourceKey<Level> getOrbitDim() {
            return orbitDim;
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

        public T withOrbitDim(ResourceKey<Level> dim) {
            this.orbitDim = dim;
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

    public static class Planet extends AbstractTerrestrialEntry<Planet> {

        private final GalacticEntry<SolarSystem> solarSystem;

        public Planet(ResourceLocation name, GalacticEntry<SolarSystem> solarSystem) {
            super(name);
            this.solarSystem = solarSystem;
            this.withAge(solarSystem.getAge());
        }

        public GalacticEntry<SolarSystem> getSolarSystem() {
            return solarSystem;
        }

        @Override
        protected String getType() {
            return "planet";
        }
    }

    public static class Moon extends AbstractTerrestrialEntry<Moon> {

        private final GalacticEntry<Planet> planet;

        public Moon(ResourceLocation name, GalacticEntry<Planet> planet) {
            super(name);
            this.planet = planet;
            this.withAge(planet.getAge());
        }

        public GalacticEntry<Planet> getPlanet() {
            return planet;
        }

        @Override
        protected String getType() {
            return "moon";
        }
    }

    public static class MoonMoon extends AbstractTerrestrialEntry<MoonMoon> {

        private final GalacticEntry<Moon> moon;

        public MoonMoon(ResourceLocation name, GalacticEntry<Moon> moon) {
            super(name);
            this.moon = moon;
            this.withAge(moon.getAge());
        }

        public GalacticEntry<Moon> getMoon() {
            return moon;
        }

        @Override
        protected String getType() {
            return "moonmoon";
        }
    }

    /**
     * An entry for the galaxy map.
     * @param <G> The type of entry
     */
    public static class GalacticEntry<G extends GalacticEntryBuilder<G>> {
        private final G builder;

        public GalacticEntry(G builder) {
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

        /**
         * A description of the atmosphere on this entry.
         * @return "null" if entry is not of type {@link Planet} or {@link Moon} or {@link MoonMoon}
         */
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

        /**
         * The amount of terrestrial moon entries this planet/moon/moonmoon has.
         * @return -1 if entry is not of type {@link Planet} or {@link Moon} or {@link MoonMoon}
         */
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

        /**
         * The gravitational acceleration of the entry in m/s^2.
         * @return -1 if entry is not of type {@link Planet} or {@link Moon} or {@link MoonMoon}
         */
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

        /**
         * The tier required for a rocket to visit this planet.
         * @return -1 if entry is not of type {@link Planet} or {@link Moon} or {@link MoonMoon}
         */
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

        /**
         * The amount of stars that exist in this galaxy.
         * @return -1 if entry is not of type {@link Galaxy}
         */
        public double getStars() {
            if (builder instanceof Galaxy) {
                return ((Galaxy) builder).getStars();
            }
            return -1;
        }

        /**
         * The amount of terrestrial entries this solar system has.
         * @return -1 if entry is not of type {@link SolarSystem}
         */
        public int getTerrestrialObjects() {
            if (builder instanceof SolarSystem) {
                return ((SolarSystem) builder).getCelestialObjects();
            }
            return -1;
        }

        /**
         * The horizontal (x) position that this solar system will appear on, on the map of the parent galaxy.
         * @param width Width of screen
         * @param leftPos Left position of screen
         * @return -1 if entry is not of type {@link SolarSystem}
         */
        public int getSolarSystemX(int width, int leftPos) {
            if (builder instanceof SolarSystem) {
                return ((SolarSystem) builder).getX().applyX(width, leftPos);
            }
            return -1;
        }

        /**
         * The vertical (y) position that this solar system will appear on, on the map of the parent galaxy.
         * @param height Height of screen
         * @param topPos Top position of screen
         * @return -1 if entry is not of type {@link SolarSystem}
         */
        public int getSolarSystemY(int height, int topPos) {
            if (builder instanceof SolarSystem) {
                return ((SolarSystem) builder).getY().applyY(height, topPos);
            }
            return -1;
        }

        public int getX() {
            if (builder instanceof SolarSystem) {
                return ((SolarSystem) builder).x;
            }
            return -1;
        }

        public int getY() {
            if (builder instanceof SolarSystem) {
                return ((SolarSystem) builder).y;
            }
            return -1;
        }

        /**
         * The index of the ring that this entry will orbit on the map.
         * @return -1 if entry is not of type {@link Planet} or {@link Moon} or {@link MoonMoon}
         */
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

        /**
         * The starting angle of the entry on the map
         * @return -1 if entry is not of type {@link Planet} or {@link Moon} or {@link MoonMoon}
         */
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

        /**
         * The orbital period of the entry in days per earth year.
         * @return -1 if entry is not of type {@link Planet} or {@link Moon} or {@link MoonMoon}
         */
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

        /**
         * The ResourceKey of the dimension that belongs to this planet/moon
         * @return null if entry is not of type {@link Planet} or {@link Moon}
         */
        public ResourceKey<Level> getDimension() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getDim();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getDim();
            }
            return null;
        }

        /**
         * The ResourceKey of the dimension that belongs to this planet's/moon's orbit.
         * @return null if entry is not of type {@link Planet} or {@link Moon}
         */
        public ResourceKey<Level> getOrbitDimension() {
            if (builder instanceof Planet) {
                return ((Planet) builder).getOrbitDim();
            } else if (builder instanceof Moon) {
                return ((Moon) builder).getOrbitDim();
            }
            return null;
        }

        /**
         * The formatting of the entry depending on its type.
         */
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

    /**
     * Convenience interface. Made exclusively for the parameter names :)
     */
    public interface X {
        int applyX(int width, int leftPos);
    }

    /**
     * Convenience interface. Made exclusively for the parameter names :)
     */
    public interface Y {
        int applyY(int height, int topPos);
    }
}
