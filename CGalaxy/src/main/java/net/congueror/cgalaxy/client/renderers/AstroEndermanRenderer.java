package net.congueror.cgalaxy.client.renderers;

import net.congueror.cgalaxy.client.renderers.layers.SpaceSuitLayer;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AstroEndermanRenderer extends EndermanRenderer {
    public AstroEndermanRenderer(EntityRendererProvider.Context p_173992_) {
        super(p_173992_);
        this.addLayer(new SpaceSuitLayer<>(this));
    }
}
