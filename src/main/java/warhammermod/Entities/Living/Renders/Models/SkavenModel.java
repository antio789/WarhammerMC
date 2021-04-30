package warhammermod.Entities.Living.Renders.Models;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class SkavenModel<T extends MobEntity & IRangedAttackMob> extends EntityModel<T> implements IHasArm {
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedHead;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedRightArm;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer mouthup;
    public ModelRenderer mouthdown;
    public ModelRenderer earleft;
    public ModelRenderer earright;
    public ModelRenderer tail1;
    public ModelRenderer tail2;
    public ModelRenderer tailend;
    public ModelRenderer Chestplate;
    public ModelRenderer Armplates;
    public ModelRenderer Legplates;
    public ModelRenderer Helmet;
    public ModelRenderer Helmetfront;
    public ModelRenderer HelmetfrontFull;
    public ModelRenderer Crest;
    public ModelRenderer pagne;



    public SkavenModel(float scale) {
        this.texWidth = 64;
        this.texHeight = 64;
        this.bipedHead = new ModelRenderer(this, 20, 0);
        this.bipedHead.setPos(0.0F, 3.4F, -6.0F);
        this.bipedHead.addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3, 0.0F+scale);
        this.mouthup = new ModelRenderer(this, 35, 0);
        this.mouthup.setPos(0.0F, -1.1F, -1.7F);
        this.mouthup.addBox(-1.5F, -2.0F, -2.5F, 3, 2, 3, -0.3F+scale);
        this.mouthdown = new ModelRenderer(this, 20, 8);
        this.mouthdown.setPos(0.0F, 0.2F, -1.6F);
        this.mouthdown.addBox(-1.5F, -2.0F, -2.5F, 3, 2, 3, -0.4F+scale);
        this.earleft = new ModelRenderer(this, 50, 0);
        this.earleft.setPos(1.4F, -1.5F, 0.7F);
        this.earleft.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.2F+scale);
        this.earright = new ModelRenderer(this, 50, 0);
        this.earright.setPos(-1.4F, -1.5F, 0.7F);
        this.earright.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.2F+scale);
        this.setRotateAngle(earleft, 0.0F, 0.0F, 0.2617993877991494F);
        this.setRotateAngle(earright, 0.0F, 0.0F, -0.2617993877991494F);

        this.bipedBody = new ModelRenderer(this, 0, 0);
        this.bipedBody.setPos(0.0F, 2.5F, -3.5F);
        this.bipedBody.addBox(-3.0F, 0.0F, -1.5F, 6, 10, 3, 0.0F+scale);
        this.setRotateAngle(bipedBody, 0.31869712141416456F, 0.0F, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 0, 16);
        this.bipedRightArm.setPos(-3.5F, 4.5F, -3.4F);
        this.bipedRightArm.addBox(-0.5F, -1.0F, -0.5F, 1, 10, 1, 0.1F+scale);
        this.bipedLeftArm = new ModelRenderer(this, 0, 16);
        this.bipedLeftArm.setPos(3.5F, 4.5F, -3.4F);
        this.bipedLeftArm.mirror=true;
        this.bipedLeftArm.addBox(-0.5F, -1.0F, -0.5F, 1, 10, 1, 0.1F+scale);
        //this.setRotateAngle(bipedLeftArm, -1.3962634015954636F, -0.10000736613927509F, 0.10000736613927509F);
        //this.setRotateAngle(bipedRightArm, -1.3962634015954636F, 0.10000736613927509F, -0.10000736613927509F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 30);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setPos(2.0F, 12.0F, -1.0F);
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F+scale);
        this.bipedRightLeg = new ModelRenderer(this, 0, 30);
        this.bipedRightLeg.setPos(-2.0F, 12.0F, -1.0F);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F+scale);

        this.tail1 = new ModelRenderer(this, 10, 16);
        this.tail1.setPos(0.0F, 11.0F, 0.0F);
        this.tail1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, -0.2F+scale);
        this.setRotateAngle(tail1, -0.4553564018453205F, 0.0F, 0.0F);
        this.tail2 = new ModelRenderer(this, 10, 30);
        this.tail2.setPos(0.0F, 0F, 6.9F);
        this.tail2.addBox(-1.0F, -1.0F, -0.7F, 2, 2, 7, -0.3F+scale);
        this.tailend = new ModelRenderer(this, 10, 45);
        this.tailend.setPos(0.0F, 0F, 6F);
        this.tailend.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 7, 0.0F+scale);

        this.bipedHead.addChild(mouthup);
        this.bipedHead.addChild(earleft);
        this.bipedHead.addChild(earright);
        this.bipedHead.addChild(mouthdown);
        this.tail1.addChild(tail2);
        this.tail2.addChild(tailend);

        this.Chestplate = new ModelRenderer(this, 35, 10);
        this.Chestplate.setPos(0.0F, 0F, 0F);
        this.Chestplate.addBox(-3F, 0.0F, -1.5F, 6, 10, 3, 0.1F+scale);
        this.bipedBody.addChild(Chestplate);
        this.Armplates = new ModelRenderer(this, 35, 24);
        this.Armplates.setPos(0F, 0F, 0F);
        this.Armplates.addBox(-0.5F, -2.0F, -0.5F, 1, 10, 1, 0.2F+scale);
        this.bipedLeftArm.addChild(Armplates);
        this.bipedRightArm.addChild(Armplates);
        this.Legplates = new ModelRenderer(this, 35, 36);
        this.Legplates.setPos(0F, 0F, 0F);
        this.Legplates.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.1F+scale);
        this.bipedLeftLeg.addChild(Legplates);
        this.bipedRightLeg.addChild(Legplates);
        this.Helmet = new ModelRenderer(this,44,24);
        this.Helmet.setPos(0F, 0F, 0F);
        this.Helmet.addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3, 0.1F+scale);
        this.bipedHead.addChild(Helmet);
        this.Helmetfront = new ModelRenderer(this,44,30);
        this.Helmetfront.setPos(0F, 0F, 0F);
        this.Helmetfront.addBox(-1.5F, -2.0F, -2.5F, 3, 2, 3, -0.2F+scale);
        this.mouthup.addChild(Helmetfront);
        this.Crest= new ModelRenderer(this,44,35);
        this.Crest.setPos(0,0,0);
        this.Crest.addBox(-0.5F, -5F, -1.5F, 1, 2, 3, -0.1F+scale);
        this.bipedHead.addChild(Crest);
        this.pagne = new ModelRenderer(this, 35, 50);
        this.pagne.setPos(0.0F, 0F, 0F);
        this.pagne.addBox(-3.0F, 9F, 1.5F, 6, 5, 3, 0.1F+scale);
        this.setRotateAngle(pagne, -0.31869712141416456F, 0.0F, 0.0F);
        this.bipedBody.addChild(pagne);
        this.HelmetfrontFull = new ModelRenderer(this, 0, 55);
        this.HelmetfrontFull.setPos(0.0F, 0.2F, -1.6F);
        this.HelmetfrontFull.addBox(-1.5F, -2.1F, -0.8F, 3, 3, 3, -0.1F+scale);
        this.mouthup.addChild(HelmetfrontFull);



    }

    protected Iterable<ModelRenderer> getHead() {
        return ImmutableList.of(this.bipedHead);
    }


    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.bipedBody, this.bipedRightArm, this.bipedLeftArm, this.bipedRightLeg, this.bipedLeftLeg, this.tail1);
    }




    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {

            this.getHead().forEach((p_228228_8_) -> {
                p_228228_8_.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
            });
            this.getParts().forEach((p_228227_8_) -> {
                p_228227_8_.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
            });


    }


    public void setupAnim(T entityIn,float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if ((entityIn).isAggressive())
        {
            float f = MathHelper.sin(this.attackTime * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * (float) Math.PI);
            this.bipedRightArm.zRot = 0.0F;
            this.bipedLeftArm.zRot = 0.0F;
            this.bipedRightArm.yRot = -(0.1F - f * 0.6F);
            this.bipedLeftArm.yRot = 0.1F - f * 0.6F;
            this.bipedRightArm.xRot = -((float) Math.PI / 2.2F);
            this.bipedLeftArm.xRot = -((float) Math.PI / 2.2F);
            this.bipedRightArm.xRot -= f * 1.2F - f1 * 0.4F;
            this.bipedLeftArm.xRot -= f * 1.2F - f1 * 0.4F;
            this.bipedRightArm.zRot += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.bipedLeftArm.zRot -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.bipedRightArm.xRot += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.bipedLeftArm.xRot -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.bipedHead.yRot = netHeadYaw * 0.017453292F;
            this.bipedHead.xRot = headPitch * 0.017453292F;

        }
        else{

            bipedHead.xRot=0F;
            bipedHead.yRot=0;
            bipedRightArm.zRot=0F;
            bipedRightArm.zRot += MathHelper.cos(ageInTicks*1.1F)*0.04F;
            bipedLeftArm.zRot=0F;
            bipedLeftArm.zRot -= MathHelper.cos(ageInTicks*1.1F)*0.04F;
            bipedLeftArm.yRot=0F;
            bipedRightArm.yRot=0F;
            bipedRightArm.xRot=0F;
            bipedLeftArm.xRot=0F;
            bipedHead.xRot += MathHelper.cos(ageInTicks*1.1F)*0.04F;

        }


        this.bipedRightLeg.xRot = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.bipedLeftLeg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI)  * limbSwingAmount;
        if(limbSwing<0.05) {
            tail1.yRot = MathHelper.cos(ageInTicks * 0.3F) * 0.2F;
            tail2.yRot = MathHelper.cos(ageInTicks * 0.3F) * 0.15F;
            tailend.yRot = MathHelper.cos(ageInTicks * 0.3F) * 0.15F;
        }else{
            tail1.yRot = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount*0.8F;
            tail2.yRot = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount*0.8F;
            tailend.yRot = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount*0.8F;

        }
        this.bipedRightLeg.yRot = 0.0F;
        this.bipedLeftLeg.yRot = 0.0F;
        this.bipedRightLeg.zRot = 0.0F;
        this.bipedLeftLeg.zRot = 0.0F;


    }



    public void translateToHand(HandSide p_225599_1_, MatrixStack p_225599_2_) {
        this.getArmForSide(p_225599_1_).translateAndRotate(p_225599_2_);
    }


    public ModelRenderer getArmForSide(HandSide side) {
        return side == HandSide.LEFT ? this.bipedLeftArm : this.bipedRightArm;
    }




/*
    public void func_225599_a_(HandSide p_225599_1_, MatrixStack p_225599_2_) {
        float f = p_225599_1_ == HandSide.RIGHT ? 1.0F : -1.0F;
        ModelRenderer modelrenderer = this.getArmForSide(p_225599_1_);
        modelrenderer.rotationPointX += f;
        modelrenderer.func_228307_a_(p_225599_2_);
        modelrenderer.rotationPointX -= f;
    }

*/

}
