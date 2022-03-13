package net.congueror.clib.util.registry;

import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.congueror.clib.blocks.generic.CLOreBlock;
import net.congueror.clib.util.HarvestLevels;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

@SuppressWarnings("unchecked")
public abstract class ResourceBuilder<T extends ResourceBuilder<T>> {
    protected final String name;
    protected String capitalized;
    protected CreativeModeTab tab;

    protected ItemBuilder gem;
    protected ItemBuilder ingot;
    protected ItemBuilder nugget;
    protected ItemBuilder dust;
    protected ItemBuilder gear;
    protected ItemBuilder raw;
    protected ItemBuilder blend;
    protected ItemBuilder scrap;

    protected BlockBuilder ore;
    protected BlockBuilder deepslate;
    protected BlockBuilder block;

    public ResourceBuilder(String name, CreativeModeTab tab) {
        this.name = name;
        this.tab = tab;
        capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public T withOreAffix(String affix) {
        this.ore.withTranslation(affix + " " + name.substring(0, 1).toUpperCase() + name.substring(1) + " Ore");
        return (T) this;
    }

    protected void addGem() {
        gem = new ItemBuilder(name, new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized)
                .withNewItemTag("forge:gems/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected void addIngot() {
        ingot = new ItemBuilder(name + "_ingot", new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Ingot")
                .withNewItemTag("forge:ingots/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected void addNugget() {
        nugget = new ItemBuilder(name + "_nugget", new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Nugget")
                .withNewItemTag("forge:nuggets/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected void addDust() {
        dust = new ItemBuilder(name + "_dust", new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Dust")
                .withNewItemTag("forge:dusts/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected void addGear() {
        gear = new ItemBuilder(name + "_gear", new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Gear")
                .withNewItemTag("forge:gears/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected void addRawIngot() {
        raw = new ItemBuilder("raw_" + name, new Item(new Item.Properties().tab(tab)))
                .withTranslation("Raw " + capitalized)
                .withNewItemTag("forge:ores/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected void addBlend() {
        blend = new ItemBuilder(name + "_blend", new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Blend")
                .withNewItemTag("forge:ores/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected void addScrap() {
        scrap = new ItemBuilder(name + "_scrap", new Item(new Item.Properties().tab(tab)))
                .withTranslation(capitalized + " Scrap")
                .withNewItemTag("forge:scraps/" + name)
                .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
    }

    protected void addOre(int exp, HarvestLevels harvestLvl) {
        Item drop = raw == null ? gem.getItem() : raw.getItem();
        ore = new BlockBuilder(name + "_ore", new CLOreBlock(BlockBehaviour.Properties
                .of(Material.STONE).requiresCorrectToolForDrops()
                .strength(3.0f, 3.0f)
                .sound(SoundType.STONE), exp))
                .withCreativeTab(tab)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                .withNewBlockTag("forge:ores/" + name)
                .withNewItemTag("forge:ores/" + name)
                .withTranslation(capitalized + " Ore")
                .withLootTable((lootTableDataProvider, block1) -> lootTableDataProvider.createOreDrop(block1, drop))
                .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
    }

    protected void addDeepslateOre(int exp, HarvestLevels harvestLvl) {
        deepslate = new BlockBuilder("deepslate_" + name + "_ore", new CLOreBlock(BlockBehaviour.Properties
                .of(Material.STONE).requiresCorrectToolForDrops()
                .strength(4.5f, 3.0f)
                .sound(SoundType.DEEPSLATE), exp * 2))
                .withCreativeTab(tab)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                .withNewBlockTag("forge:ores/" + name)
                .withNewItemTag("forge:ores/" + name)
                .withTranslation("Deepslate " + capitalized + " Ore")
                .withLootTable((lootTableDataProvider, block1) -> lootTableDataProvider.createSingleDrop(block1, raw.getItem()))
                .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
    }

    protected void addBlock(float hardness, HarvestLevels harvestLvl) {
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

    protected void addDebris(String debrisName, int exp, HarvestLevels harvestLvl) {
        ore = new BlockBuilder(name + "_ore", new CLOreBlock(BlockBehaviour.Properties
                .of(Material.STONE).requiresCorrectToolForDrops()
                .strength(8.0f, 28.0f)
                .sound(SoundType.STONE), exp))
                .withCreativeTab(tab)
                .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                .withNewBlockTag("forge:ores/" + name)
                .withNewItemTag("forge:ores/" + name)
                .withTranslation(debrisName)
                .withBlockModel((blockModelDataProvider, block1) ->
                        blockModelDataProvider.cubeColumnBlock(block1,
                                block1.getRegistryName().getNamespace() + ":resource/" + name + "/" + block1.getRegistryName().getPath() + "_side",
                                block1.getRegistryName().getNamespace() + ":resource/" + name + "/" + block1.getRegistryName().getPath() + "_top"));
    }
}

