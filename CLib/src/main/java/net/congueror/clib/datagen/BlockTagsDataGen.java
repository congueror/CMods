package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.BlockTagsDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTagsDataGen extends BlockTagsDataProvider {
    public BlockTagsDataGen(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, CLib.MODID, existingFileHelper);
    }
}
