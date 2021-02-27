package warhammermod.Entities.living.Render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.living.EntitySkaven;
import warhammermod.Entities.living.Model.ModelSkaven;
import warhammermod.Entities.living.Render.layers.LayerHeldItemSkaven;
import warhammermod.util.reference;

@SideOnly(Side.CLIENT)
public class RenderSkaven extends RenderLiving<EntitySkaven>
{

    public RenderSkaven(RenderManager manager)
    {
        super(manager, new ModelSkaven(0), 0.25F);
        this.addLayer(new LayerHeldItemSkaven(this));
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntitySkaven entity, double x, double y, double z, float entityYaw, float partialTicks)
    {

        this.shadowSize = 0.25F *entity.getSkavenSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntitySkaven entitylivingbaseIn, float partialTickTime)
    {

        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSkavenSize();
        GlStateManager.scale(f1, f1, f1);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntitySkaven entity)
    {
        return new ResourceLocation(reference.modid,"textures/entity/skaven/"+entity.getSkaventype()+".png");
    }


}