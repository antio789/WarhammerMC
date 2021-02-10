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




    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207, MatrixStack p_228364_2_, IRenderTypeBuffer p_228364_3_, int p_228364_4_, int p_228364_5_) {
        Slingtemplate item = (Slingtemplate) stack.getItem();

        p_228364_2_.push();
        p_228364_2_.scale(1.0F, -1.0F, -1.0F);
        Boolean used = Minecraft.getInstance().player.getActiveItemStack()==stack;
        IVertexBuilder ivertexbuilder = ItemRenderer.func_239391_c_(p_228364_3_, this.model.getRenderType(resource_location), false, stack.hasEffect());
        this.model.render(p_228364_2_,ivertexbuilder,p_228364_4_,p_228364_5_,1.0F,1.0F,1.0F,1.0F,item,used);
        p_228364_2_.pop();
    }



}
