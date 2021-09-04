package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.ItemModelDataGenerator;
import net.congueror.clib.init.BlockInit;
import net.congueror.clib.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelDataGen extends ItemModelDataGenerator {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CLib.MODID, existingFileHelper);
    }
}
