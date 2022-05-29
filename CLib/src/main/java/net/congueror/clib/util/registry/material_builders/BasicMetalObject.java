package net.congueror.clib.util.registry.material_builders;

import net.congueror.clib.blocks.generic.CLOreBlock;
import net.congueror.clib.util.HarvestLevels;
import net.congueror.clib.util.registry.ResourceBuilder;
import net.congueror.clib.util.registry.data.RecipeDataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public record BasicMetalObject(RegistryObject<Item> ingot,
                               RegistryObject<Item> nugget,
                               RegistryObject<Item> dust,
                               RegistryObject<Item> gear,
                               RegistryObject<Item> raw,
                               RegistryObject<CLOreBlock> ore,
                               RegistryObject<CLOreBlock> deepslate,
                               RegistryObject<Block> block) implements ResourceBuilder.ResourceObject {

    @Override
    public void generateRecipes(Consumer<FinishedRecipe> c) {
        Block block = block().get();
        Block ore = ore().get();
        Item ingot = ingot().get();
        Item nugget = nugget().get();
        Item dust = dust().get();
        Item gear = gear().get();

        RecipeDataProvider.shapelessRecipe(c, block, 1, RecipeDataProvider.getTag("forge:ingots/", ingot), 9);
        RecipeDataProvider.shapelessRecipe(c, ingot, 9, RecipeDataProvider.getTag("forge:storage_blocks/", block), 1);
        RecipeDataProvider.shapelessRecipe(c, nugget, 9, RecipeDataProvider.getTag("forge:ingots/", ingot), 1);
        RecipeDataProvider.shapelessRecipe(c, ingot, 1, RecipeDataProvider.getTag("forge:nuggets/", nugget), 9);
        RecipeDataProvider.smeltingRecipe(c, ingot, RecipeDataProvider.getTag("forge:ores/", ore), 0.7f, 200);
        RecipeDataProvider.blastingRecipe(c, ingot, RecipeDataProvider.getTag("forge:ores/", ore), 0.7f, 400);
        RecipeDataProvider.smeltingRecipe(c, ingot, RecipeDataProvider.getTag("forge:raw_materials/", ore), 0.7f, 200);
        RecipeDataProvider.blastingRecipe(c, ingot, RecipeDataProvider.getTag("forge:raw_materials/", ore), 0.7f, 400);
        RecipeDataProvider.dustRecipes(c, dust, ingot, RecipeDataProvider.getTag("forge:ingots/", ingot));
        RecipeDataProvider.gearRecipe(c, gear, RecipeDataProvider.getTag("forge:ingots/", ingot));
    }

    public static class Builder extends ResourceBuilder<Builder, BasicMetalObject> {

        public Builder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, float hardness, HarvestLevels harvestLvl, int exp) {
            super(modid, name, tab, blockRegister, itemRegister);
            addIngot();
            addNugget();
            addDust();
            addGear();
            addRawIngot();
            addOre(exp, harvestLvl);
            addDeepslateOre(exp, harvestLvl);
            addBlock(hardness, harvestLvl);
        }

        @Override
        protected BasicMetalObject buildObject() {
            return new BasicMetalObject(
                    ingot.build(),
                    nugget.build(),
                    dust.build(),
                    gear.build(),
                    raw.build(),
                    ore.build(),
                    deepslate.build(),
                    block.build());
        }
    }
}
