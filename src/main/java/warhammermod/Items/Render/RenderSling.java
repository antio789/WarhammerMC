package warhammermod.Items.Render;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Ranged.SlingTemplate;
import warhammermod.Items.Render.Model.ModelSling;
import warhammermod.utils.reference;

public class RenderSling extends ItemStackTileEntityRenderer {

    private static final ResourceLocation resource_location=new ResourceLocation(reference.modid,"textures/items/sling.png");
    private final ModelSling model = new ModelSling();


    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
        SlingTemplate item = (SlingTemplate) stack.getItem();
        p_239207_3_.pushPose();
        p_239207_3_.scale(1.0F, -1.0F, -1.0F);
        Boolean used = Minecraft.getInstance().player.getUseItem()==stack;
        IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(p_239207_4_, this.model.renderType(resource_location), false, stack.hasFoil());
        this.model.render(p_239207_3_, ivertexbuilder1, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F,item,used);
        p_239207_3_.popPose();
    }




}
