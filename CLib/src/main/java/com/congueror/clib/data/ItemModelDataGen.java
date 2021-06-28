package com.congueror.clib.data;

import com.congueror.clib.CLib;
import com.congueror.clib.init.BlockInit;
import com.congueror.clib.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelDataGen extends ItemModelProvider {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CLib.MODID, existingFileHelper);
    }

    protected void texture(Item item, String texture) {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(CLib.MODID, texture));
    }

    @Override
    protected void registerModels() {
        ItemInit.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
            if (item == ItemInit.RUBBER_SAPLING.get()) {
                texture(item, "block/" + item.toString());
            } else {
                texture(item, "item/" + item.toString());
            }
        });
    }
}
