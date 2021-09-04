package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.ItemTagsDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagsDataGen extends ItemTagsDataGenerator {
    public ItemTagsDataGen(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, CLib.MODID, existingFileHelper);
    }
}
