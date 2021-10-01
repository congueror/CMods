package net.congueror.cgalaxy.client.renderers;

import net.congueror.cgalaxy.client.renderers.layers.SpaceSuitLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;

public class AstroZombieRenderer extends ZombieRenderer {
    public AstroZombieRenderer(EntityRendererProvider.Context p_174456_) {
        super(p_174456_);
        this.addLayer(new SpaceSuitLayer<>(this));
    }
}
