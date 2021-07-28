package com.congueror.clib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;

public class ItemModelDataGenerator extends ItemModelProvider {

    String modid;
    DeferredRegister<Item> itemRegistry;
    Item[] exceptions;

    protected ItemModelDataGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
        this.modid = modid;
    }

    /**
     * @param itemRegistry The DeferredRegistry for items in your mod.
     * @param exceptions   An array list of items that will be skipped when adding textures.
     */
    public ItemModelDataGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper, DeferredRegister<Item> itemRegistry, Item... exceptions) {
        this(generator, modid, existingFileHelper);
        this.itemRegistry = itemRegistry;
        this.exceptions = exceptions;
    }

    /**
     * Simple single textured item.
     */
    public void texture(Item item, String texture) {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(modid, texture));
    }

    /**
     * Override if you want custom models for your exceptions.
     */
    @Override
    protected void registerModels() {
        itemRegistry.getEntries().stream().map(RegistryObject::get).filter(item -> !Arrays.asList(exceptions).contains(item)).forEach(item -> {
            if (!(item instanceof BlockItem)) {
                ResourceLocation rl = new ResourceLocation(modid, "item/" + item);
                //Checks whether the texture exists so the process doesn't stop if it can't find a texture
                if (existingFileHelper.exists(rl, ResourcePackType.CLIENT_RESOURCES, ".png", "textures")) {
                    texture(item, "item/" + item);
                }
            }
        });
    }
}
