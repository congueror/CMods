package net.congueror.cgalaxy.blocks.sealed_cable;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.clib.util.capability.ModEnergyStorage;
import net.congueror.clib.blocks.machine_base.EnergyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class SealedEnergyCableBlockEntity extends SealedCableBlockEntity implements EnergyBlockEntity<SealedEnergyCableBlockEntity> {
    protected ModEnergyStorage energyStorage = createEnergy();
    protected LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    protected Set<Direction> activeEnergyFaces;


    public SealedEnergyCableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.SEALED_ENERGY_CABLE.get(), pWorldPosition, pBlockState);
        activeEnergyFaces = Set.of(pBlockState.getValue(BlockStateProperties.FACING), pBlockState.getValue(BlockStateProperties.FACING).getOpposite());
    }

    @Override
    public @NotNull SealedEnergyCableBlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public ModEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public int getEnergyCapacity() {
        return 1000;
    }

    @Override
    public int getMaxExtract() {
        return 1000;
    }

    @Override
    public int getMaxReceive() {
        return 1000;
    }

    @Override
    public Set<Direction> getActiveEnergyFaces() {
        return activeEnergyFaces;
    }

    @Override
    public void tick() {
        sendOutEnergy(getBlockState().getValue(BlockStateProperties.FACING));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (isEnergyCapability(cap, side)) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        energy.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        loadEnergy(nbt);
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        saveEnergy(pTag);
        super.saveAdditional(pTag);
    }
}
