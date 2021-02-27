package warhammermod.Items.Render;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Ranged.slingtemplate;
import warhammermod.Items.Render.model.ModelSling;
import warhammermod.util.reference;

public class RenderSling extends TileEntityItemStackRenderer {

    private static ResourceLocation resource_location=new ResourceLocation(reference.modid,"textures/items/sling.png");
    private static ResourceLocation resource_location2=new ResourceLocation(reference.modid,"textures/items/slingf.png");
    private final ModelSling model = new ModelSling();




    public void renderByItem(ItemStack stack, float partialTicks) {
        slingtemplate item = (slingtemplate)stack.getItem();
        //boolean isfiring = item.firing;
        Minecraft.getMinecraft().getTextureManager().bindTexture(resource_location);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
            Boolean used = Minecraft.getMinecraft().player.getActiveItemStack()==stack;
            this.model.render(item,used);
        GlStateManager.popMatrix();
    }



}
