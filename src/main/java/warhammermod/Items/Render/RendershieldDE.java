package warhammermod.Items.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Render.model.Elfshhield_model;


public class RendershieldDE extends TileEntityItemStackRenderer {
    private static String resource_location="warhammermod:textures/items/shieldebase.png";
    static final ResourceLocation SHIELD_TEXTURE = new ResourceLocation(resource_location);
    private final ModelShield model_Shield = new Elfshhield_model();

    public void renderByItem(ItemStack p_192838_1_, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(SHIELD_TEXTURE);

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        this.model_Shield.render();
        GlStateManager.popMatrix();
    }
}
