package com.congueror.clib.blocks;

import com.congueror.clib.network.Networking;
import com.congueror.clib.network.PacketUpdateFluidTanks;
import com.congueror.clib.util.ModEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.util.*;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public abstract class AbstractFluidContainer<T extends AbstractFluidTileEntity> extends Container {
    T tile;
    IIntArray data;
    IItemHandler playerInventory;
    NonNullList<FluidStack> fluidLastTick = NonNullList.create();

    public AbstractFluidContainer(@Nullable ContainerType<?> type, int id, PlayerInventory playerInventory, T tile, IIntArray dataIn) {
        super(type, id);
        this.tile = tile;
        this.data = dataIn;
        this.playerInventory = new InvWrapper(playerInventory);
        if (fluidLastTick.isEmpty()) {
            for (int i = 0; i < getFluidTanks().length; i++) {
                fluidLastTick.add(i, FluidStack.EMPTY);
            }
        }

        trackPower();
        layoutPlayerInventorySlots(28, 84);
    }

    public abstract FluidTank[] getFluidTanks();

    public abstract int getEnergyUsage();

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    protected int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    protected int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount,
                             int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < getFluidTanks().length; i++) {
            FluidStack stack = getFluidTanks()[i].getFluid();
            FluidStack stack1 = this.fluidLastTick.get(i);
            if (stack != stack1) {
                boolean stackChanged = !stack1.isFluidStackIdentical(stack);
                FluidStack stack2 = stack.copy();
                this.fluidLastTick.set(i, stack2);
                if (stackChanged) {
                    for (IContainerListener icontainerlistener : this.listeners) {
                        Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) icontainerlistener),
                                new PacketUpdateFluidTanks(windowId, stack2.getFluid().getRegistryName(), stack2.getAmount(), i));
                    }
                }
            }
        }
    }

    public void updateTanks(ResourceLocation rl, int amount, int tankId) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(rl);
        if (fluid != null) {
            tile.tanks[tankId].setFluid(new FluidStack(fluid, amount));
        } else {
            tile.tanks[tankId].setFluid(FluidStack.EMPTY);
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), playerIn, tile.getBlockState().getBlock());
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
        trackInt(new IntReferenceHolder() {
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
        trackInt(new IntReferenceHolder() {
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
