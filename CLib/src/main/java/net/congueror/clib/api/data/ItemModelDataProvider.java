package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.api.registry.ItemBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ItemModelDataProvider extends ItemModelProvider {

    String modid;

    public ItemModelDataProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
        this.modid = modid;
    }

    /**
     * Simple single textured item.
     */
    public void texture(Item item, String texture) {
        singleTexture(Objects.requireNonNull(item.getRegistryName()).getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(modid, texture));
    }

    /**
     * Simple spawn egg item.
     */
    public void spawnEggTexture(Item item) {
        withExistingParent(Objects.requireNonNull(item.getRegistryName()).getPath(), "item/template_spawn_egg");
    }

    @Override
    protected void registerModels() {
        if (BlockBuilder.OBJECTS.get(modid) != null)
            BlockBuilder.OBJECTS.get(modid).forEach(builder -> {
            if (builder.itemModel != null) {
                builder.itemModel.accept(this, builder.block);
            }
        });
        if (ItemBuilder.OBJECTS.get(modid) != null)
            ItemBuilder.OBJECTS.get(modid).forEach(builder -> {
            if (builder.itemModel != null) {
                builder.itemModel.accept(this, builder.getItem());
            }
        });
    }
}
