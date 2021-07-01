package com.congueror.cgalaxy.util.events;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.world.dimension.Dimensions;
import com.congueror.clib.util.ModItemGroups;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
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
    }

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
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
        }
    }
}