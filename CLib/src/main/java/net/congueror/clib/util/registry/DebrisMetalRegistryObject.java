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

public class DebrisMetalRegistryObject {

    private final RegistryObject<Item> ingot;
    private final RegistryObject<Item> nugget;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> gear;
    private final RegistryObject<Item> scrap;

    private final RegistryObject<Block> ore;
    private final RegistryObject<Block> block;

    private DebrisMetalRegistryObject(RegistryObject<Item> ingot,
                                      RegistryObject<Item> nugget,
                                      RegistryObject<Item> dust,
                                      RegistryObject<Item> gear,
                                      RegistryObject<Item> scrap,
                                      RegistryObject<Block> ore,
                                      RegistryObject<Block> block) {
        this.ingot = ingot;
        this.nugget = nugget;
        this.dust = dust;
        this.gear = gear;
        this.scrap = scrap;
        this.ore = ore;
        this.block = block;
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

    public RegistryObject<Item> getGear() {
        return gear;
    }

    public RegistryObject<Item> getScrap() {
        return scrap;
    }

    public RegistryObject<Block> getOre() {
        return ore;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    public static class DebrisMetalBuilder extends ResourceBuilder<DebrisMetalBuilder> {


        public DebrisMetalBuilder(String name, String debrisName, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl, int exp) {
            super(name, tab);
            addIngot();
            addNugget();
            addDust();
            addGear();
            addScrap();
            addDebris(debrisName, exp, harvestLvl);
            addBlock(hardness, harvestLvl);
        }

        public DebrisMetalRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new DebrisMetalRegistryObject(
                    ingot.build(itemRegistry),
                    nugget.build(itemRegistry),
                    dust.build(itemRegistry),
                    gear.build(itemRegistry),
                    scrap.build(itemRegistry),
                    ore.build(blockRegistry),
                    block.build(blockRegistry)
            );
        }
    }
}
