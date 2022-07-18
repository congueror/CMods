package net.congueror.clib.data;

import net.congueror.clib.CLib;
import net.congueror.clib.util.registry.data.ItemModelDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelDataGen extends ItemModelDataProvider {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CLib.MODID, existingFileHelper);
    }
}
