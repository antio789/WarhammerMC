package warhammermod.Entities.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import warhammermod.Entities.GrenadeEntity;
import warhammermod.Entities.Render.Model.model_grenade;
import warhammermod.util.reference;


public class GrenadeRender extends EntityRenderer<GrenadeEntity>
{


    private final model_grenade model= new model_grenade();
    private static final ResourceLocation grenade_texture = new ResourceLocation(reference.modid,"textures/entity/grenade.png");

    public GrenadeRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void render(GrenadeEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        p_225623_4_.push();
        p_225623_4_.translate(0.0D, (double) 0.15F, 0.0D);
        p_225623_4_.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(p_225623_3_, p_225623_1_.prevRotationYaw, p_225623_1_.rotationYaw) - 90.0F));
        p_225623_4_.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(p_225623_3_, p_225623_1_.prevRotationPitch, p_225623_1_.rotationPitch)));
        //this.model.setRotationAngles(p_225623_1_, p_225623_3_, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.model.getRenderType(grenade_texture));
        this.model.render(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.pop();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    public ResourceLocation getEntityTexture(GrenadeEntity entity) {
        return grenade_texture;
    }

}
