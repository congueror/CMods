package net.congueror.clib.util.registry.data;

import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelDataProvider extends BlockStateProvider {
    String modid;
    ExistingFileHelper helper;

    public BlockModelDataProvider(DataGenerator gen, String modid, ExistingFileHelper fileHelper) {
        super(gen, modid, fileHelper);
        this.modid = modid;
        this.helper = fileHelper;
    }

    /**
     * Block model with orientable parent and state with horizontal facing and lit properties.
     */
    public void machineBlock(Block block, String sideTexture, String frontTexture, String frontOnTexture, String topTexture) {
        ModelFile fileOff = models().orientable(block.getRegistryName().getPath(),
                new ResourceLocation(sideTexture),
                new ResourceLocation(frontTexture),
                new ResourceLocation(topTexture));
        ModelFile fileOn = models().orientable(block.getRegistryName().getPath() + "_on",
                new ResourceLocation(sideTexture),
                new ResourceLocation(frontOnTexture),
                new ResourceLocation(topTexture));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).with(BlockStateProperties.LIT, false)
                .modelForState().rotationY(180).modelFile(fileOff).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).with(BlockStateProperties.LIT, true)
                .modelForState().rotationY(180).modelFile(fileOn).addModel()

                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).with(BlockStateProperties.LIT, false)
                .modelForState().rotationY(0).modelFile(fileOff).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).with(BlockStateProperties.LIT, true)
                .modelForState().rotationY(0).modelFile(fileOn).addModel()

                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).with(BlockStateProperties.LIT, false)
                .modelForState().rotationY(90).modelFile(fileOff).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).with(BlockStateProperties.LIT, true)
                .modelForState().rotationY(90).modelFile(fileOn).addModel()

                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).with(BlockStateProperties.LIT, false)
                .modelForState().rotationY(270).modelFile(fileOff).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).with(BlockStateProperties.LIT, true)
                .modelForState().rotationY(270).modelFile(fileOn).addModel();
    }

    /**
     * Block model with cube_all parent and simple blockstate.
     *
     * @param texture (e.g. <strong> modid:block/my_textures_name </strong>)
     */
    public void cubeAllBlock(Block block, String texture) {
        simpleBlock(block, models().cubeAll(block.getRegistryName().getPath(), new ResourceLocation(texture)));
    }

    /**
     * Block model with cube_bottom_top parent and simple blockstate.
     *
     * @param sideTexture   (e.g. <strong> modid:block/my_textures_name </strong>)
     * @param bottomTexture (e.g. <strong> modid:block/my_textures_name </strong>)
     * @param topTexture    (e.g. <strong> modid:block/my_textures_name </strong>)
     */
    public void cubeBottomTopBlock(Block block, String sideTexture, String bottomTexture, String topTexture) {
        simpleBlock(block, models().cubeBottomTop(block.getRegistryName().getPath(),
                new ResourceLocation(sideTexture),
                new ResourceLocation(bottomTexture),
                new ResourceLocation(topTexture)));
    }

    /**
     * Block model with cube_column parent and simple blockstate (e.g. ANCIENT_DEBRIS)
     *
     * @param sideTexture (e.g. <strong> modid:block/my_textures_name </strong>)
     * @param endTexture  (e.g. <strong> modid:block/my_textures_name </strong>)
     */
    public void cubeColumnBlock(Block block, String sideTexture, String endTexture) {
        simpleBlock(block, models().cubeColumn(block.getRegistryName().getPath()
                , new ResourceLocation(sideTexture)
                , new ResourceLocation(endTexture)));
    }

    /**
     * Block model and state for log-like blocks
     */
    public void logBlock(Block block) {
        logBlock((RotatedPillarBlock) block);
    }

    /**
     * Block model and state for axis blocks (e.g. OAK_WOOD)
     *
     * @param sideTexture (e.g. <strong> modid:block/my_textures_name </strong>)
     */
    public void axisBlock(Block block, String sideTexture) {
        axisBlock((RotatedPillarBlock) block,
                new ResourceLocation(sideTexture),
                new ResourceLocation(sideTexture));
    }

    /**
     * Block model and state for sapling like blocks
     */
    public void crossBlock(Block block) {
        simpleBlock(block, models().cross(block.getRegistryName().getPath(),
                new ResourceLocation(block.getRegistryName().getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + block.getRegistryName().getPath())));
    }

    /**
     * Block model and state for slab block
     *
     * @param texture (e.g. <strong> modid:block/my_textures_name </strong>)
     */
    public void slabBlock(Block block, String texture) {
        slabBlock((SlabBlock) block,
                new ResourceLocation(texture),
                new ResourceLocation(texture));
    }

    /**
     * Block model and state for stairs.
     *
     * @param texture (e.g. <strong> modid:block/my_textures_name </strong>)
     */
    public void stairsBlock(Block block, String texture) {
        stairsBlock((StairBlock) block,
                new ResourceLocation(texture));
    }

    /**
     * Basic block item model and state
     */
    public void basicBlockItem(Block block) {
        simpleBlockItem(block, models().getExistingFile(
                new ResourceLocation(block.getRegistryName().getNamespace(), block.getRegistryName().getPath())));
    }

    /**
     * Block model and state for torch blocks.
     *
     * @param texture (e.g. <strong> modid:block/my_textures_name </strong>)
     */
    public void torchBlock(Block block, String texture) {
        simpleBlock(block, models().torch(block.getRegistryName().getPath(),
                new ResourceLocation(texture)));
    }

    /**
     * Block model and state for wall torch blocks.
     *
     * @param texture (e.g. <strong> modid:block/my_textures_name </strong>)
     */
    public void wallTorchBlock(Block block, String texture) {
        ModelFile file = models().torchWall(block.getRegistryName().getPath(), new ResourceLocation(texture));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
                .modelForState().rotationY(270).modelFile(file).addModel()

                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
                .modelForState().rotationY(90).modelFile(file).addModel()

                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
                .modelForState().rotationY(0).modelFile(file).addModel()

                .partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST)
                .modelForState().rotationY(180).modelFile(file).addModel();
    }

    public void age7Block(Block block, ModelFile[] files) {
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.AGE_7, 0)
                .modelForState().modelFile(files[0]).addModel()

                .partialState().with(BlockStateProperties.AGE_7, 1)
                .modelForState().modelFile(files[1]).addModel()

                .partialState().with(BlockStateProperties.AGE_7, 2)
                .modelForState().modelFile(files[2]).addModel()

                .partialState().with(BlockStateProperties.AGE_7, 3)
                .modelForState().modelFile(files[3]).addModel()

                .partialState().with(BlockStateProperties.AGE_7, 4)
                .modelForState().modelFile(files[4]).addModel()

                .partialState().with(BlockStateProperties.AGE_7, 5)
                .modelForState().modelFile(files[5]).addModel()

                .partialState().with(BlockStateProperties.AGE_7, 6)
                .modelForState().modelFile(files[6]).addModel()

                .partialState().with(BlockStateProperties.AGE_7, 7)
                .modelForState().modelFile(files[7]).addModel();
    }

    public void age7CrossBlock(Block block, String texture) {
        ModelFile[] files = new ModelFile[8];
        for (int i = 0; i <= 7; i++)
            files[i] = models().cross(block.getRegistryName().getPath() + "_stage" + i, new ResourceLocation(texture + "_stage" + i));
        age7Block(block, files);
    }

    public void age7CropBlock(Block block, String texture) {
        ModelFile[] files = new ModelFile[] {
                models().crop(block.getRegistryName().getPath() + "_stage0", new ResourceLocation(texture + "_stage0")),
                models().crop(block.getRegistryName().getPath() + "_stage0", new ResourceLocation(texture + "_stage0")),
                models().crop(block.getRegistryName().getPath() + "_stage1", new ResourceLocation(texture + "_stage1")),
                models().crop(block.getRegistryName().getPath() + "_stage1", new ResourceLocation(texture + "_stage1")),
                models().crop(block.getRegistryName().getPath() + "_stage2", new ResourceLocation(texture + "_stage2")),
                models().crop(block.getRegistryName().getPath() + "_stage2", new ResourceLocation(texture + "_stage2")),
                models().crop(block.getRegistryName().getPath() + "_stage2", new ResourceLocation(texture + "_stage2")),
                models().crop(block.getRegistryName().getPath() + "_stage3", new ResourceLocation(texture + "_stage3")),
        };
        age7Block(block, files);
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
