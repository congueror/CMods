package net.congueror.clib.datagen;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.BlockModelDataGenerator;
import net.congueror.clib.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelDataGen extends BlockModelDataGenerator {

    public BlockModelDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CLib.MODID, exFileHelper);
    }
}
