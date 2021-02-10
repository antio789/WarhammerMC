package warhammermod.Items.Render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Render.Model.RatlingGunModel;

public class RenderRatlingGun extends ItemStackTileEntityRenderer {
    private static String resource_location="warhammermod:textures/items/ratling_gun.png";
    static final ResourceLocation TEXTURE = new ResourceLocation(resource_location);
    private final RatlingGunModel model = new RatlingGunModel();
    private static int rotation;

    public void render(ItemStack stack, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.model.getRenderType(TEXTURE), false, stack.hasEffect());
        this.model.cannonback.rotateAngleZ=rotation;
        this.model.render(matrixStackIn,ivertexbuilder,combinedLightIn,combinedOverlayIn,1.0F,1.0F,1.0F,1.0F);
        matrixStackIn.pop();
    }

    public static void setrotationangle(){
        if(rotation==300){
            rotation=0;
        }else rotation+=60;
    }


}
