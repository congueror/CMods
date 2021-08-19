package com.congueror.clib.api.data;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class BlockTagsDataGenerator extends BlockTagsProvider {
    ArrayList<TagWrapper> wrapper;

    public BlockTagsDataGenerator(DataGenerator generatorIn, ArrayList<TagWrapper> wrapper, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
        this.wrapper = wrapper;
    }

    /**
     * Registers a parent tag and a child tag.
     * @param parent The parent tag (e.g. "ores", "storage_blocks", e.t.c.)
     * @param sub The child tag (e.g. "ores/your_ore", e.t.c.)
     * @param block An array of {@link Block}s to be added to this tag.
     */
    protected void registerTag(ITag.INamedTag<Block> parent, ITag.INamedTag<Block> sub, Block[] block) {
        getOrCreateBuilder(parent).addTag(sub);
        getOrCreateBuilder(sub).add(block);
    }

    /**
     * Registers a series of tags based on the {@link TagWrapper} entries in the arraylist provided. To be called from {@link #registerTags()}.
     */
    protected void registerTagWrapper() {
        wrapper.forEach(tagWrapper -> {
            if (!tagWrapper.isItemTag()) {
                if (tagWrapper.getParent() == null) {
                    getOrCreateBuilder(tagWrapper.getBlockTag()).add(tagWrapper.getBlocks());
                }
                if (tagWrapper.getParent() != null) {
                    registerTag(tagWrapper.getParent().getParentBlockTag(), tagWrapper.getBlockTag(), tagWrapper.getBlocks());
                }
            }
        });
    }

    @Override
    protected void registerTags() {
        registerTagWrapper();
    }
}
