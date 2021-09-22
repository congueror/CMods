package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.FluidBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LangDataProvider extends LanguageProvider {

    String locale;
    String modid;

    /**
     * Call this method from your data generation event to generate all the language files from the builders.
     */
    public static void create(DataGenerator gen, String modid) {
        List<String> locales = new ArrayList<>();
        if (ItemBuilder.OBJECTS.get(modid) != null)
            ItemBuilder.OBJECTS.get(modid).stream().map(itemBuilder -> itemBuilder.locale).forEach(stringStringMap -> {
                for (Map.Entry<String, String> string : stringStringMap.entrySet()) {
                    locales.add(string.getKey());
                }
            });
        if (ItemBuilder.OBJECTS.get(modid) != null)
            BlockBuilder.OBJECTS.get(modid).stream().map(blockBuilder -> blockBuilder.locale).forEach(stringStringMap -> {
                for (Map.Entry<String, String> string : stringStringMap.entrySet()) {
                    locales.add(string.getKey());
                }
            });
        if (FluidBuilder.OBJECTS.get(modid) != null)
            FluidBuilder.OBJECTS.get(modid).stream().map(fluidBuilder -> fluidBuilder.locale).forEach(stringStringMap -> {
                for (Map.Entry<String, String> string : stringStringMap.entrySet()) {
                    locales.add(string.getKey());
                }
            });
        locales.stream().distinct().forEach(s -> gen.addProvider(new LangDataProvider(gen, modid, s)));
    }

    public LangDataProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
        this.modid = modid;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        if (ItemBuilder.OBJECTS.get(modid) != null)
            ItemBuilder.OBJECTS.get(modid).stream().filter(itemBuilder -> itemBuilder.locale.containsKey(locale)).forEach(builder -> add(builder.getItem(), builder.locale.get(locale)));
        if (BlockBuilder.OBJECTS.get(modid) != null)
            BlockBuilder.OBJECTS.get(modid).stream().filter(blockBuilder -> blockBuilder.locale.containsKey(locale)).forEach(blockBuilder -> add(blockBuilder.block, blockBuilder.locale.get(locale)));
        if (FluidBuilder.OBJECTS.get(modid) != null)
            FluidBuilder.OBJECTS.get(modid).stream().filter(fluidBuilder -> fluidBuilder.locale.containsKey(locale)).forEach(fluidBuilder -> add(fluidBuilder.getStill().get().getAttributes().getTranslationKey(), fluidBuilder.locale.get(locale)));
    }
}
