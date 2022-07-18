package net.congueror.cgalaxy.items;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.util.registry.SpaceStationBlueprint;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpaceStationItem extends Item {
    public SpaceStationItem(Properties pProperties) {
        super(pProperties);
    }

    public void setBlueprint(ItemStack stack, SpaceStationBlueprint blueprint) {
        if (blueprint != null && blueprint.getRegistryName() != null && stack.getItem() instanceof SpaceStationItem) {
            stack.getOrCreateTag().putString("Blueprint", blueprint.getRegistryName().toString());
        }
    }

    public SpaceStationBlueprint getBlueprint(ItemStack stack) {
        if (stack.getItem() instanceof SpaceStationItem)
            return CGalaxy.REGISTRY.get().getValue(new ResourceLocation(stack.getOrCreateTag().getString("Blueprint")));
        return null;
    }

    public void addOptionalIngredient(ItemStack stack, Item item) {
        if (item != null && stack.getItem() instanceof SpaceStationItem) {
            ListTag list = stack.getOrCreateTag().getList("Optional", Tag.TAG_STRING);
            list.add(StringTag.valueOf(item.getRegistryName().toString()));
            stack.getOrCreateTag().put("Optional", list);
        }
    }

    public List<Item> getOptionalIngredients(ItemStack stack) {
        if (stack.getItem() instanceof SpaceStationItem) {
            List<Item> items = new ArrayList<>();
            ListTag list = stack.getOrCreateTag().getList("Optional", Tag.TAG_STRING);
            for (Tag tag : list) {
                if (tag instanceof StringTag s) {
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s.getAsString()));
                    if (item != null) items.add(item);
                }
            }
            return items;
        }
        return Collections.emptyList();
    }

    public List<String> getOptionalIngredientsByName(ItemStack stack) {
        if (stack.getItem() instanceof SpaceStationItem) {
            List<String> items = new ArrayList<>();
            ListTag list = stack.getOrCreateTag().getList("Optional", Tag.TAG_STRING);
            for (Tag tag : list) {
                if (tag instanceof StringTag s) {
                    items.add(s.getAsString());
                }
            }
            return items;
        }
        return Collections.emptyList();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (getBlueprint(pStack) != null) {
            pTooltipComponents.add(new TextComponent("Blueprint:\"" + getBlueprint(pStack).getRegistryName().toString() + "\"").withStyle(ChatFormatting.GREEN));
            pTooltipComponents.add(new TextComponent("OIngredients: [").withStyle(ChatFormatting.GREEN));

            List<String> optionalIngredientsByName = getOptionalIngredientsByName(pStack);
            for (int i = 0; i < optionalIngredientsByName.size(); i++) {
                String s = optionalIngredientsByName.get(i);
                pTooltipComponents.add(new TextComponent("     " + i + ": " + s).withStyle(ChatFormatting.GREEN));
            }

            pTooltipComponents.add(new TextComponent("]").withStyle(ChatFormatting.GREEN));
        } else {
            pTooltipComponents.add(new TextComponent("Must be created from Space Station Creator").withStyle(ChatFormatting.RED));
        }
    }
}
