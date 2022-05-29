package net.congueror.clib.util.registry.data;

import net.congueror.clib.util.TagHelper;
import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Map;

public class ItemTagsDataProvider extends ItemTagsProvider {

    String modid;

    public ItemTagsDataProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
        this.modid = modId;
    }

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
        tag(ItemTags.create(new ResourceLocation("forge:sticks"))).add(Items.STICK);

        tag(TagHelper.STICKS).addTags(ItemTags.create(new ResourceLocation("forge:dusts/blaze_powder")));
        tag(TagHelper.DUSTS_BLAZE_POWDER).add(Items.BLAZE_POWDER);
        tag(TagHelper.SHARDS_AMETHYST).add(Items.AMETHYST_SHARD);
    }
}
