package net.congueror.cgalaxy.client.models;

import net.congueror.cgalaxy.CGalaxy;
import net.congueror.cgalaxy.entity.GalacticVisitor;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GalacticVisitorModel extends HumanoidModel<GalacticVisitor> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CGalaxy.MODID, "galactic_visitor"), "main");
    public static final LayerDefinition LAYER = LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64);

    public GalacticVisitorModel(ModelPart pRoot) {
        super(pRoot);
    }

    @Override
    public void prepareMobModel(GalacticVisitor pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        ItemStack itemstack = pEntity.getItemInHand(InteractionHand.MAIN_HAND);
        if (pEntity.isAggressive()) {
            if (itemstack.is(Items.BOW)) {
                if (pEntity.getMainArm() == HumanoidArm.RIGHT) {
                    this.rightArmPose = ArmPose.BOW_AND_ARROW;
                } else {
                    this.leftArmPose = ArmPose.BOW_AND_ARROW;
                }
            }
        }
        if (itemstack.is(Items.CROSSBOW)) {
            if (pEntity.isChargingCrossbow()) {
                if (pEntity.getMainArm() == HumanoidArm.RIGHT) {
                    this.rightArmPose = ArmPose.CROSSBOW_CHARGE;
                } else {
                    this.leftArmPose = ArmPose.CROSSBOW_CHARGE;
                }
            } else {
                if (pEntity.getMainArm() == HumanoidArm.RIGHT) {
                    this.rightArmPose = ArmPose.CROSSBOW_HOLD;
                } else {
                    this.leftArmPose = ArmPose.CROSSBOW_HOLD;
                }
            }
        }
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
    }

    @Override
    public void setupAnim(GalacticVisitor pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        if (this.rightArmPose == ArmPose.CROSSBOW_HOLD) {
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
        } else if (this.leftArmPose == ArmPose.CROSSBOW_HOLD) {
            AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
        } else if (this.rightArmPose == ArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, pEntity, true);
        } else if (this.leftArmPose == ArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, pEntity, false);
        }
    }
}
