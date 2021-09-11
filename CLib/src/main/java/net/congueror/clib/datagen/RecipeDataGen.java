package net.congueror.clib.datagen;

import net.congueror.clib.api.data.RecipeDataProvider;
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

public class RecipeDataGen extends RecipeDataProvider {
    public RecipeDataGen(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> r) {
        metalRecipes(r, CLBlockInit.TIN_BLOCK.get(), CLItemInit.TIN_INGOT.get(), CLItemInit.TIN_NUGGET.get(), CLItemInit.TIN_DUST.get(), CLItemInit.TIN_GEAR.get(), CLBlockInit.TIN_ORE.get());

        alloyRecipes(r, CLBlockInit.STEEL_BLOCK.get(), CLItemInit.STEEL_INGOT.get(), CLItemInit.STEEL_NUGGET.get(), CLItemInit.STEEL_DUST.get(), CLItemInit.STEEL_GEAR.get(), CLItemInit.STEEL_BLEND.get());
        shapelessRecipe(r, CLItemInit.STEEL_BLEND.get(), 2, Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON, ItemTags.COALS);

        metalRecipes(r, CLBlockInit.ALUMINUM_BLOCK.get(), CLItemInit.ALUMINUM_INGOT.get(), CLItemInit.ALUMINUM_NUGGET.get(), CLItemInit.ALUMINUM_DUST.get(), CLItemInit.ALUMINUM_GEAR.get(), CLBlockInit.ALUMINUM_ORE.get());
        metalRecipes(r, CLBlockInit.LEAD_BLOCK.get(), CLItemInit.LEAD_INGOT.get(), CLItemInit.LEAD_NUGGET.get(), CLItemInit.LEAD_DUST.get(), CLItemInit.LEAD_GEAR.get(), CLBlockInit.LEAD_ORE.get());
        gemRecipes(r, CLBlockInit.RUBY_BLOCK.get(), CLItemInit.RUBY.get(), CLItemInit.RUBY_DUST.get(), CLItemInit.RUBY_GEAR.get(), CLBlockInit.RUBY_ORE.get());
        metalRecipes(r, CLBlockInit.SILVER_BLOCK.get(), CLItemInit.SILVER_INGOT.get(), CLItemInit.SILVER_NUGGET.get(), CLItemInit.SILVER_DUST.get(), CLItemInit.SILVER_GEAR.get(), CLBlockInit.SILVER_ORE.get());

        alloyRecipes(r, CLBlockInit.LUMIUM_BLOCK.get(), CLItemInit.LUMIUM_INGOT.get(), CLItemInit.LUMIUM_NUGGET.get(), CLItemInit.LUMIUM_DUST.get(), CLItemInit.LUMIUM_GEAR.get(), CLItemInit.LUMIUM_BLEND.get());
        shapelessRecipe(r, CLItemInit.LUMIUM_BLEND.get(), 2, getTag("forge:ingots/", CLItemInit.SILVER_INGOT.get()), getTag("forge:ingots/", CLItemInit.SILVER_INGOT.get()), getTag("forge:ingots/", CLItemInit.TIN_INGOT.get()), Tags.Items.DUSTS_GLOWSTONE);

        metalRecipes(r, CLBlockInit.NICKEL_BLOCK.get(), CLItemInit.NICKEL_INGOT.get(), CLItemInit.NICKEL_NUGGET.get(), CLItemInit.NICKEL_DUST.get(), CLItemInit.NICKEL_GEAR.get(), CLBlockInit.NICKEL_ORE.get());

        alloyRecipes(r, CLBlockInit.INVAR_BLOCK.get(), CLItemInit.INVAR_INGOT.get(), CLItemInit.INVAR_NUGGET.get(), CLItemInit.INVAR_DUST.get(), CLItemInit.INVAR_GEAR.get(), CLItemInit.INVAR_BLEND.get());
        shapelessRecipe(r, CLItemInit.INVAR_BLEND.get(), 3, getTag("forge:ingots/", CLItemInit.NICKEL_INGOT.get()), getTag("forge:ingots/", CLItemInit.NICKEL_INGOT.get()), Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON);

        alloyRecipes(r, CLBlockInit.ELECTRUM_BLOCK.get(), CLItemInit.ELECTRUM_INGOT.get(), CLItemInit.ELECTRUM_NUGGET.get(), CLItemInit.ELECTRUM_DUST.get(), CLItemInit.ELECTRUM_GEAR.get(), CLItemInit.ELECTRUM_BLEND.get());
        shapelessRecipe(r, CLItemInit.ELECTRUM_BLEND.get(), 2, getTag("forge:ingots/", CLItemInit.SILVER_INGOT.get()), getTag("forge:ingots/", CLItemInit.SILVER_INGOT.get()), Tags.Items.INGOTS_GOLD);

        metalRecipes(r, CLBlockInit.PLATINUM_BLOCK.get(), CLItemInit.PLATINUM_INGOT.get(), CLItemInit.PLATINUM_NUGGET.get(), CLItemInit.PLATINUM_DUST.get(), CLItemInit.PLATINUM_GEAR.get(), CLBlockInit.PLATINUM_ORE.get());

        alloyRecipes(r, CLBlockInit.ENDERIUM_BLOCK.get(), CLItemInit.ENDERIUM_INGOT.get(), CLItemInit.ENDERIUM_NUGGET.get(), CLItemInit.ENDERIUM_DUST.get(), CLItemInit.ENDERIUM_GEAR.get(), CLItemInit.ENDERIUM_BLEND.get());
        shapelessRecipe(r, CLItemInit.ENDERIUM_BLEND.get(), 2, getTag("forge:ingots/", CLItemInit.PLATINUM_INGOT.get()), getTag("forge:ingots/", CLItemInit.SILVER_INGOT.get()), getTag("forge:ingots/", CLItemInit.PLATINUM_INGOT.get()), getTag("forge:ingots/", CLItemInit.TIN_INGOT.get()), getTag("forge:ingots/", CLItemInit.TIN_INGOT.get()));

        alloyRecipes(r, CLBlockInit.SIGNALUM_BLOCK.get(), CLItemInit.SIGNALUM_INGOT.get(), CLItemInit.SIGNALUM_NUGGET.get(), CLItemInit.SIGNALUM_DUST.get(), CLItemInit.SIGNALUM_GEAR.get(), CLItemInit.SIGNALUM_BLEND.get());
        shapelessRecipe(r, CLItemInit.SIGNALUM_BLEND.get(), 4, ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), getTag("forge:ingots/", CLItemInit.TIN_INGOT.get()), getTag("forge:ingots/", CLItemInit.TIN_INGOT.get()), getTag("forge:ingots/", CLItemInit.TIN_INGOT.get()), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE);

