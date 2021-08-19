package com.congueror.cgalaxy.data;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.clib.api.data.BlockModelDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelDataGen extends BlockModelDataGenerator {

    public BlockModelDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CGalaxy.MODID, exFileHelper, BlockInit.BLOCKS,
                BlockInit.LAUNCH_PAD.get(),
                BlockInit.FUEL_REFINERY.get(),
                BlockInit.FUEL_LOADER.get(),
                BlockInit.MOON_STONE_SLAB.get(),
                BlockInit.MOON_STONE_STAIRS.get());
    }

    @Override
    protected void registerStatesAndModels() {
        super.registerStatesAndModels();
        basicBlockItem(BlockInit.LAUNCH_PAD.get());
        basicBlockItem(BlockInit.FUEL_REFINERY.get());
        basicBlockItem(BlockInit.FUEL_LOADER.get());
        slabBlock(BlockInit.MOON_STONE_SLAB.get(), "moon_stone");
        stairsBlock(BlockInit.MOON_STONE_STAIRS.get(), "moon_stone");
        slabBlock(BlockInit.MOON_COBBLESTONE_SLAB.get(), "moon_cobblestone");
        stairsBlock(BlockInit.MOON_COBBLESTONE_STAIRS.get(), "moon_cobblestone");
        slabBlock(BlockInit.MOON_STONE_BRICKS_SLAB.get(), "moon_stone_bricks");
        stairsBlock(BlockInit.MOON_STONE_BRICKS_STAIRS.get(), "moon_stone_bricks");
    }
}
