package net.congueror.cgalaxy.world.structures;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGStructureInit;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StrongholdConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CGStructures {

    public static ConfiguredStructureFeature<?, ?> LUNAR_VILLAGE = CGStructureInit.LUNAR_VILLAGE.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(CGalaxy.MODID, "lunar_village"), LUNAR_VILLAGE);
    }

    private static Method GETCODEC_METHOD;

    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel serverLevel) {
            ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();

            // Skip superflat worlds to prevent issues with it.
            if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            StructureSettings worldStructureConfig = chunkGenerator.getSettings();


            //////////// DIMENSION BASED STRUCTURE SPAWNING ////////////

            /*
             * Skip Terraforged's chunk generator as they are a special case of a mod locking down their chunkgenerator.
             * They will handle your structure spacing for your if you add to Registry.NOISE_GENERATOR_SETTINGS_REGISTRY in your structure's registration.
             * This here is done with reflection as this tutorial is not about setting up and using Mixins.
             * If you are using mixins, you can call the codec method with an invoker mixin instead of using reflection.
             */
            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkGenerator));
                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                CGalaxy.LOGGER.error("Was unable to check if " + serverLevel.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            /*
             * Makes sure this chunkgenerator and datapack dimensions can spawn our structure.
             *
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             */
            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(worldStructureConfig.structureConfig());
            tempMap.putIfAbsent(CGStructureInit.LUNAR_VILLAGE.get(), StructureSettings.DEFAULTS.get(CGStructureInit.LUNAR_VILLAGE.get()));
            worldStructureConfig.structureConfig = tempMap;

            /*
             * The above three lines can be changed to do dimension blacklists/whitelist for your structure.
             * (Don't forget to attempt to remove your structure too from the map if you are blacklisting that dimension in case it already has the structure)
             *
             * If you do start whitelisting/blacklisting by dimensions instead of the default adding the spacing to all dimensions,
             * you may want to deep copy the structureConfig field first due to the fact that two dimensions using minecraft:overworld noise setting shares the same instance.
             * The deep copying would let you only add to one dimension and not the other instead of your changes applying to both dimensions together at same time.
             * https://github.com/TelepathicGrunt/RepurposedStructures/blob/latest-released/src/main/java/com/telepathicgrunt/repurposedstructures/misc/NoiseSettingsDeepCopier.java
             */
        }
    }

    public static StructureSettings deepCopyDimensionStructuresSettings(StructureSettings settings) {
        // Grab old copy of stronghold spacing settings
        StrongholdConfiguration oldStrongholdSettings = settings.stronghold();

        // Make a deep copy and wrap it in an optional as StructureSettings requires an optional
        Optional<StrongholdConfiguration> newStrongholdSettings = oldStrongholdSettings == null ?
                Optional.empty() :
                Optional.of(new StrongholdConfiguration(
                        oldStrongholdSettings.distance(),
                        oldStrongholdSettings.spread(),
                        oldStrongholdSettings.count()));

        // Create new deep copied StructureSettings
        // We do not need to create a new structure spacing map instance here as our patch into
        // StructureSettings will already create the new map instance for us.
        StructureSettings newStructureSettings = new StructureSettings(newStrongholdSettings, settings.structureConfig());
        newStructureSettings.configuredStructures = ImmutableMap.copyOf(settings.configuredStructures);
        return newStructureSettings;
    }
}
