package com.congueror.clib.items;

import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ModSaplingItem extends BlockItem {
    public ModSaplingItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    //TODO
    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this == ItemInit.RUBBER_SAPLING.get()) {
            items.add(BlockInit.RUBBER_LEAVES.get().asItem().getGroup().getIndex() + 1, new ItemStack(this));
        }
    }
}