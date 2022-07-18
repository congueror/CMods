package net.congueror.clib.util.registry.builders;

import net.congueror.clib.blocks.generic.CLBuddingBlock;
import net.congueror.clib.blocks.generic.CLOreBlock;
import net.congueror.clib.util.CreativeTabs;
import net.congueror.clib.util.HarvestLevels;
import net.congueror.clib.util.ListMap;
import net.congueror.clib.util.registry.builders.material_builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public abstract class ResourceBuilder<T extends ResourceBuilder<T, I>, I extends ResourceBuilder.ResourceObject> {
    protected final String modid;
    protected final String name;
    protected String capitalized;
    protected CreativeModeTab tab;

    public static final ListMap<String, ResourceObject> OBJECTS = new ListMap<>();

    protected final BlockBuilder.BlockDeferredRegister blockRegister;
    protected final ItemBuilder.ItemDeferredRegister itemRegister;

    public record ResourceDeferredRegister(String modid, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {

        public BasicMetalObject.Builder createBasicMetal(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLevel, int exp) {
            return new BasicMetalObject.Builder(modid, name, tab, blockRegister, itemRegister, hardness, harvestLevel, exp);
        }

        public AlloyMetalObject.Builder createAlloyMetal(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLevel) {
            return new AlloyMetalObject.Builder(modid, name, tab, blockRegister, itemRegister, hardness, harvestLevel);
        }

        public SingleGemObject.Builder createSingleGem(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLevel, int exp) {
            return new SingleGemObject.Builder(modid, name, tab, blockRegister, itemRegister, hardness, harvestLevel, exp);
        }

        public GeodeGemObject.Builder createGeodeGem(String name, CreativeModeTab tab) {
            return new GeodeGemObject.Builder(modid, name, tab, blockRegister, itemRegister);
        }

        public DebrisMetalObject.Builder createDebrisMetal(String name, String debrisName, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl, int exp) {
            return new DebrisMetalObject.Builder(modid, name, tab, blockRegister, itemRegister, hardness, harvestLvl, exp, debrisName);
        }

        public RadioactiveMetalObject.Builder createRadioactiveMetal(String name, CreativeModeTab tab, HarvestLevels harvestLvl, int exp) {
            return new RadioactiveMetalObject.Builder(modid, name, tab, blockRegister, itemRegister, harvestLvl, exp);
        }

        public SingleMetalObject.Builder createSingleMetal(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl, int exp) {
            return new SingleMetalObject.Builder(modid, name, tab, blockRegister, itemRegister, hardness, harvestLvl, exp);
        }
    }

    public interface ResourceObject {
        void generateRecipes(Consumer<FinishedRecipe> c);
    }

    public ResourceBuilder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister) {
        this.modid = modid;
        this.name = name;
        this.tab = tab;
        this.blockRegister = new BlockBuilder.BlockDeferredRegister(blockRegister, itemRegister);
        this.itemRegister = new ItemBuilder.ItemDeferredRegister(itemRegister);
        this.capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    protected abstract I buildObject();

    public I build() {
        I obj = buildObject();
        OBJECTS.addEntry(this.modid, obj);
        return obj;
    }

    protected ItemBuilder<Item> addGem() {
        return itemRegister.create(name, () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized)
                .withItemTag("forge:gems/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected ItemBuilder<Item> addIngot() {
        return itemRegister.create(name + "_ingot", () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Ingot")
                .withItemTag("forge:ingots/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected ItemBuilder<Item> addNugget() {
        return itemRegister.create(name + "_nugget", () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Nugget")
                .withItemTag("forge:nuggets/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected ItemBuilder<Item> addDust() {
        return itemRegister.create(name + "_dust", () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Dust")
                .withItemTag("forge:dusts/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected ItemBuilder<Item> addGear() {
        return itemRegister.create(name + "_gear", () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Gear")
                .withItemTag("forge:gears/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected ItemBuilder<Item> addRawIngot() {
        return itemRegister.create("raw_" + name, () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation("Raw " + capitalized)
                .withItemTag("forge:raw_materials/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected ItemBuilder<Item> addBlend() {
        return itemRegister.create(name + "_blend", () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Blend")
                .withItemTag("forge:ores/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected ItemBuilder<Item> addScrap() {
        return itemRegister.create(name + "_scrap", () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Scrap")
                .withItemTag("forge:scraps/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected BlockBuilder<CLOreBlock, BlockItem> addOre(int exp, HarvestLevels harvestLvl) {
        //Supplier<Item> drop = objectMap.get("raw") == null ? ((ItemBuilder<Item>) objectMap.get("gem")).regObject : ((ItemBuilder<Item>) objectMap.get("raw")).regObject;
        return blockRegister.create(name + "_ore", () -> new CLOreBlock(BlockBehaviour.Properties
                .of(Material.STONE).requiresCorrectToolForDrops()
                .strength(3.0f, 3.0f)
                .sound(SoundType.STONE), exp))
                .withCreativeTab(tab)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                .withNewBlockTag("forge:ores/" + name)
                .withItemTag("forge:ores/" + name)
                .withTranslation(capitalized + " Ore")
                //.withLootTable((lootTableDataProvider, block1) -> lootTableDataProvider.createOreDrop(block1, drop.get()))
                .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
    }

    protected BlockBuilder<CLOreBlock, BlockItem> addDeepslateOre(int exp, HarvestLevels harvestLvl) {
        return blockRegister.create("deepslate_" + name + "_ore", () -> new CLOreBlock(BlockBehaviour.Properties
                .of(Material.STONE).requiresCorrectToolForDrops()
                .strength(4.5f, 3.0f)
                .sound(SoundType.DEEPSLATE), exp * 2))
                .withCreativeTab(tab)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                .withNewBlockTag("forge:ores/" + name)
                .withItemTag("forge:ores/" + name)
                .withTranslation("Deepslate " + capitalized + " Ore")
                //.withLootTable((lootTableDataProvider, block1) -> lootTableDataProvider.createSingleDrop(block1, ((ItemBuilder<Item>) objectMap.get("raw")).regObject.get()))
                .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
    }

    protected BlockBuilder<Block, BlockItem> addBlock(float hardness, HarvestLevels harvestLvl) {
        return blockRegister.create(name + "_block", () -> new Block(BlockBehaviour.Properties
                .of(Material.METAL).requiresCorrectToolForDrops()
                .strength(hardness, 6.0f)
                .sound(SoundType.METAL)))
                .withCreativeTab(tab)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, harvestLvl.getTag())
                .withNewBlockTag("forge:storage_blocks/" + name)
                .withItemTag("forge:storage_blocks/" + name)
                .withTranslation("Block of " + capitalized)
                .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
    }

    protected BlockBuilder<CLOreBlock, BlockItem> addDebris(String debrisName, int exp, HarvestLevels harvestLvl) {
        return blockRegister.create(name + "_ore", () -> new CLOreBlock(BlockBehaviour.Properties
                .of(Material.STONE).requiresCorrectToolForDrops()
                .strength(8.0f, 28.0f)
                .sound(SoundType.STONE), exp))
                .withCreativeTab(tab)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                .withNewBlockTag("forge:ores/" + name)
                .withItemTag("forge:ores/" + name)
                .withTranslation(debrisName)
                .withBlockModel((blockModelDataProvider, block1) ->
                        blockModelDataProvider.cubeColumnBlock(block1,
                                block1.getRegistryName().getNamespace() + ":resource/" + name + "/" + block1.getRegistryName().getPath() + "_side",
                                block1.getRegistryName().getNamespace() + ":resource/" + name + "/" + block1.getRegistryName().getPath() + "_top"));
    }

    protected ItemBuilder<Item> addShard() {
        return itemRegister.create(name + "_shard", () -> new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Shard")
                .withItemTag("forge:shards/" + name)
                .withItemTag("forge:gems/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected BlockBuilder<Block, BlockItem> addCrystalBlock() {
        return blockRegister.create(name + "_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)))
                .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.CRYSTAL_SOUND_BLOCKS)
                .withNewBlockTag("forge:storage_blocks/" + name)
                .withItemTag("forge:storage_blocks/" + name)
                .withTranslation("Block of " + capitalized)
                .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
    }

    protected BlockBuilder<AmethystClusterBlock, BlockItem> addSmallBud() {
        return blockRegister.create("small_" + name + "_bud", () -> new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.copy(Blocks.SMALL_AMETHYST_BUD)))
                .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
                .withBlockModel((blockModelDataGenerator, block1) -> blockModelDataGenerator.directionalBlock(block1, blockModelDataGenerator.models()
                        .cross(block1.getRegistryName().getPath(),
                                new ResourceLocation(block1.getRegistryName().getNamespace(), "resource/" + name + "/" + block1.getRegistryName().getPath()))))
                .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "resource/" + name + "/" + block.getRegistryName().getPath()))
                .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
                .withTranslation("Small " + capitalized + " Bud")
                .withRenderType(RenderType.cutout());
    }

    protected BlockBuilder<AmethystClusterBlock, BlockItem> addMediumBud() {
        return blockRegister.create("medium_" + name + "_bud", () -> new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.copy(Blocks.MEDIUM_AMETHYST_BUD)))
                .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
                .withBlockModel((blockModelDataGenerator, block1) -> blockModelDataGenerator.directionalBlock(block1, blockModelDataGenerator.models()
                        .cross(block1.getRegistryName().getPath(),
                                new ResourceLocation(block1.getRegistryName().getNamespace(), "resource/" + name + "/" + block1.getRegistryName().getPath()))))
                .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "resource/" + name + "/" + block.getRegistryName().getPath()))
                .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
                .withTranslation("Medium " + capitalized + " Bud")
                .withRenderType(RenderType.cutout());
    }

    protected BlockBuilder<AmethystClusterBlock, BlockItem> addLargeBud() {
        return blockRegister.create("large_" + name + "_bud", () -> new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD)))
                .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
                .withBlockModel((blockModelDataGenerator, block1) -> blockModelDataGenerator.directionalBlock(block1, blockModelDataGenerator.models()
                        .cross(block1.getRegistryName().getPath(),
                                new ResourceLocation(block1.getRegistryName().getNamespace(), "resource/" + name + "/" + block1.getRegistryName().getPath()))))
                .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "resource/" + name + "/" + block.getRegistryName().getPath()))
                .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
                .withTranslation("Large " + capitalized + " Bud")
                .withRenderType(RenderType.cutout());
    }

    protected BlockBuilder<AmethystClusterBlock, BlockItem> addCluster(ItemBuilder<Item> shard) {
        return blockRegister.create(name + "_cluster", () -> new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)))
                .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
                .withBlockModel((blockModelDataGenerator, block1) -> blockModelDataGenerator.directionalBlock(block1, blockModelDataGenerator.models()
                        .cross(block1.getRegistryName().getPath(),
                                new ResourceLocation(block1.getRegistryName().getNamespace(), "resource/" + name + "/" + block1.getRegistryName().getPath()))))
                .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "resource/" + name + "/" + block.getRegistryName().getPath()))
                .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createClusterDrop(block, shard.regObject.get()))
                .withTranslation(capitalized + " Cluster")
                .withRenderType(RenderType.cutout());
    }

    protected BlockBuilder<CLBuddingBlock, BlockItem> addBudding(BlockBuilder<AmethystClusterBlock, BlockItem> small_bud, BlockBuilder<AmethystClusterBlock, BlockItem> medium_bud
        , BlockBuilder<AmethystClusterBlock, BlockItem> large_bud, BlockBuilder<AmethystClusterBlock, BlockItem> cluster) {
        return blockRegister.create("budding_" + name, () -> new CLBuddingBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST),
                        small_bud.regObject,
                        medium_bud.regObject,
                        large_bud.regObject,
                        cluster.regObject))
                .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.CRYSTAL_SOUND_BLOCKS)
                .withLootTable(null)
                .withTranslation(capitalized + " Budding")
                .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
    }

    public static abstract class OreBuilder<T extends ResourceBuilder<T, I>, I extends ResourceBuilder.ResourceObject> extends ResourceBuilder<T, I> {

        protected BlockBuilder<CLOreBlock, BlockItem> ore;

        public OreBuilder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, HarvestLevels harvestLvl, int exp) {
            super(modid, name, tab, blockRegister, itemRegister);
            ore = addOre(exp, harvestLvl);
        }

        public T withOreAffix(String affix) {
            ore.withTranslation(affix + " " + name.substring(0, 1).toUpperCase() + name.substring(1) + " Ore");
            return (T) this;
        }
    }
}

