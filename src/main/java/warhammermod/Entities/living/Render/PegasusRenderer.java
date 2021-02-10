package warhammermod.Entities.living.Render;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.living.PegasusEntity;
import warhammermod.Entities.living.Render.layers.LeatherPegasusArmorLayer;
import warhammermod.Entities.living.Render.model.Pegasusmodel;
import warhammermod.util.reference;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class PegasusRenderer extends AbstractHorseRenderer256<PegasusEntity, Pegasusmodel<PegasusEntity>> {
   private static final ResourceLocation PEGASUS_TEXTURE = new ResourceLocation(reference.modid,"textures/entity/pegasus.png");
   private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();

   public PegasusRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new Pegasusmodel<>(0.0F), 1.1F);
      this.addLayer(new LeatherPegasusArmorLayer(this));
   }

   public ResourceLocation getEntityTexture(PegasusEntity entity)
   {
      if(entity.getBloodtype()) {

         String s = entity.getHorseTexture();
         ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);
         if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(entity.getVariantTexturePaths()));
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
         }
         return LAYERED_LOCATION_CACHE.put(s,resourcelocation);
      }

      return PEGASUS_TEXTURE;
   }
}