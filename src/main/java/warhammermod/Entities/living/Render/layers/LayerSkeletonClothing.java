package warhammermod.Entities.living.Render.layers;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.living.EntityPirateSkeleton;
import warhammermod.util.reference;

@SideOnly(Side.CLIENT)
public class LayerSkeletonClothing implements LayerRenderer<EntityPirateSkeleton>
{
    private static final ResourceLocation STRAY_CLOTHES_TEXTURES = new ResourceLocation(reference.modid,"textures/entity/pirateskeleton.png");
    private final RenderLivingBase<?> renderer;
    private final ModelSkeleton layerModel = new ModelSkeleton(0.25F, true);

    public LayerSkeletonClothing(RenderLivingBase<?> p_i47183_1_)
    {
        this.renderer = p_i47183_1_;
    }

    public void doRenderLayer(EntityPirateSkeleton entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.layerModel.setModelAttributes(this.renderer.getMainModel());
        this.layerModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderer.bindTexture(STRAY_CLOTHES_TEXTURES);
        this.layerModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}