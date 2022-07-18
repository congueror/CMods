package net.congueror.clib.items;

import net.congueror.clib.util.capability.IFluidItem;
import net.congueror.clib.util.capability.ItemFluidWrapper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public abstract class AbstractFluidItem extends Item implements IFluidItem {
    public AbstractFluidItem(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemFluidWrapper(stack, this);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getFluid(stack).getAmount() != getCapacity();
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return getFluid(pStack).getFluid().getAttributes().getColor();
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return Math.round(13.0F - (getCapacity() - getFluid(stack).getAmount()) * 13.0F / (float) getCapacity());
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @javax.annotation.Nullable net.minecraft.world.level.Level pLevel, java.util.List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        MutableComponent c = new TranslatableComponent(getFluid(pStack).isEmpty() ? "key.clib.empty" : getFluid(pStack).getTranslationKey()).append(getFluid(pStack).isEmpty() ? "" : " " + getFluid(pStack).getAmount() + "/" + getCapacity());
        pTooltipComponents.add(new TranslatableComponent("key.clib.fluid").append(": ").withStyle(ChatFormatting.AQUA).append(c).withStyle(ChatFormatting.GREEN));
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (this.allowdedIn(pCategory)) {
            pItems.add(new ItemStack(this));
            ForgeRegistries.FLUIDS.forEach(fluid -> {
                ItemStack stack = new ItemStack(this);
                if (setFluid(stack, new FluidStack(fluid, getCapacity())))
                    pItems.add(stack);
            });
        }
    }
}
