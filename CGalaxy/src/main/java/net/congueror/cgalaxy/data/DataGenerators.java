package net.congueror.cgalaxy.data;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.data.CGLangDataProvider;
import net.congueror.cgalaxy.util.CGGalacticObjects;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherDataEvent(GatherDataEvent e) {
        DataGenerator gen = e.getGenerator();
        CGGalacticObjects.init();

        if (e.includeServer()) {
            CGalaxy.LOGGER.debug("Starting Server Data Generators for CGalaxy");
            BlockTagsProvider blockTags = new BlockTagsDataGen(gen, e.getExistingFileHelper());
            gen.addProvider(new RecipeDataGen(gen));
            gen.addProvider(blockTags);
            gen.addProvider(new ItemTagsDataGen(gen, blockTags, e.getExistingFileHelper()));
            gen.addProvider(new FluidTagsDataGen(gen, e.getExistingFileHelper()));
            gen.addProvider(new LootTableDataGen(gen, CGalaxy.MODID));
        }
        if (e.includeClient()) {
            CGalaxy.LOGGER.debug("Starting Client Data Generators for CGalaxy");
            gen.addProvider(new ItemModelDataGen(gen, e.getExistingFileHelper()));
            gen.addProvider(new BlockModelDataGen(gen, e.getExistingFileHelper()));
            CGLangDataProvider.create(gen, CGalaxy.MODID);
            gen.addProvider(new LangDataGen(gen, "en_us"));
            gen.addProvider(new LangDataGen(gen, "el_gr"));
            gen.addProvider(new LangDataGen(gen, "el_cy"));
        }
    }
}
