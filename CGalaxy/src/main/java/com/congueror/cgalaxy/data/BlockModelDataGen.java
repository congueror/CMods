package com.congueror.cgalaxy.data;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.clib.data.BlockModelDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelDataGen extends BlockModelDataGenerator {

    public BlockModelDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CGalaxy.MODID, exFileHelper, BlockInit.BLOCKS, BlockInit.LAUNCH_PAD.get(), BlockInit.FUEL_REFINERY.get(), BlockInit.FUEL_LOADER.get());
    }

    @Override
    protected void registerStatesAndModels() {
        super.registerStatesAndModels();
        basicBlockItem(BlockInit.LAUNCH_PAD.get());
        basicBlockItem(BlockInit.FUEL_REFINERY.get());
        basicBlockItem(BlockInit.FUEL_LOADER.get());
    }
}
