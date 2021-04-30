package warhammermod.Entities.Projectile.Render.Model;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class model_grenade<T extends Entity> extends Model {
    public ModelRenderer grenadepart1;
    public ModelRenderer grenadetop;

    public model_grenade() {
        super(RenderType::entitySolid);
        this.texWidth = 64;
        this.texHeight = 32;
        this.grenadepart1 = new ModelRenderer(this, 3, 0);
        this.grenadepart1.setPos(0.0F, 0.0F, 0.0F);
        this.grenadepart1.addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.grenadetop = new ModelRenderer(this, 0, 0);
        this.grenadetop.setPos(0.0F, 0.0F, 0.0F);
        this.grenadetop.addBox(-0.5F, 1.5F, -0.5F, 1, 1, 1, 0.0F);
    }

    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.grenadetop.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.grenadepart1.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }


}
