package warhammermod.Entities.Living.Renders.Models;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.Living.PegasusEntity;


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
      this.WingLBone.setPos(4.5F,5F , -6.5F);
      this.WingLBone.addBox(0F, -1F, -1F, 21, 2, 2, -0.2F);

      this.wingRlarge = new ModelRenderer(this, 0, 70);
      this.wingRlarge.setPos(-20F, 0F, 0F);
      this.wingRlarge.addBox(0F, -0.5F, 0.5F, 20, 1, 15, 0.0F);

      this.wingLlarge = new ModelRenderer(this, 0, 70);
      this.wingLlarge.setPos(0F, 0F, 0F);
      this.wingLlarge.addBox(0F, -0.5F, 0.5F, 20, 1, 15, 0.0F);

      this.wingLend = new ModelRenderer(this,8 , 87);
      this.wingLend.setPos(20F, 0F, 0F);
      this.wingLend.addBox(0F, -0.5F, 0.5F, 15, 1, 15, 0.0F);

      this.wingRend = new ModelRenderer(this, 8, 104);
      this.wingRend.setPos(-20F, 0F, 0F);
      this.wingRend.addBox(-15F, -0.5F, 0.5F, 15, 1, 15, 0.0F);

      this.WingRbone = new ModelRenderer(this, 0, 65);
      this.WingRbone.setPos(-4.5F, 5.0F, -6.5F);
      this.WingRbone.addBox(-21.0F, -1.0F, -1.0F, 21, 2, 2, -0.2F);

      this.WingLBone.addChild(wingLlarge);
      this.WingLBone.addChild(wingLend);
      this.WingRbone.addChild(wingRlarge);
      this.WingRbone.addChild(wingRend);
   }

   public Iterable<ModelRenderer> bodyParts() {
      return Iterables.concat(super.bodyParts(), ImmutableList.of(this.WingLBone, this.WingRbone));
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

   public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
      super.prepareMobModel(entityIn,limbSwing,limbSwingAmount,partialTick);
      if(entityIn.isIsstationaryflying()) {
         if (WingAngle > 0.6) {
            WingDirection=-Math.abs(WingDirection);
         }else if (WingAngle <-0.6){WingDirection= Math.abs(WingDirection);}
         WingLBone.zRot=WingAngle;
         wingLend.zRot=WingAngle/2.1F;
         WingRbone.zRot=-WingAngle;
         wingRend.zRot=-WingAngle/2.1F;
         WingAngle+=WingDirection*timeinterval(partialTick);
         WingRbone.xRot=0F;
         WingRbone.yRot=0F;
         WingLBone.xRot=0F;
         WingLBone.yRot=0F;
         wingLend.yRot=0F;
         wingRend.yRot=0F;
         wingRend.xRot=0F;
         wingLend.xRot=0F;
      }
      else if(entityIn.isIselytrafly()){
         if(entityIn.xRot>0){WingAngleElytra=0.1F;}
         else if(Math.abs(WingAngleElytra) > 0.2) {
            WingDirection*=-1;
         }
         WingLBone.zRot=-WingAngleElytra;
         wingLend.zRot=WingAngleElytra;
         WingRbone.zRot=WingAngleElytra;
         wingRend.zRot=-WingAngleElytra;
         WingLBone.yRot=-0.15F;
         WingRbone.yRot=0.15F;
         wingLend.yRot=0F;
         wingRend.yRot=0F;
         wingRend.xRot=0F;
         wingLend.xRot=0F;
         WingRbone.xRot=0F;
         WingLBone.xRot=0F;
         //WingAngleElytra+=WingDirection*0.4F;

      }
      //should only activate when flyingdownsafely rework ongroundtimer not working when half of the entity not on a block
      else if(entityIn.getDeltaMovement().y < -0.745 && !entityIn.isVehicle()){
         WingLBone.zRot=-0.1F;
         wingLend.zRot=0.1F;
         WingRbone.zRot=0.1F;
         wingRend.zRot=-0.1F;
         WingLBone.yRot=-0.15F;
         WingRbone.yRot=0.15F;
         wingLend.yRot=0F;
         wingRend.yRot=0F;
         wingRend.xRot=0F;
         wingLend.xRot=0F;
         WingRbone.xRot=0F;
         WingLBone.xRot=0F;
      }
      else{ //on ground
         WingRbone.xRot=0.61F;
         WingRbone.yRot=0.94F;
         WingRbone.zRot=-1.04F;
         WingLBone.xRot=0.61F;
         WingLBone.yRot=-0.94F;
         WingLBone.zRot=1.04F;
         wingLend.yRot=-0.82F;
         wingRend.yRot=0.82F;
         wingRend.xRot=-0.06F;
         wingLend.xRot=-0.06F;
      }
      if(!entityIn.isgrounded()){
         if(ongroundtimer<18) ongroundtimer++;
      }
      else ongroundtimer=0;


   }



   }