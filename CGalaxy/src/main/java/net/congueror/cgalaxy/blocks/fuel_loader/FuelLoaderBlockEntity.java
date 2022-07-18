package net.congueror.cgalaxy.blocks.fuel_loader;

import net.congueror.cgalaxy.blocks.LaunchPadBlock;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.cgalaxy.util.CGConfig;
import net.congueror.clib.blocks.machine_base.machine.AbstractFluidMachineBlockEntity;
import net.congueror.clib.util.recipe.FluidRecipe;
import net.congueror.clib.items.UpgradeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.function.Predicate;

public class FuelLoaderBlockEntity extends AbstractFluidMachineBlockEntity {
    AbstractRocket entity;

    public FuelLoaderBlockEntity(BlockPos pos, BlockState state) {
        super(CGBlockEntityInit.FUEL_LOADER.get(), pos, state);
    }

    @Override
    public int getInvSize() {
        return 6;
    }

    @Override
    public int getSlotLimit(int slot) {
        if (slot == 1) {
            return 1;
        }
        return super.getSlotLimit(slot);
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0 || slot == 1) {
            return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
        } else if (slot >= 2) {
            return stack.getItem() instanceof UpgradeItem;
        }
        return false;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public int getTankCapacity(int tank) {
        return 3400;
    }

    @Nullable
    @Override
    public FluidRecipe<?> getRecipe() {
        assert level != null;
        return level.getRecipeManager().getRecipeFor(CGRecipeSerializerInit.FUEL_LOADING_TYPE.get(), wrapper, level).orElse(null);
    }

    @Override
    public HashMap<String, int[]> inputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{0});
        map.put("slots", new int[]{0});
        return map;
    }

    @Override
    public HashMap<String, int[]> outputSlotsAndTanks() {
        HashMap<String, int[]> map = new HashMap<>();
        map.put("tanks", new int[]{0});
        map.put("slots", new int[]{1});
        return map;
    }

    @Override
    public int getEnergyUsage() {
        return CGConfig.FUEL_LOADER_ENERGY_USAGE.get();
    }

    @Override
    public int getEnergyCapacity() {
        return CGConfig.FUEL_LOADER_ENERGY_CAPACITY.get();
    }

    @Override
    public boolean requisites() {
        for (Direction dir : Direction.values()) {
            if (dir.equals(Direction.DOWN) || dir.equals(Direction.UP)) {
                continue;
            }
            BlockPos pos = getBlockPos().relative(dir);
            assert level != null;
            if (level.getBlockState(pos).is(CGBlockInit.LAUNCH_PAD.get())) {
                LaunchPadBlock pad = (LaunchPadBlock) level.getBlockState(pos).getBlock();
                if (pad.getMidBlock(level, pos) != null) {
                    BlockPos mid = pad.getMidBlock(level, pos);
                    if (((LaunchPadBlock) level.getBlockState(mid).getBlock()).getRocket(level, mid) != null) {
                        entity = ((LaunchPadBlock) level.getBlockState(mid).getBlock()).getRocket(level, mid);
                        if (entity.getFuel() < entity.getCapacity()) {
                            return true;
                        } else {
                            info = "key.cgalaxy.idle_rocket_full";
                        }
                    } else {
                        info = "key.cgalaxy.error_missing_rocket";
                    }
                } else {
                    info = "key.cgalaxy.error_incomplete_pad";
                }
            }
        }
        return false;
    }

    @Override
    public void execute() {
        FuelLoaderRecipe recipe = (FuelLoaderRecipe) getRecipe();
        assert recipe != null;
        int amount = recipe.getIngredient().getFluidStacks().get(0).getAmount();
        int fill = entity.fill(Math.min(tanks[0].getFluidAmount(), amount + getFluidProcessSize()));
        tanks[0].drain(fill, FluidAction.EXECUTE);
    }

    @Override
    public void executeExtra() {
        emptyFluidSlot(0, 0);
        fillFluidSlot(0, 1);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
        return new FuelLoaderContainer(pContainerId, pPlayer, pInventory, this, data);
    }
}
