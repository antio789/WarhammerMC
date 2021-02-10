package warhammermod.Items.Render.Model;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SkavenShieldModel extends Model {
    public ModelRenderer plate1;
    public ModelRenderer plate2;
    public ModelRenderer plate3;
    public ModelRenderer plate4;
    public ModelRenderer plate5;
    public ModelRenderer plate6;
    public ModelRenderer plate7;
    public ModelRenderer plate8;
    public ModelRenderer plate9;
    //public ModelRenderer plate10;
    public ModelRenderer handle;

    public SkavenShieldModel(){
        super(RenderType::getEntitySolid);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.handle = new ModelRenderer(this, 48, 0);
        this.handle.addBox(-1.0F, -4.0F, -1.0F, 2, 6, 6, 0.0F);
        this.plate1 = new ModelRenderer(this,0,0);
        this.plate1.addBox(-0.5F,5F,-2.0F,1,1,1,0.0F);
        this.plate2 = new ModelRenderer(this,0,2);
        this.plate2.addBox(-1.5F,4,-2,3,1,1,0.0F);
        this.plate3= new ModelRenderer(this,0,4);
        this.plate3.addBox(-2.5F,2F,-2F,5,2,1,0.0F);
        this.plate4 = new ModelRenderer(this,0,7);
        this.plate4.addBox(-3.5F,0F,-2F,7,2,1,0.0F);
        this.plate5= new ModelRenderer(this,0,10);
        this.plate5.addBox(-4.5F,-2F,-2,9,2,1,0.0F);
        this.plate6= new ModelRenderer(this,0,13);
        this.plate6.addBox(-5.5F,-4F,-2,11,2,1,0.0F);
        this.plate7= new ModelRenderer(this,0,16);
        this.plate7.addBox(-6.5F,-6F,-2,13,2,1,0.0F);
        this.plate8= new ModelRenderer(this,0,19);
        this.plate8.addBox(-7.5F,-8F,-2,15,2,1,0.0F);
        this.plate9= new ModelRenderer(this,0,22);
        this.plate9.addBox(-8.5F,-9F,-2,17,1,1,0.0F);
        /*
        this.plate10= new ModelRenderer(this,0,24);
        this.plate10.func_22830_(-8.5F,-10F,1.5F,17,1,1,0.0F);
        this.plate10.rotateAngleY=3.141592654F;
         */



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
        //this.plate10.render();(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
        this.handle.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
    }


}