        metalRecipes(r, CLBlockInit.TUNGSTEN_BLOCK.get(), CLItemInit.TUNGSTEN_INGOT.get(), CLItemInit.TUNGSTEN_NUGGET.get(), CLItemInit.TUNGSTEN_DUST.get(), CLItemInit.TUNGSTEN_GEAR.get(), CLBlockInit.TUNGSTEN_ORE.get());

        alloyRecipes(r, CLBlockInit.BRONZE_BLOCK.get(), CLItemInit.BRONZE_INGOT.get(), CLItemInit.BRONZE_NUGGET.get(), CLItemInit.BRONZE_DUST.get(), CLItemInit.BRONZE_GEAR.get(), CLItemInit.BRONZE_BLEND.get());
        shapelessRecipe(r, CLItemInit.BRONZE_BLEND.get(), 3, ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), getTag("forge:ingots/", CLItemInit.TIN_INGOT.get()), getTag("forge:ingots/", CLItemInit.TIN_INGOT.get()), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")));

        shaped2x2Recipe(r, CLBlockInit.SAPPHIRE_BLOCK.get(), 1, getTag("forge:shards/", CLItemInit.SAPPHIRE_SHARD.get()));
        dustRecipes(r, CLItemInit.SAPPHIRE_DUST.get(), CLItemInit.SAPPHIRE_SHARD.get(), getTag("forge:shards/", CLItemInit.SAPPHIRE_SHARD.get()));

