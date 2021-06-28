package com.congueror.clib.blocks;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class ModOreBlock extends OreBlock {
    private int exp;

    public ModOreBlock(Properties properties, int exp) {
        super(properties);
        this.exp = exp;
    }

    @Override
    protected int getExperience(Random rand) {
        if (exp == 0) {
            return 0;
        } else {
            return MathHelper.nextInt(rand, exp, (exp + 2));
        }
    }
}
