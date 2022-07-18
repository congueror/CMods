package net.congueror.clib.data;

import net.congueror.clib.CLib;
import net.congueror.clib.util.registry.data.BlockTagsDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTagsDataGen extends BlockTagsDataProvider {
    public BlockTagsDataGen(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, CLib.MODID, existingFileHelper);
    }
}
