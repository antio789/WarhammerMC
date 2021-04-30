package warhammermod.Entities.Projectile.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import warhammermod.Entities.Projectile.GrenadeEntity;
import warhammermod.Entities.Projectile.Render.Model.model_grenade;
import warhammermod.utils.reference;

public class GrenadeRender extends EntityRenderer<GrenadeEntity> {
    private final model_grenade model= new model_grenade();
    private static final ResourceLocation grenade_texture = new ResourceLocation(reference.modid,"textures/entity/grenade.png");

    public GrenadeRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    public void render(GrenadeEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(p_225623_3_, p_225623_1_.yRotO, p_225623_1_.yRot) - 90.0F));
        p_225623_4_.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(p_225623_3_, p_225623_1_.xRotO, p_225623_1_.xRot) + 90.0F));
        IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getFoilBufferDirect(p_225623_5_, this.model.renderType(grenade_texture), false, false);
        this.model.renderToBuffer(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.popPose();
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    @Override
    public ResourceLocation getTextureLocation(GrenadeEntity p_110775_1_) {
        return grenade_texture;
    }
}
