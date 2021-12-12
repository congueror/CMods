package net.congueror.cgalaxy.gui.galaxy_map;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketSyncMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GalaxyMapContainer extends AbstractContainerMenu {
    public boolean unlocked;
    private boolean unlockedLastTick;
    @Nullable
    public GalacticObjectBuilder.GalacticObject<?> map;
    Player player;

    public GalaxyMapContainer(int id, Player player, boolean unlocked, @Nullable GalacticObjectBuilder.GalacticObject<?> map) {
        super(CGContainerInit.GALAXY_MAP.get(), id);
        this.player = player;
        this.unlocked = unlocked;
        this.map = map;
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if (this.unlocked != this.unlockedLastTick) {
            this.unlockedLastTick = this.unlocked;
            if (player instanceof ServerPlayer player1) {
                CGNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player1),
                        new PacketSyncMap(containerId, unlocked, map == null ? new ResourceLocation("null") : map.getId()));
            }
        }
    }

    public void sync(boolean unlocked, ResourceLocation name) {
        this.unlocked = unlocked;
        this.map = GalacticObjectBuilder.getObjectFromId(name);
    }

    @Override
    public boolean stillValid(@Nonnull Player playerIn) {
        return true;
    }
}