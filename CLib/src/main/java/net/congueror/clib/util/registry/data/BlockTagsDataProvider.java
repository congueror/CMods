package net.congueror.clib.util.registry.data;

import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Map;

public class BlockTagsDataProvider extends BlockTagsProvider {

    public BlockTagsDataProvider(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        BlockBuilder.OBJECTS.get(modId).forEach(block -> {
            for (Map.Entry<String, TagKey<Block>> tags : block.blockTags.entrySet()) {
                tag(tags.getValue()).add(block.regObject.get());
            }
            for (Map.Entry<TagKey<Block>, TagKey<Block>> tags : block.blockTagsGen.entrySet()) {
                tag(tags.getKey()).addTag(tags.getValue());
            }
        });
    }
}