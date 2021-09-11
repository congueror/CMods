package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.registry.FluidBuilder;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGFluidInit {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CGalaxy.MODID);

    public static final FluidBuilder.FluidObject KEROSENE = new FluidBuilder("kerosene", ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Source::new)
            .withBlock(() -> (LiquidBlock) CGBlockInit.KEROSENE.get())
            .build(FLUIDS);
}
