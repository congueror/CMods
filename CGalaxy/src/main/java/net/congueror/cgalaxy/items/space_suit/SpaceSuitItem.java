package net.congueror.cgalaxy.items.space_suit;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.SpaceSuitModel;
import net.congueror.cgalaxy.init.CGSoundInit;
import net.congueror.clib.util.capability.ItemItemHandlerWrapper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Consumer;

public class SpaceSuitItem extends ArmorItem {
    private static final UUID[] UUIDS = new UUID[]{
            UUID.fromString("28eb4c77-08fe-4e93-bebe-59e17ccdb464"),
            UUID.fromString("5bdc6c81-153a-463f-8401-ca1c75ada7a8"),
            UUID.fromString("29327168-f307-4437-9fb3-3e082d7bfb9b"),
            UUID.fromString("17950d90-b967-49e1-9255-0da2590ad0ee")
    };

    public SpaceSuitItem(EquipmentSlot pSlot, Properties pProperties) {
        super(new SpaceSuitMaterial(pSlot), pSlot, pProperties);
    }

    public boolean upgradeArmor(ItemStack stack, SpaceSuitUpgradeItem item) {
        if (stack.getItem() instanceof SpaceSuitItem) {
            if (SpaceSuitUpgradeItem.isApplicable(getTier(stack), item)) {
                setTier(stack, item);
                return true;
            }
        }
        return false;
    }

    @Nullable
    public SpaceSuitUpgradeItem getTier(ItemStack stack) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(stack.getOrCreateTag().getString("Tier")));
        return item instanceof SpaceSuitUpgradeItem ? ((SpaceSuitUpgradeItem) item) : null;
    }

    public void setTier(ItemStack stack, SpaceSuitUpgradeItem item) {
        stack.getOrCreateTag().putString("Tier", item.getRegistryName().toString());
    }

    public static void modifyAttributes(ItemAttributeModifierEvent e) {
        SpaceSuitItem item = ((SpaceSuitItem) e.getItemStack().getItem());
        ItemStack stack = e.getItemStack();
        SpaceSuitUpgradeItem upgrade = item.getTier(stack);
        if (upgrade != null && item.getSlot().equals(e.getSlotType())) {
            e.addModifier(Attributes.ARMOR, new AttributeModifier(UUIDS[item.getSlot().getIndex()], "Armor defense", upgrade.getDefense(), AttributeModifier.Operation.ADDITION));
            e.addModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUIDS[item.getSlot().getIndex()], "Armor toughness", upgrade.getToughness(), AttributeModifier.Operation.ADDITION));
            e.addModifier(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUIDS[item.getSlot().getIndex()], "Armor knockback", upgrade.getKnockbackResistance(), AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        super.onArmorTick(stack, world, player);
        if (!world.isClientSide && this.getTier(stack) != null) {
            if (this.getTier(stack).getDefense() != ((SpaceSuitItem) stack.getItem()).getDefense()) {
                setTier(stack, this.getTier(stack));
            }
        }
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
            public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return new SpaceSuitModel(SpaceSuitModel.createBodyLayer());
            }
        });
    }

    @Override
    public int getDefense() {
        return this.material.getDefenseForSlot(this.slot);
    }

    @Override
    public float getToughness() {
        return this.material.getToughness();
    }

    public static class SpaceSuitMaterial implements ArmorMaterial {
        private final int defense;
        private final SoundEvent sound;
        private final String name;
        private final float toughness;
        private final float knockback;

        public SpaceSuitMaterial(EquipmentSlot pSlot) {
            this.defense = 1;
            this.sound = pSlot.equals(EquipmentSlot.HEAD) ? CGSoundInit.OXYGEN_MASK_EQUIP_LAZY.get() : SoundEvents.ARMOR_EQUIP_LEATHER;
            this.name = "space_suit";
            this.toughness = 0;
            this.knockback = 0;
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
