package net.congueror.clib.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;

import java.util.stream.Stream;

public final class TagHelper {



    private TagHelper() {}

    public static Stream<TagKey<Item>> getItemTags(Item item) {
        return ForgeRegistries.ITEMS.tags().getReverseTag(item).map(IReverseTag::getTagKeys).orElseGet(Stream::of);
        //return Registry.ITEM.getHolderOrThrow(ResourceKey.create(Registry.ITEM.key(), item.getRegistryName())).tags();
    }

    public static Stream<TagKey<Block>> getBlockTags(Block block) {
        return ForgeRegistries.BLOCKS.tags().getReverseTag(block).map(IReverseTag::getTagKeys).orElseGet(Stream::of);
        //return Registry.BLOCK.getHolderOrThrow(ResourceKey.create(Registry.BLOCK.key(), block.getRegistryName())).tags();
    }

    public static Stream<Fluid> getFluidTagValues(TagKey<Fluid> key) {
        return ForgeRegistries.FLUIDS.tags().getTag(key).stream();
        //return StreamSupport.stream(Registry.FLUID.getTagOrEmpty(key).spliterator(), false).map(Holder::value);
    }

    public static boolean tagContains(TagKey<Block> tag, Block block) {
        return ForgeRegistries.BLOCKS.tags().getTag(tag).contains(block);
        //return Registry.BLOCK.getHolderOrThrow(ResourceKey.create(Registry.BLOCK.key(), block.getRegistryName())).is(tag);
    }
}
