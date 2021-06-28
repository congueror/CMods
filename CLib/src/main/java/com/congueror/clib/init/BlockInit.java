package com.congueror.clib.init;

import com.congueror.clib.CLib;
import com.congueror.clib.blocks.ModOreBlock;
import com.congueror.clib.world.tree_gen.RubberTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//Harvest Levels: 0(Wood), 1(Stone), 2(Iron), 3(Diamond)
public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CLib.MODID);

    public static final RegistryObject<Block> TIN_BLOCK = registerStorageBlock("tin_block", 3.0f, 1);
    public static final RegistryObject<Block> TIN_ORE = registerOreBlock("tin_ore", 1, 0);

    public static final RegistryObject<Block> STEEL_BLOCK = registerStorageBlock("steel_block", 6.0f, 2);

    public static final RegistryObject<Block> ALUMINUM_BLOCK = registerStorageBlock("aluminum_block", 5.0f, 2);
    public static final RegistryObject<Block> ALUMINUM_ORE = registerOreBlock("aluminum_ore", 2, 0);

    public static final RegistryObject<Block> LEAD_BLOCK = registerStorageBlock("lead_block", 4.0f, 2);
    public static final RegistryObject<Block> LEAD_ORE = registerOreBlock("lead_ore", 2, 0);

    public static final RegistryObject<Block> COPPER_BLOCK = registerStorageBlock("copper_block", 3.0f, 1);
    public static final RegistryObject<Block> COPPER_ORE = registerOreBlock("copper_ore", 1, 0);

    public static final RegistryObject<Block> RUBY_BLOCK = registerStorageBlock("ruby_block", 6.0f, 2);
    public static final RegistryObject<Block> RUBY_ORE = registerOreBlock("ruby_ore", 2, 7);

    public static final RegistryObject<Block> SILVER_BLOCK = registerStorageBlock("silver_block", 5.0f, 2);
    public static final RegistryObject<Block> SILVER_ORE = registerOreBlock("silver_ore", 2, 0);

    public static final RegistryObject<Block> LUMIUM_BLOCK = registerStorageBlock("lumium_block", 3.0f, 2);

    public static final RegistryObject<Block> NICKEL_BLOCK = registerStorageBlock("nickel_block", 4.0f, 2);
    public static final RegistryObject<Block> NICKEL_ORE = registerOreBlock("nickel_ore", 2, 0);

    public static final RegistryObject<Block> INVAR_BLOCK = registerStorageBlock("invar_block", 3.0f, 2);

    public static final RegistryObject<Block> ELECTRUM_BLOCK = registerStorageBlock("electrum_block", 4.0f, 2);

    public static final RegistryObject<Block> PLATINUM_BLOCK = registerStorageBlock("platinum_block", 6.0f, 3);
    public static final RegistryObject<Block> PLATINUM_ORE = registerOreBlock("platinum_ore", 3, 0);

    public static final RegistryObject<Block> ENDERIUM_BLOCK = registerStorageBlock("enderium_block", 7.0f, 3);

    public static final RegistryObject<Block> SIGNALUM_BLOCK = registerStorageBlock("signalum_block", 3.0f, 2);

    public static final RegistryObject<Block> TUNGSTEN_BLOCK = registerStorageBlock("tungsten_block", 6.0f, 3);
    public static final RegistryObject<Block> TUNGSTEN_ORE = registerOreBlock("tungsten_ore", 3, 0);

    public static final RegistryObject<Block> BRONZE_BLOCK = registerStorageBlock("bronze_block", 4.0f, 2);

    public static final RegistryObject<Block> AMETHYST_BLOCK = registerStorageBlock("amethyst_block", 6.0f, 2);
    public static final RegistryObject<Block> AMETHYST_ORE = registerOreBlock("amethyst_ore", 2, 5);

    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerStorageBlock("sapphire_block", 5.0f, 2);
    public static final RegistryObject<Block> SAPPHIRE_ORE = registerOreBlock("sapphire_ore", 2, 4);

    public static final RegistryObject<Block> OPAL_BLOCK = registerStorageBlock("opal_block", 7.0f, 3);
    public static final RegistryObject<Block> OPAL_ORE = registerOreBlock("opal_ore", 3, 17);

    public static final RegistryObject<Block> TITANIUM_BLOCK = registerStorageBlock("titanium_block", 9.0f, 3);
    public static final RegistryObject<Block> TITANIUM_ORE = registerOreBlock("titanium_ore", 3, 0);

    public static final RegistryObject<Block> URANIUM_ORE = registerOreBlock("uranium_ore", 3, 0);

    public static final RegistryObject<Block> COBALT_BLOCK = registerStorageBlock("cobalt_block", 6.0f, 3);
    public static final RegistryObject<Block> COBALT_ORE = registerOreBlock("cobalt_ore", 3, 0);

    public static final RegistryObject<Block> ZINC_BLOCK = registerStorageBlock("zinc_block", 4.0f, 2);
    public static final RegistryObject<Block> ZINC_ORE = registerOreBlock("zinc_ore", 2, 0);

    public static final RegistryObject<Block> BRASS_BLOCK = registerStorageBlock("brass_block", 4.0f, 2);

    public static final RegistryObject<Block> CHROMIUM_BLOCK = registerStorageBlock("chromium_block", 4.0f, 2);
    public static final RegistryObject<Block> CHROMIUM_ORE = registerOreBlock("chromium_ore", 2, 0);

    public static final RegistryObject<Block> THORIUM_ORE = registerOreBlock("thorium_ore", 3, 0);

    public static final RegistryObject<Block> SALTPETRE_ORE = registerOreBlock("saltpetre_ore", 1, 1);
    public static final RegistryObject<Block> SULFUR_ORE = registerOreBlock("sulfur_ore", 1, 1);

    public static final RegistryObject<Block> SALT_BLOCK = BLOCKS.register("salt_block", () -> new Block(AbstractBlock.Properties
            .create(Material.CLAY)
            .hardnessAndResistance(0.7f)
            .sound(SoundType.GROUND)
            .harvestTool(ToolType.SHOVEL)));

    public static final RegistryObject<Block> RUBBER_LOG = BLOCKS.register("rubber_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> RUBBER_WOOD = BLOCKS.register("rubber_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> RUBBER_STRIPPED_LOG = BLOCKS.register("stripped_rubber_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> RUBBER_STRIPPED_WOOD = BLOCKS.register("stripped_rubber_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> RUBBER_LEAVES = BLOCKS.register("rubber_leaves", () -> new LeavesBlock(AbstractBlock.Properties.from(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> RUBBER_PLANKS = BLOCKS.register("rubber_planks", () -> new Block(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> RUBBER_SAPLING = BLOCKS.register("rubber_sapling", () -> new SaplingBlock(new RubberTree(), AbstractBlock.Properties.from(Blocks.OAK_SAPLING)));


    public static final RegistryObject<Block> TEST = registerStorageBlock("test", 1, 1);
    public static RegistryObject<Block> registerStorageBlock(String name, float hardness, int harvestLvl) {
        return BLOCKS.register(name, () -> new Block(AbstractBlock.Properties
                .create(Material.IRON).setRequiresTool()
                .hardnessAndResistance(hardness, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(harvestLvl)
                .harvestTool(ToolType.PICKAXE)));
    }

    public static RegistryObject<Block> registerOreBlock(String name, int harvestLvl, int exp) {
        return BLOCKS.register(name, () -> new ModOreBlock(AbstractBlock.Properties
                .create(Material.ROCK).setRequiresTool()
                .hardnessAndResistance(3.0f, 6.0f)
                .sound(SoundType.METAL)
                .harvestLevel(harvestLvl)
                .harvestTool(ToolType.PICKAXE)
                , exp));
    }
}
