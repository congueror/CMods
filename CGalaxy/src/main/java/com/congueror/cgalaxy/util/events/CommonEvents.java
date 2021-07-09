package com.congueror.cgalaxy.util.events;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.commands.ModCommands;
import com.congueror.cgalaxy.entities.rocket_entity.RocketEntity;
import com.congueror.cgalaxy.gui.GalaxyMapGui;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.EntityTypeInit;
import com.congueror.cgalaxy.init.Keybinds;
import com.congueror.cgalaxy.network.Networking;
import com.congueror.cgalaxy.network.PacketOpenGalaxyMap;
import com.congueror.cgalaxy.world.dimension.Dimensions;
import com.congueror.clib.util.ModItemGroups;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.List;
import java.util.Set;

public class CommonEvents {
    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent e) {
            Networking.registerMessages();
            ObfuscationReflectionHelper.setPrivateValue(WorldCarver.class, WorldCarver.CAVE, new ImmutableSet.Builder<Block>()
                    .addAll((Set<Block>) ObfuscationReflectionHelper.getPrivateValue(WorldCarver.class, WorldCarver.CAVE, "field_222718_j"))
                    .add(BlockInit.MOON_STONE.get().getDefaultState().getBlock()).build(), "field_222718_j");
            e.enqueueWork(Dimensions::setupDims);
        }

        @SubscribeEvent
        public static void onRegisterItem(final RegistryEvent.Register<Item> e) {
            final IForgeRegistry<Item> registry = e.getRegistry();
            BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block ->
            {
                final Item.Properties properties = new Item.Properties().group(ModItemGroups.BlocksIG.instance);
                final BlockItem blockItem;
                blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
            });
        }

        @SubscribeEvent
        public static void entityAttributeCreate(EntityAttributeCreationEvent e) {
            e.put(EntityTypeInit.ROCKET_TIER_1.get(), CreatureEntity.func_233666_p_()
                    .createMutableAttribute(Attributes.MAX_HEALTH, 500)
                    .create());
        }
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {
        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent e) {
            ModCommands.register(e.getDispatcher());
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity player = e.player;
            World world = player.getEntityWorld();
            double x = player.getPosX();
            double y = player.getPosY();
            double z = player.getPosZ();

            if (world.getDimensionKey() == Dimensions.MOON) {
                List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null);
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity) {
                        AttributeModifierManager manager = ((LivingEntity) entity).getAttributeManager();
                        if (world.getDimensionKey() == Dimensions.MOON) {
                            manager.createInstanceIfAbsent(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.01);
                            entity.fallDistance = 1;
                        } else {
                            manager.createInstanceIfAbsent(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08);
                        }
                    }
                    if (entity instanceof ItemEntity) {
                        if (world.getDimensionKey() == Dimensions.MOON && entity.getPersistentData().getDouble("ItemGravity") <= 1 && entity.getMotion().getY() <= -0.1) {
                            entity.getPersistentData().putDouble("ItemGravity", 2);
                            entity.setMotion((entity.getMotion().getX()), ((entity.getMotion().getY()) + 0.04),
                                    (entity.getMotion().getZ()));
                            entity.getPersistentData().putDouble("ItemGravity", 0);
                        }
                    }
                }
            }

            Entity entity = player.getRidingEntity();

            if (mc.currentScreen == null) {
                if (Keybinds.LAUNCH_ROCKET.isPressed()) {
                    if (!world.isBlockLoaded(new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ()))) {
                        return;
                    }
                    if (world.isRemote) {
                        return;
                    }
                    if (entity instanceof RocketEntity) {
                        if (!entity.getPersistentData().getBoolean("Powered") && entity.getPersistentData().getInt("Fuel") >= 0/*TODO*/) {
                            entity.getPersistentData().putBoolean("Powered", true);
                        } else {
                            player.sendStatusMessage(new TranslationTextComponent("text.cgalaxy.insufficient_fuel"), false);
                        }
                    }
                }
            }

            if (player.getPosY() >= 600 && player.getRidingEntity() instanceof RocketEntity) {
                if (player instanceof ServerPlayerEntity) {
                    Networking.sendToClient(new PacketOpenGalaxyMap(), (ServerPlayerEntity) player);
                }
            }
        }
    }
}
