package com.congueror.clib.data.gen;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTagsDataGen extends BlockTagsProvider {
    public BlockTagsDataGen(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, CLib.MODID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_TIN, BlockInit.TIN_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_TIN, BlockInit.TIN_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_STEEL, BlockInit.STEEL_BLOCK.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_ALUMINUM, BlockInit.ALUMINUM_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_ALUMINUM, BlockInit.ALUMINUM_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_LEAD, BlockInit.LEAD_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_LEAD, BlockInit.LEAD_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_COPPER, BlockInit.COPPER_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_COPPER, BlockInit.COPPER_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_RUBY, BlockInit.RUBY_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_RUBY, BlockInit.RUBY_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_SILVER, BlockInit.SILVER_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_SILVER, BlockInit.SILVER_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_NICKEL, BlockInit.NICKEL_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_NICKEL, BlockInit.NICKEL_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_LUMIUM, BlockInit.LUMIUM_BLOCK.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_INVAR, BlockInit.INVAR_BLOCK.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_ELECTRUM, BlockInit.ELECTRUM_BLOCK.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_PLATINUM, BlockInit.PLATINUM_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_PLATINUM, BlockInit.PLATINUM_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_ENDERIUM, BlockInit.ENDERIUM_BLOCK.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_SIGNALUM, BlockInit.SIGNALUM_BLOCK.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_TUNGSTEN, BlockInit.TUNGSTEN_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_TUNGSTEN, BlockInit.TUNGSTEN_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_BRONZE, BlockInit.BRONZE_BLOCK.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_AMETHYST, BlockInit.AMETHYST_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_AMETHYST, BlockInit.AMETHYST_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_SAPPHIRE, BlockInit.SAPPHIRE_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_SAPPHIRE, BlockInit.SAPPHIRE_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_OPAL, BlockInit.OPAL_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_OPAL, BlockInit.OPAL_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_TITANIUM, BlockInit.TITANIUM_BLOCK.get());

        registerOreTag(ModTags.Blocks.ORES_URANIUM, BlockInit.URANIUM_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_COBALT, BlockInit.COBALT_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_COBALT, BlockInit.COBALT_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_ZINC, BlockInit.ZINC_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_ZINC, BlockInit.ZINC_ORE.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_BRASS, BlockInit.BRASS_BLOCK.get());

        registerStorageBlockTag(ModTags.Blocks.STORAGE_BLOCKS_CHROMIUM, BlockInit.CHROMIUM_BLOCK.get());
        registerOreTag(ModTags.Blocks.ORES_CHROMIUM, BlockInit.CHROMIUM_ORE.get());

        registerOreTag(ModTags.Blocks.ORES_THORIUM, BlockInit.THORIUM_ORE.get());

        registerOreTag(ModTags.Blocks.ORES_SALTPETRE, BlockInit.SALTPETRE_ORE.get());
        registerOreTag(ModTags.Blocks.ORES_SULFUR, BlockInit.SULFUR_ORE.get());

        getOrCreateBuilder(ModTags.Blocks.RUBBER_LOGS).add(BlockInit.RUBBER_LOG.get());
        getOrCreateBuilder(ModTags.Blocks.RUBBER_LOGS).add(BlockInit.RUBBER_STRIPPED_LOG.get());
        getOrCreateBuilder(ModTags.Blocks.RUBBER_LOGS).add(BlockInit.RUBBER_WOOD.get());
        getOrCreateBuilder(ModTags.Blocks.RUBBER_LOGS).add(BlockInit.RUBBER_STRIPPED_WOOD.get());
    }

    protected void registerTag(Tags.IOptionalNamedTag<Block> parent, Tags.IOptionalNamedTag<Block> sub, Block block) {
        getOrCreateBuilder(parent).addTag(sub);
        getOrCreateBuilder(sub).add(block);
    }

    protected void registerOreTag(Tags.IOptionalNamedTag<Block> sub, Block block) {
        registerTag(ModTags.Blocks.ORES, sub, block);
    }

    protected void registerStorageBlockTag(Tags.IOptionalNamedTag<Block> sub, Block block) {
        registerTag(ModTags.Blocks.STORAGE_BLOCKS, sub, block);
    }
}
