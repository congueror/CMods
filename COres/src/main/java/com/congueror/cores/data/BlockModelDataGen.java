package com.congueror.cores.data;

import com.congueror.clib.data.BlockModelDataGenerator;
import com.congueror.cores.COres;
import com.congueror.cores.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelDataGen extends BlockModelDataGenerator {

    public BlockModelDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, COres.MODID, exFileHelper, BlockInit.BLOCKS,
                BlockInit.TITANIUM_ORE.get(),
                BlockInit.RUBBER_LOG.get(),
                BlockInit.RUBBER_WOOD.get(),
                BlockInit.RUBBER_STRIPPED_LOG.get(),
                BlockInit.RUBBER_STRIPPED_WOOD.get(),
                BlockInit.RUBBER_SAPLING.get());
    }

    @Override
    protected void registerStatesAndModels() {
        super.registerStatesAndModels();
        cubeColumnBlock(BlockInit.TITANIUM_ORE.get());
        logBlock(BlockInit.RUBBER_LOG.get());
        logBlock(BlockInit.RUBBER_STRIPPED_LOG.get());
        axisBlock(BlockInit.RUBBER_WOOD.get(), "rubber_log");
        axisBlock(BlockInit.RUBBER_STRIPPED_WOOD.get(), "rubber_log");
        crossBlock(BlockInit.RUBBER_SAPLING.get());
    }
}
