package net.congueror.cgalaxy.items;

import net.congueror.cgalaxy.entity.LaserBlast;
import net.congueror.cgalaxy.init.CGSoundInit;
import net.congueror.clib.items.AbstractEnergyItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class LaserBlasterItem extends AbstractEnergyItem {
    public LaserBlasterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (((LaserBlasterItem) pStack.getItem()).getEnergyStored(pStack) > 50) {
            LaserBlast blast = new LaserBlast(pLevel, pLivingEntity.getX(), pLivingEntity.getEyeY(), pLivingEntity.getZ(), pLivingEntity);
            blast.shootFromRotation(pLivingEntity, pLivingEntity.getXRot(), pLivingEntity.getYRot(), 0.0f, 1.0f, 0);
            //blast.shoot(pLivingEntity, pLivingEntity.getXRot(), pLivingEntity.getYRot());
            pLevel.addFreshEntity(blast);
            pLivingEntity.playSound(CGSoundInit.LASER_BLAST_LAZY.get(), 2.0f, 1.0f);
            this.extractEnergy(pStack, 50, false);
        }
        return pStack;
    }

    public int getUseDuration(ItemStack pStack) {
        return 12;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        return 10000;
    }
}
