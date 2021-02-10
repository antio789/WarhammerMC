package warhammermod.Entities.living.Render.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.living.PegasusEntity;
import warhammermod.Entities.living.Render.model.Pegasusmodel;

@OnlyIn(Dist.CLIENT)
public class LeatherPegasusArmorLayer extends LayerRenderer<PegasusEntity, Pegasusmodel<PegasusEntity>> {
   private final Pegasusmodel<PegasusEntity> field_215341_a = new Pegasusmodel<>(0.1F);

   public LeatherPegasusArmorLayer(IEntityRenderer<PegasusEntity, Pegasusmodel<PegasusEntity>> p_i50937_1_) {
      super(p_i50937_1_);
   }

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, PegasusEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      ItemStack itemstack = entitylivingbaseIn.func_213803_dV();
      if (itemstack.getItem() instanceof HorseArmorItem) {
         HorseArmorItem horsearmoritem = (HorseArmorItem) itemstack.getItem();
         this.getEntityModel().copyModelAttributesTo(this.field_215341_a);
         this.field_215341_a.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.field_215341_a.setRotationAngles(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         float f;
         float f1;
         float f2;
         if (horsearmoritem instanceof DyeableHorseArmorItem) {
            int i = ((DyeableHorseArmorItem) horsearmoritem).getColor(itemstack);
            f = (float) (i >> 16 & 255) / 255.0F;
            f1 = (float) (i >> 8 & 255) / 255.0F;
            f2 = (float) (i & 255) / 255.0F;
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }


      }
   }
}