package warhammermod.Entities.living.Render.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.passive.horse.CoatTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.living.PegasusEntity;
import warhammermod.Entities.living.Render.model.Pegasusmodel;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PegasusMarkingsLayer extends LayerRenderer<PegasusEntity, Pegasusmodel<PegasusEntity>> {
   private static final Map<CoatTypes, ResourceLocation> field_239405_a_ = Util.make(Maps.newEnumMap(CoatTypes.class), (p_239406_0_) -> {
      p_239406_0_.put(CoatTypes.NONE, (ResourceLocation)null);
      p_239406_0_.put(CoatTypes.WHITE, new ResourceLocation("textures/entity/horse/horse_markings_white.png"));
      p_239406_0_.put(CoatTypes.WHITE_FIELD, new ResourceLocation("textures/entity/horse/horse_markings_whitefield.png"));
      p_239406_0_.put(CoatTypes.WHITE_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_whitedots.png"));
      p_239406_0_.put(CoatTypes.BLACK_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_blackdots.png"));
   });

   public PegasusMarkingsLayer(IEntityRenderer<PegasusEntity, Pegasusmodel<PegasusEntity>> p_i232476_1_) {
      super(p_i232476_1_);
   }

   public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, PegasusEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      ResourceLocation resourcelocation = field_239405_a_.get(entitylivingbaseIn.func_234240_eM_());
      if (resourcelocation != null && !entitylivingbaseIn.isInvisible()) {
         IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(resourcelocation));
         this.getEntityModel().render(matrixStackIn, ivertexbuilder, packedLightIn, LivingRenderer.getPackedOverlay(entitylivingbaseIn, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}