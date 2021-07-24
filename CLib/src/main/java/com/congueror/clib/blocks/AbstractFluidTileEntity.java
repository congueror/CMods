package com.congueror.clib.blocks;

import com.congueror.clib.util.ModEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractFluidTileEntity extends TileEntity implements IFluidHandler, ITickableTileEntity, INamedContainerProvider {
    protected ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    /**Initialization in subclass constructor like so:
     * tanks = IntStream.range(0, [Amount of tanks]).mapToObj(k -> new FluidTank([Capacity])).toArray(FluidTank[]::new);
     */
    public FluidTank[] tanks;
    private final LazyOptional<IFluidHandler> fluidHandler;

    protected ModEnergyStorage energyStorage;
    protected LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    protected float progress;
    protected int processTime;
    public static final int FIELDS_COUNT = 2;
    public final IIntArray data = new IIntArray() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return (int) progress;
                case 1:
                    return processTime;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    progress = value;
                    break;
                case 1:
                    processTime = value;
                    break;
            }
        }

        @Override
        public int size() {
            return FIELDS_COUNT;
        }
    };

    public AbstractFluidTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);

        this.energyStorage = createEnergy();
        this.fluidHandler = LazyOptional.of(() -> this);
    }

    /**
     * An array of numbers that represent slots. Must start at 0. Must include 4 additional slots at the end for upgrades.
     */
    public abstract int[] invSize();

    /**
     * Check whether an item can fit in the given slot.
     * @param slot The slot of the item
     * @param stack The ItemStack in the slot.
     */
    public abstract boolean canItemFit(int slot, ItemStack stack);

    /**
     * The amount of FE consumed per tick.
     */
    public abstract int getEnergyUsage();
    /**
     * The capacity of the energy buffer.
     */
    public abstract int getCapacity();

    /**
     * The maximum FE/t that the machine can receive.
     */
    public abstract int getMaxReceive();

    /**
     * The amount of ticks it takes for the machine to complete a process
     */
    public abstract int getProcessTime();

    /**
     * The result of the machine.
     */
    public abstract void execute();

    /**
     * What happens to a slot when it detects an item.
     */
    public abstract void executeSlot();

    @Override
    public void tick() {
        if (world != null && world.isRemote) {
            return;
        }

        FluidStack tank1 = tanks[0].getFluid();
        if (tank1.getFluid() != Fluids.EMPTY && tank1.getAmount() >= 100) {
            if (energyStorage.getEnergyStored() >= getEnergyUsage()) {
                processTime = getProcessTime();
                progress += getProgressSpeed();
                energyStorage.consumeEnergy(getEnergyUsage());
                markDirty();
                if (progress >= processTime) {
                    execute();
                    progress = 0;
                    markDirty();
                }
            }
        }

        executeSlot();
    }

    //TODO
    public int getProgressSpeed() {
        return 1;
    }

    private ModEnergyStorage createEnergy() {
        return new ModEnergyStorage(getCapacity(), getMaxReceive(), 0);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(invSize().length) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return canItemFit(slot, stack);
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getBlockState().getBlock().getTranslationKey());
    }

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
        fluidHandler.invalidate();
        energy.invalidate();
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("Progress");
        this.processTime = nbt.getInt("ProcessTime");
        energyStorage.deserializeNBT(nbt.getCompound("energy"));

        for (int i = 0; i < tanks.length; i++) {
            if (nbt.contains("tank" + i)) {
                tanks[i].setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("tank" + i)));
            }
        }

        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("Progress", (int) this.progress);
        tag.putInt("ProcessTime", this.processTime);
        tag.put("energy", energyStorage.serializeNBT());

        for (int i = 0; i < tanks.length; i++) {
            if (!tanks[i].isEmpty()) {
                tag.put("tank" + i, tanks[i].getFluid().writeToNBT(new CompoundNBT()));
            }
        }

        return super.write(tag);
    }

    @Override
    public int getTanks() {
        return tanks.length;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank < 0 || tank >= tanks.length) {
            return FluidStack.EMPTY;
        }
        return tanks[tank].getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        if (tank < 0 || tank >= tanks.length) {
            return 0;
        }
        return tanks[tank].getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        if (tank < 0 || tank >= tanks.length) {
            return false;
        }
        return tanks[tank].isFluidValid(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        for (int i = 0; i < 1; ++i) {
            FluidStack fluidInTank = tanks[i].getFluid();
            if (isFluidValid(i, resource) && (fluidInTank.isEmpty() || resource.isFluidEqual(fluidInTank))) {
                return tanks[i].fill(resource, action);
            }
        }
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) {
            return FluidStack.EMPTY;
        }

        for (int i = 0; i < 1; ++i) {
            if (resource.isFluidEqual(tanks[i].getFluid())) {
                return tanks[i].drain(resource, action);
            }
        }
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        for (int i = 0; i < 1; ++i) {
            if (tanks[i].getFluidAmount() > 0) {
                return tanks[i].drain(maxDrain, action);
            }
        }
        return FluidStack.EMPTY;
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }
}
