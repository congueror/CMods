package com.congueror.cgalaxy.entities.rocket_tier_1;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class RocketTier1Renderer extends EntityModel<Entity> {
    private final ModelRenderer Rocket;

    public RocketTier1Renderer() {
        textureWidth = 512;
        textureHeight = 256;
        Rocket = new ModelRenderer(this);

        Rocket.setRotationPoint(0.0F, 23.0F, 0.0F);
        Rocket.setTextureOffset(43, 17).addBox(11.0F, -8.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -8.0F, -12.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-12.0F, -8.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -8.0F, 11.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(273, 90).addBox(4.0F, -31.0F, -8.0F, 3.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(273, 90).addBox(-7.0F, -31.0F, -8.0F, 3.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(85, 46).addBox(3.0F, -31.0F, -8.1F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(86, 47).addBox(-4.0F, -31.0F, -8.1F, 1.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(87, 52).addBox(-3.0F, -31.0F, -8.1F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(89, 52).addBox(-3.0F, -25.0F, -8.1F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(274, 82).addBox(-7.0F, -39.0F, -8.0F, 14.0F, 8.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(276, 135).addBox(-7.0F, -24.0F, -8.0F, 14.0F, 19.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(279, 120).addBox(-7.0F, -39.0F, 7.0F, 14.0F, 34.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(276, 107).addBox(-8.0F, -39.0F, -7.0F, 1.0F, 34.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(307, 78).addBox(-8.0F, -40.0F, -8.0F, 1.0F, 35.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(307, 78).addBox(-8.0F, -40.0F, 7.0F, 1.0F, 35.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(37, 23).addBox(-7.0F, -40.0F, 7.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(37, 23).addBox(-7.0F, -40.0F, -8.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(29, 17).addBox(-8.0F, -40.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(29, 17).addBox(7.0F, -40.0F, -7.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(307, 78).addBox(7.0F, -40.0F, 7.0F, 1.0F, 35.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(307, 78).addBox(7.0F, -37.0F, -8.0F, 1.0F, 32.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(307, 78).addBox(7.0F, -40.0F, -8.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(10.0F, -10.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -10.0F, -11.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-11.0F, -10.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -10.0F, 10.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(9.0F, -11.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -11.0F, -10.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-10.0F, -11.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -11.0F, 9.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(12.0F, -6.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -6.0F, -13.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-13.0F, -6.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -6.0F, 12.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(13.0F, -4.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -4.0F, -14.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-14.0F, -4.0F, -1.0F, 1.0F, 6.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -4.0F, 13.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(8.0F, -12.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -12.0F, -9.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-9.0F, -12.0F, -1.0F, 1.0F, 7.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-1.0F, -12.0F, 8.0F, 2.0F, 7.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(277, 106).addBox(7.0F, -39.0F, -7.0F, 1.0F, 34.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(33, 87).addBox(4.0F, -41.0F, -7.0F, 2.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(33, 87).addBox(6.0F, -41.0F, -6.0F, 1.0F, 1.0F, 12.0F, 0.0F, false);
        Rocket.setTextureOffset(33, 87).addBox(-7.0F, -41.0F, -6.0F, 1.0F, 1.0F, 12.0F, 0.0F, false);
        Rocket.setTextureOffset(98, 112).addBox(-7.0F, -41.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(104, 118).addBox(6.0F, -41.0F, -7.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(105, 122).addBox(5.0F, -42.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(109, 105).addBox(5.0F, -43.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(85, 99).addBox(4.0F, -45.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(114, 114).addBox(4.0F, -44.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(109, 104).addBox(4.0F, -45.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(132, 104).addBox(4.0F, -44.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(115, 98).addBox(-5.0F, -45.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(101, 115).addBox(-4.0F, -47.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(95, 111).addBox(-3.0F, -49.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(93, 95).addBox(-3.0F, -48.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(86, 115).addBox(2.0F, -49.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(86, 91).addBox(2.0F, -48.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(108, 116).addBox(-3.0F, -49.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(96, 105).addBox(-2.0F, -50.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(66, 112).addBox(-2.0F, -51.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(111, 119).addBox(-2.0F, -50.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(102, 111).addBox(-2.0F, -51.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(91, 105).addBox(1.0F, -50.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(83, 109).addBox(1.0F, -51.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(93, 118).addBox(1.0F, -50.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(105, 115).addBox(1.0F, -51.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(101, 109).addBox(-3.0F, -48.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(75, 115).addBox(2.0F, -49.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(122, 105).addBox(2.0F, -48.0F, 2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(83, 93).addBox(-4.0F, -46.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(66, 111).addBox(-4.0F, -47.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(111, 111).addBox(-4.0F, -46.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(104, 93).addBox(3.0F, -47.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(89, 96).addBox(3.0F, -46.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(102, 99).addBox(3.0F, -47.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(104, 106).addBox(3.0F, -46.0F, 3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(83, 96).addBox(-5.0F, -44.0F, -5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(105, 108).addBox(-5.0F, -45.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(122, 95).addBox(-5.0F, -44.0F, 4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(70, 112).addBox(-6.0F, -42.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(104, 96).addBox(-6.0F, -43.0F, -6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(56, 119).addBox(5.0F, -42.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(106, 104).addBox(5.0F, -43.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(96, 93).addBox(-6.0F, -42.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(112, 106).addBox(-6.0F, -43.0F, 5.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(96, 116).addBox(-7.0F, -41.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(118, 98).addBox(6.0F, -41.0F, 6.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(31, 17).addBox(4.0F, -5.0F, -8.0F, 4.0F, 1.0F, 16.0F, 0.0F, false);
        Rocket.setTextureOffset(31, 17).addBox(3.0F, -40.0F, -7.0F, 4.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(91, 4).addBox(-1.0F, -68.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(177, 46).addBox(-1.0F, -66.0F, -1.0F, 2.0F, 15.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(64, 102).addBox(-1.0F, -50.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);
        Rocket.setTextureOffset(64, 102).addBox(-1.0F, -51.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);
        Rocket.setTextureOffset(64, 102).addBox(-2.0F, -50.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(64, 102).addBox(-2.0F, -51.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(64, 102).addBox(1.0F, -50.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(64, 102).addBox(1.0F, -51.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 110).addBox(-3.0F, -49.0F, -2.0F, 6.0F, 1.0F, 4.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 110).addBox(-3.0F, -48.0F, -2.0F, 6.0F, 1.0F, 4.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 110).addBox(-2.0F, -49.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 110).addBox(-2.0F, -48.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 110).addBox(-2.0F, -49.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 110).addBox(-2.0F, -48.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 88).addBox(-3.0F, -47.0F, -4.0F, 6.0F, 1.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 88).addBox(-3.0F, -46.0F, -4.0F, 6.0F, 1.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 88).addBox(3.0F, -47.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 88).addBox(3.0F, -46.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 88).addBox(-4.0F, -47.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(88, 88).addBox(-4.0F, -46.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(75, 97).addBox(-4.0F, -45.0F, -5.0F, 8.0F, 1.0F, 10.0F, 0.0F, false);
        Rocket.setTextureOffset(75, 97).addBox(-4.0F, -44.0F, -5.0F, 8.0F, 1.0F, 10.0F, 0.0F, false);
        Rocket.setTextureOffset(75, 97).addBox(-5.0F, -45.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(75, 97).addBox(-5.0F, -44.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(75, 97).addBox(4.0F, -45.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(75, 97).addBox(4.0F, -44.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(65, 106).addBox(-5.0F, -42.0F, -6.0F, 10.0F, 1.0F, 12.0F, 0.0F, false);
        Rocket.setTextureOffset(65, 106).addBox(-5.0F, -43.0F, -6.0F, 10.0F, 1.0F, 12.0F, 0.0F, false);
        Rocket.setTextureOffset(65, 106).addBox(-6.0F, -42.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        Rocket.setTextureOffset(65, 106).addBox(-6.0F, -43.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        Rocket.setTextureOffset(65, 106).addBox(5.0F, -42.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        Rocket.setTextureOffset(65, 106).addBox(5.0F, -43.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        Rocket.setTextureOffset(66, 94).addBox(-6.0F, -41.0F, -7.0F, 10.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(444, 32).addBox(-7.0F, -5.0F, -6.0F, 11.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(30, 14).addBox(-7.0F, -40.0F, -7.0F, 10.0F, 1.0F, 14.0F, 0.0F, false);
        Rocket.setTextureOffset(43, 17).addBox(-7.0F, -5.0F, -8.0F, 11.0F, 1.0F, 2.0F, 0.0F, false);
        Rocket.setTextureOffset(27, 15).addBox(-8.0F, -5.0F, -8.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
        Rocket.setTextureOffset(33, 23).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(30, 23).addBox(-3.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(14, 2).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 0.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(33, 29).addBox(-4.0F, -2.0F, 3.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(37, 26).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(45, 20).addBox(3.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(37, 20).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(34, 17).addBox(2.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        Rocket.setTextureOffset(39, 26).addBox(-2.0F, -3.0F, 2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(40, 11).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(34, 30).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(32, 16).addBox(-5.0F, -1.0F, 4.0F, 10.0F, 1.0F, 1.0F, 0.0F, false);
        Rocket.setTextureOffset(34, 23).addBox(4.0F, -1.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        Rocket.setTextureOffset(34, 17).addBox(-5.0F, -1.0F, -4.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        this.Rocket.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);

        //Animation 1
        this.Rocket.rotateAngleZ = ageInTicks / (180F / (float) Math.PI);
        if (entity instanceof LivingEntity) {
            this.Rocket.rotateAngleZ = (float) entity.getPersistentData().getDouble("Animation");
        }
        // Animation2
        this.Rocket.rotateAngleX = ageInTicks / (180F / (float) Math.PI);
        if (entity instanceof LivingEntity) {
            this.Rocket.rotateAngleX = (float) entity.getPersistentData().getDouble("AnimationPitch");
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Rocket.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}