package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LangDataGenerator extends LanguageProvider {

    String modid;

    public LangDataGenerator(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
        this.modid = modid;
    }

    @Override
    protected void addTranslations() {
        ItemBuilder.OBJECTS.forEach(builder -> {

        });
    }
}
