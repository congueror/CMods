package net.congueror.cgalaxy.client.renderers;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.GalacticVisitorModel;
import net.congueror.cgalaxy.client.renderers.layers.GalacticVisitorEyesLayer;
import net.congueror.cgalaxy.entity.GalacticVisitor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GalacticVisitorRenderer extends HumanoidMobRenderer<GalacticVisitor, GalacticVisitorModel> {
    public GalacticVisitorRenderer(EntityRendererProvider.Context p_174169_, GalacticVisitorModel p_174170_) {
        super(p_174169_, p_174170_, 0.5f);
        this.addLayer(new GalacticVisitorEyesLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GalacticVisitor pEntity) {
        return CGalaxy.location("textures/entity/galactic_visitor.png");
    }
}
