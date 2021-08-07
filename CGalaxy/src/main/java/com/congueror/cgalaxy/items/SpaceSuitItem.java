package com.congueror.cgalaxy.items;

import com.congueror.cgalaxy.CGalaxy;
import com.congueror.cgalaxy.client.SpaceSuitModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nullable;

public class SpaceSuitItem extends ArmorItem {
    //TODO: GUI that allows you to apply tank, which adds the tank model to the player.
    public SpaceSuitItem(EquipmentSlotType slot, Properties builderIn) {
        super(SpaceSuitMaterial.SPACE_SUIT_MATERIAL, slot, builderIn);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "cgalaxy:textures/models/armor/space_suit.png";
    }

    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        //noinspection unchecked
        return (A) new SpaceSuitModel();
    }

    public enum SpaceSuitMaterial implements IArmorMaterial {
        SPACE_SUIT_MATERIAL();

        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return 1300;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return 2;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public SoundEvent getSoundEvent() {
            return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return null;
        }

        @Override
        public String getName() {
            return new ResourceLocation(CGalaxy.MODID, "space_suit").toString();
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }
}
