package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.LangDataGenerator;
import net.minecraft.data.DataGenerator;

public class LangDataGen extends LangDataGenerator {
    public LangDataGen(DataGenerator gen, String locale) {
        super(gen, CLib.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        super.addTranslations();

    }
}
