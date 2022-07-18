package net.congueror.cgalaxy;

import net.congueror.cgalaxy.util.registry.SpaceStationBlueprint;
import net.congueror.cgalaxy.blocks.space_station_creator.DefaultBlueprint;
import net.congueror.cgalaxy.init.*;
import net.congueror.cgalaxy.util.CGConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

@Mod("cgalaxy")
public class CGalaxy {

    public static final String MODID = "cgalaxy";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ResourceLocation SPACE_STATION_BLUEPRINT = location("space_station_blueprint");
    public static final DeferredRegister<SpaceStationBlueprint> SPACE_STATION_BLUEPRINTS = DeferredRegister.create(SPACE_STATION_BLUEPRINT, MODID);
    public static final Supplier<IForgeRegistry<SpaceStationBlueprint>> REGISTRY = SPACE_STATION_BLUEPRINTS.makeRegistry(SpaceStationBlueprint.class, RegistryBuilder::new);

    public static final RegistryObject<SpaceStationBlueprint> DEFAULT_SPACE_STATION = SPACE_STATION_BLUEPRINTS.register("default", () -> new DefaultBlueprint(location("space_station/space_station_1")));

    public CGalaxy() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CGConfig.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CGConfig.SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CGConfig.COMMON_CONFIG);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CGItemInit.ITEMS.register(modEventBus);
        CGBlockInit.BLOCKS.register(modEventBus);
        CGBlockEntityInit.BLOCK_ENTITY_TYPES.register(modEventBus);
        CGContainerInit.MENU_TYPES.register(modEventBus);
        CGRecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
        CGRecipeSerializerInit.RECIPE_TYPES.register(modEventBus);
        CGFluidInit.FLUIDS.register(modEventBus);
        CGEntityTypeInit.ENTITY_TYPES.register(modEventBus);
        CGCarverInit.CARVERS.register(modEventBus);
        CGFeatureInit.FEATURES.register(modEventBus);
        CGSoundInit.SOUNDS.register(modEventBus);
        CGStructureInit.STRUCTURES.register(modEventBus);
        CGEnchantmentInit.ENCHANTMENTS.register(modEventBus);

        SPACE_STATION_BLUEPRINTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation location(String texture) {
        return new ResourceLocation(CGalaxy.MODID, texture);
    }
}