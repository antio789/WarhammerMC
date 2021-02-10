package warhammermod.Entities.living.Render.model;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import warhammermod.Entities.living.DwarfEntity;


public class DwarfModel<T extends DwarfEntity> extends SegmentedModel<T> implements IHasArm, IHasHead
{
    /** The head box of the VillagerModel */
    public ModelRenderer head;
    /** The body of the VillagerModel */
    public ModelRenderer villagerBody;
    /** The arms of the VillagerModel */
    public ModelRenderer villagerArms;
    /** The right leg of the VillagerModel */
    public ModelRenderer rightVillagerLeg;
    /** The left leg of the VillagerModel */
    public ModelRenderer leftVillagerLeg;
    public ModelRenderer villagerNose;
    public ModelRenderer villagerbeard;
    public ModelRenderer villagermustache1;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;

    protected ModelRenderer hat;
    protected ModelRenderer hat2;


    public DwarfModel(float scale)
    {
        this(scale, 0.0F, 128, 128);
    }

    public DwarfModel(float scale, float p_i1164_2_, int width, int height)
    {
        this.head = (new ModelRenderer(this)).setTextureSize(width, height);
        this.head.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
        this.head.setTextureOffset(0, 0).addBox(-4.0F, -6.0F, -4.0F, 8, 10, 8, scale);
        this.hat = (new ModelRenderer(this)).setTextureSize(width, height);
        this.hat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat.setTextureOffset(65, 0).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 10.0F, 8.0F, scale + 0.5F);
        this.hat2 = (new ModelRenderer(this)).setTextureSize(width, height);
        this.hat2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat2.setTextureOffset(65, 25).addBox(-8.0F, -8.0F, -3.0F, 16.0F, 16.0F, 1.0F, scale);
        this.hat2.rotateAngleX = (-(float) Math.PI / 2F);
        this.hat.addChild(this.hat2);
        this.head.addChild(hat);

