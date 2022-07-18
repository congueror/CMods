package net.congueror.clib;

import net.congueror.clib.init.*;
import net.congueror.clib.util.CLConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CLib.MODID)
public class CLib {

    public static final String MODID = "clib";
    public static final Logger LOGGER = LogManager.getLogger();

    public CLib() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLConfig.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CLConfig.SERVER_CONFIG);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CLItemInit.ITEMS.register(modEventBus);
        CLBlockInit.BLOCKS.register(modEventBus);
        CLRecipeSerializerInit.RECIPE_SERIALIZERS.register(modEventBus);
        CLRecipeSerializerInit.RECIPE_TYPES.register(modEventBus);
        CLContainerInit.MENU_TYPES.register(modEventBus);

        CLMaterialInit.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isModLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public static boolean isCGalaxyLoaded() {
        return isModLoaded("cgalaxy");
    }

    public static boolean isTConstructLoaded() {
        return isModLoaded("tconstruct");
    }
}
