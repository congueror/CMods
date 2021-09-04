package net.congueror.clib.datagen;

import net.congueror.clib.api.data.RecipeDataGenerator;
import net.congueror.clib.init.BlockInit;
import net.congueror.clib.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RecipeDataGen extends RecipeDataGenerator {
    public RecipeDataGen(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> r) {
        metalRecipes(r, BlockInit.TIN_BLOCK.get(), ItemInit.TIN_INGOT.get(), ItemInit.TIN_NUGGET.get(), ItemInit.TIN_DUST.get(), ItemInit.TIN_GEAR.get(), BlockInit.TIN_ORE.get());

        alloyRecipes(r, BlockInit.STEEL_BLOCK.get(), ItemInit.STEEL_INGOT.get(), ItemInit.STEEL_NUGGET.get(), ItemInit.STEEL_DUST.get(), ItemInit.STEEL_GEAR.get(), ItemInit.STEEL_BLEND.get());
        shapelessRecipe(r, ItemInit.STEEL_BLEND.get(), 2, Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON, ItemTags.COALS);

        metalRecipes(r, BlockInit.ALUMINUM_BLOCK.get(), ItemInit.ALUMINUM_INGOT.get(), ItemInit.ALUMINUM_NUGGET.get(), ItemInit.ALUMINUM_DUST.get(), ItemInit.ALUMINUM_GEAR.get(), BlockInit.ALUMINUM_ORE.get());
        metalRecipes(r, BlockInit.LEAD_BLOCK.get(), ItemInit.LEAD_INGOT.get(), ItemInit.LEAD_NUGGET.get(), ItemInit.LEAD_DUST.get(), ItemInit.LEAD_GEAR.get(), BlockInit.LEAD_ORE.get());
        gemRecipes(r, BlockInit.RUBY_BLOCK.get(), ItemInit.RUBY.get(), ItemInit.RUBY_DUST.get(), ItemInit.RUBY_GEAR.get(), BlockInit.RUBY_ORE.get());
        metalRecipes(r, BlockInit.SILVER_BLOCK.get(), ItemInit.SILVER_INGOT.get(), ItemInit.SILVER_NUGGET.get(), ItemInit.SILVER_DUST.get(), ItemInit.SILVER_GEAR.get(), BlockInit.SILVER_ORE.get());

        alloyRecipes(r, BlockInit.LUMIUM_BLOCK.get(), ItemInit.LUMIUM_INGOT.get(), ItemInit.LUMIUM_NUGGET.get(), ItemInit.LUMIUM_DUST.get(), ItemInit.LUMIUM_GEAR.get(), ItemInit.LUMIUM_BLEND.get());
        shapelessRecipe(r, ItemInit.LUMIUM_BLEND.get(), 2, getTag("forge:ingots/", ItemInit.SILVER_INGOT.get()), getTag("forge:ingots/", ItemInit.SILVER_INGOT.get()), getTag("forge:ingots/", ItemInit.TIN_INGOT.get()), Tags.Items.DUSTS_GLOWSTONE);

        metalRecipes(r, BlockInit.NICKEL_BLOCK.get(), ItemInit.NICKEL_INGOT.get(), ItemInit.NICKEL_NUGGET.get(), ItemInit.NICKEL_DUST.get(), ItemInit.NICKEL_GEAR.get(), BlockInit.NICKEL_ORE.get());

        alloyRecipes(r, BlockInit.INVAR_BLOCK.get(), ItemInit.INVAR_INGOT.get(), ItemInit.INVAR_NUGGET.get(), ItemInit.INVAR_DUST.get(), ItemInit.INVAR_GEAR.get(), ItemInit.INVAR_BLEND.get());
        shapelessRecipe(r, ItemInit.INVAR_BLEND.get(), 3, getTag("forge:ingots/", ItemInit.NICKEL_INGOT.get()), getTag("forge:ingots/", ItemInit.NICKEL_INGOT.get()), Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON);

        alloyRecipes(r, BlockInit.ELECTRUM_BLOCK.get(), ItemInit.ELECTRUM_INGOT.get(), ItemInit.ELECTRUM_NUGGET.get(), ItemInit.ELECTRUM_DUST.get(), ItemInit.ELECTRUM_GEAR.get(), ItemInit.ELECTRUM_BLEND.get());
        shapelessRecipe(r, ItemInit.ELECTRUM_BLEND.get(), 2, getTag("forge:ingots/", ItemInit.SILVER_INGOT.get()), getTag("forge:ingots/", ItemInit.SILVER_INGOT.get()), Tags.Items.INGOTS_GOLD);

        metalRecipes(r, BlockInit.PLATINUM_BLOCK.get(), ItemInit.PLATINUM_INGOT.get(), ItemInit.PLATINUM_NUGGET.get(), ItemInit.PLATINUM_DUST.get(), ItemInit.PLATINUM_GEAR.get(), BlockInit.PLATINUM_ORE.get());

        alloyRecipes(r, BlockInit.ENDERIUM_BLOCK.get(), ItemInit.ENDERIUM_INGOT.get(), ItemInit.ENDERIUM_NUGGET.get(), ItemInit.ENDERIUM_DUST.get(), ItemInit.ENDERIUM_GEAR.get(), ItemInit.ENDERIUM_BLEND.get());
        shapelessRecipe(r, ItemInit.ENDERIUM_BLEND.get(), 2, getTag("forge:ingots/", ItemInit.PLATINUM_INGOT.get()), getTag("forge:ingots/", ItemInit.SILVER_INGOT.get()), getTag("forge:ingots/", ItemInit.PLATINUM_INGOT.get()), getTag("forge:ingots/", ItemInit.TIN_INGOT.get()), getTag("forge:ingots/", ItemInit.TIN_INGOT.get()));

        alloyRecipes(r, BlockInit.SIGNALUM_BLOCK.get(), ItemInit.SIGNALUM_INGOT.get(), ItemInit.SIGNALUM_NUGGET.get(), ItemInit.SIGNALUM_DUST.get(), ItemInit.SIGNALUM_GEAR.get(), ItemInit.SIGNALUM_BLEND.get());
        shapelessRecipe(r, ItemInit.SIGNALUM_BLEND.get(), 4, ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), getTag("forge:ingots/", ItemInit.TIN_INGOT.get()), getTag("forge:ingots/", ItemInit.TIN_INGOT.get()), getTag("forge:ingots/", ItemInit.TIN_INGOT.get()), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE, Tags.Items.DUSTS_REDSTONE);

        metalRecipes(r, BlockInit.TUNGSTEN_BLOCK.get(), ItemInit.TUNGSTEN_INGOT.get(), ItemInit.TUNGSTEN_NUGGET.get(), ItemInit.TUNGSTEN_DUST.get(), ItemInit.TUNGSTEN_GEAR.get(), BlockInit.TUNGSTEN_ORE.get());

        alloyRecipes(r, BlockInit.BRONZE_BLOCK.get(), ItemInit.BRONZE_INGOT.get(), ItemInit.BRONZE_NUGGET.get(), ItemInit.BRONZE_DUST.get(), ItemInit.BRONZE_GEAR.get(), ItemInit.BRONZE_BLEND.get());
        shapelessRecipe(r, ItemInit.BRONZE_BLEND.get(), 3, ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), getTag("forge:ingots/", ItemInit.TIN_INGOT.get()), getTag("forge:ingots/", ItemInit.TIN_INGOT.get()), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")));

        shaped2x2Recipe(r, BlockInit.SAPPHIRE_BLOCK.get(), 1, getTag("forge:shards/", ItemInit.SAPPHIRE_SHARD.get()));
        dustRecipes(r, ItemInit.SAPPHIRE_DUST.get(), ItemInit.SAPPHIRE_SHARD.get(), getTag("forge:shards/", ItemInit.SAPPHIRE_SHARD.get()));

        gemRecipes(r, BlockInit.OPAL_BLOCK.get(), ItemInit.OPAL.get(), ItemInit.OPAL_DUST.get(), ItemInit.OPAL_GEAR.get(), BlockInit.OPAL_ORE.get());
        debrisRecipes(r, BlockInit.TITANIUM_BLOCK.get(), ItemInit.TITANIUM_INGOT.get(), ItemInit.TITANIUM_NUGGET.get(), ItemInit.TITANIUM_DUST.get(), ItemInit.TITANIUM_GEAR.get(), BlockInit.TITANIUM_ORE.get(), ItemInit.TITANIUM_SCRAP.get());
        metalRecipes(r, null, ItemInit.URANIUM_INGOT.get(), ItemInit.URANIUM_NUGGET.get(), ItemInit.URANIUM_DUST.get(), null, BlockInit.URANIUM_ORE.get());
        metalRecipes(r, BlockInit.COBALT_BLOCK.get(), ItemInit.COBALT_INGOT.get(), ItemInit.COBALT_NUGGET.get(), ItemInit.COBALT_DUST.get(), ItemInit.COBALT_GEAR.get(), BlockInit.COBALT_ORE.get());
        metalRecipes(r, BlockInit.ZINC_BLOCK.get(), ItemInit.ZINC_INGOT.get(), ItemInit.ZINC_NUGGET.get(), ItemInit.ZINC_DUST.get(), ItemInit.ZINC_GEAR.get(), BlockInit.ZINC_ORE.get());

        alloyRecipes(r, BlockInit.BRASS_BLOCK.get(), ItemInit.BRASS_INGOT.get(), ItemInit.BRASS_NUGGET.get(), ItemInit.BRASS_DUST.get(), ItemInit.BRASS_GEAR.get(), ItemInit.BRASS_BLEND.get());
        shapelessRecipe(r, ItemInit.BRASS_BLEND.get(), 4, getTag("forge:ingots/", ItemInit.ZINC_INGOT.get()), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")), getTag("forge:ingots/", ItemInit.ZINC_INGOT.get()));

        metalRecipes(r, BlockInit.CHROMIUM_BLOCK.get(), ItemInit.CHROMIUM_INGOT.get(), ItemInit.CHROMIUM_NUGGET.get(), ItemInit.CHROMIUM_DUST.get(), ItemInit.CHROMIUM_GEAR.get(), BlockInit.CHROMIUM_ORE.get());
        metalRecipes(r, null, ItemInit.THORIUM_INGOT.get(), ItemInit.THORIUM_NUGGET.get(), ItemInit.THORIUM_DUST.get(), null, BlockInit.THORIUM_ORE.get());

        smeltingRecipe(r, ItemInit.SULFUR_DUST.get(), getTag("forge:ores/", BlockInit.SULFUR_ORE.get()), 0.5f, 200);
        blastingRecipe(r, ItemInit.SULFUR_DUST.get(), getTag("forge:ores/", BlockInit.SULFUR_ORE.get()), 0.5f, 400);

        smeltingRecipe(r, ItemInit.SALTPETRE_DUST.get(), getTag("forge:ores/", BlockInit.SALTPETRE_ORE.get()), 0.5f, 200);
        blastingRecipe(r, ItemInit.SALTPETRE_DUST.get(), getTag("forge:ores/", BlockInit.SALTPETRE_ORE.get()), 0.5f, 400);

        shapelessRecipe(r, ItemInit.COAL_NUGGET.get(), 9, ItemTags.COALS, 1);
        shapelessRecipe(r, Items.COAL, 1, getTag("forge:nuggets/", ItemInit.COAL_NUGGET.get()), 9);

        smeltingRecipe(r, ItemInit.SILICON.get(), getTag("forge:dusts/silica"), 0.5f, 200);
        blastingRecipe(r, ItemInit.SILICON.get(), getTag("forge:dusts/silica"), 0.5f, 400);

        ShapedRecipeBuilder.shaped(BlockInit.SALT_BLOCK.get(), 1)
                .pattern("aa ")
                .pattern("aa ")
                .pattern("   ")
                .define('a', getTag("forge:dusts/", ItemInit.SALT.get()))
                .unlockedBy("has_salt", has(getTag("forge:dusts/", ItemInit.SALT.get())))
                .save(r);

        ShapedRecipeBuilder.shaped(ItemInit.IRON_HAMMER.get(), 1)
                .pattern(" aa")
                .pattern(" sa")
                .pattern("s  ")
                .define('a', Tags.Items.INGOTS_IRON)
                .define('s', ItemTags.createOptional(new ResourceLocation("forge:sticks")))
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(r);

        shapelessRecipe(r, Items.GUNPOWDER, 4, getTag("forge:dusts/", ItemInit.SALTPETRE_DUST.get()), getTag("forge:dusts/", ItemInit.COAL_DUST.get()), getTag("forge:dusts/", ItemInit.COAL_DUST.get()), getTag("forge:dusts/", ItemInit.SULFUR_DUST.get()), getTag("forge:dusts/", ItemInit.SALTPETRE_DUST.get()));

        ShapedRecipeBuilder.shaped(ItemInit.PYROTHEUM_DUST.get(), 1)
                .pattern("bb ")
                .pattern("rs ")
                .pattern("   ")
                .define('b', ItemTags.createOptional(new ResourceLocation("forge:dusts/blaze_powder")))
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('s', getTag("forge:dusts/", ItemInit.SULFUR_DUST.get()))
                .unlockedBy("has_blaze_powder", has(ItemTags.createOptional(new ResourceLocation("forge:dusts/blaze_powder"))))
                .save(r);

        shapelessRecipe(r, ItemInit.WOOD_DUST.get(), 1, getTag("forge:dusts/", ItemInit.WOOD_DUST.get()), ItemTags.LOGS, ItemTags.createOptional(new ResourceLocation("clib:hammer")));
        dustRecipes(r, ItemInit.COAL_DUST.get(), Items.COAL, ItemTags.COALS);
        dustRecipes(r, ItemInit.IRON_DUST.get(), Items.IRON_INGOT, Tags.Items.INGOTS_IRON);
        dustRecipes(r, ItemInit.COPPER_DUST.get(), Items.COPPER_INGOT, ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")));//TODO
        dustRecipes(r, ItemInit.GOLD_DUST.get(), Items.GOLD_INGOT, Tags.Items.INGOTS_GOLD);
        dustRecipes(r, ItemInit.LAPIS_DUST.get(), Items.LAPIS_LAZULI, Tags.Items.GEMS_LAPIS);
        dustRecipes(r, ItemInit.QUARTZ_DUST.get(), Items.QUARTZ, Tags.Items.GEMS_QUARTZ);
        dustRecipes(r, ItemInit.AMETHYST_DUST.get(), Items.AMETHYST_SHARD, ItemTags.createOptional(new ResourceLocation("forge:gems/amethyst")));
        dustRecipes(r, ItemInit.DIAMOND_DUST.get(), Items.DIAMOND, Tags.Items.GEMS_DIAMOND);
        dustRecipes(r, ItemInit.EMERALD_DUST.get(), Items.EMERALD, Tags.Items.GEMS_EMERALD);
        dustRecipes(r, ItemInit.NETHERITE_DUST.get(), Items.NETHERITE_INGOT, Tags.Items.INGOTS_NETHERITE);
        shapelessRecipe(r, ItemInit.OBSIDIAN_DUST.get(), 1, getTag("forge:dusts/", ItemInit.OBSIDIAN_DUST.get()), Tags.Items.OBSIDIAN, ItemTags.createOptional(new ResourceLocation("clib:hammer")));

        gearRecipe(r, ItemInit.WOOD_GEAR.get(), ItemTags.LOGS);
        gearRecipe(r, ItemInit.STONE_GEAR.get(), Tags.Items.STONE);
        gearRecipe(r, ItemInit.IRON_GEAR.get(), Tags.Items.INGOTS_IRON);
        gearRecipe(r, ItemInit.COPPER_GEAR.get(), ItemTags.createOptional(new ResourceLocation("forge:ingots/copper")));//TODO
        gearRecipe(r, ItemInit.GOLD_GEAR.get(), Tags.Items.INGOTS_GOLD);
        gearRecipe(r, ItemInit.LAPIS_GEAR.get(), Tags.Items.GEMS_LAPIS);
        gearRecipe(r, ItemInit.QUARTZ_GEAR.get(), Tags.Items.GEMS_QUARTZ);
        gearRecipe(r, ItemInit.DIAMOND_GEAR.get(), Tags.Items.GEMS_DIAMOND);
        gearRecipe(r, ItemInit.EMERALD_GEAR.get(), Tags.Items.GEMS_EMERALD);
        gearRecipe(r, ItemInit.NETHERITE_GEAR.get(), Tags.Items.INGOTS_NETHERITE);

        ShapedRecipeBuilder.shaped(ItemInit.TREE_TAP.get())
                .pattern(" a ")
                .pattern("aaa")
                .pattern("a  ")
                .define('a', ItemTags.PLANKS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(r);
        shapelessRecipe(r, ItemInit.RUBBER.get(), 1, getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), getTag("forge:rubber_logs"), ItemTags.createOptional(new ResourceLocation("clib:tree_tap")));
        shapelessRecipe(r, BlockInit.RUBBER_PLANKS.get(), 4, getTag("forge:rubber_logs"), 1);
        ShapedRecipeBuilder.shaped(BlockInit.RUBBER_WOOD.get(), 3)
                .pattern("aa ")
                .pattern("aa ")
                .pattern("   ")
                .define('a', BlockInit.RUBBER_LOG.get())
                .unlockedBy("has_rubber_log", has(BlockInit.RUBBER_LOG.get()))
                .save(r);
        ShapedRecipeBuilder.shaped(BlockInit.RUBBER_STRIPPED_WOOD.get(), 3)
                .pattern("aa ")
                .pattern("aa ")
                .pattern("   ")
                .define('a', BlockInit.RUBBER_STRIPPED_LOG.get())
                .unlockedBy("has_stripped_rubber_log", has(BlockInit.RUBBER_STRIPPED_LOG.get()))
                .save(r);
    }
}
