package warhammermod.Entities.Living.Renders;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.Living.PegasusEntity;
import warhammermod.Entities.Living.Renders.Layers.LeatherPegasusArmorLayer;
import warhammermod.Entities.Living.Renders.Layers.PegasusMarkingsLayer;
import warhammermod.Entities.Living.Renders.Models.Pegasusmodel;
import warhammermod.utils.reference;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public final class PegasusRenderer extends AbstractHorseRenderer256<PegasusEntity, Pegasusmodel<PegasusEntity>> {
   private static final Map<CoatColors, ResourceLocation> COAT_COLORS_RESOURCE_LOCATION_MAP = Util.make(Maps.newEnumMap(CoatColors.class), (p_239384_0_) -> {
      p_239384_0_.put(CoatColors.WHITE, new ResourceLocation("textures/entity/horse/horse_white.png"));
      p_239384_0_.put(CoatColors.CREAMY, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
      p_239384_0_.put(CoatColors.CHESTNUT, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
      p_239384_0_.put(CoatColors.BROWN, new ResourceLocation("textures/entity/horse/horse_brown.png"));
      p_239384_0_.put(CoatColors.BLACK, new ResourceLocation("textures/entity/horse/horse_black.png"));
      p_239384_0_.put(CoatColors.GRAY, new ResourceLocation("textures/entity/horse/horse_gray.png"));
      p_239384_0_.put(CoatColors.DARKBROWN, new ResourceLocation("textures/entity/horse/horse_darkbrown.png"));
   });


   private static final ResourceLocation PEGASUS_TEXTURE = new ResourceLocation(reference.modid,"textures/entity/pegasus.png");

   public PegasusRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new Pegasusmodel<>(0.0F), 1.1F);
      this.addLayer(new PegasusMarkingsLayer(this));
      this.addLayer(new LeatherPegasusArmorLayer(this));
   }

   public ResourceLocation getTextureLocation(PegasusEntity entity)
   {
      if(entity.ismixblood()) {
         return COAT_COLORS_RESOURCE_LOCATION_MAP.get(entity.getVariant());
      }
      return PEGASUS_TEXTURE;
   }
}