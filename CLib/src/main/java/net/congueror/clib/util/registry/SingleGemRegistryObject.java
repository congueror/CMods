package net.congueror.clib.util.registry;

import net.congueror.clib.util.HarvestLevels;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SingleGemRegistryObject {

    private final RegistryObject<Item> gem;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> gear;

    private final RegistryObject<Block> block;
    private final RegistryObject<Block> ore;

    private SingleGemRegistryObject(RegistryObject<Item> gem, RegistryObject<Item> dust, RegistryObject<Item> gear, RegistryObject<Block> block, RegistryObject<Block> ore) {
        this.gem = gem;
        this.dust = dust;
        this.gear = gear;
        this.block = block;
        this.ore = ore;
    }

    public RegistryObject<Item> getGem() {
        return gem;
    }

    public RegistryObject<Item> getDust() {
        return dust;
    }

    public RegistryObject<Item> getGear() {
        return gear;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    public RegistryObject<Block> getOre() {
        return ore;
    }

    public static class SingleGemBuilder extends ResourceBuilder<SingleGemBuilder> {
        public SingleGemBuilder(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl, int exp) {
            super(name, tab);
            addGem();
            addDust();
            addGear();
            addOre(exp, harvestLvl);
            addBlock(hardness, harvestLvl);
        }

        public SingleGemRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new SingleGemRegistryObject(
                    gem.build(itemRegistry),
                    dust.build(itemRegistry),
                    gear.build(itemRegistry),
                    block.build(blockRegistry),
                    ore.build(blockRegistry)
            );
        }
    }
}
