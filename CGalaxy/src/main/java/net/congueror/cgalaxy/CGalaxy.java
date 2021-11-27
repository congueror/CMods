package net.congueror.cgalaxy;

import net.congueror.cgalaxy.init.*;
import net.congueror.cgalaxy.util.CGConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("cgalaxy")
public class CGalaxy {

    public static final String MODID = "cgalaxy";
    public static final Logger LOGGER = LogManager.getLogger();

    //Persistent Data Constant Strings
    /**Integer*/
    public static final String PLAYER_TEMPERATURE = "Temperature";
    /**Float*/
    public static final String PLAYER_RADIATION = "Radiation";
    /**Double*/
    public static final String PLAYER_AIR_PRESSURE = "AirPressure";
    /**Integer*/
    public static final String LIVING_HEAT_TICK = "HeatTick";
    /**Integer*/
    public static final String LIVING_COLD_TICK = "ColdTick";
    /**Integer*/
    public static final String LIVING_OXYGEN_TICK = "OxygenTick";
    /**Integer*/
    public static final String LIVING_RADIATION_TICK = "RadiationTick";
    /**Integer*/
    public static final String ROCKET_POWERED = "Powered";
    /**Boolean*/
    public static final String ROCKET_LAUNCH = "Launch";
    /**Integer*/
    public static final String ITEM_GRAVITY = "ItemGravity";

    public CGalaxy() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CGConfig.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CGConfig.SERVER_CONFIG);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CGItemInit.ITEMS.register(modEventBus);
        CGBlockInit.BLOCKS.register(modEventBus);
        CGBlockEntityInit.BLOCK_ENTITY_TYPES.register(modEventBus);
        CGContainerInit.MENU_TYPES.register(modEventBus);
        CGRecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
        CGFluidInit.FLUIDS.register(modEventBus);
        CGEntityTypeInit.ENTITY_TYPES.register(modEventBus);
        CGCarverInit.CARVERS.register(modEventBus);
        CGFeatureInit.FEATURES.register(modEventBus);
        CGSoundInit.SOUNDS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
//TODO: Lunar Crop
//TODO: Moon Village/Villagers
//TODO: Moon Boss
//TODO: Space Station (Zero-G: 0 Gravity Attribute, delta movement x - 0.4 unless tethered or in air, ability to go up or down)

    public static ResourceLocation location(String texture) {
        return new ResourceLocation(CGalaxy.MODID, texture);
    }
}