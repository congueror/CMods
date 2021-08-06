package com.congueror.cgalaxy.gui.galaxy_map;

import com.congueror.cgalaxy.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;

public class GalaxyMapContainer extends Container {
    boolean unlocked;

    public GalaxyMapContainer(int id, boolean unlocked) {
        super(ContainerInit.GALAXY_MAP.get(), id);
        this.unlocked = unlocked;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
