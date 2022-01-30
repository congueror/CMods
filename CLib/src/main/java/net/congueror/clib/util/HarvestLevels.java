package net.congueror.clib.util;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public enum HarvestLevels {

    WOOD(Tags.Blocks.NEEDS_WOOD_TOOL),
    GOLD(Tags.Blocks.NEEDS_GOLD_TOOL),
    STONE(BlockTags.NEEDS_STONE_TOOL),
    IRON(BlockTags.NEEDS_IRON_TOOL),
    DIAMOND(BlockTags.NEEDS_DIAMOND_TOOL),
    NETHERITE(Tags.Blocks.NEEDS_NETHERITE_TOOL)
    ;

    Tag.Named<Block> tag;

    HarvestLevels(Tag.Named<Block> tag) {
        this.tag = tag;
    }

    public Tag.Named<Block> getTag() {
        return tag;
    }
}
