package net.congueror.clib.api.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class FluidBuilder {
    private final String name;
    private final Function<ForgeFlowingFluid.Properties, FlowingFluid> stillFluid;
    private final Function<ForgeFlowingFluid.Properties, FlowingFluid> flowingFluid;

    private RegistryObject<FlowingFluid> still;
    private RegistryObject<FlowingFluid> flowing;

    private ResourceLocation stillTexture = new ResourceLocation("block/water_still");
    private ResourceLocation flowingTexture = new ResourceLocation("block/water_flow");
    private Supplier<? extends LiquidBlock> block;
    private Item bucket;
    private float explosionResistance;
    private final FluidAttributes.Builder attributes = FluidAttributes.builder(stillTexture, flowingTexture);

    public FluidBuilder(String name, Function<ForgeFlowingFluid.Properties, FlowingFluid> stillFluid, Function<ForgeFlowingFluid.Properties, FlowingFluid> flowingFluid) {
        this.name = name;
        this.stillFluid = stillFluid;
        this.flowingFluid = flowingFluid;
    }

    public ForgeFlowingFluid.Properties properties() {
        return new ForgeFlowingFluid.Properties(still, flowing, attributes)
                .block(() -> block.get())
                .bucket(() -> bucket)
                .explosionResistance(explosionResistance);
    }

    public FluidObject build(DeferredRegister<Fluid> register) {
        still = register.register(name + "_still", () -> stillFluid.apply(properties()));
        flowing = register.register(name + "_flowing", () -> flowingFluid.apply(properties()));
        return new FluidObject(this);
    }

    public final FluidBuilder withStillTexture(String texture) {
        stillTexture = new ResourceLocation(texture);
        return this;
    }

    public final FluidBuilder withFlowingTexture(String texture) {
        flowingTexture = new ResourceLocation(texture);
        return this;
    }

    public final FluidBuilder withBucket(Item bucket) {
        this.bucket = bucket;
        return this;
    }

    public final FluidBuilder withBlock(Supplier<? extends LiquidBlock> block) {
        this.block = block;
        return this;
    }

    public final FluidBuilder withExplosionResistance(float resistance) {
        this.explosionResistance = resistance;
        return this;
    }

    //========================================Attributes==============================================

    public final FluidBuilder withColor(int color) {
        this.attributes.color(color);
        return this;
    }

    public final FluidBuilder withDensity(int density) {
        this.attributes.density(density);
        return this;
    }

    //================================================================================================

    public static class FluidObject {
        public RegistryObject<FlowingFluid> still;
        public RegistryObject<FlowingFluid> flowing;

        public FluidObject(FluidBuilder builder) {
            still = builder.still;
            flowing = builder.flowing;
        }

        public FlowingFluid getStill() {
            return still.get();
        }

        public FlowingFluid getFlowing() {
            return flowing.get();
        }
    }
}
