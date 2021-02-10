package warhammermod.Entities.living.Render.model;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.living.PegasusEntity;


@OnlyIn(Dist.CLIENT)
public class Pegasusmodel<T extends PegasusEntity> extends HorseModelx<T> {

   public ModelRenderer WingRbone;
   public ModelRenderer WingLBone;
   public ModelRenderer wingLlarge;
   public ModelRenderer wingRlarge;
   public ModelRenderer wingRend;
   public ModelRenderer wingLend;

   public Pegasusmodel(float size) {
      super(size);
      this.WingLBone = new ModelRenderer(this, 0, 65);
      this.WingLBone.setRotationPoint(4.5F,5F , -6.5F);
      this.WingLBone.addBox(0F, -1F, -1F, 21, 2, 2, -0.2F);

      this.wingRlarge = new ModelRenderer(this, 0, 70);
      this.wingRlarge.setRotationPoint(-20F, 0F, 0F);
      this.wingRlarge.addBox(0F, -0.5F, 0.5F, 20, 1, 15, 0.0F);

      this.wingLlarge = new ModelRenderer(this, 0, 70);
      this.wingLlarge.setRotationPoint(0F, 0F, 0F);
      this.wingLlarge.addBox(0F, -0.5F, 0.5F, 20, 1, 15, 0.0F);

      this.wingLend = new ModelRenderer(this,8 , 87);
      this.wingLend.setRotationPoint(20F, 0F, 0F);
      this.wingLend.addBox(0F, -0.5F, 0.5F, 15, 1, 15, 0.0F);

      this.wingRend = new ModelRenderer(this, 8, 104);
      this.wingRend.setRotationPoint(-20F, 0F, 0F);
      this.wingRend.addBox(-15F, -0.5F, 0.5F, 15, 1, 15, 0.0F);

      this.WingRbone = new ModelRenderer(this, 0, 65);
      this.WingRbone.setRotationPoint(-4.5F, 5.0F, -6.5F);
      this.WingRbone.addBox(-21.0F, -1.0F, -1.0F, 21, 2, 2, -0.2F);

      this.WingLBone.addChild(wingLlarge);
      this.WingLBone.addChild(wingLend);
      this.WingRbone.addChild(wingRlarge);
      this.WingRbone.addChild(wingRend);
   }

   public Iterable<ModelRenderer> getBodyParts() {
      return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.WingLBone, this.WingRbone));
   }

   private float WingAngle=0.2F;
   private float WingDirection=0.08F;
   private float WingAngleElytra=0.1F;
   private Byte ongroundtimer=0;
   private float prevpartial;
   private float timeinterval(float tick){
      float result = Math.abs(tick-prevpartial);
      prevpartial=tick;
      return result;
   }

   public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      super.setLivingAnimations(entityIn,limbSwing,limbSwingAmount,partialTick);
      if(entityIn.isIsstationaryflying()) {
         if (WingAngle > 0.6) {
            WingDirection=-Math.abs(WingDirection);
         }else if (WingAngle <-0.6){WingDirection= Math.abs(WingDirection);}
         WingLBone.rotateAngleZ=WingAngle;
         wingLend.rotateAngleZ=WingAngle/2.1F;
         WingRbone.rotateAngleZ=-WingAngle;
         wingRend.rotateAngleZ=-WingAngle/2.1F;
         WingAngle+=WingDirection*timeinterval(partialTick);
         WingRbone.rotateAngleX=0F;
         WingRbone.rotateAngleY=0F;
         WingLBone.rotateAngleX=0F;
         WingLBone.rotateAngleY=0F;
         wingLend.rotateAngleY=0F;
         wingRend.rotateAngleY=0F;
         wingRend.rotateAngleX=0F;
         wingLend.rotateAngleX=0F;
      }else if(entityIn.isIselytrafly()){
         if(entityIn.rotationPitch>0){WingAngleElytra=0.1F;}
         else if(Math.abs(WingAngleElytra) > 0.2) {
            WingDirection*=-1;
         }
         WingLBone.rotateAngleZ=-WingAngleElytra;
         wingLend.rotateAngleZ=WingAngleElytra;
         WingRbone.rotateAngleZ=WingAngleElytra;
         wingRend.rotateAngleZ=-WingAngleElytra;
         WingLBone.rotateAngleY=-0.15F;
         WingRbone.rotateAngleY=0.15F;
         wingLend.rotateAngleY=0F;
         wingRend.rotateAngleY=0F;
         wingRend.rotateAngleX=0F;
         wingLend.rotateAngleX=0F;
         WingRbone.rotateAngleX=0F;
         WingLBone.rotateAngleX=0F;
         //WingAngleElytra+=WingDirection*0.4F;

      }
      else if(ongroundtimer>17){
         WingLBone.rotateAngleZ=-0.1F;
         wingLend.rotateAngleZ=0.1F;
         WingRbone.rotateAngleZ=0.1F;
         wingRend.rotateAngleZ=-0.1F;
         WingLBone.rotateAngleY=-0.15F;
         WingRbone.rotateAngleY=0.15F;
         wingLend.rotateAngleY=0F;
         wingRend.rotateAngleY=0F;
         wingRend.rotateAngleX=0F;
         wingLend.rotateAngleX=0F;
         WingRbone.rotateAngleX=0F;
         WingLBone.rotateAngleX=0F;
      }
      else{ //on ground
         WingRbone.rotateAngleX=0.61F;
         WingRbone.rotateAngleY=0.94F;
         WingRbone.rotateAngleZ=-1.04F;
         WingLBone.rotateAngleX=0.61F;
         WingLBone.rotateAngleY=-0.94F;
         WingLBone.rotateAngleZ=1.04F;
         wingLend.rotateAngleY=-0.82F;
         wingRend.rotateAngleY=0.82F;
         wingRend.rotateAngleX=-0.06F;
         wingLend.rotateAngleX=-0.06F;
      }
      if(!entityIn.isgrounded()){
         if(ongroundtimer<18) ongroundtimer++;
      }
      else ongroundtimer=0;


   }



   }