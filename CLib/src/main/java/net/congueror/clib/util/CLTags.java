package net.congueror.clib.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CLTags {

    public static class Items {
        public static final TagKey<Item> HAMMERS = ItemTags.create(new ResourceLocation("clib:hammer"));

        public static final TagKey<Item> STICKS = ItemTags.create(new ResourceLocation("forge:sticks"));
        public static final TagKey<Item> SILICON = ItemTags.create(new ResourceLocation("forge:silicon"));
        public static final TagKey<Item> SALT = ItemTags.create(new ResourceLocation("forge:salt"));

        public static final TagKey<Item> GEMS_RUBY = ItemTags.create(new ResourceLocation("forge:gems/ruby"));

        public static final TagKey<Item> INGOTS_ALUMINUM = ItemTags.create(new ResourceLocation("forge:ingots/aluminum"));
        public static final TagKey<Item> INGOTS_BRASS = ItemTags.create(new ResourceLocation("forge:ingots/brass"));

        public static final TagKey<Item> NUGGETS_COAL = ItemTags.create(new ResourceLocation("forge:nuggets/coal"));

        public static final TagKey<Item> DUSTS_BLAZE_POWDER = ItemTags.create(new ResourceLocation("forge:dusts/blaze_powder"));
        public static final TagKey<Item> DUSTS_PYROTHEUM = ItemTags.create(new ResourceLocation("forge:dusts/pyrotheum"));
        public static final TagKey<Item> DUSTS_SALT = ItemTags.create(new ResourceLocation("forge:dusts/salt"));
        public static final TagKey<Item> DUSTS_SULFUR = ItemTags.create(new ResourceLocation("forge:dusts/sulfur"));
        public static final TagKey<Item> DUSTS_SALTPETRE = ItemTags.create(new ResourceLocation("forge:dusts/saltpetre"));

        public static final TagKey<Item> SHARDS = ItemTags.create(new ResourceLocation("forge:shards"));
        public static final TagKey<Item> SHARDS_AMETHYST = ItemTags.create(new ResourceLocation("forge:shards/amethyst"));
        public static final TagKey<Item> SHARDS_SAPPHIRE = ItemTags.create(new ResourceLocation("forge:shards/sapphire"));
    }
}
