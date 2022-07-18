package net.congueror.cgalaxy.util.events;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;

public class AddSealedBlocksEvent extends Event {

    private final ArrayList<Block> blocks = new ArrayList<>();

    public ArrayList<Block> getBlocks() {
        return this.blocks;
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }
}
