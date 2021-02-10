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


    public void func_239207_a_(ItemStack p_228364_1_, ItemCameraTransforms.TransformType p_239207, MatrixStack p_228364_2_, IRenderTypeBuffer p_228364_3_, int p_228364_4_, int p_228364_5_) {
        p_228364_2_.push();
        p_228364_2_.scale(1.0F, -1.0F, -1.0F);

        IVertexBuilder ivertexbuilder = ItemRenderer.func_239391_c_(p_228364_3_, this.modelShield.getRenderType(SHIELD_TEXTURE), false, p_228364_1_.hasEffect());
        this.modelShield.render(p_228364_2_,ivertexbuilder,p_228364_4_,p_228364_5_,1.0F,1.0F,1.0F,1.0F);

        p_228364_2_.pop();
    }


}
