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
import warhammermod.Items.Ranged.Slingtemplate;
import warhammermod.Items.Render.Model.ModelSling;
import warhammermod.util.reference;

public class RenderSling extends ItemStackTileEntityRenderer {

    private static final ResourceLocation resource_location=new ResourceLocation(reference.modid,"textures/items/sling.png");
    private static final ResourceLocation resource_location2=new ResourceLocation(reference.modid,"textures/items/slingf.png");
    private final ModelSling model = new ModelSling();




    public void render(ItemStack stack, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Slingtemplate item = (Slingtemplate) stack.getItem();

        matrixStackIn.push();
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        Boolean used = Minecraft.getInstance().player.getActiveItemStack()==stack;
        IVertexBuilder ivertexbuilder = ItemRenderer.getBuffer(bufferIn, this.model.getRenderType(resource_location), false, stack.hasEffect());
        this.model.render(matrixStackIn,ivertexbuilder,combinedLightIn,combinedOverlayIn,1.0F,1.0F,1.0F,1.0F,item,used);
        matrixStackIn.pop();
    }




}
