package com.congueror.clib.util.events;

import com.congueror.clib.CLib;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModCommonEvents {

    }

    @Mod.EventBusSubscriber(modid = CLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommonEvents {
        @SubscribeEvent
        public static void tooltipSetup(ItemTooltipEvent event)
        {
            if(event.getFlags().isAdvanced()) {
                Item item = event.getItemStack().getItem();
                CompoundNBT nbt = event.getItemStack().getTag();
                Set<ResourceLocation> blockTags = Block.getBlockFromItem(item).getTags();
                Set<ResourceLocation> itemTags = item.getTags();
                if (!blockTags.isEmpty() || !itemTags.isEmpty() || nbt != null)
                {
                    List<ITextComponent> lines = event.getToolTip();
                    if (Screen.hasControlDown())
                    {
                        if (!blockTags.isEmpty()) {
                            lines.add(getTextComponent("tooltip.clib.block_tags").mergeStyle(TextFormatting.GRAY));
                            blockTags.stream().map(Object::toString).map(a -> "  " + a).map(b -> getTextComponent(b).mergeStyle(TextFormatting.DARK_GRAY)).forEach(lines::add);
                        }
                        if (!itemTags.isEmpty()) {
                            lines.add(getTextComponent("tooltip.clib.item_tags").mergeStyle(TextFormatting.GRAY));
                            itemTags.stream().map(Object::toString).map(a -> "  " + a).map(b -> getTextComponent(b).mergeStyle(TextFormatting.DARK_GRAY)).forEach(lines::add);
                        }
                        if(nbt != null) {//nbt.isEmpty() crashes game
                            lines.add(getTextComponent("tooltip.clib.nbt_tags").mergeStyle(TextFormatting.GRAY));
                            lines.add(new StringTextComponent("  ").appendString(nbt.toString()).mergeStyle(TextFormatting.DARK_GRAY));
                        }
                    } else {
                        lines.add(new TranslationTextComponent("tooltip.clib.hold_ctrl_for_tags").mergeStyle(TextFormatting.GRAY));
                    }
                }
            }
        }
    }

    public static IFormattableTextComponent getTextComponent(String key) {
        return canLocalize(key) ? new TranslationTextComponent(key) : new StringTextComponent(key);
    }

    public static boolean canLocalize(String key) {
        return I18n.hasKey(key);
    }
}
