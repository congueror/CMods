package com.congueror.cgalaxy.util.events;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.commands.ModCommands;
import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.gui.GalaxyMapContainer;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.EntityTypeInit;
import com.congueror.cgalaxy.network.Networking;
import com.congueror.cgalaxy.network.PacketOpenGalaxyMap;
import com.congueror.cgalaxy.init.CarverInit;
import com.congueror.cgalaxy.world.dimension.Dimensions;
import com.congueror.cgalaxy.world.PlanetOreGen;
import com.congueror.clib.util.ModItemGroups;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class CommonEvents {
    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent e) {
            Networking.registerMessages();
            e.enqueueWork(() -> {
                Dimensions.setupDims();
                PlanetOreGen.registerFeatures();
                CarverInit.registerCarvers();
            });
        }

        @SubscribeEvent
        public static void onRegisterItem(final RegistryEvent.Register<Item> e) {
            final IForgeRegistry<Item> registry = e.getRegistry();
            BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> !(block instanceof FlowingFluidBlock)).forEach(block ->
            {
                Item.Properties properties = new Item.Properties().group(ModItemGroups.BlocksIG.instance);
                if (block.matchesBlock(BlockInit.LAUNCH_PAD.get()) || block.matchesBlock(BlockInit.FUEL_REFINERY.get()) || block.matchesBlock(BlockInit.FUEL_LOADER.get())) {
                    properties = new Item.Properties().group(ModItemGroups.CGalaxyIG.instance);
                }
                final BlockItem blockItem;
                blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(block.getRegistryName());
                registry.register(blockItem);
            });
        }

        @SubscribeEvent
        public static void entityAttributeCreate(EntityAttributeCreationEvent e) {
            e.put(EntityTypeInit.ROCKET_TIER_1.get(), CreatureEntity.func_233666_p_()
                    .createMutableAttribute(Attributes.MAX_HEALTH, 1)
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

            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity) {
                    AttributeModifierManager manager = ((LivingEntity) entity).getAttributeManager();
                    if (world.getDimensionKey() == Dimensions.MOON) {
                        manager.createInstanceIfAbsent(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.0162);
                        entity.fallDistance = 1;
                    } else {
                        manager.createInstanceIfAbsent(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(ForgeMod.ENTITY_GRAVITY.get().getDefaultValue());
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

            if (player.getPosY() >= 600 && player.getRidingEntity() instanceof RocketEntity && mc.currentScreen == null) {
                if (player instanceof ServerPlayerEntity) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                        @Override
                        public ITextComponent getDisplayName() {
                            return new TranslationTextComponent("gui.cgalaxy.galaxy_map");
                        }

                        @Nullable
                        @Override
                        public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                            return new GalaxyMapContainer(p_createMenu_1_, true);
                        }
                    });
                }
            }
        }
    }
}
