package net.congueror.cgalaxy.items;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.registry.SpaceStationBlueprint;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlueprintItem extends Item {
    public BlueprintItem(Properties pProperties) {
        super(pProperties);
    }

    public SpaceStationBlueprint getBlueprint(ItemStack stack) {
        if (stack.getItem() instanceof BlueprintItem) {
            return CGalaxy.REGISTRY.get().getValue(new ResourceLocation(stack.getOrCreateTag().getString("Blueprint")));
        } else {
            return null;
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TextComponent("Blueprint:\"" + getBlueprint(pStack).getRegistryName().toString() + "\"").withStyle(ChatFormatting.GREEN));
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if (pCategory.equals(this.category))
            CGalaxy.REGISTRY.get().forEach(b -> {
                ItemStack stack = new ItemStack(this);
                stack.getOrCreateTag().putString("Blueprint", b.getRegistryName().toString());
                pItems.add(stack);
            });
    }
}
