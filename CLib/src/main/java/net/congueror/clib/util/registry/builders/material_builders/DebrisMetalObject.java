package net.congueror.clib.util.registry.builders.material_builders;

import net.congueror.clib.blocks.generic.CLOreBlock;
import net.congueror.clib.util.HarvestLevels;
import net.congueror.clib.util.registry.builders.ResourceBuilder;
import net.congueror.clib.util.registry.builders.BlockBuilder;
import net.congueror.clib.util.registry.builders.ItemBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static net.congueror.clib.util.registry.data.RecipeDataProvider.*;

public record DebrisMetalObject(RegistryObject<Item> ingot,
                                RegistryObject<Item> nugget,
                                RegistryObject<Item> dust,
                                RegistryObject<Item> gear,
                                RegistryObject<Item> scrap,
                                RegistryObject<CLOreBlock> ore,
                                RegistryObject<Block> block) implements ResourceBuilder.ResourceObject {

    @Override
    public void generateRecipes(Consumer<FinishedRecipe> c) {
        Block block = block().get();
        Block ore = ore().get();
        Item ingot = ingot().get();
        Item nugget = nugget().get();
        Item dust = dust().get();
        Item gear = gear().get();
        Item scrap = scrap().get();

        shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
        shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        shapelessRecipe(c, ingot, 1, getTag("forge:scraps/", scrap), 9);
        smeltingRecipe(c, scrap, getTag("forge:ores/", ore), 0.7f, 400);
        blastingRecipe(c, scrap, getTag("forge:ores/", ore), 0.7f, 200);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        gearRecipe(c, gear, getTag("forge:ingots/", ingot));
    }

    public static class Builder extends ResourceBuilder<Builder, DebrisMetalObject> {

        protected ItemBuilder<Item> ingot;
        protected ItemBuilder<Item> nugget;
        protected ItemBuilder<Item> dust;
        protected ItemBuilder<Item> gear;
        protected ItemBuilder<Item> scrap;
        protected BlockBuilder<CLOreBlock, BlockItem> debris;
        protected BlockBuilder<Block, BlockItem> block;

        public Builder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, float hardness, HarvestLevels harvestLvl, int exp, String debrisName) {
            super(modid, name, tab, blockRegister, itemRegister);
            ingot = addIngot();
            nugget = addNugget();
            dust = addDust();
            gear = addGear();
            scrap = addScrap();
            debris = addDebris(debrisName, exp, harvestLvl);
            block = addBlock(hardness, harvestLvl);
        }

        public Builder withIngotTranslation(String translation, String locale) {
            ingot.withTranslation(translation, locale);
            return this;
        }

        public Builder withNuggetTranslation(String translation, String locale) {
            nugget.withTranslation(translation, locale);
            return this;
        }

        public Builder withDustTranslation(String translation, String locale) {
            dust.withTranslation(translation, locale);
            return this;
        }

        public Builder withGearTranslation(String translation, String locale) {
            gear.withTranslation(translation, locale);
            return this;
        }

        public Builder withScrapTranslation(String translation, String locale) {
            scrap.withTranslation(translation, locale);
            return this;
        }

        public Builder withDebrisTranslation(String translation, String locale) {
            debris.withTranslation(translation, locale);
            return this;
        }

        public Builder withBlockTranslation(String translation, String locale) {
            block.withTranslation(translation, locale);
            return this;
        }

        public DebrisMetalObject buildObject() {
            return new DebrisMetalObject(
                    ingot.build(),
                    nugget.build(),
                    dust.build(),
                    gear.build(),
                    scrap.build(),
                    debris.build(),
                    block.build());
        }
    }
}
