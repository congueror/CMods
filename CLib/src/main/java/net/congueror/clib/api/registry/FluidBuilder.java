package net.congueror.clib.api.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class FluidBuilder {
    private final String name;
    private final Function<ForgeFlowingFluid.Properties, FlowingFluid> stillFluid;
    private final Function<ForgeFlowingFluid.Properties, FlowingFluid> flowingFluid;


    private RegistryObject<FlowingFluid> still;
    private RegistryObject<FlowingFluid> flowing;
    public static final Map<String, List<FluidBuilder>> OBJECTS = new HashMap<>();
    /**
     * All the fluid tags added via the {@link #withNewFluidTag(String)} method. The string is simply the full name of the tag, e.g. "forge:storage_blocks/steel".
     */
    public static final Map<String, Tag.Named<Fluid>> FLUID_TAGS = new HashMap<>();
    public final Map<String, Tag.Named<Fluid>> fluidTags = new HashMap<>();
    public final Map<String, String> locale = new HashMap<>();

    private Supplier<? extends LiquidBlock> block;
    private Supplier<? extends Item> bucket;
    private float explosionResistance;
    private boolean canMultiply;
    private int levelDecreasePerBlock = 1;
    private int slopeFindDistance = 5;
    private int tickRate;
    private ResourceLocation stillTexture = new ResourceLocation("block/water_still");
    private ResourceLocation flowingTexture = new ResourceLocation("block/water_flow");
    private int color = 0xFFFFFFFF;
    private int density;
    private int temperature;
    private boolean gaseous;
    private int viscosity;
    private int luminosity;
    private ResourceLocation overlay;
    private Rarity rarity;
    private SoundEvent fill = SoundEvents.BUCKET_FILL;
    private SoundEvent empty = SoundEvents.BUCKET_EMPTY;

    /**
     * This is the main constructor of the builder.
     * @param name The name of your fluid. Note that a "_still" and "_flow" will be appended to the name. e.g. "my_fluid" -> "my_fluid_still", "my_fluid_flow"
     * @param stillFluid A new instance of your fluid source class. Use the given properties.
     * @param flowingFluid A new instance of your fluid flowing class. Use the given properties.
     */
    public FluidBuilder(@Nonnull String name, @Nonnull Function<ForgeFlowingFluid.Properties, FlowingFluid> stillFluid, @Nonnull Function<ForgeFlowingFluid.Properties, FlowingFluid> flowingFluid) {
        this.name = name;
        this.stillFluid = stillFluid;
        this.flowingFluid = flowingFluid;
    }

    public RegistryObject<FlowingFluid> getStill() {
        return still;
    }

    public RegistryObject<FlowingFluid> getFlowing() {
        return flowing;
    }

    private ForgeFlowingFluid.Properties properties() {
        FluidAttributes.Builder attributes = FluidAttributes.builder(stillTexture, flowingTexture)
                .color(color)
                .density(density)
                .temperature(temperature)
                .viscosity(viscosity)
                .luminosity(luminosity)
                .sound(fill, empty);
        if (gaseous) {
            attributes.gaseous();
        }
        if (overlay != null) {
            attributes.overlay(overlay);
        }
        if (rarity != null) {
            attributes.rarity(rarity);
        }
        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(still, flowing, attributes);
        if (block != null) {
            properties.block(block);
        }
        if (bucket != null) {
            properties.bucket(bucket);
        }
        if (explosionResistance > 0) {
            properties.explosionResistance(explosionResistance);
        }
        if (canMultiply) {
            properties.canMultiply();
        }
        if (levelDecreasePerBlock >= 0) {
            properties.levelDecreasePerBlock(levelDecreasePerBlock);
        }
        if (slopeFindDistance > 0) {
            properties.slopeFindDistance(slopeFindDistance);
        }
        if (tickRate > 0) {
            properties.tickRate(tickRate);
        }
        return properties;
    }

    /**
     * Temporary fix until forge fixes this. Called from a {@link net.minecraftforge.fml.event.lifecycle.InterModProcessEvent}.
     */
    public static void registerBlockFluidFix() {
        for (Map.Entry<String, List<FluidBuilder>> entry : OBJECTS.entrySet()) {
            //entry.getValue().stream().filter(fluidBuilder -> fluidBuilder.block != null).forEach(fluidBuilder -> fluidBuilder.block.get().fluid = fluidBuilder.block.get().getFluid());
        }
    }

    /**
     * Builds/Registers this FluidBuilder object to the DeferredRegister passed in.
     * @param register The fluid {@link DeferredRegister} of your mod.
     * @return A {@link FluidObject} which you can use to get either the source or flowing fluid.
     */
    public FluidObject build(DeferredRegister<Fluid> register) {
        still = register.register(name + "_still", () -> stillFluid.apply(properties()));
        flowing = register.register(name + "_flowing", () -> flowingFluid.apply(properties()));
        String modid = still.getId().getNamespace();
        List<FluidBuilder> newList;
        if (OBJECTS.get(modid) != null) {
            newList = new ArrayList<>(OBJECTS.get(modid));
        } else {
            newList = new ArrayList<>();
        }
        newList.add(this);
        OBJECTS.put(modid, newList);
        return new FluidObject(this);
    }

    /**
     * The bucket item of the fluid. Item must be called like so: () -> MyItems.FLUID_BUCKET.get()
     * @param bucket A {@link Supplier} of the bucket item.
     */
    public final FluidBuilder withBucket(Supplier<? extends Item> bucket) {
        this.bucket = bucket;
        return this;
    }

    /**
     * The block of the fluid
     * @param block A {@link Supplier} of the block
     */
    public final FluidBuilder withBlock(Supplier<? extends LiquidBlock> block) {
        this.block = block;
        return this;
    }

    /**
     * The explosion resistance of the fluid
     * @param resistance The explosion resistance
     */
    public final FluidBuilder withExplosionResistance(float resistance) {
        this.explosionResistance = resistance;
        return this;
    }

    /**
     * Whether the fluid can become an infinite source.
     */
    public final FluidBuilder withMultiplication(boolean canMultiply) {
        this.canMultiply = canMultiply;
        return this;
    }

    /**
     * How many levels the fluid will decrease every block.
     * @param lvlDecrease Levels per block
     */
    public final FluidBuilder withLevelDecrease(int lvlDecrease) {
        this.levelDecreasePerBlock = lvlDecrease;
        return this;
    }

    /**
     * How many blocks the fluid will attempt to find a slope (and move toward it).
     * @param slopeFindDistance How many blocks away it will search for the slope.
     */
    public final FluidBuilder withSlopeDistance(int slopeFindDistance) {
        this.slopeFindDistance = slopeFindDistance;
        return this;
    }

    /**
     * How fast this fluid ticks. Higher tick rates means slower fluid movement.
     * @param tickRate The amount of ticks.
     */
    public final FluidBuilder withTickRate(int tickRate) {
        this.tickRate = tickRate;
        return this;
    }

    //========================================Attributes==============================================

    /**
     * The texture location of the source fluid. By default, this is the water texture
     * @param texture The location of the texture including the modId, e.g. "modid:block/my_fluid"
     */
    public final FluidBuilder withStillTexture(String texture) {
        stillTexture = new ResourceLocation(texture);
        return this;
    }

    /**
     * The texture location of the flowing fluid. By default, this is the water texture
     * @param texture The location of the texture including the modId, e.g. "modid:block/my_fluid"
     */
    public final FluidBuilder withFlowingTexture(String texture) {
        flowingTexture = new ResourceLocation(texture);
        return this;
    }

    /**
     * The Color used by universal bucket and the ModelFluid baked model.
     * Note that this int includes the alpha so converting this to RGB with alpha would be
     *   float r = ((color >> 16) & 0xFF) / 255f; // red
     *   float g = ((color >> 8) & 0xFF) / 255f; // green
     *   float b = ((color >> 0) & 0xFF) / 255f; // blue
     *   float a = ((color >> 24) & 0xFF) / 255f; // alpha
     * @param color The color in hex, e.g. 0xFFFFFFFF
     */
    public final FluidBuilder withColor(int color) {
        this.color = color;
        return this;
    }

    /**
     * Density of the fluid - completely arbitrary;
     * negative density indicates that the fluid is lighter than air. Default value is approximately the real-life density of water in kg/m^3.
     * @param density The density in kg/m^3.
     */
    public final FluidBuilder withDensity(int density) {
        this.density = density;
        return this;
    }

    /**
     * Temperature of the fluid - completely arbitrary; higher temperature indicates that the fluid is
     * hotter than air.
     * @param temperature The temperature in kelvin.
     */
    public final FluidBuilder withTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    /**
     * This indicates that the fluid is gaseous.
     */
    public final FluidBuilder withGaseousForm() {
        this.gaseous = true;
        return this;
    }

    /**
     * The light level emitted by this fluid.
     * @param luminosity The light level emitted, default is 0.
     */
    public final FluidBuilder withLuminosity(int luminosity) {
        this.luminosity = luminosity;
        return this;
    }

    /**
     * Viscosity ("thickness") of the fluid - completely arbitrary; negative values are not
     * permissible.
     * Default value is approximately the real-life density of water in m/s^2 (x10^-3).
     *
     * Higher viscosity means that a fluid flows more slowly, like molasses.
     * Lower viscosity means that a fluid flows more quickly, like helium.
     * @param viscosity The viscosity in m/s^2.
     */
    public final FluidBuilder withViscosity(int viscosity) {
        this.viscosity = viscosity;
        return this;
    }

    /**
     * The overlay of the fluid.
     * @param rl The overlay location.
     */
    public final FluidBuilder withOverlay(ResourceLocation rl) {
        this.overlay = rl;
        return this;
    }

    /**
     * The rarity of the fluid.
     * @param rarity The rarity.
     */
    public final FluidBuilder withRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    /**
     * The sound this fluid will make when interacted with by buckets.
     * @param fill Fill sound event.
     * @param empty Empty sound event.
     */
    public final FluidBuilder withSound(SoundEvent fill, SoundEvent empty) {
        this.fill = fill;
        this.empty = empty;
        return this;
    }

    //================================================================================================

    /**
     * Adds an existing tag to this fluid.
     */
    @SafeVarargs
    public final FluidBuilder withExistingFluidTags(Tag.Named<Fluid>... tags) {
        for (Tag.Named<Fluid> tag : tags) {
            fluidTags.put(tag.getName().toString(), tag);
        }
        return this;
    }

    /**
     * Adds a new tag to this fluid.
     * @param tagName The name with modid, e.g. "forge:my_fluid"
     */
    public FluidBuilder withNewFluidTag(String tagName) {
        Tag.Named<Fluid> tag = FluidTags.createOptional(new ResourceLocation(tagName));
        fluidTags.put(tagName, tag);
        FLUID_TAGS.put(tagName, tag);
        return this;
    }

    /**
     * Adds a translation to this fluid in the en_us locale. If you wish to change locale use {@link #withTranslation(String, String)}
     * @param translation The name of the translated fluid, e.g. "My Fluid"
     */
    public FluidBuilder withTranslation(String translation) {
        this.locale.put("en_us", translation);
        return this;
    }

    /**
     * Adds a translation to this fluid in the given locale.
     * @param translation The name of the translated block, e.g. "My Fluid"
     * @param locale The localization this translation will be added to, e.g. "en_us"
     */
    public FluidBuilder withTranslation(String translation, String locale) {
        this.locale.put(locale, translation);
        return this;
    }

    public static class FluidObject {
        public RegistryObject<FlowingFluid> still;
        public RegistryObject<FlowingFluid> flowing;

        public FluidObject(FluidBuilder builder) {
            still = builder.getStill();
            flowing = builder.getFlowing();
        }

        public RegistryObject<FlowingFluid> getStill() {
            return still;
        }

        public RegistryObject<FlowingFluid> getFlowing() {
            return flowing;
        }
    }
}
