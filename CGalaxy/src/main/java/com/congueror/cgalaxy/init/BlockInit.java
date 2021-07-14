package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.block.LaunchPadBlock;
import com.congueror.cgalaxy.block.fuel_refinery.FuelRefineryBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
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

    public static final RegistryObject<Block> LAUNCH_PAD = BLOCKS.register("launch_pad", () -> new LaunchPadBlock(AbstractBlock.Properties.from(Blocks.IRON_BLOCK)));

    public static final RegistryObject<FlowingFluidBlock> KEROSENE = BLOCKS.register("kerosene", () -> new FlowingFluidBlock(FluidInit.KEROSENE_STILL, AbstractBlock.Properties
            .create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100f).noDrops()));

    public static final RegistryObject<Block> FUEL_REFINERY = BLOCKS.register("fuel_refinery", () -> new FuelRefineryBlock(AbstractBlock.Properties.create(Material.IRON)
            .hardnessAndResistance(6f).sound(SoundType.METAL)));
}
