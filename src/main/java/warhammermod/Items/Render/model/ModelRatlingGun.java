package warhammermod.Items.Render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

/**
 * ModelPlayer - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelRatlingGun extends ModelBase {
    public ModelRenderer core1;
    public ModelRenderer recipient;
    public ModelRenderer cannon1;
    public ModelRenderer cannon6;
    public ModelRenderer cannon2;
    public ModelRenderer cannon4;
    public ModelRenderer cannon5;
    public ModelRenderer cannon3;
    public ModelRenderer cannonback;
    public ModelRenderer core2;
    public ModelRenderer core3;
    public ModelRenderer tube2;
    public ModelRenderer tube1;
    public ModelRenderer handle1;
    public ModelRenderer handle2;
    public ModelRenderer handlebar;
    public ModelRenderer cannonfront;
    public ModelRenderer container;
    public ModelRenderer tube3;
    public ModelRenderer tube4;
    public ModelRenderer tube5;
    public ModelRenderer handleback;

    public ModelRatlingGun() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.handle2 = new ModelRenderer(this,0, 50);
        this.handle2.setRotationPoint(-1.5F, 7.5F, -13.5F);
        this.handle2.addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
        this.cannon2 = new ModelRenderer(this, 35, 0);
        this.cannon2.setRotationPoint(1.4F, -1.1F, -8.0F);
        this.cannon2.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 13, 0.1F);
        this.setRotateAngle(cannon2, 0.0F, 0.0F, -0.5235987755982988F);
        this.cannon1 = new ModelRenderer(this, 35, 0);
        this.cannon1.setRotationPoint(0.0F, -1.9F, -8.0F);
        this.cannon1.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 13, 0.1F);
        this.cannonback = new ModelRenderer(this, 45, 16);
        this.cannonback.setRotationPoint(0.0F, 10.8F, -15.0F);
        this.cannonback.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 1, 0.0F);
        this.handlebar = new ModelRenderer(this, 0, 50);
        this.handlebar.setRotationPoint(0.0F, 5.5F, -14.0F);
        this.handlebar.addBox(-2.0F, 0.0F, 0.0F, 4, 1, 1, 0.0F);
        this.core2 = new ModelRenderer(this, 0, 12);
        this.core2.setRotationPoint(0.5F, 10.0F, -12.0F);
        this.core2.addBox(-2.5F, -2.5F, -2.0F, 4, 5, 1, 0.0F);
        this.core1 = new ModelRenderer(this, 0, 0);
        this.core1.setRotationPoint(0.0F, 10.0F, -5.0F);
        this.core1.addBox(-2.5F, -2.5F, -3.0F, 5, 5, 6, 0.0F);
        this.tube2 = new ModelRenderer(this, 25, 37);
        this.tube2.setRotationPoint(-0.5F, 7.5F, -13.0F);
        this.tube2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 4, 0.0F);
        this.tube1 = new ModelRenderer(this, 25, 35);
        this.tube1.setRotationPoint(-0.5F, 8.5F, -10.0F);
        this.tube1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.cannon4 = new ModelRenderer(this, 35, 0);
        this.cannon4.setRotationPoint(0.0F, 1.3F, -8.0F);
        this.cannon4.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 13, 0.1F);
        this.cannon5 = new ModelRenderer(this, 35, 0);
        this.cannon5.setRotationPoint(-1.4F, 0.5F, -8.0F);
        this.cannon5.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 13, 0.1F);
        this.setRotateAngle(cannon5, 0.0F, 0.0F, -0.5235987755982988F);
        this.core3 = new ModelRenderer(this, 0, 19);
        this.core3.setRotationPoint(0.0F, 10.2F, -12.0F);
        this.core3.addBox(-1.5F, -1.5F, -1.0F, 3, 4, 2, 0.0F);
        this.cannon3 = new ModelRenderer(this, 35, 0);
        this.cannon3.setRotationPoint(1.4F, 0.5F, -8.0F);
        this.cannon3.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 13, 0.1F);
        this.setRotateAngle(cannon3, 0.0F, 0.0F, 0.5235987755982988F);
        this.tube4 = new ModelRenderer(this, 25, 35);
        this.tube4.setRotationPoint(-3.7F, 10.4F, -5.0F);
        this.tube4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.2F);
        this.tube5 = new ModelRenderer(this, 25, 35);
        this.tube5.setRotationPoint(-3.7F, 10.4F, 0.0F);
        this.tube5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.2F);
        this.handle1 = new ModelRenderer(this, 0, 50);
        this.handle1.setRotationPoint(1.5F, 7.5F, -13.5F);
        this.handle1.addBox(-0.5F, -1.0F, -0.5F, 1, 1, 1, 0.0F);
        this.container = new ModelRenderer(this, 0, 35);
        this.container.setRotationPoint(0.0F, 14.0F, 0.5F);
        this.container.addBox(-2.5F, -4.0F, -2.5F, 5, 8, 5, 0.0F);
        this.recipient = new ModelRenderer(this, 0, 26);
        this.recipient.setRotationPoint(0.0F, 12.0F, -9.5F);
        this.recipient.addBox(-1.5F, -2.5F, -1.5F, 3, 5, 3, 0.0F);
        this.cannonfront = new ModelRenderer(this, 45, 16);
        this.cannonfront.setRotationPoint(0.0F, -0.2F, -10.0F);
        this.cannonfront.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 1, 0.0F);
        this.tube3 = new ModelRenderer(this, 23, 45);
        this.tube3.setRotationPoint(-5.1F, 10.4F, -3.0F);
        this.tube3.addBox(0.0F, 0.0F, -2.0F, 1, 1, 6, 0.2F);
        this.cannon6 = new ModelRenderer(this, 35, 0);
        this.cannon6.setRotationPoint(-1.4F, -1.1F, -8.0F);
        this.cannon6.addBox(-0.5F, -0.5F, -5.0F, 1, 1, 13, 0.1F);
        this.setRotateAngle(cannon6, 0.0F, 0.0F, 0.5235987755982988F);
        this.handleback=new ModelRenderer(this,14,12);
        this.handleback.setRotationPoint(-1,13,3);
        this.handleback.addBox(0,0,0,2,2,4);
        this.cannonback.addChild(cannon1);
        this.cannonback.addChild(cannon2);
        this.cannonback.addChild(cannon3);
        this.cannonback.addChild(cannon4);
        this.cannonback.addChild(cannon5);
        this.cannonback.addChild(cannon6);
        this.cannonback.addChild(cannonfront);
    }

    public void render() {
        this.handle2.render(0.0625F);
        this.handlebar.render(0.0625F);
        this.core2.render(0.0625F);
        this.core1.render(0.0625F);
        this.tube2.render(0.0625F);
        this.tube1.render(0.0625F);
        this.core3.render(0.0625F);
        this.tube4.render(0.0625F);
        this.tube5.render(0.0625F);
        this.handle1.render(0.0625F);
        this.container.render(0.0625F);
        this.recipient.render(0.0625F);
        this.handleback.render(0.0625F);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.cannonfront.offsetX, this.cannonfront.offsetY, this.cannonfront.offsetZ);
        GlStateManager.translate(this.cannonfront.rotationPointX * 0.0625F, this.cannonfront.rotationPointY * 0.0625F, this.cannonfront.rotationPointZ * 0.0625F);
        GlStateManager.scale(1.2D, 1.2D, 1.0D);
        GlStateManager.translate(-this.cannonfront.offsetX, -this.cannonfront.offsetY, -this.cannonfront.offsetZ);
        GlStateManager.translate(-this.cannonfront.rotationPointX * 0.0625F, -this.cannonfront.rotationPointY * 0.0625F, -this.cannonfront.rotationPointZ * 0.0625F);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.cannonback.offsetX, this.cannonback.offsetY, this.cannonback.offsetZ);
        GlStateManager.translate(this.cannonback.rotationPointX * 0.0625F, this.cannonback.rotationPointY * 0.0625F, this.cannonback.rotationPointZ * 0.0625F);
        GlStateManager.scale(1.2D, 1.2D, 1.0D);
        GlStateManager.translate(-this.cannonback.offsetX, -this.cannonback.offsetY, -this.cannonback.offsetZ);
        GlStateManager.translate(-this.cannonback.rotationPointX * 0.0625F, -this.cannonback.rotationPointY * 0.0625F, -this.cannonback.rotationPointZ * 0.0625F);
        this.cannonback.render(0.0625F);
        GlStateManager.popMatrix();
        this.tube3.render(0.0625F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
