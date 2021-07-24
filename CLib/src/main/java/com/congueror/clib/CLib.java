package com.congueror.clib;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("clib")
@Mod.EventBusSubscriber(modid = "clib", bus = Mod.EventBusSubscriber.Bus.MOD)
public class CLib {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "clib";
    public static CLib instance;

    public CLib() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CLib.instance = this;

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isModLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public static boolean isCOresLoaded() {
        return isModLoaded("cores");
    }

    public static boolean isCGalaxyLoaded() {
        return isModLoaded("cgalaxy");
    }
}