package warhammermod.Entities.living.Render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.living.EntityPegasus;
import warhammermod.Entities.living.Model.ModelPegasus;
import warhammermod.util.reference;


@SideOnly(Side.CLIENT)
public class RenderPegasus extends RenderLiving<EntityPegasus>
{
    private static final ResourceLocation LAYERED_LOCATION_CACHE = new ResourceLocation(reference.modid,"textures/entity/pegasus.png");

    public RenderPegasus(RenderManager p_i47205_1_)
    {
        super(p_i47205_1_, new ModelPegasus(), 0.75F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityPegasus entity)
    {
        return LAYERED_LOCATION_CACHE;
    }


}