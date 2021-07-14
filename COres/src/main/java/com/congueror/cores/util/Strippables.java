package com.congueror.cores.util;

import com.congueror.cores.COres;
import com.congueror.cores.init.BlockInit;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

public class Strippables {
    public static void strippableBlock(Block log, Block strippable) {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        AxeItem.BLOCK_STRIPPING_MAP.put(log, strippable);
    }

    public static void strippableLogs() {
        COres.LOGGER.debug("COres: Adding strippable Blocks...");

        strippableBlock(BlockInit.RUBBER_LOG.get(), BlockInit.RUBBER_STRIPPED_LOG.get());
        strippableBlock(BlockInit.RUBBER_WOOD.get(), BlockInit.RUBBER_STRIPPED_WOOD.get());

        COres.LOGGER.debug("COres: Added strippable Blocks...");
    }
}
