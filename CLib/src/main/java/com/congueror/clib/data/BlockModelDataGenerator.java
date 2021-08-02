package com.congueror.clib.data;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;

public class BlockModelDataGenerator extends BlockStateProvider {
    String modid;
    DeferredRegister<Block> blockRegistry;
    Block[] exceptions;
    ExistingFileHelper helper;

    protected BlockModelDataGenerator(DataGenerator gen, String modid, ExistingFileHelper fileHelper) {
        super(gen, modid, fileHelper);
        this.modid = modid;
        this.helper = fileHelper;
    }

    /**
     * @param blockRegistry The DeferredRegistry for blocks in your mod.
     * @param exceptions    An array list of blocks that will be skipped when adding textures.
     */
    public BlockModelDataGenerator(DataGenerator gen, String modid, ExistingFileHelper exFileHelper, DeferredRegister<Block> blockRegistry, Block... exceptions) {
        this(gen, modid, exFileHelper);
        this.blockRegistry = blockRegistry;
        this.exceptions = exceptions;
    }

    /**
     * Basic block model and block item model
     *
     * @param block
     */
    public void basicBlock(Block block) {
        simpleBlock(block);
        basicBlockItem(block);
    }

    /**
     * Block model with cube_column parent
     *
     * @param block
     */
    public void cubeColumnBlock(Block block) {
        simpleBlock(block, models().cubeColumn(block.getRegistryName().getPath()
                , new ResourceLocation(modid, "block/" + block.getRegistryName().getPath() + "_side")
                , new ResourceLocation(modid, "block/" + block.getRegistryName().getPath() + "_top")));
        basicBlockItem(block);
    }

    /**
     * Block model for log-like blocks
     *
     * @param block
     */
    public void logBlock(Block block) {
        logBlock((RotatedPillarBlock) block);
        basicBlockItem(block);
    }

    /**
     * Block model for axis blocks
     *
     * @param block
     */
    public void axisBlock(Block block, String texture) {
        axisBlock((RotatedPillarBlock) block, new ResourceLocation(modid, ModelProvider.BLOCK_FOLDER + "/" + texture), new ResourceLocation(modid, ModelProvider.BLOCK_FOLDER + "/" + texture));
        basicBlockItem(block);
    }

    /**
     * Block model for sapling like blocks
     *
     * @param block
     */
    public void crossBlock(Block block) {
        simpleBlock(block, models().cross(block.getRegistryName().getPath(), new ResourceLocation(modid, "block/" + block.getRegistryName().getPath())));
    }

    /**
     * Basic block item model
     *
     * @param block
     */
    public void basicBlockItem(Block block) {
        simpleBlockItem(block, models().getExistingFile(new ResourceLocation(modid, block.getRegistryName().getPath())));
    }

    /**
     * Override if you want custom models for your exceptions.
     */
    @Override
    protected void registerStatesAndModels() {
        blockRegistry.getEntries().stream().map(RegistryObject::get).filter(block -> !Arrays.asList(exceptions).contains(block)).forEach(block -> {
            if (!(block instanceof FlowingFluidBlock)) {
                ResourceLocation rl = new ResourceLocation(modid, "block/" + block.asItem());
                //Checks whether the texture exists so the process doesn't stop if it can't find a texture
                if (helper.exists(rl, ResourcePackType.CLIENT_RESOURCES, ".png", "textures")) {
                    basicBlock(block);
                }
            }
        });
    }
}
