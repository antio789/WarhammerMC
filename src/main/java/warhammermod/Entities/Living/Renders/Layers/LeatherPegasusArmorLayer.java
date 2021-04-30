package warhammermod.Entities.Living.Renders.Layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.Living.PegasusEntity;
import warhammermod.Entities.Living.Renders.Models.Pegasusmodel;


@OnlyIn(Dist.CLIENT)
public class LeatherPegasusArmorLayer extends LayerRenderer<PegasusEntity, Pegasusmodel<PegasusEntity>> {
   private final Pegasusmodel<PegasusEntity> model = new Pegasusmodel<>(0.1F);

   public LeatherPegasusArmorLayer(IEntityRenderer<PegasusEntity, Pegasusmodel<PegasusEntity>> p_i50937_1_) {
      super(p_i50937_1_);
   }

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, PegasusEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      ItemStack itemstack = entitylivingbaseIn.getArmor();
      if (itemstack.getItem() instanceof HorseArmorItem) {
         HorseArmorItem horsearmoritem = (HorseArmorItem)itemstack.getItem();
         this.getParentModel().copyPropertiesTo(this.model);
         this.model.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.model.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         float f;
         float f1;
         float f2;
         if (horsearmoritem instanceof DyeableHorseArmorItem) {
            int i = ((DyeableHorseArmorItem)horsearmoritem).getColor(itemstack);
            f = (float)(i >> 16 & 255) / 255.0F;
            f1 = (float)(i >> 8 & 255) / 255.0F;
            f2 = (float)(i & 255) / 255.0F;
         } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
         }

         IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(horsearmoritem.getTexture()));
         this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
      }
   }
}