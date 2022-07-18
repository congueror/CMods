package net.congueror.clib.util.registry.data;

import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Map;

import static net.congueror.clib.util.CLTags.Items.*;

public class ItemTagsDataProvider extends ItemTagsProvider {

    String modid;

    public ItemTagsDataProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
        this.modid = modId;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        if (BlockBuilder.OBJECTS.get(this.modid) != null)
            BlockBuilder.OBJECTS.get(this.modid).forEach(block -> {
                for (Map.Entry<String, TagKey<Item>> tags : block.itemTags.entrySet()) {
                    tag(tags.getValue()).add(block.regObject.get().asItem());
                }
                for (Map.Entry<TagKey<Item>, TagKey<Item>> tags : block.itemTagsGen.entrySet()) {
                    tag(tags.getKey()).addTags(tags.getValue());
                }
            });
        if (ItemBuilder.OBJECTS.get(this.modid) != null)
            ItemBuilder.OBJECTS.get(this.modid).forEach(item -> {
                for (Map.Entry<String, TagKey<Item>> tags : item.itemTags.entrySet()) {
                    tag(tags.getValue()).add(item.regObject.get());
                }
                for (Map.Entry<TagKey<Item>, TagKey<Item>> tags : item.itemTagsGen.entrySet()) {
                    tag(tags.getKey()).addTags(tags.getValue());
                }
            });

        tag(STICKS).add(Items.STICK);
        tag(Tags.Items.DUSTS).addTags(DUSTS_BLAZE_POWDER);
        tag(DUSTS_BLAZE_POWDER).add(Items.BLAZE_POWDER);
        tag(SHARDS_AMETHYST).add(Items.AMETHYST_SHARD);
        tag(SHARDS).addTags(SHARDS_AMETHYST);
    }
}
