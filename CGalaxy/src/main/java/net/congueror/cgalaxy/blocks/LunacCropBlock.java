package net.congueror.cgalaxy.blocks;

import net.congueror.cgalaxy.api.registry.CGCropBlock;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.init.CGBlockInit;
import net.congueror.cgalaxy.init.CGItemInit;
import net.congueror.cgalaxy.util.DamageSources;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.cgalaxy.world.CGDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Random;

public class LunacCropBlock extends CropBlock implements CGCropBlock {

    public LunacCropBlock(Properties p_52247_) {
        super(p_52247_);
    }

    @Override
    public boolean canSurvive(CGDimensionBuilder.DimensionObject object, int temperature) {
        return object.equals(CGDimensions.MOON);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(CGBlockInit.MOON_REGOLITH.get());
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CGBlockInit.LUNAC_CROP.get();
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, Random pRandom, BlockPos pPos, BlockState pState) {
        return false;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        CGDimensionBuilder.DimensionObject obj = CGDimensionBuilder.getObjectFromKey(pLevel.dimension());
        if (obj != null && pEntity instanceof LivingEntity e) {
            if (SpaceSuitUtils.hasRadiationProtection(e, obj.getRadiation())) {
                pEntity.hurt(DamageSources.RADIATION, 1.0f);
            }
        } else if (pState.getValue(BlockStateProperties.AGE_7) == 7 && pEntity instanceof ItemEntity e) {
            if (e.getItem().is(Items.NETHERITE_INGOT)) {
                CompoundTag tag = e.getItem().getOrCreateTag();
                if (e.getAge() != -32768) {
                    e.setUnlimitedLifetime();
                }
                tag.putInt("lunacTimer", tag.getInt("lunacTimer") + 1);
                if (tag.getInt("lunacTimer") > 36000) {
                    e.getItem().shrink(1);
                    e.getItem().getOrCreateTag().putInt("lunacTimer", 0);
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(BlockStateProperties.AGE_7, 0));
                    ItemEntity newEntity = new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(CGItemInit.LUNARITE_INGOT.get()));
                    pLevel.addFreshEntity(newEntity);
                }
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }
}