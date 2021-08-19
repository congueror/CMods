package com.congueror.clib.util;

import com.congueror.clib.api.data.TagWrapper;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

public class CLTags {

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

    public static final TagWrapper INGOTS_TIN = getOrCreateItemTag("ingots/tin", TagWrapper.Type.INGOTS, new Item[]{ItemInit.TIN_INGOT.get()});
    public static final TagWrapper INGOTS_STEEL = getOrCreateItemTag("ingots/steel", TagWrapper.Type.INGOTS, new Item[]{ItemInit.STEEL_INGOT.get()});
    public static final TagWrapper INGOTS_ALUMINUM = getOrCreateItemTag("ingots/aluminum", TagWrapper.Type.INGOTS, new Item[]{ItemInit.ALUMINUM_INGOT.get()});
    public static final TagWrapper INGOTS_LEAD = getOrCreateItemTag("ingots/lead", TagWrapper.Type.INGOTS, new Item[]{ItemInit.LEAD_INGOT.get()});
    public static final TagWrapper INGOTS_COPPER = getOrCreateItemTag("ingots/copper", TagWrapper.Type.INGOTS, new Item[]{ItemInit.COPPER_INGOT.get()});
    public static final TagWrapper INGOTS_SILVER = getOrCreateItemTag("ingots/silver", TagWrapper.Type.INGOTS, new Item[]{ItemInit.SILVER_INGOT.get()});
    public static final TagWrapper INGOTS_LUMIUM = getOrCreateItemTag("ingots/lumium", TagWrapper.Type.INGOTS, new Item[]{ItemInit.LUMIUM_INGOT.get()});
    public static final TagWrapper INGOTS_NICKEL = getOrCreateItemTag("ingots/nickel", TagWrapper.Type.INGOTS, new Item[]{ItemInit.NICKEL_INGOT.get()});
    public static final TagWrapper INGOTS_INVAR = getOrCreateItemTag("ingots/invar", TagWrapper.Type.INGOTS, new Item[]{ItemInit.INVAR_INGOT.get()});
    public static final TagWrapper INGOTS_ELECTRUM = getOrCreateItemTag("ingots/electrum", TagWrapper.Type.INGOTS, new Item[]{ItemInit.ELECTRUM_INGOT.get()});
    public static final TagWrapper INGOTS_PLATINUM = getOrCreateItemTag("ingots/platinum", TagWrapper.Type.INGOTS, new Item[]{ItemInit.PLATINUM_INGOT.get()});
    public static final TagWrapper INGOTS_ENDERIUM = getOrCreateItemTag("ingots/enderium", TagWrapper.Type.INGOTS, new Item[]{ItemInit.ENDERIUM_INGOT.get()});
    public static final TagWrapper INGOTS_SIGNALUM = getOrCreateItemTag("ingots/signalum", TagWrapper.Type.INGOTS, new Item[]{ItemInit.SIGNALUM_INGOT.get()});
    public static final TagWrapper INGOTS_TUNGSTEN = getOrCreateItemTag("ingots/tungsten", TagWrapper.Type.INGOTS, new Item[]{ItemInit.TUNGSTEN_INGOT.get()});
    public static final TagWrapper INGOTS_BRONZE = getOrCreateItemTag("ingots/bronze", TagWrapper.Type.INGOTS, new Item[]{ItemInit.BRONZE_INGOT.get()});
    public static final TagWrapper INGOTS_TITANIUM = getOrCreateItemTag("ingots/titanium", TagWrapper.Type.INGOTS, new Item[]{ItemInit.TITANIUM_INGOT.get()});
    public static final TagWrapper INGOTS_URANIUM = getOrCreateItemTag("ingots/uranium", TagWrapper.Type.INGOTS, new Item[]{ItemInit.URANIUM_INGOT.get()});
    public static final TagWrapper INGOTS_COBALT = getOrCreateItemTag("ingots/cobalt", TagWrapper.Type.INGOTS, new Item[]{ItemInit.COBALT_INGOT.get()});
    public static final TagWrapper INGOTS_ZINC = getOrCreateItemTag("ingots/zinc", TagWrapper.Type.INGOTS, new Item[]{ItemInit.ZINC_INGOT.get()});
    public static final TagWrapper INGOTS_BRASS = getOrCreateItemTag("ingots/brass", TagWrapper.Type.INGOTS, new Item[]{ItemInit.BRASS_INGOT.get()});
    public static final TagWrapper INGOTS_CHROMIUM = getOrCreateItemTag("ingots/chromium", TagWrapper.Type.INGOTS, new Item[]{ItemInit.CHROMIUM_INGOT.get()});
    public static final TagWrapper INGOTS_THORIUM = getOrCreateItemTag("ingots/thorium", TagWrapper.Type.INGOTS, new Item[]{ItemInit.THORIUM_INGOT.get()});

