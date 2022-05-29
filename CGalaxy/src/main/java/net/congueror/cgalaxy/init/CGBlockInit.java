package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.CoalTorchBlock;
import net.congueror.cgalaxy.blocks.CoalWallTorchBlock;
import net.congueror.cgalaxy.blocks.LunacCropBlock;
import net.congueror.cgalaxy.blocks.LunarCarrotsBlock;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderBlock;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryBlock;
import net.congueror.cgalaxy.blocks.launch_pad.LaunchPadBlock;
import net.congueror.cgalaxy.blocks.meteorite.MeteoriteBlock;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorBlock;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlock;
import net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreBlock;
import net.congueror.cgalaxy.items.CoalTorchBlockItem;
import net.congueror.cgalaxy.util.CGFoods;
import net.congueror.clib.blocks.abstract_machine.fluid.AbstractFluidBlock;
import net.congueror.clib.init.CLMaterialInit;
import net.congueror.clib.util.registry.ResourceBuilder;
import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.registry.data.RecipeDataProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CGBlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CGalaxy.MODID);
    private static final BlockBuilder.BlockDeferredRegister REGISTER = new BlockBuilder.BlockDeferredRegister(BLOCKS, CGItemInit.ITEMS);

    public static final RegistryObject<CoalTorchBlock> COAL_TORCH = REGISTER.create("coal_torch", () ->
            new CoalTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.WOOD)))
            .withTranslation("Coal Torch")
            .withItemModel((itemModelDataProvider, block) -> itemModelDataProvider.modTexture(block.asItem(), "block/coal_torch"))
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.torchBlock(block, "cgalaxy:block/coal_torch"))
            .withItem(block -> new CoalTorchBlockItem(block, new Item.Properties().tab(CreativeTabs.AssortmentsIG.instance)))
            .build();
    public static final RegistryObject<CoalWallTorchBlock> COAL_WALL_TORCH = REGISTER.create("coal_wall_torch", () ->
            new CoalWallTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION)
                    .noCollission().instabreak().sound(SoundType.WOOD).lootFrom(() -> ForgeRegistries.BLOCKS.getValue(CGalaxy.location("coal_torch")))))
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.wallTorchBlock(block, "cgalaxy:block/coal_torch"))
            .withLootTable(null)
            .withGeneratedBlockItem(false)
            .build();

    public static final RegistryObject<FallingBlock> MOON_REGOLITH = REGISTER.create("moon_regolith", () ->
            new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).strength(1.0f).sound(SoundType.SAND)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withTranslation("Moon Regolith")
            .build();
    public static final RegistryObject<Block> MOON_STONE = REGISTER.create("moon_stone", moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, Tags.Blocks.STONE)
            .withTranslation("Moon Stone")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDropWithSilkTouch(block, CGBlockInit.MOON_COBBLESTONE.get()))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.smeltingRecipe(finishedRecipeConsumer, block, CGBlockInit.MOON_COBBLESTONE.get(), 0.1f, 200))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.shaped2x2Recipe(finishedRecipeConsumer, block, 1, MOON_REGOLITH.get()))
            .build();
    public static final RegistryObject<Block> MOON_COBBLESTONE = REGISTER.create("moon_cobblestone", moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone")
            .build();
    public static final RegistryObject<Block> MOON_STONE_BRICKS = REGISTER.create("moon_stone_bricks", moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Bricks")
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.shaped2x2Recipe(finishedRecipeConsumer, block, 4, MOON_STONE.get()))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MOON_STONE.get()))
            .build();
    public static final RegistryObject<Block> MOON_SMOOTH_STONE = REGISTER.create("smooth_moon_stone", moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Smooth Moon Stone")
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.smeltingRecipe(finishedRecipeConsumer, block, MOON_STONE.get(), 0.1f, 200))
            .build();
    public static final RegistryObject<Block> MOON_CHISELED_STONE_BRICKS = REGISTER.create("chiseled_moon_stone_bricks", moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Chiseled Moon Stone Bricks")
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block)
                    .pattern("A")
                    .pattern("A")
                    .pattern(" ")
                    .define('A', CGBlockInit.MOON_STONE_BRICKS_SLAB.get())
                    .unlockedBy("has_moon_stone_brick_slabs", RecipeDataProvider.has(CGBlockInit.MOON_STONE_BRICKS_SLAB.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, CGBlockInit.MOON_STONE.get()))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, CGBlockInit.MOON_STONE_BRICKS.get()))
            .build();
    public static final RegistryObject<SlabBlock> MOON_STONE_SLAB = REGISTER.create("moon_stone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/moon_stone"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 6)
                    .pattern("   ")
                    .pattern("SSS")
                    .pattern("   ")
                    .define('S', MOON_STONE.get())
                    .unlockedBy("has_moon_stone", RecipeDataProvider.has(MOON_STONE.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, 2, MOON_STONE.get()))
            .build();
    public static final RegistryObject<StairBlock> MOON_STONE_STAIRS = REGISTER.create("moon_stone_stairs", () ->
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/moon_stone"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 4)
                    .pattern("S  ")
                    .pattern("SS ")
                    .pattern("SSS")
                    .define('S', MOON_STONE.get())
                    .unlockedBy("has_moon_stone", RecipeDataProvider.has(MOON_STONE.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MOON_STONE.get()))
            .build();
    public static final RegistryObject<SlabBlock> MOON_COBBLESTONE_SLAB = REGISTER.create("moon_cobblestone_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/moon_cobblestone"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 6)
                    .pattern("   ")
                    .pattern("SSS")
                    .pattern("   ")
                    .define('S', MOON_COBBLESTONE.get())
                    .unlockedBy("has_moon_cobblestone", RecipeDataProvider.has(MOON_COBBLESTONE.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, 2, MOON_COBBLESTONE.get()))
            .build();
    public static final RegistryObject<StairBlock> MOON_COBBLESTONE_STAIRS = REGISTER.create("moon_cobblestone_stairs", () ->
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/moon_cobblestone"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 4)
                    .pattern("S  ")
                    .pattern("SS ")
                    .pattern("SSS")
                    .define('S', MOON_COBBLESTONE.get())
                    .unlockedBy("has_moon_cobblestone", RecipeDataProvider.has(MOON_COBBLESTONE.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MOON_COBBLESTONE.get()))
            .build();
    public static final RegistryObject<SlabBlock> MOON_STONE_BRICKS_SLAB = REGISTER.create("moon_stone_bricks_slab", () ->
            new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Brick Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/moon_stone_bricks"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 6)
                    .pattern("   ")
                    .pattern("SSS")
                    .pattern("   ")
                    .define('S', MOON_STONE_BRICKS.get())
                    .unlockedBy("has_moon_stone_bricks", RecipeDataProvider.has(MOON_STONE_BRICKS.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, 2, MOON_STONE_BRICKS.get()))
            .build();
    public static final RegistryObject<StairBlock> MOON_STONE_BRICKS_STAIRS = REGISTER.create("moon_stone_bricks_stairs", () ->
            new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Brick Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/moon_stone_bricks"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 4)
                    .pattern("S  ")
                    .pattern("SS ")
                    .pattern("SSS")
                    .define('S', MOON_STONE_BRICKS.get())
                    .unlockedBy("has_moon_stone_bricks", RecipeDataProvider.has(MOON_STONE_BRICKS.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MOON_STONE_BRICKS.get()))
            .build();
    public static final RegistryObject<Block> MOON_IRON_ORE = REGISTER.create("moon_iron_ore", moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, Tags.Blocks.ORES_IRON, Tags.Blocks.ORES)
            .withExistingItemTags(Tags.Items.ORES_IRON, Tags.Items.ORES)
            .withTranslation("Moon Iron Ore")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createOreDrop(block, Items.RAW_IRON))
            .build();
    public static final RegistryObject<Block> MOON_SILICON_ORE = REGISTER.create("moon_silicon_ore", moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Silicon Ore")
            .withNewBlockTag("forge:ores/silicon")
            .withNewItemTag("forge:ores/silicon")
            .build();
    public static final RegistryObject<Block> MOON_ALUMINUM_ORE = REGISTER.create("moon_aluminum_ore", moonStoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Aluminum Ore")
            .withNewBlockTag("forge:ores/aluminum")
            .withNewItemTag("forge:ores/aluminum")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createOreDrop(block, CLMaterialInit.ALUMINUM.raw().get()))
            .build();
    public static final RegistryObject<Block> MOON_GLOWSTONE_ORE = REGISTER.create("moon_glowstone_ore", () ->
                    new Block(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).lightLevel(value -> 8).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Glowstone Ore")
            .withNewBlockTag("forge:ores/glowstone")
            .withNewItemTag("forge:ores/glowstone")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMultipleDrops(block, Items.GLOWSTONE_DUST, 2, 6))
            .build();

    public static final RegistryObject<MeteoriteBlock> ASTRAL_SAPPHIRE_ORE = REGISTER.create("astral_sapphire_ore", () ->
            new MeteoriteBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(45f, 600f).sound(SoundType.STONE)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL)
            .withBlockModel(null)
            .withTranslation("Astral Sapphire Meteorite")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDropWithSilkTouch(block, CGItemInit.ASTRAL_SAPPHIRE.get()))
            .build();
    public static final RegistryObject<Block> ASTRAL_SAPPHIRE_BLOCK = REGISTER.create("astral_sapphire_block", () ->
            new Block(BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(7.0F, 6.0f).sound(SoundType.METAL)))
            .withItemProperties(new Item.Properties().tab(CreativeTabs.AssortmentsIG.instance).rarity(Rarity.create("dark_blue", ChatFormatting.DARK_BLUE)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL, BlockTags.BEACON_BASE_BLOCKS)
            .withNewBlockTag("forge:storage_blocks/astral_sapphire")
            .withNewItemTag("forge:storage_blocks/astral_sapphire")
            .withTranslation("Block of Astral Sapphire")
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.shapelessRecipe(finishedRecipeConsumer,
                    block, 1, CGItemInit.ASTRAL_SAPPHIRE.get(), 9))
            .build();

    public static final RegistryObject<MeteoriteBlock> METEORITE = REGISTER.create("meteorite", () ->
            new MeteoriteBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(45f, 600f).sound(SoundType.STONE)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_DIAMOND_TOOL)
            .withBlockModel(null)
            .withTranslation("Meteorite")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDropWithSilkTouch(block, CGItemInit.METEORITE_CHUNK.get()))
            .build();

    public static final RegistryObject<Block> MOON_CHEESE = REGISTER.create("moon_cheese", () ->
            new Block(BlockBehaviour.Properties.of(Material.CLAY).sound(SoundType.SLIME_BLOCK)))
            .withTranslation("Moon Cheese")
            .build();

    public static final RegistryObject<LaunchPadBlock> LAUNCH_PAD = REGISTER.create("launch_pad", () ->
            new LaunchPadBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel(null)
            .withTranslation("Launch Pad")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 3)
                    .pattern("III")
                    .pattern("IBI")
                    .pattern("III")
                    .define('I', CLMaterialInit.STEEL.ingot().get())
                    .define('B', CLMaterialInit.STEEL.block().get())
                    .unlockedBy("has_steel", RecipeDataProvider.has(CLMaterialInit.STEEL.ingot().get()))
                    .save(finishedRecipeConsumer))
            .build();

    public static final RegistryObject<LunarCarrotsBlock> LUNAR_CARROTS = REGISTER.create("lunar_carrots", () ->
            new LunarCarrotsBlock(BlockBehaviour.Properties.copy(Blocks.CARROTS)))
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.age7CropBlock(block, "cgalaxy:block/lunar_carrots"))
            .withItemModel((itemModelDataProvider, block) -> itemModelDataProvider.modTexture(block.asItem(), "item/lunar_carrot"))
            .withRenderType(RenderType.cutout())
            .withTranslation("Lunar Carrot")
            .withItem(block -> new ItemNameBlockItem(block, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance).food(CGFoods.LUNAR_CARROT)))
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createCropDrop(block, StatePropertiesPredicate.Builder.properties().hasProperty(CarrotBlock.AGE, 7)))
            .build();

    public static final RegistryObject<LunacCropBlock> LUNAC_CROP = REGISTER.create("lunac_crop", () ->
            new LunacCropBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP)))
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.age7CrossBlock(block, "cgalaxy:block/lunac_crop"))
            .withItemModel((itemModelDataProvider, block) -> itemModelDataProvider.modTexture(block.asItem(), "item/lunac_seeds"))
            .withRenderType(RenderType.cutout())
            .withTranslation("Lunac Crop")
            .withItem(block -> new ItemNameBlockItem(block, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .build();

    public static final RegistryObject<FuelLoaderBlock> FUEL_LOADER = REGISTER.create("fuel_loader", () ->
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
            .build();
    public static final RegistryObject<FuelRefineryBlock> FUEL_REFINERY = REGISTER.create("fuel_refinery", () ->
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
            .build();
    public static final RegistryObject<GasExtractorBlock> GAS_EXTRACTOR = REGISTER.create("gas_extractor", () ->
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
            .build();
    public static final RegistryObject<RoomPressurizerBlock> ROOM_PRESSURIZER = REGISTER.create("room_pressurizer", () ->
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
            .build();
    public static final RegistryObject<SpaceStationCoreBlock> SPACE_STATION_CORE = REGISTER.create("space_station_core", () ->
            new SpaceStationCoreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeAllBlock(block, "cgalaxy:block/space_station_core_on"))
            .withTranslation("Space Station Core")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .build();

    public static final RegistryObject<LiquidBlock> KEROSENE = REGISTER.createFluid("kerosene", () ->
            new LiquidBlock(CGFluidInit.KEROSENE.getStill(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
            .build();
    public static final RegistryObject<LiquidBlock> OIL = REGISTER.createFluid("oil", () ->
            new LiquidBlock(CGFluidInit.OIL.getStill(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
            .build();

    public static Supplier<Block> moonStoneBlock() {
        return () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops());
    }
}
