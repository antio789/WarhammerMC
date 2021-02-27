package warhammermod.Entities.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import warhammermod.Entities.EntityGrenade;
import warhammermod.Entities.render.model.model_grenade;
import warhammermod.util.reference;


public class rendergrenade<T extends EntityGrenade> extends Render<T>
{

    private ResourceLocation grenade_texture;
    private final model_grenade model= new model_grenade();

    public rendergrenade(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        setEntityTexture();


    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityGrenade entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();

        GlStateManager.translate((float)x, (float)y, (float)z);

        this.bindEntityTexture(entity);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0, 0, 0.15F);

        GlStateManager.popMatrix();

    }
    private void setEntityTexture(){
        grenade_texture= new ResourceLocation(reference.modid,"textures/entity/grenade.png");
    }
    @Override
    public ResourceLocation getEntityTexture(EntityGrenade entity){

        return grenade_texture;
    }

    protected boolean bindEntityTexture(EntityGrenade entity)
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
