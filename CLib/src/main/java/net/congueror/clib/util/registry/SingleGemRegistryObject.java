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

public class SingleGemRegistryObject {

    private final RegistryObject<Item> gem;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> gear;

    private final RegistryObject<Block> block;
    private final RegistryObject<Block> ore;

    private SingleGemRegistryObject(RegistryObject<Item> gem, RegistryObject<Item> dust, RegistryObject<Item> gear, RegistryObject<Block> block, RegistryObject<Block> ore) {
        this.gem = gem;
        this.dust = dust;
        this.gear = gear;
        this.block = block;
        this.ore = ore;
    }

    public RegistryObject<Item> getGem() {
        return gem;
    }

    public RegistryObject<Item> getDust() {
        return dust;
    }

    public RegistryObject<Item> getGear() {
        return gear;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    public RegistryObject<Block> getOre() {
        return ore;
    }

    public static class SingleGemBuilder {
        final String name;

        ItemBuilder gem;
        ItemBuilder dust;
        ItemBuilder gear;

        BlockBuilder block;
        BlockBuilder ore;

        public SingleGemBuilder(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl, int exp) {
            this.name = name;
            String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1);
            gem = new ItemBuilder(name, new Item(new Item.Properties().tab(tab)))
                    .withTranslation(capitalized)
                    .withNewItemTag("forge:gems/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.texture(item, "resource/" + name + "/" + item));
            dust = new ItemBuilder(name + "_dust", new Item(new Item.Properties().tab(tab)))
                    .withTranslation(capitalized + " Dust")
                    .withNewItemTag("forge:dusts/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.texture(item, "resource/" + name + "/" + item));
            gear = new ItemBuilder(name + "_gear", new Item(new Item.Properties().tab(tab)))
                    .withTranslation(capitalized + " Gear")
                    .withNewItemTag("forge:gears/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.texture(item, "resource/" + name + "/" + item));

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
            ore = new BlockBuilder(name + "_ore", new CLOreBlock(BlockBehaviour.Properties
                    .of(Material.STONE).requiresCorrectToolForDrops()
                    .strength(3.0f, 3.0f)
                    .sound(SoundType.STONE), exp))
                    .withCreativeTab(tab)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, harvestLvl.getTag())
                    .withNewBlockTag("forge:ores/" + name)
                    .withNewItemTag("forge:ores/" + name)
                    .withTranslation(capitalized + " Ore")
                    .withLootTable((lootTableDataProvider, block1) -> lootTableDataProvider.createMultipleDrops(block1, gem.getItem(), 1, 1))
                    .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
        }

        public SingleGemBuilder withOreAffix(String affix) {
            this.ore.withTranslation(affix + " " + name.substring(0, 1).toUpperCase() + name.substring(1) + " Ore");
            return this;
        }

        public SingleGemRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new SingleGemRegistryObject(
                    gem.build(itemRegistry),
                    dust.build(itemRegistry),
                    gear.build(itemRegistry),
                    block.build(blockRegistry),
                    ore.build(blockRegistry)
            );
        }
    }
}
