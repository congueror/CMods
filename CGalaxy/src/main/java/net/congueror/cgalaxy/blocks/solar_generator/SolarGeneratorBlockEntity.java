package net.congueror.cgalaxy.blocks.solar_generator;

import net.congueror.cgalaxy.init.CGBlockEntityInit;
import net.congueror.cgalaxy.init.CGRecipeSerializerInit;
import net.congueror.clib.blocks.machine_base.machine.AbstractRecipeItemBlockEntity;
import net.congueror.clib.init.CLBlockEntityInit;
import net.congueror.clib.init.CLRecipeSerializerInit;
import net.congueror.clib.items.UpgradeItem;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

public class SolarGeneratorBlockEntity extends AbstractRecipeItemBlockEntity<SolarGeneratorRecipe> {
    int energyGen;

    public SolarGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(CGBlockEntityInit.SOLAR_GENERATOR.get(), pWorldPosition, pBlockState);
        activeItemFaces = Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.DOWN);
        activeEnergyFaces = Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.DOWN);
    }

    public int getEnergyGen() {
        return energyGen;
    }

    public ResourceLocation getTexture() {
        return getRecipe() != null ? new ResourceLocation(Objects.requireNonNull(getRecipe()).getTexture()) : new ResourceLocation("cgalaxy:textures/gui/overworld.png");
    }

    public float getNightIntensity() {
        return Objects.requireNonNull(getRecipe()).getNightIntensity();
    }

    public float getDayIntensity() {
        return Objects.requireNonNull(getRecipe()).getDayIntensity();
    }

    @Nullable
    @Override
    public SolarGeneratorRecipe getRecipe() {
        assert level != null;
        return level.getRecipeManager().getRecipeFor(CGRecipeSerializerInit.SOLAR_ENERGY_TYPE.get(), wrapper, level).orElse(null);
    }

    @Override
    public int[] inputSlots() {
        return new int[]{0};
    }

    @Override
    public int[] outputSlots() {
        return new int[]{0};
    }

    @Override
    public int getInvSize() {
        return 5;
    }

    @Override
    public boolean canItemFit(int slot, ItemStack stack) {
        if (slot == 0) {
            return stack.getCapability(CapabilityEnergy.ENERGY).isPresent();
        } else if (slot >= 1) {
            return stack.getItem() instanceof UpgradeItem;
        }
        return false;
    }

    @Override
    protected int getEnergyUsage() {
        return 0;
    }

    @Override
    public int getEnergyCapacity() {
        return 10000;
    }

    @Override
    public int getMaxExtract() {
        return 1000;
    }

    @Override
    public boolean requisites() {
        assert level != null;
        if (energyStorage.isFullEnergy()) {
            info = "key.clib.idle";
            return false;
        }

        if (Objects.requireNonNull(getRecipe()).matches(wrapper, level)) {
            if (!RenderingHelper.isDayTime(level)) {
                info = "key.clib.error_night_time";
                return false;
            }

            if (!level.canSeeSky(getBlockPos().above())) {
                info = "key.clib.error_no_sky";
                return false;
            }

            return getNightIntensity() > 0 || getDayIntensity() > 0;
        } else {
            info = "key.clib.error_invalid_dimension";
            return false;
        }
    }

    @Override
    public void execute() {
        assert level != null;
        Block block = level.getBlockState(getBlockPos()).getBlock();
        if (block instanceof SolarGeneratorBlock sg) {
            float fe = sg.getGeneration() + ((getProgressSpeedFinal() - 1) * 5);
            float in = RenderingHelper.isDayTime(level) ? getDayIntensity() : (getNightIntensity() + ((sg.getTier() - 1) / 10f));
            energyGen = (int) (fe * in);
            energyStorage.generateEnergy(energyGen);
        }
    }

    @Override
    public void executeExtra() {
        fillEnergySlot(0);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new SolarGeneratorContainer(id, player, inv, this, data);
    }
}