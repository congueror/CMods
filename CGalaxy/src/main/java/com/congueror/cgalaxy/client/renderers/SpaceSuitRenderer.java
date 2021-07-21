package com.congueror.cgalaxy.client.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class SpaceSuitRenderer extends BipedModel<PlayerEntity> {
    private final ModelRenderer Head;
    private final ModelRenderer Body;
    private final ModelRenderer RightArm;
    private final ModelRenderer LeftArm;
    private final ModelRenderer RightLeg;
    private final ModelRenderer LeftLeg;
    private final ModelRenderer Tanks;
    private final ModelRenderer Gear;

    public SpaceSuitRenderer() {
        super(0, 0, 64, 64);
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.setTextureOffset(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        Head = new ModelRenderer(this);
        Body.addChild(Head);
        Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        Head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        Head.setTextureOffset(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.5F, false);

        RightArm = new ModelRenderer(this);
        Body.addChild(RightArm);
        RightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        RightArm.setTextureOffset(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        LeftArm = new ModelRenderer(this);
        Body.addChild(LeftArm);
        LeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        LeftArm.setTextureOffset(16, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        RightLeg = new ModelRenderer(this);
        Body.addChild(RightLeg);
        RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        RightLeg.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        LeftLeg = new ModelRenderer(this);
        Body.addChild(LeftLeg);
        LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        LeftLeg.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        Tanks = new ModelRenderer(this);
        Body.addChild(Tanks);
        Tanks.setRotationPoint(0.0F, 24.0F, 0.0F);
        Tanks.setTextureOffset(0, 48).addBox(-4.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, 0.0F, false);
        Tanks.setTextureOffset(0, 48).addBox(1.0F, -20.0F, 2.0F, 3.0F, 4.0F, 3.0F, 0.0F, false);
        Tanks.setTextureOffset(32, 32).addBox(1.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
        Tanks.setTextureOffset(32, 32).addBox(-4.0F, -22.0F, 2.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);
        this.bipedBody.addChild(Tanks);


        Gear = new ModelRenderer(this);
        Body.addChild(Gear);
        Gear.setRotationPoint(0.0F, 24.0F, 0.0F);
        Gear.setTextureOffset(32, 37).addBox(-3.0F, -23.0F, 3.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        Gear.setTextureOffset(32, 37).addBox(-1.0F, -24.0F, 3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        this.bipedHead.addChild(Gear);
    }
}
