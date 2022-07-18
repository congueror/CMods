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
import static net.congueror.clib.util.registry.data.RecipeDataProvider.getTag;

public record SingleGemObject(RegistryObject<Item> gem,
                              RegistryObject<Item> dust,
                              RegistryObject<Item> gear,
                              RegistryObject<Block> block,
                              RegistryObject<CLOreBlock> ore) implements ResourceBuilder.ResourceObject {

    @Override
    public void generateRecipes(Consumer<FinishedRecipe> c) {
        Item gem = gem().get();
        Item dust = dust().get();
        Item gear = gear().get();
        Block block = block().get();
        Block ore = ore().get();

        shapelessRecipe(c, block, 1, getTag("forge:gems/", gem), 9);
        shapelessRecipe(c, gem, 9, getTag("forge:storage_blocks/", block), 1);
        smeltingRecipe(c, gem, getTag("forge:ores/", ore), 0.7f, 200);
        blastingRecipe(c, gem, getTag("forge:ores/", ore), 0.7f, 100);
        dustRecipes(c, dust, gem, getTag("forge:gems/", gem));
        gearRecipe(c, gear, getTag("forge:gems/", gem));
    }

    public static class Builder extends ResourceBuilder.OreBuilder<Builder, SingleGemObject> {

        protected ItemBuilder<Item> gem;
        protected ItemBuilder<Item> dust;
        protected ItemBuilder<Item> gear;
        protected BlockBuilder<CLOreBlock, BlockItem> deepslate;
        protected BlockBuilder<Block, BlockItem> block;

        public Builder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, float hardness, HarvestLevels harvestLvl, int exp) {
            super(modid, name, tab, blockRegister, itemRegister, harvestLvl, exp);
            gem = addGem();
            dust = addDust();
            gear = addGear();
            deepslate = addDeepslateOre(exp, harvestLvl);
            block = addBlock(hardness, harvestLvl);
        }

        public Builder withGemTranslation(String translation, String locale) {
            gem.withTranslation(translation, locale);
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

        public Builder withOreTranslation(String translation, String locale) {
            ore.withTranslation(translation, locale);
            return this;
        }

        public Builder withDeepslateOreTranslation(String translation, String locale) {
            deepslate.withTranslation(translation, locale);
            return this;
        }

        public Builder withBlockTranslation(String translation, String locale) {
            block.withTranslation(translation, locale);
            return this;
        }

        public SingleGemObject buildObject() {
            return new SingleGemObject(
                    gem.build(),
                    dust.build(),
                    gear.build(),
                    block.build(),
                    ore.build());
        }
    }
}
