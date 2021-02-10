package warhammermod.Entities.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.BulletEntity;
import warhammermod.Entities.HalberdEntity;
import warhammermod.util.reference;

@OnlyIn(Dist.CLIENT)
public class Halberdrender extends EntityRenderer<HalberdEntity> {
    private static final ResourceLocation BULLET_TEXTURE = new ResourceLocation(reference.modid,"textures/entity/bullet.png");


    public Halberdrender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(BulletEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {

    }

    public ResourceLocation getEntityTexture(HalberdEntity entity) {
        return BULLET_TEXTURE;
    }
}
