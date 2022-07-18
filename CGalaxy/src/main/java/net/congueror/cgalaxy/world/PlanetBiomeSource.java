package net.congueror.cgalaxy.world;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class PlanetBiomeSource extends BiomeSource {
    public static final Codec<PlanetBiomeSource> CODEC = RecordCodecBuilder.create((p_48644_) ->
            p_48644_.group(
                    RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter((p_151890_) -> null),
                    Biome.CODEC.fieldOf("south").forGetter(source -> source.south),
                    Codec.INT.fieldOf("maxX").forGetter(planetBiomeSource -> planetBiomeSource.maxX),
                    Codec.INT.fieldOf("maxZ").forGetter(planetBiomeSource -> planetBiomeSource.maxZ),
                    ExtraCodecs.nonEmptyList(RecordCodecBuilder.<Pair<Climate.ParameterPoint, Holder<Biome>>>create((p_187078_) ->
                                    p_187078_.group(
                                                    Climate.ParameterPoint.CODEC.fieldOf("parameters").forGetter(Pair::getFirst),
                                                    Biome.CODEC.fieldOf("biome").forGetter(Pair::getSecond))
                                            .apply(p_187078_, Pair::of)).listOf())
                            .xmap(Climate.ParameterList::new, Climate.ParameterList::values).fieldOf("biomes").forGetter((p_187080_) -> p_187080_.parameters)
            ).apply(p_48644_, p_48644_.stable(PlanetBiomeSource::new)));

    private final Climate.ParameterList<Holder<Biome>> parameters;
    private final Registry<Biome> biomes;
    private final Holder<Biome> south;
    private final int maxX, maxZ;

    private final int x, z;

    public static final Map<ResourceKey<Biome>, int[]> LOCATIONS = new HashMap<>();

    protected PlanetBiomeSource(Registry<Biome> biomes, Holder<Biome> south, int maxX, int maxZ, Climate.ParameterList<Holder<Biome>> params) {
        super(Stream.concat(params.values().stream().map(Pair::getSecond), Stream.of(south)));
        this.parameters = params;
        this.biomes = biomes;
        this.south = south;
        this.maxX = maxX;
        this.maxZ = maxZ;

        Random rand = new Random();
        x = rand.nextInt(-maxX, maxX);
        z = rand.nextInt(-maxZ, maxZ);
        CGalaxy.LOGGER.info("Created {} at x:{}, z:{}", south.value().getRegistryName(), x, z);

        LOCATIONS.put(south.unwrapKey().get(), new int[] {x, z});
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long pSeed) {
        return this;
    }

    @Override
    public Holder<Biome> getNoiseBiome(int pX, int pY, int pZ, Climate.@NotNull Sampler sampler) {
        int xA = pX * 4;
        int zA = pZ * 4;
        if (xA < this.x + 300 &&
                xA > this.x - 300 &&
                zA < this.z + 300 &&
                zA > this.z - 300) {
            return this.south;
        } else {
            return getNoiseBiome(sampler.sample(pX, pY, pZ));
        }
    }

    public Holder<Biome> getNoiseBiome(Climate.TargetPoint p_204270_) {
        return this.parameters.findValue(p_204270_);
    }
}
