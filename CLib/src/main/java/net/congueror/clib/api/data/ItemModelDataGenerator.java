package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;

public class ItemModelDataGenerator extends ItemModelProvider {

    String modid;

    protected ItemModelDataGenerator(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
        this.modid = modid;
    }

    /**
     * Simple single textured item.
     */
    public void texture(Item item, String texture) {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(modid, texture));
    }

    @Override
    protected void registerModels() {
        BlockBuilder.OBJECTS.forEach(builder -> {
            if (builder.itemModel != null) {
                builder.itemModel.accept(this, builder.getBlock());
            }
        });
        ItemBuilder.OBJECTS.forEach(builder -> {
            if (builder.itemModel != null) {
                builder.itemModel.accept(this, builder.getItem());
            } else {
                texture(builder.getItem(), "item/" + builder.getItem());
            }
        });
    }
}
