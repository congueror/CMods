package net.congueror.cgalaxy.util.events;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.commands.CGCommands;
import net.congueror.cgalaxy.entity.AstroEndermanEntity;
import net.congueror.cgalaxy.entity.AstroZombieEntity;
import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import net.congueror.cgalaxy.init.CGCarverInit;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.congueror.cgalaxy.networking.CGNetwork;
import net.congueror.cgalaxy.util.DamageSources;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.cgalaxy.world.Dimensions;
import net.congueror.cgalaxy.world.FeatureGen;
import net.congueror.clib.api.registry.FluidBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
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
                Dimensions.registerDimensions();
                FeatureGen.registerFeatures();
                CGCarverInit.registerCarvers();
            });
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent e) {
            e.put(CGEntityTypeInit.ASTRO_ZOMBIE.get(), AstroZombieEntity.entityAttributes().build());
            e.put(CGEntityTypeInit.ASTRO_ENDERMAN.get(), AstroEndermanEntity.createMonsterAttributes().build());
        }

        @SubscribeEvent
        public static void interModProcess(InterModProcessEvent e) {
            FluidBuilder.registerBlockFluidFix();
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

            int temperature = 0;

            boolean hasOxygen = SpaceSuitUtils.hasOxygen(player);
            SpaceSuitUtils.drainTanks(player);

            List<Entity> entities = level.getEntitiesOfClass(Entity.class, new AABB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)));
            for (Entity entity : entities) {
                for (Dimensions.DimensionObject obj : Dimensions.CGDimensionBuilder.OBJECTS) {
                    if (level.dimension().equals(obj.getDim())) {
                        if (entity instanceof LivingEntity) {
                            AttributeMap manager = ((LivingEntity) entity).getAttributes();
                            Objects.requireNonNull(manager.getInstance(ForgeMod.ENTITY_GRAVITY.get())).setBaseValue(obj.getGravity());
                            entity.fallDistance = 1;
                            if (!obj.getBreathable()) {
                                if (!hasOxygen) {
                                    if (entity instanceof AstroEndermanEntity || entity instanceof AstroZombieEntity) {
                                    } else {
                                        entity.hurt(DamageSources.NO_OXYGEN, 1.0f);
                                    }
                                }
                            }
                            if (level.isDay()) {
                                temperature = obj.getDayTemp();
                            } else if (level.isNight()) {
                                temperature = obj.getNightTemp();
                            }
                        }

                        if (entity instanceof ItemEntity) {
                            if (level.dimension() == Dimensions.MOON.getDim() && entity.getPersistentData().getDouble("ItemGravity") <= 1 && entity.getDeltaMovement().y() <= -0.1) {
                                entity.getPersistentData().putDouble("ItemGravity", 2);
                                entity.setDeltaMovement((entity.getDeltaMovement().x()), ((entity.getDeltaMovement().y()) + (obj.getGravity() * 2.5)),
                                        (entity.getDeltaMovement().z()));
                                entity.getPersistentData().putDouble("ItemGravity", 0);
                            }
                        }
                    } else {
                        if (entity instanceof LivingEntity) {
                            AttributeMap manager = ((LivingEntity) entity).getAttributes();
                            Objects.requireNonNull(manager.getInstance(ForgeMod.ENTITY_GRAVITY.get())).setBaseValue(ForgeMod.ENTITY_GRAVITY.get().getDefaultValue());
                        }
                    }
                }
            }

            if (player.getY() >= 600 && player.getVehicle() instanceof RocketEntity && mc.screen == null) {
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
    }//ForgeCommonEvents End
}
