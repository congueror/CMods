package com.congueror.cgalaxy.data;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelDataGen extends ItemModelProvider {
    public ItemModelDataGen(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CGalaxy.MODID, existingFileHelper);
    }

    protected void texture(Item item, String texture) {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(CGalaxy.MODID, texture));
    }

    @Override
    protected void registerModels() {
        ItemInit.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
            if (item == ItemInit.ROCKET_TIER_1.get()) {
                return;
            } else {
                texture(item, "item/" + item.toString());
            }
        });
    }
}
