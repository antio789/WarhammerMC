package warhammermod.Entities.Render;

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
import warhammermod.Entities.Projectilebase;
import warhammermod.Entities.Render.Model.BulletModel;
import warhammermod.util.reference;

@OnlyIn(Dist.CLIENT)
public class BulletRender extends EntityRenderer<Projectilebase> {
    private static final ResourceLocation BULLET_TEXTURE = new ResourceLocation(reference.modid,"textures/entity/bullet.png");
    private final BulletModel model;

    public BulletRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        model=new BulletModel(0);
    }

    public void render(Projectilebase p_225623_1_, float entityYaw, float partialTicks, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int packedLightIn) {
            p_225623_4_.push();
            p_225623_4_.translate(0.0D, (double) 0.15F, 0.0D);
            p_225623_4_.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, p_225623_1_.prevRotationYaw, p_225623_1_.rotationYaw) - 90.0F));
            p_225623_4_.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, p_225623_1_.prevRotationPitch, p_225623_1_.rotationPitch)));
            //this.model.setRotationAngles(p_225623_1_, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
            IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.model.getRenderType(BULLET_TEXTURE));
            this.model.render(p_225623_4_, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            p_225623_4_.pop();
            super.render(p_225623_1_, entityYaw, partialTicks, p_225623_4_, p_225623_5_, packedLightIn);
    }

    public ResourceLocation getEntityTexture(Projectilebase entity) {
        return BULLET_TEXTURE;
    }
}
