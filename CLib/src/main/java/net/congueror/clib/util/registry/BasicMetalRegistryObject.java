package net.congueror.clib.util.registry;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.blocks.generic.CLOreBlock;
import net.congueror.clib.util.HarvestLevels;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BasicMetalRegistryObject {

    private final RegistryObject<Item> ingot;
    private final RegistryObject<Item> nugget;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> gear;
    private final RegistryObject<Item> raw;

    private final RegistryObject<Block> ore;
    private final RegistryObject<Block> deepslate;
    private final RegistryObject<Block> block;

    private BasicMetalRegistryObject(RegistryObject<Item> ingot,
                                     RegistryObject<Item> nugget,
                                     RegistryObject<Item> dust,
                                     RegistryObject<Item> gear,
                                     RegistryObject<Item> raw,
                                     RegistryObject<Block> ore,
                                     RegistryObject<Block> deepslate,
                                     RegistryObject<Block> block) {
        this.ingot = ingot;
        this.nugget = nugget;
        this.dust = dust;
        this.gear = gear;
        this.raw = raw;
        this.ore = ore;
        this.deepslate = deepslate;
        this.block = block;
    }

    public RegistryObject<Item> getIngot() {
        return ingot;
    }

    public RegistryObject<Item> getNugget() {
        return nugget;
    }

    public RegistryObject<Item> getDust() {
        return dust;
    }

    public RegistryObject<Item> getGear() {
        return gear;
    }

    public RegistryObject<Item> getRawItem() {
        return raw;
    }

    public RegistryObject<Block> getOre() {
        return ore;
    }

    public RegistryObject<Block> getDeepslateOre() {
        return deepslate;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    public static class BasicMetalBuilder {
        final String name;

        ItemBuilder ingot;
        ItemBuilder nugget;
        ItemBuilder dust;
        ItemBuilder gear;
        ItemBuilder raw;

        BlockBuilder ore;
        BlockBuilder deepslate;
        BlockBuilder block;

        public BasicMetalBuilder(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl, int exp) {
            this.name = name;
            String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
            ingot = new ItemBuilder(name + "_ingot", new Item(new Item.Properties().tab(tab)))
                    .withTranslation(capitalized + " Ingot")
                    .withNewItemTag("forge:ingots/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.texture(item, "resource/" + name + "/" + item));
            nugget = new ItemBuilder(name + "_nugget", new Item(new Item.Properties().tab(tab)))
                    .withTranslation(capitalized + " Nugget")
                    .withNewItemTag("forge:nuggets/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.texture(item, "resource/" + name + "/" + item));
            dust = new ItemBuilder(name + "_dust", new Item(new Item.Properties().tab(tab)))
                    .withTranslation(capitalized + " Dust")
                    .withNewItemTag("forge:dusts/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.texture(item, "resource/" + name + "/" + item));
            gear = new ItemBuilder(name + "_gear", new Item(new Item.Properties().tab(tab)))
                    .withTranslation(capitalized + " Gear")
                    .withNewItemTag("forge:gears/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.texture(item, "resource/" + name + "/" + item));
            raw = new ItemBuilder("raw_" + name, new Item(new Item.Properties().tab(tab)))
                    .withTranslation("Raw " + capitalized)
                    .withNewItemTag("forge:ores/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.texture(item, "resource/" + name + "/" + item));

            ore = new BlockBuilder(name + "_ore", new CLOreBlock(BlockBehaviour.Properties
                    .of(Material.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f)
                    .sound(SoundType.STONE), exp))
                    .withCreativeTab(tab)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                    .withNewBlockTag("forge:ores/" + name)
                    .withNewItemTag("forge:ores/" + name)
                    .withTranslation(capitalized + " Ore")
                    .withLootTable((lootTableDataProvider, block1) -> lootTableDataProvider.createOreDrop(block1, raw.getItem()))
                    .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
            deepslate = new BlockBuilder("deepslate_" + name + "_ore", new CLOreBlock(BlockBehaviour.Properties
                    .of(Material.STONE).requiresCorrectToolForDrops()
                    .strength(4.5f, 3.0f)
                    .sound(SoundType.DEEPSLATE), exp * 2))
                    .withCreativeTab(tab)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                    .withNewBlockTag("forge:ores/" + name)
                    .withNewItemTag("forge:ores/" + name)
                    .withTranslation(capitalized + " Ore")
                    .withLootTable((lootTableDataProvider, block1) -> lootTableDataProvider.createSingleDrop(block1, raw.getItem()))
                    .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
            block = new BlockBuilder(name + "_block", new Block(BlockBehaviour.Properties
                    .of(Material.METAL).requiresCorrectToolForDrops()
                    .strength(hardness, 6.0f)
                    .sound(SoundType.METAL)))
                    .withCreativeTab(tab)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.BEACON_BASE_BLOCKS, harvestLvl.getTag())
                    .withNewBlockTag("forge:storage_blocks/" + name)
                    .withNewItemTag("forge:storage_blocks/" + name)
                    .withTranslation("Block of " + capitalized)
                    .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
        }

        public BasicMetalBuilder withOreAffix(String affix) {
            this.ore.withTranslation(affix + " " + name.substring(0, 1).toUpperCase() + name.substring(1) + " Ore");
            return this;
        }

        public BasicMetalRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new BasicMetalRegistryObject(
                    ingot.build(itemRegistry),
                    nugget.build(itemRegistry),
                    dust.build(itemRegistry),
                    gear.build(itemRegistry),
                    raw.build(itemRegistry),
                    ore.build(blockRegistry),
                    deepslate.build(blockRegistry),
                    block.build(blockRegistry)
            );
        }
    }
}
