package net.congueror.cgalaxy.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class OxygenTankItem extends Item {
    protected final int capacity;
    protected final BiFunction<EntityModelSet, Boolean, EntityModel<? extends LivingEntity>> model;

    public OxygenTankItem(Properties properties, int capacity, BiFunction<EntityModelSet, Boolean, EntityModel<? extends LivingEntity>> model) {
        super(properties.stacksTo(1));
        this.capacity = capacity;
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public EntityModel<? extends LivingEntity> getModel(boolean twoTanks) {
        return model.apply(Minecraft.getInstance().getEntityModels(), twoTanks);
    }

    public int getOxygen(ItemStack stack) {
        if (stack.getItem() instanceof OxygenTankItem) {
            return stack.getOrCreateTag().getInt("Oxygen");
        } else {
            return 0;
        }
    }

    public int drain(ItemStack stack, int amount) {
        int oxygen = stack.getOrCreateTag().getInt("Oxygen");
        if (oxygen > 0 && oxygen - amount >= 0) {
            stack.getOrCreateTag().putInt("Oxygen", oxygen - amount);
            return amount;
        }
        return 0;
    }

    public int fill(ItemStack stack, int amount) {
        int filled = capacity - getOxygen(stack);
        if (amount < filled) {
            stack.getOrCreateTag().putInt("Oxygen", getOxygen(stack) + amount);
            filled = amount;
        } else {
            stack.getOrCreateTag().putInt("Oxygen", capacity);
        }
        return filled;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("Oxygen", capacity);
        if (this.allowdedIn(pCategory)) {
            pItems.add(new ItemStack(this));
            pItems.add(stack);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getOxygen(stack) != getCapacity();
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        OxygenTankItem item = (OxygenTankItem) stack.getItem();
        return (double) (item.getCapacity() - item.getOxygen(stack)) / (double) item.getCapacity();
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable net.minecraft.world.level.Level pLevel, java.util.List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TranslatableComponent("fluid.cgalaxy.oxygen_still").append(": ").withStyle(ChatFormatting.AQUA).append(getOxygen(pStack) + "/" + capacity).withStyle(ChatFormatting.GREEN));
    }
}