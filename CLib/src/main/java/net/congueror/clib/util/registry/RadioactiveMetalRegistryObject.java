package net.congueror.clib.util.registry;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.congueror.clib.blocks.generic.CLOreBlock;
import net.congueror.clib.util.HarvestLevels;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RadioactiveMetalRegistryObject {

    private final RegistryObject<Item> ingot;
    private final RegistryObject<Item> nugget;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> raw;

    private final RegistryObject<Block> ore;
    private final RegistryObject<Block> deepslate;

    private RadioactiveMetalRegistryObject(RegistryObject<Item> ingot,
                                           RegistryObject<Item> nugget,
                                           RegistryObject<Item> dust,
                                           RegistryObject<Item> raw,
                                           RegistryObject<Block> ore,
                                           RegistryObject<Block> deepslate) {
        this.ingot = ingot;
        this.nugget = nugget;
        this.dust = dust;
        this.raw = raw;
        this.ore = ore;
        this.deepslate = deepslate;
    }

    public RegistryObject<Item> getIngot() {
        return ingot;
    }

    public RegistryObject<Item> getNugget() {
        return nugget;
    }

    public RegistryObject<Item> getDust() {
        return dust;
    }

    public RegistryObject<Item> getRaw() {
        return raw;
    }

    public RegistryObject<Block> getOre() {
        return ore;
    }

    public RegistryObject<Block> getDeepslateOre() {
        return deepslate;
    }

    public static class RadioactiveMetalBuilder extends ResourceBuilder<RadioactiveMetalBuilder> {

        public RadioactiveMetalBuilder(String name, CreativeModeTab tab, HarvestLevels harvestLvl, int exp) {
            super(name, tab);
            addIngot();
            addNugget();
            addDust();
            addRawIngot();
            addOre(exp, harvestLvl);
            addDeepslateOre(exp, harvestLvl);
        }

        public RadioactiveMetalRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new RadioactiveMetalRegistryObject(
                    ingot.build(itemRegistry),
                    nugget.build(itemRegistry),
                    dust.build(itemRegistry),
                    raw.build(itemRegistry),
                    ore.build(blockRegistry),
                    deepslate.build(blockRegistry)
            );
        }
    }
}
