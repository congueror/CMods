package com.congueror.cores.data;

import com.congueror.clib.data.ItemModelDataGenerator;
import com.congueror.cores.COres;
import com.congueror.cores.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelDataGen extends ItemModelDataGenerator {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, COres.MODID, existingFileHelper, ItemInit.ITEMS, ItemInit.RUBBER_SAPLING.get());
    }

    @Override
    protected void registerModels() {
        super.registerModels();
        texture(ItemInit.RUBBER_SAPLING.get(), "block/rubber_sapling");
    }
}
