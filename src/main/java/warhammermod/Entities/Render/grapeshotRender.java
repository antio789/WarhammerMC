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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import warhammermod.Entities.Projectilebase;
import warhammermod.Entities.Render.Model.grapeshotModel;
import warhammermod.util.reference;

@OnlyIn(Dist.CLIENT)
public class grapeshotRender extends EntityRenderer<Projectilebase> {
    private static final ResourceLocation BULLET_TEXTURE = new ResourceLocation(reference.modid,"textures/entity/bullet.png");
    private final grapeshotModel model;

    public grapeshotRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        model=new grapeshotModel(0);
    }

    public void render(Projectilebase entityIn, float p_225623_2_, float partialTicks, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {


        p_225623_4_.push();
        p_225623_4_.translate(0.0D, (double) 0.15F, 0.0D);
        p_225623_4_.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        p_225623_4_.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
        this.model.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.model.getRenderType(BULLET_TEXTURE));
        this.model.render(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.pop();
        super.render(entityIn, p_225623_2_, partialTicks, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    public ResourceLocation getEntityTexture(Projectilebase entity) {
        return BULLET_TEXTURE;
    }
}

