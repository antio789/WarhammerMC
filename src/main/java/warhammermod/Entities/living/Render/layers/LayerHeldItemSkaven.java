package warhammermod.Entities.living.Render.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import warhammermod.Entities.living.EntitySkaven;
import warhammermod.Entities.living.Model.ModelSkaven;
import warhammermod.Items.Ranged.RatlingGun;
import warhammermod.Items.Ranged.SkavenGuns;
import warhammermod.Items.Ranged.slingtemplate;
import warhammermod.util.Handler.inithandler.Itemsinit;

@SideOnly(Side.CLIENT)
public class LayerHeldItemSkaven implements LayerRenderer<EntitySkaven>
{
    protected final RenderLivingBase<?> livingEntityRenderer;

    public LayerHeldItemSkaven(RenderLivingBase<?> livingEntityRendererIn)
    {
        this.livingEntityRenderer = livingEntityRendererIn;
    }

    public void doRenderLayer(EntitySkaven skaven, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        ItemStack itemstackmain =  skaven.getHeldItemMainhand();
        ItemStack itemstackoff = skaven.getHeldItemOffhand() ;

        if (!itemstackmain.isEmpty() || !itemstackoff.isEmpty())
        {
            GlStateManager.pushMatrix();


                float f = 0.6F;
                GlStateManager.translate(0.0F, 0.2F, -0.3F);
                GlStateManager.scale(f, f, f);


            this.renderHeldItem(skaven, itemstackmain, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            if(skaven.getHeldItemOffhand().getItem() == Itemsinit.SKAVEN_SHIELD){
            this.renderHeldItemshield(skaven, itemstackoff, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);}
            else renderHeldItem(skaven,itemstackoff, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND,EnumHandSide.LEFT);
            GlStateManager.popMatrix();
        }
    }

    private void renderHeldItem(EntitySkaven skaven, ItemStack stack, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide)
    {
        if (!stack.isEmpty())
        {
            GlStateManager.pushMatrix();

            this.translateToHand(handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            float f;// 3F-(skaven.getSkavenSize()-1.1F);
            f=2.3F;
            float z;
            float y=0;
            if(skaven.getSkavenSize()==1.3F){f=3F;}
            if(skaven.isSwingingArms()){
                y=0.45F;
                z=0.05F;
                if (stack.getItem() == Itemsinit.WarpLock_Jezzail) {
                    z = -0.15F;
                    y = 0.2F;
                } else if (stack.getItem() == Itemsinit.sling) {
                    f -= 2.6F;
                }
            }
            else {
                f=3;
                z=-0.2F;
                if (stack.getItem() == Itemsinit.WarpLock_Jezzail) {
                    z = -0.3F;
                    y = -0.2F;
                } else if (stack.getItem() == Itemsinit.sling) {
                    z = 0.08F;
                }
            }
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -f:f) / 16.0F, -0.2F+y, -0.625F+z);

            Minecraft.getMinecraft().getItemRenderer().renderItemSide(skaven, stack, p_188358_3_, false);
            GlStateManager.popMatrix();
        }
    }


    private void renderHeldItemshield(EntitySkaven skaven, ItemStack stack, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide)
    {
        if (!stack.isEmpty())
        {
            GlStateManager.pushMatrix();
            float y=0;
            float z=0;
            if (skaven.isSwingingArms())
            {
                y=0.3F;
                z=0.2F;
            }
            // Forge: moved this call down, fixes incorrect offset while sneaking.
            this.translateToHand(handSide);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-30.0F, 0.0F, 0.0F, 1.0F);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0F, -0.3F+y, -0.8F+z);
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(skaven, stack, p_188358_3_, flag);
            GlStateManager.popMatrix();
        }
    }

    protected void translateToHand(EnumHandSide p_191361_1_)
    {
        ((ModelSkaven)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, p_191361_1_);
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

    private boolean hasranged(Item item){
        return  (item instanceof SkavenGuns || item instanceof RatlingGun || item instanceof slingtemplate);
    }
}