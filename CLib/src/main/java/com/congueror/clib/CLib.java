package com.congueror.clib;

import com.congueror.clib.compat.tconstruct.ModifierInit;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.FluidInit;
import com.congueror.clib.init.ItemInit;
import com.congueror.clib.util.CLTags;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CLib.MODID)
@Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CLib {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "clib";
    public static CLib instance;

    public CLib() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CLib.instance = this;
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        FluidInit.FLUIDS.register(modEventBus);

        if (isTConstructLoaded()) {
            ModifierInit.MODIFIERS.register(modEventBus);
        }

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