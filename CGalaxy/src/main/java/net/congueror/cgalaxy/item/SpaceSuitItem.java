package net.congueror.cgalaxy.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.capabilities.ItemItemHandlerWrapper;
import net.congueror.cgalaxy.client.models.SpaceSuitModel;
import net.congueror.cgalaxy.init.CGFluidInit;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.resources.language.I18n;
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
import net.minecraftforge.common.ForgeMod;
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
                Minecraft mc = Minecraft.getInstance();

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
                    int y1 = 128 + a;
                    int y2 = y1 + (50 - a);
                    RenderingHelper.renderFluid(matrix, stack1, x1, x2, y1, y2, 0);
                    if (tankAmount.size() >= 2) {
                        FluidStack stack2 = new FluidStack(CGFluidInit.OXYGEN.getStill(), tankAmount.get(1));
                        int a1 = 50 - (50 * MathHelper.getPercent(tankAmount.get(1), tankCapacity.get(1)) / 100);
                        int x3 = 18;
                        int x4 = x3 + 16;
                        int y3 = 128 + a1;
                        int y4 = y3 + (50 - a1);
                        RenderingHelper.renderFluid(matrix, stack2, x3, x4, y3, y4, 0);
                    }
                }
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/oxygen_tank_gui.png"));
                GuiComponent.blit(poseStack, 0, 127, 18, 52, 0, 0, 18, 52, 18, 52);
                GuiComponent.blit(poseStack, 17, 127, 18, 52, 0, 0, 18, 52, 18, 52);

                //Radiation
                {
                    String rad = "Radiation: " + player.getPersistentData().getFloat(CGalaxy.PLAYER_RADIATION) + " Sv/y";
                    int k = mc.font.width(rad);
                    int l = mc.getWindow().getGuiScaledWidth() - 2 - k;
                    GuiComponent.drawString(poseStack, mc.font, rad, l, 127, MathHelper.calculateRGB(223, 255, 0));
                }

                //Temperature
                {
                    int pTemp = player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE);
                    int color = MathHelper.calculateRGB(240, 240, 255);
                    if (pTemp >= 30 && pTemp < 60) {
                        color = MathHelper.calculateRGB(255, 105, 0);
                    } else if (pTemp >= 60) {
                        color = MathHelper.calculateRGB(255, 0, 0);
                    } else if (pTemp <= 10 && pTemp > -60) {
                        color = MathHelper.calculateRGB(149, 214, 220);
                    } else if (pTemp <= -60) {
                        color = MathHelper.calculateRGB(0, 0, 237);
                    }

                    String temp = "Temperature: " + pTemp + " \u00b0C";
                    int k = mc.font.width(temp);
                    int l = mc.getWindow().getGuiScaledWidth() - 2 - k;
                    GuiComponent.drawString(poseStack, mc.font, temp, l, 138, color);
                }

                //Coordinates
                {
                    String coords = "x: " + (int) player.getX() + ", y: " + (int) player.getY() + ", z:" + (int) player.getZ();
                    int k = mc.font.width(coords);
                    int l = mc.getWindow().getGuiScaledWidth() - 2 - k;
                    GuiComponent.drawString(poseStack, mc.font, coords, l, 10, MathHelper.calculateRGB(91, 127, 0));
                }

                //Planet Info
                {
                    String locale = "dimension." + level.dimension().location().getNamespace() + "." + level.dimension().location().getPath();
                    String planetName = "Planet: " + I18n.get(locale);
                    GuiComponent.drawString(poseStack, mc.font, planetName, 2, 10, MathHelper.calculateRGB(91, 127, 0));

                    String locale1 = "biome." + level.getBiome(player.blockPosition()).getRegistryName().getNamespace() + "." + level.getBiome(player.blockPosition()).getRegistryName().getPath();
                    String biomeName = "Biome: " + I18n.get(locale1);
                    GuiComponent.drawString(poseStack, mc.font, biomeName, 2, 20, MathHelper.calculateRGB(91, 127, 0));

                    String grav = "Gravity: " + (String.format("%.02f", player.getAttributeValue(ForgeMod.ENTITY_GRAVITY.get()) * 100)) + "m/s\u00b2";
                    GuiComponent.drawString(poseStack, mc.font, grav, 2, 30, MathHelper.calculateRGB(91, 127, 0));
                    //Atmosphere?
                }
                //TODO: Planet Name/Info, Crack on screen if low, 3 Protection Bars(above hunger)
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
