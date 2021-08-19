package com.congueror.clib.api.data;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ItemTagsDataGenerator extends ItemTagsProvider {
    protected ArrayList<TagWrapper> wrapper;

    public ItemTagsDataGenerator(DataGenerator dataGenerator, ArrayList<TagWrapper> wrapper, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
        this.wrapper = wrapper;
    }

    /**
     * Registers a parent tag and a child tag.
     * @param parent The parent tag (e.g. "ingots", "nuggets", e.t.c.)
     * @param sub The child tag (e.g. "ingots/your_ingot", e.t.c.)
     * @param item An array of {@link Item}s to be added to this tag.
     */
    protected void registerTag(ITag.INamedTag<Item> parent, ITag.INamedTag<Item> sub, Item[] item) {
        getOrCreateBuilder(parent).addTag(sub);
        getOrCreateBuilder(sub).add(item);
    }

    /**
     * Registers a series of tags based on the {@link TagWrapper} entries in the arraylist provided. To be called from {@link #registerTags()}.
     */
    protected void registerTagWrapper() {
        wrapper.forEach(tagWrapper -> {
            if (tagWrapper.getParent() == null) {
                if (tagWrapper.isItemTag()) {
                    getOrCreateBuilder(tagWrapper.getItemTag()).add(tagWrapper.getItems());
                } else {
                    for (Block block : tagWrapper.getBlocks()) {
                        getOrCreateBuilder(tagWrapper.getItemTag()).add(block.asItem());
                    }
                }
            }
            if (tagWrapper.getParent() != null) {
                registerTag(tagWrapper.getParent().getParentItemTag(), tagWrapper.getItemTag(), tagWrapper.getItems());
            }
        });
    }

    @Override
    protected void registerTags() {
        registerTagWrapper();
    }
}
