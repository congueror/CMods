package net.congueror.cgalaxy.blocks.space_station_creator;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGItemInit;
import net.congueror.cgalaxy.items.BlueprintItem;
import net.congueror.cgalaxy.items.SpaceStationItem;
import net.congueror.cgalaxy.util.registry.SpaceStationBlueprint;
import net.congueror.clib.blocks.machine_base.machine.AbstractItemMachineBlockEntity;
import net.congueror.clib.util.PairList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SpaceStationCreatorBlockEntity extends AbstractItemMachineBlockEntity {

    @Nullable
    SpaceStationBlueprint blueprint;
    PairList.Unique<Item, Integer> storedIngredients = new PairList.Unique<>();
    PairList.Default<Item, Integer> optionalIngredients = new PairList.Default<>();
    boolean hasChanged;

    public SpaceStationCreatorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.SPACE_STATION_CREATOR.get(), pWorldPosition, pBlockState);
        activeEnergyFaces = Set.of(pBlockState.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite());
        activeItemFaces = Set.of();
    }

    @Override
    public int getInvSize() {
        return 2;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0) {
            return stack.getItem() instanceof BlueprintItem;
        } else if (slot == 1) {
            return blueprint != null && ((optionalIngredients.size() <= blueprint.getOptionalIngredients().size()) || blueprint.getIngredients().get(stack.getItem()) != null);
        }
        return false;
    }

    @Override
    public int[] inputSlots() {
        return new int[]{};
    }

    @Override
    public int[] outputSlots() {
        return new int[]{};
    }

    @Override
    protected int getEnergyUsage() {
        return 120;
    }

    @Override
    public int getEnergyCapacity() {
        return 40000;
    }

    @Override
    public int getProcessTime() {
        return 5000;
    }

    @Override
    public int getProgressSpeedFinal() {
        return 1;
    }

    @Override
    public boolean requisites() {
        if (blueprint == null) {
            info = "key.cgalaxy.error_no_blueprint";
            return false;
        }

        if (!storedIngredients.isEmpty()) {
            AtomicBoolean flag = new AtomicBoolean(true);
            for (var e : blueprint.getIngredients()) {
                Item item = e.getKey();
                flag.set(storedIngredients.get(item) != null && storedIngredients.get(item).equals(e.getValue()));
                if (!flag.get()) break;
            }
            if (flag.get()) {
                if (blueprint.getOptionalIngredients().size() != optionalIngredients.size()) {
                    flag.set(false);
                } else {
                    for (int i = 0; i < blueprint.getOptionalIngredients().size(); i++) {
                        flag.set(optionalIngredients.get(i).getValue().equals(blueprint.getOptionalIngredients().get(i)));
                    }
                }
            }
            if (!flag.get()) info = "key.cgalaxy.error_insufficient_materials";
            return flag.get();
        }
        info = "key.cgalaxy.error_insufficient_materials";
        return false;
    }

    @Override
    public void execute() {
        ItemStack stack = new ItemStack(CGItemInit.SPACE_STATION.get(), 1);
        ((SpaceStationItem) stack.getItem()).setBlueprint(stack, blueprint);
        for (int i = 0; i < optionalIngredients.size(); i++) {
            ((SpaceStationItem) stack.getItem()).addOptionalIngredient(stack, optionalIngredients.get(i).getKey().asItem());
        }
        Containers.dropItemStack(getLevel(), getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), stack);

        optionalIngredients.clear();
        storedIngredients.clear();
        hasChanged = true;
    }

    @Override
    public void executeExtra() {
        if (hasChanged) setChanged();

        ItemStack stack = itemHandler.getStackInSlot(1);
        Item item = stack.getItem();

        if (!stack.isEmpty() && blueprint != null) {
            if (blueprint.getIngredients().get(item) == null && blueprint.hasOptionalIngredient()) {
                boolean match = false;
                for (int i = 0; i < optionalIngredients.size(); i++) {
                    if (optionalIngredients.get(i).getKey().equals(item)) {
                        match = true;
                        if (optionalIngredients.get(i).getValue() < blueprint.getOptionalIngredients().get(i)) {
                            optionalIngredients.get(i).setValue(optionalIngredients.get(i).getValue() + 1);
                            stack.shrink(1);
                            itemHandler.setStackInSlot(1, stack);
                            hasChanged = true;
                        } else {
                            match = false;
                        }
                    }
                }
                if (!match && optionalIngredients.size() < blueprint.getOptionalIngredients().size()) {
                    optionalIngredients.addMutable(item, 1);
                    stack.shrink(1);
                    itemHandler.setStackInSlot(1, stack);
                    hasChanged = true;
                }
            } else if (storedIngredients.get(item) != null && storedIngredients.get(item) < blueprint.getIngredients().get(item)) {
                storedIngredients.setValue(item, storedIngredients.get(item) + 1);
                stack.shrink(1);
                itemHandler.setStackInSlot(1, stack);
                hasChanged = true;
            } else if (storedIngredients.get(item) == null) {
                storedIngredients.addMutable(item, 1);
                stack.shrink(1);
                itemHandler.setStackInSlot(1, stack);
                hasChanged = true;
            }
        }
    }

    @Override
    public void onSlotChanged(int slot) {
        if (slot == 0) {
            //drop items
            if (itemHandler.getStackInSlot(slot).getItem() instanceof BlueprintItem b) {
                blueprint = b.getBlueprint(itemHandler.getStackInSlot(0));
                if (!this.level.isClientSide) {
                    storedIngredients = new PairList.Unique<>();
                    optionalIngredients = new PairList.Default<>();
                    dropIngredients();
                }
            } else if (itemHandler.getStackInSlot(slot).isEmpty()) {
                blueprint = null;
                if (!this.level.isClientSide) {
                    storedIngredients = new PairList.Unique<>();
                    optionalIngredients = new PairList.Default<>();
                    dropIngredients();
                }
            }
            hasChanged = true;
        }
    }

    protected void dropIngredients() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (var p : storedIngredients) {
            if (p.getValue() % 64 != 0)
                drops.add(new ItemStack(p.getKey(), p.getValue() % 64));
            for (int i = 64; i < p.getValue(); i += 64) {
                drops.add(new ItemStack(p.getKey(), 64));
            }
        }
        for (var p : optionalIngredients) {
            if (p.getValue() % 64 != 0)
                drops.add(new ItemStack(p.getKey(), p.getValue() % 64));
            for (int i = 64; i < p.getValue(); i += 64) {
                drops.add(new ItemStack(p.getKey(), 64));
            }
        }
        Containers.dropContents(getLevel(), getBlockPos().above(), drops);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new SpaceStationCreatorContainer(pContainerId, pPlayer, pInventory, this, this.data);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (!itemHandler.getStackInSlot(0).isEmpty()) {
            blueprint = ((BlueprintItem) itemHandler.getStackInSlot(0).getItem()).getBlueprint(itemHandler.getStackInSlot(0));
        }
        storedIngredients = PairList.Unique.load(nbt, "Stored",
                tag -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item"))),
                tag -> tag.getInt("value"));
        optionalIngredients = PairList.Default.load(nbt, "Optional",
                tag -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item"))),
                tag -> tag.getInt("value"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Stored", storedIngredients.save(
                (tag, itemLike) -> tag.putString("item", itemLike.asItem().getRegistryName().toString()),
                (tag, integer) -> tag.putInt("value", integer)));
        pTag.put("Optional", optionalIngredients.save(
                (tag, itemLike) -> tag.putString("item", itemLike.asItem().getRegistryName().toString()),
                (tag, integer) -> tag.putInt("value", integer)));
    }
}
