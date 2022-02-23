package net.congueror.clib.util.registry;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.blocks.generic.CLBuddingBlock;
import net.congueror.clib.util.CreativeTabs;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GeodeGemRegistryObject {

    private final RegistryObject<Item> shard;
    private final RegistryObject<Item> dust;

    private final RegistryObject<Block> block;
    private final RegistryObject<Block> small_bud;
    private final RegistryObject<Block> medium_bud;
    private final RegistryObject<Block> large_bud;
    private final RegistryObject<Block> cluster;
    private final RegistryObject<Block> budding;

    private GeodeGemRegistryObject(RegistryObject<Item> shard,
                                  RegistryObject<Item> dust,
                                  RegistryObject<Block> block,
                                  RegistryObject<Block> small_bud,
                                  RegistryObject<Block> medium_bud,
                                  RegistryObject<Block> large_bud,
                                  RegistryObject<Block> cluster,
                                  RegistryObject<Block> budding) {
        this.shard = shard;
        this.dust = dust;
        this.block = block;
        this.small_bud = small_bud;
        this.medium_bud = medium_bud;
        this.large_bud = large_bud;
        this.cluster = cluster;
        this.budding = budding;
    }

    public RegistryObject<Item> getShard() {
        return shard;
    }

    public RegistryObject<Item> getDust() {
        return dust;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    public RegistryObject<Block> getSmallBud() {
        return small_bud;
    }

    public RegistryObject<Block> getMediumBud() {
        return medium_bud;
    }

    public RegistryObject<Block> getLargeBud() {
        return large_bud;
    }

    public RegistryObject<Block> getCluster() {
        return cluster;
    }

    public RegistryObject<Block> getBudding() {
        return budding;
    }

    public static class GeodeGemBuilder extends ResourceBuilder<GeodeGemBuilder> {

        ItemBuilder shard;

        BlockBuilder block;
        BlockBuilder small_bud;
        BlockBuilder medium_bud;
        BlockBuilder large_bud;
        BlockBuilder cluster;
        BlockBuilder budding;

        public GeodeGemBuilder(String name, CreativeModeTab tab) {
            super(name, tab);
            shard = new ItemBuilder(name + "_shard", new Item(new Item.Properties().tab(tab)))
                    .withTranslation(capitalized + " Shard")
                    .withNewItemTag("forge:shards/" + name)
                    .withNewItemTag("forge:gems/" + name)
                    .withItemModel((itemModelDataProvider, item) -> itemModelDataProvider.modTexture(item, "resource/" + name + "/" + item));
            addDust();

            block = new BlockBuilder(name + "_block", new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)))
                    .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.CRYSTAL_SOUND_BLOCKS)
                    .withNewBlockTag("forge:storage_blocks/" + name)
                    .withNewItemTag("forge:storage_blocks/" + name)
                    .withTranslation("Block of " + capitalized)
                    .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));
            small_bud = new BlockBuilder("small_" + name + "_bud", new AmethystClusterBlock(3, 4, BlockBehaviour.Properties.copy(Blocks.SMALL_AMETHYST_BUD)))
                    .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
                    .withBlockModel((blockModelDataGenerator, block1) -> blockModelDataGenerator.directionalBlock(block1, blockModelDataGenerator.models()
                            .cross(block1.getRegistryName().getPath(),
                                    new ResourceLocation(block1.getRegistryName().getNamespace(), "resource/" + name + "/" + block1.getRegistryName().getPath()))))
                    .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "resource/" + name + "/" + block.getRegistryName().getPath()))
                    .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
                    .withTranslation("Small " + capitalized + " Bud")
                    .withRenderType(RenderType.cutout());
            medium_bud = new BlockBuilder("medium_" + name + "_bud", new AmethystClusterBlock(4, 3, BlockBehaviour.Properties.copy(Blocks.MEDIUM_AMETHYST_BUD)))
                    .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
                    .withBlockModel((blockModelDataGenerator, block1) -> blockModelDataGenerator.directionalBlock(block1, blockModelDataGenerator.models()
                            .cross(block1.getRegistryName().getPath(),
                                    new ResourceLocation(block1.getRegistryName().getNamespace(), "resource/" + name + "/" + block1.getRegistryName().getPath()))))
                    .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "resource/" + name + "/" + block.getRegistryName().getPath()))
                    .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
                    .withTranslation("Medium " + capitalized + " Bud")
                    .withRenderType(RenderType.cutout());
            large_bud = new BlockBuilder("large_" + name + "_bud", new AmethystClusterBlock(5, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD)))
                    .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
                    .withBlockModel((blockModelDataGenerator, block1) -> blockModelDataGenerator.directionalBlock(block1, blockModelDataGenerator.models()
                            .cross(block1.getRegistryName().getPath(),
                                    new ResourceLocation(block1.getRegistryName().getNamespace(), "resource/" + name + "/" + block1.getRegistryName().getPath()))))
                    .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "resource/" + name + "/" + block.getRegistryName().getPath()))
                    .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createSilkTouchOnlyDrop(block, block))
                    .withTranslation("Large " + capitalized + " Bud")
                    .withRenderType(RenderType.cutout());
            cluster = new BlockBuilder(name + "_cluster", new AmethystClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)))
                    .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE)
                    .withBlockModel((blockModelDataGenerator, block1) -> blockModelDataGenerator.directionalBlock(block1, blockModelDataGenerator.models()
                            .cross(block1.getRegistryName().getPath(),
                                    new ResourceLocation(block1.getRegistryName().getNamespace(), "resource/" + name + "/" + block1.getRegistryName().getPath()))))
                    .withItemModel((itemModelDataGenerator, block) -> itemModelDataGenerator.modTexture(block.asItem(), "resource/" + name + "/" + block.getRegistryName().getPath()))
                    .withLootTable((lootTableDataGenerator, block) -> lootTableDataGenerator.createClusterDrop(block, shard.getItem()))
                    .withTranslation(capitalized + " Cluster")
                    .withRenderType(RenderType.cutout());
            budding = new BlockBuilder("budding_" + name, new CLBuddingBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST),
                    () -> small_bud.block, () -> medium_bud.block, () -> large_bud.block, () -> cluster.block))
                    .withCreativeTab(CreativeTabs.ResourcesIG.instance)
                    .withExistingBlockTags(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.CRYSTAL_SOUND_BLOCKS)
                    .withLootTable(null)
                    .withTranslation(capitalized + " Budding")
                    .withBlockModel((blockModelDataProvider, block1) -> blockModelDataProvider.cubeAllBlock(block1, "clib:resource/" + name + "/" + block1.getRegistryName().getPath()));;
        }

        public GeodeGemRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new GeodeGemRegistryObject(
                    shard.build(itemRegistry),
                    dust.build(itemRegistry),
                    block.build(blockRegistry),
                    small_bud.build(blockRegistry),
                    medium_bud.build(blockRegistry),
                    large_bud.build(blockRegistry),
                    cluster.build(blockRegistry),
                    budding.build(blockRegistry)
            );
        }
    }
}
