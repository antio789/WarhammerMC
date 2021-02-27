package warhammermod.Entities.living.Render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.living.EntityDwarf;
import warhammermod.Entities.living.Model.ModelDwarf;
import warhammermod.Entities.living.Render.layers.LayerHeldItemDwarf;
import warhammermod.util.utils;

@SideOnly(Side.CLIENT)
public class RenderDwarf extends RenderLiving<EntityDwarf>
{



    public RenderDwarf(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelDwarf(0.0F), 0.5F);
        this.addLayer(new LayerHeldItemDwarf(this)
        {
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
            {
                if (((EntityDwarf)entitylivingbaseIn).isAggressive(1))
                {
                    super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }
            }
            protected void translateToHand(EnumHandSide p_191361_1_)
            {
                ((ModelDwarf)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }

    public void doRender(EntityDwarf entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }



    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityDwarf entity)
    {
        return(utils.dwarftexturechooser.getdwarftexture(entity));
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityDwarf entitylivingbaseIn, float partialTickTime)
    {
        float f = 0.9375F;

        if (entitylivingbaseIn.getGrowingAge() < 0)
        {
            f = (float)((double)f * 0.5D);
            this.shadowSize = 0.25F;
        }
        else
        {
            this.shadowSize = 0.5F;
        }

        GlStateManager.scale(f, f, f);
    }

}