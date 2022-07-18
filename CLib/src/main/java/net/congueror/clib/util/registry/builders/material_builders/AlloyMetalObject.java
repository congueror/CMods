package net.congueror.clib.util.registry.builders.material_builders;

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

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static net.congueror.clib.util.registry.data.RecipeDataProvider.*;

public record AlloyMetalObject(RegistryObject<Item> ingot,
                               RegistryObject<Item> nugget,
                               RegistryObject<Item> dust,
                               RegistryObject<Item> gear,
                               RegistryObject<Item> blend,
                               RegistryObject<Block> block) implements ResourceBuilder.ResourceObject {

    @Override
    public void generateRecipes(Consumer<FinishedRecipe> c) {
        Block block = block().get();
        Item ingot = ingot().get();
        Item nugget = nugget().get();
        Item dust = dust().get();
        Item gear = gear().get();
        Item ore = blend().get();

        shapelessRecipe(c, block, 1, getTag("forge:ingots/", ingot), 9);
        shapelessRecipe(c, ingot, 9, getTag("forge:storage_blocks/", block), 1);
        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        smeltingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 400);
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 200);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        gearRecipe(c, gear, getTag("forge:ingots/", ingot));
    }

    public static class Builder extends ResourceBuilder<Builder, AlloyMetalObject> {

        protected ItemBuilder<Item> ingot;
        protected ItemBuilder<Item> nugget;
        protected ItemBuilder<Item> dust;
        protected ItemBuilder<Item> gear;
        protected ItemBuilder<Item> blend;
        protected BlockBuilder<Block, BlockItem> block;

        public Builder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, float hardness, HarvestLevels harvestLvl) {
            super(modid, name, tab, blockRegister, itemRegister);
            ingot = addIngot();
            nugget = addNugget();
            dust = addDust();
            gear = addGear();
            blend = addBlend();
            block = addBlock(hardness, harvestLvl);
        }

        public Builder withBlendRecipe(BiConsumer<Consumer<FinishedRecipe>, Item> consumer) {
            blend.withRecipe(consumer);
            return this;
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

        public Builder withBlendTranslation(String translation, String locale) {
            blend.withTranslation(translation, locale);
            return this;
        }

        public Builder withBlockTranslation(String translation, String locale) {
            block.withTranslation(translation, locale);
            return this;
        }

        public AlloyMetalObject buildObject() {
            return new AlloyMetalObject(
                    ingot.build(),
                    nugget.build(),
                    dust.build(),
                    gear.build(),
                    blend.build(),
                    block.build());
        }
    }
}
