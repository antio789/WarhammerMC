package warhammermod.Entities.living.Render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.living.Render.layers.LayerSkeletonClothing;

@SideOnly(Side.CLIENT)
public class Rendermusketeer_skeleton extends RenderSkeleton
{
    private static final ResourceLocation MUSKETEER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public Rendermusketeer_skeleton(RenderManager p_i47191_1_)
    {
        super(p_i47191_1_);
        this.addLayer(new LayerSkeletonClothing(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractSkeleton entity)
    {
        return MUSKETEER_SKELETON_TEXTURES;
    }
}