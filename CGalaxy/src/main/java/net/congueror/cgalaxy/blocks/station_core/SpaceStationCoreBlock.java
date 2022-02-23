package net.congueror.cgalaxy.blocks.station_core;

import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import static net.congueror.cgalaxy.blocks.station_core.SpaceStationCoreContainer.CORE_LOCATIONS;

public class SpaceStationCoreBlock extends Block {
    public SpaceStationCoreBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            CGDimensionBuilder.DimensionObject obj = CGDimensionBuilder.getObjectFromKey(pLevel.dimension());
            if (obj != null) {
                if (obj.getIsOrbit()) {
                    NetworkHooks.openGui((ServerPlayer) pPlayer, new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return new TranslatableComponent("gui.cgalaxy.space_station_core");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
                            return new SpaceStationCoreContainer(pContainerId, pPos, pInventory);
                        }
                    }, pPos);
                } else {
                    pPlayer.sendMessage(new TextComponent("Invalid Dimension"), pPlayer.getUUID());
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        var list = CORE_LOCATIONS.get(pLevel.dimension());
        if (list != null) {
            SpaceStationCoreContainer.SpaceStationCoreObject obj = list.stream().filter(o -> o.position.equals(pPos)).findFirst().orElse(null);
            if (obj != null) {
                list.remove(obj);
                CORE_LOCATIONS.put(pLevel.dimension(), list);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
