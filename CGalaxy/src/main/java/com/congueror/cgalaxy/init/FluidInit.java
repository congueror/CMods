package com.congueror.cgalaxy.init;

import com.congueror.cgalaxy.CGalaxy;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CGalaxy.MODID);

    public static final RegistryObject<FlowingFluid> KEROSENE = FLUIDS.register("kerosene", () ->
            new ForgeFlowingFluid.Flowing(keroseneProperties()));
    private static ForgeFlowingFluid.Properties keroseneProperties() {
        return new ForgeFlowingFluid.Properties(() -> null, KEROSENE, FluidAttributes.builder(new ResourceLocation(CGalaxy.MODID, "block/kerosene"), new ResourceLocation(CGalaxy.MODID, "block/kerosene"))
                .density(1).temperature(437));
    }

    public static final RegistryObject<FlowingFluid> OIL = FLUIDS.register("oil", () ->
            new ForgeFlowingFluid.Flowing(oilProperties()));
    private static ForgeFlowingFluid.Properties oilProperties() {
        return new ForgeFlowingFluid.Properties(() -> null, OIL, FluidAttributes.builder(new ResourceLocation(CGalaxy.MODID, "block/oil_still"), new ResourceLocation(CGalaxy.MODID, "block/oil_flowing"))
                .density(825).temperature(437));
    }
}
