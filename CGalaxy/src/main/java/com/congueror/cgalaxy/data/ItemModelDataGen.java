package com.congueror.cgalaxy.data;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.ItemInit;
import com.congueror.clib.api.data.ItemModelDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelDataGen extends ItemModelDataGenerator {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CGalaxy.MODID, existingFileHelper, ItemInit.ITEMS, ItemInit.ROCKET_TIER_1.get());
    }

    @Override
    protected void registerModels() {
        super.registerModels();
    }
}
