package net.congueror.clib.blocks.solar_generator;

import net.congueror.clib.blocks.abstract_machine.item.AbstractItemContainer;
import net.congueror.clib.init.CLContainerInit;
import net.congueror.clib.items.UpgradeItem;
import net.congueror.clib.networking.CLNetwork;
import net.congueror.clib.networking.PacketUpdateSolarGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;

public class SolarGeneratorContainer extends AbstractItemContainer<SolarGeneratorBlockEntity> {
    int generationLastTick;

    public SolarGeneratorContainer(int id, Player player, Inventory playerInventory, SolarGeneratorBlockEntity tile, ContainerData dataIn) {
        super(CLContainerInit.SOLAR_GENERATOR.get(), id, player, playerInventory, tile, dataIn);

        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            addSlot(new SlotItemHandler(iItemHandler, 0, 137, 53));
            addSlot(new SlotItemHandler(iItemHandler, 1, 4, 4));
            addSlot(new SlotItemHandler(iItemHandler, 2, 4, 22));
            addSlot(new SlotItemHandler(iItemHandler, 3, 4, 40));
            addSlot(new SlotItemHandler(iItemHandler, 4, 4, 58));
        });

        addDataSlots(tile.data);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        if (tile.energyGen != generationLastTick) {
            generationLastTick = tile.energyGen;
            if (player instanceof ServerPlayer) {
                CLNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                        new PacketUpdateSolarGenerator(containerId, tile.energyGen));
            }
        }
    }

    public void updateData(int generation) {
        tile.energyGen = generation;
    }

    public int getEnergyGen() {
        return tile.energyGen;
    }

    public ResourceLocation texture() {
        return tile.getTexture();
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
            } else if (itemstack1.getItem() instanceof UpgradeItem) {
                if (!this.moveItemStackTo(itemstack1, 37, 41, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.getCapability(CapabilityEnergy.ENERGY).isPresent()) {
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
