package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.capabilities.ItemEnergyWrapper;
import net.congueror.clib.api.machine.ModEnergyStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractProtectionUnitItem extends Item {
    private final int capacity;

    public AbstractProtectionUnitItem(Properties pProperties, int capacity) {
        super(pProperties);
        this.capacity = capacity;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemEnergyWrapper(stack, nbt, capacity);
    }

    public int getEnergy(ItemStack stack) {
        AtomicInteger stored = new AtomicInteger();
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> stored.set(iEnergyStorage.getEnergyStored()));
        return stored.get();
    }

    public int getCapacity(ItemStack stack) {
        AtomicInteger capacity = new AtomicInteger();
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> capacity.set(iEnergyStorage.getMaxEnergyStored()));
        return capacity.get();
    }

    public void drain(ItemStack stack, int amount) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> {
            if (iEnergyStorage instanceof ModEnergyStorage storage) {
                storage.consumeEnergy(amount);
            }
        });
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab pCategory, @Nonnull NonNullList<ItemStack> pItems) {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("Energy", capacity);
        if (this.allowdedIn(pCategory)) {
            pItems.add(new ItemStack(this));
            pItems.add(stack);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getEnergy(stack) != getCapacity(stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return (double) (getCapacity(stack) - getEnergy(stack)) / (double) getCapacity(stack);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable net.minecraft.world.level.Level pLevel, java.util.List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("tooltip.cgalaxy.screen.energy_percent").append(": ").withStyle(ChatFormatting.AQUA).append(getEnergy(pStack) + "/" + getCapacity(pStack)).withStyle(ChatFormatting.GREEN));
    }
}
