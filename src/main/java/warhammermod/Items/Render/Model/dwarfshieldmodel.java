package warhammermod.Items.Render.Model;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class dwarfshieldmodel extends Model {
    public ModelRenderer plate1;
    public ModelRenderer plate2;
    public ModelRenderer plate3;
    public ModelRenderer plate4;
    public ModelRenderer plate5;
    public ModelRenderer plate6;
    public ModelRenderer plate7;
    public ModelRenderer plate8;
    public ModelRenderer plate9;
    public ModelRenderer handle;

    public dwarfshieldmodel(){
        super(RenderType::getEntitySolid);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.plate1 = new ModelRenderer(this,0,0);
        this.plate1.addBox(-3F,-11F,-2.0F,6,1,1,0.0F);
        this.plate2 = new ModelRenderer(this,0,2);
        this.plate2.addBox(-5F,-10F,-2.0F,10,1,1,0.0F);
        this.plate3 = new ModelRenderer(this,0,4);
        this.plate3.addBox(-6F,-9F,-2.0F,12,1,1,0.0F);
        this.plate4 = new ModelRenderer(this,0,6);
        this.plate4.addBox(-7F,-8F,-2.0F,14,2,1,0.0F);
        this.plate5 = new ModelRenderer(this,0,9);
        this.plate5.addBox(-8F,-6F,-2.0F,16,6,1,0.0F);
        this.plate6 = new ModelRenderer(this,0,16);
        this.plate6.addBox(-7F,0F,-2.0F,14,2,1,0.0F);
        this.plate7 = new ModelRenderer(this,0,19);
        this.plate7.addBox(-6F,2F,-2.0F,12,1,1,0.0F);
        this.plate8 = new ModelRenderer(this,0,21);
        this.plate8.addBox(-5F,3F,-2.0F,10,1,1,0.0F);
        this.plate9 = new ModelRenderer(this,0,23);
        this.plate9.addBox(-3F,4F,-2.0F,6,1,1,0.0F);
        this.handle = new ModelRenderer(this, 48, 0);
        this.handle.addBox(-1.0F, -4.0F, -1.0F, 2, 6, 6, 0.0F);
    }


    public void render(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
        this.plate1.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate2.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate3.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate4.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate5.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate6.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate7.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate8.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.plate9.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.handle.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }

}
