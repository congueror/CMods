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
    public boolean unlocked;
    private boolean unlockedLastTick;
    Player player;

    public GalaxyMapContainer(int id, Player player, boolean unlocked) {
        super(CGContainerInit.GALAXY_MAP.get(), id);
        this.player = player;
        this.unlocked = unlocked;
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if (this.unlocked != this.unlockedLastTick) {
            this.unlockedLastTick = this.unlocked;
            if (player instanceof ServerPlayer player1) {
                CGNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player1),
                        new PacketUnlockMap(containerId, unlocked));
            }
        }
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    @Override
    public boolean stillValid(@Nonnull Player playerIn) {
        return true;
    }
}
