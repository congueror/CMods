package net.congueror.clib.util.registry.data;

import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.builders.FluidBuilder;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LangDataProvider extends LanguageProvider {

    protected String locale;
    protected String modid;

    protected static List<String> locales = new ArrayList<>();

    /**
     * Call this method from your data generation event to generate all the language files from the builders.
     */
    public static void create(DataGenerator gen, String modid) {
        if (ItemBuilder.OBJECTS.get(modid) != null)
            ItemBuilder.OBJECTS.get(modid).stream().map(itemBuilder -> itemBuilder.locale).forEach(stringStringMap -> {
                for (Map.Entry<String, String> string : stringStringMap.entrySet()) {
                    locales.add(string.getKey());
                }
            });
        if (BlockBuilder.OBJECTS.get(modid) != null)
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
            ItemBuilder.OBJECTS.get(modid).stream().filter(itemBuilder -> itemBuilder.locale.containsKey(locale)).forEach(builder -> add(builder.regObject.get(), builder.locale.get(locale)));
        if (BlockBuilder.OBJECTS.get(modid) != null)
            BlockBuilder.OBJECTS.get(modid).stream().filter(blockBuilder -> blockBuilder.locale.containsKey(locale))
                    .forEach(blockBuilder -> add(blockBuilder.regObject.get(), blockBuilder.locale.get(locale)));
        if (FluidBuilder.OBJECTS.get(modid) != null)
            FluidBuilder.OBJECTS.get(modid).stream().filter(fluidBuilder -> fluidBuilder.locale.containsKey(locale)).forEach(fluidBuilder -> add(fluidBuilder.getStill().get().getAttributes().getTranslationKey(), fluidBuilder.locale.get(locale)));
    }
}
