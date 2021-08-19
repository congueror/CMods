package com.congueror.clib.data.gen;

import com.congueror.clib.CLib;
import com.congueror.clib.api.data.ItemTagsDataGenerator;
import com.congueror.clib.util.CLTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagsDataGen extends ItemTagsDataGenerator {

    public ItemTagsDataGen(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, CLTags.tags, blockTagProvider, CLib.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        registerTagWrapper();
    }
}
