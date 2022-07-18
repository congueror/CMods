package net.congueror.clib.blocks.machine_base.machine;

import net.congueror.clib.util.recipe.ItemRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

public abstract class AbstractRecipeItemBlockEntity<R extends ItemRecipe<?>> extends AbstractItemMachineBlockEntity {
    public AbstractRecipeItemBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    /**
     * The recipe that the block entity uses. This can be acquired by using the recipe manager like so:
     * <pre>level.getRecipeManager().getRecipe([RECIPE], wrapper, world).orElse(null)</pre>
     */
    @Nullable
    public abstract R getRecipe();

    @Override
    @Nullable
    public Collection<ItemStack> getItemResults() {
        return Objects.requireNonNull(getRecipe()).getItemResults();
    }

    @Override
    public int getProcessTime() {
        return Objects.requireNonNull(getRecipe()).getProcessTime();
    }

    @Override
    public boolean shouldRun() {
        return getRecipe() != null && super.shouldRun();
    }

    @Override
    public void executeElse() {
        if (getRecipe() == null) {
            for (int i : inputSlots()) {
                info = !wrapper.getItem(i).isEmpty() ? "key.clib.error_invalid_recipe" : "key.clib.idle";
            }
        }
    }
}
