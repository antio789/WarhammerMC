package warhammermod.Items.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Render.model.repeaterhandgun;

public class RenderRepeater extends TileEntityItemStackRenderer {
    private static String resource_location="warhammermod:textures/items/repeater.png";
    static final ResourceLocation TEXTURE = new ResourceLocation(resource_location);
    private final repeaterhandgun model = new repeaterhandgun();


    public void renderByItem(ItemStack stack, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);


        GlStateManager.pushMatrix();
        //GlStateManager.scale(1.0F, -1.0F, -1.0F);
        this.model.render(0.0625F);

        GlStateManager.popMatrix();
    }
}
