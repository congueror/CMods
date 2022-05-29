package net.congueror.clib.util.registry.material_builders;

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
        blastingRecipe(c, ingot, getTag("forge:ores/", ore), 0.7f, 800);
        dustRecipes(c, dust, ingot, getTag("forge:ingots/", ingot));
        gearRecipe(c, gear, getTag("forge:ingots/", ingot));
    }

    public static class Builder extends ResourceBuilder<Builder, AlloyMetalObject> {

        public Builder(String modid, String name, CreativeModeTab tab, DeferredRegister<Block> blockRegister, DeferredRegister<Item> itemRegister, float hardness, HarvestLevels harvestLvl) {
            super(modid, name, tab, blockRegister, itemRegister);
            addIngot();
            addNugget();
            addDust();
            addGear();
            addBlend();
            addBlock(hardness, harvestLvl);
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
