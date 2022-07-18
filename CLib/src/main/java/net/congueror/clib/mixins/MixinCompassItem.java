package net.congueror.clib.mixins;

import net.congueror.clib.util.events.CompassTickEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CompassItem.class)
public class MixinCompassItem {

    @Inject(method = "inventoryTick", at = @At(value = "HEAD"), cancellable = true)
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new CompassTickEvent(pStack, pLevel, pEntity, pItemSlot, pIsSelected))) {
            ci.cancel();
        }
    }
}
