package net.congueror.cgalaxy.data;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.data.BlockTagsDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTagsDataGen extends BlockTagsDataProvider {
    public BlockTagsDataGen(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, CGalaxy.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        super.addTags();
        tag(BlockTags.createOptional(CGalaxy.location("torches"))).add(Blocks.TORCH, Blocks.WALL_TORCH, Blocks.SOUL_TORCH, Blocks.SOUL_WALL_TORCH, Blocks.REDSTONE_TORCH, Blocks.REDSTONE_WALL_TORCH);
    }
}
