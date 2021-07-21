package com.congueror.cgalaxy.block.fuel_refinery;

import com.congueror.cgalaxy.init.FluidInit;
import com.congueror.cgalaxy.init.TileEntityInit;
import com.congueror.clib.util.ModEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
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
import java.util.stream.IntStream;

public class FuelRefineryTileEntity extends TileEntity implements IFluidHandler, ITickableTileEntity, INamedContainerProvider {
    protected ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public FluidTank[] tanks;
    private final LazyOptional<IFluidHandler> fluidHandler;

    protected ModEnergyStorage energyStorage = createEnergy();
    protected LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    protected int capacity, receive;
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

    public FuelRefineryTileEntity() {
        super(TileEntityInit.FUEL_REFINERY.get());
        energyStorage = createEnergy();
        capacity = 40000;
        receive = 1000;

        this.tanks = IntStream.range(0, 2).mapToObj(k -> new FluidTank(15000)).toArray(FluidTank[]::new);
        this.fluidHandler = LazyOptional.of(() -> this);
    }

    private ModEnergyStorage createEnergy() {
        return new ModEnergyStorage(capacity, receive, 0);
    }

    public int[] invSize() {
        return new int[]{0, 1, 2, 3, 4, 5};
    }

    public int getEnergyUsage() {
        return 100;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxReceive() {
        return receive;
    }

    public int getProcessTime() {
        return 1000;
    }

    public int getProgressSpeed() {
        return 1;
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }

        ItemStack inputStack = itemHandler.getStackInSlot(0);
        FluidStack tank1 = tanks[0].getFluid();
        if (tank1.getFluid() != null && tank1.getAmount() >= 100) {
            if (energyStorage.getEnergyStored() >= getEnergyUsage()) {
                processTime = getProcessTime();
                progress += getProgressSpeed();
                energyStorage.consumeEnergy(getEnergyUsage());
                markDirty();
                if (progress >= processTime) {
                    tanks[0].drain(new FluidStack(tank1.getFluid(), tank1.getAmount() - 100), FluidAction.EXECUTE);
                    tanks[1].fill(new FluidStack(FluidInit.KEROSENE.get(), 100), FluidAction.EXECUTE);
                    markDirty();
                    progress = 0;
                }
            }
        }
    }

    public boolean isBucket(ItemStack stack) {
        return stack.getItem() instanceof BucketItem;
    }

    //TODO
    public boolean isUpgrade(ItemStack stack) {
        return false;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandler.cast();
        }
        return super.getCapability(cap, side);
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
                if (slot <= 1) {
                    return FuelRefineryTileEntity.this.isBucket(stack);
                } else {
                    return false;
                }
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!FuelRefineryTileEntity.this.isBucket(stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getBlockState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new FuelRefineryContainer(p_createMenu_1_, p_createMenu_2_, this, data);
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
        this.progress = nbt.getInt("Progress");
        this.processTime = nbt.getInt("ProcessTime");
        energyStorage.deserializeNBT(nbt.getCompound("energy"));

        deserializeContents(nbt);

        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.putInt("Progress", (int) this.progress);
        tag.putInt("ProcessTime", this.processTime);
        tag.put("energy", energyStorage.serializeNBT());

        tag.put("contents", serializeContents());

        return super.write(tag);
    }

    private CompoundNBT serializeContents() {
        CompoundNBT tag = new CompoundNBT();

        for (int i = 0; i < invSize().length; i++) {
            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                tag.put("slot" + i, itemHandler.getStackInSlot(i).serializeNBT());
            }
        }

        for (int i = 0; i < tanks.length; i++) {
            if (!tanks[i].isEmpty()) {
                tag.put("tank" + i, tanks[i].getFluid().writeToNBT(new CompoundNBT()));
            }
        }

        return tag;
    }

    private void deserializeContents(CompoundNBT tag) {
        for (int i = 0; i < invSize().length; i++) {
            if (tag.contains("slot" + i)) {
                itemHandler.deserializeNBT(tag.getCompound("slot" + i));
            }
        }

        for (int i = 0; i < tanks.length; i++) {
            if (tag.contains("tank" + i)) {
                tanks[i].setFluid(FluidStack.loadFluidStackFromNBT(tag.getCompound("tank" + i)));
            }
        }
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
}
