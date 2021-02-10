package warhammermod.Entities.living.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.living.Render.model.HorseModelx;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractHorseRenderer256<T extends AbstractHorseEntity, M extends HorseModelx<T>> extends MobRenderer<T, M> {
   private final float scale;

   public AbstractHorseRenderer256(EntityRendererManager p_i50975_1_, M p_i50975_2_, float p_i50975_3_) {
      super(p_i50975_1_, p_i50975_2_, 0.75F);
      this.scale = p_i50975_3_;
   }

   protected void preRenderCallback(T p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
      p_225620_2_.scale(this.scale, this.scale, this.scale);
      super.preRenderCallback(p_225620_1_, p_225620_2_, p_225620_3_);
   }
}