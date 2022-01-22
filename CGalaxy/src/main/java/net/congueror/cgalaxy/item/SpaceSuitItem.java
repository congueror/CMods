package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.capability.ItemItemHandlerWrapper;
import net.congueror.cgalaxy.client.models.SpaceSuitModel;
import net.congueror.cgalaxy.init.CGSoundInit;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SpaceSuitItem extends ArmorItem {

    public SpaceSuitItem(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if (slot.equals(EquipmentSlot.HEAD)) {
            return new ItemItemHandlerWrapper(stack, nbt, 1, (integer, itemStack) -> {
                if (integer == 0) {
                    return itemStack.getItem() instanceof OxygenGearItem;
                }
                return false;
            });
        } else if (slot.equals(EquipmentSlot.CHEST)) {
            return new ItemItemHandlerWrapper(stack, nbt, 5, (integer, itemStack) -> {
                if (integer == 0 || integer == 1) {
                    return itemStack.getItem() instanceof OxygenTankItem;
                }
                if (integer == 2) {
                    return itemStack.getItem() instanceof HeatProtectionUnitItem;
                }
                if (integer == 3) {
                    return itemStack.getItem() instanceof ColdProtectionUnitItem;
                }
                if (integer == 4) {
                    return itemStack.getItem() instanceof RadiationProtectionUnitItem;
                }
                return false;
            });
        } else if (slot.equals(EquipmentSlot.LEGS)) {
            return new ItemItemHandlerWrapper(stack, nbt, 1, (integer, itemStack) -> {
                return true;
            });
        } else if (slot.equals(EquipmentSlot.FEET)) {
            return new ItemItemHandlerWrapper(stack, nbt, 1, (integer, itemStack) -> {
                return true;
            });
        }
        return null;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "cgalaxy:textures/models/space_suit.png";
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
                //noinspection unchecked
                return (A) new SpaceSuitModel(SpaceSuitModel.createBodyLayer());
            }
        });
    }

    public enum SpaceSuitMaterial implements ArmorMaterial {
        MNORMAL(1, CGSoundInit.OXYGEN_MASK_EQUIP_LAZY.get(), "space_suit", 0, 0),
        MGOLD(2, CGSoundInit.OXYGEN_MASK_EQUIP_LAZY.get(), "gold_space_suit", 0, 0),
        MIRON(3, CGSoundInit.OXYGEN_MASK_EQUIP_LAZY.get(), "iron_space_suit", 0, 0),
        MDIAMOND(4, CGSoundInit.OXYGEN_MASK_EQUIP_LAZY.get(), "diamond_space_suit", 0.2F, 0),
        MNETHERITE(5, CGSoundInit.OXYGEN_MASK_EQUIP_LAZY.get(), "netherite_space_suit", 0.4F, 0.2F),

        NORMAL(1, SoundEvents.ARMOR_EQUIP_LEATHER, "space_suit", 0, 0),
        GOLD(2, SoundEvents.ARMOR_EQUIP_GOLD, "gold_space_suit", 0, 0),
        IRON(3, SoundEvents.ARMOR_EQUIP_IRON, "iron_space_suit", 0, 0),
        DIAMOND(4, SoundEvents.ARMOR_EQUIP_DIAMOND, "diamond_space_suit", 0.2F, 0),
        NETHERITE(5, SoundEvents.ARMOR_EQUIP_NETHERITE, "netherite_space_suit", 0.4F, 0.2F),
        ;

        final int defense;
        final SoundEvent sound;
        final String name;
        final float toughness;
        final float knockback;

        SpaceSuitMaterial(int defense, SoundEvent sound, String name, float toughness, float knockback) {
            this.defense = defense;
            this.sound = sound;
            this.name = name;
            this.toughness = toughness;
            this.knockback = knockback;
        }

        @Override
        public int getDurabilityForSlot(@Nonnull EquipmentSlot slotIn) {
            return -1;
        }

        @Override
        public int getDefenseForSlot(@Nonnull EquipmentSlot slotIn) {
            return defense;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Nonnull
        @Override
        public SoundEvent getEquipSound() {
            return sound;
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Nonnull
        @Override
        public String getName() {
            return new ResourceLocation(CGalaxy.MODID, name).toString();
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockback;
        }
    }
}
