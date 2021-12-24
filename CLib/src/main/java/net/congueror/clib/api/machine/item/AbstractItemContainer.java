package net.congueror.clib.api.machine.item;

import net.congueror.clib.api.machine.AbstractInventoryContainer;
import net.congueror.clib.api.machine.ModEnergyStorage;
import net.congueror.clib.networking.CLNetwork;
import net.congueror.clib.networking.PacketUpdateInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractItemContainer<T extends AbstractItemBlockEntity<?>> extends AbstractInventoryContainer {
    protected T tile;
    ContainerData data;
    protected Player player;
    protected String infoLastTick;


    protected AbstractItemContainer(@Nullable MenuType<?> type, int id, Player player, Inventory playerInventory, T tile, ContainerData dataIn) {
        super(type, id, playerInventory);
        this.tile = tile;
        this.data = dataIn;
        this.player = player;

        trackPower();
        layoutPlayerInventorySlots(28, 84);
    }

    public int getEnergy() {
        return tile.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getEnergyUsage() {
        return tile.getEnergyUsageFinal();
    }

    public int getMaxEnergy() {
        return tile.getEnergyCapacity();
    }

    public int getProgress() {
        return tile.data.get(0);
    }

    public int getProcessTime() {
        return tile.data.get(1);
    }

    public String getInfo() {
        return tile.info;
    }


    /**
     * Called from the {@link PacketUpdateInfo} packet to sync info field.
     */
    public void updateInfo(String info) {
        tile.info = info;
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if (!tile.info.equals(infoLastTick)) {
            infoLastTick = tile.info;
            if (player instanceof ServerPlayer) {
                CLNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                        new PacketUpdateInfo(containerId, tile.info));
            }
        }
    }

    // Setup syncing of power from server to client so that the GUI can show the
    // amount of power in the block
    private void trackPower() {
        // Unfortunatelly on a dedicated server ints are actually truncated to short so
        // we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                tile.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((ModEnergyStorage) h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                tile.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((ModEnergyStorage) h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

    @Override
    public boolean stillValid(@Nonnull Player playerIn) {
        assert tile.getLevel() != null;
        return stillValid(ContainerLevelAccess.create(tile.getLevel(), tile.getBlockPos()), playerIn, tile.getBlockState().getBlock());
    }
}
