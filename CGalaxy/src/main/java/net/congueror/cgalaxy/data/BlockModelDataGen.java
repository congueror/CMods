package net.congueror.cgalaxy.data;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.data.BlockModelDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelDataGen extends BlockModelDataProvider {
    public BlockModelDataGen(DataGenerator gen, ExistingFileHelper fileHelper) {
        super(gen, CGalaxy.MODID, fileHelper);
    }
}
