package net.congueror.clib.util.registry;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
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

public class AlloyMetalRegistryObject {

    private final RegistryObject<Item> ingot;
    private final RegistryObject<Item> nugget;
    private final RegistryObject<Item> dust;
    private final RegistryObject<Item> gear;
    private final RegistryObject<Item> blend;

    private final RegistryObject<Block> block;

    private AlloyMetalRegistryObject(RegistryObject<Item> ingot,
                                    RegistryObject<Item> nugget,
                                    RegistryObject<Item> dust,
                                    RegistryObject<Item> gear,
                                    RegistryObject<Item> blend,
                                    RegistryObject<Block> block) {
        this.ingot = ingot;
        this.nugget = nugget;
        this.dust = dust;
        this.gear = gear;
        this.blend = blend;
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

    public RegistryObject<Item> getBlend() {
        return blend;
    }

    public RegistryObject<Block> getBlock() {
        return block;
    }

    public static class AlloyMetalBuilder extends ResourceBuilder<AlloyMetalBuilder> {

        public AlloyMetalBuilder(String name, CreativeModeTab tab, float hardness, HarvestLevels harvestLvl) {
            super(name, tab);
            addIngot();
            addNugget();
            addDust();
            addGear();
            addBlend();
            addBlock(hardness, harvestLvl);
        }

        public AlloyMetalRegistryObject build(DeferredRegister<Item> itemRegistry, DeferredRegister<Block> blockRegistry) {
            return new AlloyMetalRegistryObject(
                    ingot.build(itemRegistry),
                    nugget.build(itemRegistry),
                    dust.build(itemRegistry),
                    gear.build(itemRegistry),
                    blend.build(itemRegistry),
                    block.build(blockRegistry)
            );
        }
    }
}
