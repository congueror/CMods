package net.congueror.clib.api.data;

import net.congueror.clib.api.registry.FluidBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.Map;

public class FluidTagsDataProvider extends FluidTagsProvider {
    public FluidTagsDataProvider(DataGenerator pGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        FluidBuilder.OBJECTS.get(modId).forEach(fluidBuilder -> {
            for (Map.Entry<String, Tag.Named<Fluid>> tags : fluidBuilder.fluidTags.entrySet()) {
                tag(tags.getValue()).add(fluidBuilder.getStill().get());
            }
        });
    }
}
