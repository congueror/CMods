package net.congueror.cgalaxy.blocks.room_pressurizer;

import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketUpdateRoomPressurizer;
import net.congueror.clib.blocks.abstract_machine.fluid.AbstractFluidContainer;
import net.congueror.clib.items.UpgradeItem;
import net.congueror.clib.networking.CLNetwork;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RoomPressurizerContainer extends AbstractFluidContainer<RoomPressurizerBlockEntity> {
    final RoomPressurizerBlockEntity be;
    Map<Direction, Integer> rangeLastTick;

    public RoomPressurizerContainer(int id, Player player, Inventory playerInventory, RoomPressurizerBlockEntity tile, ContainerData dataIn) {
        super(CGContainerInit.ROOM_PRESSURIZER.get(), id, player, playerInventory, tile, dataIn);
        this.be = tile;
        if (fluidLastTick.isEmpty()) {
            for (int i = 0; i < getFluidTanks().length; i++) {
                fluidLastTick.add(i, FluidStack.EMPTY);
            }
        }

        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 53, 17));
            addSlot(new SlotItemHandler(iItemHandler, 1, 53, 53));
            addSlot(new SlotItemHandler(iItemHandler, 2, 130, 17));
            addSlot(new SlotItemHandler(iItemHandler, 3, 130, 53));
            addSlot(new SlotItemHandler(iItemHandler, 4, 4, 4));
            addSlot(new SlotItemHandler(iItemHandler, 5, 4, 22));
            addSlot(new SlotItemHandler(iItemHandler, 6, 4, 40));
            addSlot(new SlotItemHandler(iItemHandler, 7, 4, 58));
        });

        addDataSlots(be.data);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if (!be.range.equals(rangeLastTick)) {
            rangeLastTick = getRange();
            if (player instanceof ServerPlayer sp) {
                CGNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sp),
                        new PacketUpdateRoomPressurizer(containerId, getRange()));
            }
        }
    }

    public void update(Map<Direction, Integer> map) {
        be.range = map;
    }

    public void update(boolean isBack, int ordinal) {
        rangeLastTick = null;
        int range = getRange().get(Direction.values()[ordinal]);
        if (isBack) {
            if (range > 1) {
                be.range.put(Direction.values()[ordinal], range - 1);
            }
        } else {
            if (range < be.getRange()) {
                be.range.put(Direction.values()[ordinal], range + 1);
            }
        }
    }

    public Map<Direction, Integer> getRange() {
        return be.range;
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return be.tanks;
    }

    @Override
    public int getEnergyUsage() {
        return be.getEnergyUsageFinal();
    }

    @Override
    public int getMaxEnergy() {
        return be.getEnergyCapacity();
    }

    @Override
    public int getProgress() {
        return be.data.get(0);
    }

    @Override
    public int getProcessTime() {
        return be.data.get(1);
    }

    @Override
    public ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex > 35) {
                if (!this.moveItemStackTo(itemstack1, 0, 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof UpgradeItem) {
                if (!this.moveItemStackTo(itemstack1, 38, 42, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof BucketItem) {
                if (((BucketItem) itemstack1.getItem()).getFluid().equals(Fluids.EMPTY)) {
                    if (!this.moveItemStackTo(itemstack1, 37, 38, true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.moveItemStackTo(itemstack1, 36, 37, true)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }
        return itemstack;
    }
}
