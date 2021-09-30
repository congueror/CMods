package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderBlock;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryBlock;
import net.congueror.cgalaxy.blocks.launch_pad.LaunchPadBlock;
import net.congueror.cgalaxy.blocks.meteorite.MeteoriteBlock;
import net.congueror.cgalaxy.blocks.oxygen_compressor.OxygenCompressorBlock;
import net.congueror.clib.api.machine.fluid.AbstractFluidBlock;
import net.congueror.clib.api.objects.blocks.CLBlock;
import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.init.CLItemInit;
import net.congueror.clib.util.ModCreativeTabs;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGBlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CGalaxy.MODID);

    public static final RegistryObject<Block> MOON_TURF = new BlockBuilder("moon_turf",
            new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).strength(1.0f).sound(SoundType.SAND)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withTranslation("Moon Turf")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE = new BlockBuilder("moon_stone",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, Tags.Blocks.STONE)
            .withTranslation("Moon Stone")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_COBBLESTONE = new BlockBuilder("moon_cobblestone",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE_BRICKS = new BlockBuilder("moon_stone_bricks",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Bricks")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_SMOOTH_STONE = new BlockBuilder("smooth_moon_stone",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Smooth Moon Stone")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_CHISELED_STONE_BRICKS = new BlockBuilder("chiseled_moon_stone_bricks",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Chiseled Moon Stone Bricks")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE_SLAB = new BlockBuilder("moon_stone_slab",
            new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "moon_stone"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE_STAIRS = new BlockBuilder("moon_stone_stairs",
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "moon_stone"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_COBBLESTONE_SLAB = new BlockBuilder("moon_cobblestone_slab",
            new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "moon_cobblestone"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_COBBLESTONE_STAIRS = new BlockBuilder("moon_cobblestone_stairs",
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "moon_cobblestone"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE_BRICKS_SLAB = new BlockBuilder("moon_stone_bricks_slab",
            new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Brick Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "moon_stone_bricks"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE_BRICKS_STAIRS = new BlockBuilder("moon_stone_bricks_stairs",
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Brick Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "moon_stone_bricks"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_IRON_ORE = new BlockBuilder("moon_iron_ore",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, Tags.Blocks.ORES_IRON, Tags.Blocks.ORES)
            .withExistingItemTags(Tags.Items.ORES_IRON, Tags.Items.ORES)
            .withTranslation("Moon Iron Ore")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_SILICON_ORE = new BlockBuilder("moon_silicon_ore",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Iron Ore")
            .withNewBlockTag("forge:ores/silicon")
            .withNewItemTag("forge:ores/silicon")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_ALUMINUM_ORE = new BlockBuilder("moon_aluminum_ore",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Aluminum Ore")
            .withNewBlockTag("forge:ores/aluminum")
            .withNewItemTag("forge:ores/aluminum")
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_TITANIUM_ORE = new BlockBuilder("moon_titanium_ore",
            moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Titanium Ore")
            .withNewBlockTag("forge:ores/titanium")
            .withNewItemTag("forge:ores/titanium")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDrop(block, CLItemInit.TITANIUM_SCRAP.get()))
            .build(BLOCKS);

    public static final RegistryObject<Block> METEORITE = new BlockBuilder("meteorite",
            new MeteoriteBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(45f, 600f).sound(SoundType.STONE)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL)
            .withBlockModel(null)
            .withTranslation("Meteorite")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createLitBlockStateDrop(block, CGItemInit.METEORITE_CHUNK.get(), CGItemInit.RAW_LUNARITE.get()))
            .build(BLOCKS);

    public static final RegistryObject<Block> LAUNCH_PAD = new BlockBuilder("launch_pad",
            new LaunchPadBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel(null)
            .withTranslation("Launch Pad")
            .withCreativeTab(ModCreativeTabs.CGalaxyIG.instance)
            .build(BLOCKS);

    public static final RegistryObject<Block> FUEL_LOADER = new BlockBuilder("fuel_loader",
            new FuelLoaderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel(null)
            .withTranslation("Fuel Loader")
            .withCreativeTab(ModCreativeTabs.CGalaxyIG.instance)
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createFluidMachineDrop((AbstractFluidBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> FUEL_REFINERY = new BlockBuilder("fuel_refinery",
            new FuelRefineryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel(null)
            .withTranslation("Fuel Refinery")
            .withCreativeTab(ModCreativeTabs.CGalaxyIG.instance)
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createFluidMachineDrop((AbstractFluidBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> OXYGEN_COMPRESSOR = new BlockBuilder("oxygen_compressor",
            new OxygenCompressorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel(null)
            .withTranslation("Oxygen Compressor")
            .withCreativeTab(ModCreativeTabs.CGalaxyIG.instance)
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createFluidMachineDrop((AbstractFluidBlock) block))
            .build(BLOCKS);

    public static final RegistryObject<Block> KEROSENE = BlockBuilder.createFluid("kerosene",
            new LiquidBlock(CGFluidInit.KEROSENE::getStill, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
            .build(BLOCKS);
    public static final RegistryObject<Block> OIL = BlockBuilder.createFluid("oil",
            new LiquidBlock(CGFluidInit.OIL::getStill, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
            .build(BLOCKS);

    public static Block moonStoneBlock() {
        return new CLBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops());
    }
}
