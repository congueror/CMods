package com.congueror.clib.data;

import com.congueror.clib.init.BlockInit;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTableDataGen extends LootTableProvider implements IDataProvider {
    public LootTableDataGen(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();

    void standardDropTable(Block b) {
        blockTable(b, LootTable.builder().addLootPool(createStandardDrops(b)));
    }

    void blockTable(Block b, LootTable.Builder lootTable) {
        addTable(b.getLootTable(), lootTable, LootParameterSets.BLOCK);
    }

    void addTable(ResourceLocation path, LootTable.Builder lootTable, LootParameterSet paramSet) {
        tables.add(Pair.of(() -> (lootBuilder) -> lootBuilder.accept(path, lootTable), paramSet));
    }

    LootPool.Builder createStandardDrops(IItemProvider itemProvider) {
        return LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(SurvivesExplosion.builder())
                .addEntry(ItemLootEntry.builder(itemProvider));
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        tables.clear();

        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(BlockInit.RUBY_ORE.get());
        blocks.add(BlockInit.AMETHYST_ORE.get());
        blocks.add(BlockInit.SAPPHIRE_ORE.get());
        blocks.add(BlockInit.OPAL_ORE.get());
        blocks.add(BlockInit.SALTPETRE_ORE.get());
        blocks.add(BlockInit.SULFUR_ORE.get());
        blocks.add(BlockInit.SALT_BLOCK.get());
        blocks.add(BlockInit.RUBBER_LEAVES.get());

        BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> !blocks.contains(block)).forEach(this::standardDropTable);

        return tables;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((loc, table) -> LootTableManager.validateLootTable(validationtracker, loc, table));
    }
}
