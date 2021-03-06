package net.congueror.cgalaxy.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.SpaceSuitModel;
import net.congueror.cgalaxy.client.renderers.OxygenMaskItemRenderer;
import net.congueror.cgalaxy.entity.AbstractRocket;
import net.congueror.cgalaxy.init.CGFluidInit;
import net.congueror.cgalaxy.util.CGConfig;
import net.congueror.cgalaxy.util.SpaceSuitUtils;
import net.congueror.clib.util.MathHelper;
import net.congueror.clib.util.RenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.Consumer;

public class OxygenMaskItem extends SpaceSuitItem {
    public OxygenMaskItem(ArmorMaterial material, EquipmentSlot slot, Properties builderIn) {
        super(material, slot, builderIn);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null) {
            consumer.accept(new IItemRenderProperties() {
                int pTemp;
                int i;

                @Override
                public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
                    //noinspection unchecked
                    return (A) new SpaceSuitModel(SpaceSuitModel.createBodyLayer());
                }

                @Override
                public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                    return new OxygenMaskItemRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels());
                }

                @Override
                public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTicks) {
                    Level level = player.level;
                    PoseStack poseStack = new PoseStack();
                    Matrix4f matrix = poseStack.last().pose();
                    Minecraft mc = Minecraft.getInstance();

                    int textColor = CGConfig.GUI_COLOR.get().rgb;

                    //Tanks
                    {
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
                        String textureName = CGConfig.GUI_COLOR.get().toString().equals("GREEN") ? "oxygen_tank_gui.png" : CGConfig.GUI_COLOR.get().toString().toLowerCase() + "/" + CGConfig.GUI_COLOR.get().toString().toLowerCase() + "_oxygen_tank_gui.png";
                        RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/space_suit/" + textureName));
                        int x = 0;
                        if (player.getVehicle() instanceof AbstractRocket) {
                            x = 16;
                        }
                        GuiComponent.blit(poseStack, x, 127, 18, 52, 0, 0, 18, 52, 18, 52);
                        GuiComponent.blit(poseStack, x + 17, 127, 18, 52, 0, 0, 18, 52, 18, 52);
                    }

                    //Protection Bars
                    {
                        ArrayList<Integer> heat = new ArrayList<>();
                        ArrayList<Integer> cold = new ArrayList<>();
                        ArrayList<Integer> radiation = new ArrayList<>();
                        heat.add(0, 0);
                        heat.add(1, 0);
                        cold.add(0, 0);
                        cold.add(1, 0);
                        radiation.add(0, 0);
                        radiation.add(1, 0);
                        SpaceSuitUtils.deserializeContents(player)
                                .forEach(itemStack -> {
                                    if (itemStack.getItem() instanceof HeatProtectionUnitItem item) {
                                        heat.add(0, item.getEnergy(itemStack));
                                        heat.add(1, item.getCapacity(itemStack));
                                    }
                                    if (itemStack.getItem() instanceof ColdProtectionUnitItem item) {
                                        cold.add(0, item.getEnergy(itemStack));
                                        cold.add(1, item.getCapacity(itemStack));
                                    }
                                    if (itemStack.getItem() instanceof RadiationProtectionUnitItem item) {
                                        radiation.add(0, item.getEnergy(itemStack));
                                        radiation.add(1, item.getCapacity(itemStack));
                                    }
                                });
                        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                        String textureName = CGConfig.GUI_COLOR.get().toString().equals("GREEN") ? "unit_protections_hud.png" : CGConfig.GUI_COLOR.get().toString().toLowerCase() + "/" + CGConfig.GUI_COLOR.get().toString().toLowerCase() + "_unit_protections_hud.png";
                        RenderSystem.setShaderTexture(0, new ResourceLocation(CGalaxy.MODID, "textures/gui/space_suit/" + textureName));
                        int l = mc.getWindow().getGuiScaledWidth() - 2;
                        int k = mc.getWindow().getGuiScaledHeight() - 16;
                        GuiComponent.blit(poseStack, l - 101, k, 102, 8, 0, 0, 102, 8, 102, 26);
                        GuiComponent.blit(poseStack, l - 101, k - 10, 102, 8, 0, 0, 102, 8, 102, 26);
                        GuiComponent.blit(poseStack, l - 101, k - 20, 102, 8, 0, 0, 102, 8, 102, 26);

                        if (radiation.get(0) != 0 && radiation.get(1) != 0) {
                            int a = MathHelper.getPercent(radiation.get(0), radiation.get(1)) + 1;
                            GuiComponent.blit(poseStack, l - 101, k + 1, a, 6, 0, 8, a, 6, 102, 26);
                        }
                        if (heat.get(0) != 0 && heat.get(1) != 0) {
                            int b = MathHelper.getPercent(heat.get(0), heat.get(1)) + 1;
                            GuiComponent.blit(poseStack, l - 101, k - 19, b, 6, 0, 14, b, 6, 102, 26);
                        }
                        if (cold.get(0) != 0 && cold.get(1) != 0) {
                            int c = MathHelper.getPercent(cold.get(0), cold.get(1)) + 1;
                            GuiComponent.blit(poseStack, l - 101, k - 9, c, 6, 0, 20, c, 6, 102, 26);
                        }
                    }

                    //Radiation
                    {
                        float pRad = player.getPersistentData().getFloat(CGalaxy.PLAYER_RADIATION);
                        String rad = "Radiation: " + pRad + " Sv/y";
                        if (CGConfig.RADIATION.get() == CGConfig.RadiationUnits.REM) {
                            rad = "Radiation: " + new DecimalFormat("#.00").format(MathHelper.sievertsToRem(pRad)) + " rem";
                        }
                        int k = mc.font.width(rad);
                        int l = mc.getWindow().getGuiScaledWidth() - 2 - k;
                        GuiComponent.drawString(poseStack, mc.font, rad, l, 127, MathHelper.calculateRGB(223, 255, 0));
                    }

                    //Temperature
                    {
                        if (pTemp != player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE)) {
                            i++;
                            if (i == 20) {
                                i = 0;
                                if (pTemp > player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE)) {
                                    pTemp--;
                                } else if (pTemp <= player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE)) {
                                    pTemp++;
                                }
                            }
                        } else {
                            i = 0;
                        }

                        //pTemp = player.getPersistentData().getInt(CGalaxy.PLAYER_TEMPERATURE);
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

                        String temp = "Temperature: " + new DecimalFormat("#.00").format(pTemp) + " \u00b0C";
                        if (CGConfig.TEMPERATURE.get() == CGConfig.TemperatureUnits.KELVIN) {
                            temp = "Temperature: " + new DecimalFormat("#.00").format(MathHelper.celciusToKelvin(pTemp)) + " K";
                        } else if (CGConfig.TEMPERATURE.get() == CGConfig.TemperatureUnits.FARRENHEIT) {
                            temp = "Temperature: " + new DecimalFormat("#.00").format(MathHelper.celciusToFarrenheit(pTemp)) + " \u00b0F";
                        }
                        int k = mc.font.width(temp);
                        int l = mc.getWindow().getGuiScaledWidth() - 2 - k;
                        GuiComponent.drawString(poseStack, mc.font, temp, l, 138, color);
                    }

                    //Coordinates
                    {
                        String coords = "x: " + (int) player.getX() + ", y: " + (int) player.getY() + ", z:" + (int) player.getZ();
                        int k = mc.font.width(coords);
                        int l = mc.getWindow().getGuiScaledWidth() - 2 - k;
                        GuiComponent.drawString(poseStack, mc.font, coords, l, 5, textColor);
                    }

                    //Planet Info
                    {
                        String locale = "dimension." + level.dimension().location().getNamespace() + "." + level.dimension().location().getPath();
                        String planetName = "Planet: " + I18n.get(locale);
                        GuiComponent.drawString(poseStack, mc.font, planetName, 2, 10, textColor);

                        String locale1 = "biome." + level.getBiome(player.blockPosition()).getRegistryName().getNamespace() + "." + level.getBiome(player.blockPosition()).getRegistryName().getPath();
                        String biomeName = "Biome: " + I18n.get(locale1);
                        GuiComponent.drawString(poseStack, mc.font, biomeName, 2, 20, textColor);

                        String grav = "Gravity: " + (String.format("%.02f", player.getAttributeValue(ForgeMod.ENTITY_GRAVITY.get()) * 100)) + "m/s\u00b2";
                        GuiComponent.drawString(poseStack, mc.font, grav, 2, 30, textColor);

                        String pres = "Air Pressure: " + MathHelper.formatUnit(player.getPersistentData().getDouble(CGalaxy.PLAYER_AIR_PRESSURE), "Pa");
                        if (CGConfig.AIR_PRESSURE.get() == CGConfig.AirPressureUnits.PSI) {
                            pres = "Air Pressure: " + MathHelper.formatUnit(MathHelper.pascalsToPsi(player.getPersistentData().getDouble(CGalaxy.PLAYER_AIR_PRESSURE)), "lbf/in\u00b2");
                        } else if (CGConfig.AIR_PRESSURE.get() == CGConfig.AirPressureUnits.BARS) {
                            pres = "Air Pressure: " + MathHelper.formatUnit(MathHelper.pascalsToBars(player.getPersistentData().getDouble(CGalaxy.PLAYER_AIR_PRESSURE)), "bar");
                        } else if (CGConfig.AIR_PRESSURE.get() == CGConfig.AirPressureUnits.TORR) {
                            pres = "Air Pressure: " + MathHelper.formatUnit(MathHelper.pascalsToTorr(player.getPersistentData().getDouble(CGalaxy.PLAYER_AIR_PRESSURE)), "torr");
                        } else if (CGConfig.AIR_PRESSURE.get() == CGConfig.AirPressureUnits.ATMOSPHERES) {
                            pres = "Air Pressure: " + MathHelper.formatUnit(MathHelper.pascalsToStandardAtmospheres(player.getPersistentData().getDouble(CGalaxy.PLAYER_AIR_PRESSURE)), "atm");
                        }
                        GuiComponent.drawString(poseStack, mc.font, pres, 2, 40, textColor);
                    }
                    //TODO: Crack on screen if low
                }
            });
        }
    }
}
