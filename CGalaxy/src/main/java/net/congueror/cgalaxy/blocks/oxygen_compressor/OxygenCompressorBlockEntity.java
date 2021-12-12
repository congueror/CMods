package net.congueror.cgalaxy.blocks.oxygen_compressor;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.cgalaxy.item.OxygenTankItem;
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
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.IntStream;

public class OxygenCompressorBlockEntity extends AbstractFluidBlockEntity {
    public OxygenCompressorBlockEntity(BlockPos pos, BlockState state) {
        super(CGBlockEntityInit.OXYGEN_COMPRESSOR.get(), pos, state);
        this.tanks = IntStream.range(0, 1).mapToObj(k -> new FluidTank(10000)).toArray(FluidTank[]::new);
    }

    @Override
    public int getSlotLimits(int slot) {
        if (slot == 2) {
            return 1;
        }
        return super.getSlotLimits(slot);
    }

    @Nullable
    @Override
    public IFluidRecipe<?> getRecipe() {
        assert level != null;
        return level.getRecipeManager().getRecipeFor(CGRecipeSerializerInit.Types.OXYGEN_COMPRESSING, wrapper, level).orElse(null);
    }

    @Override
    public int[] invSize() {
        return new int[]{0, 1, 2, 3, 4, 5, 6};
    }

    @Override
    public HashMap<String, int[]> inputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{});
        map.put("slots", new int[]{0, 1});
        return map;
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{0});
        map.put("slots", new int[]{2});
        return map;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0) {
            return true;
        }
        if (slot == 1) {
            return stack.getItem() instanceof BucketItem;
        }
        if (slot == 2) {
            return stack.getItem().equals(Items.BUCKET) || stack.getItem() instanceof OxygenTankItem;
        }
        if (slot >= 3 && slot <= 6) {
            return stack.getItem() instanceof UpgradeItem;
        }
        return false;
    }

    @Override
    public int getEnergyUsage() {
        return 50;
    }

    @Override
    public int getEnergyCapacity() {
        return 40000;
    }

    @Override
    public boolean requisites() {
        return true;
    }

    @Override
    public void execute() {
        Objects.requireNonNull(getFluidResults(Objects.requireNonNull(getRecipe()))).forEach(this::storeResultFluid);
        wrapper.getItem(0).shrink(1);
    }

    @Override
    public void executeSlot() {
        emptyBucketSlot(0, 1);
        fillBucketSlot(0, 2);
        ItemStack slot1 = wrapper.getItem(2);
        if (slot1.getItem() instanceof OxygenTankItem) {
            if (tanks[0].getFluid().getAmount() > 0) {
                int filled = ((OxygenTankItem) slot1.getItem()).fill(slot1, 1);
                tanks[0].drain(filled, FluidAction.EXECUTE);
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
        return new OxygenCompressorContainer(pContainerId, pPlayer, pInventory, this, this.data);
    }
}
