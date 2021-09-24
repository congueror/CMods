package net.congueror.cgalaxy.gui.galaxy_map;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketUnlockMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nonnull;

public class GalaxyMapContainer extends AbstractContainerMenu {
    boolean unlocked;

    public GalaxyMapContainer(int id, ServerPlayer player, boolean unlocked) {
        super(CGContainerInit.GALAXY_MAP.get(), id);
        CGNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),
                new PacketUnlockMap(unlocked));
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    @Override
    public boolean stillValid(@Nonnull Player playerIn) {
        return true;
    }
}
