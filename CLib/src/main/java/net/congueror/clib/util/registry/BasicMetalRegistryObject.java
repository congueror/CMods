package net.congueror.clib.util.registry;

import net.congueror.clib.util.HarvestLevels;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BasicMetalRegistryObject {

    private final RegistryObject<Item> ingot;
    private final RegistryObject<Item> nugget;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> gear;
    private final RegistryObject<Item> raw;

    private final RegistryObject<Block> ore;
    private final RegistryObject<Block> deepslate;
    private final RegistryObject<Block> block;

    private BasicMetalRegistryObject(RegistryObject<Item> ingot,
                                     RegistryObject<Item> nugget,
                                     RegistryObject<Item> dust,
                                     RegistryObject<Item> gear,
                                     RegistryObject<Item> raw,
                                     RegistryObject<Block> ore,
                                     RegistryObject<Block> deepslate,
                                     RegistryObject<Block> block) {
        this.ingot = ingot;
        this.nugget = nugget;
        this.dust = dust;
        this.gear = gear;
        this.raw = raw;
        this.ore = ore;
        this.deepslate = deepslate;
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

    public RegistryObject<Block> getDeepslateOre() {
        return deepslate;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    public static class BasicMetalBuilder extends ResourceBuilder<BasicMetalBuilder> {

        public BasicMetalBuilder(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl, int exp) {
            super(name, tab);
            addIngot();
            addNugget();
            addDust();
            addGear();
            addRawIngot();
            addOre(exp, harvestLvl);
            addDeepslateOre(exp, harvestLvl);
            addBlock(hardness, harvestLvl);
        }

        public BasicMetalRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new BasicMetalRegistryObject(
                    ingot.build(itemRegistry),
                    nugget.build(itemRegistry),
                    dust.build(itemRegistry),
                    gear.build(itemRegistry),
                    raw.build(itemRegistry),
                    ore.build(blockRegistry),
                    deepslate.build(blockRegistry),
                    block.build(blockRegistry)
            );
        }
    }
}
