package net.congueror.cgalaxy.capabilities;

import net.congueror.clib.api.machine.ModEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemEnergyWrapper implements ICapabilityProvider {
    private final int capacity;
    protected final ModEnergyStorage energyStorage;
    protected final LazyOptional<ModEnergyStorage> handler;
    private final ItemStack stack;
    @Nullable
    private final CompoundTag nbt;

    public ItemEnergyWrapper(ItemStack stack, @Nullable CompoundTag nbt, int capacity) {
        this.stack = stack;
        this.nbt = nbt;
        this.capacity = capacity;
        this.energyStorage = createEnergy();
        this.handler = LazyOptional.of(() -> energyStorage);
    }

    private ModEnergyStorage createEnergy() {
        int energy1 = stack.getOrCreateTag().getInt("Energy");
        return new ModEnergyStorage(capacity, 100, 100, energy1) {
            @Override
            protected void onEnergyChanged() {
                stack.getOrCreateTag().putInt("Energy", energy);
                super.onEnergyChanged();
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityEnergy.ENERGY)) {
            return handler.cast();
        }
        return null;
    }
}