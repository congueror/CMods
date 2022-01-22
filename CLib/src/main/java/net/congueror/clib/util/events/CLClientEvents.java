package net.congueror.clib.util.events;

import net.congueror.clib.CLib;
import net.congueror.clib.blocks.solar_generator.SolarGeneratorScreen;
import net.congueror.clib.init.CLBlockInit;
import net.congueror.clib.init.CLContainerInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;
import java.util.Set;

public class CLClientEvents {
    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvents {
        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(CLBlockInit.RUBBER_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CLBlockInit.SAPPHIRE_SMALL_BUD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CLBlockInit.SAPPHIRE_MEDIUM_BUD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CLBlockInit.SAPPHIRE_LARGE_BUD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(CLBlockInit.SAPPHIRE_CLUSTER.get(), RenderType.cutout());

            MenuScreens.register(CLContainerInit.SOLAR_GENERATOR.get(), SolarGeneratorScreen::new);
        }
    }

    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeClientEvents {
        @SubscribeEvent
        public static void tooltipSetup(ItemTooltipEvent event) {
            if (event.getFlags().isAdvanced()) {
                Item item = event.getItemStack().getItem();
                CompoundTag nbt = event.getItemStack().getTag();
                Set<ResourceLocation> blockTags = Block.byItem(item).getTags();
                Set<ResourceLocation> itemTags = item.getTags();
                if (!blockTags.isEmpty() || !itemTags.isEmpty() || nbt != null) {
                    List<Component> lines = event.getToolTip();
                    if (Screen.hasControlDown()) {
                        if (!blockTags.isEmpty()) {
                            lines.add(getTextComponent("tooltip.clib.block_tags").withStyle(ChatFormatting.BLUE));
                            blockTags.stream().map(Object::toString).map(a -> "  " + a).map(b -> getTextComponent(b).withStyle(ChatFormatting.DARK_GRAY)).forEach(lines::add);
                        }
                        if (!itemTags.isEmpty()) {
                            lines.add(getTextComponent("tooltip.clib.item_tags").withStyle(ChatFormatting.RED));
                            itemTags.stream().map(Object::toString).map(a -> "  " + a).map(b -> getTextComponent(b).withStyle(ChatFormatting.DARK_GRAY)).forEach(lines::add);
                        }
                        if (nbt != null) {
                            lines.add(getTextComponent("tooltip.clib.nbt_tags").withStyle(ChatFormatting.GREEN));
                            lines.add(new TextComponent("  ").append(nbt.toString()).withStyle(ChatFormatting.DARK_GRAY));
                        }
                    } else {
                        lines.add(new TranslatableComponent("tooltip.clib.hold_ctrl_for_tags",
                                new TextComponent("Ctrl").withStyle(ChatFormatting.RED)).withStyle(ChatFormatting.GRAY));
                    }
                }
            }
        }

        public static MutableComponent getTextComponent(String key) {
            return I18n.exists(key) ? new TranslatableComponent(key) : new TextComponent(key);
        }
    }
}
