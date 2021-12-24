package net.congueror.clib.api.machine.fluid;

import net.congueror.clib.api.machine.item.AbstractItemContainer;
import net.congueror.clib.networking.CLNetwork;
import net.congueror.clib.networking.PacketUpdateFluidTanks;
import net.congueror.clib.networking.PacketUpdateInfo;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractFluidContainer<T extends AbstractFluidBlockEntity> extends AbstractItemContainer<T> {
    protected NonNullList<FluidStack> fluidLastTick = NonNullList.create();

    public AbstractFluidContainer(@Nullable MenuType<?> type, int id, Player player, Inventory playerInventory, T tile, ContainerData dataIn) {
        super(type, id, player, playerInventory, tile, dataIn);
    }

    public abstract FluidTank[] getFluidTanks();

    /**
     * Called from the {@link PacketUpdateFluidTanks} packet to sync the fluid tanks.
     */
    public void updateTanks(ResourceLocation rl, int amount, int tankId) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(rl);
        if (fluid != null) {
            tile.tanks[tankId].setFluid(new FluidStack(fluid, amount));
        } else {
            tile.tanks[tankId].setFluid(FluidStack.EMPTY);
        }
    }

    /**
     * Used to synchronize various information between the server and client.
     */
    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
        for (int i = 0; i < getFluidTanks().length; i++) {
            FluidStack stack = getFluidTanks()[i].getFluid();
            FluidStack stack1 = this.fluidLastTick.get(i);
            if (stack != stack1) {
                boolean stackChanged = !stack1.isFluidStackIdentical(stack);
                FluidStack stack2 = stack.copy();
                this.fluidLastTick.set(i, stack2);
                if (stackChanged) {
                    if (player instanceof ServerPlayer)
                        CLNetwork.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                                new PacketUpdateFluidTanks(containerId, stack2.getFluid().getRegistryName(), stack2.getAmount(), i));
                }
            }
        }
    }
}
