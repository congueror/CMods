package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.events.AddVillagerProfessionsEvent;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.blocks.room_pressurizer.RoomPressurizerBlockEntity;
import net.congueror.cgalaxy.commands.CGCommands;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.entity.AstroEnderman;
import net.congueror.cgalaxy.entity.AstroZombie;
import net.congueror.cgalaxy.entity.villagers.LunarVillager;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGCarverInit;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.init.CGStructureInit;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.util.CGGalacticObjects;
import net.congueror.cgalaxy.util.RoomPressurizerSavedData;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.cgalaxy.world.CGBiomes;
import net.congueror.cgalaxy.world.CGDimensions;
import net.congueror.cgalaxy.world.CGFeatureGen;
import net.congueror.cgalaxy.world.structures.CGStructures;
import net.congueror.clib.api.events.BlockOnPlacedEvent;
import net.congueror.clib.api.events.CropGrowEvent;
import net.congueror.clib.api.events.SaplingAdvanceEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Objects;

public class CGCommonEvents {
    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent e) {
            CGNetwork.registerMessages();
            e.enqueueWork(() -> {
                CGDimensions.registerDimensions();
                CGFeatureGen.registerFeatures();
                CGCarverInit.registerCarvers();
                CGBiomes.registerBiomes();
                CGStructureInit.setupStructures();
                CGStructures.registerConfiguredStructures();
            });
            CGGalacticObjects.init();
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent e) {
            e.put(CGEntityTypeInit.ASTRO_ZOMBIE.get(), AstroZombie.entityAttributes().build());
            e.put(CGEntityTypeInit.ASTRO_ENDERMAN.get(), AstroEnderman.createMonsterAttributes().build());
            e.put(CGEntityTypeInit.LUNAR_VILLAGER.get(), LunarVillager.createAttributes().build());
            e.put(CGEntityTypeInit.LUNAR_ZOMBIE_VILLAGER.get(), Zombie.createAttributes().build());
        }
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {

        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent e) {
            CGCommands.register(e.getDispatcher());
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
            Minecraft mc = Minecraft.getInstance();
            Player player = e.player;

            SpaceSuitUtils.tickPlayer(e);

            if (player.getY() >= 600 && player.getVehicle() instanceof AbstractRocket && ((AbstractRocket) player.getVehicle()).getMode() != 3 && mc.screen == null) {
                if (player instanceof ServerPlayer player1) {
                    NetworkHooks.openGui(player1, new MenuProvider() {
                        @Nonnull
                        @Override
                        public Component getDisplayName() {
                            return new TranslatableComponent("gui.cgalaxy.galaxy_map");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
                            return new GalaxyMapContainer(pContainerId, pPlayer, pInventory, false, CGGalacticObjects.SOLAR_SYSTEM);
                        }
                    });
                }
            }
        }

        @SubscribeEvent
        public static void worldLoadEvent(WorldEvent.Load e) {
            CGStructures.addDimensionalSpacing(e);
        }

        @SubscribeEvent
        public static void addProfessions(AddVillagerProfessionsEvent e) {

        }

        @SubscribeEvent
        public static void onEntityPlaceBlock(BlockOnPlacedEvent.Post e) {
            Level level = e.getLevel();
            BlockState newState = e.getLevel().getBlockState(e.getPos());
            if (
                    BlockTags.FIRE.contains(e.getNewBlock()) ||
                            (BlockTags.CAMPFIRES.contains(e.getNewBlock()) && CampfireBlock.isLitCampfire(newState)) ||
                            (BlockTags.CANDLES.contains(e.getNewBlock()) && AbstractCandleBlock.isLit(newState)) ||
                            (BlockTags.createOptional(CGalaxy.location("torches")).contains(e.getNewBlock()))
            ) {
                CGDimensionBuilder.DimensionObject object = CGDimensionBuilder.getObjectFromKey(level.dimension());
                if (object != null) {
                    if (!object.getBreathable()) {
                        var list = RoomPressurizerBlockEntity.AABBS.get(level.dimension());
                        if (list != null && list.stream().anyMatch(aabb -> aabb.contains(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()))) {
                            var list1 = RoomPressurizerBlockEntity.AFFECTED_BLOCKS.get(level.dimension());
                            if (list1 == null) list1 = new ArrayList<>();
                            if (!list1.contains(e.getPos())) list1.add(e.getPos());
                            RoomPressurizerBlockEntity.AFFECTED_BLOCKS.put(level.dimension(), list1);
                            RoomPressurizerSavedData.makeDirty((ServerLevel) level);
                        } else {
                            RoomPressurizerBlockEntity.updateAffectedBlocks(newState, e.getPos(), level);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onAdvanceTree(SaplingAdvanceEvent e) {
            Level level = e.getLevel();
            Event.Result result = Event.Result.ALLOW;
            if (!Objects.requireNonNull(CGDimensionBuilder.getObjectFromKey(level.dimension())).getBreathable()) {
                result = Event.Result.DENY;
                var list = RoomPressurizerBlockEntity.AABBS.get(level.dimension());
                if (list != null && list.stream().anyMatch(aabb -> aabb.contains(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()))) {
                    result = Event.Result.ALLOW;
                }
            }
            e.setResult(result);
        }

        @SubscribeEvent
        public static void onCropGrow(CropGrowEvent e) {
            Level level = e.getLevel();
            Event.Result result = Event.Result.ALLOW;
            if (!Objects.requireNonNull(CGDimensionBuilder.getObjectFromKey(level.dimension())).getBreathable()) {
                result = Event.Result.DENY;
                var list = RoomPressurizerBlockEntity.AABBS.get(level.dimension());
                if (list != null && list.stream().anyMatch(aabb -> aabb.contains(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()))) {
                    result = Event.Result.ALLOW;
                }
            }
            e.setResult(result);
        }
    }//ForgeCommonEvents End
}
