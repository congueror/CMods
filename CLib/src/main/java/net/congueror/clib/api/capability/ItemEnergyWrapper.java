package net.congueror.clib.api.capability;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemEnergyWrapper implements ICapabilityProvider {
    protected final ItemEnergyStorage energyStorage;
    private final ItemStack stack;
    private final IEnergyItem energyItem;

    public ItemEnergyWrapper(ItemStack stack, IEnergyItem item) {
        this.stack = stack;
        this.energyItem = item;
        this.energyStorage = createEnergy();
    }

    private ItemEnergyStorage createEnergy() {
        return new ItemEnergyStorage(stack, energyItem);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CapabilityEnergy.ENERGY.orEmpty(cap, LazyOptional.of(() -> this.energyStorage));
    }
}