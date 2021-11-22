package net.congueror.clib;

import net.congueror.clib.init.CLBlockInit;
import net.congueror.clib.init.CLItemInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CLib.MODID)
public class CLib {

    public static final String MODID = "clib";
    public static final Logger LOGGER = LogManager.getLogger();
    public static CLib instance;

    public CLib() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CLib.instance = this;
        CLItemInit.ITEMS.register(modEventBus);
        CLBlockInit.BLOCKS.register(modEventBus);
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
    //Hello fatty
}
