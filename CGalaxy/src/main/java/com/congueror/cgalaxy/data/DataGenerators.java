package com.congueror.cgalaxy.data;

import com.congueror.cgalaxy.CGalaxy;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        CGalaxy.LOGGER.debug("Starting Server Data Generators");
        DataGenerator generator = event.getGenerator();

        if (event.includeServer()) {
            //BlockTagsProvider blockTags = new BlockTagsDataGen(generator, event.getExistingFileHelper());
            //generator.addProvider(new RecipeDataGen(generator));
            //generator.addProvider(blockTags);
            //generator.addProvider(new ItemTagsDataGen(generator, blockTags, event.getExistingFileHelper()));
            //generator.addProvider(new LootTableDataGen(generator));
        }
        if (event.includeClient()) {
            CGalaxy.LOGGER.debug("Starting Client Data Generators");
            generator.addProvider(new ItemModelDataGen(generator, event.getExistingFileHelper()));
            generator.addProvider(new BlockModelDataGen(generator, event.getExistingFileHelper()));
        }
    }
}

