package com.congueror.cores.data;

import com.congueror.cores.COres;
import com.congueror.cores.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelDataGen extends ItemModelProvider {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, COres.MODID, existingFileHelper);
    }

    protected void texture(Item item, String texture) {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(COres.MODID, texture));
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
