package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CGalaxy.MODID);

    //public static final RegistryObject<Block> MOON_TURF = BLOCKS.register("moon_turf", () -> new );
}
