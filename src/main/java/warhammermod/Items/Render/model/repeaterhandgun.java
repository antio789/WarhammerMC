package warhammermod.Items.Render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class repeaterhandgun extends ModelBase {
    public ModelRenderer stock1;
    public ModelRenderer handle1;
    public ModelRenderer handle2;
    public ModelRenderer stockbase;
    public ModelRenderer flint;
    public ModelRenderer stock;
    public ModelRenderer barrel_holder1;
    public ModelRenderer barrel1;
    public ModelRenderer barrel_holder2;
    public ModelRenderer barrel4;
    public ModelRenderer barrel2;
    public ModelRenderer barrel3;
    public ModelRenderer barrel5;
    public ModelRenderer barrel6;

    public repeaterhandgun() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.barrel6 = new ModelRenderer(this, 0, 0);
        this.barrel6.setRotationPoint(1.7F, 1.3F, -3.0F);
        this.barrel6.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 9, 0.0F);
        this.setRotateAngle(this.barrel6, 0.0F, 0.0F, -0.5235988F);
        this.handle2 = new ModelRenderer(this, 0, 25);
        this.handle2.setRotationPoint(0.0F, -1.0F, 1.0F);
        this.handle2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.barrel_holder1 = new ModelRenderer(this, 35, 25);
        this.barrel_holder1.setRotationPoint(0.0F, -4.0F, 8.0F);
        this.barrel_holder1.addBox(-1.5F, 0.0F, 0.0F, 4, 4, 1, 0.0F);
        this.handle1 = new ModelRenderer(this, 0, 22);
        this.handle1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.handle1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
        this.stockbase = new ModelRenderer(this, 0, 25);
        this.stockbase.setRotationPoint(0.0F, -1.0F, 2.0F);
        this.stockbase.addBox(-1.0F, -2.0F, 0.0F, 3, 3, 1, 0.0F);
        this.stock1 = new ModelRenderer(this, 0, 25);
        this.stock1.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.stock1.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
        this.barrel2 = new ModelRenderer(this, 0, 0);
        this.barrel2.setRotationPoint(-0.7F, 1.3F, -3.0F);
        this.barrel2.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 9, 0.0F);
        this.setRotateAngle(this.barrel2, 0.0F, 0.0F, 0.5235988F);
        this.barrel1 = new ModelRenderer(this, 0, 0);
        this.barrel1.setRotationPoint(0.0F, 0.1F, -3.0F);
        this.barrel1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 9, 0.0F);
        this.barrel5 = new ModelRenderer(this, 0, 0);
        this.barrel5.setRotationPoint(1.7F, 2.7F, -3.0F);
        this.barrel5.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 9, 0.0F);
        this.setRotateAngle(this.barrel5, 0.0F, 0.0F, 0.5235988F);
        this.barrel_holder2 = new ModelRenderer(this, 35, 25);
        this.barrel_holder2.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.barrel_holder2.addBox(-1.5F, 0.0F, 0.0F, 4, 4, 1, 0.0F);
        this.barrel4 = new ModelRenderer(this, 0, 0);
        this.barrel4.setRotationPoint(0.0F, 2.9F, -3.0F);
        this.barrel4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 9, 0.0F);
        this.barrel3 = new ModelRenderer(this, 0, 0);
        this.barrel3.setRotationPoint(-0.7F, 2.7F, -3.0F);
        this.barrel3.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 9, 0.0F);
        this.setRotateAngle(this.barrel3, 0.0F, 0.0F, -0.5235988F);
        this.flint = new ModelRenderer(this, 57, 20);
        this.flint.setRotationPoint(0.0F, -4.0F, 2.0F);
        this.flint.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.stock = new ModelRenderer(this, 45, 0);
        this.stock.setRotationPoint(0.0F, -4.0F, 3.0F);
        this.stock.addBox(-1.5F, 0.0F, 0.0F, 4, 4, 2, 0.0F);
        this.barrel_holder1.addChild(this.barrel1);
        this.barrel_holder1.addChild(this.barrel2);
        this.barrel_holder1.addChild(this.barrel3);
        this.barrel_holder1.addChild(this.barrel4);
        this.barrel_holder1.addChild(this.barrel5);
        this.barrel_holder1.addChild(this.barrel6);
        this.barrel_holder1.addChild(this.barrel_holder2);
    }

    public void render(float f5) {
        this.handle2.render(f5);
        this.barrel_holder1.render(f5);
        this.handle1.render(f5);
        this.stockbase.render(f5);
        this.stock1.render(f5);
        this.flint.render(f5);
        this.stock.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleX = z;
    }
}

