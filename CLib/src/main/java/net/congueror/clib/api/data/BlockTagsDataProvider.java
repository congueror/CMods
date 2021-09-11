package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.BlockBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.Tag;
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
            for (Map.Entry<String, Tag.Named<Block>> tags : block.blockTags.entrySet()) {
                tag(tags.getValue()).add(block.block);
            }
            for (Map.Entry<Tag.Named<Block>, Tag.Named<Block>> tags : block.blockTagsGen.entrySet()) {
                tag(tags.getKey()).addTag(tags.getValue());
            }
        });
    }
}
