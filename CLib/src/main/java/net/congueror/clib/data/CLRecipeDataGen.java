package net.congueror.clib.data;

import net.congueror.clib.CLib;
import net.congueror.clib.util.CLTags;
import net.congueror.clib.util.registry.data.RecipeDataProvider;
import net.congueror.clib.init.CLBlockInit;
import net.congueror.clib.init.CLItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class CLRecipeDataGen extends RecipeDataProvider {
    public CLRecipeDataGen(DataGenerator pGenerator) {
        super(pGenerator, CLib.MODID);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> r) {
        super.buildCraftingRecipes(r);

        smeltingRecipe(r, CLItemInit.SULFUR_DUST.get(), getTag("forge:ores/", CLBlockInit.SULFUR_ORE.get()), 0.5f, 200);
        blastingRecipe(r, CLItemInit.SULFUR_DUST.get(), getTag("forge:ores/", CLBlockInit.SULFUR_ORE.get()), 0.5f, 400);

        smeltingRecipe(r, CLItemInit.SALTPETRE_DUST.get(), getTag("forge:ores/", CLBlockInit.SALTPETRE_ORE.get()), 0.5f, 200);
        blastingRecipe(r, CLItemInit.SALTPETRE_DUST.get(), getTag("forge:ores/", CLBlockInit.SALTPETRE_ORE.get()), 0.5f, 400);

        shapelessRecipe(r, CLItemInit.COAL_NUGGET.get(), 9, ItemTags.COALS, 1);
        shapelessRecipe(r, Items.COAL, 1, getTag("forge:nuggets/", CLItemInit.COAL_NUGGET.get()), 9);

        smeltingRecipe(r, CLItemInit.SILICON.get(), getTag("forge:gems/quartz"), 0.5f, 200);
        blastingRecipe(r, CLItemInit.SILICON.get(), getTag("forge:gems/quartz"), 0.5f, 400);

        ShapedRecipeBuilder.shaped(CLBlockInit.SALT_BLOCK.get(), 1)
                .pattern("aa ")
                .pattern("aa ")
                .pattern("   ")
                .define('a', getTag("forge:dusts/", CLItemInit.SALT.get()))
                .unlockedBy("has_salt", has(getTag("forge:dusts/", CLItemInit.SALT.get())))
                .save(r);

        ShapedRecipeBuilder.shaped(CLItemInit.IRON_HAMMER.get(), 1)
                .pattern(" aa")
                .pattern(" sa")
                .pattern("s  ")
                .define('a', Tags.Items.INGOTS_IRON)
                .define('s', CLTags.Items.STICKS)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(r);

        shapelessRecipe(r, Items.GUNPOWDER, 4, getTag("forge:dusts/", CLItemInit.SALTPETRE_DUST.get()), getTag("forge:dusts/", CLItemInit.COAL_DUST.get()), getTag("forge:dusts/", CLItemInit.COAL_DUST.get()), getTag("forge:dusts/", CLItemInit.SULFUR_DUST.get()), getTag("forge:dusts/", CLItemInit.SALTPETRE_DUST.get()));

        ShapedRecipeBuilder.shaped(CLItemInit.PYROTHEUM_DUST.get(), 1)
                .pattern("bb ")
                .pattern("rs ")
                .pattern("   ")
                .define('b', CLTags.Items.DUSTS_BLAZE_POWDER)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('s', getTag("forge:dusts/", CLItemInit.SULFUR_DUST.get()))
                .unlockedBy("has_blaze_powder", has(CLTags.Items.DUSTS_BLAZE_POWDER))
                .save(r);

        shapelessRecipe(r, CLItemInit.WOOD_DUST.get(), 1, getTag("forge:dusts/", CLItemInit.WOOD_DUST.get()), ItemTags.LOGS, CLTags.Items.HAMMERS);
        dustRecipes(r, CLItemInit.COAL_DUST.get(), Items.COAL, ItemTags.COALS);
        dustRecipes(r, CLItemInit.IRON_DUST.get(), Items.IRON_INGOT, Tags.Items.INGOTS_IRON);
        dustRecipes(r, CLItemInit.COPPER_DUST.get(), Items.COPPER_INGOT, Tags.Items.INGOTS_COPPER);
        dustRecipes(r, CLItemInit.GOLD_DUST.get(), Items.GOLD_INGOT, Tags.Items.INGOTS_GOLD);
        dustRecipes(r, CLItemInit.LAPIS_DUST.get(), Items.LAPIS_LAZULI, Tags.Items.GEMS_LAPIS);
        dustRecipes(r, CLItemInit.QUARTZ_DUST.get(), Items.QUARTZ, Tags.Items.GEMS_QUARTZ);
        dustRecipes(r, CLItemInit.AMETHYST_DUST.get(), Items.AMETHYST_SHARD, Tags.Items.GEMS_AMETHYST);
        dustRecipes(r, CLItemInit.DIAMOND_DUST.get(), Items.DIAMOND, Tags.Items.GEMS_DIAMOND);
        dustRecipes(r, CLItemInit.EMERALD_DUST.get(), Items.EMERALD, Tags.Items.GEMS_EMERALD);
        dustRecipes(r, CLItemInit.NETHERITE_DUST.get(), Items.NETHERITE_INGOT, Tags.Items.INGOTS_NETHERITE);
        shapelessRecipe(r, CLItemInit.OBSIDIAN_DUST.get(), 1, getTag("forge:dusts/", CLItemInit.OBSIDIAN_DUST.get()), Tags.Items.OBSIDIAN, CLTags.Items.HAMMERS);

        gearRecipe(r, CLItemInit.WOOD_GEAR.get(), ItemTags.LOGS);
        gearRecipe(r, CLItemInit.STONE_GEAR.get(), Tags.Items.STONE);
        gearRecipe(r, CLItemInit.IRON_GEAR.get(), Tags.Items.INGOTS_IRON);
        gearRecipe(r, CLItemInit.COPPER_GEAR.get(), Tags.Items.INGOTS_COPPER);
        gearRecipe(r, CLItemInit.GOLD_GEAR.get(), Tags.Items.INGOTS_GOLD);
        gearRecipe(r, CLItemInit.LAPIS_GEAR.get(), Tags.Items.GEMS_LAPIS);
        gearRecipe(r, CLItemInit.QUARTZ_GEAR.get(), Tags.Items.GEMS_QUARTZ);
        gearRecipe(r, CLItemInit.DIAMOND_GEAR.get(), Tags.Items.GEMS_DIAMOND);
        gearRecipe(r, CLItemInit.EMERALD_GEAR.get(), Tags.Items.GEMS_EMERALD);
        gearRecipe(r, CLItemInit.NETHERITE_GEAR.get(), Tags.Items.INGOTS_NETHERITE);

        ShapedRecipeBuilder.shaped(CLItemInit.TREE_TAP.get())
                .pattern(" a ")
                .pattern("aaa")
                .pattern("a  ")
                .define('a', ItemTags.PLANKS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(r);
        shapelessRecipe(r, CLItemInit.RUBBER.get(), 1, getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), ItemTags.create(new ResourceLocation("clib:tree_tap")));
        shapelessRecipe(r, CLBlockInit.RUBBER_PLANKS.get(), 4, getTag("forge:rubber_logs"), 1);
        ShapedRecipeBuilder.shaped(CLBlockInit.RUBBER_WOOD.get(), 3)
                .pattern("aa ")
                .pattern("aa ")
                .pattern("   ")
                .define('a', CLBlockInit.RUBBER_LOG.get())
                .unlockedBy("has_rubber_log", has(CLBlockInit.RUBBER_LOG.get()))
                .save(r);
        ShapedRecipeBuilder.shaped(CLBlockInit.RUBBER_STRIPPED_WOOD.get(), 3)
                .pattern("aa ")
                .pattern("aa ")
                .pattern("   ")
                .define('a', CLBlockInit.RUBBER_STRIPPED_LOG.get())
                .unlockedBy("has_stripped_rubber_log", has(CLBlockInit.RUBBER_STRIPPED_LOG.get()))
                .save(r);
    }
}
