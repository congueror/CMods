package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

public class FluidInit {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CGalaxy.MODID);

    public static final RegistryObject<FlowingFluid> KEROSENE = FLUIDS.register("kerosene_still", () ->
            new ForgeFlowingFluid.Source(keroseneProperties()));
    public static final RegistryObject<FlowingFluid> KEROSENE_FLOWING = FLUIDS.register("kerosene_flowing", () ->
            new ForgeFlowingFluid.Flowing(keroseneProperties()));
    private static ForgeFlowingFluid.Properties keroseneProperties() {
        return new ForgeFlowingFluid.Properties(KEROSENE, KEROSENE_FLOWING, FluidAttributes.builder(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"))
                .color(0xE5E5C0).density(1).temperature(437).sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)).bucket(ItemInit.KEROSENE_BUCKET).block(BlockInit.KEROSENE);
    }

    public static final RegistryObject<FlowingFluid> OIL = FLUIDS.register("oil_still", () ->
            new ForgeFlowingFluid.Source(oilProperties()));
    public static final RegistryObject<FlowingFluid> OIL_FLOWING = FLUIDS.register("oil_flowing", () ->
            new ForgeFlowingFluid.Flowing(oilProperties()));
    private static ForgeFlowingFluid.Properties oilProperties() {
        return new ForgeFlowingFluid.Properties(OIL, OIL_FLOWING, FluidAttributes.builder(new ResourceLocation(CGalaxy.MODID, "block/oil_still"), new ResourceLocation(CGalaxy.MODID, "block/oil_still"))
                .density(825).temperature(437).sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)).bucket(ItemInit.OIL_BUCKET).block(BlockInit.OIL).tickRate(20);
    }

    public static final RegistryObject<FlowingFluid> OXYGEN = FLUIDS.register("oxygen_still", () ->
            new ForgeFlowingFluid.Source(oxygenProperties()));
    public static final RegistryObject<FlowingFluid> OXYGEN_FLOWING = FLUIDS.register("oxygen_flowing", () ->
            new ForgeFlowingFluid.Flowing(oxygenProperties()));
    private static ForgeFlowingFluid.Properties oxygenProperties() {
        return new ForgeFlowingFluid.Properties(OXYGEN, OXYGEN_FLOWING, FluidAttributes.builder(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"))
                .gaseous().color(0xADD8E6).density(1).temperature(437)).bucket(ItemInit.OXYGEN_BUCKET);
    }
}
