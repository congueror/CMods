package com.congueror.clib.data;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockModelDataGen extends BlockStateProvider {

    public BlockModelDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CLib.MODID, exFileHelper);
    }

    public void basicBlock(Block block) {
        simpleBlock(block);
        simpleBlockItem(block, models().getExistingFile(new ResourceLocation(CLib.MODID, block.getRegistryName().getPath())));
    }

    @Override
    protected void registerStatesAndModels() {
        BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            if (block == BlockInit.TITANIUM_ORE.get()) {
                simpleBlock(block, models().cubeColumn(block.getRegistryName().getPath()
                        , new ResourceLocation(CLib.MODID, "block/titanium_ore_side")
                        , new ResourceLocation(CLib.MODID, "block/titanium_ore_top")));
                simpleBlockItem(block, models().getExistingFile(new ResourceLocation(CLib.MODID, block.getRegistryName().getPath())));
            } else if (block == BlockInit.RUBBER_LOG.get() || block == BlockInit.RUBBER_STRIPPED_LOG.get()) {
                logBlock((RotatedPillarBlock) block);
                simpleBlockItem(block, models().getExistingFile(new ResourceLocation(CLib.MODID, block.getRegistryName().getPath())));
            } else if (block == BlockInit.RUBBER_WOOD.get()) {
                axisBlock((RotatedPillarBlock) block, new ResourceLocation(CLib.MODID, ModelProvider.BLOCK_FOLDER + "/rubber_log"), new ResourceLocation(CLib.MODID, ModelProvider.BLOCK_FOLDER + "/rubber_log"));
                simpleBlockItem(block, models().getExistingFile(new ResourceLocation(CLib.MODID, block.getRegistryName().getPath())));
            } else if (block == BlockInit.RUBBER_STRIPPED_WOOD.get()) {
                axisBlock((RotatedPillarBlock) block, new ResourceLocation(CLib.MODID, ModelProvider.BLOCK_FOLDER + "/stripped_rubber_log"), new ResourceLocation(CLib.MODID, ModelProvider.BLOCK_FOLDER + "/stripped_rubber_log"));
                simpleBlockItem(block, models().getExistingFile(new ResourceLocation(CLib.MODID, block.getRegistryName().getPath())));
            } else if (block == BlockInit.RUBBER_SAPLING.get()) {
                simpleBlock(block, models().cross(block.getRegistryName().getPath()
                        , new ResourceLocation(CLib.MODID, "block/rubber_sapling")));
            } else {
                basicBlock(block);
            }
        });
    }
}
