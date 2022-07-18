package net.congueror.clib.data;

import net.congueror.clib.CLib;
import net.congueror.clib.util.registry.data.BlockModelDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelDataGen extends BlockModelDataProvider {

    public BlockModelDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CLib.MODID, exFileHelper);
    }
}
