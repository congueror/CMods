package com.congueror.cgalaxy.data;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockModelDataGen extends BlockStateProvider {

    public BlockModelDataGen(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CGalaxy.MODID, exFileHelper);
    }

    public void basicBlock(Block block) {
        simpleBlock(block);
        simpleBlockItem(block, models().getExistingFile(new ResourceLocation(CGalaxy.MODID, block.getRegistryName().getPath())));
    }

    @Override
    protected void registerStatesAndModels() {
        BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            if (block == null) {

            } else {
                basicBlock(block);
            }
        });
    }
}
