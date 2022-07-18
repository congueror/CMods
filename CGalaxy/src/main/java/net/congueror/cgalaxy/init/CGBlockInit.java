package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.blocks.*;
import net.congueror.cgalaxy.blocks.fuel_loader.FuelLoaderBlock;
import net.congueror.cgalaxy.blocks.fuel_refinery.FuelRefineryBlock;
import net.congueror.cgalaxy.blocks.gas_extractor.GasExtractorBlock;
import net.congueror.cgalaxy.blocks.LaunchPadBlock;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlock;
import net.congueror.cgalaxy.blocks.sealed_cable.SealedEnergyCableBlock;
import net.congueror.cgalaxy.blocks.sealed_cable.SealedFluidCableBlock;
import net.congueror.cgalaxy.blocks.sealed_cable.SealedItemCableBlock;
import net.congueror.cgalaxy.blocks.solar_generator.SolarGeneratorBlock;
import net.congueror.cgalaxy.blocks.space_station_creator.SpaceStationCreatorBlock;
import net.congueror.cgalaxy.blocks.space_station_creator.SpaceStationCreatorSecondBlock;
import net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreBlock;
import net.congueror.cgalaxy.items.CoalTorchBlockItem;
import net.congueror.cgalaxy.util.CGFoods;
import net.congueror.clib.init.CLItemInit;
import net.congueror.clib.init.CLMaterialInit;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.data.LootTableDataProvider;
import net.congueror.clib.util.registry.data.RecipeDataProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
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
            .withRenderType(RenderType.cutout())
            .build();
    public static final RegistryObject<CoalWallTorchBlock> COAL_WALL_TORCH = REGISTER.create("coal_wall_torch", () ->
            new CoalWallTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION)
                    .noCollission().instabreak().sound(SoundType.WOOD).lootFrom(() -> ForgeRegistries.BLOCKS.getValue(CGalaxy.location("coal_torch")))))
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.wallTorchBlock(block, "cgalaxy:block/coal_torch"))
            .withLootTable(null)
            .withGeneratedBlockItem(false)
            .withRenderType(RenderType.cutout())
            .build();

    public static final RegistryObject<FallingBlock> MOON_REGOLITH = REGISTER.create("moon_regolith", () ->
            new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).strength(1.0f).sound(SoundType.SAND)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withTranslation("Moon Regolith")
            .build();
    public static final RegistryObject<Block> MOON_STONE = REGISTER.create("moon_stone", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, Tags.Blocks.STONE)
            .withTranslation("Moon Stone")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDropWithSilkTouch(block, CGBlockInit.MOON_COBBLESTONE.get()))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.smeltingRecipe(finishedRecipeConsumer, block, CGBlockInit.MOON_COBBLESTONE.get(), 0.1f, 200))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.shaped2x2Recipe(finishedRecipeConsumer, block, 1, MOON_REGOLITH.get()))
            .build();
    public static final RegistryObject<Block> MOON_COBBLESTONE = REGISTER.create("moon_cobblestone", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Cobblestone")
            .build();
    public static final RegistryObject<Block> MOON_STONE_BRICKS = REGISTER.create("moon_stone_bricks", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Stone Bricks")
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.shaped2x2Recipe(finishedRecipeConsumer, block, 4, MOON_STONE.get()))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MOON_STONE.get()))
            .build();
    public static final RegistryObject<Block> MOON_SMOOTH_STONE = REGISTER.create("smooth_moon_stone", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Smooth Moon Stone")
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.smeltingRecipe(finishedRecipeConsumer, block, MOON_STONE.get(), 0.1f, 200))
            .build();
    public static final RegistryObject<Block> MOON_CHISELED_STONE_BRICKS = REGISTER.create("chiseled_moon_stone_bricks", stoneBlock())
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
    public static final RegistryObject<Block> MOON_IRON_ORE = REGISTER.create("moon_iron_ore", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, Tags.Blocks.ORES_IRON, Tags.Blocks.ORES)
            .withItemTags(Tags.Items.ORES_IRON, Tags.Items.ORES)
            .withTranslation("Moon Iron Ore")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createOreDrop(block, Items.RAW_IRON))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.smeltingRecipe(finishedRecipeConsumer, Items.IRON_INGOT, RecipeDataProvider.getTag("forge:ores/iron"), 0.7f, 200))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.blastingRecipe(finishedRecipeConsumer, Items.IRON_INGOT, RecipeDataProvider.getTag("forge:ores/iron"), 0.7f, 100))
            .build();
    public static final RegistryObject<Block> MOON_SILICON_ORE = REGISTER.create("moon_silicon_ore", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Silicon Ore")
            .withNewBlockTag("forge:ores/silicon")
            .withItemTag("forge:ores/silicon")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createOreDrop(block, CLItemInit.SILICON.get()))
            .build();
    public static final RegistryObject<Block> MOON_ALUMINUM_ORE = REGISTER.create("moon_aluminum_ore", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Aluminum Ore")
            .withNewBlockTag("forge:ores/aluminum")
            .withItemTag("forge:ores/aluminum")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createOreDrop(block, CLMaterialInit.ALUMINUM.raw().get()))
            .build();
    public static final RegistryObject<Block> MOON_GLOWSTONE_ORE = REGISTER.create("moon_glowstone_ore", () ->
                    new Block(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).lightLevel(value -> 8).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Moon Glowstone Ore")
            .withNewBlockTag("forge:ores/glowstone")
            .withItemTag("forge:ores/glowstone")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createMultipleDrops(block, Items.GLOWSTONE_DUST, 2, 6))
            .build();

    public static final RegistryObject<FallingBlock> MARTIAN_SOIL = REGISTER.create("martian_soil", () ->
                    new FallingBlock(BlockBehaviour.Properties.of(Material.SAND).strength(1.0f).sound(SoundType.SAND)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withTranslation("Martian Soil")
            .build();
    public static final RegistryObject<Block> MARTIAN_STONE = REGISTER.create("martian_stone", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL, Tags.Blocks.STONE)
            .withTranslation("Martian Stone")
            .withLootTable((lootTableDataProvider, block) -> lootTableDataProvider.createSingleDropWithSilkTouch(block, CGBlockInit.MARTIAN_COBBLESTONE.get()))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.smeltingRecipe(finishedRecipeConsumer, block, CGBlockInit.MARTIAN_COBBLESTONE.get(), 0.1f, 200))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.shaped2x2Recipe(finishedRecipeConsumer, block, 1, CGBlockInit.MARTIAN_SOIL.get()))
            .build();
    public static final RegistryObject<Block> MARTIAN_COBBLESTONE = REGISTER.create("martian_cobblestone", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Martian Cobblestone")
            .build();
    public static final RegistryObject<Block> MARTIAN_STONE_BRICKS = REGISTER.create("martian_stone_bricks", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Martian Stone Bricks")
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.shaped2x2Recipe(finishedRecipeConsumer, block, 4, MARTIAN_STONE.get()))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MARTIAN_STONE.get()))
            .build();
    public static final RegistryObject<Block> MARTIAN_SMOOTH_STONE = REGISTER.create("smooth_martian_stone", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Smooth Martian Stone")
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.smeltingRecipe(finishedRecipeConsumer, block, MARTIAN_STONE.get(), 0.1f, 200))
            .build();
    public static final RegistryObject<Block> MARTIAN_CHISELED_STONE_BRICKS = REGISTER.create("chiseled_martian_stone_bricks", stoneBlock())
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Chiseled Martian Stone Bricks")
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block)
                    .pattern("A")
                    .pattern("A")
                    .pattern(" ")
                    .define('A', CGBlockInit.MARTIAN_STONE_BRICKS_SLAB.get())
                    .unlockedBy("has_martian_stone_brick_slabs", RecipeDataProvider.has(CGBlockInit.MARTIAN_STONE_BRICKS_SLAB.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, CGBlockInit.MARTIAN_STONE.get()))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, CGBlockInit.MARTIAN_STONE_BRICKS.get()))
            .build();
    public static final RegistryObject<SlabBlock> MARTIAN_STONE_SLAB = REGISTER.create("martian_stone_slab", () ->
                    new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Martian Stone Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/martian_stone"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 6)
                    .pattern("   ")
                    .pattern("SSS")
                    .pattern("   ")
                    .define('S', MARTIAN_STONE.get())
                    .unlockedBy("has_martian_stone", RecipeDataProvider.has(MARTIAN_STONE.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, 2, MARTIAN_STONE.get()))
            .build();
    public static final RegistryObject<StairBlock> MARTIAN_STONE_STAIRS = REGISTER.create("martian_stone_stairs", () ->
                    new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Martian Stone Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/martian_stone"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 4)
                    .pattern("S  ")
                    .pattern("SS ")
                    .pattern("SSS")
                    .define('S', MARTIAN_STONE.get())
                    .unlockedBy("has_martian_stone", RecipeDataProvider.has(MARTIAN_STONE.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MARTIAN_STONE.get()))
            .build();
    public static final RegistryObject<SlabBlock> MARTIAN_COBBLESTONE_SLAB = REGISTER.create("martian_cobblestone_slab", () ->
                    new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Martian Cobblestone Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/martian_cobblestone"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 6)
                    .pattern("   ")
                    .pattern("SSS")
                    .pattern("   ")
                    .define('S', MARTIAN_COBBLESTONE.get())
                    .unlockedBy("has_martian_cobblestone", RecipeDataProvider.has(MARTIAN_COBBLESTONE.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, 2, MARTIAN_COBBLESTONE.get()))
            .build();
    public static final RegistryObject<StairBlock> MARTIAN_COBBLESTONE_STAIRS = REGISTER.create("martian_cobblestone_stairs", () ->
                    new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Martian Cobblestone Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/martian_cobblestone"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 4)
                    .pattern("S  ")
                    .pattern("SS ")
                    .pattern("SSS")
                    .define('S', MARTIAN_COBBLESTONE.get())
                    .unlockedBy("has_martian_cobblestone", RecipeDataProvider.has(MARTIAN_COBBLESTONE.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MARTIAN_COBBLESTONE.get()))
            .build();
    public static final RegistryObject<SlabBlock> MARTIAN_STONE_BRICKS_SLAB = REGISTER.create("martian_stone_bricks_slab", () ->
                    new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Martian Stone Brick Slab")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.slabBlock(block, "cgalaxy:block/martian_stone_bricks"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 6)
                    .pattern("   ")
                    .pattern("SSS")
                    .pattern("   ")
                    .define('S', MARTIAN_STONE_BRICKS.get())
                    .unlockedBy("has_moon_stone_bricks", RecipeDataProvider.has(MARTIAN_STONE_BRICKS.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, 2, MARTIAN_STONE_BRICKS.get()))
            .build();
    public static final RegistryObject<StairBlock> MARTIAN_STONE_BRICKS_STAIRS = REGISTER.create("martian_stone_bricks_stairs", () ->
                    new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withTranslation("Martian Stone Brick Stairs")
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.stairsBlock(block, "cgalaxy:block/martian_stone_bricks"))
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block, 4)
                    .pattern("S  ")
                    .pattern("SS ")
                    .pattern("SSS")
                    .define('S', MARTIAN_STONE_BRICKS.get())
                    .unlockedBy("has_martian_stone_bricks", RecipeDataProvider.has(MARTIAN_STONE_BRICKS.get()))
                    .save(finishedRecipeConsumer))
            .withRecipe((finishedRecipeConsumer, block) -> RecipeDataProvider.stoneCutterRecipe(finishedRecipeConsumer, block, MARTIAN_STONE_BRICKS.get()))
            .build();

    public static final RegistryObject<SnowLayerBlock> DRY_ICE = REGISTER.create("dry_ice", () ->
            new SnowLayerBlock(BlockBehaviour.Properties.copy(Blocks.ICE).friction(1.0f).isViewBlocking((p_187417_, p_187418_, p_187419_) -> p_187417_.getValue(SnowLayerBlock.LAYERS) >= 8)))
            .withExistingBlockTags(BlockTags.ICE)
            .withTranslation("Dry Ice")
            .withBlockModel((blockModelDataProvider, snowLayerBlock) -> blockModelDataProvider.snowLayerBlock(snowLayerBlock, CGalaxy.location("block/dry_ice")))
            .withLootTable((lootTableDataProvider, snowLayerBlock) -> lootTableDataProvider.createSilkTouchOnlyDrop(snowLayerBlock, snowLayerBlock.asItem()))
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
            .withItemTag("forge:storage_blocks/astral_sapphire")
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
            .withRecipe((finishedRecipeConsumer, block) -> ShapedRecipeBuilder.shaped(block))
            .build();

    public static final RegistryObject<AdvancedDoorBlock> ADVANCED_DOOR = REGISTER.create("advanced_door", () ->
                    new AdvancedDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)))
            .withItem(advancedDoorBlock -> new DoubleHighBlockItem(advancedDoorBlock, new Item.Properties().tab(CreativeTabs.CGalaxyIG.instance)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel(null)
            .withItemModel((itemModelDataProvider, advancedDoorBlock) -> itemModelDataProvider.modTexture(advancedDoorBlock.asItem(), "item/advanced_door"))
            .withLootTable(LootTableDataProvider::createDoorDrop)
            .withRenderType(RenderType.cutout())
            .withTranslation("Advanced Door")
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
            .withItem(block -> new ItemNameBlockItem(block, new Item.Properties().tab(CreativeTabs.AssortmentsIG.instance).food(CGFoods.LUNAR_CARROT)))
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

    public static final RegistryObject<SealedEnergyCableBlock> SEALED_ENERGY_CABLE = REGISTER.create("sealed_energy_cable", () ->
            new SealedEnergyCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Sealed Energy Cable")
            .withBlockModel(null)
            .withItemModel((itemModelDataProvider, sealedEnergyCableBlock) -> itemModelDataProvider.texture(sealedEnergyCableBlock.asItem(), "cgalaxy:block/sealed_energy_cable"))
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withRenderType(RenderType.translucent())
            .build();

    public static final RegistryObject<SealedItemCableBlock> SEALED_ITEM_CABLE = REGISTER.create("sealed_item_cable", () ->
                    new SealedItemCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Sealed Item Cable")
            .withBlockModel(null)
            .withItemModel((itemModelDataProvider, sealedEnergyCableBlock) -> itemModelDataProvider.texture(sealedEnergyCableBlock.asItem(), "cgalaxy:block/sealed_item_cable"))
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withRenderType(RenderType.translucent())
            .build();

    public static final RegistryObject<SealedFluidCableBlock> SEALED_FLUID_CABLE = REGISTER.create("sealed_fluid_cable", () ->
                    new SealedFluidCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Sealed Fluid Cable")
            .withBlockModel(null)
            .withItemModel((itemModelDataProvider, sealedEnergyCableBlock) -> itemModelDataProvider.texture(sealedEnergyCableBlock.asItem(), "cgalaxy:block/sealed_fluid_cable"))
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withRenderType(RenderType.translucent())
            .build();

    public static final RegistryObject<SolarGeneratorBlock> SOLAR_GENERATOR_1 = REGISTER.create("solar_generator_1", () ->
                    new SolarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 20, 1))//TODO
            .withCreativeTab(CreativeTabs.MachinesIG.instance)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeBottomTopBlock(block, "cgalaxy:block/solar_generator_side", "cgalaxy:block/machine_frame", "cgalaxy:block/solar_generator_1_top"))
            .withTranslation("Tier 1 Solar Generator")
            .withLootTable(LootTableDataProvider::createMachineDrop)
            .build();
    public static final RegistryObject<SolarGeneratorBlock> SOLAR_GENERATOR_2 = REGISTER.create("solar_generator_2", () ->
                    new SolarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 50, 2))
            .withCreativeTab(CreativeTabs.MachinesIG.instance)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeBottomTopBlock(block, "cgalaxy:block/solar_generator_side", "cgalaxy:block/machine_frame", "cgalaxy:block/solar_generator_2_top"))
            .withTranslation("Tier 2 Solar Generator")
            .withLootTable(LootTableDataProvider::createMachineDrop)
            .build();
    public static final RegistryObject<SolarGeneratorBlock> SOLAR_GENERATOR_3 = REGISTER.create("solar_generator_3", () ->
                    new SolarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 120, 3))
            .withCreativeTab(CreativeTabs.MachinesIG.instance)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeBottomTopBlock(block, "cgalaxy:block/solar_generator_side", "cgalaxy:block/machine_frame", "cgalaxy:block/solar_generator_3_top"))
            .withTranslation("Tier 3 Solar Generator")
            .withLootTable(LootTableDataProvider::createMachineDrop)
            .build();
    public static final RegistryObject<SolarGeneratorBlock> SOLAR_GENERATOR_CREATIVE = REGISTER.create("solar_generator_creative", () ->
                    new SolarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 10000, 0))
            .withCreativeTab(CreativeTabs.MachinesIG.instance)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeBottomTopBlock(block, "cgalaxy:block/solar_generator_side", "cgalaxy:block/machine_frame", "cgalaxy:block/solar_generator_3_top"))
            .withTranslation("Creative Solar Generator")
            .withLootTable(LootTableDataProvider::createMachineDrop)
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
            .withLootTable(LootTableDataProvider::createMachineDrop)
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
            .withLootTable(LootTableDataProvider::createMachineDrop)
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
            .withLootTable(LootTableDataProvider::createMachineDrop)
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
            .withLootTable(LootTableDataProvider::createMachineDrop)
            .build();
    public static final RegistryObject<SpaceStationCreatorBlock> SPACE_STATION_CREATOR = REGISTER.create("space_station_creator", () ->
            new SpaceStationCreatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, spaceStationCreatorBlock) -> blockModelDataProvider.facingBlock(spaceStationCreatorBlock,
                    blockModelDataProvider.models().getExistingFile(CGalaxy.location("block/space_station_creator"))))
            .withTranslation("Space Station Creator")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .withLootTable(LootTableDataProvider::createMachineDrop)
            .build();
    public static final RegistryObject<SpaceStationCreatorSecondBlock> SPACE_STATION_CREATOR_1 = REGISTER.create("space_station_creator_1", () ->
                    new SpaceStationCreatorSecondBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()))
            .withGeneratedBlockItem(false)
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, spaceStationCreatorSecondBlock) -> blockModelDataProvider.simpleBlock(spaceStationCreatorSecondBlock,
                    blockModelDataProvider.models().getExistingFile(new ResourceLocation("minecraft:block/barrier"))))
            .build();
    public static final RegistryObject<SpaceStationCoreBlock> SPACE_STATION_CORE = REGISTER.create("space_station_core", () ->
            new SpaceStationCoreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1.0F, 3600000.0F)))
            .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL)
            .withBlockModel((blockModelDataProvider, block) -> blockModelDataProvider.cubeAllBlock(block, "cgalaxy:block/space_station_core_on"))
            .withTranslation("Space Station Core")
            .withCreativeTab(CreativeTabs.CGalaxyIG.instance)
            .build();

    public static final RegistryObject<BlockHolderBlock> BLOCK_HOLDER = REGISTER.create("block_holder", () ->
            new BlockHolderBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK)))
            .withCreativeTab(null)
            .withBlockModel(null)
            .withItemModel((itemModelDataProvider, block) -> itemModelDataProvider.texture(block.asItem(), "minecraft:item/barrier"))
            .withTranslation("Block Holder")
            .build();

    public static final RegistryObject<LiquidBlock> KEROSENE = REGISTER.createFluid("kerosene", () ->
            new LiquidBlock(CGFluidInit.KEROSENE.getStill(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
            .build();
    public static final RegistryObject<LiquidBlock> OIL = REGISTER.createFluid("oil", () ->
            new LiquidBlock(CGFluidInit.OIL.getStill(), BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
            .build();

    public static Supplier<Block> stoneBlock() {
        return () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 6.5f).requiresCorrectToolForDrops());
    }
}
