package net.congueror.cgalaxy.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.api.registry.CGDimensionBuilder;
import net.congueror.cgalaxy.capabilities.ItemItemHandlerWrapper;
import net.congueror.cgalaxy.client.models.SpaceSuitModel;
import net.congueror.cgalaxy.init.CGFluidInit;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.gui.GuiComponent;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SpaceSuitItem extends ArmorItem {

    public SpaceSuitItem(EquipmentSlot slot, Properties builderIn) {
        super(SpaceSuitMaterial.SPACE_SUIT_MATERIAL, slot, builderIn);
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
        } else {
            return new ItemItemHandlerWrapper(stack, nbt, 1, (integer, itemStack) -> {
                return true;
            });
        }
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

            @Override
            public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTicks) {
                Level level = player.level;
                PoseStack poseStack = new PoseStack();
                Matrix4f matrix = poseStack.last().pose();

                //Tanks
                ArrayList<Integer> tankAmount = new ArrayList<>();
                ArrayList<Integer> tankCapacity = new ArrayList<>();
                SpaceSuitUtils.deserializeContents(player).stream()
                        .filter(itemStack -> itemStack.getItem() instanceof OxygenTankItem)
                        .forEach(itemStack -> {
                            tankAmount.add(((OxygenTankItem) itemStack.getItem()).getOxygen(itemStack));
                            tankCapacity.add(((OxygenTankItem) itemStack.getItem()).getCapacity());
                        });
                if (!tankAmount.isEmpty()) {
                    FluidStack stack1 = new FluidStack(CGFluidInit.OXYGEN.getStill(), tankAmount.get(0));
                    int a = 50 - (50 * MathHelper.getPercent(tankAmount.get(0), tankCapacity.get(0)) / 100);
                    int x1 = 1;
                    int x2 = x1 + 16;
                    int y1 = 32 + a;
                    int y2 = y1 + (50 - a);
                    RenderingHelper.renderFluid(matrix, stack1, x1, x2, y1, y2, 0);

                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                    RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/oxygen_tank_gui.png"));
                    GuiComponent.blit(poseStack, 0, 16, 18, 50, 0, 0, 18, 50, 18, 50);
                }

                for (CGDimensionBuilder.DimensionObject obj : CGDimensionBuilder.OBJECTS) {
                    if (level.dimension().equals(obj.getDim())) {

                    }
                }
            }
        });
    }

    public enum SpaceSuitMaterial implements ArmorMaterial {
        SPACE_SUIT_MATERIAL();

        @Override
        public int getDurabilityForSlot(@Nonnull EquipmentSlot slotIn) {
            return -1;
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
}
