package warhammermod.Entities.Living.Renders.Layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkavenHeldItemLayer<T extends LivingEntity, M extends EntityModel<T> & IHasArm> extends LayerRenderer<T, M> {
   public SkavenHeldItemLayer(IEntityRenderer<T, M> p_i50934_1_) {
      super(p_i50934_1_);
   }

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      boolean flag = entitylivingbaseIn.getMainArm() == HandSide.RIGHT;
      ItemStack itemstack = flag ? entitylivingbaseIn.getOffhandItem() : entitylivingbaseIn.getMainHandItem();
      ItemStack itemstack1 = flag ? entitylivingbaseIn.getMainHandItem() : entitylivingbaseIn.getOffhandItem();
      if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
         matrixStackIn.pushPose();
         if (this.getParentModel().young) {
            float f = 0.5F;
            matrixStackIn.translate(0.0D, 0.75D, 0.0D);
            matrixStackIn.scale(f, f, f);
         }

         this.renderArmWithItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT, matrixStackIn, bufferIn, packedLightIn);
         this.renderArmWithItemleft(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT, matrixStackIn, bufferIn, packedLightIn);
         matrixStackIn.popPose();
      }
   }

   private void renderArmWithItem(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack p_229135_5_, IRenderTypeBuffer p_229135_6_, int transparency) {
      if (!p_229135_2_.isEmpty()) {
         p_229135_5_.pushPose();
         this.getParentModel().translateToHand(p_229135_4_, p_229135_5_);
         p_229135_5_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
         p_229135_5_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
         boolean flag = false;
         p_229135_5_.translate((double)((float) 1 / 16.0F)-0.05, 0.125D , -0.625D);
         float f = 0.8F;
         p_229135_5_.scale(f, f, f);
         Minecraft.getInstance().getItemInHandRenderer().renderItem(p_229135_1_, p_229135_2_, p_229135_3_, flag, p_229135_5_, p_229135_6_, transparency);
         p_229135_5_.popPose();
      }
   }
   private void renderArmWithItemleft(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack p_229135_5_, IRenderTypeBuffer p_229135_6_, int transparency) {
      if (!p_229135_2_.isEmpty()) {
         p_229135_5_.pushPose();
         this.getParentModel().translateToHand(p_229135_4_, p_229135_5_);
         p_229135_5_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
         p_229135_5_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
         boolean flag = p_229135_4_ == HandSide.LEFT;
         p_229135_5_.translate((double)((float)(flag ? -1 : 1) / 16.0F), 0.125D, -0.625D);
         float f = 0.8F;
         p_229135_5_.scale(f, f, f);
         Minecraft.getInstance().getItemInHandRenderer().renderItem(p_229135_1_, p_229135_2_, p_229135_3_, flag, p_229135_5_, p_229135_6_, transparency);
         p_229135_5_.popPose();
      }
   }
}