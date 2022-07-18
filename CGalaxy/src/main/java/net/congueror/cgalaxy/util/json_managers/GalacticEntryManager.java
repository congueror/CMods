package net.congueror.cgalaxy.util.json_managers;

import com.google.gson.*;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.gui.galaxy_map.GalacticEntryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class GalacticEntryManager extends SimpleJsonResourceReloadListener {
    /**
     * sub, super
     */
    public static final Map<GalacticEntryBuilder.GalacticEntry<?>, GalacticEntryBuilder.GalacticEntry<?>> OBJECTS = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().create();

    public GalacticEntryManager() {
        super(GSON, "cgalaxy/galactic_entry");
    }

    public static GalacticEntryBuilder.GalacticEntry<?> getObjectFromId(ResourceLocation name) {
        return OBJECTS.keySet().stream().filter(galacticObject -> galacticObject.getId().equals(name)).findAny().orElse(null);
    }

    public static <T extends GalacticEntryBuilder<T>> GalacticEntryBuilder.GalacticEntry<T> getObjectFromId(ResourceLocation id, Class<T> type) {
        return (GalacticEntryBuilder.GalacticEntry<T>) OBJECTS.keySet().stream().filter(galacticObject -> galacticObject.getId().equals(id)).findAny().orElse(null);
    }

    /**
     * A list of all registered galaxy entries.
     *
     * @return A {@link List} of {@link GalacticEntryBuilder.GalacticEntry} of type {@link GalacticEntryBuilder.Galaxy}
     */
    public static List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Galaxy>> galaxies() {
        return OBJECTS.keySet().stream().filter(object -> object.getType() instanceof GalacticEntryBuilder.Galaxy).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Galaxy>) object).collect(Collectors.toList());
    }

    public static List<Map.Entry<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.SolarSystem>, GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Galaxy>>> solarSystems() {
        List<Map.Entry<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.SolarSystem>, GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Galaxy>>> list = new ArrayList<>();
        List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.SolarSystem>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof GalacticEntryBuilder.SolarSystem).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.SolarSystem>) object).collect(Collectors.toList());
        List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Galaxy>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof GalacticEntryBuilder.Galaxy).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Galaxy>) object).collect(Collectors.toList());
        for (int i = 0; i < a.size(); i++) {
            list.add(new AbstractMap.SimpleEntry<>(a.get(i), b.get(i)));
        }
        return list;
    }

    public static List<Map.Entry<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Planet>, GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.SolarSystem>>> planets() {
        List<Map.Entry<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Planet>, GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.SolarSystem>>> list = new ArrayList<>();
        List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Planet>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof GalacticEntryBuilder.Planet).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Planet>) object).collect(Collectors.toList());
        List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.SolarSystem>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof GalacticEntryBuilder.SolarSystem).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.SolarSystem>) object).collect(Collectors.toList());
        for (int i = 0; i < a.size(); i++) {
            list.add(new AbstractMap.SimpleEntry<>(a.get(i), b.get(i)));
        }
        return list;
    }

    public static List<Map.Entry<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Moon>, GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Planet>>> moons() {
        List<Map.Entry<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Moon>, GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Planet>>> list = new ArrayList<>();
        List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Moon>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof GalacticEntryBuilder.Moon).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Moon>) object).collect(Collectors.toList());
        List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Planet>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof GalacticEntryBuilder.Planet).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Planet>) object).collect(Collectors.toList());
        for (int i = 0; i < a.size(); i++) {
            list.add(new AbstractMap.SimpleEntry<>(a.get(i), b.get(i)));
        }
        return list;
    }

    public static List<Map.Entry<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.MoonMoon>, GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Moon>>> moonMoons() {
        List<Map.Entry<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.MoonMoon>, GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Moon>>> list = new ArrayList<>();
        List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.MoonMoon>> a = OBJECTS.keySet().stream()
                .filter(object -> object != null && object.getType() instanceof GalacticEntryBuilder.MoonMoon).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.MoonMoon>) object).collect(Collectors.toList());
        List<GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Moon>> b = OBJECTS.values().stream()
                .filter(object -> object != null && object.getType() instanceof GalacticEntryBuilder.Moon).map(object -> (GalacticEntryBuilder.GalacticEntry<GalacticEntryBuilder.Moon>) object).collect(Collectors.toList());
        for (int i = 0; i < a.size(); i++) {
            list.add(new AbstractMap.SimpleEntry<>(a.get(i), b.get(i)));
        }
        return list;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        OBJECTS.clear();

        //Here we must order the map, so we can register each type in the correct order.
        List<Map.Entry<ResourceLocation, JsonElement>> list = new LinkedList<>(pObject.entrySet());

        list.sort((o1, o2) -> {
            String s1 = GsonHelper.convertToJsonObject(o1.getValue(), "galactic entry").get("type").getAsString();
            String s2 = GsonHelper.convertToJsonObject(o2.getValue(), "galactic entry").get("type").getAsString();

            if (s1.equals(s2)) {
                return 0;
            } else if (s1.equals("galaxy")) {
                return -1;
            } else if (s1.equals("solar_system")) {
                if (s2.equals("galaxy")) return 1;
                else return -1;
            } else if (s1.equals("planet")) {
                if (s2.equals("galaxy")) return 1;
                if (s2.equals("solar_system")) return 1;
                else return -1;
            } else if (s1.equals("moon")) {
                if (s2.equals("galaxy")) return 1;
                if (s2.equals("solar_system")) return 1;
                if (s2.equals("planet")) return 1;
                else return -1;
            } else {
                throw new JsonParseException("Exception parsing galactic entry. an entry does not contain a valid type value");
            }
        });

        for (Map.Entry<ResourceLocation, JsonElement> entry : list) {
            ResourceLocation resourcelocation = entry.getKey();
            if (resourcelocation.getPath().startsWith("_")) continue;

            try {
                JsonObject o = GsonHelper.convertToJsonObject(entry.getValue(), "galactic entry");

                ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(o, "id"));
                if (getObjectFromId(id) != null) {
                    CGalaxy.LOGGER.error("Error parsing galactic entry {}, Entry with id {} already exists.", resourcelocation, id);
                    continue;
                }

                String type = GsonHelper.getAsString(o, "type");
                if (type.equalsIgnoreCase("galaxy")) {
                    GalacticEntryBuilder.Galaxy galaxy = new GalacticEntryBuilder.Galaxy(id);

                    if (age(galaxy, o, resourcelocation)) {
                        continue;
                    }

                    if (diameter(galaxy, o, resourcelocation)) {
                        continue;
                    }

                    double stars = GsonHelper.getAsDouble(o, "stars");
                    if (stars < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid stars value of {}.", resourcelocation, stars);
                        continue;
                    }
                    galaxy.withStars(stars);

                    galaxy.withTexture(new ResourceLocation(GsonHelper.getAsString(o, "texture")));
                    galaxy.build();
                } else if (type.equalsIgnoreCase("solar_system")) {
                    ResourceLocation parentId = new ResourceLocation(GsonHelper.getAsString(o, "parent"));
                    GalacticEntryBuilder.SolarSystem solarSystem = new GalacticEntryBuilder.SolarSystem(id, getObjectFromId(parentId, GalacticEntryBuilder.Galaxy.class));

                    if (age(solarSystem, o, resourcelocation)) {
                        continue;
                    }

                    if (diameter(solarSystem, o, resourcelocation)) {
                        continue;
                    }

                    int celObjs = GsonHelper.getAsInt(o, "celestialObjects");
                    if (celObjs < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid celestialObjects value of {}.", resourcelocation, celObjs);
                        continue;
                    }
                    solarSystem.withCelestialObjects(celObjs);

                    int x = GsonHelper.getAsInt(o, "x", 0);
                    int y = GsonHelper.getAsInt(o, "y", 0);
                    solarSystem.withX(x);
                    solarSystem.withY(y);

                    solarSystem.withTexture(new ResourceLocation(GsonHelper.getAsString(o, "texture")));
                    solarSystem.build();
                } else if (type.equalsIgnoreCase("planet")) {
                    ResourceLocation parentId = new ResourceLocation(GsonHelper.getAsString(o, "parent"));
                    GalacticEntryBuilder.Planet planet = new GalacticEntryBuilder.Planet(id, getObjectFromId(parentId, GalacticEntryBuilder.SolarSystem.class));

                    try {
                        age(planet, o, resourcelocation);
                    } catch (JsonSyntaxException ignored) {

                    }

                    if (diameter(planet, o, resourcelocation)) {
                        continue;
                    }

                    int moons = GsonHelper.getAsInt(o, "moons", 0);
                    if (moons < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid moons value of {}. Value is set to default", resourcelocation, moons);
                        moons = 0;
                    }
                    planet.withMoons(moons);
                    planet.withAtmosphere(GsonHelper.getAsString(o, "atmosphere"));

                    double gravity = GsonHelper.getAsDouble(o, "gravity", 8);
                    if (gravity < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid gravity value of {}. Value is set to default", resourcelocation, gravity);
                        gravity = 8;
                    }
                    planet.withGravity(gravity);

                    int tier = GsonHelper.getAsInt(o, "tier");
                    if (tier < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid tier value of {}.", resourcelocation, tier);
                        continue;
                    }
                    planet.withTier(tier);
                    planet.withAngle(GsonHelper.getAsFloat(o, "angle", 0));

                    float daysPerYear = GsonHelper.getAsFloat(o, "daysPerYear");
                    if (daysPerYear < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid daysPerYear value of {}.", resourcelocation, daysPerYear);
                        continue;
                    }
                    planet.withDaysPerYear(daysPerYear);

                    int ringIndex = GsonHelper.getAsInt(o, "ringIndex");
                    if (ringIndex < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid ringIndex value of {}.", resourcelocation, ringIndex);
                        continue;
                    }
                    planet.withRingIndex(ringIndex);

                    planet.withTexture(new ResourceLocation(GsonHelper.getAsString(o, "texture")));

                    try {
                        ResourceLocation dim = new ResourceLocation(GsonHelper.getAsString(o, "dimension"));
                        planet.withDim(ResourceKey.create(Registry.DIMENSION_REGISTRY, dim));
                    } catch (JsonSyntaxException ignored) {}

                    try {
                        ResourceLocation orbitDim = new ResourceLocation(GsonHelper.getAsString(o, "orbitDimension"));
                        planet.withDim(ResourceKey.create(Registry.DIMENSION_REGISTRY, orbitDim));
                    } catch (JsonSyntaxException ignored) {}

                    planet.build();
                } else if (type.equalsIgnoreCase("moon")) {
                    ResourceLocation parentId = new ResourceLocation(GsonHelper.getAsString(o, "parent"));
                    GalacticEntryBuilder.Moon moon = new GalacticEntryBuilder.Moon(id, getObjectFromId(parentId, GalacticEntryBuilder.Planet.class));

                    try {
                        age(moon, o, resourcelocation);
                    } catch (JsonSyntaxException ignored) {

                    }

                    if (diameter(moon, o, resourcelocation)) {
                        continue;
                    }

                    int moons = GsonHelper.getAsInt(o, "moons", 0);
                    if (moons < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid moons value of {}. Value is set to default", resourcelocation, moons);
                        moons = 0;
                    }
                    moon.withMoons(moons);
                    moon.withAtmosphere(GsonHelper.getAsString(o, "atmosphere"));

                    double gravity = GsonHelper.getAsDouble(o, "gravity", 8);
                    if (gravity < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid gravity value of {}. Value is set to default", resourcelocation, gravity);
                        gravity = 8;
                    }
                    moon.withGravity(gravity);

                    int tier = GsonHelper.getAsInt(o, "tier");
                    if (tier < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid tier value of {}.", resourcelocation, tier);
                        continue;
                    }
                    moon.withTier(tier);
                    moon.withAngle(GsonHelper.getAsFloat(o, "angle", 0));

                    float daysPerYear = GsonHelper.getAsFloat(o, "daysPerYear");
                    if (daysPerYear < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid daysPerYear value of {}.", resourcelocation, daysPerYear);
                        continue;
                    }
                    moon.withDaysPerYear(daysPerYear);

                    int ringIndex = GsonHelper.getAsInt(o, "ringIndex");
                    if (ringIndex < 0) {
                        CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid ringIndex value of {}.", resourcelocation, ringIndex);
                        continue;
                    }
                    moon.withRingIndex(ringIndex);

                    moon.withTexture(new ResourceLocation(GsonHelper.getAsString(o, "texture")));

                    try {
                        ResourceLocation dim = new ResourceLocation(GsonHelper.getAsString(o, "dimension"));
                        moon.withDim(ResourceKey.create(Registry.DIMENSION_REGISTRY, dim));
                    } catch (JsonSyntaxException ignored) {}

                    try {
                        ResourceLocation orbitDim = new ResourceLocation(GsonHelper.getAsString(o, "orbitDimension"));
                        moon.withDim(ResourceKey.create(Registry.DIMENSION_REGISTRY, orbitDim));
                    } catch (JsonSyntaxException ignored) {}

                    moon.build();
                } else {
                    CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid type of {}", resourcelocation, type);
                }
            } catch (Exception e) {
                CGalaxy.LOGGER.error("Exception parsing galactic entry {}: {}", resourcelocation, e.getMessage());
            }
        }

        CGalaxy.LOGGER.info("Loaded {} galactic entries", OBJECTS.size());
    }

    private <T extends GalacticEntryBuilder<T>> boolean diameter(GalacticEntryBuilder<T> builder, JsonObject o, ResourceLocation rl) {
        double distance;
        String unit;
        if (o.get("diameter").isJsonObject()) {
            JsonObject diameter = GsonHelper.getAsJsonObject(o, "diameter");
            distance = GsonHelper.getAsDouble(diameter, "distance");
            unit = GsonHelper.getAsString(diameter, "unit", "LY");
        } else {
            distance = GsonHelper.getAsDouble(o, "diameter");
            unit = "LY";
        }
        if (distance < 0) {
            CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid diameter value of {}.", rl, distance);
            return true;
        }
        builder.withDiameter(distance, unit);
        return false;
    }

    private <T extends GalacticEntryBuilder<T>> boolean age(GalacticEntryBuilder<T> builder, JsonObject o, ResourceLocation rl) {
        double age = GsonHelper.getAsDouble(o, "age");
        if (age < 0) {
            CGalaxy.LOGGER.error("Error parsing galactic entry {}, invalid age value of {}.", rl, age);
            return true;
        }
        builder.withAge(age);
        return false;
    }
}
