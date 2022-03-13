package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.congueror.clib.util.registry.data.ItemTagsDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagsDataGen extends ItemTagsDataProvider {
    public ItemTagsDataGen(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, CLib.MODID, existingFileHelper);
    }
}
