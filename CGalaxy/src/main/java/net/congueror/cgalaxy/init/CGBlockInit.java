package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.CoalTorchBlock;
import net.congueror.cgalaxy.blocks.CoalWallTorchBlock;
import net.congueror.cgalaxy.blocks.LunacCropBlock;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderBlock;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryBlock;
import net.congueror.cgalaxy.blocks.launch_pad.LaunchPadBlock;
import net.congueror.cgalaxy.blocks.meteorite.MeteoriteBlock;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorBlock;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlock;
import net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreBlock;
import net.congueror.cgalaxy.item.CoalTorchBlockItem;
import net.congueror.clib.api.data.ItemModelDataProvider;
import net.congueror.clib.blocks.abstract_machine.fluid.AbstractFluidBlock;
import net.congueror.clib.blocks.generic.CLBlock;
import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.init.CLMaterialInit;
import net.congueror.clib.util.CreativeTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CGBlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CGalaxy.MODID);

    public static final RegistryObject<Block> COAL_TORCH = new BlockBuilder("coal_torch",
            new CoalTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.WOOD)))
            .withTranslation("Coal Torch")
            .withItemModel((itemModelDataProvider, block) -> itemModelDataProvider.modTexture(block.asItem(), "block/coal_torch"))
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.torchBlock(block, "cgalaxy:block/coal_torch"))
            .withItem(block -> new CoalTorchBlockItem(block, new Item.Properties().tab(CreativeTabs.BlocksIG.instance)))
            .build(BLOCKS);
    public static final RegistryObject<Block> COAL_WALL_TORCH = new BlockBuilder("coal_wall_torch",
            new CoalWallTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION)
                    .noCollission().instabreak().sound(SoundType.WOOD).lootFrom(() -> ForgeRegistries.BLOCKS.getValue(CGalaxy.location("coal_torch")))))
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.wallTorchBlock(block, "cgalaxy:block/coal_torch"))
            .withLootTable(null)
            .withGeneratedBlockItem(false)
            .build(BLOCKS);

    public static final RegistryObject<Block> MOON_REGOLITH = new BlockBuilder("moon_regolith",
            new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).strength(1.0f).sound(SoundType.SAND)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withTranslation("Moon Regolith")
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
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/moon_stone"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE_STAIRS = new BlockBuilder("moon_stone_stairs",
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/moon_stone"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_COBBLESTONE_SLAB = new BlockBuilder("moon_cobblestone_slab",
            new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/moon_cobblestone"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_COBBLESTONE_STAIRS = new BlockBuilder("moon_cobblestone_stairs",
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/moon_cobblestone"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE_BRICKS_SLAB = new BlockBuilder("moon_stone_bricks_slab",
            new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Brick Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/moon_stone_bricks"))
            .build(BLOCKS);
    public static final RegistryObject<Block> MOON_STONE_BRICKS_STAIRS = new BlockBuilder("moon_stone_bricks_stairs",
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Brick Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/moon_stone_bricks"))
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
            .withTranslation("Moon Silicon Ore")
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
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDrop(block, CLMaterialInit.TITANIUM.getScrap().get()))
            .build(BLOCKS);

    public static final RegistryObject<Block> ASTRAL_SAPPHIRE_ORE = new BlockBuilder("astral_sapphire_ore",
            new MeteoriteBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(45f, 600f).sound(SoundType.STONE)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL)
            .withBlockModel(null)
            .withTranslation("Astral Sapphire Meteorite")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDropWithSilkTouch(block, CGItemInit.ASTRAL_SAPPHIRE.get()))
            .build(BLOCKS);
    public static final RegistryObject<Block> ASTRAL_SAPPHIRE_BLOCK = new BlockBuilder("astral_sapphire_block",
            new CLBlock(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(7.0F, 6.0f).sound(SoundType.METAL)))
            .withItemProperties(new Item.Properties().rarity(Rarity.create("dark_blue", ChatFormatting.DARK_BLUE)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL, BlockTags.BEACON_BASE_BLOCKS)
            .withNewBlockTag("forge:storage_blocks/astral_sapphire")
            .withNewItemTag("forge:storage_blocks/astral_sapphire")
            .withTranslation("Block of Astral Sapphire")
            .build(BLOCKS);

    public static final RegistryObject<Block> METEORITE = new BlockBuilder("meteorite",
            new MeteoriteBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(45f, 600f).sound(SoundType.STONE)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL)
            .withBlockModel(null)
            .withTranslation("Meteorite")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDropWithSilkTouch(block, CGItemInit.METEORITE_CHUNK.get()))
            .build(BLOCKS);

    public static final RegistryObject<Block> LAUNCH_PAD = new BlockBuilder("launch_pad",
            new LaunchPadBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel(null)
            .withTranslation("Launch Pad")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .build(BLOCKS);

    public static final RegistryObject<Block> LUNAC_CROP = new BlockBuilder("lunac_crop",
            new LunacCropBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)))
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.age7CrossBlock(block, "cgalaxy:block/lunac_crop"))
            .withItemModel((itemModelDataProvider, block) -> itemModelDataProvider.modTexture(block.asItem(), "item/lunac_seeds"))
            .withRenderType(RenderType.cutout())
            .withTranslation("Lunac Crop")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withItem(block -> new ItemNameBlockItem(block, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .build(BLOCKS);

    public static final RegistryObject<Block> FUEL_LOADER = new BlockBuilder("fuel_loader",
            new FuelLoaderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.machineBlock(block,
                    "cgalaxy:block/machine_frame",
                    "cgalaxy:block/fuel_loader",
                    "cgalaxy:block/fuel_loader_lit",
                    "cgalaxy:block/machine_frame"))
            .withTranslation("Fuel Loader")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMachineDrop((AbstractFluidBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> FUEL_REFINERY = new BlockBuilder("fuel_refinery",
            new FuelRefineryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.machineBlock(block,
                    "cgalaxy:block/machine_frame",
                    "cgalaxy:block/fuel_refinery",
                    "cgalaxy:block/fuel_refinery_lit",
                    "cgalaxy:block/machine_frame"))
            .withTranslation("Fuel Refinery")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMachineDrop((AbstractFluidBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> GAS_EXTRACTOR = new BlockBuilder("gas_extractor",
            new GasExtractorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.machineBlock(block,
                    "cgalaxy:block/machine_frame",
                    "cgalaxy:block/gas_extractor_unlit",
                    "cgalaxy:block/gas_extractor_lit",
                    "cgalaxy:block/machine_frame"))
            .withTranslation("Gas Extractor")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMachineDrop((AbstractFluidBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> ROOM_PRESSURIZER = new BlockBuilder("room_pressurizer",
            new RoomPressurizerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.machineBlock(block,
                    "cgalaxy:block/machine_frame",
                    "cgalaxy:block/room_pressurizer",
                    "cgalaxy:block/room_pressurizer_lit",
                    "cgalaxy:block/machine_frame"))
            .withTranslation("Room Pressurizer")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMachineDrop((AbstractFluidBlock) block))
            .build(BLOCKS);
    public static final RegistryObject<Block> SPACE_STATION_CORE = new BlockBuilder("space_station_core",
            new SpaceStationCoreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel(null)
            .withItemModel(ItemModelDataProvider::empty)
            .withTranslation("Space Station Core")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .build(BLOCKS);

    public static final RegistryObject<Block> KEROSENE = BlockBuilder.createFluid("kerosene",
            new LiquidBlock(CGFluidInit.KEROSENE.getStill(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
            .build(BLOCKS);
    public static final RegistryObject<Block> OIL = BlockBuilder.createFluid("oil",
            new LiquidBlock(CGFluidInit.OIL.getStill(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
            .build(BLOCKS);

    public static Block moonStoneBlock() {
        return new CLBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops());
    }
}
