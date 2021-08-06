package com.congueror.clib.data.gen;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.ItemInit;
import com.congueror.clib.init.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ItemTagsDataGen extends ItemTagsProvider {

    public ItemTagsDataGen(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, CLib.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        registerIngotTag(ModTags.Items.INGOTS_TIN, ItemInit.TIN_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_TIN, ItemInit.TIN_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_TIN, ItemInit.TIN_DUST.get());
        registerGearTag(ModTags.Items.GEARS_TIN, ItemInit.TIN_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_TIN, BlockInit.TIN_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_TIN, BlockInit.TIN_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_STEEL, ItemInit.STEEL_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_STEEL, ItemInit.STEEL_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_STEEL, ItemInit.STEEL_DUST.get());
        registerGearTag(ModTags.Items.GEARS_STEEL, ItemInit.STEEL_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_STEEL, ItemInit.STEEL_BLEND.get());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_STEEL, BlockInit.STEEL_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_ALUMINUM, ItemInit.ALUMINUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_ALUMINUM, ItemInit.ALUMINUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_ALUMINUM, ItemInit.ALUMINUM_DUST.get());
        registerGearTag(ModTags.Items.GEARS_ALUMINUM, ItemInit.ALUMINUM_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_ALUMINUM, BlockInit.ALUMINUM_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_ALUMINUM, BlockInit.ALUMINUM_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_LEAD, ItemInit.LEAD_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_LEAD, ItemInit.LEAD_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_LEAD, ItemInit.LEAD_DUST.get());
        registerGearTag(ModTags.Items.GEARS_LEAD, ItemInit.LEAD_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_LEAD, BlockInit.LEAD_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_LEAD, BlockInit.LEAD_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_COPPER, ItemInit.COPPER_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_COPPER, ItemInit.COPPER_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_COPPER, ItemInit.COPPER_DUST.get());
        registerGearTag(ModTags.Items.GEARS_COPPER, ItemInit.COPPER_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_COPPER, BlockInit.COPPER_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_COPPER, BlockInit.COPPER_BLOCK.get().asItem());

        registerGemTag(ModTags.Items.GEMS_RUBY, ItemInit.RUBY.get());
        registerDustTag(ModTags.Items.DUSTS_RUBY, ItemInit.RUBY_DUST.get());
        registerGearTag(ModTags.Items.GEARS_RUBY, ItemInit.RUBY_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_RUBY, BlockInit.RUBY_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_RUBY, BlockInit.RUBY_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_SILVER, ItemInit.SILVER_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_SILVER, ItemInit.SILVER_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_SILVER, ItemInit.SILVER_DUST.get());
        registerGearTag(ModTags.Items.GEARS_SILVER, ItemInit.SILVER_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_SILVER, BlockInit.SILVER_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_SILVER, BlockInit.SILVER_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_LUMIUM, ItemInit.LUMIUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_LUMIUM, ItemInit.LUMIUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_LUMIUM, ItemInit.LUMIUM_DUST.get());
        registerGearTag(ModTags.Items.GEARS_LUMIUM, ItemInit.LUMIUM_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_LUMIUM, ItemInit.LUMIUM_BLEND.get());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_LUMIUM, BlockInit.LUMIUM_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_NICKEL, ItemInit.NICKEL_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_NICKEL, ItemInit.NICKEL_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_NICKEL, ItemInit.NICKEL_DUST.get());
        registerGearTag(ModTags.Items.GEARS_NICKEL, ItemInit.NICKEL_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_NICKEL, BlockInit.NICKEL_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_NICKEL, BlockInit.NICKEL_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_INVAR, ItemInit.INVAR_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_INVAR, ItemInit.INVAR_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_INVAR, ItemInit.INVAR_DUST.get());
        registerGearTag(ModTags.Items.GEARS_INVAR, ItemInit.INVAR_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_INVAR, ItemInit.INVAR_BLEND.get());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_INVAR, BlockInit.INVAR_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_ELECTRUM, ItemInit.ELECTRUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_ELECTRUM, ItemInit.ELECTRUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_ELECTRUM, ItemInit.ELECTRUM_DUST.get());
        registerGearTag(ModTags.Items.GEARS_ELECTRUM, ItemInit.ELECTRUM_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_ELECTRUM, ItemInit.ELECTRUM_BLEND.get());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_ELECTRUM, BlockInit.ELECTRUM_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_PLATINUM, ItemInit.PLATINUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_PLATINUM, ItemInit.PLATINUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_PLATINUM, ItemInit.PLATINUM_DUST.get());
        registerGearTag(ModTags.Items.GEARS_PLATINUM, ItemInit.PLATINUM_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_PLATINUM, BlockInit.PLATINUM_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_PLATINUM, BlockInit.PLATINUM_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_ENDERIUM, ItemInit.ENDERIUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_ENDERIUM, ItemInit.ENDERIUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_ENDERIUM, ItemInit.ENDERIUM_DUST.get());
        registerGearTag(ModTags.Items.GEARS_ENDERIUM, ItemInit.ENDERIUM_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_ENDERIUM, ItemInit.ENDERIUM_BLEND.get());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_ENDERIUM, BlockInit.ENDERIUM_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_SIGNALUM, ItemInit.SIGNALUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_SIGNALUM, ItemInit.SIGNALUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_SIGNALUM, ItemInit.SIGNALUM_DUST.get());
        registerGearTag(ModTags.Items.GEARS_SIGNALUM, ItemInit.SIGNALUM_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_SIGNALUM, ItemInit.SIGNALUM_BLEND.get());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_SIGNALUM, BlockInit.SIGNALUM_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_TUNGSTEN, ItemInit.TUNGSTEN_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_TUNGSTEN, ItemInit.TUNGSTEN_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_TUNGSTEN, ItemInit.TUNGSTEN_DUST.get());
        registerGearTag(ModTags.Items.GEARS_TUNGSTEN, ItemInit.TUNGSTEN_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_TUNGSTEN, BlockInit.TUNGSTEN_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_TUNGSTEN, BlockInit.TUNGSTEN_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_BRONZE, ItemInit.BRONZE_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_BRONZE, ItemInit.BRONZE_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_BRONZE, ItemInit.BRONZE_DUST.get());
        registerGearTag(ModTags.Items.GEARS_BRONZE, ItemInit.BRONZE_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_BRONZE, ItemInit.BRONZE_BLEND.get());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_BRONZE, BlockInit.BRONZE_BLOCK.get().asItem());

        registerGemTag(ModTags.Items.GEMS_AMETHYST, ItemInit.AMETHYST.get());
        registerDustTag(ModTags.Items.DUSTS_AMETHYST, ItemInit.AMETHYST_DUST.get());
        registerGearTag(ModTags.Items.GEARS_AMETHYST, ItemInit.AMETHYST_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_AMETHYST, BlockInit.AMETHYST_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_AMETHYST, BlockInit.AMETHYST_BLOCK.get().asItem());

        registerGemTag(ModTags.Items.GEMS_SAPPHIRE, ItemInit.SAPPHIRE.get());
        registerDustTag(ModTags.Items.DUSTS_SAPPHIRE, ItemInit.SAPPHIRE_DUST.get());
        registerGearTag(ModTags.Items.GEARS_SAPPHIRE, ItemInit.SAPPHIRE_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_SAPPHIRE, BlockInit.SAPPHIRE_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_SAPPHIRE, BlockInit.SAPPHIRE_BLOCK.get().asItem());

        registerGemTag(ModTags.Items.GEMS_OPAL, ItemInit.OPAL.get());
        registerDustTag(ModTags.Items.DUSTS_OPAL, ItemInit.OPAL_DUST.get());
        registerGearTag(ModTags.Items.GEARS_OPAL, ItemInit.OPAL_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_OPAL, BlockInit.OPAL_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_OPAL, BlockInit.OPAL_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_TITANIUM, ItemInit.TITANIUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_TITANIUM, ItemInit.TITANIUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_TITANIUM, ItemInit.TITANIUM_DUST.get());
        registerGearTag(ModTags.Items.GEARS_TITANIUM, ItemInit.TITANIUM_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_TITANIUM, ItemInit.TITANIUM_SCRAP.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_TITANIUM, BlockInit.TITANIUM_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_TITANIUM, BlockInit.TITANIUM_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_URANIUM, ItemInit.URANIUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_URANIUM, ItemInit.URANIUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_URANIUM, ItemInit.URANIUM_DUST.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_URANIUM, BlockInit.URANIUM_ORE.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_COBALT, ItemInit.COBALT_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_COBALT, ItemInit.COBALT_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_COBALT, ItemInit.COBALT_DUST.get());
        registerGearTag(ModTags.Items.GEARS_COBALT, ItemInit.COBALT_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_COBALT, BlockInit.COBALT_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_COBALT, BlockInit.COBALT_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_ZINC, ItemInit.ZINC_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_ZINC, ItemInit.ZINC_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_ZINC, ItemInit.ZINC_DUST.get());
        registerGearTag(ModTags.Items.GEARS_ZINC, ItemInit.ZINC_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_ZINC, BlockInit.ZINC_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_ZINC, BlockInit.ZINC_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_BRASS, ItemInit.BRASS_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_BRASS, ItemInit.BRASS_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_BRASS, ItemInit.BRASS_DUST.get());
        registerGearTag(ModTags.Items.GEARS_BRASS, ItemInit.BRASS_GEAR.get());
        registerBlendTag(ModTags.Items.ORES_BRASS, ItemInit.BRASS_BLEND.get());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_BRASS, BlockInit.BRASS_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_CHROMIUM, ItemInit.CHROMIUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_CHROMIUM, ItemInit.CHROMIUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_CHROMIUM, ItemInit.CHROMIUM_DUST.get());
        registerGearTag(ModTags.Items.GEARS_CHROMIUM, ItemInit.CHROMIUM_GEAR.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_CHROMIUM, BlockInit.CHROMIUM_ORE.get().asItem());
        registerTag(ModTags.Items.STORAGE_BLOCKS, ModTags.Items.STORAGE_BLOCKS_CHROMIUM, BlockInit.CHROMIUM_BLOCK.get().asItem());

        registerIngotTag(ModTags.Items.INGOTS_THORIUM, ItemInit.THORIUM_INGOT.get());
        registerNuggetTag(ModTags.Items.NUGGETS_THORIUM, ItemInit.THORIUM_NUGGET.get());
        registerDustTag(ModTags.Items.DUSTS_THORIUM, ItemInit.THORIUM_DUST.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_THORIUM, BlockInit.THORIUM_ORE.get().asItem());

        registerDustTag(ModTags.Items.DUSTS_SALTPETRE, ItemInit.SALTPETRE_DUST.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_SALTPETRE, BlockInit.SALTPETRE_ORE.get().asItem());
        registerDustTag(ModTags.Items.DUSTS_SULFUR, ItemInit.SULFUR_DUST.get());
        registerTag(ModTags.Items.ORES, ModTags.Items.ORES_SULFUR, BlockInit.SULFUR_ORE.get().asItem());
        registerDustTag(ModTags.Items.DUSTS_SALT, ItemInit.SALT.get());
        registerDustTag(ModTags.Items.DUSTS_PYROTHEUM, ItemInit.PYROTHEUM_DUST.get());

        registerDustTag(ModTags.Items.DUSTS_BLAZE_POWDER, Items.BLAZE_POWDER);
        registerDustTag(ModTags.Items.DUSTS_WOOD, ItemInit.WOOD_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_COAL, ItemInit.COAL_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_IRON, ItemInit.IRON_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_GOLD, ItemInit.GOLD_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_LAPIS, ItemInit.LAPIS_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_QUARTZ, ItemInit.QUARTZ_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_DIAMOND, ItemInit.DIAMOND_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_EMERALD, ItemInit.EMERALD_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_NETHERITE, ItemInit.NETHERITE_DUST.get());
        registerDustTag(ModTags.Items.DUSTS_OBSIDIAN, ItemInit.OBSIDIAN_DUST.get());

        registerGearTag(ModTags.Items.GEARS_WOOD, ItemInit.WOOD_GEAR.get());
        registerGearTag(ModTags.Items.GEARS_STONE, ItemInit.STONE_GEAR.get());
        registerGearTag(ModTags.Items.GEARS_IRON, ItemInit.IRON_GEAR.get());
        registerGearTag(ModTags.Items.GEARS_GOLD, ItemInit.GOLD_GEAR.get());
        registerGearTag(ModTags.Items.GEARS_LAPIS, ItemInit.LAPIS_GEAR.get());
        registerGearTag(ModTags.Items.GEARS_QUARTZ, ItemInit.QUARTZ_GEAR.get());
        registerGearTag(ModTags.Items.GEARS_DIAMOND, ItemInit.DIAMOND_GEAR.get());
        registerGearTag(ModTags.Items.GEARS_EMERALD, ItemInit.EMERALD_GEAR.get());
        registerGearTag(ModTags.Items.GEARS_NETHERITE, ItemInit.NETHERITE_GEAR.get());

        getOrCreateBuilder(ModTags.Items.RUBBER).add(ItemInit.RUBBER.get());
        getOrCreateBuilder(ModTags.Items.RUBBER_LOGS).add(BlockInit.RUBBER_LOG.get().asItem(), BlockInit.RUBBER_STRIPPED_LOG.get().asItem(), BlockInit.RUBBER_WOOD.get().asItem(), BlockInit.RUBBER_STRIPPED_WOOD.get().asItem());
    }

    protected void registerTag(Tags.IOptionalNamedTag<Item> parent, Tags.IOptionalNamedTag<Item> sub, Item item) {
        getOrCreateBuilder(parent).addTag(sub);
        getOrCreateBuilder(sub).add(item);
    }

    protected void registerIngotTag(Tags.IOptionalNamedTag<Item> sub, Item item) {
        registerTag(ModTags.Items.INGOTS, sub, item);
    }

    protected void registerNuggetTag(Tags.IOptionalNamedTag<Item> sub, Item item) {
        registerTag(ModTags.Items.NUGGETS, sub, item);
    }

    protected void registerDustTag(Tags.IOptionalNamedTag<Item> sub, Item item) {
        registerTag(ModTags.Items.DUSTS, sub, item);
    }

    protected void registerGearTag(Tags.IOptionalNamedTag<Item> sub, Item item) {
        registerTag(ModTags.Items.GEARS, sub, item);
    }

    protected void registerBlendTag(Tags.IOptionalNamedTag<Item> sub, Item item) {
        registerTag(ModTags.Items.ORES, sub, item);
    }

    protected void registerGemTag(Tags.IOptionalNamedTag<Item> sub, Item item) {
        registerTag(ModTags.Items.GEMS, sub, item);
    }
}
