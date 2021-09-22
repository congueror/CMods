package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.LangDataProvider;
import net.minecraft.data.DataGenerator;

public class LangDataGen extends LangDataProvider {
    public LangDataGen(DataGenerator gen, String locale) {
        super(gen, CLib.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        super.addTranslations();
        add("itemGroup.clibitems", "CLItems");
        add("itemGroup.clibblocks", "CLBlocks");
        add("itemGroup.cgalaxy", "CGalaxy");
        add("itemGroup.cmachinery", "CMachinery");

        add("tooltip.clib.block_tags", "Block Tags");
        add("tooltip.clib.item_tags", "Item Tags");
        add("tooltip.clib.nbt_tags", "NBT Tags");
        add("tooltip.clib.hold_ctrl_for_tags", "Hold %1$s for Tags");

        add("key.clib.working", "Working...");
        add("key.clib.idle", "Idle.");
        add("key.clib.error_invalid_recipe", "Invalid Recipe!");
        add("key.clib.error_insufficient_energy", "Insufficient Energy!");

        add("material.clib.tin", "Tin");
        add("material.clib.aluminum", "Aluminum");
        add("material.clib.nickel", "Nickel");
        add("material.clib.invar", "Invar");
        add("material.clib.platinum", "Platinum");

        add("modifier.clib.magnetic", "Magnetic");
        add("modifier.clib.magnetic.flavor", "Ferromagnetism!");
        add("modifier.clib.magnetic.description", "Uses durability to attract nearby items toward the tool.");
    }
}
