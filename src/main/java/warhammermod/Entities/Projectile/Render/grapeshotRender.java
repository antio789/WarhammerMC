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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.Projectile.Render.Model.grapeshotModel;
import warhammermod.Entities.Projectile.ShotEntity;
import warhammermod.utils.reference;

@OnlyIn(Dist.CLIENT)
public class grapeshotRender extends EntityRenderer<ShotEntity> {
    private static final ResourceLocation BULLET_TEXTURE = new ResourceLocation(reference.modid,"textures/entity/bullet.png");
    private final grapeshotModel model;

    public grapeshotRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        model=new grapeshotModel(0);
    }
    // rip usefull naming: public void render(Projectilebase entityIn, float p_225623_2_, float partialTicks, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {


    public void render(ShotEntity entity, float p_225623_2_, float partialTicks, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.translate(0.0D, 0.15F, 0.0D);
        p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entity.yRotO, entity.yRot) - 90.0F));
        p_225623_4_.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entity.xRotO, entity.xRot) + 90.0F));
        this.model.setRotationAngles(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getFoilBufferDirect(p_225623_5_, this.model.renderType(BULLET_TEXTURE), false, false);
        this.model.renderToBuffer(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.popPose();
        super.render(entity, p_225623_2_, partialTicks, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    @Override
    public ResourceLocation getTextureLocation(ShotEntity p_110775_1_) {
        return BULLET_TEXTURE;
    }
}