        gemRecipes(r, CLBlockInit.OPAL_BLOCK.get(), CLItemInit.OPAL.get(), CLItemInit.OPAL_DUST.get(), CLItemInit.OPAL_GEAR.get(), CLBlockInit.OPAL_ORE.get());
        debrisRecipes(r, CLBlockInit.TITANIUM_BLOCK.get(), CLItemInit.TITANIUM_INGOT.get(), CLItemInit.TITANIUM_NUGGET.get(), CLItemInit.TITANIUM_DUST.get(), CLItemInit.TITANIUM_GEAR.get(), CLBlockInit.TITANIUM_ORE.get(), CLItemInit.TITANIUM_SCRAP.get());
        metalRecipes(r, null, CLItemInit.URANIUM_INGOT.get(), CLItemInit.URANIUM_NUGGET.get(), CLItemInit.URANIUM_DUST.get(), null, CLBlockInit.URANIUM_ORE.get());
        metalRecipes(r, CLBlockInit.COBALT_BLOCK.get(), CLItemInit.COBALT_INGOT.get(), CLItemInit.COBALT_NUGGET.get(), CLItemInit.COBALT_DUST.get(), CLItemInit.COBALT_GEAR.get(), CLBlockInit.COBALT_ORE.get());
        metalRecipes(r, CLBlockInit.ZINC_BLOCK.get(), CLItemInit.ZINC_INGOT.get(), CLItemInit.ZINC_NUGGET.get(), CLItemInit.ZINC_DUST.get(), CLItemInit.ZINC_GEAR.get(), CLBlockInit.ZINC_ORE.get());

