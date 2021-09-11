package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.LootTableDataProvider;
import net.minecraft.data.DataGenerator;

public class LootTableDataGen extends LootTableDataProvider {
    public LootTableDataGen(DataGenerator pGenerator) {
        super(pGenerator, CLib.MODID);
    }
}
