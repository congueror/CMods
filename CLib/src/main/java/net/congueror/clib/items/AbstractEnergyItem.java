package net.congueror.clib.items;

import net.congueror.clib.api.capability.IEnergyItem;
import net.congueror.clib.api.capability.ItemEnergyWrapper;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractEnergyItem extends Item implements IEnergyItem {
    public AbstractEnergyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab pCategory, @Nonnull NonNullList<ItemStack> pItems) {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("energy", getMaxEnergyStored(stack));
        if (this.allowdedIn(pCategory)) {
            pItems.add(new ItemStack(this));
            pItems.add(stack);
        }
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getEnergyStored(stack) != getMaxEnergyStored(stack);
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        AbstractEnergyItem item = (AbstractEnergyItem) stack.getItem();
        return Math.round(13.0F - (item.getMaxEnergyStored(stack) - item.getEnergyStored(stack)) * 13.0F / (float)item.getMaxEnergyStored(stack));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable net.minecraft.world.level.Level pLevel, java.util.List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("key.clib.energy_percent").append(": ").withStyle(ChatFormatting.AQUA).append(getEnergyStored(pStack) + "/" + getMaxEnergyStored(pStack)).withStyle(ChatFormatting.GREEN));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemEnergyWrapper(stack, this);
    }

    @Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        int energy = getEnergyStored(stack);
        int energyReceived = Math.min(getMaxEnergyStored(stack) - energy, Math.min(getMaxReceive(stack), maxReceive));
        if (!simulate) {
            energy += energyReceived;
            stack.getOrCreateTag().putInt("energy", energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        int energy = getEnergyStored(stack);
        int energyExtracted = Math.min(energy, Math.min(getMaxExtract(stack), maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            stack.getOrCreateTag().putInt("energy", energy);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ItemStack stack) {
        return stack.getOrCreateTag().getInt("energy");
    }
}
