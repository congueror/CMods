package com.congueror.cgalaxy.util.events;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.commands.ModCommands;
import com.congueror.cgalaxy.compat.curios.CuriosCompat;
import com.congueror.cgalaxy.entities.RocketEntity;
import com.congueror.cgalaxy.gui.galaxy_map.GalaxyMapContainer;
import com.congueror.cgalaxy.init.BlockInit;
import com.congueror.cgalaxy.init.CarverInit;
import com.congueror.cgalaxy.init.EnchantmentInit;
import com.congueror.cgalaxy.init.EntityTypeInit;
import com.congueror.cgalaxy.items.OxygenGearItem;
import com.congueror.cgalaxy.items.OxygenTankItem;
import com.congueror.cgalaxy.items.SpaceSuitItem;
import com.congueror.cgalaxy.network.Networking;
import com.congueror.cgalaxy.util.DamageSources;
import com.congueror.cgalaxy.world.FeatureGen;
import com.congueror.cgalaxy.world.dimension.Dimensions;
import com.congueror.clib.util.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.IForgeRegistry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommonEvents {
    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent e) {
            Networking.registerMessages();
            e.enqueueWork(() -> {
                Dimensions.setupDims();
                FeatureGen.registerFeatures();
                CarverInit.registerCarvers();
            });
        }

        @SubscribeEvent
        public static void interModEnqueue(InterModEnqueueEvent e) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("oxygen_tank").priority(1000).size(2).icon(new ResourceLocation(CGalaxy.MODID, "gui/oxygen_tank_slot")).build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("oxygen_gear").priority(1000).size(1).icon(new ResourceLocation(CGalaxy.MODID, "gui/oxygen_gear_slot")).build());
        }

        @SubscribeEvent
        public static void onRegisterItem(final RegistryEvent.Register<Item> e) {
            final IForgeRegistry<Item> registry = e.getRegistry();
            BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(block -> !(block instanceof FlowingFluidBlock)).forEach(block ->
            {
                Item.Properties properties = new Item.Properties().group(ModItemGroups.BlocksIG.instance);
                ArrayList<Block> exc = new ArrayList<>();
                exc.add(BlockInit.LAUNCH_PAD.get());
                exc.add(BlockInit.FUEL_REFINERY.get());
                exc.add(BlockInit.FUEL_LOADER.get());
                exc.add(BlockInit.OXYGEN_COMPRESSOR.get());
                if (exc.contains(block)) {
                    properties = new Item.Properties().group(ModItemGroups.CGalaxyIG.instance);
                }
                final BlockItem blockItem;
                blockItem = new BlockItem(block, properties);
                blockItem.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
                registry.register(blockItem);
            });
        }

        @SubscribeEvent
        public static void entityAttributeCreate(EntityAttributeCreationEvent e) {
            e.put(EntityTypeInit.ROCKET_TIER_1.get(), CreatureEntity.func_233666_p_()
                    .createMutableAttribute(Attributes.MAX_HEALTH, 1)
                    .create());
        }
    }//ModCommonEvents END

    @Mod.EventBusSubscriber(modid = CGalaxy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {
        @SubscribeEvent
        public static void registerCommands(RegisterCommandsEvent e) {
            ModCommands.register(e.getDispatcher());
        }

        static int i;

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity player = e.player;
            World world = player.getEntityWorld();
            double x = player.getPosX();
            double y = player.getPosY();
            double z = player.getPosZ();

            i++;
            ArrayList<ItemStack> items = CuriosCompat.getAllCuriosItemStacks(player);
            boolean hasOxygen = hasOxygen(player, items);
            if (hasOxygen) {
                ItemStack stack = items.stream()
                        .filter(stack1 -> stack1.getItem() instanceof OxygenTankItem && ((OxygenTankItem) stack1.getItem()).getOxygen(stack1) > 0).findFirst().orElse(ItemStack.EMPTY);
                if (i > 80) {
                    ((OxygenTankItem) stack.getItem()).drain(stack, 1);
                    i = 0;
                }
            }

            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - (192 / 2d), y - (192 / 2d), z - (192 / 2d), x + (192 / 2d), y + (192 / 2d), z + (192 / 2d)), null);
            for (Entity entity : entities) {
                if (entity instanceof LivingEntity) {
                    AttributeModifierManager manager = ((LivingEntity) entity).getAttributeManager();
                    if (world.getDimensionKey() == Dimensions.MOON) {
                        Objects.requireNonNull(manager.createInstanceIfAbsent(ForgeMod.ENTITY_GRAVITY.get())).setBaseValue(0.0162);
                        entity.fallDistance = 1;
                        boolean hasOxygen1 = hasOxygen((LivingEntity) entity, items);
                        if (!hasOxygen1) {
                            entity.attackEntityFrom(DamageSources.NO_OXYGEN, 1.0f);
                        }
                    } else {
                        Objects.requireNonNull(manager.createInstanceIfAbsent(ForgeMod.ENTITY_GRAVITY.get())).setBaseValue(ForgeMod.ENTITY_GRAVITY.get().getDefaultValue());
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

                        @Override
                        public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                            return new GalaxyMapContainer(p_createMenu_1_, false);
                        }
                    });
                }
            }
        }
    }//ForgeCommonEvents END

    public static boolean hasOxygen(LivingEntity entity, ArrayList<ItemStack> items) {
        boolean flag = false;
        boolean flag1 = false;
        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            if (slot != EquipmentSlotType.MAINHAND && slot != EquipmentSlotType.OFFHAND) {
                if (entity.getItemStackFromSlot(slot).getItem() instanceof SpaceSuitItem) {
                    flag = true;
                }
                if (EnchantmentHelper.getEnchantments(entity.getItemStackFromSlot(slot)).containsKey(EnchantmentInit.AIRTIGHT.get())) {
                    flag1 = true;
                }
            }
        }

        if (flag || flag1) {
            if (!items.isEmpty()) {
                boolean flag2 = false;
                boolean flag3 = false;
                for (ItemStack item : items) {
                    if (item.getItem() instanceof OxygenGearItem) {
                        flag2 = true;
                    }
                    if (item.getItem() instanceof OxygenTankItem) {
                        if (((OxygenTankItem) item.getItem()).getOxygen(item) > 0) {
                            flag3 = true;
                        }
                    }
                }
                return flag2 && flag3;
            }
        }
        return false;
    }
}
