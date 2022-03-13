package net.congueror.clib.util.registry;

import net.congueror.clib.util.HarvestLevels;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SingleMetalRegistryObject {

    private final RegistryObject<Item> ingot;
    private final RegistryObject<Item> nugget;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> gear;
    private final RegistryObject<Item> raw;

    private final RegistryObject<Block> ore;
    private final RegistryObject<Block> block;

    private SingleMetalRegistryObject(RegistryObject<Item> ingot,
                                     RegistryObject<Item> nugget,
                                     RegistryObject<Item> dust,
                                     RegistryObject<Item> gear,
                                     RegistryObject<Item> raw,
                                     RegistryObject<Block> ore,
                                     RegistryObject<Block> block) {
        this.ingot = ingot;
        this.nugget = nugget;
        this.dust = dust;
        this.gear = gear;
        this.raw = raw;
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

    public RegistryObject<Item> getRawItem() {
        return raw;
    }

    public RegistryObject<Block> getOre() {
        return ore;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    public static class SingleMetalBuilder extends ResourceBuilder<SingleMetalBuilder> {

        public SingleMetalBuilder(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl, int exp) {
            super(name, tab);
            addIngot();
            addNugget();
            addDust();
            addGear();
            addRawIngot();
            addOre(exp, harvestLvl);
            addBlock(hardness, harvestLvl);
        }

        public SingleMetalRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new SingleMetalRegistryObject(
                    ingot.build(itemRegistry),
                    nugget.build(itemRegistry),
                    dust.build(itemRegistry),
                    gear.build(itemRegistry),
                    raw.build(itemRegistry),
                    ore.build(blockRegistry),
                    block.build(blockRegistry)
            );
        }
    }
}
