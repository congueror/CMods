package net.congueror.clib.api.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CapabilityProvider<C extends INBTSerializable<CompoundTag>> implements ICapabilityProvider, INBTSerializable<CompoundTag> {//TODO

    private final C instance;
    private final LazyOptional<C> optional;

    private final Capability<C> capability;

    public CapabilityProvider(Capability<C> capability, NonNullSupplier<C> instance) {
        this.instance = instance.get();
        this.optional = LazyOptional.of(instance);
        this.capability = capability;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return capability.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.instance.deserializeNBT(nbt);
    }
}
