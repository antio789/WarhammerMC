package warhammermod.Entities.Projectile.Render.Model;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WarpBulletModel<T extends Entity> extends Model {
    public ModelRenderer bullet;

    public WarpBulletModel() {
        super(RenderType::entitySolid);
        this.texWidth = 64;
        this.texHeight = 32;
        this.bullet = new ModelRenderer(this, 0, 8);
        this.bullet.setPos(0.0F, 0.0F, 0.0F);
        this.bullet.addBox(-1.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
    }

    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.bullet.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }



}
