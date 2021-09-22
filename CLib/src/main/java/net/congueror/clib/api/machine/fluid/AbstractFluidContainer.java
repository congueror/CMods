package net.congueror.clib.api.machine.fluid;

import net.congueror.clib.api.machine.AbstractInventoryContainer;
import net.congueror.clib.api.machine.ModEnergyStorage;
import net.congueror.clib.networking.CLNetwork;
import net.congueror.clib.networking.PacketUpdateFluidTanks;
import net.congueror.clib.networking.PacketUpdateInfo;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public abstract class AbstractFluidContainer<T extends AbstractFluidBlockEntity> extends AbstractInventoryContainer {
    T tile;
    ContainerData data;
    protected Player player;
    protected NonNullList<FluidStack> fluidLastTick = NonNullList.create();
    protected String infoLastTick;

    public AbstractFluidContainer(@Nullable MenuType<?> type, int id, Player player, Inventory playerInventory, T tile, ContainerData dataIn) {
        super(type, id, playerInventory);
        this.tile = tile;
        this.player = player;
        this.data = dataIn;

        trackPower();
        layoutPlayerInventorySlots(28, 84);
    }

    public abstract FluidTank[] getFluidTanks();

    public abstract int getEnergyUsage();

    public abstract int getMaxEnergy();

    public abstract int getProgress();

    public abstract int getProcessTime();

    public String getInfo() {
        return tile.info;
    }

    /**
     * Called from the {@link PacketUpdateFluidTanks} packet to sync the fluid tanks.
     */
    public void updateTanks(ResourceLocation rl, int amount, int tankId) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(rl);
        if (fluid != null) {
            tile.tanks[tankId].setFluid(new FluidStack(fluid, amount));
        } else {
            tile.tanks[tankId].setFluid(FluidStack.EMPTY);
        }
    }

    /**
     * Called from the {@link PacketUpdateInfo} packet to sync info field.
     */
    public void updateInfo(String info) {
        tile.info = info;
    }

    /**
     * Used to synchronize various information between the server and client.
     */
    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        for (int i = 0; i < getFluidTanks().length; i++) {
            FluidStack stack = getFluidTanks()[i].getFluid();
            FluidStack stack1 = this.fluidLastTick.get(i);
            if (stack != stack1) {
                boolean stackChanged = !stack1.isFluidStackIdentical(stack);
                FluidStack stack2 = stack.copy();
                this.fluidLastTick.set(i, stack2);
                if (stackChanged) {
                    if (player instanceof ServerPlayer)
                        CLNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                                new PacketUpdateFluidTanks(containerId, stack2.getFluid().getRegistryName(), stack2.getAmount(), i));
                }
            }
        }
        if (!tile.info.equals(infoLastTick)) {
            infoLastTick = tile.info;
            if (player instanceof ServerPlayer) {
                CLNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                        new PacketUpdateInfo(containerId, tile.info));
            }
        }
    }

    @Override
    public boolean stillValid(@Nonnull Player playerIn) {
        assert tile.getLevel() != null;
        return stillValid(ContainerLevelAccess.create(tile.getLevel(), tile.getBlockPos()), playerIn, tile.getBlockState().getBlock());
    }

    public int getEnergy() {
        return tile.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
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
}
