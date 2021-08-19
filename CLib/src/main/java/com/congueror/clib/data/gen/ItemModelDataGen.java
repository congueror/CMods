package com.congueror.clib.data.gen;

import com.congueror.clib.CLib;
import com.congueror.clib.api.data.ItemModelDataGenerator;
import com.congueror.clib.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelDataGen extends ItemModelDataGenerator {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CLib.MODID, existingFileHelper, ItemInit.ITEMS, ItemInit.RUBBER_SAPLING.get());
    }

    @Override
    protected void registerModels() {
        super.registerModels();
        texture(ItemInit.RUBBER_SAPLING.get(), "block/rubber_sapling");
    }
}
