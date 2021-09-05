package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LangDataGenerator extends LanguageProvider {

    String locale;

    /**
     * Call this method from your data generation event to generate all the language files from the builders.
     */
    public static void create(DataGenerator gen, String modid) {
        List<String> locales = new ArrayList<>();
        ItemBuilder.OBJECTS.stream().map(itemBuilder -> itemBuilder.locale).forEach(stringStringMap -> {
            for (Map.Entry<String, String> string : stringStringMap.entrySet()) {
                locales.add(string.getKey());
            }
        });
        BlockBuilder.OBJECTS.stream().map(blockBuilder -> blockBuilder.locale).forEach(stringStringMap -> {
            for (Map.Entry<String, String> string : stringStringMap.entrySet()) {
                locales.add(string.getKey());
            }
        });
        locales.forEach(s -> new LangDataGenerator(gen, modid, s));
    }

    public LangDataGenerator(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        ItemBuilder.OBJECTS.stream().filter(itemBuilder -> itemBuilder.locale.containsKey(locale)).forEach(builder -> add(builder.getItem(), builder.locale.get(locale)));
        BlockBuilder.OBJECTS.stream().filter(blockBuilder -> blockBuilder.locale.containsKey(locale)).forEach(blockBuilder -> add(blockBuilder.getBlock(), blockBuilder.locale.get(locale)));
    }
}
