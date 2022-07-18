package net.congueror.clib.items;

import net.congueror.clib.util.capability.IEnergyItem;
import net.congueror.clib.util.capability.ItemEnergyWrapper;
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemEnergyWrapper(stack, this);
    }

    @Override
    public void fillItemCategory(@Nonnull CreativeModeTab pCategory, @Nonnull NonNullList<ItemStack> pItems) {
        ItemStack stack = new ItemStack(this);
        receiveEnergy(stack, getMaxEnergyStored(stack), false);
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
        return Math.round(13.0F - (getMaxEnergyStored(stack) - getEnergyStored(stack)) * 13.0F / (float)getMaxEnergyStored(stack));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable net.minecraft.world.level.Level pLevel, java.util.List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("key.clib.energy_percent").append(": ").withStyle(ChatFormatting.AQUA).append(getEnergyStored(pStack) + "/" + getMaxEnergyStored(pStack)).withStyle(ChatFormatting.GREEN));
    }
}
