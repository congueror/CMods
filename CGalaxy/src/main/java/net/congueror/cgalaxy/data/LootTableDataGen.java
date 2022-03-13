package net.congueror.cgalaxy.data;

import com.mojang.datafixers.util.Pair;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.init.CGItemInit;
import net.congueror.clib.util.registry.data.LootTableDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTableDataGen extends LootTableDataProvider {
    public LootTableDataGen(DataGenerator pGenerator, String modid) {
        super(pGenerator, modid);
    }

    @NotNull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        super.getTables();
        createChestLootTable(CGalaxy.location("chests/lunar_cartography"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(CGItemInit.ASTRAL_SAPPHIRE.get()).setWeight(10)))
        );
        createChestLootTable(CGalaxy.location("chests/lunar_farm"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(CGItemInit.ASTRAL_SAPPHIRE.get()).setWeight(10)))
        );
        return tables;
    }
}
