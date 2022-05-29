package net.congueror.clib.util.registry.material_builders;

import net.congueror.clib.blocks.generic.CLOreBlock;
import net.congueror.clib.util.HarvestLevels;
import net.congueror.clib.util.registry.ResourceBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static net.congueror.clib.util.registry.data.RecipeDataProvider.*;

public record RadioactiveMetalObject(RegistryObject<Item> ingot,
                                     RegistryObject<Item> nugget,
                                     RegistryObject<Item> dust,
                                     RegistryObject<Item> raw,
                                     RegistryObject<CLOreBlock> ore,
                                     RegistryObject<CLOreBlock> deepslate) implements ResourceBuilder.ResourceObject {

    @Override
    public void generateRecipes(Consumer<FinishedRecipe> c) {
        Block ore = ore().get();
        Item ingot = ingot().get();
        Item nugget = nugget().get();
        Item dust = dust().get();

        shapelessRecipe(c, nugget, 9, getTag("forge:ingots/", ingot), 1);
        shapelessRecipe(c, ingot, 1, getTag("forge:nuggets/", nugget), 9);
        smeltingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 200);
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 400);
        smeltingRecipe(c, ingot, getTag("forge:raw_materials/", ore), 0.7f, 200);
        blastingRecipe(c, ingot, getTag("forge:raw_materials/", ore), 0.7f, 400);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
    }

    public static class Builder extends ResourceBuilder<Builder, RadioactiveMetalObject> {

        public Builder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, HarvestLevels harvestLvl, int exp) {
            super(modid, name, tab, blockRegister, itemRegister);
            addIngot();
            addNugget();
            addDust();
            addRawIngot();
            addOre(exp, harvestLvl);
            addDeepslateOre(exp, harvestLvl);
        }

        public RadioactiveMetalObject buildObject() {
            return new RadioactiveMetalObject(
                    ingot.build(),
                    nugget.build(),
                    dust.build(),
                    raw.build(),
                    ore.build(),
                    deepslate.build());
        }
    }
}
