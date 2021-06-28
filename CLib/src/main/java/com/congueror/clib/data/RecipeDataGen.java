package com.congueror.clib.data;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.ItemInit;
import com.congueror.clib.init.ModTags;
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
                , ModTags.Items.INGOTS_TIN
                , ItemInit.TIN_NUGGET.get()
                , ModTags.Items.NUGGETS_TIN
                , ItemInit.TIN_DUST.get()
                , BlockInit.TIN_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_TIN
                , BlockInit.TIN_ORE.get().asItem()
                , ItemInit.TIN_GEAR.get());

        //STEEL
        registerMetal(recipe
                , ItemInit.STEEL_INGOT.get()
                , ModTags.Items.INGOTS_STEEL
                , ItemInit.STEEL_NUGGET.get()
                , ModTags.Items.NUGGETS_STEEL
                , ItemInit.STEEL_DUST.get()
                , BlockInit.STEEL_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_STEEL
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
                , ModTags.Items.INGOTS_ALUMINUM
                , ItemInit.ALUMINUM_NUGGET.get()
                , ModTags.Items.NUGGETS_ALUMINUM
                , ItemInit.ALUMINUM_DUST.get()
                , BlockInit.ALUMINUM_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_ALUMINUM
                , BlockInit.ALUMINUM_ORE.get().asItem()
                , ItemInit.ALUMINUM_GEAR.get());

        //LEAD
        registerMetal(recipe
                , ItemInit.LEAD_INGOT.get()
                , ModTags.Items.INGOTS_LEAD
                , ItemInit.LEAD_NUGGET.get()
                , ModTags.Items.NUGGETS_LEAD
                , ItemInit.LEAD_DUST.get()
                , BlockInit.LEAD_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_LEAD
                , BlockInit.LEAD_ORE.get().asItem()
                , ItemInit.LEAD_GEAR.get());

        //COPPER
        registerMetal(recipe
                , ItemInit.COPPER_INGOT.get()
                , ModTags.Items.INGOTS_COPPER
                , ItemInit.COPPER_NUGGET.get()
                , ModTags.Items.NUGGETS_COPPER
                , ItemInit.COPPER_DUST.get()
                , BlockInit.COPPER_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_COPPER
                , BlockInit.COPPER_ORE.get().asItem()
                , ItemInit.COPPER_GEAR.get());

        //RUBY
        registerStone(recipe
                , ItemInit.RUBY.get()
                , ModTags.Items.GEMS_RUBY
                , ItemInit.RUBY_DUST.get()
                , BlockInit.RUBY_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_RUBY
                , BlockInit.RUBY_ORE.get().asItem()
                , ItemInit.RUBY_GEAR.get());

        //SILVER
        registerMetal(recipe
                , ItemInit.SILVER_INGOT.get()
                , ModTags.Items.INGOTS_SILVER
                , ItemInit.SILVER_NUGGET.get()
                , ModTags.Items.NUGGETS_SILVER
                , ItemInit.SILVER_DUST.get()
                , BlockInit.SILVER_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_SILVER
                , BlockInit.SILVER_ORE.get().asItem()
                , ItemInit.SILVER_GEAR.get());

        //LUMIUM
        registerMetal(recipe
                , ItemInit.LUMIUM_INGOT.get()
                , ModTags.Items.INGOTS_LUMIUM
                , ItemInit.LUMIUM_NUGGET.get()
                , ModTags.Items.NUGGETS_LUMIUM
                , ItemInit.LUMIUM_DUST.get()
                , BlockInit.LUMIUM_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_LUMIUM
                , ItemInit.LUMIUM_BLEND.get()
                , ItemInit.LUMIUM_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.LUMIUM_BLEND.get(), 2)
                .patternLine("cg ")
                .patternLine("i  ")
                .patternLine("   ")
                .key('i', Tags.Items.DUSTS_GLOWSTONE)
                .key('c', ModTags.Items.INGOTS_SILVER)
                .key('g', ModTags.Items.INGOTS_TIN)
                .addCriterion("silver_ingot", hasItem(ModTags.Items.INGOTS_SILVER))
                .build(recipe);

        //NICKEL
        registerMetal(recipe
                , ItemInit.NICKEL_INGOT.get()
                , ModTags.Items.INGOTS_NICKEL
                , ItemInit.NICKEL_NUGGET.get()
                , ModTags.Items.NUGGETS_NICKEL
                , ItemInit.NICKEL_DUST.get()
                , BlockInit.NICKEL_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_NICKEL
                , BlockInit.NICKEL_ORE.get().asItem()
                , ItemInit.NICKEL_GEAR.get());

        //INVAR
        registerMetal(recipe
                , ItemInit.INVAR_INGOT.get()
                , ModTags.Items.INGOTS_INVAR
                , ItemInit.INVAR_NUGGET.get()
                , ModTags.Items.NUGGETS_INVAR
                , ItemInit.INVAR_DUST.get()
                , BlockInit.INVAR_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_INVAR
                , ItemInit.INVAR_BLEND.get()
                , ItemInit.INVAR_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.INVAR_BLEND.get(), 2)
                .patternLine("ci ")
                .patternLine("i  ")
                .patternLine("   ")
                .key('i', Tags.Items.INGOTS_IRON)
                .key('c', ModTags.Items.INGOTS_NICKEL)
                .addCriterion("nickel_ingot", hasItem(ModTags.Items.INGOTS_NICKEL))
                .build(recipe);

        //ELECTRUM
        registerMetal(recipe
                , ItemInit.ELECTRUM_INGOT.get()
                , ModTags.Items.INGOTS_ELECTRUM
                , ItemInit.ELECTRUM_NUGGET.get()
                , ModTags.Items.NUGGETS_ELECTRUM
                , ItemInit.ELECTRUM_DUST.get()
                , BlockInit.ELECTRUM_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_ELECTRUM
                , ItemInit.ELECTRUM_BLEND.get()
                , ItemInit.ELECTRUM_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.ELECTRUM_BLEND.get(), 2)
                .patternLine(" i ")
                .patternLine("c  ")
                .patternLine("   ")
                .key('i', Tags.Items.INGOTS_GOLD)
                .key('c', ModTags.Items.INGOTS_SILVER)
                .addCriterion("silver_ingot", hasItem(ModTags.Items.INGOTS_SILVER))
                .build(recipe);

        //PLATINUM
        registerMetal(recipe
                , ItemInit.PLATINUM_INGOT.get()
                , ModTags.Items.INGOTS_PLATINUM
                , ItemInit.PLATINUM_NUGGET.get()
                , ModTags.Items.NUGGETS_PLATINUM
                , ItemInit.PLATINUM_DUST.get()
                , BlockInit.PLATINUM_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_PLATINUM
                , BlockInit.PLATINUM_ORE.get().asItem()
                , ItemInit.PLATINUM_GEAR.get());

        //ENDERIUM
        registerMetal(recipe
                , ItemInit.ENDERIUM_INGOT.get()
                , ModTags.Items.INGOTS_ENDERIUM
                , ItemInit.ENDERIUM_NUGGET.get()
                , ModTags.Items.NUGGETS_ENDERIUM
                , ItemInit.ENDERIUM_DUST.get()
                , BlockInit.ENDERIUM_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_ENDERIUM
                , ItemInit.ENDERIUM_BLEND.get()
                , ItemInit.ENDERIUM_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.ENDERIUM_BLEND.get(), 1)
                .patternLine("ii ")
                .patternLine("cp ")
                .patternLine("   ")
                .key('i', ModTags.Items.INGOTS_TIN)
                .key('c', ModTags.Items.INGOTS_SILVER)
                .key('p', ModTags.Items.INGOTS_PLATINUM)
                .addCriterion("platinum_ingot", hasItem(ModTags.Items.INGOTS_PLATINUM))
                .build(recipe);

        //SIGNALUM
        registerMetal(recipe
                , ItemInit.SIGNALUM_INGOT.get()
                , ModTags.Items.INGOTS_SIGNALUM
                , ItemInit.SIGNALUM_NUGGET.get()
                , ModTags.Items.NUGGETS_SIGNALUM
                , ItemInit.SIGNALUM_DUST.get()
                , BlockInit.SIGNALUM_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_SIGNALUM
                , ItemInit.SIGNALUM_BLEND.get()
                , ItemInit.SIGNALUM_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.SIGNALUM_BLEND.get(), 2)
                .patternLine("ipi")
                .patternLine("cic")
                .patternLine("ccc")
                .key('i', ModTags.Items.INGOTS_COPPER)
                .key('c', Tags.Items.DUSTS_REDSTONE)
                .key('p', ModTags.Items.INGOTS_SILVER)
                .addCriterion("silver_ingot", hasItem(ModTags.Items.INGOTS_SILVER))
                .build(recipe);

        //TUNGSTEN
        registerMetal(recipe
                , ItemInit.TUNGSTEN_INGOT.get()
                , ModTags.Items.INGOTS_TUNGSTEN
                , ItemInit.TUNGSTEN_NUGGET.get()
                , ModTags.Items.NUGGETS_TUNGSTEN
                , ItemInit.TUNGSTEN_DUST.get()
                , BlockInit.TUNGSTEN_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_TUNGSTEN
                , BlockInit.TUNGSTEN_ORE.get().asItem()
                , ItemInit.TUNGSTEN_GEAR.get());

        //BRONZE
        registerMetal(recipe
                , ItemInit.BRONZE_INGOT.get()
                , ModTags.Items.INGOTS_BRONZE
                , ItemInit.BRONZE_NUGGET.get()
                , ModTags.Items.NUGGETS_BRONZE
                , ItemInit.BRONZE_DUST.get()
                , BlockInit.BRONZE_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_BRONZE
                , ItemInit.BRONZE_BLEND.get()
                , ItemInit.BRONZE_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.BRONZE_BLEND.get(), 2)
                .patternLine("ip ")
                .patternLine("p  ")
                .patternLine("   ")
                .key('i', ModTags.Items.INGOTS_COPPER)
                .key('p', ModTags.Items.INGOTS_TIN)
                .addCriterion("copper_ingot", hasItem(ModTags.Items.INGOTS_COPPER))
                .build(recipe);

        //AMETHYST
        registerStone(recipe
                , ItemInit.AMETHYST.get()
                , ModTags.Items.GEMS_AMETHYST
                , ItemInit.AMETHYST_DUST.get()
                , BlockInit.AMETHYST_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_AMETHYST
                , BlockInit.AMETHYST_ORE.get().asItem()
                , ItemInit.AMETHYST_GEAR.get());

        //SAPPHIRE
        registerStone(recipe
                , ItemInit.SAPPHIRE.get()
                , ModTags.Items.GEMS_SAPPHIRE
                , ItemInit.SAPPHIRE_DUST.get()
                , BlockInit.SAPPHIRE_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_SAPPHIRE
                , BlockInit.SAPPHIRE_ORE.get().asItem()
                , ItemInit.SAPPHIRE_GEAR.get());

        //OPAL
        registerStone(recipe
                , ItemInit.OPAL.get()
                , ModTags.Items.GEMS_OPAL
                , ItemInit.OPAL_DUST.get()
                , BlockInit.OPAL_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_OPAL
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

        registerDust(recipe, ItemInit.TITANIUM_DUST.get(), ItemInit.TITANIUM_INGOT.get(), ModTags.Items.INGOTS_TITANIUM);

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
                .addIngredient(ModTags.Items.INGOTS_URANIUM)
                .addCriterion("uranium_ingot", hasItem(ModTags.Items.INGOTS_URANIUM))
                .build(recipe);

        //ingot_nuggets
        ShapedRecipeBuilder.shapedRecipe(ItemInit.URANIUM_INGOT.get(), 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', ModTags.Items.NUGGETS_URANIUM)
                .addCriterion("uranium_nugget", hasItem(ModTags.Items.NUGGETS_URANIUM))
                .build(recipe, new ResourceLocation(CLib.MODID, "uranium_ingot_nuggets"));

        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.URANIUM_ORE.get()), ItemInit.URANIUM_INGOT.get(), 0.7f, 400)
                .addCriterion("uranium_ore", hasItem(BlockInit.URANIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "uranium_ingot_blasting"));

        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.URANIUM_ORE.get()), ItemInit.URANIUM_INGOT.get(), 0.7f, 200)
                .addCriterion("uranium_ore", hasItem(BlockInit.URANIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "uranium_ingot_smelting"));

        registerDust(recipe, ItemInit.URANIUM_DUST.get(), ItemInit.URANIUM_INGOT.get(), ModTags.Items.INGOTS_URANIUM);

        //====================================================|URANIUM END|==================================================================================

        //COBALT
        registerMetal(recipe
                , ItemInit.COBALT_INGOT.get()
                , ModTags.Items.INGOTS_COBALT
                , ItemInit.COBALT_NUGGET.get()
                , ModTags.Items.NUGGETS_COBALT
                , ItemInit.COBALT_DUST.get()
                , BlockInit.COBALT_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_COBALT
                , BlockInit.COBALT_ORE.get().asItem()
                , ItemInit.COBALT_GEAR.get());

        //ZINC
        registerMetal(recipe
                , ItemInit.ZINC_INGOT.get()
                , ModTags.Items.INGOTS_ZINC
                , ItemInit.ZINC_NUGGET.get()
                , ModTags.Items.NUGGETS_ZINC
                , ItemInit.ZINC_DUST.get()
                , BlockInit.ZINC_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_ZINC
                , BlockInit.ZINC_ORE.get().asItem()
                , ItemInit.ZINC_GEAR.get());

        //BRASS
        registerMetal(recipe
                , ItemInit.BRASS_INGOT.get()
                , ModTags.Items.INGOTS_BRASS
                , ItemInit.BRASS_NUGGET.get()
                , ModTags.Items.NUGGETS_BRASS
                , ItemInit.BRASS_DUST.get()
                , BlockInit.BRASS_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_BRASS
                , ItemInit.BRASS_BLEND.get()
                , ItemInit.BRASS_GEAR.get());
        ShapedRecipeBuilder.shapedRecipe(ItemInit.BRASS_BLEND.get(), 4)
                .patternLine("pp ")
                .patternLine("pi ")
                .patternLine("   ")
                .key('i', ModTags.Items.INGOTS_ZINC)
                .key('p', ModTags.Items.INGOTS_COPPER)
                .addCriterion("zinc_ingot", hasItem(ModTags.Items.INGOTS_ZINC))
                .build(recipe);

        //CHROMIUM
        registerMetal(recipe
                , ItemInit.CHROMIUM_INGOT.get()
                , ModTags.Items.INGOTS_CHROMIUM
                , ItemInit.CHROMIUM_NUGGET.get()
                , ModTags.Items.NUGGETS_CHROMIUM
                , ItemInit.CHROMIUM_DUST.get()
                , BlockInit.CHROMIUM_BLOCK.get()
                , ModTags.Items.STORAGE_BLOCKS_CHROMIUM
                , BlockInit.CHROMIUM_ORE.get().asItem()
                , ItemInit.CHROMIUM_GEAR.get());

        //====================================================|THORIUM|======================================================================================
        //nugget
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.THORIUM_NUGGET.get(), 9)
                .addIngredient(ModTags.Items.INGOTS_THORIUM)
                .addCriterion("thorium_ingot", hasItem(ModTags.Items.INGOTS_THORIUM))
                .build(recipe);

        //ingot_nuggets
        ShapedRecipeBuilder.shapedRecipe(ItemInit.THORIUM_INGOT.get(), 1)
                .patternLine("aaa")
                .patternLine("aaa")
                .patternLine("aaa")
                .key('a', ModTags.Items.NUGGETS_THORIUM)
                .addCriterion("thorium_nugget", hasItem(ModTags.Items.NUGGETS_THORIUM))
                .build(recipe, new ResourceLocation(CLib.MODID, "thorium_ingot_nuggets"));

        //ingot_blasting
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(BlockInit.THORIUM_ORE.get()), ItemInit.THORIUM_INGOT.get(), 0.7f, 400)
                .addCriterion("thorium_ore", hasItem(BlockInit.THORIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "thorium_ingot_blasting"));

        //ingot_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(BlockInit.THORIUM_ORE.get()), ItemInit.THORIUM_INGOT.get(), 0.7f, 200)
                .addCriterion("thorium_ore", hasItem(BlockInit.THORIUM_ORE.get()))
                .build(recipe, new ResourceLocation(CLib.MODID, "thorium_ingot_smelting"));

        registerDust(recipe, ItemInit.THORIUM_DUST.get(), ItemInit.THORIUM_INGOT.get(), ModTags.Items.INGOTS_THORIUM);
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

        //SALT BLOCK
        ShapedRecipeBuilder.shapedRecipe(BlockInit.SALT_BLOCK.get(), 1)
                .patternLine("pp ")
                .patternLine("pp ")
                .patternLine("   ")
                .key('p', ModTags.Items.DUSTS_SALT)
                .addCriterion("salt", hasItem(ModTags.Items.DUSTS_SALT))
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
                .addIngredient(ModTags.Items.DUSTS_COAL)
                .addIngredient(ModTags.Items.DUSTS_COAL)
                .addIngredient(ModTags.Items.DUSTS_SULFUR)
                .addIngredient(ModTags.Items.DUSTS_SALTPETRE)
                .addCriterion("saltpetre_dust", hasItem(ItemInit.SALTPETRE_DUST.get()))
                .build(recipe);

        //PYROTHEUM DUST
        ShapedRecipeBuilder.shapedRecipe(ItemInit.PYROTHEUM_DUST.get(), 1)
                .patternLine("bb ")
                .patternLine("rs ")
                .patternLine("   ")
                .key('b', ModTags.Items.DUSTS_BLAZE_POWDER)
                .key('r', Tags.Items.DUSTS_REDSTONE)
                .key('s', ModTags.Items.DUSTS_SULFUR)
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
                .addCriterion("iron_dust", hasItem(ModTags.Items.DUSTS_IRON))
                .build(recipe, new ResourceLocation(CLib.MODID, "iron_dust_smelting"));
        //gold_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.GOLD_DUST.get(), 1)
                .addIngredient(Tags.Items.INGOTS_GOLD)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("gold_ingot", hasItem(Tags.Items.INGOTS_GOLD))
                .build(recipe);
        //gold_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.GOLD_DUST.get()), Items.GOLD_INGOT, 0.7f, 200)
                .addCriterion("gold_dust", hasItem(ModTags.Items.DUSTS_GOLD))
                .build(recipe, new ResourceLocation(CLib.MODID, "gold_dust_smelting"));
        //lapis_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.LAPIS_DUST.get(), 1)
                .addIngredient(Tags.Items.GEMS_LAPIS)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("lapis_lazuli", hasItem(Tags.Items.GEMS_LAPIS))
                .build(recipe);
        //lapis_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.LAPIS_DUST.get()), Items.LAPIS_LAZULI, 0.7f, 200)
                .addCriterion("lapis_dust", hasItem(ModTags.Items.DUSTS_LAPIS))
                .build(recipe, new ResourceLocation(CLib.MODID, "lapis_dust_smelting"));
        //quartz_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.QUARTZ_DUST.get(), 1)
                .addIngredient(Tags.Items.GEMS_QUARTZ)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("quartz", hasItem(Tags.Items.GEMS_QUARTZ))
                .build(recipe);
        //quartz_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.QUARTZ_DUST.get()), Items.QUARTZ, 0.7f, 200)
                .addCriterion("quartz", hasItem(ModTags.Items.DUSTS_QUARTZ))
                .build(recipe, new ResourceLocation(CLib.MODID, "quartz_dust_smelting"));
        //diamond_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.DIAMOND_DUST.get(), 1)
                .addIngredient(Tags.Items.GEMS_DIAMOND)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("diamond", hasItem(Tags.Items.GEMS_DIAMOND))
                .build(recipe);
        //diamond_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.DIAMOND_DUST.get()), Items.DIAMOND, 0.7f, 200)
                .addCriterion("diamond_dust", hasItem(ModTags.Items.DUSTS_DIAMOND))
                .build(recipe, new ResourceLocation(CLib.MODID, "diamond_dust_smelting"));
        //emerald_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.EMERALD_DUST.get(), 1)
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("emerald", hasItem(Tags.Items.GEMS_EMERALD))
                .build(recipe);
        //emerald_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.EMERALD_DUST.get()), Items.EMERALD, 0.7f, 200)
                .addCriterion("emerald", hasItem(ModTags.Items.DUSTS_EMERALD))
                .build(recipe, new ResourceLocation(CLib.MODID, "emerald_dust_smelting"));
        //netherite_dust
        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.NETHERITE_DUST.get(), 1)
                .addIngredient(Tags.Items.INGOTS_NETHERITE)
                .addIngredient(ItemInit.IRON_HAMMER.get())
                .addCriterion("netherite_ingot", hasItem(Tags.Items.INGOTS_NETHERITE))
                .build(recipe);
        //netherite_dust_smelting
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.NETHERITE_DUST.get()), Items.NETHERITE_INGOT, 0.7f, 200)
                .addCriterion("netherite_dust", hasItem(ModTags.Items.DUSTS_NETHERITE))
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
                .addCriterion("rubber_log", hasItem(ModTags.Items.RUBBER_LOGS))
                .build(recipe);
        ShapelessRecipeBuilder.shapelessRecipe(BlockInit.RUBBER_PLANKS.get(), 4)
                .addIngredient(ModTags.Items.RUBBER_LOGS)
                .addCriterion("rubber_log", hasItem(ModTags.Items.RUBBER_LOGS))
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