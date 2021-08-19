package com.congueror.cgalaxy.util;

import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.clib.api.data.TagWrapper;
import com.congueror.clib.util.CLTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

public class CGTags {

    public static ArrayList<TagWrapper> tags = new ArrayList<>();

    public static TagWrapper getOrCreateTag(String name, TagWrapper.Type parent, Item[] items, @Nullable Block[] blocks) {
        TagWrapper newTag;
        if (blocks == null) {
            newTag = new TagWrapper(new ResourceLocation("forge", name), parent, items);
        } else {
            newTag = new TagWrapper(new ResourceLocation("forge", name), parent, items, blocks);
        }
        TagWrapper tag = tags.stream().filter(tagWrapper -> tagWrapper.getRl().equals(new ResourceLocation("forge", name))).findFirst()
                .orElse(newTag);
        tags.add(tag);
        return tag;
    }

    public static TagWrapper getOrCreateItemTag(String name, TagWrapper.Type parent, Item[] items) {
        return getOrCreateTag(name, parent, items, null);
    }

    public static TagWrapper getOrCreateBlockTag(String name, TagWrapper.Type parent, Block[] blocks) {
        return getOrCreateTag(name, parent, Arrays.stream(blocks).map(Block::asItem).toArray(Item[]::new), blocks);
    }

    public static final TagWrapper STONE_MOON = getOrCreateBlockTag("stone/moon", TagWrapper.Type.STONE, new Block[]{
            BlockInit.MOON_STONE.get()});
    public static final TagWrapper COBBLESTONE_MOON = getOrCreateBlockTag("cobblestone/moon", null, new Block[]{
            BlockInit.MOON_COBBLESTONE.get()});

    public static final TagWrapper ORES_IRON = getOrCreateBlockTag("ores/iron", TagWrapper.Type.ORES, new Block[]{
            BlockInit.MOON_IRON_ORE.get()});
    public static final TagWrapper ORES_ALUMINUM = getOrCreateBlockTag("ores/aluminum", TagWrapper.Type.ORES, new Block[]{
            BlockInit.MOON_ALUMINUM_ORE.get()});
    public static final TagWrapper ORES_TITANIUM = getOrCreateBlockTag("ores/titanium", TagWrapper.Type.ORES, new Block[]{
            BlockInit.MOON_TITANIUM_ORE.get()});
    public static final TagWrapper ORES_SILICON = getOrCreateBlockTag("ores/silicon", TagWrapper.Type.ORES, new Block[]{
            BlockInit.MOON_SILICON_ORE.get()});
}
