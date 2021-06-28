package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CGalaxy.MODID);

    public static final RegistryObject<Block> MOON_TURF = BLOCKS.register("moon_turf", () -> new FallingBlock(AbstractBlock.Properties
            .create(Material.SAND).hardnessAndResistance(1.0F).sound(SoundType.SAND).harvestTool(ToolType.SHOVEL)));

    public static final RegistryObject<Block> MOON_STONE = BLOCKS.register("moon_stone", () -> new Block(AbstractBlock.Properties
            .create(Material.ROCK).hardnessAndResistance(2f, 6.5f).sound(SoundType.STONE).harvestLevel(1).harvestTool(ToolType.PICKAXE)));
}
