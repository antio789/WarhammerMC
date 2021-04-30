package warhammermod.Entities.Projectile.Render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.Projectile.SpearEntity;
import warhammermod.utils.reference;

@OnlyIn(Dist.CLIENT)
public class RenderSpear<T extends SpearEntity> extends ArrowRenderer<SpearEntity> {
   public RenderSpear(EntityRendererManager renderManagerIn) {
      super(renderManagerIn);
   }
   @Override
   public ResourceLocation getTextureLocation(SpearEntity p_110775_1_) {
      return SPEAR8TEXTURE;
   }
   public static final ResourceLocation SPEAR8TEXTURE = new ResourceLocation(reference.modid,"textures/entity/spear.png");

}