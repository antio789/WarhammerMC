package warhammermod.Items.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Render.Model.repeaterhandgunmodel;


public class RenderRepeater extends ItemStackTileEntityRenderer {
    private static final String resource_location="warhammermod:textures/items/repeater.png";
    static final ResourceLocation TEXTURE = new ResourceLocation(resource_location);
    private final repeaterhandgunmodel model = new repeaterhandgunmodel();
    private static int rotation;


    public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
        p_239207_3_.pushPose();
        p_239207_3_.scale(1.0F, -1.0F, -1.0F);
        this.model.barrel_holder1.zRot=rotation;
        IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(p_239207_4_, this.model.renderType(TEXTURE), false, p_239207_1_.hasFoil());
        this.model.renderToBuffer(p_239207_3_, ivertexbuilder1, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
        p_239207_3_.popPose();
    }

    public static void setrotationangle(){
        if(rotation==300){
            rotation=0;
        }else rotation+=60;
    }
}
