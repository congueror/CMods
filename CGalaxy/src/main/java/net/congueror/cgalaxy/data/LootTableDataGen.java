package net.congueror.cgalaxy.data;

import com.mojang.datafixers.util.Pair;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGItemInit;
import net.congueror.clib.util.registry.data.LootTableDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTableDataGen extends LootTableDataProvider {
    public LootTableDataGen(DataGenerator pGenerator, String modid) {
        super(pGenerator, modid);
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        super.getTables();
        createChestLootTable(CGalaxy.location("chests/lunar_cartography"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(6))
                        .add(EmptyLootItem.emptyItem().setWeight(5))
                        .add(LootItem.lootTableItem(CGItemInit.ASTRAL_SAPPHIRE.get()).setWeight(10))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(20).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                ));
        createChestLootTable(CGalaxy.location("chests/lunar_farm"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(6))
                        .add(EmptyLootItem.emptyItem().setWeight(5))
                        .add(LootItem.lootTableItem(CGItemInit.ASTRAL_SAPPHIRE.get()).setWeight(6))
                        .add(LootItem.lootTableItem(CGBlockInit.LUNAR_CARROTS.get()).setWeight(10))
                        .add(LootItem.lootTableItem(CGBlockInit.MOON_REGOLITH.get()).setWeight(20))
                ));
        return tables;
    }
}
