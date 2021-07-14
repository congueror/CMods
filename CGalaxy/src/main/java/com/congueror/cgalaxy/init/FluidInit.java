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

    public static final RegistryObject<FlowingFluid> KEROSENE_STILL = FLUIDS.register("kerosene_still", () ->
            new ForgeFlowingFluid.Source(keroseneProperties()));
    public static final RegistryObject<FlowingFluid> KEROSENE = FLUIDS.register("kerosene", () ->
            new ForgeFlowingFluid.Flowing(keroseneProperties()));
    private static ForgeFlowingFluid.Properties keroseneProperties() {
        return new ForgeFlowingFluid.Properties(KEROSENE_STILL, KEROSENE, FluidAttributes.builder(new ResourceLocation(CGalaxy.MODID, "fluid/kerosene_still"), new ResourceLocation(CGalaxy.MODID, "fluid/kerosene_flowing"))
                .density(1).temperature(437));
    }
}
