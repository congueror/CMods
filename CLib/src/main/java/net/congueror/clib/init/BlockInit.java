package net.congueror.clib.init;

import net.congueror.clib.CLib;
import net.congueror.clib.api.data.BlockModelDataGenerator;
import net.congueror.clib.api.objects.blocks.*;
import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.world.FeatureGen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CLib.MODID);

    public static final RegistryObject<Block> TIN_BLOCK = registerStorageBlock("tin_block", 3.0f, 1).build(BLOCKS);
    public static final RegistryObject<Block> TIN_ORE = registerOreBlock("tin_ore", 1, 0).build(BLOCKS);

    public static final RegistryObject<Block> STEEL_BLOCK = registerStorageBlock("steel_block", 6.0f, 2).build(BLOCKS);

    public static final RegistryObject<Block> ALUMINUM_BLOCK = registerStorageBlock("aluminum_block", 5.0f, 2).build(BLOCKS);
    public static final RegistryObject<Block> ALUMINUM_ORE = registerOreBlock("aluminum_ore", 2, 0).build(BLOCKS);

    public static final RegistryObject<Block> LEAD_BLOCK = registerStorageBlock("lead_block", 4.0f, 2).build(BLOCKS);
    public static final RegistryObject<Block> LEAD_ORE = registerOreBlock("lead_ore", 2, 0).build(BLOCKS);

    public static final RegistryObject<Block> RUBY_BLOCK = registerStorageBlock("ruby_block", 6.0f, 2).build(BLOCKS);
    public static final RegistryObject<Block> RUBY_ORE = new BlockBuilder(new ResourceLocation(CLib.MODID, "ruby_ore"), new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(3.0f, 6.0f)
            .sound(SoundType.NETHERRACK)
            , 7))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, tierToTag(2))
            .withNewBlockTag("forge:ores/ruby")
            .withNewItemTag("forge:ores/ruby")
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, ItemInit.RUBY.get(), 1, 1))
            .build(BLOCKS);

    public static final RegistryObject<Block> SILVER_BLOCK = registerStorageBlock("silver_block", 5.0f, 2).build(BLOCKS);
    public static final RegistryObject<Block> SILVER_ORE = registerOreBlock("silver_ore", 2, 0).build(BLOCKS);

    public static final RegistryObject<Block> LUMIUM_BLOCK = registerStorageBlock("lumium_block", 3.0f, 2).build(BLOCKS);

    public static final RegistryObject<Block> NICKEL_BLOCK = registerStorageBlock("nickel_block", 4.0f, 2).build(BLOCKS);
    public static final RegistryObject<Block> NICKEL_ORE = registerOreBlock("nickel_ore", 2, 0).build(BLOCKS);

    public static final RegistryObject<Block> INVAR_BLOCK = registerStorageBlock("invar_block", 3.0f, 2).build(BLOCKS);

    public static final RegistryObject<Block> ELECTRUM_BLOCK = registerStorageBlock("electrum_block", 4.0f, 2).build(BLOCKS);

    public static final RegistryObject<Block> PLATINUM_BLOCK = registerStorageBlock("platinum_block", 6.0f, 3).build(BLOCKS);
    public static final RegistryObject<Block> PLATINUM_ORE = registerOreBlock("platinum_ore", 3, 0).build(BLOCKS);

    public static final RegistryObject<Block> ENDERIUM_BLOCK = registerStorageBlock("enderium_block", 7.0f, 3).build(BLOCKS);

    public static final RegistryObject<Block> SIGNALUM_BLOCK = registerStorageBlock("signalum_block", 3.0f, 2).build(BLOCKS);

    public static final RegistryObject<Block> TUNGSTEN_BLOCK = registerStorageBlock("tungsten_block", 6.0f, 3).build(BLOCKS);
    public static final RegistryObject<Block> TUNGSTEN_ORE = registerOreBlock("tungsten_ore", 3, 0).build(BLOCKS);

    public static final RegistryObject<Block> BRONZE_BLOCK = registerStorageBlock("bronze_block", 4.0f, 2).build(BLOCKS);

    public static final RegistryObject<Block> SAPPHIRE_BLOCK = new BlockBuilder("clib:sapphire_block", new CLBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.CRYSTAL_SOUND_BLOCKS)
            .withNewBlockTag("forge:storage_blocks/sapphire")
            .withNewItemTag("forge:storage_blocks/sapphire")
            .build(BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_SMALL_BUD = new BlockBuilder("clib:small_sapphire_bud",
            new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.copy(Blocks.SMALL_AMETHYST_BUD)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withBlockModel((blockModelDataGenerator, block) -> blockModelDataGenerator.directionalBlock(block, blockModelDataGenerator.models().cross(Objects.requireNonNull(block.getRegistryName()).getPath(), new ResourceLocation(CLib.MODID, "block/small_sapphire_bud"))))
            .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.texture(block.asItem(), "block/small_sapphire_bud"))
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
            .build(BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_MEDIUM_BUD = new BlockBuilder("clib:medium_sapphire_bud",
            new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.copy(Blocks.MEDIUM_AMETHYST_BUD)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withBlockModel((blockModelDataGenerator, block) -> blockModelDataGenerator.directionalBlock(block, blockModelDataGenerator.models().cross(Objects.requireNonNull(block.getRegistryName()).getPath(), new ResourceLocation(CLib.MODID, "block/medium_sapphire_bud"))))
            .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.texture(block.asItem(), "block/medium_sapphire_bud"))
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
            .build(BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_LARGE_BUD = new BlockBuilder("clib:large_sapphire_bud",
            new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withBlockModel((blockModelDataGenerator, block) -> blockModelDataGenerator.directionalBlock(block, blockModelDataGenerator.models().cross(Objects.requireNonNull(block.getRegistryName()).getPath(), new ResourceLocation(CLib.MODID, "block/large_sapphire_bud"))))
            .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.texture(block.asItem(), "block/large_sapphire_bud"))
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
            .build(BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_CLUSTER = new BlockBuilder("clib:sapphire_cluster",
            new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withBlockModel((blockModelDataGenerator, block) -> blockModelDataGenerator.directionalBlock(block, blockModelDataGenerator.models().cross(Objects.requireNonNull(block.getRegistryName()).getPath(), new ResourceLocation(CLib.MODID, "block/sapphire_cluster"))))
            .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.texture(block.asItem(), "block/sapphire_cluster"))
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createClusterDrop(block, ItemInit.SAPPHIRE_SHARD.get()))
            .build(BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_BUDDING = new BlockBuilder("clib:budding_sapphire", new CLBuddingBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST),
            SAPPHIRE_SMALL_BUD, SAPPHIRE_MEDIUM_BUD, SAPPHIRE_LARGE_BUD, SAPPHIRE_CLUSTER))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.CRYSTAL_SOUND_BLOCKS)
            .withLootTable(null)
            .build(BLOCKS);

    public static final RegistryObject<Block> OPAL_BLOCK = registerStorageBlock("opal_block", 7.0f, 3).build(BLOCKS);
    public static final RegistryObject<Block> OPAL_ORE = new BlockBuilder(new ResourceLocation(CLib.MODID, "opal_ore"), new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(3.0f, 6.0f)
            .sound(SoundType.STONE)
            , 17))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, tierToTag(3))
            .withNewBlockTag("forge:ores/opal")
            .withNewItemTag("forge:ores/opal")
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, ItemInit.OPAL.get(), 1, 1))
            .build(BLOCKS);

    public static final RegistryObject<Block> TITANIUM_BLOCK = registerStorageBlock("titanium_block", 9.0f, 3).build(BLOCKS);
    public static final RegistryObject<Block> TITANIUM_ORE = new BlockBuilder(new ResourceLocation(CLib.MODID, "titanium_ore"), new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(8.0f, 28.0f)
            .sound(SoundType.STONE)
            , 0))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, tierToTag(3))
            .withNewBlockTag("forge:ores/titanium")
            .withNewItemTag("forge:ores/titanium")
            .withBlockModel(BlockModelDataGenerator::cubeColumnBlock)
            .build(BLOCKS);

    public static final RegistryObject<Block> URANIUM_ORE = registerOreBlock("uranium_ore", 3, 0).build(BLOCKS);

    public static final RegistryObject<Block> COBALT_BLOCK = registerStorageBlock("cobalt_block", 6.0f, 3).build(BLOCKS);
    public static final RegistryObject<Block> COBALT_ORE = registerOreBlock("cobalt_ore", 3, 0).build(BLOCKS);

    public static final RegistryObject<Block> ZINC_BLOCK = registerStorageBlock("zinc_block", 4.0f, 2).build(BLOCKS);
    public static final RegistryObject<Block> ZINC_ORE = registerOreBlock("zinc_ore", 2, 0).build(BLOCKS);

    public static final RegistryObject<Block> BRASS_BLOCK = registerStorageBlock("brass_block", 4.0f, 2).build(BLOCKS);

    public static final RegistryObject<Block> CHROMIUM_BLOCK = registerStorageBlock("chromium_block", 4.0f, 2).build(BLOCKS);
    public static final RegistryObject<Block> CHROMIUM_ORE = registerOreBlock("chromium_ore", 2, 0).build(BLOCKS);

    public static final RegistryObject<Block> THORIUM_ORE = registerOreBlock("thorium_ore", 3, 0).build(BLOCKS);

    public static final RegistryObject<Block> SALTPETRE_ORE = new BlockBuilder(new ResourceLocation(CLib.MODID, "saltpetre_ore"), new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(3.0f, 6.0f)
            .sound(SoundType.STONE)
            , 1))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, tierToTag(1))
            .withNewBlockTag("forge:ores/saltpetre")
            .withNewItemTag("forge:ores/saltpetre")
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, ItemInit.SALTPETRE_DUST.get(), 4, 6))
            .withTranslation("Saltpetre Ore")
            .build(BLOCKS);
    public static final RegistryObject<Block> SULFUR_ORE = new BlockBuilder(new ResourceLocation(CLib.MODID, "sulfur_ore"), new CLOreBlock(BlockBehaviour.Properties
            .of(Material.STONE).requiresCorrectToolForDrops()
            .strength(3.0f, 6.0f)
            .sound(SoundType.NETHERRACK)
            , 2))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, tierToTag(1))
            .withNewBlockTag("forge:ores/sulfur")
            .withNewItemTag("forge:ores/sulfur")
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, ItemInit.SULFUR_DUST.get(), 3, 6))
            .withTranslation("Nether Sulfur Ore")
            .build(BLOCKS);

    public static final RegistryObject<Block> SALT_BLOCK = new BlockBuilder("clib:salt_block",
            new CLBlock(BlockBehaviour.Properties.of(Material.CLAY).strength(0.7f).sound(SoundType.SAND)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createMultipleDrops(block, ItemInit.SALT.get(), 4, 4))
            .withTranslation("Salt Block")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_STRIPPED_LOG = new BlockBuilder("clib:stripped_rubber_log",
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withNewItemTag("forge:rubber_logs")
            .withBlockModel(BlockModelDataGenerator::logBlock)
            .withTranslation("Stripped Rubber Log")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_STRIPPED_WOOD = new BlockBuilder("clib:stripped_rubber_wood",
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withNewItemTag("forge:rubber_logs")
            .withBlockModel((blockModelDataGenerator, block) -> blockModelDataGenerator.axisBlock(block, "stripped_rubber_log"))
            .withTranslation("Stripped Rubber Wood")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_LOG = new BlockBuilder("clib:rubber_log",
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withNewItemTag("forge:rubber_logs")
            .withModifiedState(ToolActions.AXE_STRIP, RUBBER_STRIPPED_LOG)
            .withBlockModel(BlockModelDataGenerator::logBlock)
            .withTranslation("Rubber Log")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_WOOD = new BlockBuilder("clib:rubber_wood",
            new CLRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE)
            .withNewBlockTag("forge:rubber_logs")
            .withNewItemTag("forge:rubber_logs")
            .withModifiedState(ToolActions.AXE_STRIP, RUBBER_STRIPPED_WOOD)
            .withBlockModel((blockModelDataGenerator, block) -> blockModelDataGenerator.axisBlock(block, "rubber_log"))
            .withTranslation("Rubber Wood")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_LEAVES = new BlockBuilder("clib:rubber_leaves",
            new CLLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_HOE)
            .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createLeavesDrops(block, BlockInit.RUBBER_SAPLING.get()))
            .withTranslation("Rubber Leaves")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_PLANKS = new BlockBuilder("clib:rubber_planks",
            new CLBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.PLANKS)
            .withExistingItemTags(ItemTags.PLANKS)
            .withTranslation("Rubber Planks")
            .build(BLOCKS);

    public static final RegistryObject<Block> RUBBER_SAPLING = new BlockBuilder("clib:rubber_sapling",
            new CLSaplingBlock(FeatureGen.RUBBER_TREE, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)))
            .withBlockModel(BlockModelDataGenerator::crossBlock)
            .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.texture(block.asItem(), "block/rubber_sapling"))
            .withTranslation("Rubber Sapling")
            .build(BLOCKS);

    public static BlockBuilder registerStorageBlock(String name, float hardness, int harvestLvl) {
        return registerStorageBlock(name, hardness, tierToTag(harvestLvl));
    }

    public static BlockBuilder registerStorageBlock(String name, float hardness, Tag.Named<Block> harvestTag) {
        final String tagName = "forge:storage_blocks/" + name.substring(0, name.indexOf("_"));
        return new BlockBuilder(new ResourceLocation(CLib.MODID, name), new CLBlock(BlockBehaviour.Properties
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
        return new BlockBuilder(new ResourceLocation(CLib.MODID, name), new CLOreBlock(BlockBehaviour.Properties
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