    public static final TagWrapper NUGGETS_TIN = getOrCreateItemTag("nuggets/tin", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.TIN_NUGGET.get()});
    public static final TagWrapper NUGGETS_STEEL = getOrCreateItemTag("nuggets/steel", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.STEEL_NUGGET.get()});
    public static final TagWrapper NUGGETS_ALUMINUM = getOrCreateItemTag("nuggets/aluminum", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.ALUMINUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_LEAD = getOrCreateItemTag("nuggets/lead", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.LEAD_NUGGET.get()});
    public static final TagWrapper NUGGETS_COPPER = getOrCreateItemTag("nuggets/copper", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.COPPER_NUGGET.get()});
    public static final TagWrapper NUGGETS_SILVER = getOrCreateItemTag("nuggets/silver", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.SILVER_NUGGET.get()});
    public static final TagWrapper NUGGETS_LUMIUM = getOrCreateItemTag("nuggets/lumium", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.LUMIUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_NICKEL = getOrCreateItemTag("nuggets/nickel", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.NICKEL_NUGGET.get()});
    public static final TagWrapper NUGGETS_INVAR = getOrCreateItemTag("nuggets/invar", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.INVAR_NUGGET.get()});
    public static final TagWrapper NUGGETS_ELECTRUM = getOrCreateItemTag("nuggets/electrum", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.ELECTRUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_PLATINUM = getOrCreateItemTag("nuggets/platinum", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.PLATINUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_ENDERIUM = getOrCreateItemTag("nuggets/enderium", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.ENDERIUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_SIGNALUM = getOrCreateItemTag("nuggets/signalum", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.SIGNALUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_TUNGSTEN = getOrCreateItemTag("nuggets/tungsten", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.TUNGSTEN_NUGGET.get()});
    public static final TagWrapper NUGGETS_BRONZE = getOrCreateItemTag("nuggets/bronze", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.BRONZE_NUGGET.get()});
    public static final TagWrapper NUGGETS_TITANIUM = getOrCreateItemTag("nuggets/titanium", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.TITANIUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_URANIUM = getOrCreateItemTag("nuggets/uranium", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.URANIUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_COBALT = getOrCreateItemTag("nuggets/cobalt", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.COBALT_NUGGET.get()});
    public static final TagWrapper NUGGETS_ZINC = getOrCreateItemTag("nuggets/zinc", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.ZINC_NUGGET.get()});
    public static final TagWrapper NUGGETS_BRASS = getOrCreateItemTag("nuggets/brass", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.BRASS_NUGGET.get()});
    public static final TagWrapper NUGGETS_CHROMIUM = getOrCreateItemTag("nuggets/chromium", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.CHROMIUM_NUGGET.get()});
    public static final TagWrapper NUGGETS_THORIUM = getOrCreateItemTag("nuggets/thorium", TagWrapper.Type.NUGGETS, new Item[]{ItemInit.THORIUM_NUGGET.get()});

    public static final TagWrapper DUSTS_TIN = getOrCreateItemTag("dusts/tin", TagWrapper.Type.DUSTS, new Item[]{ItemInit.TIN_DUST.get()});
    public static final TagWrapper DUSTS_STEEL = getOrCreateItemTag("dusts/steel", TagWrapper.Type.DUSTS, new Item[]{ItemInit.STEEL_DUST.get()});
    public static final TagWrapper DUSTS_ALUMINUM = getOrCreateItemTag("dusts/aluminum", TagWrapper.Type.DUSTS, new Item[]{ItemInit.ALUMINUM_DUST.get()});
    public static final TagWrapper DUSTS_LEAD = getOrCreateItemTag("dusts/lead", TagWrapper.Type.DUSTS, new Item[]{ItemInit.LEAD_DUST.get()});
    public static final TagWrapper DUSTS_COPPER = getOrCreateItemTag("dusts/copper", TagWrapper.Type.DUSTS, new Item[]{ItemInit.COPPER_DUST.get()});
    public static final TagWrapper DUSTS_RUBY = getOrCreateItemTag("dusts/ruby", TagWrapper.Type.DUSTS, new Item[]{ItemInit.RUBY_DUST.get()});
    public static final TagWrapper DUSTS_SILVER = getOrCreateItemTag("dusts/silver", TagWrapper.Type.DUSTS, new Item[]{ItemInit.SILVER_DUST.get()});
    public static final TagWrapper DUSTS_LUMIUM = getOrCreateItemTag("dusts/lumium", TagWrapper.Type.DUSTS, new Item[]{ItemInit.LUMIUM_DUST.get()});
    public static final TagWrapper DUSTS_NICKEL = getOrCreateItemTag("dusts/nickel", TagWrapper.Type.DUSTS, new Item[]{ItemInit.NICKEL_DUST.get()});
    public static final TagWrapper DUSTS_INVAR = getOrCreateItemTag("dusts/invar", TagWrapper.Type.DUSTS, new Item[]{ItemInit.INVAR_DUST.get()});
    public static final TagWrapper DUSTS_ELECTRUM = getOrCreateItemTag("dusts/electrum", TagWrapper.Type.DUSTS, new Item[]{ItemInit.ELECTRUM_DUST.get()});
    public static final TagWrapper DUSTS_PLATINUM = getOrCreateItemTag("dusts/platinum", TagWrapper.Type.DUSTS, new Item[]{ItemInit.PLATINUM_DUST.get()});
    public static final TagWrapper DUSTS_ENDERIUM = getOrCreateItemTag("dusts/enderium", TagWrapper.Type.DUSTS, new Item[]{ItemInit.ENDERIUM_DUST.get()});
    public static final TagWrapper DUSTS_SIGNALUM = getOrCreateItemTag("dusts/signalum", TagWrapper.Type.DUSTS, new Item[]{ItemInit.SIGNALUM_DUST.get()});
    public static final TagWrapper DUSTS_TUNGSTEN = getOrCreateItemTag("dusts/tungsten", TagWrapper.Type.DUSTS, new Item[]{ItemInit.TUNGSTEN_DUST.get()});
    public static final TagWrapper DUSTS_BRONZE = getOrCreateItemTag("dusts/bronze", TagWrapper.Type.DUSTS, new Item[]{ItemInit.BRONZE_DUST.get()});
    public static final TagWrapper DUSTS_AMETHYST = getOrCreateItemTag("dusts/amethyst", TagWrapper.Type.DUSTS, new Item[]{ItemInit.AMETHYST_DUST.get()});
    public static final TagWrapper DUSTS_SAPPHIRE = getOrCreateItemTag("dusts/sapphire", TagWrapper.Type.DUSTS, new Item[]{ItemInit.SAPPHIRE_DUST.get()});
    public static final TagWrapper DUSTS_OPAL = getOrCreateItemTag("dusts/opal", TagWrapper.Type.DUSTS, new Item[]{ItemInit.OPAL_DUST.get()});
    public static final TagWrapper DUSTS_TITANIUM = getOrCreateItemTag("dusts/titanium", TagWrapper.Type.DUSTS, new Item[]{ItemInit.TITANIUM_DUST.get()});
    public static final TagWrapper DUSTS_URANIUM = getOrCreateItemTag("dusts/uranium", TagWrapper.Type.DUSTS, new Item[]{ItemInit.URANIUM_DUST.get()});
    public static final TagWrapper DUSTS_COBALT = getOrCreateItemTag("dusts/cobalt", TagWrapper.Type.DUSTS, new Item[]{ItemInit.COBALT_DUST.get()});
    public static final TagWrapper DUSTS_ZINC = getOrCreateItemTag("dusts/zinc", TagWrapper.Type.DUSTS, new Item[]{ItemInit.ZINC_DUST.get()});
    public static final TagWrapper DUSTS_BRASS = getOrCreateItemTag("dusts/brass", TagWrapper.Type.DUSTS, new Item[]{ItemInit.BRASS_DUST.get()});
    public static final TagWrapper DUSTS_CHROMIUM = getOrCreateItemTag("dusts/chromium", TagWrapper.Type.DUSTS, new Item[]{ItemInit.CHROMIUM_DUST.get()});
    public static final TagWrapper DUSTS_THORIUM = getOrCreateItemTag("dusts/thorium", TagWrapper.Type.DUSTS, new Item[]{ItemInit.THORIUM_DUST.get()});
    public static final TagWrapper DUSTS_SALTPETRE = getOrCreateItemTag("dusts/saltpetre", TagWrapper.Type.DUSTS, new Item[]{ItemInit.SALTPETRE_DUST.get()});
    public static final TagWrapper DUSTS_SULFUR = getOrCreateItemTag("dusts/sulfur", TagWrapper.Type.DUSTS, new Item[]{ItemInit.SULFUR_DUST.get()});
    public static final TagWrapper DUSTS_SALT = getOrCreateItemTag("dusts/salt", TagWrapper.Type.DUSTS, new Item[]{ItemInit.SALT.get()});
    public static final TagWrapper DUSTS_PYROTHEUM = getOrCreateItemTag("dusts/pyrotheum", TagWrapper.Type.DUSTS, new Item[]{ItemInit.PYROTHEUM_DUST.get()});
    public static final TagWrapper DUSTS_BLAZE_POWDER = getOrCreateItemTag("dusts/blaze_powder", TagWrapper.Type.DUSTS, new Item[]{net.minecraft.item.Items.BLAZE_POWDER});
    public static final TagWrapper DUSTS_WOOD = getOrCreateItemTag("dusts/wood", TagWrapper.Type.DUSTS, new Item[]{ItemInit.WOOD_DUST.get()});
    public static final TagWrapper DUSTS_COAL = getOrCreateItemTag("dusts/coal", TagWrapper.Type.DUSTS, new Item[]{ItemInit.COAL_DUST.get()});
    public static final TagWrapper DUSTS_IRON = getOrCreateItemTag("dusts/iron", TagWrapper.Type.DUSTS, new Item[]{ItemInit.IRON_DUST.get()});
    public static final TagWrapper DUSTS_GOLD = getOrCreateItemTag("dusts/gold", TagWrapper.Type.DUSTS, new Item[]{ItemInit.GOLD_DUST.get()});
    public static final TagWrapper DUSTS_LAPIS = getOrCreateItemTag("dusts/lapis", TagWrapper.Type.DUSTS, new Item[]{ItemInit.LAPIS_DUST.get()});
    public static final TagWrapper DUSTS_QUARTZ = getOrCreateItemTag("dusts/quartz", TagWrapper.Type.DUSTS, new Item[]{ItemInit.QUARTZ_DUST.get()});
    public static final TagWrapper DUSTS_DIAMOND = getOrCreateItemTag("dusts/diamond", TagWrapper.Type.DUSTS, new Item[]{ItemInit.DIAMOND_DUST.get()});
    public static final TagWrapper DUSTS_EMERALD = getOrCreateItemTag("dusts/emerald", TagWrapper.Type.DUSTS, new Item[]{ItemInit.EMERALD_DUST.get()});
    public static final TagWrapper DUSTS_NETHERITE = getOrCreateItemTag("dusts/netherite", TagWrapper.Type.DUSTS, new Item[]{ItemInit.NETHERITE_DUST.get()});
    public static final TagWrapper DUSTS_OBSIDIAN = getOrCreateItemTag("dusts/obsidian", TagWrapper.Type.DUSTS, new Item[]{ItemInit.OBSIDIAN_DUST.get()});

    public static final TagWrapper GEARS_TIN = getOrCreateItemTag("gears/tin", TagWrapper.Type.GEARS, new Item[]{ItemInit.TIN_GEAR.get()});
    public static final TagWrapper GEARS_STEEL = getOrCreateItemTag("gears/steel", TagWrapper.Type.GEARS, new Item[]{ItemInit.STEEL_GEAR.get()});
    public static final TagWrapper GEARS_ALUMINUM = getOrCreateItemTag("gears/aluminum", TagWrapper.Type.GEARS, new Item[]{ItemInit.ALUMINUM_GEAR.get()});
    public static final TagWrapper GEARS_LEAD = getOrCreateItemTag("gears/lead", TagWrapper.Type.GEARS, new Item[]{ItemInit.LEAD_GEAR.get()});
    public static final TagWrapper GEARS_COPPER = getOrCreateItemTag("gears/copper", TagWrapper.Type.GEARS, new Item[]{ItemInit.COPPER_GEAR.get()});
    public static final TagWrapper GEARS_RUBY = getOrCreateItemTag("gears/ruby", TagWrapper.Type.GEARS, new Item[]{ItemInit.RUBY_GEAR.get()});
    public static final TagWrapper GEARS_SILVER = getOrCreateItemTag("gears/silver", TagWrapper.Type.GEARS, new Item[]{ItemInit.SILVER_GEAR.get()});
    public static final TagWrapper GEARS_LUMIUM = getOrCreateItemTag("gears/lumium", TagWrapper.Type.GEARS, new Item[]{ItemInit.LUMIUM_GEAR.get()});
    public static final TagWrapper GEARS_NICKEL = getOrCreateItemTag("gears/nickel", TagWrapper.Type.GEARS, new Item[]{ItemInit.NICKEL_GEAR.get()});
    public static final TagWrapper GEARS_INVAR = getOrCreateItemTag("gears/invar", TagWrapper.Type.GEARS, new Item[]{ItemInit.INVAR_GEAR.get()});
    public static final TagWrapper GEARS_ELECTRUM = getOrCreateItemTag("gears/electrum", TagWrapper.Type.GEARS, new Item[]{ItemInit.ELECTRUM_GEAR.get()});
    public static final TagWrapper GEARS_PLATINUM = getOrCreateItemTag("gears/platinum", TagWrapper.Type.GEARS, new Item[]{ItemInit.PLATINUM_GEAR.get()});
    public static final TagWrapper GEARS_ENDERIUM = getOrCreateItemTag("gears/enderium", TagWrapper.Type.GEARS, new Item[]{ItemInit.ENDERIUM_GEAR.get()});
    public static final TagWrapper GEARS_SIGNALUM = getOrCreateItemTag("gears/signalum", TagWrapper.Type.GEARS, new Item[]{ItemInit.SIGNALUM_GEAR.get()});
    public static final TagWrapper GEARS_TUNGSTEN = getOrCreateItemTag("gears/tungsten", TagWrapper.Type.GEARS, new Item[]{ItemInit.TUNGSTEN_GEAR.get()});
    public static final TagWrapper GEARS_BRONZE = getOrCreateItemTag("gears/bronze", TagWrapper.Type.GEARS, new Item[]{ItemInit.BRONZE_GEAR.get()});
    public static final TagWrapper GEARS_AMETHYST = getOrCreateItemTag("gears/amethyst", TagWrapper.Type.GEARS, new Item[]{ItemInit.AMETHYST_GEAR.get()});
    public static final TagWrapper GEARS_SAPPHIRE = getOrCreateItemTag("gears/sapphire", TagWrapper.Type.GEARS, new Item[]{ItemInit.SAPPHIRE_GEAR.get()});
    public static final TagWrapper GEARS_OPAL = getOrCreateItemTag("gears/opal", TagWrapper.Type.GEARS, new Item[]{ItemInit.OPAL_GEAR.get()});
    public static final TagWrapper GEARS_TITANIUM = getOrCreateItemTag("gears/titanium", TagWrapper.Type.GEARS, new Item[]{ItemInit.TITANIUM_GEAR.get()});
    public static final TagWrapper GEARS_COBALT = getOrCreateItemTag("gears/cobalt", TagWrapper.Type.GEARS, new Item[]{ItemInit.COBALT_GEAR.get()});
    public static final TagWrapper GEARS_ZINC = getOrCreateItemTag("gears/zinc", TagWrapper.Type.GEARS, new Item[]{ItemInit.ZINC_GEAR.get()});
    public static final TagWrapper GEARS_BRASS = getOrCreateItemTag("gears/brass", TagWrapper.Type.GEARS, new Item[]{ItemInit.BRASS_GEAR.get()});
    public static final TagWrapper GEARS_CHROMIUM = getOrCreateItemTag("gears/chromium", TagWrapper.Type.GEARS, new Item[]{ItemInit.CHROMIUM_GEAR.get()});
    public static final TagWrapper GEARS_WOOD = getOrCreateItemTag("gears/wood", TagWrapper.Type.GEARS, new Item[]{ItemInit.WOOD_GEAR.get()});
    public static final TagWrapper GEARS_STONE = getOrCreateItemTag("gears/stone", TagWrapper.Type.GEARS, new Item[]{ItemInit.STONE_GEAR.get()});
    public static final TagWrapper GEARS_IRON = getOrCreateItemTag("gears/iron", TagWrapper.Type.GEARS, new Item[]{ItemInit.IRON_GEAR.get()});
    public static final TagWrapper GEARS_GOLD = getOrCreateItemTag("gears/gold", TagWrapper.Type.GEARS, new Item[]{ItemInit.GOLD_GEAR.get()});
    public static final TagWrapper GEARS_LAPIS = getOrCreateItemTag("gears/lapis", TagWrapper.Type.GEARS, new Item[]{ItemInit.LAPIS_GEAR.get()});
    public static final TagWrapper GEARS_QUARTZ = getOrCreateItemTag("gears/quartz", TagWrapper.Type.GEARS, new Item[]{ItemInit.QUARTZ_GEAR.get()});
    public static final TagWrapper GEARS_DIAMOND = getOrCreateItemTag("gears/diamond", TagWrapper.Type.GEARS, new Item[]{ItemInit.DIAMOND_GEAR.get()});
    public static final TagWrapper GEARS_EMERALD = getOrCreateItemTag("gears/emerald", TagWrapper.Type.GEARS, new Item[]{ItemInit.EMERALD_GEAR.get()});
    public static final TagWrapper GEARS_NETHERITE = getOrCreateItemTag("gears/netherite", TagWrapper.Type.GEARS, new Item[]{ItemInit.NETHERITE_GEAR.get()});

    public static final TagWrapper GEMS_RUBY = getOrCreateItemTag("gems/ruby", TagWrapper.Type.GEMS, new Item[]{ItemInit.RUBY.get()});
    public static final TagWrapper GEMS_AMETHYST = getOrCreateItemTag("gems/amethyst", TagWrapper.Type.GEMS, new Item[]{ItemInit.AMETHYST.get()});
    public static final TagWrapper GEMS_SAPPHIRE = getOrCreateItemTag("gems/sapphire", TagWrapper.Type.GEMS, new Item[]{ItemInit.SAPPHIRE.get()});
    public static final TagWrapper GEMS_OPAL = getOrCreateItemTag("gems/opal", TagWrapper.Type.GEMS, new Item[]{ItemInit.OPAL.get()});

    public static final TagWrapper RUBBER = getOrCreateItemTag("rubber", null, new Item[]{ItemInit.RUBBER.get()});
    public static final TagWrapper SILICON = getOrCreateItemTag("silicon", null, new Item[]{ItemInit.SILICON.get()});

    public static final TagWrapper STORAGE_BLOCKS_TIN = getOrCreateBlockTag("storage_blocks/tin", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.TIN_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_STEEL = getOrCreateBlockTag("storage_blocks/steel", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.STEEL_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_ALUMINUM = getOrCreateBlockTag("storage_blocks/aluminum", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.ALUMINUM_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_LEAD = getOrCreateBlockTag("storage_blocks/lead", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.LEAD_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_COPPER = getOrCreateBlockTag("storage_blocks/copper", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.COPPER_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_RUBY = getOrCreateBlockTag("storage_blocks/ruby", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.RUBY_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_SILVER = getOrCreateBlockTag("storage_blocks/silver", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.SILVER_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_LUMIUM = getOrCreateBlockTag("storage_blocks/lumium", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.LUMIUM_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_NICKEL = getOrCreateBlockTag("storage_blocks/nickel", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.NICKEL_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_INVAR = getOrCreateBlockTag("storage_blocks/invar", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.INVAR_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_ELECTRUM = getOrCreateBlockTag("storage_blocks/electrum", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.ELECTRUM_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_PLATINUM = getOrCreateBlockTag("storage_blocks/platinum", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.PLATINUM_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_ENDERIUM = getOrCreateBlockTag("storage_blocks/enderium", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.ENDERIUM_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_SIGNALUM = getOrCreateBlockTag("storage_blocks/signalum", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.SIGNALUM_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_TUNGSTEN = getOrCreateBlockTag("storage_blocks/tungsten", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.TUNGSTEN_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_BRONZE = getOrCreateBlockTag("storage_blocks/bronze", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.BRONZE_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_AMETHYST = getOrCreateBlockTag("storage_blocks/amethyst", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.AMETHYST_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_SAPPHIRE = getOrCreateBlockTag("storage_blocks/sapphire", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.SAPPHIRE_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_OPAL = getOrCreateBlockTag("storage_blocks/opal", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.OPAL_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_TITANIUM = getOrCreateBlockTag("storage_blocks/titanium", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.TITANIUM_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_COBALT = getOrCreateBlockTag("storage_blocks/cobalt", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.COBALT_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_ZINC = getOrCreateBlockTag("storage_blocks/zinc", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.ZINC_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_BRASS = getOrCreateBlockTag("storage_blocks/brass", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.BRASS_BLOCK.get()});
    public static final TagWrapper STORAGE_BLOCKS_CHROMIUM = getOrCreateBlockTag("storage_blocks/chromium", TagWrapper.Type.STORAGE_BLOCKS, new Block[]{BlockInit.CHROMIUM_BLOCK.get()});

    public static final TagWrapper ORES_TIN = getOrCreateBlockTag("ores/tin", TagWrapper.Type.ORES, new Block[]{BlockInit.TIN_ORE.get()});
    public static final TagWrapper ORES_STEEL = getOrCreateItemTag("ores/steel", TagWrapper.Type.ORES, new Item[]{ItemInit.STEEL_BLEND.get()});
    public static final TagWrapper ORES_ALUMINUM = getOrCreateBlockTag("ores/aluminum", TagWrapper.Type.ORES, new Block[]{BlockInit.ALUMINUM_ORE.get()});
    public static final TagWrapper ORES_LEAD = getOrCreateBlockTag("ores/lead", TagWrapper.Type.ORES, new Block[]{BlockInit.LEAD_ORE.get()});
    public static final TagWrapper ORES_COPPER = getOrCreateBlockTag("ores/copper", TagWrapper.Type.ORES, new Block[]{BlockInit.COPPER_ORE.get()});
    public static final TagWrapper ORES_RUBY = getOrCreateBlockTag("ores/ruby", TagWrapper.Type.ORES, new Block[]{BlockInit.RUBY_ORE.get()});
    public static final TagWrapper ORES_SILVER = getOrCreateBlockTag("ores/silver", TagWrapper.Type.ORES, new Block[]{BlockInit.SILVER_ORE.get()});
    public static final TagWrapper ORES_LUMIUM = getOrCreateItemTag("ores/lumium", TagWrapper.Type.ORES, new Item[]{ItemInit.LUMIUM_BLEND.get()});
    public static final TagWrapper ORES_NICKEL = getOrCreateBlockTag("ores/nickel", TagWrapper.Type.ORES, new Block[]{BlockInit.NICKEL_ORE.get()});
    public static final TagWrapper ORES_INVAR = getOrCreateItemTag("ores/invar", TagWrapper.Type.ORES, new Item[]{ItemInit.INVAR_BLEND.get()});
    public static final TagWrapper ORES_ELECTRUM = getOrCreateItemTag("ores/electrum", TagWrapper.Type.ORES, new Item[]{ItemInit.ELECTRUM_BLEND.get()});
    public static final TagWrapper ORES_PLATINUM = getOrCreateBlockTag("ores/platinum", TagWrapper.Type.ORES, new Block[]{BlockInit.PLATINUM_ORE.get()});
    public static final TagWrapper ORES_ENDERIUM = getOrCreateItemTag("ores/enderium", TagWrapper.Type.ORES, new Item[]{ItemInit.ENDERIUM_BLEND.get()});
    public static final TagWrapper ORES_SIGNALUM = getOrCreateItemTag("ores/signalum", TagWrapper.Type.ORES, new Item[]{ItemInit.SIGNALUM_BLEND.get()});
    public static final TagWrapper ORES_TUNGSTEN = getOrCreateBlockTag("ores/tungsten", TagWrapper.Type.ORES, new Block[]{BlockInit.TUNGSTEN_ORE.get()});
    public static final TagWrapper ORES_BRONZE = getOrCreateItemTag("ores/bronze", TagWrapper.Type.ORES, new Item[]{ItemInit.BRONZE_BLEND.get()});
    public static final TagWrapper ORES_AMETHYST = getOrCreateBlockTag("ores/amethyst", TagWrapper.Type.ORES, new Block[]{BlockInit.AMETHYST_ORE.get()});
    public static final TagWrapper ORES_SAPPHIRE = getOrCreateBlockTag("ores/sapphire", TagWrapper.Type.ORES, new Block[]{BlockInit.SAPPHIRE_ORE.get()});
    public static final TagWrapper ORES_OPAL = getOrCreateBlockTag("ores/opal", TagWrapper.Type.ORES, new Block[]{BlockInit.OPAL_ORE.get()});
    public static final TagWrapper ORES_TITANIUM = getOrCreateBlockTag("ores/titanium", TagWrapper.Type.ORES, new Block[]{BlockInit.TITANIUM_ORE.get()});
    public static final TagWrapper ORES_URANIUM = getOrCreateBlockTag("ores/uranium", TagWrapper.Type.ORES, new Block[]{BlockInit.URANIUM_ORE.get()});
    public static final TagWrapper ORES_COBALT = getOrCreateBlockTag("ores/cobalt", TagWrapper.Type.ORES, new Block[]{BlockInit.COBALT_ORE.get()});
    public static final TagWrapper ORES_ZINC = getOrCreateBlockTag("ores/zinc", TagWrapper.Type.ORES, new Block[]{BlockInit.ZINC_ORE.get()});
    public static final TagWrapper ORES_BRASS = getOrCreateItemTag("ores/brass", TagWrapper.Type.ORES, new Item[]{ItemInit.BRASS_BLEND.get()});
    public static final TagWrapper ORES_CHROMIUM = getOrCreateBlockTag("ores/chromium", TagWrapper.Type.ORES, new Block[]{BlockInit.CHROMIUM_ORE.get()});
    public static final TagWrapper ORES_THORIUM = getOrCreateBlockTag("ores/thorium", TagWrapper.Type.ORES, new Block[]{BlockInit.THORIUM_ORE.get()});
    public static final TagWrapper ORES_SALTPETRE = getOrCreateBlockTag("ores/saltpetre", TagWrapper.Type.ORES, new Block[]{BlockInit.SALTPETRE_ORE.get()});
    public static final TagWrapper ORES_SULFUR = getOrCreateBlockTag("ores/sulfur", TagWrapper.Type.ORES, new Block[]{BlockInit.SULFUR_ORE.get()});

    public static final TagWrapper RUBBER_LOGS = getOrCreateBlockTag("rubber_logs", null, new Block[]{
            BlockInit.RUBBER_LOG.get(),
            BlockInit.RUBBER_WOOD.get(),
            BlockInit.RUBBER_STRIPPED_LOG.get(),
            BlockInit.RUBBER_STRIPPED_WOOD.get()});
}
