package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.blocks.generic.CLOreBlock;
import net.congueror.clib.blocks.generic.CLRotatedPillarBlock;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.data.BlockModelDataProvider;
import net.congueror.clib.world.FeatureGen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Random;

public class CLBlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CLib.MODID);
    private static final BlockBuilder.BlockDeferredRegister REGISTER = new BlockBuilder.BlockDeferredRegister(BLOCKS, CLItemInit.ITEMS);

    public static final RegistryObject<CLOreBlock> SALTPETRE_ORE = REGISTER.create("saltpetre_ore", () -> new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(3.0f, 6.0f)
            .sound(SoundType.STONE)
            , 1))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withNewBlockTag("forge:ores/saltpetre")
            .withItemTag("forge:ores/saltpetre")
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, CLItemInit.SALTPETRE_DUST.get(), 4, 6))
            .withTranslation("Saltpetre Ore")
            .withTranslation("\u785d\u77f3\u77ff\u77f3", "zh_cn")
            .withCreativeTab(CreativeTabs.ResourcesIG.instance)
            .build();
    public static final RegistryObject<CLOreBlock> SULFUR_ORE = REGISTER.create("sulfur_ore", () -> new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(3.0f, 6.0f)
            .sound(SoundType.NETHERRACK)
            , 2))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withNewBlockTag("forge:ores/sulfur")
            .withItemTag("forge:ores/sulfur")
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, CLItemInit.SULFUR_DUST.get(), 3, 6))
            .withTranslation("Nether Sulfur Ore")
            .withTranslation("\u4e0b\u754c\u786b\u77ff\u77f3", "zh_cn")
            .withCreativeTab(CreativeTabs.ResourcesIG.instance)
            .build();

    public static final RegistryObject<Block> SALT_BLOCK = REGISTER.create("salt_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.CLAY).strength(0.7f).sound(SoundType.SAND)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, CLItemInit.SALT.get(), 4, 4))
            .withTranslation("Salt Block")
            .withCreativeTab(CreativeTabs.ResourcesIG.instance)
            .build();

    public static final RegistryObject<CLRotatedPillarBlock> RUBBER_STRIPPED_LOG = REGISTER.create("stripped_rubber_log", () ->
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withItemTag("forge:rubber_logs")
            .withBlockModel(BlockModelDataProvider::logBlock)
            .withTranslation("Stripped Rubber Log")
            .build();

    public static final RegistryObject<CLRotatedPillarBlock> RUBBER_STRIPPED_WOOD = REGISTER.create("stripped_rubber_wood", () ->
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withItemTag("forge:rubber_logs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.axisBlock(block, "clib:block/stripped_rubber_log"))
            .withTranslation("Stripped Rubber Wood")
            .build();

    public static final RegistryObject<CLRotatedPillarBlock> RUBBER_LOG = REGISTER.create("rubber_log", () ->
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)).setModifiedState(ToolActions.AXE_STRIP, RUBBER_STRIPPED_LOG))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.LOGS)
            .withNewBlockTag("forge:rubber_logs")
            .withItemTag("forge:rubber_logs")
            .withBlockModel(BlockModelDataProvider::logBlock)
            .withTranslation("Rubber Log")
            .build();

    public static final RegistryObject<CLRotatedPillarBlock> RUBBER_WOOD = REGISTER.create("rubber_wood", () ->
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)).setModifiedState(ToolActions.AXE_STRIP, RUBBER_STRIPPED_WOOD))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withItemTag("forge:rubber_logs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.axisBlock(block, "clib:block/rubber_log"))
            .withTranslation("Rubber Wood")
            .build();

    public static final RegistryObject<LeavesBlock> RUBBER_LEAVES = REGISTER.create("rubber_leaves", () ->
            new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_HOE, BlockTags.LEAVES)
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createLeavesDrops(block, CLBlockInit.RUBBER_SAPLING.get()))
            .withTranslation("Rubber Leaves")
            .build();

    public static final RegistryObject<Block> RUBBER_PLANKS = REGISTER.create("rubber_planks", () ->
            new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.PLANKS)
            .withItemTags(ItemTags.PLANKS)
            .withTranslation("Rubber Planks")
            .build();

    public static final RegistryObject<SaplingBlock> RUBBER_SAPLING = REGISTER.create("rubber_sapling", () ->
            new SaplingBlock(new AbstractTreeGrower() {
                @Nullable
                @Override
                protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
                    return FeatureGen.RUBBER_TREE;
                }
            }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)))
            .withBlockModel(BlockModelDataProvider::crossBlock)
            .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "block/rubber_sapling"))
            .withTranslation("Rubber Sapling")
            .withRenderType(RenderType.cutout())
            .build();
}