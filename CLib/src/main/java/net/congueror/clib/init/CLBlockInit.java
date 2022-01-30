package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.BlockModelDataProvider;
import net.congueror.clib.blocks.abstract_machine.item.AbstractItemBlock;
import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.blocks.generic.*;
import net.congueror.clib.blocks.solar_generator.SolarGeneratorBlock;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.world.FeatureGen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class CLBlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CLib.MODID);

    public static final RegistryObject<Block> SALTPETRE_ORE = new BlockBuilder("saltpetre_ore", new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(3.0f, 6.0f)
            .sound(SoundType.STONE)
            , 1))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, tierToTag(1))
            .withNewBlockTag("forge:ores/saltpetre")
            .withNewItemTag("forge:ores/saltpetre")
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, CLItemInit.SALTPETRE_DUST.get(), 4, 6))
            .withTranslation("Saltpetre Ore")
            .build(BLOCKS);
    public static final RegistryObject<Block> SULFUR_ORE = new BlockBuilder("sulfur_ore", new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(3.0f, 6.0f)
            .sound(SoundType.NETHERRACK)
            , 2))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, tierToTag(1))
            .withNewBlockTag("forge:ores/sulfur")
            .withNewItemTag("forge:ores/sulfur")
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, CLItemInit.SULFUR_DUST.get(), 3, 6))
            .withTranslation("Nether Sulfur Ore")
            .build(BLOCKS);

    public static final RegistryObject<Block> SALT_BLOCK = new BlockBuilder("salt_block",
            new CLBlock(BlockBehaviour.Properties.of(Material.CLAY).strength(0.7f).sound(SoundType.SAND)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, CLItemInit.SALT.get(), 4, 4))
            .withTranslation("Salt Block")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_STRIPPED_LOG = new BlockBuilder("stripped_rubber_log",
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withNewItemTag("forge:rubber_logs")
            .withBlockModel(BlockModelDataProvider::logBlock)
            .withTranslation("Stripped Rubber Log")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_STRIPPED_WOOD = new BlockBuilder("stripped_rubber_wood",
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withNewItemTag("forge:rubber_logs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.axisBlock(block, "clib:block/stripped_rubber_log"))
            .withTranslation("Stripped Rubber Wood")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_LOG = new BlockBuilder("rubber_log",
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.LOGS)
            .withNewBlockTag("forge:rubber_logs")
            .withNewItemTag("forge:rubber_logs")
            .withModifiedState(ToolActions.AXE_STRIP, RUBBER_STRIPPED_LOG)
            .withBlockModel(BlockModelDataProvider::logBlock)
            .withTranslation("Rubber Log")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_WOOD = new BlockBuilder("rubber_wood",
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withNewItemTag("forge:rubber_logs")
            .withModifiedState(ToolActions.AXE_STRIP, RUBBER_STRIPPED_WOOD)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.axisBlock(block, "clib:block/rubber_log"))
            .withTranslation("Rubber Wood")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_LEAVES = new BlockBuilder("rubber_leaves",
            new CLLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_HOE, BlockTags.LEAVES)
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createLeavesDrops(block, CLBlockInit.RUBBER_SAPLING.get()))
            .withTranslation("Rubber Leaves")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_PLANKS = new BlockBuilder("rubber_planks",
            new CLBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.PLANKS)
            .withExistingItemTags(ItemTags.PLANKS)
            .withTranslation("Rubber Planks")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_SAPLING = new BlockBuilder("rubber_sapling",
            new SaplingBlock(new AbstractTreeGrower() {
                @Nullable
                @Override
                protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random pRandom, boolean pLargeHive) {
                    return FeatureGen.RUBBER_TREE;
                }
            }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)))
            .withBlockModel(BlockModelDataProvider::crossBlock)
            .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.texture(block.asItem(), "block/rubber_sapling"))
            .withTranslation("Rubber Sapling")
            .withRenderType(RenderType.cutout())
            .build(BLOCKS);

    public static final RegistryObject<Block> SOLAR_GENERATOR_1 = new BlockBuilder("solar_generator_1",
            new SolarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 20, 1))//TODO
            .withCreativeTab(CreativeTabs.MachinesIG.instance)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeBottomTopBlock(block, "clib:block/solar_generator_side", "clib:block/machine_frame", "clib:block/solar_generator_1_top"))
            .withTranslation("Tier 1 Solar Generator")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMachineDrop((AbstractItemBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> SOLAR_GENERATOR_2 = new BlockBuilder("solar_generator_2",
            new SolarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 50, 2))
            .withCreativeTab(CreativeTabs.MachinesIG.instance)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeBottomTopBlock(block, "clib:block/solar_generator_side", "clib:block/machine_frame", "clib:block/solar_generator_2_top"))
            .withTranslation("Tier 2 Solar Generator")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMachineDrop((AbstractItemBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> SOLAR_GENERATOR_3 = new BlockBuilder("solar_generator_3",
            new SolarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 120, 3))
            .withCreativeTab(CreativeTabs.MachinesIG.instance)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeBottomTopBlock(block, "clib:block/solar_generator_side", "clib:block/machine_frame", "clib:block/solar_generator_3_top"))
            .withTranslation("Tier 3 Solar Generator")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMachineDrop((AbstractItemBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> SOLAR_GENERATOR_CREATIVE = new BlockBuilder("solar_generator_creative",
            new SolarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 10000, 0))
            .withCreativeTab(CreativeTabs.MachinesIG.instance)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeBottomTopBlock(block, "clib:block/solar_generator_side", "clib:block/machine_frame", "clib:block/solar_generator_3_top"))
            .withTranslation("Creative Solar Generator")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMachineDrop((AbstractItemBlock) block))
            .build(BLOCKS);


    public static BlockBuilder registerStorageBlock(String name, float hardness, int harvestLvl) {
        return registerStorageBlock(name, hardness, tierToTag(harvestLvl));
    }

    public static BlockBuilder registerStorageBlock(String name, float hardness, Tag.Named<Block> harvestTag) {
        final String tagName = "forge:storage_blocks/" + name.substring(0, name.indexOf("_"));
        return new BlockBuilder(name, new CLBlock(BlockBehaviour.Properties
                .of(Material.METAL).requiresCorrectToolForDrops()
                .strength(hardness, 6.0f)
                .sound(SoundType.METAL)))
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestTag, BlockTags.BEACON_BASE_BLOCKS)
                .withNewBlockTag(tagName)
                .withNewItemTag(tagName);
    }

    public static BlockBuilder registerOreBlock(String name, int harvestLvl, int exp) {
        return registerOreBlock(name, tierToTag(harvestLvl), exp);
    }

    public static BlockBuilder registerOreBlock(String name, Tag.Named<Block> harvestTag, int exp) {
        final String tagName = "forge:ores/" + name.substring(0, name.indexOf("_"));
        return new BlockBuilder(name, new CLOreBlock(BlockBehaviour.Properties
                .of(Material.STONE).requiresCorrectToolForDrops()
                .strength(3.0f, 6.0f)
                .sound(SoundType.STONE)
                , exp))
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestTag)
                .withNewBlockTag(tagName)
                .withNewItemTag(tagName);
    }

    public static Tag.Named<Block> tierToTag(int harvestLvl) {
        Tag.Named<Block> harvestTag = null;
        if (harvestLvl == 0) {
            harvestTag = Tags.Blocks.NEEDS_WOOD_TOOL;
        } else if (harvestLvl == 1) {
            harvestTag = BlockTags.NEEDS_STONE_TOOL;
        } else if (harvestLvl == 2) {
            harvestTag = BlockTags.NEEDS_IRON_TOOL;
        } else if (harvestLvl == 3) {
            harvestTag = BlockTags.NEEDS_DIAMOND_TOOL;
        } else if (harvestLvl == 4) {
            harvestTag = Tags.Blocks.NEEDS_NETHERITE_TOOL;
        }
        return harvestTag;
    }
}