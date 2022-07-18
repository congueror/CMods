package net.congueror.cgalaxy.blocks.station_core;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.util.saved_data.SpaceStationCoreObject;
import net.congueror.cgalaxy.util.saved_data.WorldSavedData;
import net.congueror.clib.blocks.machine_base.AbstractInventoryContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static net.congueror.cgalaxy.util.saved_data.WorldSavedData.CORE_LOCATIONS;

public class SpaceStationCoreContainer extends AbstractInventoryContainer {
    SpaceStationCoreObject currentObject;
    Level level;
    Player player;

    public SpaceStationCoreContainer(int id, BlockPos pos, Inventory playerInventory) {
        super(CGContainerInit.SPACE_STATION.get(), id, playerInventory);
        layoutPlayerInventorySlots(28, 84);
        this.player = playerInventory.player;
        this.level = playerInventory.player.level;
        var list = CORE_LOCATIONS.get(level.dimension());
        this.currentObject = list != null ?
                list.stream().filter(spaceStationCoreObject -> spaceStationCoreObject.getPosition().equals(pos)).findFirst()
                        .orElse(new SpaceStationCoreObject(playerInventory.player.getUUID(), pos, 1, new HashSet<>()))
                : new SpaceStationCoreObject(playerInventory.player.getUUID(), pos, 1, new HashSet<>());
    }

    public void setCore(UUID owner, BlockPos launchPadPos, int visibility, Set<UUID> list) {
        currentObject.setOwner(owner);
        currentObject.setLaunchPadPos(launchPadPos);
        currentObject.setVisibility(visibility);
        currentObject.setList(list);

        if (level instanceof ServerLevel l) WorldSavedData.update(l);
        CORE_LOCATIONS.stream(level.dimension()).filter(o -> o.getOwner().equals(owner)).findFirst()
                .ifPresent(tempOwner -> CORE_LOCATIONS.removeEntry(level.dimension(), tempOwner));
        CORE_LOCATIONS.addEntry(level.dimension(), currentObject);
        if (level instanceof ServerLevel l) WorldSavedData.makeDirty(l);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

}