        alloyRecipes(r, CLBlockInit.BRASS_BLOCK.get(), CLItemInit.BRASS_INGOT.get(), CLItemInit.BRASS_NUGGET.get(), CLItemInit.BRASS_DUST.get(), CLItemInit.BRASS_GEAR.get(), CLItemInit.BRASS_BLEND.get());
        shapelessRecipe(r, CLItemInit.BRASS_BLEND.get(), 4, getTag("forge:ingots/", CLItemInit.ZINC_INGOT.get()), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), getTag("forge:ingots/", CLItemInit.ZINC_INGOT.get()));

        metalRecipes(r, CLBlockInit.CHROMIUM_BLOCK.get(), CLItemInit.CHROMIUM_INGOT.get(), CLItemInit.CHROMIUM_NUGGET.get(), CLItemInit.CHROMIUM_DUST.get(), CLItemInit.CHROMIUM_GEAR.get(), CLBlockInit.CHROMIUM_ORE.get());
        metalRecipes(r, null, CLItemInit.THORIUM_INGOT.get(), CLItemInit.THORIUM_NUGGET.get(), CLItemInit.THORIUM_DUST.get(), null, CLBlockInit.THORIUM_ORE.get());

        smeltingRecipe(r, CLItemInit.SULFUR_DUST.get(), getTag("forge:ores/", CLBlockInit.SULFUR_ORE.get()), 0.5f, 200);
        blastingRecipe(r, CLItemInit.SULFUR_DUST.get(), getTag("forge:ores/", CLBlockInit.SULFUR_ORE.get()), 0.5f, 400);

        smeltingRecipe(r, CLItemInit.SALTPETRE_DUST.get(), getTag("forge:ores/", CLBlockInit.SALTPETRE_ORE.get()), 0.5f, 200);
        blastingRecipe(r, CLItemInit.SALTPETRE_DUST.get(), getTag("forge:ores/", CLBlockInit.SALTPETRE_ORE.get()), 0.5f, 400);

        shapelessRecipe(r, CLItemInit.COAL_NUGGET.get(), 9, ItemTags.COALS, 1);
        shapelessRecipe(r, Items.COAL, 1, getTag("forge:nuggets/", CLItemInit.COAL_NUGGET.get()), 9);

        smeltingRecipe(r, CLItemInit.SILICON.get(), getTag("forge:dusts/silica"), 0.5f, 200);
        blastingRecipe(r, CLItemInit.SILICON.get(), getTag("forge:dusts/silica"), 0.5f, 400);

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
                .define('s', ItemTags.createOptional(new ResourceLocation("forge:sticks")))
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(r);

        shapelessRecipe(r, Items.GUNPOWDER, 4, getTag("forge:dusts/", CLItemInit.SALTPETRE_DUST.get()), getTag("forge:dusts/", CLItemInit.COAL_DUST.get()), getTag("forge:dusts/", CLItemInit.COAL_DUST.get()), getTag("forge:dusts/", CLItemInit.SULFUR_DUST.get()), getTag("forge:dusts/", CLItemInit.SALTPETRE_DUST.get()));

        ShapedRecipeBuilder.shaped(CLItemInit.PYROTHEUM_DUST.get(), 1)
                .pattern("bb ")
                .pattern("rs ")
                .pattern("   ")
                .define('b', ItemTags.createOptional(new ResourceLocation("forge:dusts/blaze_powder")))
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('s', getTag("forge:dusts/", CLItemInit.SULFUR_DUST.get()))
                .unlockedBy("has_blaze_powder", has(ItemTags.createOptional(new ResourceLocation("forge:dusts/blaze_powder"))))
                .save(r);

        shapelessRecipe(r, CLItemInit.WOOD_DUST.get(), 1, getTag("forge:dusts/", CLItemInit.WOOD_DUST.get()), ItemTags.LOGS, ItemTags.createOptional(new ResourceLocation("clib:hammer")));
        dustRecipes(r, CLItemInit.COAL_DUST.get(), Items.COAL, ItemTags.COALS);
        dustRecipes(r, CLItemInit.IRON_DUST.get(), Items.IRON_INGOT, Tags.Items.INGOTS_IRON);
        dustRecipes(r, CLItemInit.COPPER_DUST.get(), Items.COPPER_INGOT, ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")));//TODO
        dustRecipes(r, CLItemInit.GOLD_DUST.get(), Items.GOLD_INGOT, Tags.Items.INGOTS_GOLD);
        dustRecipes(r, CLItemInit.LAPIS_DUST.get(), Items.LAPIS_LAZULI, Tags.Items.GEMS_LAPIS);
        dustRecipes(r, CLItemInit.QUARTZ_DUST.get(), Items.QUARTZ, Tags.Items.GEMS_QUARTZ);
        dustRecipes(r, CLItemInit.AMETHYST_DUST.get(), Items.AMETHYST_SHARD, ItemTags.createOptional(new ResourceLocation("forge:gems/amethyst")));
        dustRecipes(r, CLItemInit.DIAMOND_DUST.get(), Items.DIAMOND, Tags.Items.GEMS_DIAMOND);
        dustRecipes(r, CLItemInit.EMERALD_DUST.get(), Items.EMERALD, Tags.Items.GEMS_EMERALD);
        dustRecipes(r, CLItemInit.NETHERITE_DUST.get(), Items.NETHERITE_INGOT, Tags.Items.INGOTS_NETHERITE);
        shapelessRecipe(r, CLItemInit.OBSIDIAN_DUST.get(), 1, getTag("forge:dusts/", CLItemInit.OBSIDIAN_DUST.get()), Tags.Items.OBSIDIAN, ItemTags.createOptional(new ResourceLocation("clib:hammer")));

        gearRecipe(r, CLItemInit.WOOD_GEAR.get(), ItemTags.LOGS);
        gearRecipe(r, CLItemInit.STONE_GEAR.get(), Tags.Items.STONE);
        gearRecipe(r, CLItemInit.IRON_GEAR.get(), Tags.Items.INGOTS_IRON);
        gearRecipe(r, CLItemInit.COPPER_GEAR.get(), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")));//TODO
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
        shapelessRecipe(r, CLItemInit.RUBBER.get(), 1, getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), ItemTags.createOptional(new ResourceLocation("clib:tree_tap")));
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
