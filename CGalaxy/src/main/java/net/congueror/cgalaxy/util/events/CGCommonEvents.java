package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.world.Dimensions;
import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.FluidBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

public class CGCommonEvents {
    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent e) {
            CGNetwork.registerMessages();
            e.enqueueWork(Dimensions::setupDims);
        }

        @SubscribeEvent
        public static void interModProcess(InterModProcessEvent e) {
            FluidBuilder.registerBlockFluidFix();
        }
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {

    }
}
