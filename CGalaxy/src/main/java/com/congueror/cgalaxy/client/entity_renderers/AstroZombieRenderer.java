package com.congueror.cgalaxy.client.entity_renderers;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;

public class AstroZombieRenderer extends ZombieRenderer {
    public AstroZombieRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.addLayer(new SpaceSuitLayer<>(this));
    }
}
