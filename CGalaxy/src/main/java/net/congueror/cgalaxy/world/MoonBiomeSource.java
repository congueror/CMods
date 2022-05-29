package net.congueror.cgalaxy.world;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

public class MoonBiomeSource extends BiomeSource {
    public static final Codec<MoonBiomeSource> CODEC = RecordCodecBuilder.create((p_48644_) -> {
        return p_48644_.group(RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter((p_151890_) -> {
            return null;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((p_151888_) -> {
            return p_151888_.seed;
        })).apply(p_48644_, p_48644_.stable(MoonBiomeSource::new));
    });
    private final Registry<Biome> biomes;
    private final long seed;
    private final Holder<Biome> main;
    private final Holder<Biome> south;

    private final int x, z;

    protected MoonBiomeSource(Registry<Biome> biomes, long seed) {
        super(ImmutableList.of(biomes.getHolderOrThrow(CGBiomes.THE_MOON), biomes.getHolderOrThrow(CGBiomes.THE_MOON_SOUTH)));
        this.biomes = biomes;
        this.seed = seed;
        this.main = biomes.getHolderOrThrow(CGBiomes.THE_MOON);
        this.south = biomes.getHolderOrThrow(CGBiomes.THE_MOON_SOUTH);

        Random rand = new Random(seed);
        x = rand.nextInt(-3000, 3000);
        z = rand.nextInt(-3000, 3000);
    }

    @Nonnull
    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Nonnull
    @Override
    public BiomeSource withSeed(long pSeed) {
        return new MoonBiomeSource(this.biomes, pSeed);
    }

    @Nonnull
    @Override
    public Holder<Biome> getNoiseBiome(int pX, int pY, int pZ, Climate.@NotNull Sampler sampler) {//blockPos = quartPos / 4
        int xA = pX * 4;
        int zA = pZ * 4;
        if (xA < this.x + 100 &&
            xA > this.x - 100 &&
            zA < this.z + 100 &&
            zA > this.z - 100) {
            return this.south;
        } else {
            return this.main;
        }
    }
}