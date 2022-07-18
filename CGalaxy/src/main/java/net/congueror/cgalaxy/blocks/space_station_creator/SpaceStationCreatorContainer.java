package net.congueror.cgalaxy.blocks.space_station_creator;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.registry.SpaceStationBlueprint;
import net.congueror.cgalaxy.init.CGContainerInit;
import net.congueror.cgalaxy.items.BlueprintItem;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.networking.PacketUpdateSSCreator;
import net.congueror.clib.blocks.machine_base.machine.AbstractItemMachineContainer;
import net.congueror.clib.util.PairList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpaceStationCreatorContainer extends AbstractItemMachineContainer<SpaceStationCreatorBlockEntity> {

    public SpaceStationCreatorContainer(int id, Player player, Inventory playerInventory, SpaceStationCreatorBlockEntity tile, ContainerData dataIn) {
        super(CGContainerInit.SPACE_STATION_CREATOR.get(), id, player, playerInventory, tile, dataIn);

        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 25, 5));
            addSlot(new SlotItemHandler(iItemHandler, 1, 28, 37));
        });

        tile.hasChanged = true;

        addDataSlots(tile.data);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if (tile.hasChanged) {
            tile.hasChanged = false;
            if (player instanceof ServerPlayer) {
                CGNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                        new PacketUpdateSSCreator(containerId, tile.blueprint == null ? "" : tile.blueprint.getRegistryName().toString(), tile.storedIngredients, tile.optionalIngredients));
            }
        }
    }

    public void updateData(String blueprint, PairList.Unique<Item, Integer> stored, PairList.Default<Item, Integer> optional) {
        tile.blueprint = CGalaxy.REGISTRY.get().getValue(new ResourceLocation(blueprint));
        tile.storedIngredients = stored;
        tile.optionalIngredients = optional;
    }

    @Nullable
    public SpaceStationBlueprint getBlueprint() {
        return tile.blueprint;
    }

    public PairList.Unique<Item, Integer> getStoredIngredients() {
        return tile.storedIngredients;
    }

    public PairList.Default<Item, Integer> getOptionalIngredients() {
        return tile.optionalIngredients;
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex > 35) {
                if (!this.moveItemStackTo(itemstack1, 0, 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getItem() instanceof BlueprintItem) {
                if (!this.moveItemStackTo(itemstack1, 37, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (tile.canItemFit(1, itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 36, 37, true)) {
                    return ItemStack.EMPTY;
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
