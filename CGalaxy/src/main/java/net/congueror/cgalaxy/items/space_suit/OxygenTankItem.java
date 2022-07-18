package net.congueror.cgalaxy.items.space_suit;

import net.congueror.cgalaxy.init.CGFluidInit;
import net.congueror.clib.items.AbstractFluidItem;
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
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class OxygenTankItem extends AbstractFluidItem {
    protected final int capacity;
    protected final BiFunction<EntityModelSet, Boolean, EntityModel<? extends LivingEntity>> model;

    public OxygenTankItem(Properties properties, int capacity, BiFunction<EntityModelSet, Boolean, EntityModel<? extends LivingEntity>> model) {
        super(properties.stacksTo(1));
        this.capacity = capacity;
        this.model = model;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return stack.getFluid() == CGFluidInit.OXYGEN.getStill().get();
    }

    public int fill(ItemStack stack, int amount) {
        return fill(stack, new FluidStack(CGFluidInit.OXYGEN.getStill().get(), amount));
    }

    public EntityModel<? extends LivingEntity> getModel(boolean twoTanks) {
        return model.apply(Minecraft.getInstance().getEntityModels(), twoTanks);
    }
}