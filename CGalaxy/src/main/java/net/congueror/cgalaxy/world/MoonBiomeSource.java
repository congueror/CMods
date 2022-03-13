package net.congueror.cgalaxy.world;

import com.google.common.collect.ImmutableList;
import com.mojang.math.Quaternion;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

public class MoonBiomeSource extends BiomeSource {
    public static final Codec<MoonBiomeSource> CODEC = RecordCodecBuilder.create((p_48644_) -> {
        return p_48644_.group(RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((p_151890_) -> {
            return p_151890_.biomes;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((p_151888_) -> {
            return p_151888_.seed;
        })).apply(p_48644_, p_48644_.stable(MoonBiomeSource::new));
    });
    private final Registry<Biome> biomes;
    private final long seed;
    private final Biome main;
    private final Biome south;

    private final int x, z;

    protected MoonBiomeSource(Registry<Biome> biomes, long seed) {
        super(ImmutableList.of(biomes.getOrThrow(CGBiomes.THE_MOON), biomes.getOrThrow(CGBiomes.THE_MOON_SOUTH)));
        this.biomes = biomes;
        this.seed = seed;
        this.main = biomes.getOrThrow(CGBiomes.THE_MOON);
        this.south = biomes.getOrThrow(CGBiomes.THE_MOON_SOUTH);

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
    public Biome getNoiseBiome(int pX, int pY, int pZ, Climate.@NotNull Sampler sampler) {//blockPos = quartPos / 4
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