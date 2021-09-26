package net.congueror.cgalaxy.item;

import net.congueror.cgalaxy.client.renderers.RocketItemRenderer;
import net.congueror.cgalaxy.entity.RocketEntity;
import net.congueror.cgalaxy.entity.rockets.RocketTier1Entity;
import net.congueror.cgalaxy.init.CGEntityTypeInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RocketTier1Item extends RocketItem {

    public RocketTier1Item(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public RocketEntity rocketEntity(Level level) {
        return new RocketTier1Entity(CGEntityTypeInit.ROCKET_TIER_1.get(), level);
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null)
            consumer.accept(new IItemRenderProperties() {
                @Override
                public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                    return new RocketItemRenderer(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels());
                }
            });
    }
}
