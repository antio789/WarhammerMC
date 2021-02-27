package warhammermod.Items.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Render.model.highelfshieldmodel;


public class RenderShieldHE extends TileEntityItemStackRenderer {
    private static String resource_location="warhammermod:textures/items/high elf shield.png";
    static final ResourceLocation SHIELD_TEXTURE = new ResourceLocation(resource_location);
    private final ModelShield model_Shield = new highelfshieldmodel();

    public void renderByItem(ItemStack p_192838_1_, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(SHIELD_TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        this.model_Shield.render();
        GlStateManager.popMatrix();
    }
}

