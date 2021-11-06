package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.events.AddVillagerProfessionsEvent;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.api.registry.CGEntity;
import net.congueror.cgalaxy.commands.CGCommands;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.entity.AstroEnderman;
import net.congueror.cgalaxy.entity.AstroZombie;
import net.congueror.cgalaxy.entity.villagers.LunarVillager;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.congueror.cgalaxy.init.CGCarverInit;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.util.DamageSources;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.cgalaxy.world.CGBiomes;
import net.congueror.cgalaxy.world.CGDimensions;
import net.congueror.cgalaxy.world.CGFeatureGen;
import net.congueror.clib.api.registry.BlockBuilder;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.List;
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
            });
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent e) {
            e.put(CGEntityTypeInit.ASTRO_ZOMBIE.get(), AstroZombie.entityAttributes().build());
            e.put(CGEntityTypeInit.ASTRO_ENDERMAN.get(), AstroEnderman.createMonsterAttributes().build());
            e.put(CGEntityTypeInit.LUNAR_VILLAGER.get(), LunarVillager.createAttributes().build());
            e.put(CGEntityTypeInit.LUNAR_ZOMBIE_VILLAGER.get(), Zombie.createAttributes().build());
        }

        @SubscribeEvent
        public static void onRegisterItem(final RegistryEvent.Register<Item> e) {
            BlockBuilder.registerBlockItems(e, CGalaxy.MODID);
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
            Level level = player.getCommandSenderWorld();
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();

            boolean hasOxygen = SpaceSuitUtils.hasOxygen(player);
            SpaceSuitUtils.drainTanks(player);
            SpaceSuitUtils.drainProtection(player, player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE), player.getPersistentData().getFloat(CGalaxy.PLAYER_RADIATION));

            List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)));
            for (Entity entity : entities) {
                CGDimensionBuilder.DimensionObject obj = CGDimensionBuilder.getObjectFromKey(level.dimension());
                if (obj != null) {
                    if (entity instanceof LivingEntity) {
                        AttributeMap manager = ((LivingEntity) entity).getAttributes();
                        Objects.requireNonNull(manager.getInstance(ForgeMod.ENTITY_GRAVITY.get())).setBaseValue(obj.getGravity());
                        entity.fallDistance = 1;
                        if (!obj.getBreathable()) {
                            if (!hasOxygen) {
                                player.hurt(DamageSources.NO_OXYGEN, 2.0f);
                            }
                            if (entity instanceof CGEntity i) {
                                if (!i.canBreath(obj)) {
                                    entity.hurt(DamageSources.NO_OXYGEN, 2.0f);
                                }
                            }
                        }
                        player.getPersistentData().putDouble(CGalaxy.PLAYER_AIR_PRESSURE, obj.getAirPressure());
                        if (RenderingHelper.isDayTime(level)) {
                            player.getPersistentData().putInt(CGalaxy.PLAYER_TEMPERATURE, obj.getDayTemp());
                        } else {
                            player.getPersistentData().putInt(CGalaxy.PLAYER_TEMPERATURE, obj.getNightTemp());
                        }
                        player.getPersistentData().putFloat(CGalaxy.PLAYER_RADIATION, obj.getRadiation());
                        if (!SpaceSuitUtils.hasHeatProtection(player, player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE))) {
                            player.hurt(DamageSources.NO_HEAT, 3.0f);
                        }
                        if (!SpaceSuitUtils.hasColdProtection(player, player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE))) {
                            player.hurt(DamageSources.NO_COLD, 3.0f);
                        }
                        if (!SpaceSuitUtils.hasRadiationProtection(player, player.getPersistentData().getFloat(CGalaxy.PLAYER_RADIATION))) {
                            player.hurt(DamageSources.NO_RADIATION, 1.0f);
                        }

                        if (obj.getRadiation() < 100) {
                            entity.getPersistentData().putInt(CGalaxy.LIVING_RADIATION_TICK, 0);
                        }
                        if (obj.getBreathable()) {
                            entity.getPersistentData().putInt(CGalaxy.LIVING_OXYGEN_TICK, 0);
                        }
                        if (player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE) < 60) {
                            entity.getPersistentData().putInt(CGalaxy.LIVING_HEAT_TICK, 0);
                        }
                        if (player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE) > -60) {
                            entity.getPersistentData().putInt(CGalaxy.LIVING_COLD_TICK, 0);
                        }
                    }

                    if (entity instanceof ItemEntity) {//TODO: non-hardcoded item gravity

                        //entity.setDeltaMovement(0.0D, -obj.getGravity() / 4.0D, 0.0D);
                        if (level.dimension() == CGDimensions.MOON.getDim() && entity.getPersistentData().getDouble(CGalaxy.ITEM_GRAVITY) <= 1 && entity.getDeltaMovement().y() <= -0.1) {
                            entity.getPersistentData().putDouble(CGalaxy.ITEM_GRAVITY, 2);
                            entity.setDeltaMovement((entity.getDeltaMovement().x()), ((entity.getDeltaMovement().y()) + (obj.getGravity() * 2.5)),
                                    (entity.getDeltaMovement().z()));
                            entity.getPersistentData().putDouble(CGalaxy.ITEM_GRAVITY, 0);
                        }
                    }
                }
            }

            if (player.getY() >= 600 && player.getVehicle() instanceof AbstractRocket && mc.screen == null) {
                if (player instanceof ServerPlayer player1) {
                    NetworkHooks.openGui(player1, new MenuProvider() {
                        @Nonnull
                        @Override
                        public Component getDisplayName() {
                            return new TranslatableComponent("gui.cgalaxy.galaxy_map");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int pContainerId, @Nonnull Inventory pInventory, @Nonnull Player pPlayer) {
                            return new GalaxyMapContainer(pContainerId, pPlayer, false);
                        }
                    });
                }
            }
        }

        @SubscribeEvent
        public static void addProfessions(AddVillagerProfessionsEvent e) {

        }

        @SubscribeEvent
        public static void onEntityPlaceBlock(BlockEvent.EntityPlaceEvent e) {
            if (e.getPlacedBlock().getBlock() instanceof TorchBlock) {
                e.getWorld().setBlock(e.getBlockSnapshot().getPos(), Blocks.DIAMOND_BLOCK.defaultBlockState(), 1);
            }
        }
    }//ForgeCommonEvents End
}
