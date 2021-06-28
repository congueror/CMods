package com.congueror.cgalaxy;

import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.clib.CLib;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("cgalaxy")
@Mod.EventBusSubscriber(modid = "cgalaxy", bus = Mod.EventBusSubscriber.Bus.MOD)
public class CGalaxy {

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "cgalaxy";
    public static CGalaxy instance;

    public CGalaxy() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CGalaxy.instance = this;
        BlockInit.BLOCKS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}