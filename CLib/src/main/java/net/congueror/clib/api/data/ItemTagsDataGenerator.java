package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Map;

public class ItemTagsDataGenerator extends ItemTagsProvider {

    public ItemTagsDataGenerator(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        BlockBuilder.OBJECTS.forEach(block -> {
            for (Map.Entry<String, Tag.Named<Item>> tags : block.itemTags.entrySet()) {
                tag(tags.getValue()).add(block.getBlock().asItem());
            }
            for (Map.Entry<Tag.Named<Item>, Tag.Named<Item>> tags : block.itemTagsGen.entrySet()) {
                tag(tags.getKey()).addTag(tags.getValue());
            }
        });
        ItemBuilder.OBJECTS.forEach(item -> {
            for (Map.Entry<String, Tag.Named<Item>> tags : item.itemTags.entrySet()) {
                tag(tags.getValue()).add(item.getItem());
            }
            for (Map.Entry<Tag.Named<Item>, Tag.Named<Item>> tags : item.itemTagsGen.entrySet()) {
                tag(tags.getKey()).addTag(tags.getValue());
            }
        });
        tag(ItemTags.createOptional(new ResourceLocation("forge:sticks"))).add(Items.STICK);

        tag(ItemTags.createOptional(new ResourceLocation("forge:dusts"))).addTag(ItemTags.createOptional(new ResourceLocation("forge:dusts/blaze_powder")));
        tag(ItemTags.createOptional(new ResourceLocation("forge:dusts/blaze_powder"))).add(Items.BLAZE_POWDER);

        tag(ItemTags.createOptional(new ResourceLocation("forge:ingots/copper"))).add(Items.COPPER_INGOT);//TODO Temp, until forge adds it's own tags.
        tag(ItemTags.createOptional(new ResourceLocation("forge:gems/amethyst"))).add(Items.AMETHYST_SHARD);
        tag(ItemTags.createOptional(new ResourceLocation("forge:shards/amethyst"))).add(Items.AMETHYST_SHARD);
    }
}
