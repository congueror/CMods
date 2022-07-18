package net.congueror.cgalaxy.util.registry.data;

import net.congueror.cgalaxy.util.json_managers.GalacticEntryManager;
import net.congueror.clib.util.registry.data.LangDataProvider;
import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.builders.FluidBuilder;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class CGLangDataProvider extends LangDataProvider {
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
        if (GalacticEntryManager.OBJECTS.keySet().stream().anyMatch(object -> object.getId().getNamespace().equals(modid))) {
            GalacticEntryManager.OBJECTS.keySet().stream().filter(object -> object.getId().getNamespace().equals(modid))
                    .map(object -> object.getType().locale).forEach(stringStringMap -> {
                        for (var string : stringStringMap.entrySet()) {
                            locales.add(string.getKey());
                        }
                    });
        }
        locales.stream().distinct().forEach(s -> gen.addProvider(new LangDataProvider(gen, modid, s)));
    }

    public CGLangDataProvider(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        super.addTranslations();
        if (GalacticEntryManager.OBJECTS.keySet().stream().anyMatch(object -> object.getId().getNamespace().equals(modid)))
            GalacticEntryManager.OBJECTS.keySet().stream().filter(object -> object.getId().getNamespace().equals(modid))
                    .filter(object -> object.getType().locale.containsKey(locale))
                    .forEach(object -> add(object.getTranslationKey(), object.getType().locale.get(locale)));
    }
}
