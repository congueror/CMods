package net.congueror.cgalaxy.util.json_managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.congueror.clib.world.WorldHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;

public class DimensionManager extends SimpleJsonResourceReloadListener {
    public static final ArrayList<CGDimensionBuilder.DimensionObject> OBJECTS = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().create();

    public DimensionManager() {
        super(GSON, "cgalaxy/dimension");
    }

    /**
     * Gets the dimension object that matches the given key.
     *
     * @return A {@link CGDimensionBuilder.DimensionObject} if key belongs to any of the registered objects, otherwise null.
     */
    @Nullable
    public static CGDimensionBuilder.DimensionObject getObjectFromKey(ResourceKey<Level> key) {
        return OBJECTS.stream().filter(object -> object.getDim().equals(key)).findAny().orElse(null);
    }

    public static boolean hasDimensionKey(ResourceLocation key) {
        return OBJECTS.stream().anyMatch(o -> o.getDim().getRegistryName().equals(key));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        OBJECTS.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            if (resourcelocation.getPath().startsWith("_")) continue;

            try {

                JsonObject o = GsonHelper.convertToJsonObject(entry.getValue(), "dimension object");

                ResourceLocation dim = new ResourceLocation(GsonHelper.getAsString(o, "dimension"));
                if (hasDimensionKey(dim)) {
                    CGalaxy.LOGGER.error("Error parsing dimension object {}, Object with dimension key {} already exists.", resourcelocation, dim);
                    continue;
                }

                CGDimensionBuilder builder = new CGDimensionBuilder(WorldHelper.registerDim(dim));

                double gravity = GsonHelper.getAsDouble(o, "gravity", 8);
                if (gravity < 0) {
                    CGalaxy.LOGGER.error("Error parsing dimension object {}, invalid gravity value of {}. Value is set to default", resourcelocation, gravity);
                    gravity = 8;
                }
                builder.withGravity(gravity);
                builder.withBreathableAtmosphere(GsonHelper.getAsBoolean(o, "breathable", true));
                builder.withDayTemperature(GsonHelper.getAsInt(o, "dayTemperature", 30));
                builder.withNightTemperature(GsonHelper.getAsInt(o, "nightTemperature", 10));
                float radiation = GsonHelper.getAsFloat(o, "radiation", 2.4f);
                if (radiation < 0) {
                    CGalaxy.LOGGER.error("Error parsing dimension object {}, invalid radiation value of {}. Value is set to default", resourcelocation, radiation);
                    radiation = 2.4f;
                }
                builder.withRadiation(radiation);
                double airPressure = GsonHelper.getAsDouble(o, "airPressure", 101352.9D);
                if (airPressure < 0) {
                    CGalaxy.LOGGER.error("Error parsing dimension object {}, invalid air pressure value of {}. Value is set to default", resourcelocation, airPressure);
                    airPressure = 101352.9D;
                }
                builder.withAirPressure(airPressure);
                builder.withYOverlayTexture(new ResourceLocation(GsonHelper.getAsString(o, "overlayTexture", "cgalaxy:textures/gui/rocket_y_hud.png")));
                builder.isOrbit(GsonHelper.getAsBoolean(o, "isOrbit", false));

                String south = GsonHelper.getAsString(o, "southBiome", null);
                if (south != null) {
                    builder.withSouthBiome(WorldHelper.registerBiome(new ResourceLocation(south)));
                }

                builder.build();
            } catch (Exception e) {
                CGalaxy.LOGGER.error("Exception parsing dimension object {}: {}", resourcelocation, e.getMessage());
            }
        }

        CGalaxy.LOGGER.info("Loaded {} dimension objects", OBJECTS.size());
    }
}
