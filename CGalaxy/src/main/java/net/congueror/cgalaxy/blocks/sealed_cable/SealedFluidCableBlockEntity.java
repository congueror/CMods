package net.congueror.cgalaxy.blocks.sealed_cable;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.clib.blocks.machine_base.FluidBlockEntity;
import net.congueror.clib.blocks.machine_base.TickingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class SealedFluidCableBlockEntity extends SealedCableBlockEntity implements TickingBlockEntity, FluidBlockEntity<SealedFluidCableBlockEntity> {

    public FluidTank[] tanks = createFluidTanks();
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this);
    protected Set<Direction> activeFluidFaces;

    public SealedFluidCableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.SEALED_FLUID_CABLE.get(), pWorldPosition, pBlockState);
        activeFluidFaces = Set.of(pBlockState.getValue(BlockStateProperties.FACING), pBlockState.getValue(BlockStateProperties.FACING).getOpposite());
    }

    @Override
    @Nonnull
    public SealedFluidCableBlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public FluidTank[] getFluidTanks() {
        return tanks;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public int getTankCapacity(int tank) {
        return 1000;
    }

    @Override
    public Set<Direction> getActiveFluidFaces() {
        return activeFluidFaces;
    }

    @Override
    public void tick() {
        sendOutFluid(new int[]{0}, getBlockState().getValue(BlockStateProperties.FACING));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (isFluidHandlerCapability(cap, side)) {
            return fluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidHandler.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        loadFluidTanks(nbt);
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        saveFluidTanks(pTag);
        super.saveAdditional(pTag);
    }
}
