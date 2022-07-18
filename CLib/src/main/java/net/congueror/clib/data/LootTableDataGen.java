package net.congueror.clib.data;

import net.congueror.clib.CLib;
import net.congueror.clib.util.registry.data.LootTableDataProvider;
import net.minecraft.data.DataGenerator;

public class LootTableDataGen extends LootTableDataProvider {
    public LootTableDataGen(DataGenerator pGenerator) {
        super(pGenerator, CLib.MODID);
    }
}
