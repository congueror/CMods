package com.congueror.cores;

import com.congueror.clib.CLib;
import com.congueror.cores.compat.tconstruct.ModifierInit;
import com.congueror.cores.init.BlockInit;
import com.congueror.cores.init.FluidInit;
import com.congueror.cores.init.ItemInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("cores")
@Mod.EventBusSubscriber(modid = "cores", bus = Mod.EventBusSubscriber.Bus.MOD)
public class COres {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "cores";
    public static COres instance;

    public COres() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        instance = this;
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        FluidInit.FLUIDS.register(modEventBus);

        if (CLib.isTConstructLoaded()) {
            ModifierInit.MODIFIERS.register(modEventBus);
        }

        MinecraftForge.EVENT_BUS.register(this);
    }
}
