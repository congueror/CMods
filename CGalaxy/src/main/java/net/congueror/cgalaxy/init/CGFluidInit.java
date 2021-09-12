package net.congueror.cgalaxy.init;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.clib.api.registry.FluidBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CGFluidInit {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, CGalaxy.MODID);

    public static final FluidBuilder.FluidObject KEROSENE = new FluidBuilder("kerosene", ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new)
            .withColor(0xE5E5C0)
            .withDensity(1)
            .withTemperature(437)
            .withSound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .withBlock(() -> (LiquidBlock) CGBlockInit.KEROSENE.get())
            .withTickRate(2)
            .withNewFluidTag("forge:kerosene")
            .build(FLUIDS);

    public static final FluidBuilder.FluidObject OIL = new FluidBuilder("oil", ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new)
            .withDensity(825)
            .withTemperature(437)
            .withSound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
            .withStillTexture("cgalaxy:block/oil_still")
            .withFlowingTexture("cgalaxy:block/oil_flow")
            .withBlock(() -> (LiquidBlock) CGBlockInit.OIL.get())
            .withTickRate(20)
            .withNewFluidTag("forge:oil")
            .build(FLUIDS);

    public static final FluidBuilder.FluidObject OXYGEN = new FluidBuilder("oxygen", ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new)
            .withColor(0xADD8E6)
            .withGaseousForm()
            .withDensity(1)
            .withTemperature(437)
            .withBucket(CGItemInit.OXYGEN_BUCKET)
            .withNewFluidTag("forge:oxygen")
            .build(FLUIDS);
}
