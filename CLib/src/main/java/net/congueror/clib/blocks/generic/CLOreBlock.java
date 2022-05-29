package net.congueror.clib.blocks.generic;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraftforge.common.ToolAction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CLOreBlock extends OreBlock {

    private final Map<ToolAction, Supplier<? extends Block>> modifiedState = new HashMap<>();

    public CLOreBlock(Properties properties, int exp) {
        super(properties, exp == 0 ? UniformInt.of(0, 0) : UniformInt.of(exp, exp + 2));
    }
}
