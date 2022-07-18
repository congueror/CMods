package net.congueror.clib.blocks.machine_base;

import net.congueror.clib.util.capability.ModEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public interface EnergyBlockEntity<T extends BlockEntity> {
    default ModEnergyStorage createEnergy() {
        return new ModEnergyStorage(getEnergyCapacity(), getMaxReceive(), getMaxExtract()) {
            @Override
            protected void onEnergyChanged() {
                EnergyBlockEntity.this.onEnergyChanged();
                getBlockEntity().setChanged();
            }
        };
    }

    T getBlockEntity();

    ModEnergyStorage getEnergyStorage();

    /**
     * The capacity of the energy buffer.
     */
    int getEnergyCapacity();

    /**
     * The maximum amount of energy that can be received from other machines.
     *
     * @return max receive amount.
     */
    default int getMaxReceive() {
        return 1000;
    }

    /**
     * The maximum amount of energy that can be extracted from the machine.
     *
     * @return max extract amount.
     */
    default int getMaxExtract() {
        return 0;
    }

    default void onEnergyChanged() {

    }

    default Set<Direction> getActiveEnergyFaces() {
        return Set.of(Direction.values());
    }

    default <C> boolean isEnergyCapability(Capability<C> cap, Direction side) {
        return cap == CapabilityEnergy.ENERGY && (side == null || getActiveEnergyFaces().contains(side));
    }

    default void loadEnergy(CompoundTag nbt) {
        getEnergyStorage().deserializeNBT(nbt.getCompound("energy"));
    }

    default void saveEnergy(CompoundTag tag) {
        tag.put("energy", getEnergyStorage().serializeNBT());
    }

    /**
     * Outputs energy from the block in all active directions
     */
    default void sendOutEnergy() {
        sendOutEnergy(getActiveEnergyFaces().toArray(Direction[]::new));
    }

    /**
     * Outputs energy from the block in the given directions
     */
    default void sendOutEnergy(Direction... directions) {
        AtomicInteger capacity = new AtomicInteger(getEnergyStorage().getEnergyStored());
        if (capacity.get() > 0) {
            for (Direction direction : directions) {
                BlockEntity be = getBlockEntity().getLevel().getBlockEntity(getBlockEntity().getBlockPos().offset(new Vec3i(0, 0, 0).relative(direction)));
                if (be != null) {
                    boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
                                if (handler.canReceive()) {
                                    int received = handler.receiveEnergy(Math.min(capacity.get(), getMaxExtract()), false);
                                    capacity.addAndGet(-received);
                                    getEnergyStorage().consumeEnergy(received);
                                    getBlockEntity().setChanged();
                                    return capacity.get() > 0;
                                } else {
                                    return true;
                                }
                            }
                    ).orElse(true);
                    if (!doContinue) {
                        return;
                    }
                }
            }
        }
    }
}
