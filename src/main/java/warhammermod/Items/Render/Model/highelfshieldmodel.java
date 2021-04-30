package warhammermod.Items.Render.Model;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class highelfshieldmodel extends Model {
    private final ModelRenderer plate2;
    private final ModelRenderer plate3;
    private final ModelRenderer plate4;
    private final ModelRenderer plate5;
    private final ModelRenderer plate6;
    private final ModelRenderer plate7;
    private final ModelRenderer handle;

    public highelfshieldmodel()
    {
        super(RenderType::entitySolid);
        this.texWidth = 64;
        this.texHeight = 64;
        this.plate2 = new ModelRenderer(this,0,0);
        this.plate2.addBox(-6F,-11F,-2.0F,11,19,1,0.0F);
        this.plate3 = new ModelRenderer(this,0,20);
        this.plate3.addBox(-5F,8F,-2.0F,9,1,1,0.0F);
        this.plate4 = new ModelRenderer(this,0,22);
        this.plate4.addBox(-4F,9F,-2.0F,7,1,1,0.0F);
        this.plate5 = new ModelRenderer(this,0,24);
        this.plate5.addBox(-3F,10F,-2.0F,5,2,1,0.0F);
        this.plate6 = new ModelRenderer(this,0,27);
        this.plate6.addBox(-2F,12F,-2.0F,3,1,1,0.0F);
        this.plate7 = new ModelRenderer(this,0,29);
        this.plate7.addBox(-1F,13F,-2.0F,1,1,1,0.0F);
        this.handle = new ModelRenderer(this, 26, 0);
        this.handle.addBox(-1.0F, -3.0F, -1.0F, 2, 6, 6, 0.0F);
    }
    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.plate2.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate3.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate4.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate5.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate6.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate7.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.handle.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }
}
