package com.congueror.clib.data.gen;

import com.congueror.clib.CLib;
import com.congueror.clib.api.data.BlockTagsDataGenerator;
import com.congueror.clib.util.CLTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTagsDataGen extends BlockTagsDataGenerator {
    public BlockTagsDataGen(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, CLTags.tags, CLib.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        registerTagWrapper();
    }
}
