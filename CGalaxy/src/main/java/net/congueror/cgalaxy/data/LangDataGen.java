package net.congueror.cgalaxy.data;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.data.LangDataProvider;
import net.minecraft.data.DataGenerator;

public class LangDataGen extends LangDataProvider {
    public LangDataGen(DataGenerator gen, String locale) {
        super(gen, CGalaxy.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        super.addTranslations();
        add("chat.cgalaxy.launch_off", " is launching off to space!");
        add("key.cgalaxy.empty", "Empty");
        add("tooltip.cgalaxy.screen.energy_percent", "Energy");
        add("tooltip.cgalaxy.screen.energy_usage", "Usage");
    }
}
