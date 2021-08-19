package com.congueror.clib.api.data;

import com.congueror.clib.util.CLTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Arrays;

public class TagWrapper {

    final Tags.IOptionalNamedTag<Item> itemTag;
    @Nullable final Tags.IOptionalNamedTag<Block> blockTag;
    final ResourceLocation rl;
    final Item[] items;
    @Nullable final Block[] blocks;
    @Nullable final Type parent;

    public TagWrapper(ResourceLocation rl, Type parent, Block... blocks) {
        this.itemTag = null;
        this.blockTag = BlockTags.createOptional(rl);
        this.rl = rl;
        this.items = null;
        this.blocks = blocks;
        this.parent = parent;
    }

    public TagWrapper(ResourceLocation rl, Type parent, Item... items) {
        this.itemTag = ItemTags.createOptional(rl);
        this.blockTag = null;
        this.rl = rl;
        this.items = items;
        this.blocks = null;
        this.parent = parent;
    }

    public TagWrapper(ResourceLocation rl, Type parent, Item[] items, Block[] blocks) {
        this.itemTag = ItemTags.createOptional(rl);
        this.blockTag = BlockTags.createOptional(rl);
        this.rl = rl;
        this.items = items;
        this.blocks = blocks;
        this.parent = parent;
    }

    public boolean isItemTag() {
        return blockTag == null;
    }

    public Tags.IOptionalNamedTag<Item> getItemTag() {
        return itemTag;
    }

    public Tags.IOptionalNamedTag<Block> getBlockTag() {
        return blockTag;
    }

    public ResourceLocation getRl() {
        return rl;
    }

    public Item[] getItems() {
        return items;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public Type getParent() {
        return parent;
    }

    public enum Type {
        INGOTS(Tags.Items.INGOTS),
        NUGGETS(Tags.Items.NUGGETS),
        DUSTS(Tags.Items.DUSTS),
        GEARS(ItemTags.createOptional(new ResourceLocation("forge", "gears"))),
        GEMS(Tags.Items.GEMS),
        STORAGE_BLOCKS(Tags.Items.STORAGE_BLOCKS, Tags.Blocks.STORAGE_BLOCKS),
        ORES(Tags.Items.ORES, Tags.Blocks.ORES),
        STONE(Tags.Items.STONE, Tags.Blocks.STONE),
        COBBLESTONE(Tags.Items.COBBLESTONE, Tags.Blocks.COBBLESTONE);


        ITag.INamedTag<Item> itemTag;
        ITag.INamedTag<Block> blockTag;

        Type(ITag.INamedTag<Item> itemTag) {
            this.itemTag = itemTag;
            this.blockTag = null;
        }

        Type(ITag.INamedTag<Item> itemTag, @Nullable ITag.INamedTag<Block> blockTag) {
            this.itemTag = itemTag;
            this.blockTag = blockTag;
        }

        public boolean isItemTag() {
            return blockTag == null;
        }

        public ITag.INamedTag<Item> getParentItemTag() {
            return itemTag;
        }

        public ITag.INamedTag<Block> getParentBlockTag() {
            return blockTag;
        }
    }
}
