package warhammermod.Entities.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import warhammermod.Entities.EntityWarpBullet;
import warhammermod.Entities.render.model.WarpBulletModel;
import warhammermod.util.reference;

public class RenderWarpBullet<T extends EntityWarpBullet> extends Render<T> {
    private ResourceLocation bullet_texture;
    private final WarpBulletModel model= new WarpBulletModel();
    public RenderWarpBullet(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        setEntityTexture();
    }



    public void doRender(EntityWarpBullet entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        //float f = 0;
        //float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        //float f2 = (float)entity.ticksExisted + partialTicks;
        GlStateManager.translate((float)x, (float)y, (float)z);
        //GlStateManager.rotate(MathHelper.sin(f2 * 0.1F) * 180.0F, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(MathHelper.cos(f2 * 0.1F) * 180.0F, 1.0F, 0.0F, 0.0F);
        //GlStateManager.rotate(MathHelper.sin(f2 * 0.15F) * 360.0F, 0.0F, 0.0F, 1.0F);
        //float f3 = 0.03125F;
        //GlStateManager.enableRescaleNormal();
        //GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.bindEntityTexture(entity);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0, 0, 0.1F);
        //GlStateManager.enableBlend();
        //GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        //GlStateManager.scale(1.5F, 1.5F, 1.5F);
        //this.model.render(entity, 0.0F, 0.0F, 0.0F, f, f1, 0.03125F);
        //GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        //super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    private void setEntityTexture(){
        bullet_texture = new ResourceLocation(reference.modid,"textures/entity/bullet.png");
    }
    @Override
    public ResourceLocation getEntityTexture(EntityWarpBullet entity){

        return bullet_texture;
    }

    protected boolean bindEntityTexture(EntityWarpBullet entity)
    {
        ResourceLocation resourcelocation = this.getEntityTexture(entity);

        if (resourcelocation == null)
        {
            return false;
        }
        else
        {
            this.bindTexture(resourcelocation);
            return true;
        }
    }

}
