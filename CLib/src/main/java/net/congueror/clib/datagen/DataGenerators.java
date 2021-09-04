package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        if (event.includeServer()) {
            CLib.LOGGER.debug("Starting Server Data Generators for CLib");
            BlockTagsProvider blockTags = new BlockTagsDataGen(generator, event.getExistingFileHelper());
            generator.addProvider(new RecipeDataGen(generator));
            generator.addProvider(blockTags);
            generator.addProvider(new ItemTagsDataGen(generator, blockTags, event.getExistingFileHelper()));
            generator.addProvider(new LootTableDataGen(generator));
        }
        if (event.includeClient()) {
            CLib.LOGGER.debug("Starting Client Data Generators for CLib");
            generator.addProvider(new ItemModelDataGen(generator, event.getExistingFileHelper()));
            generator.addProvider(new BlockModelDataGen(generator, event.getExistingFileHelper()));
            generator.addProvider(new LangDataGen(generator, "en_us"));
        }
    }
}
