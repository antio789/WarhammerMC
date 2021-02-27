package warhammermod.Items.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Ranged.RatlingGun;
import warhammermod.Items.Render.model.ModelRatlingGun;

public class RenderRatlingGun extends TileEntityItemStackRenderer {
    private static String resource_location="warhammermod:textures/items/ratling_gun.png";
    static final ResourceLocation TEXTURE = new ResourceLocation(resource_location);
    private final ModelRatlingGun model = new ModelRatlingGun();
    private int cooldown;

    public void renderByItem(ItemStack stack, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        RatlingGun gun=(RatlingGun)stack.getItem();
        if(gun.isFiring()){
        if(cooldown>=gun.getFireRate()/2){
            cooldown=0;
            this.model.cannonback.rotateAngleZ+=30;
            if(this.model.cannonback.rotateAngleZ>=360){
                this.model.cannonback.rotateAngleZ=0;
            }
        }else cooldown++;
        }



        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        this.model.render();

        GlStateManager.popMatrix();
    }
}
