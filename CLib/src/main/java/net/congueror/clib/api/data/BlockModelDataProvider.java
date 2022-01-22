package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.BlockBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class BlockModelDataProvider extends BlockStateProvider {
    String modid;
    ExistingFileHelper helper;

    public static final int DEFAULT_ANGLE_OFFSET = 180;

    public BlockModelDataProvider(DataGenerator gen, String modid, ExistingFileHelper fileHelper) {
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
        simpleBlockItem(block, models().getExistingFile(new ResourceLocation(modid, Objects.requireNonNull(block.getRegistryName()).getPath())));
    }

    /**
     * Block model for torch
     */
    public void torchBlock(Block block, String texture) {
        simpleBlock(block, models().torch(block.getRegistryName().getPath(), new ResourceLocation(texture)));
    }

    public void wallTorchBlock(Block block, String texture) {
        horizontalBlock(block, models().torchWall(block.getRegistryName().getPath(), new ResourceLocation(texture)));
    }

    @Override
    protected void registerStatesAndModels() {
        BlockBuilder.OBJECTS.get(modid).forEach(builder -> {
            if (builder.blockModel != null) {
                builder.blockModel.accept(this, builder.block);
            }
            if (builder.itemModel == null && builder.generateBlockItem) {
                basicBlockItem(builder.block);
            }
        });
    }
}
