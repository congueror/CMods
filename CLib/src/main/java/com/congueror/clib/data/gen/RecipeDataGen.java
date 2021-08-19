package com.congueror.clib.data.gen;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.ItemInit;
import com.congueror.clib.util.CLTags;
import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipeDataGen extends RecipeProvider {
    public RecipeDataGen(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected void registerMetal(Consumer<IFinishedRecipe> recipe
            , Item ingot
            , Tags.IOptionalNamedTag<Item> ingotTag
            , Item nugget
            , Tags.IOptionalNamedTag<Item> nuggetTag
            , Item dust
            , Block block
            , Tags.IOptionalNamedTag<Item> blockTag
            , Item ore
            , Item gear) {
        //block
        ShapedRecipeBuilder.shapedRecipe(block, 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', ingotTag)
                .addCriterion(ingot.getItem().toString(), hasItem(ingotTag))
                .build(recipe);
        //ingot_block
        ShapelessRecipeBuilder.shapelessRecipe(ingot, 9)
                .addIngredient(blockTag)
                .addCriterion(block.asItem().toString(), hasItem(blockTag))
                .build(recipe, new ResourceLocation(CLib.MODID, ingot.getItem().toString() + "_block"));
        //nugget
        ShapelessRecipeBuilder.shapelessRecipe(nugget, 9)
                .addIngredient(ingotTag)
                .addCriterion(ingot.getItem().toString(), hasItem(ingotTag))
                .build(recipe);
        //ingot_nuggets
        ShapedRecipeBuilder.shapedRecipe(ingot, 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', nuggetTag)
                .addCriterion(nugget.getItem().toString(), hasItem(nugget))
                .build(recipe, new ResourceLocation(CLib.MODID, ingot.getItem().toString() + "_nuggets"));
        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ore), ingot, 0.7f, 400)
                .addCriterion(ore.getItem().toString(), hasItem(ore))
                .build(recipe, new ResourceLocation(CLib.MODID, ingot.getItem().toString() + "_blasting"));
        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ore), ingot, 0.7f, 200)
                .addCriterion(ore.getItem().toString(), hasItem(ore))
                .build(recipe, new ResourceLocation(CLib.MODID, ingot.getItem().toString() + "_smelting"));
        registerDust(recipe, dust, ingot, ingotTag);
        //gear
        if (gear != null) {
            ShapedRecipeBuilder.shapedRecipe(gear, 1)
                    .patternLine(" a ")
                    .patternLine("a a")
                    .patternLine(" a ")
                    .key('a', ingotTag)
                    .addCriterion(ingot.getItem().toString(), hasItem(ingotTag))
                    .build(recipe);
        }
    }

    protected void registerStone(Consumer<IFinishedRecipe> recipe
            , Item gem
            , Tags.IOptionalNamedTag<Item> gemTag
            , Item dust
            , Block block
            , Tags.IOptionalNamedTag<Item> blockTag
            , Item ore
            , Item gear) {
        //block
        ShapedRecipeBuilder.shapedRecipe(block, 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', gemTag)
                .addCriterion(gem.getItem().toString(), hasItem(gemTag))
                .build(recipe);
        //ingot_block
        ShapelessRecipeBuilder.shapelessRecipe(gem, 9)
                .addIngredient(blockTag)
                .addCriterion(block.asItem().toString(), hasItem(blockTag))
                .build(recipe, new ResourceLocation(CLib.MODID, gem.getItem().toString() + "_gem_block"));
        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ore), gem, 0.7f, 400)
                .addCriterion(ore.getItem().toString(), hasItem(ore))
                .build(recipe, new ResourceLocation(CLib.MODID, gem.getItem().toString() + "_blasting"));
        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ore), gem, 0.7f, 200)
                .addCriterion(ore.getItem().toString(), hasItem(ore))
                .build(recipe, new ResourceLocation(CLib.MODID, gem.getItem().toString() + "_smelting"));
        registerDust(recipe, dust, gem, gemTag);
        //gear
        ShapedRecipeBuilder.shapedRecipe(gear, 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', gemTag)
                .addCriterion(gem.getItem().toString(), hasItem(gemTag))
                .build(recipe);
    }

    protected void registerDust(Consumer<IFinishedRecipe> recipe, Item dust, Item material, ITag<Item> material_tag) {
        //dust
        ShapelessRecipeBuilder.shapelessRecipe(dust, 1)
                .addIngredient(material_tag)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion(material.getItem().toString(), hasItem(material))
                .build(recipe);
        //dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(dust), material, 0.7f, 200)
                .addCriterion(dust.getItem().toString(), hasItem(dust))
                .build(recipe, new ResourceLocation(CLib.MODID, dust.getItem().toString() + "_smelting"));
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> recipe) {
        //TIN
        registerMetal(recipe
                , ItemInit.TIN_INGOT.get()
                , CLTags.INGOTS_TIN.getItemTag()
                , ItemInit.TIN_NUGGET.get()
                , CLTags.NUGGETS_TIN.getItemTag()
                , ItemInit.TIN_DUST.get()
                , BlockInit.TIN_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_TIN.getItemTag()
                , BlockInit.TIN_ORE.get().asItem()
                , ItemInit.TIN_GEAR.get());

        //STEEL
        registerMetal(recipe
                , ItemInit.STEEL_INGOT.get()
                , CLTags.INGOTS_STEEL.getItemTag()
                , ItemInit.STEEL_NUGGET.get()
                , CLTags.NUGGETS_STEEL.getItemTag()
                , ItemInit.STEEL_DUST.get()
                , BlockInit.STEEL_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_STEEL.getItemTag()
                , ItemInit.STEEL_BLEND.get()
                , ItemInit.STEEL_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.STEEL_BLEND.get(), 2)
                .patternLine("ci ")
                .patternLine("i  ")
                .patternLine("   ")
                .key('i', Tags.Items.INGOTS_IRON)
                .key('c', Items.COAL)
                .addCriterion("iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(recipe);

        //ALUMINUM
        registerMetal(recipe
                , ItemInit.ALUMINUM_INGOT.get()
                , CLTags.INGOTS_ALUMINUM.getItemTag()
                , ItemInit.ALUMINUM_NUGGET.get()
                , CLTags.NUGGETS_ALUMINUM.getItemTag()
                , ItemInit.ALUMINUM_DUST.get()
                , BlockInit.ALUMINUM_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_ALUMINUM.getItemTag()
                , BlockInit.ALUMINUM_ORE.get().asItem()
                , ItemInit.ALUMINUM_GEAR.get());

        //LEAD
        registerMetal(recipe
                , ItemInit.LEAD_INGOT.get()
                , CLTags.INGOTS_LEAD.getItemTag()
                , ItemInit.LEAD_NUGGET.get()
                , CLTags.NUGGETS_LEAD.getItemTag()
                , ItemInit.LEAD_DUST.get()
                , BlockInit.LEAD_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_LEAD.getItemTag()
                , BlockInit.LEAD_ORE.get().asItem()
                , ItemInit.LEAD_GEAR.get());

        //COPPER
        registerMetal(recipe
                , ItemInit.COPPER_INGOT.get()
                , CLTags.INGOTS_COPPER.getItemTag()
                , ItemInit.COPPER_NUGGET.get()
                , CLTags.NUGGETS_COPPER.getItemTag()
                , ItemInit.COPPER_DUST.get()
                , BlockInit.COPPER_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_COPPER.getItemTag()
                , BlockInit.COPPER_ORE.get().asItem()
                , ItemInit.COPPER_GEAR.get());

        //RUBY
        registerStone(recipe
                , ItemInit.RUBY.get()
                , CLTags.GEMS_RUBY.getItemTag()
                , ItemInit.RUBY_DUST.get()
                , BlockInit.RUBY_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_RUBY.getItemTag()
                , BlockInit.RUBY_ORE.get().asItem()
                , ItemInit.RUBY_GEAR.get());

        //SILVER
        registerMetal(recipe
                , ItemInit.SILVER_INGOT.get()
                , CLTags.INGOTS_SILVER.getItemTag()
                , ItemInit.SILVER_NUGGET.get()
                , CLTags.NUGGETS_SILVER.getItemTag()
                , ItemInit.SILVER_DUST.get()
                , BlockInit.SILVER_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_SILVER.getItemTag()
                , BlockInit.SILVER_ORE.get().asItem()
                , ItemInit.SILVER_GEAR.get());

        //LUMIUM
        registerMetal(recipe
                , ItemInit.LUMIUM_INGOT.get()
                , CLTags.INGOTS_LUMIUM.getItemTag()
                , ItemInit.LUMIUM_NUGGET.get()
                , CLTags.NUGGETS_LUMIUM.getItemTag()
                , ItemInit.LUMIUM_DUST.get()
                , BlockInit.LUMIUM_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_LUMIUM.getItemTag()
                , ItemInit.LUMIUM_BLEND.get()
                , ItemInit.LUMIUM_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.LUMIUM_BLEND.get(), 2)
                .patternLine("cg ")
                .patternLine("i  ")
                .patternLine("   ")
                .key('i', Tags.Items.DUSTS_GLOWSTONE)
                .key('c', CLTags.INGOTS_SILVER.getItemTag())
                .key('g', CLTags.INGOTS_TIN.getItemTag())
                .addCriterion("silver_ingot", hasItem(CLTags.INGOTS_SILVER.getItemTag()))
                .build(recipe);

        //NICKEL
        registerMetal(recipe
                , ItemInit.NICKEL_INGOT.get()
                , CLTags.INGOTS_NICKEL.getItemTag()
                , ItemInit.NICKEL_NUGGET.get()
                , CLTags.NUGGETS_NICKEL.getItemTag()
                , ItemInit.NICKEL_DUST.get()
                , BlockInit.NICKEL_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_NICKEL.getItemTag()
                , BlockInit.NICKEL_ORE.get().asItem()
                , ItemInit.NICKEL_GEAR.get());

        //INVAR
        registerMetal(recipe
                , ItemInit.INVAR_INGOT.get()
                , CLTags.INGOTS_INVAR.getItemTag()
                , ItemInit.INVAR_NUGGET.get()
                , CLTags.NUGGETS_INVAR.getItemTag()
                , ItemInit.INVAR_DUST.get()
                , BlockInit.INVAR_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_INVAR.getItemTag()
                , ItemInit.INVAR_BLEND.get()
                , ItemInit.INVAR_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.INVAR_BLEND.get(), 2)
                .patternLine("ci ")
                .patternLine("i  ")
                .patternLine("   ")
                .key('i', Tags.Items.INGOTS_IRON)
                .key('c', CLTags.INGOTS_NICKEL.getItemTag())
                .addCriterion("nickel_ingot", hasItem(CLTags.INGOTS_NICKEL.getItemTag()))
                .build(recipe);

        //ELECTRUM
        registerMetal(recipe
                , ItemInit.ELECTRUM_INGOT.get()
                , CLTags.INGOTS_ELECTRUM.getItemTag()
                , ItemInit.ELECTRUM_NUGGET.get()
                , CLTags.NUGGETS_ELECTRUM.getItemTag()
                , ItemInit.ELECTRUM_DUST.get()
                , BlockInit.ELECTRUM_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_ELECTRUM.getItemTag()
                , ItemInit.ELECTRUM_BLEND.get()
                , ItemInit.ELECTRUM_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.ELECTRUM_BLEND.get(), 2)
                .patternLine(" i ")
                .patternLine("c  ")
                .patternLine("   ")
                .key('i', Tags.Items.INGOTS_GOLD)
                .key('c', CLTags.INGOTS_SILVER.getItemTag())
                .addCriterion("silver_ingot", hasItem(CLTags.INGOTS_SILVER.getItemTag()))
                .build(recipe);

        //PLATINUM
        registerMetal(recipe
                , ItemInit.PLATINUM_INGOT.get()
                , CLTags.INGOTS_PLATINUM.getItemTag()
                , ItemInit.PLATINUM_NUGGET.get()
                , CLTags.NUGGETS_PLATINUM.getItemTag()
                , ItemInit.PLATINUM_DUST.get()
                , BlockInit.PLATINUM_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_PLATINUM.getItemTag()
                , BlockInit.PLATINUM_ORE.get().asItem()
                , ItemInit.PLATINUM_GEAR.get());

        //ENDERIUM
        registerMetal(recipe
                , ItemInit.ENDERIUM_INGOT.get()
                , CLTags.INGOTS_ENDERIUM.getItemTag()
                , ItemInit.ENDERIUM_NUGGET.get()
                , CLTags.NUGGETS_ENDERIUM.getItemTag()
                , ItemInit.ENDERIUM_DUST.get()
                , BlockInit.ENDERIUM_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_ENDERIUM.getItemTag()
                , ItemInit.ENDERIUM_BLEND.get()
                , ItemInit.ENDERIUM_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.ENDERIUM_BLEND.get(), 1)
                .patternLine("ii ")
                .patternLine("cp ")
                .patternLine("   ")
                .key('i', CLTags.INGOTS_TIN.getItemTag())
                .key('c', CLTags.INGOTS_SILVER.getItemTag())
                .key('p', CLTags.INGOTS_PLATINUM.getItemTag())
                .addCriterion("platinum_ingot", hasItem(CLTags.INGOTS_PLATINUM.getItemTag()))
                .build(recipe);

        //SIGNALUM
        registerMetal(recipe
                , ItemInit.SIGNALUM_INGOT.get()
                , CLTags.INGOTS_SIGNALUM.getItemTag()
                , ItemInit.SIGNALUM_NUGGET.get()
                , CLTags.NUGGETS_SIGNALUM.getItemTag()
                , ItemInit.SIGNALUM_DUST.get()
                , BlockInit.SIGNALUM_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_SIGNALUM.getItemTag()
                , ItemInit.SIGNALUM_BLEND.get()
                , ItemInit.SIGNALUM_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.SIGNALUM_BLEND.get(), 2)
                .patternLine("ipi")
                .patternLine("cic")
                .patternLine("ccc")
                .key('i', CLTags.INGOTS_COPPER.getItemTag())
                .key('c', Tags.Items.DUSTS_REDSTONE)
                .key('p', CLTags.INGOTS_SILVER.getItemTag())
                .addCriterion("silver_ingot", hasItem(CLTags.INGOTS_SILVER.getItemTag()))
                .build(recipe);

        //TUNGSTEN
        registerMetal(recipe
                , ItemInit.TUNGSTEN_INGOT.get()
                , CLTags.INGOTS_TUNGSTEN.getItemTag()
                , ItemInit.TUNGSTEN_NUGGET.get()
                , CLTags.NUGGETS_TUNGSTEN.getItemTag()
                , ItemInit.TUNGSTEN_DUST.get()
                , BlockInit.TUNGSTEN_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_TUNGSTEN.getItemTag()
                , BlockInit.TUNGSTEN_ORE.get().asItem()
                , ItemInit.TUNGSTEN_GEAR.get());

        //BRONZE
        registerMetal(recipe
                , ItemInit.BRONZE_INGOT.get()
                , CLTags.INGOTS_BRONZE.getItemTag()
                , ItemInit.BRONZE_NUGGET.get()
                , CLTags.NUGGETS_BRONZE.getItemTag()
                , ItemInit.BRONZE_DUST.get()
                , BlockInit.BRONZE_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_BRONZE.getItemTag()
                , ItemInit.BRONZE_BLEND.get()
                , ItemInit.BRONZE_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.BRONZE_BLEND.get(), 2)
                .patternLine("ip ")
                .patternLine("p  ")
                .patternLine("   ")
                .key('i', CLTags.INGOTS_COPPER.getItemTag())
                .key('p', CLTags.INGOTS_TIN.getItemTag())
                .addCriterion("copper_ingot", hasItem(CLTags.INGOTS_COPPER.getItemTag()))
                .build(recipe);

        //AMETHYST
        registerStone(recipe
                , ItemInit.AMETHYST.get()
                , CLTags.GEMS_AMETHYST.getItemTag()
                , ItemInit.AMETHYST_DUST.get()
                , BlockInit.AMETHYST_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_AMETHYST.getItemTag()
                , BlockInit.AMETHYST_ORE.get().asItem()
                , ItemInit.AMETHYST_GEAR.get());

        //SAPPHIRE
        registerStone(recipe
                , ItemInit.SAPPHIRE.get()
                , CLTags.GEMS_SAPPHIRE.getItemTag()
                , ItemInit.SAPPHIRE_DUST.get()
                , BlockInit.SAPPHIRE_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_SAPPHIRE.getItemTag()
                , BlockInit.SAPPHIRE_ORE.get().asItem()
                , ItemInit.SAPPHIRE_GEAR.get());

        //OPAL
        registerStone(recipe
                , ItemInit.OPAL.get()
                , CLTags.GEMS_OPAL.getItemTag()
                , ItemInit.OPAL_DUST.get()
                , BlockInit.OPAL_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_OPAL.getItemTag()
                , BlockInit.OPAL_ORE.get().asItem()
                , ItemInit.OPAL_GEAR.get());

        //====================================================|TITANIUM|=====================================================================================
        //ingot
        ShapedRecipeBuilder.shapedRecipe(ItemInit.TITANIUM_INGOT.get(), 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', ItemInit.TITANIUM_SCRAP.get())
                .addCriterion("titanium_scrap", hasItem(ItemInit.TITANIUM_SCRAP.get()))
                .build(recipe);

        //block
        ShapedRecipeBuilder.shapedRecipe(BlockInit.TITANIUM_BLOCK.get(), 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', ItemInit.TITANIUM_INGOT.get())
                .addCriterion("titanium_ingot", hasItem(ItemInit.TITANIUM_INGOT.get()))
                .build(recipe);

        //ingot_block
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.TITANIUM_INGOT.get(), 9)
                .addIngredient(BlockInit.TITANIUM_BLOCK.get())
                .addCriterion("titanium_block", hasItem(BlockInit.TITANIUM_BLOCK.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "titanium_ingot_block"));

        //nugget
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.TITANIUM_NUGGET.get(), 9)
                .addIngredient(ItemInit.TITANIUM_INGOT.get())
                .addCriterion("titanium_ingot", hasItem(ItemInit.TITANIUM_INGOT.get()))
                .build(recipe);

        //ingot_nuggets
        ShapedRecipeBuilder.shapedRecipe(ItemInit.TITANIUM_INGOT.get(), 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', ItemInit.TITANIUM_NUGGET.get())
                .addCriterion("titanium_nugget", hasItem(ItemInit.TITANIUM_NUGGET.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "titanium_ingot_nuggets"));

        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.TITANIUM_ORE.get()), ItemInit.TITANIUM_SCRAP.get(), 6.9f, 800)
                .addCriterion("titanium_ore", hasItem(BlockInit.TITANIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "titanium_ingot_blasting"));

        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.TITANIUM_ORE.get()), ItemInit.TITANIUM_SCRAP.get(), 6.9f, 700)
                .addCriterion("titanium_ore", hasItem(BlockInit.TITANIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "titanium_ingot_smelting"));

        registerDust(recipe, ItemInit.TITANIUM_DUST.get(), ItemInit.TITANIUM_INGOT.get(), CLTags.INGOTS_TITANIUM.getItemTag());

        ShapedRecipeBuilder.shapedRecipe(ItemInit.TITANIUM_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', ItemInit.TITANIUM_INGOT.get())
                .addCriterion("titanium_ingot", hasItem(ItemInit.TITANIUM_INGOT.get()))
                .build(recipe);
        //====================================================|TITANIUM END|=================================================================================

        //====================================================|URANIUM|======================================================================================
        //nugget
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.URANIUM_NUGGET.get(), 9)
                .addIngredient(CLTags.INGOTS_URANIUM.getItemTag())
                .addCriterion("uranium_ingot", hasItem(CLTags.INGOTS_URANIUM.getItemTag()))
                .build(recipe);

        //ingot_nuggets
        ShapedRecipeBuilder.shapedRecipe(ItemInit.URANIUM_INGOT.get(), 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', CLTags.NUGGETS_URANIUM.getItemTag())
                .addCriterion("uranium_nugget", hasItem(CLTags.NUGGETS_URANIUM.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "uranium_ingot_nuggets"));

        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.URANIUM_ORE.get()), ItemInit.URANIUM_INGOT.get(), 0.7f, 400)
                .addCriterion("uranium_ore", hasItem(BlockInit.URANIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "uranium_ingot_blasting"));

        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.URANIUM_ORE.get()), ItemInit.URANIUM_INGOT.get(), 0.7f, 200)
                .addCriterion("uranium_ore", hasItem(BlockInit.URANIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "uranium_ingot_smelting"));

        registerDust(recipe, ItemInit.URANIUM_DUST.get(), ItemInit.URANIUM_INGOT.get(), CLTags.INGOTS_URANIUM.getItemTag());

        //====================================================|URANIUM END|==================================================================================

        //COBALT
        registerMetal(recipe
                , ItemInit.COBALT_INGOT.get()
                , CLTags.INGOTS_COBALT.getItemTag()
                , ItemInit.COBALT_NUGGET.get()
                , CLTags.NUGGETS_COBALT.getItemTag()
                , ItemInit.COBALT_DUST.get()
                , BlockInit.COBALT_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_COBALT.getItemTag()
                , BlockInit.COBALT_ORE.get().asItem()
                , ItemInit.COBALT_GEAR.get());

        //ZINC
        registerMetal(recipe
                , ItemInit.ZINC_INGOT.get()
                , CLTags.INGOTS_ZINC.getItemTag()
                , ItemInit.ZINC_NUGGET.get()
                , CLTags.NUGGETS_ZINC.getItemTag()
                , ItemInit.ZINC_DUST.get()
                , BlockInit.ZINC_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_ZINC.getItemTag()
                , BlockInit.ZINC_ORE.get().asItem()
                , ItemInit.ZINC_GEAR.get());

        //BRASS
        registerMetal(recipe
                , ItemInit.BRASS_INGOT.get()
                , CLTags.INGOTS_BRASS.getItemTag()
                , ItemInit.BRASS_NUGGET.get()
                , CLTags.NUGGETS_BRASS.getItemTag()
                , ItemInit.BRASS_DUST.get()
                , BlockInit.BRASS_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_BRASS.getItemTag()
                , ItemInit.BRASS_BLEND.get()
                , ItemInit.BRASS_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.BRASS_BLEND.get(), 4)
                .patternLine("pp ")
                .patternLine("pi ")
                .patternLine("   ")
                .key('i', CLTags.INGOTS_ZINC.getItemTag())
                .key('p', CLTags.INGOTS_COPPER.getItemTag())
                .addCriterion("zinc_ingot", hasItem(CLTags.INGOTS_ZINC.getItemTag()))
                .build(recipe);

        //CHROMIUM
        registerMetal(recipe
                , ItemInit.CHROMIUM_INGOT.get()
                , CLTags.INGOTS_CHROMIUM.getItemTag()
                , ItemInit.CHROMIUM_NUGGET.get()
                , CLTags.NUGGETS_CHROMIUM.getItemTag()
                , ItemInit.CHROMIUM_DUST.get()
                , BlockInit.CHROMIUM_BLOCK.get()
                , CLTags.STORAGE_BLOCKS_CHROMIUM.getItemTag()
                , BlockInit.CHROMIUM_ORE.get().asItem()
                , ItemInit.CHROMIUM_GEAR.get());

        //====================================================|THORIUM|======================================================================================
        //nugget
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.THORIUM_NUGGET.get(), 9)
                .addIngredient(CLTags.INGOTS_THORIUM.getItemTag())
                .addCriterion("thorium_ingot", hasItem(CLTags.INGOTS_THORIUM.getItemTag()))
                .build(recipe);

        //ingot_nuggets
        ShapedRecipeBuilder.shapedRecipe(ItemInit.THORIUM_INGOT.get(), 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', CLTags.NUGGETS_THORIUM.getItemTag())
                .addCriterion("thorium_nugget", hasItem(CLTags.NUGGETS_THORIUM.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "thorium_ingot_nuggets"));

        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.THORIUM_ORE.get()), ItemInit.THORIUM_INGOT.get(), 0.7f, 400)
                .addCriterion("thorium_ore", hasItem(BlockInit.THORIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "thorium_ingot_blasting"));

        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.THORIUM_ORE.get()), ItemInit.THORIUM_INGOT.get(), 0.7f, 200)
                .addCriterion("thorium_ore", hasItem(BlockInit.THORIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "thorium_ingot_smelting"));

        registerDust(recipe, ItemInit.THORIUM_DUST.get(), ItemInit.THORIUM_INGOT.get(), CLTags.INGOTS_THORIUM.getItemTag());
        //====================================================|THORIUM END|==================================================================================

        //====================================================|SULFUR|======================================================================================
        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.SULFUR_ORE.get()), ItemInit.SULFUR_DUST.get(), 0.7f, 400)
                .addCriterion("sulfur_ore", hasItem(BlockInit.SULFUR_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "sulfur_dust_blasting"));

        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.SULFUR_ORE.get()), ItemInit.SULFUR_DUST.get(), 0.7f, 200)
                .addCriterion("sulfur_ore", hasItem(BlockInit.SULFUR_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "sulfur_dust_smelting"));
        //====================================================|SULFUR END|==================================================================================

        //====================================================|SALTPETRE|======================================================================================
        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.SALTPETRE_ORE.get()), ItemInit.SALTPETRE_DUST.get(), 0.7f, 400)
                .addCriterion("saltpetre_ore", hasItem(BlockInit.SALTPETRE_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "saltpetre_dust_blasting"));

        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.SALTPETRE_ORE.get()), ItemInit.SALTPETRE_DUST.get(), 0.7f, 200)
                .addCriterion("saltpetre_ore", hasItem(BlockInit.SALTPETRE_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "saltpetre_dust_smelting"));
        //====================================================|SALTPETRE END|==================================================================================

        //====================================================|COAL NUGGET|====================================================================================
        //nugget
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.COAL_NUGGET.get(), 9)
                .addIngredient(Items.COAL)
                .addCriterion("coal", hasItem(Items.COAL))
                .build(recipe);

        //ingot_nuggets
        ShapedRecipeBuilder.shapedRecipe(Items.COAL, 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', ItemInit.COAL_NUGGET.get())
                .addCriterion("coal", hasItem(Items.COAL))
                .build(recipe, new ResourceLocation(CLib.MODID, "coal_nuggets"));
        //====================================================|COAL NUGGET END|================================================================================

        //SILICON
        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ItemInit.SILICA.get()), ItemInit.SILICON.get(), 0.7f, 400)
                .addCriterion("silica", hasItem(ItemInit.SILICA.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "silicon_blasting"));

        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.SILICA.get()), ItemInit.SILICON.get(), 0.7f, 200)
                .addCriterion("silica", hasItem(ItemInit.SILICA.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "silicon_smelting"));

        //SALT BLOCK
        ShapedRecipeBuilder.shapedRecipe(BlockInit.SALT_BLOCK.get(), 1)
                .patternLine("pp ")
                .patternLine("pp ")
                .patternLine("   ")
                .key('p', CLTags.DUSTS_SALT.getItemTag())
                .addCriterion("salt", hasItem(CLTags.DUSTS_SALT.getItemTag()))
                .build(recipe);

        //IRON HAMMER
        ShapedRecipeBuilder.shapedRecipe(ItemInit.IRON_HAMMER.get(), 1)
                .patternLine(" aa")
                .patternLine(" sa")
                .patternLine("s  ")
                .key('a', Tags.Items.INGOTS_IRON)
                .key('s', Items.STICK)
                .addCriterion("iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(recipe);

        //GUNPOWDER
        ShapelessRecipeBuilder.shapelessRecipe(Items.GUNPOWDER, 4)
                .addIngredient(CLTags.DUSTS_COAL.getItemTag())
                .addIngredient(CLTags.DUSTS_COAL.getItemTag())
                .addIngredient(CLTags.DUSTS_SULFUR.getItemTag())
                .addIngredient(CLTags.DUSTS_SALTPETRE.getItemTag())
                .addCriterion("saltpetre_dust", hasItem(ItemInit.SALTPETRE_DUST.get()))
                .build(recipe);

        //PYROTHEUM DUST
        ShapedRecipeBuilder.shapedRecipe(ItemInit.PYROTHEUM_DUST.get(), 1)
                .patternLine("bb ")
                .patternLine("rs ")
                .patternLine("   ")
                .key('b', CLTags.DUSTS_BLAZE_POWDER.getItemTag())
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .key('s', CLTags.DUSTS_SULFUR.getItemTag())
                .addCriterion("blaze_powder", hasItem(Items.BLAZE_POWDER))
                .build(recipe);

        //====================================================|VANILLA DUST|================================================================================
        //wood_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.WOOD_DUST.get(), 1)
                .addIngredient(ItemTags.LOGS)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("logs", hasItem(ItemTags.LOGS))
                .build(recipe);
        //coal_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.COAL_DUST.get(), 1)
                .addIngredient(Items.COAL)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("coal", hasItem(Items.COAL))
                .build(recipe);
        //coal_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.COAL_DUST.get()), Items.COAL, 0.7f, 200)
                .addCriterion("coal_dust", hasItem(ItemInit.COAL_DUST.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "coal_dust_smelting"));
        //iron_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.IRON_DUST.get(), 1)
                .addIngredient(Tags.Items.INGOTS_IRON)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(recipe);
        //iron_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.IRON_DUST.get()), Items.IRON_INGOT, 0.7f, 200)
                .addCriterion("iron_dust", hasItem(CLTags.DUSTS_IRON.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "iron_dust_smelting"));
        //gold_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.GOLD_DUST.get(), 1)
                .addIngredient(Tags.Items.INGOTS_GOLD)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("gold_ingot", hasItem(Tags.Items.INGOTS_GOLD))
                .build(recipe);
        //gold_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.GOLD_DUST.get()), Items.GOLD_INGOT, 0.7f, 200)
                .addCriterion("gold_dust", hasItem(CLTags.DUSTS_GOLD.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "gold_dust_smelting"));
        //lapis_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.LAPIS_DUST.get(), 1)
                .addIngredient(Tags.Items.GEMS_LAPIS)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("lapis_lazuli", hasItem(Tags.Items.GEMS_LAPIS))
                .build(recipe);
        //lapis_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.LAPIS_DUST.get()), Items.LAPIS_LAZULI, 0.7f, 200)
                .addCriterion("lapis_dust", hasItem(CLTags.DUSTS_LAPIS.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "lapis_dust_smelting"));
        //quartz_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.QUARTZ_DUST.get(), 1)
                .addIngredient(Tags.Items.GEMS_QUARTZ)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("quartz", hasItem(Tags.Items.GEMS_QUARTZ))
                .build(recipe);
        //quartz_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.QUARTZ_DUST.get()), Items.QUARTZ, 0.7f, 200)
                .addCriterion("quartz", hasItem(CLTags.DUSTS_QUARTZ.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "quartz_dust_smelting"));
        //diamond_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.DIAMOND_DUST.get(), 1)
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("diamond", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(recipe);
        //diamond_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.DIAMOND_DUST.get()), Items.DIAMOND, 0.7f, 200)
                .addCriterion("diamond_dust", hasItem(CLTags.DUSTS_DIAMOND.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "diamond_dust_smelting"));
        //emerald_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.EMERALD_DUST.get(), 1)
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("emerald", hasItem(Tags.Items.GEMS_EMERALD))
                .build(recipe);
        //emerald_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.EMERALD_DUST.get()), Items.EMERALD, 0.7f, 200)
                .addCriterion("emerald", hasItem(CLTags.DUSTS_EMERALD.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "emerald_dust_smelting"));
        //netherite_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.NETHERITE_DUST.get(), 1)
                .addIngredient(Tags.Items.INGOTS_NETHERITE)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("netherite_ingot", hasItem(Tags.Items.INGOTS_NETHERITE))
                .build(recipe);
        //netherite_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.NETHERITE_DUST.get()), Items.NETHERITE_INGOT, 0.7f, 200)
                .addCriterion("netherite_dust", hasItem(CLTags.DUSTS_NETHERITE.getItemTag()))
                .build(recipe, new ResourceLocation(CLib.MODID, "netherite_dust_smelting"));
        //obsidian_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.OBSIDIAN_DUST.get(), 1)
                .addIngredient(Tags.Items.OBSIDIAN)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("obsidian", hasItem(Tags.Items.OBSIDIAN))
                .build(recipe);
        //====================================================|VANILLA DUST END|============================================================================

        //====================================================|VANILLA GEARS|===============================================================================
        ShapedRecipeBuilder.shapedRecipe(ItemInit.WOOD_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', ItemTags.PLANKS)
                .addCriterion("planks", hasItem(ItemTags.PLANKS))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(ItemInit.STONE_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', Tags.Items.STONE)
                .addCriterion("stone", hasItem(Tags.Items.STONE))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(ItemInit.IRON_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', Tags.Items.INGOTS_IRON)
                .addCriterion("iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(ItemInit.GOLD_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', Tags.Items.INGOTS_GOLD)
                .addCriterion("gold_ingot", hasItem(Tags.Items.INGOTS_GOLD))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(ItemInit.LAPIS_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', Tags.Items.GEMS_LAPIS)
                .addCriterion("lapis_lazuli", hasItem(Tags.Items.GEMS_LAPIS))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(ItemInit.QUARTZ_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', Tags.Items.GEMS_QUARTZ)
                .addCriterion("stone", hasItem(Tags.Items.GEMS_QUARTZ))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(ItemInit.DIAMOND_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', Tags.Items.GEMS_DIAMOND)
                .addCriterion("diamond", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(ItemInit.EMERALD_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', Tags.Items.GEMS_EMERALD)
                .addCriterion("emerald", hasItem(Tags.Items.GEMS_EMERALD))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(ItemInit.NETHERITE_GEAR.get(), 1)
                .patternLine(" a ")
                .patternLine("a a")
                .patternLine(" a ")
                .key('a', Tags.Items.INGOTS_NETHERITE)
                .addCriterion("netherite_ingot", hasItem(Tags.Items.INGOTS_NETHERITE))
                .build(recipe);
        //====================================================|VANILLA GEARS END|===========================================================================

        //====================================================|RUBBER|======================================================================================
        ShapedRecipeBuilder.shapedRecipe(ItemInit.TREE_TAP.get(), 1)
                .patternLine(" a ")
                .patternLine("aaa")
                .patternLine("a  ")
                .key('a', ItemTags.PLANKS)
                .addCriterion("planks", hasItem(ItemTags.PLANKS))
                .build(recipe);
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.RUBBER.get(), 1)
                .addIngredient(BlockInit.RUBBER_LOG.get(), 4)
                .addIngredient(ItemInit.TREE_TAP.get())
                .addCriterion("rubber_log", hasItem(CLTags.RUBBER_LOGS.getItemTag()))
                .build(recipe);
        ShapelessRecipeBuilder.shapelessRecipe(BlockInit.RUBBER_PLANKS.get(), 4)
                .addIngredient(CLTags.RUBBER_LOGS.getItemTag())
                .addCriterion("rubber_log", hasItem(CLTags.RUBBER_LOGS.getItemTag()))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(BlockInit.RUBBER_WOOD.get(), 3)
                .patternLine("aa ")
                .patternLine("aa ")
                .patternLine("   ")
                .key('a', BlockInit.RUBBER_LOG.get())
                .addCriterion("rubber_log", hasItem(BlockInit.RUBBER_LOG.get()))
                .build(recipe);
        ShapedRecipeBuilder.shapedRecipe(BlockInit.RUBBER_STRIPPED_WOOD.get(), 3)
                .patternLine("aa ")
                .patternLine("aa ")
                .patternLine("   ")
                .key('a', BlockInit.RUBBER_STRIPPED_LOG.get())
                .addCriterion("stripped_rubber_log", hasItem(BlockInit.RUBBER_STRIPPED_LOG.get()))
                .build(recipe);
        //====================================================|RUBBER END|==================================================================================
    }
}