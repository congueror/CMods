package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.capabilities.ItemHandlerProvider;
import net.congueror.cgalaxy.client.models.SpaceSuitModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SpaceSuitItem extends ArmorItem {
    protected ItemStackHandler itemHandler = createHandler();

    public SpaceSuitItem(EquipmentSlot slot, Properties builderIn) {
        super(SpaceSuitMaterial.SPACE_SUIT_MATERIAL, slot, builderIn);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemHandlerProvider(stack, nbt, itemHandler);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                ItemStack stack = getStackInSlot(slot);
                stack.getOrCreateTag().put("inventory", this.serializeNBT());
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }
        };
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
        SPACE_SUIT_MATERIAL();

        @Override
        public int getDurabilityForSlot(@Nonnull EquipmentSlot slotIn) {
            return 1300;
        }

        @Override
        public int getDefenseForSlot(@Nonnull EquipmentSlot slotIn) {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Nonnull
        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Nonnull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Nonnull
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

    public static boolean isEquipped(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SpaceSuitItem &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpaceSuitItem &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SpaceSuitItem &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SpaceSuitItem;
    }
}
