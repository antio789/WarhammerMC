package warhammermod.Entities.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.SpearEntity;
import warhammermod.util.reference;

@OnlyIn(Dist.CLIENT)
public class RenderSpear<T extends SpearEntity> extends ArrowRenderer<SpearEntity> {
   public RenderSpear(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
   }

   public static final ResourceLocation SPEAR8TEXTURE = new ResourceLocation(reference.modid,"textures/entity/spear.png");

   public ResourceLocation getEntityTexture(SpearEntity entity) {
      return SPEAR8TEXTURE;
   }
}