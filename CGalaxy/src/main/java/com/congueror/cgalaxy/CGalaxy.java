package com.congueror.cgalaxy;

import com.congueror.cgalaxy.init.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("cgalaxy")
@Mod.EventBusSubscriber(modid = "cgalaxy", bus = Mod.EventBusSubscriber.Bus.MOD)
public class CGalaxy {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "cgalaxy";
    public static CGalaxy instance;

    public CGalaxy() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CGalaxy.instance = this;
        BlockInit.BLOCKS.register(modEventBus);
        EntityTypeInit.ENTITY_TYPES.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(Keybinds.class);
    }
}