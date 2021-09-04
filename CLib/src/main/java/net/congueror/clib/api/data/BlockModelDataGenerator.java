package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.BlockBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;

public class BlockModelDataGenerator extends BlockStateProvider {
    String modid;
    ExistingFileHelper helper;

    protected BlockModelDataGenerator(DataGenerator gen, String modid, ExistingFileHelper fileHelper) {
        super(gen, modid, fileHelper);
        this.modid = modid;
        this.helper = fileHelper;
    }

    /**
     * Block model with cube_column parent
     */
    public void cubeColumnBlock(Block block) {
        simpleBlock(block, models().cubeColumn(block.getRegistryName().getPath()
                , new ResourceLocation(modid, "block/" + block.getRegistryName().getPath() + "_side")
                , new ResourceLocation(modid, "block/" + block.getRegistryName().getPath() + "_top")));
    }

    /**
     * Block model for log-like blocks
     */
    public void logBlock(Block block) {
        logBlock((RotatedPillarBlock) block);
    }

    /**
     * Block model for axis blocks
     */
    public void axisBlock(Block block, String sideTexture) {
        axisBlock((RotatedPillarBlock) block, new ResourceLocation(modid, ModelProvider.BLOCK_FOLDER + "/" + sideTexture), new ResourceLocation(modid, ModelProvider.BLOCK_FOLDER + "/" + sideTexture));
    }

    /**
     * Block model for sapling like blocks
     */
    public void crossBlock(Block block) {
        simpleBlock(block, models().cross(block.getRegistryName().getPath(), new ResourceLocation(modid, ModelProvider.BLOCK_FOLDER + "/" + block.getRegistryName().getPath())));
    }

    /**
     * Block model for slab block
     */
    public void slabBlock(Block block, String texture) {
        slabBlock((SlabBlock) block, new ResourceLocation(modid, ModelProvider.BLOCK_FOLDER + "/" + texture), new ResourceLocation(modid, ModelProvider.BLOCK_FOLDER + "/" + texture));
    }

    /**
     * Block model for stairs.
     */
    public void stairsBlock(Block block, String texture) {
        stairsBlock((StairBlock) block, new ResourceLocation(modid, ModelProvider.BLOCK_FOLDER + "/" + texture));
    }

    /**
     * Basic block item model
     */
    public void basicBlockItem(Block block) {
        simpleBlockItem(block, models().getExistingFile(new ResourceLocation(modid, block.getRegistryName().getPath())));
    }

    @Override
    protected void registerStatesAndModels() {
        BlockBuilder.OBJECTS.forEach(builder -> {
            if (builder.blockModel != null) {
                builder.blockModel.accept(this, builder.getBlock());
            } else {
                simpleBlock(builder.getBlock());
            }
            if (builder.itemModel == null) {
                basicBlockItem(builder.getBlock());
            }
        });
    }
}
