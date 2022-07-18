package net.congueror.cgalaxy.blocks.station_core;

import net.congueror.cgalaxy.util.json_managers.DimensionManager;
import net.congueror.cgalaxy.util.saved_data.SpaceStationCoreObject;
import net.congueror.cgalaxy.util.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.util.saved_data.WorldSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
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

import static net.congueror.cgalaxy.util.saved_data.WorldSavedData.CORE_LOCATIONS;

public class SpaceStationCoreBlock extends Block {
    public SpaceStationCoreBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && !pPlayer.isCrouching()) {
            CGDimensionBuilder.DimensionObject obj = DimensionManager.getObjectFromKey(pLevel.dimension());
            if (obj != null) {
                if (obj.isOrbit()) {
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
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pLevel instanceof ServerLevel l) WorldSavedData.update(l);
        var list = CORE_LOCATIONS.get(pLevel.dimension());
        if (list != null) {
            SpaceStationCoreObject obj = list.stream().filter(o -> o.getPosition().equals(pPos)).findFirst().orElse(null);
            if (obj != null) {
                list.remove(obj);
                CORE_LOCATIONS.put(pLevel.dimension(), list);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
