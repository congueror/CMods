package net.congueror.cgalaxy.client.renderers.layers;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.client.models.GalacticVisitorModel;
import net.congueror.cgalaxy.entity.GalacticVisitor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

public class GalacticVisitorEyesLayer extends EyesLayer<GalacticVisitor, GalacticVisitorModel> {

    public GalacticVisitorEyesLayer(RenderLayerParent<GalacticVisitor, GalacticVisitorModel> p_116981_) {
        super(p_116981_);
    }

    @Override
    public RenderType renderType() {
        return RenderType.eyes(CGalaxy.location("textures/entity/galactic_visitor_eyes.png"));
    }
}
