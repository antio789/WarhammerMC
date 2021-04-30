package warhammermod.Items.Render.Model;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import warhammermod.Items.Ranged.SlingTemplate;


public class ModelSling extends Model {

    ModelRenderer cord1;
    ModelRenderer Back1;
    ModelRenderer Cord2;
    ModelRenderer Back2;
    ModelRenderer Stone;

    public ModelSling()
    {
        super(RenderType::entitySolid);
        this.texWidth = 64;
        this.texHeight = 64;
        this.cord1 = new ModelRenderer(this,0, 0);
        this.cord1.setPos(0.0F, 0.0F, 0.0F);
        this.cord1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0F);
        this.cord1.addBox(-1F, 1F, 0.0F, 2, 1, 1, 0F);
        this.cord1.addBox(0.0F, 2F, 0.0F, 1, 2, 1, 0F);
        this.cord1.addBox(-2F, 2F, 0.0F, 1, 1, 1, 0F);
        this.cord1.addBox(-1F, 4F, 0.0F, 1, 8, 1, 0F);
        this.cord1.addBox(-3F, 3F, 0.0F, 1, 7, 1, 0F);
        this.cord1.addBox(-4F, 10F, 0.0F, 1, 2, 1, 0F);
        this.Back1= new ModelRenderer(this,15,0);
        this.Back1.addBox(-4F, 12F, 0.0F, 4, 1, 1, 0F);
        this.Back1.addBox(-3F, 13F, 0.0F, 2, 1, 1, 0F);
        this.cord1.addChild(Back1);

        this.Cord2 = new ModelRenderer(this,0,0);
        this.Cord2.setPos(0,0,0);
        this.Cord2.addBox(0,0,0,1,11,1,0);
        this.Cord2.addBox(-1,4,0,1,6,1,0);
        this.Cord2.addBox(1,11,0,1,2,1,0);
        this.Cord2.addBox(-2,10,0,1,3,1,0);
        this.Back2= new ModelRenderer(this,15,0);
        this.Back2.setPos(0,0,0);
        this.Back2.addBox(1,13,0,1,1,1,0);
        this.Back2.addBox(-2,13,0,1,1,1,0);
        this.Back2.addBox(-1,14,0,2,1,1,0);
        this.Stone= new ModelRenderer(this,0,15);
        this.Stone.setPos(0,0,0);
        this.Stone.addBox(-1,12,0,2,2,1,0);
        Cord2.addChild(Back2);
        Cord2.addChild(Stone);



    }



    public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
    }

    public void render(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_, SlingTemplate item, Boolean used) {
        if(used){
            this.Cord2.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, 0.0625F);
            Cord2.zRot+=0.25*item.charging;
        }
        else{
            this.cord1.render(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, 0.0625F);
        }
    }




}
