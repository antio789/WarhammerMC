package warhammermod.Items.Render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import warhammermod.Items.Ranged.slingtemplate;
import warhammermod.util.reference;


public class ModelSling extends ModelBase {

    ModelRenderer cord1;
    ModelRenderer Back1;
    ModelRenderer Cord2;
    ModelRenderer Back2;
    ModelRenderer Stone;

    public ModelSling()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.cord1 = new ModelRenderer(this,0, 0);
        this.cord1.setRotationPoint(0.0F, 0.0F, 0.0F);
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
        this.Cord2.setRotationPoint(0,0,0);
        this.Cord2.addBox(0,0,0,1,11,1,0);
        this.Cord2.addBox(-1,4,0,1,6,1);
        this.Cord2.addBox(1,11,0,1,2,1);
        this.Cord2.addBox(-2,10,0,1,3,1);
        this.Back2= new ModelRenderer(this,15,0);
        this.Back2.setRotationPoint(0,0,0);
        this.Back2.addBox(1,13,0,1,1,1);
        this.Back2.addBox(-2,13,0,1,1,1);
        this.Back2.addBox(-1,14,0,2,1,1);
        this.Stone= new ModelRenderer(this,0,15);
        this.Stone.setRotationPoint(0,0,0);
        this.Stone.addBox(-1,12,0,2,2,1);
        Cord2.addChild(Back2);
        Cord2.addChild(Stone);



    }
    private static ResourceLocation resource_location=new ResourceLocation(reference.modid,"using");

    public void render(slingtemplate item,Boolean used){

        if(used){
            Cord2.render(0.0625F);
            Cord2.rotateAngleZ+=0.25*item.charging;
        }
        else{
            this.cord1.render(0.0625F);
            //Cord2.rotateAngleZ=0;

        }


    }
    public void render2(){


            Cord2.render(0.0625F);
            Cord2.rotateAngleZ+=0.25;


    }


}
