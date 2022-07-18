package net.congueror.clib.blocks.machine_base.machine;

import net.congueror.clib.util.capability.ModEnergyStorage;
import net.congueror.clib.blocks.machine_base.EnergyBlockEntity;
import net.congueror.clib.blocks.machine_base.ItemBlockEntity;
import net.congueror.clib.blocks.machine_base.TickingBlockEntity;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

public abstract class AbstractItemMachineBlockEntity extends BlockEntity implements MenuProvider, TickingBlockEntity, EnergyBlockEntity<AbstractItemMachineBlockEntity>, ItemBlockEntity<AbstractItemMachineBlockEntity> {
    protected ModEnergyStorage energyStorage = createEnergy();
    protected LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
    @Nonnull
    protected Set<Direction> activeEnergyFaces = Set.of(Direction.values());
    protected boolean sendOutEnergy = true;

    protected ItemStackHandler itemHandler = createHandler();
    protected final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected RecipeWrapper wrapper = new RecipeWrapper(itemHandler);
    @Nonnull
    protected Set<Direction> activeItemFaces = Set.of(Direction.values());
    protected boolean sendOutItems = false;

    protected float progress;
    protected int processTime;
    public String info = "";
    public static final int FIELDS_COUNT = 2;
    public final ContainerData data = new ContainerData() {

        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> (int) progress;
                case 1 -> processTime;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> processTime = value;
            }
        }

        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };

    public AbstractItemMachineBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    /**
     * An array of all input slot indexes.
     */
    public abstract int[] inputSlots();

    /**
     * An array of all output slot indexes.
     */
    public abstract int[] outputSlots();

    /**
     * The amount of FE consumed per tick.
     */
    protected abstract int getEnergyUsage();

    /**
     * The amount of ticks it takes for the machine to complete a process
     */
    public abstract int getProcessTime();

    /**
     * Here you can add any additional requirements for the machine to start
     *
     * @return True if the machine should run.
     */
    public abstract boolean requisites();

    /**
     * Gets executed when sufficient progress is met and {@link #requisites()} are true.
     */
    public abstract void execute();

    /**
     * Gets executed regardless of the state of the machine. this is where slot logic should happen.
     */
    public abstract void executeExtra();

    /**
     * Gets executed only when {@link #shouldRun()} returns false.
     */
    public void executeElse() {

    }

    /**
     * The range of the machine. Unless overriding, use {@link #getRangeFinal()}.
     *
     * @return Initial range of machine in blocks.
     */
    protected int getRange() {
        return -1;
    }

    @Override
    public @NotNull AbstractItemMachineBlockEntity getBlockEntity() {
        return this;
    }

    @Override
    public ModEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public Set<Direction> getActiveEnergyFaces() {
        return activeEnergyFaces;
    }

    @Override
    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public int getSlotLimit(int slot) {
        if (getInvSize() - 4 > 0 && slot >= getInvSize() - 4) {
            return 1;
        } else {
            return 64;
        }
    }

    @Override
    public int getStackLimit(int slot, @NotNull ItemStack stack) {
        if (stack.getItem() instanceof UpgradeItem) {
            return 1;
        }
        return ItemBlockEntity.super.getStackLimit(slot, stack);
    }

    public Set<Direction> getActiveItemFaces() {
        return activeItemFaces;
    }

    @Override
    public void tick() {
        if (level != null && level.isClientSide) {
            return;
        }

        if (shouldRun()) {
            if (energyStorage.getEnergyStored() >= getEnergyUsageFinal() && requisites()) {
                processTime = getProcessTime();
                progress += getProgressSpeedFinal();
                energyStorage.consumeEnergy(getEnergyUsageFinal());
                info = "key.clib.working";
                setChanged();
                if (progress >= processTime) {
                    execute();
                    progress = 0;
                    sendUpdate(getActiveState());
                    setChanged();
                } else {
                    sendUpdate(getActiveState());
                }
            } else {
                if (energyStorage.getEnergyStored() < getEnergyUsageFinal()) {
                    info = "key.clib.error_insufficient_energy";
                }
                if (progress > 0) {
                    progress--;
                }
            }
        } else {
            executeElse();
            for (int i : inputSlots()) {
                if (wrapper.getItem(i) == null) {
                    progress = 0;
                }
            }
            sendUpdate(getInactiveState());
        }

        if (sendOutItems) {
            sendOutItem(outputSlots());
        }

        if (getMaxExtract() > 0 && sendOutEnergy) {
            sendOutEnergy();
        }
        executeExtra();
    }

    /**
     * The item results of this machine.
     *
     * @return A {@link Collection} of {@link ItemStack}s.
     */
    @Nullable
    public Collection<ItemStack> getItemResults() {
        return null;
    }

    /**
     * Checks whether the block entity should run.
     */
    public boolean shouldRun() {
        return true;
    }

    /**
     * Puts the resulting item stack in the output slots.
     *
     * @param results A collection of {@link ItemStack}s, usually used with {@link #getItemResults()}.
     */
    protected void storeResultItem(Collection<ItemStack> results) {
        results.forEach(itemStack -> {
            for (int i : outputSlots()) {
                ItemStack output = wrapper.getItem(i);
                if (canItemsStack(itemStack, output)) {
                    if (output.isEmpty()) {
                        wrapper.setItem(i, itemStack);
                    } else {
                        output.setCount(output.getCount() + itemStack.getCount());
                    }
                }
            }
        });
    }

    /**
     * Calculates the range that the machine will function in.
     *
     * @return The range in blocks.
     */
    public int getRangeFinal() {
        int[] slots = new int[]{getInvSize() - 1, getInvSize() - 2, getInvSize() - 3, getInvSize() - 4};
        int range = getRange();
        for (int i = 0; i < slots.length; i++) {
            if (itemHandler.getStackInSlot(i).getItem() instanceof UpgradeItem) {
                if (((UpgradeItem) itemHandler.getStackInSlot(i).getItem()).getType().equals(UpgradeItem.UpgradeType.RANGE)) {
                    range += getRange() / 2;
                }
            }
        }
        return range;
    }

    /**
     * Calculates how much the {@link #progress} will be increased each tick.
     *
     * @return the progress per tick.
     */
    public int getProgressSpeedFinal() {
        int[] slots = new int[]{getInvSize() - 1, getInvSize() - 2, getInvSize() - 3, getInvSize() - 4};
        int progress = 1;
        for (int i = 0; i < slots.length; i++) {
            if (itemHandler.getStackInSlot(i).getItem() instanceof UpgradeItem) {
                if (((UpgradeItem) itemHandler.getStackInSlot(i).getItem()).getType().equals(UpgradeItem.UpgradeType.SPEED)) {
                    progress += 1;
                }
            }
        }
        return progress;
    }

    public int getEnergyUsageFinal() {//TODO
        int usage = getEnergyUsage();
        return usage;
    }

    public void fillEnergySlot(int... slots) {
        for (int i : slots) {
            ItemStack slot = wrapper.getItem(i);
            slot.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> {
                if (iEnergyStorage.getEnergyStored() + 4 < iEnergyStorage.getMaxEnergyStored() && energyStorage.getEnergyStored() > 4) {
                    iEnergyStorage.receiveEnergy(4, false);
                    energyStorage.consumeEnergy(4);
                } else if (iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored() && energyStorage.getEnergyStored() > 0) {
                    iEnergyStorage.receiveEnergy(1, false);
                    energyStorage.consumeEnergy(1);
                }
            });
        }
    }

    /**
     * Used by block to drop contents when broken.
     *
     * @return A list of item stacks inside the te.
     */
    public NonNullList<ItemStack> getDrops() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < getInvSize(); i++) {
            drops.add(itemHandler.getStackInSlot(i));
        }
        return drops;
    }

    /**
     * Determines if the given stacks can stack.
     */
    public static boolean canItemsStack(ItemStack a, ItemStack b) {
        if (a.isEmpty() || b.isEmpty()) return true;
        return ItemHandlerHelper.canItemStacksStack(a, b) && a.getCount() + b.getCount() <= a.getMaxStackSize();
    }

    protected void sendUpdate(BlockState newState) {
        if (level == null)
            return;
        BlockState oldState = level.getBlockState(worldPosition);
        if (oldState != newState) {
            level.setBlock(worldPosition, newState, 3);
            level.sendBlockUpdated(worldPosition, oldState, newState, 3);
        }
    }

    protected BlockState getActiveState() {
        return getBlockState().setValue(AbstractFurnaceBlock.LIT, true);
    }

    protected BlockState getInactiveState() {
        return getBlockState().setValue(AbstractFurnaceBlock.LIT, false);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (isEnergyCapability(cap, side)) {
            return energy.cast();
        } else if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && (side == null || getActiveItemFaces().contains(side))) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        energy.invalidate();
        handler.invalidate();
    }

    @Override
    public void load(CompoundTag nbt) {
        loadEnergy(nbt);
        loadItemHandler(nbt);
        this.progress = nbt.getInt("Progress");
        this.processTime = nbt.getInt("ProcessTime");

        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        saveEnergy(pTag);
        saveItemHandler(pTag);
        pTag.putInt("Progress", (int) this.progress);
        pTag.putInt("ProcessTime", this.processTime);

        super.saveAdditional(pTag);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return new TranslatableComponent(this.getBlockState().getBlock().getDescriptionId());
    }
}
