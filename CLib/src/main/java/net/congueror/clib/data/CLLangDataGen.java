package net.congueror.clib.data;

import net.congueror.clib.CLib;
import net.congueror.clib.util.registry.data.LangDataProvider;
import net.minecraft.data.DataGenerator;

public class CLLangDataGen extends LangDataProvider {
    public CLLangDataGen(DataGenerator gen, String locale) {
        super(gen, CLib.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        super.addTranslations();
        if (locale.equals("en_us")) {
            add("credits", "me :)");

            add("recipe.cgalaxy.solar_energy", "Solar Energy");

            add("itemGroup.cresources", "CResources");
            add("itemGroup.cassortments", "CAssortments");
            add("itemGroup.cgalaxy", "CGalaxy");
            add("itemGroup.cmachinery", "CMachinery");

            add("tooltip.clib.block_tags", "Block Tags");
            add("tooltip.clib.item_tags", "Item Tags");
            add("tooltip.clib.nbt_tags", "NBT Tags");
            add("tooltip.clib.hold_ctrl_for_tags", "Hold %1$s for Tags");

            add("key.clib.fluid", "Fluid");
            add("key.clib.empty", "Empty");
            add("key.clib.energy_percent", "Energy");
            add("key.clib.energy_usage", "Usage");
            add("key.clib.energy_generation", "Generation");
            add("key.clib.working", "Working...");
            add("key.clib.idle", "Idle.");
            add("key.clib.error_invalid_recipe", "Invalid Recipe!");
            add("key.clib.error_insufficient_energy", "Insufficient Energy!");
            add("key.clib.error_insufficient_ingredients", "Insufficient Ingredients!");
            add("key.clib.error_no_storage", "Not enough storage.");
            add("key.clib.upgrade_speed1", "Processing: Increases the processing speed of the machine.");
            add("key.clib.upgrade_speed2", "Generating: Increases energy outcome.");

            add("material.clib.tin", "Tin");
            add("material.clib.aluminum", "Aluminum");
            add("material.clib.nickel", "Nickel");
            add("material.clib.invar", "Invar");
            add("material.clib.platinum", "Platinum");

            add("modifier.clib.magnetic", "Magnetic");
            add("modifier.clib.magnetic.flavor", "Ferromagnetism!");
            add("modifier.clib.magnetic.description", "Uses durability to attract nearby items toward the tool.");
        } else if (locale.equals("zh_cn")) {
            add("credits", "pyking233(34%)");
        }

    }
}