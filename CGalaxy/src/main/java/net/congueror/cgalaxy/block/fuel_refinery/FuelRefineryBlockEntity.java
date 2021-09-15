package net.congueror.cgalaxy.block.fuel_refinery;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.api.machine.fluid.AbstractFluidBlockEntity;
import net.congueror.clib.api.recipe.IFluidRecipe;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.IntStream;

public class FuelRefineryBlockEntity extends AbstractFluidBlockEntity {
    public FuelRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(CGBlockEntityInit.FUEL_REFINERY.get(), pos, state);
        this.tanks = IntStream.range(0, 2).mapToObj(k -> new FluidTank(15000)).toArray(FluidTank[]::new);
    }

    @Nullable
    @Override
    public IFluidRecipe<?> getRecipe() {
        assert level != null;
        return level.getRecipeManager().getRecipeFor(CGRecipeSerializerInit.Types.FUEL_REFINING, wrapper, level).orElse(null);
    }

    @Override
    public int[] invSize() {
        return new int[] {0, 1, 2, 3, 4, 5};
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{1});
        map.put("slots", new int[]{1});
        return map;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0) {
            return stack.getItem() instanceof BucketItem;
        }
        if (slot == 1) {
            return stack.getItem().equals(Items.BUCKET);
        }
        if (slot >= 2 && slot <= 5) {
            return stack.getItem() instanceof UpgradeItem;
        }
        return false;
    }

    @Override
    public int getEnergyUsage() {
        return 60;
    }

    @Override
    public int getEnergyCapacity() {
        return 10000;
    }

    @Override
    public int getProcessTime() {
        return Objects.requireNonNull(getRecipe()).getProcessTime();
    }

    @Nullable
    @Override
    public Collection<FluidStack> getFluidResults(IFluidRecipe<?> recipe) {
        return recipe.getFluidResults();
    }

    @Nullable
    @Override
    public Collection<ItemStack> getItemResults(IFluidRecipe<?> recipe) {
        return recipe.getItemResults();
    }

    @Override
    public boolean requisites() {
        return true;
    }

    @Override
    public void execute() {
        Objects.requireNonNull(getFluidResults(Objects.requireNonNull(getRecipe()))).forEach(this::storeResultFluid);
        tanks[0].drain(getFluidProcessSize(), FluidAction.EXECUTE);
    }

    @Override
    public void executeSlot() {
        emptyBucketSlot(0, 0);
        fillBucketSlot(1, 1);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
        return new FuelRefineryContainer(pContainerId, pPlayer, pInventory, this, this.data);
    }
}
