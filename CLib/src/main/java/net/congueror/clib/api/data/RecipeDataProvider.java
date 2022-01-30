package net.congueror.clib.api.data;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.util.registry.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public class RecipeDataProvider extends RecipeProvider {

    String modid;

    public RecipeDataProvider(DataGenerator pGenerator, String modid) {
        super(pGenerator);
        this.modid = modid;
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        if (BlockBuilder.OBJECTS.get(modid) != null)
            BlockBuilder.OBJECTS.get(modid).forEach(builder -> {
                builder.recipes.forEach(consumerBlockBiConsumer -> consumerBlockBiConsumer.accept(pFinishedRecipeConsumer, builder.block));
            });

        if (ItemBuilder.OBJECTS.get(modid) != null)
            ItemBuilder.OBJECTS.get(modid).forEach(builder -> {
                builder.recipes.forEach(consumerItemBiConsumer -> consumerItemBiConsumer.accept(pFinishedRecipeConsumer, builder.getItem()));
            });
    }

    public static String getHasName(ItemLike pItemLike) {
        return "has_" + Objects.requireNonNull(pItemLike.asItem().getRegistryName()).getPath();
    }

    public static String getHasName(Tag.Named<Item> tag) {
        return "has_" + tag.getName().getPath();
    }

    public static Tag.Named<Item> getTag(String parent, ItemLike item) {
        String path = Objects.requireNonNull(item.asItem().getRegistryName()).getPath();
        String full = parent + path.substring(0, (path.lastIndexOf("_") == -1 ? path.length() : path.lastIndexOf("_")));
        return ItemBuilder.ITEM_TAGS.get(full);
    }

    public static Tag.Named<Item> getTag(String full) {
        return ItemBuilder.ITEM_TAGS.get(full);
    }

    public static void shaped2x2Recipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, int resultCount, Tag.Named<Item> ingredient) {
        ShapedRecipeBuilder.shaped(result, resultCount)
                .pattern("aa ")
                .pattern("aa ")
                .pattern("   ")
                .define('a', ingredient)
                .unlockedBy("has_" + ingredient.getName().getPath(), has(ingredient))
                .save(finishedRecipeConsumer);
    }

    public static void shapelessRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, int resultCount, ItemLike ingredient, int ingredientCount) {
        ShapelessRecipeBuilder.shapeless(result, resultCount).requires(Ingredient.of(ingredient), ingredientCount).unlockedBy(getHasName(ingredient), has(ingredient)).save(finishedRecipeConsumer);
    }

    public static void shapelessRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, int resultCount, Tag.Named<Item> ingredient, int ingredientCount) {
        ShapelessRecipeBuilder.shapeless(result, resultCount).requires(Ingredient.of(ingredient), ingredientCount).unlockedBy(getHasName(ingredient), has(ingredient)).save(finishedRecipeConsumer);
    }

    @SafeVarargs
    public static void shapelessRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, int resultCount, Tag.Named<Item> has, Tag.Named<Item>... ingredient) {
        ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(result, resultCount).unlockedBy(getHasName(has), has(has));
        if (ingredient.length == 1) {
            builder.requires(ingredient[0]);
        } else if (ingredient.length == 2) {
            builder.requires(ingredient[0]).requires(ingredient[1]);
        } else if (ingredient.length == 3) {
            builder.requires(ingredient[0]).requires(ingredient[1]).requires(ingredient[2]);
        } else if (ingredient.length == 4) {
            builder.requires(ingredient[0]).requires(ingredient[1]).requires(ingredient[2]).requires(ingredient[3]);
        } else if (ingredient.length == 5) {
            builder.requires(ingredient[0]).requires(ingredient[1]).requires(ingredient[2]).requires(ingredient[3]).requires(ingredient[4]);
        } else if (ingredient.length == 6) {
            builder.requires(ingredient[0]).requires(ingredient[1]).requires(ingredient[2]).requires(ingredient[3]).requires(ingredient[4]).requires(ingredient[5]);
        } else if (ingredient.length == 7) {
            builder.requires(ingredient[0]).requires(ingredient[1]).requires(ingredient[2]).requires(ingredient[3]).requires(ingredient[4]).requires(ingredient[5]).requires(ingredient[6]);
        } else if (ingredient.length == 8) {
            builder.requires(ingredient[0]).requires(ingredient[1]).requires(ingredient[2]).requires(ingredient[3]).requires(ingredient[4]).requires(ingredient[5]).requires(ingredient[6]).requires(ingredient[7]);
        } else if (ingredient.length >= 9) {
            builder.requires(ingredient[0]).requires(ingredient[1]).requires(ingredient[2]).requires(ingredient[3]).requires(ingredient[4]).requires(ingredient[5]).requires(ingredient[6]).requires(ingredient[7]).requires(ingredient[8]);
        }
        builder.save(finishedRecipeConsumer);
    }

    public static void blastingRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, Tag.Named<Item> pIngredient, float exp, int cookTime) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(pIngredient), result, exp, cookTime).unlockedBy(getHasName(pIngredient), has(pIngredient)).save(finishedRecipeConsumer);
    }

    public static void smeltingRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, ItemLike result, Tag.Named<Item> pIngredient, float exp, int cookTime) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(pIngredient), result, exp, cookTime).unlockedBy(getHasName(pIngredient), has(pIngredient)).save(finishedRecipeConsumer);
    }

    public static void dustRecipes(Consumer<FinishedRecipe> finishedRecipeConsumer, Item dust, Item material, Tag.Named<Item> tag) {
        shapelessRecipe(finishedRecipeConsumer, dust, 1, getTag("forge:dusts/", dust), tag, ItemTags.createOptional(new ResourceLocation("clib:hammer")));
        smeltingRecipe(finishedRecipeConsumer, material, getTag("forge:dusts/", dust), 0.7f, 200);
    }

    public static void gearRecipe(Consumer<FinishedRecipe> finishedRecipeConsumer, Item gear, Tag.Named<Item> material) {
        ShapedRecipeBuilder.shaped(gear, 1)
                .pattern(" a ")
                .pattern("a a")
                .pattern(" a ")
                .define('a', material)
                .unlockedBy(getHasName(material), has(material))
                .save(finishedRecipeConsumer);
    }

    public static void basicMetalRecipes(Consumer<FinishedRecipe> c, BasicMetalRegistryObject obj) {
        Block block = obj.getBlock().get();
        Block ore = obj.getOre().get();
        Item ingot = obj.getIngot().get();
        Item nugget = obj.getNugget().get();
        Item dust = obj.getDust().get();
        Item gear = obj.getGear().get();
        Item raw = obj.getRawItem().get();

        shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
        shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        smeltingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 200);
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 400);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        gearRecipe(c, gear, getTag("forge:ingots/", ingot));
    }

    public static void alloyMetalRecipes(Consumer<FinishedRecipe> c, AlloyMetalRegistryObject obj) {
        Block block = obj.getBlock().get();
        Item ingot = obj.getIngot().get();
        Item nugget = obj.getNugget().get();
        Item dust = obj.getDust().get();
        Item gear = obj.getGear().get();
        Item ore = obj.getBlend().get();

        shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
        shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        smeltingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 400);
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 800);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        gearRecipe(c, gear, getTag("forge:ingots/", ingot));
    }

    public static void singleGemRecipes(Consumer<FinishedRecipe> c, SingleGemRegistryObject obj) {
        Item gem = obj.getGem().get();
        Item dust = obj.getDust().get();
        Item gear = obj.getGear().get();
        Block block = obj.getBlock().get();
        Block ore = obj.getOre().get();

        shapelessRecipe(c, block, 1, getTag("forge:gems/", gem), 9);
        shapelessRecipe(c, gem, 9, getTag("forge:storage_blocks/", block), 1);
        smeltingRecipe(c, gem, getTag("forge:ores/", ore), 0.7f, 205);
        blastingRecipe(c, gem, getTag("forge:ores/", ore), 0.7f, 400);
        dustRecipes(c, dust, gem, getTag("forge:gems/", gem));
        gearRecipe(c, gear, getTag("forge:gems/", gem));
    }

    public static void geodeGemRecipes(Consumer<FinishedRecipe> r, GeodeGemRegistryObject obj) {
        Block block = obj.getBlock().get();
        Item shard = obj.getShard().get();
        Item dust = obj.getDust().get();
        shaped2x2Recipe(r, block, 1, getTag("forge:shards/", shard));
        dustRecipes(r, dust, shard, getTag("forge:shards/", shard));
    }

    public static void debrisMetalRecipes(Consumer<FinishedRecipe> c, DebrisMetalRegistryObject obj) {
        Block block = obj.getBlock().get();
        Block ore = obj.getOre().get();
        Item ingot = obj.getIngot().get();
        Item nugget = obj.getNugget().get();
        Item dust = obj.getDust().get();
        Item gear = obj.getGear().get();
        Item scrap = obj.getScrap().get();

        shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
        shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        shapelessRecipe(c, ingot, 1, getTag("forge:scraps/", scrap), 9);
        smeltingRecipe(c, scrap, getTag("forge:ores/", ore), 0.7f, 400);
        blastingRecipe(c, scrap, getTag("forge:ores/", ore), 0.7f, 800);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        gearRecipe(c, gear, getTag("forge:ingots/", ingot));
    }

    public static void radioactiveMetalRecipes(Consumer<FinishedRecipe> c, RadioactiveMetalRegistryObject obj) {
        Block ore = obj.getOre().get();
        Item ingot = obj.getIngot().get();
        Item nugget = obj.getNugget().get();
        Item dust = obj.getDust().get();

        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        smeltingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 200);
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 400);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
    }

    public static void singleMetalRecipes(Consumer<FinishedRecipe> c, SingleMetalRegistryObject obj) {
        Block block = obj.getBlock().get();
        Block ore = obj.getOre().get();
        Item ingot = obj.getIngot().get();
        Item nugget = obj.getNugget().get();
        Item dust = obj.getDust().get();
        Item gear = obj.getGear().get();
        Item raw = obj.getRawItem().get();

        shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
        shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        smeltingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 200);
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 400);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        gearRecipe(c, gear, getTag("forge:ingots/", ingot));
    }













    public static void metalRecipes(Consumer<FinishedRecipe> c, @Nullable Block block, Item ingot, Item nugget, Item dust, @Nullable Item gear, Block ore) {
        if (block != null) {
            shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
            shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        }
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        smeltingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 200);
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 400);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        if (gear != null) {
            gearRecipe(c, gear, getTag("forge:ingots/", ingot));
        }
    }

    public static void alloyRecipes(Consumer<FinishedRecipe> c, Block block, Item ingot, Item nugget, Item dust, @Nullable Item gear, Item ore) {
        shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
        shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        smeltingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 400);
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 800);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        if (gear != null) {
            gearRecipe(c, gear, getTag("forge:ingots/", ingot));
        }
    }

    public static void gemRecipes(Consumer<FinishedRecipe> c, Block block, Item gem, Item dust, @Nullable Item gear, Block ore) {
        shapelessRecipe(c, block, 1, getTag("forge:gems/", gem), 9);
        shapelessRecipe(c, gem, 9, getTag("forge:storage_blocks/", block), 1);
        smeltingRecipe(c, gem, getTag("forge:ores/", ore), 0.7f, 205);
        blastingRecipe(c, gem, getTag("forge:ores/", ore), 0.7f, 400);
        dustRecipes(c, dust, gem, getTag("forge:gems/", gem));
        if (gear != null) {
            gearRecipe(c, gear, getTag("forge:gems/", gem));
        }
    }

    public static void debrisRecipes(Consumer<FinishedRecipe> c, Block block, Item ingot, Item nugget, Item dust, @Nullable Item gear, Block ore, Item scrap) {
        shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
        shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        shapelessRecipe(c, ingot, 1, getTag("forge:scraps/", scrap), 9);
        smeltingRecipe(c, scrap, getTag("forge:ores/", ore), 0.7f, 400);
        blastingRecipe(c, scrap, getTag("forge:ores/", ore), 0.7f, 800);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        if (gear != null) {
            gearRecipe(c, gear, getTag("forge:ingots/", ingot));
        }
    }

    @Override
    public void run(HashCache pCache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Map<String, Integer> map = new HashMap<>();
        buildCraftingRecipes((p_125991_) -> {
            if (!set.add(p_125991_.getId())) {
                if (map.get(p_125991_.getId().toString()) != null) {
                    map.put(p_125991_.getId().toString(), map.get(p_125991_.getId().toString()) + 1);
                } else {
                    map.put(p_125991_.getId().toString(), 1);
                }
            }
            saveRecipe(pCache, p_125991_.serializeRecipe(), path.resolve("data/" + p_125991_.getId().getNamespace() + "/recipes/" + p_125991_.getId().getPath() + (map.get(p_125991_.getId().toString()) == null ? "" : "_" + map.get(p_125991_.getId().toString())) + ".json"));
            JsonObject jsonobject = p_125991_.serializeAdvancement();
            if (jsonobject != null) {
                saveAdvancement(pCache, jsonobject, path.resolve("data/" + p_125991_.getId().getNamespace() + "/advancements/" + p_125991_.getAdvancementId().getPath() + ".json"));
            }
        });
    }
}
