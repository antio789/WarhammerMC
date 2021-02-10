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
import warhammermod.util.reference;

public class RenderShield extends ItemStackTileEntityRenderer {
    private final ResourceLocation SHIELD_TEXTURE;

    private Model modelShield;

    public RenderShield(String pathin, Model modelin){
        SHIELD_TEXTURE = new ResourceLocation(reference.modid,pathin);
        modelShield=modelin;
    }


    public void render(ItemStack stack, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);

        IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.modelShield.getRenderType(SHIELD_TEXTURE), false, stack.hasEffect());
        this.modelShield.render(matrixStackIn,ivertexbuilder,combinedLightIn,combinedOverlayIn,1.0F,1.0F,1.0F,1.0F);

        matrixStackIn.pop();
    }


}
