package warhammermod.Items.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.utils.reference;

public class RenderShield extends ItemStackTileEntityRenderer {
    private final ResourceLocation SHIELD_TEXTURE;

    private final Model modelShield;

    public RenderShield(String pathin, Model modelin){
        SHIELD_TEXTURE = new ResourceLocation(reference.modid,pathin);
        modelShield=modelin;
    }


    public void renderByItem(ItemStack p_239207_1_, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack p_239207_3_, IRenderTypeBuffer p_239207_4_, int p_239207_5_, int p_239207_6_) {
        p_239207_3_.pushPose();
        p_239207_3_.scale(1.0F, -1.0F, -1.0F);
        IVertexBuilder ivertexbuilder1 = ItemRenderer.getFoilBufferDirect(p_239207_4_, this.modelShield.renderType(SHIELD_TEXTURE), false, p_239207_1_.hasFoil());
        this.modelShield.renderToBuffer(p_239207_3_, ivertexbuilder1, p_239207_5_, p_239207_6_, 1.0F, 1.0F, 1.0F, 1.0F);
        p_239207_3_.popPose();
    }


}
