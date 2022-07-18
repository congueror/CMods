package net.congueror.cgalaxy.blocks.space_station_creator;

import net.congueror.cgalaxy.util.registry.SpaceStationBlueprint;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.clib.util.PairList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;
import java.util.List;

public class DefaultBlueprint extends SpaceStationBlueprint {

    private static final PairList.Unique<Item, Integer> INGREDIENTS = new PairList.Unique<>() {{
        addImmutable(Items.POLISHED_ANDESITE, 167);
        addImmutable(CGBlockInit.LAUNCH_PAD.get().asItem(), 9);
        addImmutable(CGBlockInit.ADVANCED_DOOR.get().asItem(), 1);
    }};

    public DefaultBlueprint(ResourceLocation nbtLoc) {
        super(nbtLoc);
    }

    @Override
    public PairList.Unique<Item, Integer> getIngredients() {
        return INGREDIENTS;
    }

    @Override
    @Nonnull
    public List<Integer> getOptionalIngredients() {
        return List.of(140);
    }
}
