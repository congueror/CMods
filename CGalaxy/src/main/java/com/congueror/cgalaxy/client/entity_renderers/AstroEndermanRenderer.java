package com.congueror.cgalaxy.client.entity_renderers;

import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class AstroEndermanRenderer extends EndermanRenderer {
    public AstroEndermanRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.addLayer(new SpaceSuitLayer<>(this));
    }
}