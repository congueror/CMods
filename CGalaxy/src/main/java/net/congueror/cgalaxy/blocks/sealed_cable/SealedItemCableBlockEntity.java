package net.congueror.cgalaxy.blocks.sealed_cable;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.clib.blocks.machine_base.ItemBlockEntity;
import net.congueror.clib.blocks.machine_base.TickingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SealedItemCableBlockEntity extends SealedCableBlockEntity implements TickingBlockEntity, ItemBlockEntity<SealedItemCableBlockEntity> {
    protected ItemStackHandler itemHandler = createHandler();
    protected final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected Set<Direction> activeItemFaces;

    public SealedItemCableBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.SEALED_ITEM_CABLE.get(), pWorldPosition, pBlockState);
        activeItemFaces = Set.of(pBlockState.getValue(BlockStateProperties.FACING), pBlockState.getValue(BlockStateProperties.FACING).getOpposite());
    }

    @Override
    public @NotNull SealedItemCableBlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public int getInvSize() {
        return 1;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public Set<Direction> getActiveItemFaces() {
        return activeItemFaces;
    }

    @Override
    public void tick() {
        sendOutItem(new int[]{0}, getBlockState().getValue(BlockStateProperties.FACING));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (isItemHandlerCapability(cap, side)) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        loadItemHandler(nbt);
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        saveItemHandler(pTag);
        super.saveAdditional(pTag);
    }
}
