package net.congueror.clib.api.data;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.congueror.clib.api.machine.fluid.AbstractFluidBlock;
import net.congueror.clib.api.registry.BlockBuilder;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class LootTableDataProvider extends LootTableProvider implements DataProvider {//net.minecraft.data.loot.BlockLoot
    public final String modid;

    public static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    public static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    public static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    public static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());

    protected final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = new ArrayList<>();

    public LootTableDataProvider(DataGenerator pGenerator, String modid) {
        super(pGenerator);
        this.modid = modid;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) {
        map.forEach((loc, table) -> LootTables.validate(validationtracker, loc, table));
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        tables.clear();
        BlockBuilder.OBJECTS.get(modid).forEach(builder -> {
            if (builder.lootTable != null) {
                builder.lootTable.accept(this, builder.block);
            }
        });
        return tables;
    }

    public void addTable(ResourceLocation path, LootTable.Builder lootTable, LootContextParamSet paramSet) {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), paramSet));
    }

    public static <T> T applyExplosionDecay(ItemLike pItem, FunctionUserBuilder<T> pFunction) {
        return !EXPLOSION_RESISTANT.contains(pItem.asItem()) ? pFunction.apply(ApplyExplosionDecay.explosionDecay()) : pFunction.unwrap();
    }

    protected static <T> T applyExplosionCondition(ItemLike pItem, ConditionUserBuilder<T> pCondition) {
        return !EXPLOSION_RESISTANT.contains(pItem.asItem()) ? pCondition.when(ExplosionCondition.survivesExplosion()) : pCondition.unwrap();
    }

    /**
     * Creates a loot table which will drop the given block.
     *
     * @param b The {@link Block}
     */
    public void createStandardBlockDrop(Block b) {
        addTable(b.getLootTable(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(ExplosionCondition.survivesExplosion())
                        .add(LootItem.lootTableItem(b))), LootContextParamSets.BLOCK);
    }

    public void createFluidMachineDrop(AbstractFluidBlock b) {//TODO
        addTable(b.getLootTable(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(ExplosionCondition.survivesExplosion())
                        .add(LootItem.lootTableItem(b))
        ), LootContextParamSets.BLOCK);
    }

    /**
     * Creates a loot table that drops a single Item/Block.
     *
     * @param block The {@link Block} which the loot table will be applied to
     * @param item  An {@link Item} or {@link Block} which results from this loot table
     */
    public void createSingleDrop(Block block, ItemLike item) {
        addTable(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(item))), LootContextParamSets.BLOCK);
    }

    /**
     * Creates a loot table that drops a single item/block, if silk touch is present then it will drop the block.
     *
     * @param block The {@link Block} which the loot table will be applied to
     * @param item  An {@link Item} or {@link Block} which results from this loot table
     */
    public void createSingleDropWithSilkTouch(Block block, ItemLike item) {
        addTable(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block).when(HAS_SILK_TOUCH)
                        .otherwise(applyExplosionDecay(item, LootItem.lootTableItem(item))))), LootContextParamSets.BLOCK);
    }

    /**
     * Creates a loot table that drops multiple Items/Blocks of the same type, taking into account the Silk Touch and Fortune Enchantments.
     *
     * @param block  The {@link Block} which the loot table will be applied to.
     * @param normal The {@link Item} or {@link Block} which results from this loot table.
     * @param max    The maximum amount of items that will drop without fortune.
     */
    public void createMultipleDrops(Block block, ItemLike normal, int min, int max) {
        addTable(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block).when(HAS_SILK_TOUCH)
                        .otherwise(applyExplosionDecay(normal, LootItem.lootTableItem(normal)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))))), LootContextParamSets.BLOCK);
    }

    /**
     * Creates a loot table similar to the vanilla leaves loot tables.
     *
     * @param block   The Leaves {@link Block} which the loot table will be applied to
     * @param sapling The sapling that will be dropped.
     */
    public void createLeavesDrops(Block block, Block sapling) {
        addTable(block.getLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block).when(HAS_SHEARS_OR_SILK_TOUCH)
                                .otherwise(applyExplosionCondition(block, LootItem.lootTableItem(sapling))
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.0625F, 0.083333336F, 0.1F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
                        .add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))), LootContextParamSets.BLOCK);
    }

    /**
     * Creates a loot table that only drops if the player has silk touch
     *
     * @param block The {@link Block} which the loot table will be applied to.
     * @param drop  The {@link Item} or {@link Block} which results from this loot table.
     */
    public void createSilkTouchOnlyDrop(Block block, ItemLike drop) {
        addTable(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool()
                .when(HAS_SILK_TOUCH).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(drop))), LootContextParamSets.BLOCK);
    }

    /**
     * Creates a loot table similar to that of an amethyst cluster.
     *
     * @param block The {@link Block} which the loot table will be applied to.
     * @param drop  The {@link Item} or {@link Block} which results from this loot table.
     */
    public void createClusterDrop(Block block, ItemLike drop) {
        addTable(block.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block).when(HAS_SILK_TOUCH).otherwise(LootItem.lootTableItem(drop)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES)))
                        .otherwise(applyExplosionDecay(block, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))))), LootContextParamSets.BLOCK);
    }

    /**
     * Creates a loot table which will drop the given drop unless the block's {@link BlockStateProperties#LIT} property is true in which case it will drop the 3rd param.
     * If the player has silk touch it will drop itself.
     *
     * @param block The {@link Block} which the loot table will be applied to.
     * @param drop  The {@link Item} or {@link Block} which results from this loot table.
     */
    public void createLitBlockStateDrop(Block block, ItemLike drop, ItemLike litDrop) {
        addTable(block.getLootTable(), applyExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(block).when(HAS_SILK_TOUCH)
                        .otherwise(LootItem.lootTableItem(litDrop)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(BlockStateProperties.LIT, true)))
                                .otherwise(LootItem.lootTableItem(drop)))))), LootContextParamSets.BLOCK);
    }
}