        this.villagerNose = (new ModelRenderer(this)).setTextureSize(width, height);
        this.villagerNose.setRotationPoint(0.0F, p_i1164_2_ - 2.0F, 0.0F);
        this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, 2.0F, -6.0F, 2, 4, 2, scale);
        this.villagerbeard= (new ModelRenderer(this)).setTextureSize(width,height);
        this.villagerbeard.setRotationPoint(0.0F, p_i1164_2_ - 2.0F, 0.0F);
        this.villagerbeard.setTextureOffset(36,38).addBox(-4.0F,6.0F,-5.0F,8,6,1,0);
        this.villagermustache1= (new ModelRenderer(this)).setTextureSize(width,height);
        this.villagermustache1.setRotationPoint(0.0F, p_i1164_2_ - 2.0F, 0.0F);
        this.villagermustache1.setTextureOffset(36,38).addBox(-4.0F,3.0F,-5.0F,2,3,1,0);
        this.villagermustache1.setTextureOffset(36,38).addBox(2.0F,3.0F,-5.0F,2,3,1,0);
        this.villagermustache1.setTextureOffset(36,38).addBox(-2.0F,3.0F,-5.0F,1,1,1,0);
        this.villagermustache1.setTextureOffset(36,38).addBox(1.0F,3.0F,-5.0F,1,1,1,0);
        this.head.addChild(this.villagerbeard);
        this.head.addChild(this.villagermustache1);
        this.head.addChild(this.villagerNose);
        this.villagerBody = (new ModelRenderer(this)).setTextureSize(width, height);
        this.villagerBody.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
        this.villagerBody.setTextureOffset(0, 19).addBox(-5.0F, 4.0F, -3.0F, 10, 10, 7, scale);
        this.villagerBody.setTextureOffset(0, 38).addBox(-5.0F, 4.0F, -3.0F, 10, 16, 7, scale + 0.5F);
        this.villagerArms = (new ModelRenderer(this)).setTextureSize(width, height);
        this.villagerArms.setRotationPoint(0.0F, 0.0F + p_i1164_2_ + 2.0F, 0.0F);
        this.villagerArms.setTextureOffset(40, 16).addBox(-8.0F, 1.0F, 1.0F, 4, 8, 4, scale+0.009F);
        this.villagerArms.setTextureOffset(40, 16).addBox(4.0F, 1.0F, 1.0F, 4, 8, 4, scale+0.009F);
        this.villagerArms.setTextureOffset(36, 29).addBox(-4.0F, 5.0F, 1.0F, 8, 4, 4, scale+0.009F);
        this.rightVillagerLeg = (new ModelRenderer(this, 34, 0)).setTextureSize(width, height);
        this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + p_i1164_2_, 0.0F);
        this.rightVillagerLeg.addBox(-3F, 2.0F, -2.0F, 5, 10, 5, scale);
        this.leftVillagerLeg = (new ModelRenderer(this, 34, 0)).setTextureSize(width, height);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F + p_i1164_2_, 0.0F);
        this.leftVillagerLeg.addBox(-2F, 2.0F, -2.0F, 5, 10, 5, scale);
        //this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        //this.bipedHeadwear.addBox()(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.5F);
        //this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
        this.rightArm = (new ModelRenderer(this, 35, 46)).setTextureSize(width, height);
        this.rightArm.addBox(-3F, -2F, 2F, 4, 10, 4, scale);
        this.rightArm.setRotationPoint(-5.0F, 2.0F + p_i1164_2_, 0.0F);
        this.leftArm = (new ModelRenderer(this, 35, 46)).setTextureSize(width, height);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1.0F, 2.0F, -2F, 4, 10, 4, scale);
        this.leftArm.setRotationPoint(5.0F, 2.0F + p_i1164_2_, 0.0F);
    }


    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.head, this.villagerBody, this.rightVillagerLeg, this.leftVillagerLeg, this.villagerArms, this.rightArm, this.leftArm);
    }
    /**
     * Sets the models various rotation angles then renders the model.
     */


    /**
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.head.render(scale);
        this.villagerBody.render(scale);
        this.rightVillagerLeg.render(scale);
        this.leftVillagerLeg.render(scale);

        if(entityIn instanceof EntityDwarf){
        EntityDwarf entityDwarf = (EntityDwarf)entityIn;
        if (entityDwarf.getArmPose()== AbstractIllager.IllagerArmPose.CROSSED)
        {
            this.villagerArms.render(scale+0.009F);

        }
        else
        {

            this.rightArm.render(scale+0.009F);
            this.leftArm.render(scale+0.009F);
        }
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */

    public void setRotationAngles(DwarfEntity entityIn,float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = entityIn.getShakeHeadTicks()>0;

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        if (flag) {
            this.head.rotateAngleZ = 0.3F * MathHelper.sin(0.45F * ageInTicks);
            this.head.rotateAngleX = 0.4F;
        } else {
            this.head.rotateAngleZ = 0.0F;
        }
        this.villagerArms.rotationPointY = 3.0F;
        this.villagerArms.rotationPointZ = -1.0F;
        this.villagerArms.rotateAngleX = -0.75F;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.rightVillagerLeg.rotateAngleY = 0.0F;
        this.leftVillagerLeg.rotateAngleY = 0.0F;


            DwarfEntity entityDwarf = (DwarfEntity) entityIn;
            if (entityDwarf.getArmPose()== AbstractIllagerEntity.ArmPose.ATTACKING || entityDwarf.isBattleready())
            {
                float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
                float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
                this.rightArm.rotateAngleZ = 0.0F;
                this.leftArm.rotateAngleZ = 0.0F;
                this.rightArm.rotateAngleY = 0.15707964F;
                this.leftArm.rotateAngleY = -0.15707964F;

                if(entityDwarf.getProfession().getName().equals("slayer")){
                    this.rightArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
                    this.leftArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
                    this.rightArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                    this.leftArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                }
                else if ((entityIn).getPrimaryHand() == HandSide.RIGHT)
                {
                    this.rightArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
                    this.leftArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
                    this.rightArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                    this.leftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                }
                else
                {
                    this.rightArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
                    this.leftArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
                    this.rightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                    this.leftArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                }

                this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
                this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            }

        boolean attacking = (entityIn.getArmPose() == AbstractIllagerEntity.ArmPose.ATTACKING);
        this.villagerArms.showModel = !attacking;
        this.leftArm.showModel = attacking;
        this.rightArm.showModel = attacking;

    }

    private ModelRenderer getArm(HandSide p_191216_1_) {
        return p_191216_1_ == HandSide.LEFT ? this.leftArm : this.rightArm;
    }
    public ModelRenderer getModelHead() {
        return this.head;
    }

    public void translateHand(HandSide p_225599_1_, MatrixStack p_225599_2_) {
        this.getArm(p_225599_1_).translateRotate(p_225599_2_);
    }
}
